package netTest.exam.logic.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.common.constant.RolesConstant;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestcheckerConstant;
import netTest.exam.constant.TestdeptConstant;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.dao.TestcheckerDao;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.dto.TestinfoQuery;
import netTest.exam.logic.CheckPaperLogic;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.vo.Testchecker;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTest.exception.ExceptionConstant;
import netTest.paper.dao.PaperDao;
import netTest.paper.vo.Paper;

import org.springframework.security.GrantedAuthority;

import commonTool.base.Page;
import commonTool.event.Event;
import commonTool.exception.NeedParamsException;
import commonTool.exception.NoRightException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

/**
 * 处理考试相关信息
 * @author fan
 *
 */
public class TestinfoLogicImpl implements TestinfoLogic {

	private TestinfoDao testDao;
	private TestcheckerDao checkerDao;
	private TestuserDao testuserDao;
	private TestdeptDao testdeptDao;
	private PaperDao paperDao;
	private UseranswerDao useranswerDao;
	private CheckPaperLogic checkpaperLogic;

	/**
	 * 查询要列出来的考试信息，本函数为操作员查看的。学员能看到的考试信息不在此方法
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	// sql的逻辑：当magtesttype=1时，查询操作者单位创建的考试和参与的考试，因为所有创建考试的单位在testDept表中都存在
	// 当magtesttype=2时，查询操作者单位的下级单位创建的考试和参与的考试
	public Page listTestForOper(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total){
		if(queryVO.getShopid()==null)
			throw new NeedParamsException("--TestinfoLogic:listTestForOper()");
		if(queryVO.getCreateorgid()!=null||queryVO.getCreateuserid()!=null){
			// 如果指定了考试的创建者，则不需要根据查询者的单位来显示考试情况了。
			queryVO.setOrgbaseid(null);
		}else{
			// 默认只查询和我相关的考试
			if(queryVO.getQrytesttype()==null||
				(!"1".equals(queryVO.getQrytesttype())&&(!"2".equals(queryVO.getQrytesttype()))))
			{
				queryVO.setQrytesttype("1");
			}
		}
		Page page = testDao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		return page;
	}
	
	/**
	 * 查询要列出来的考试信息，本函数为参加考试的考生查看的
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
//	public Page listTestForUser(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total){
//		Page page = testDao.selectByVOPage(queryVO, pageIndex, pageSize,total);
//		return page;
//	}
	
	/**
	 * 查询在主页上要显示的开放考试
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
//	public Page listOpenTest(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total){
//		if(queryVO==null){
//			return Page.EMPTY_PAGE;
//		}
//		queryVO.setOpentype(TestinfoConstant.OpenType_AllUser_Internet);
//		// 只查询准备中的考试和进行中的考试
//		queryVO.setTeststatuspage(TestinfoConstant.Teststatus_testPrepareOrStart);
//		Page page = testDao.listTestByProdCatePage(queryVO, pageIndex, pageSize,total);
//		// 加载shop信息
//		if(page!=null&&page.getList()!=null&&page.getList().size()>0){
//		   ShopDao shopdao = platform.logicImpl.BOFactory_Platform.getShopDao();
//		   Testinfo infoVO = null;
//		   Shop shopVO = null;
//		   for(int i=0;i<page.getList().size();i++){
//			   infoVO = (Testinfo)page.getList().get(i);
//			   if(infoVO!=null){
//				   shopVO = shopdao.selectByPK(infoVO.getShopid(), queryVO.getLocaleid());
//				   infoVO.setShopname(shopVO.getShopname());
//			   }
//		   }
//		}
//		return page;
//	}
	
    /**
     * 保存考试信息
     * @param record
     * @return
     * @throws Exception
     */
    public Testinfo save(Testinfo record){
    	if(record==null)
    		return null;
		if(record.getTestid()==null||record.getTestid().intValue()==0){
			Long pkValue = testDao.insert(record);
			record.setTestid(pkValue);
			// 把创建考试的人员作为默认的考试阅卷人员
			Testchecker checkerVO = new Testchecker();
			checkerVO.setTestid(pkValue);
			checkerVO.setUserid(record.getCreateuserid());
			checkerVO.setShopid(record.getShopid());
			checkerVO.setIsexamcreator(TestcheckerConstant.IsExamCreator_yes);
			checkerVO.setCheckquesnum(0);
			checkerDao.insert(checkerVO);
			// 同时把本单位插入考试单位列表中，记录本次考试的总的统计信息
			Testdept deptVO = new Testdept();
			deptVO.setTestid(pkValue);
			deptVO.setShopid(record.getShopid());
			deptVO.setSelfendexamnum(0);
			deptVO.setSelfexamernum(0);
			deptVO.setSelfexamingnum(0);
			deptVO.setOrgbaseid(record.getCreateorgid());
			deptVO.setType(TestdeptConstant.Type_TestInfo);
			testdeptDao.insert(deptVO);
			
			// send a message for add testinfo
			// 目的是: 在课程通知中生成一条课程新增考试的feed, when add new test, will add test in the product post
			// 如果课程opentype是：所有课程用户自动加入，则把课程中的所有用户加入该考试中
			Map<String,String> paraMap = new HashMap<String,String>();
 		    paraMap.put("testid", pkValue.toString());
 		    paraMap.put("shopid", record.getShopid().toString());
 		    Event event = new Event(EventHandlerNetTest.EventType_TestInfo_Added, paraMap);
 		    EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
			
    		return record;
		}else{
			testDao.updateByPK(record);
			return record;
		}
    }
    
    /**
     * 删除考试信息
     * @param testid
     * @param vo
     * @param shopid
     * @param userorgid
     * @return
     */
    public int deleteTest(Long testid, Testinfo vo, Long shopid,Long userorgid){
    	if(testid==null)
    		return 0;
    	int rtn = 1;
    	if(vo==null) {
    	   vo = testDao.selectByPK(testid);
    	}
    	if(vo==null)
    		throw new NoSuchRecordException(ExceptionConstant.Error_Testinfo_notExist);
    	if(userorgid!=null && !vo.getCreateorgid().equals(userorgid)){
    		throw new NoRightException();
    	}
    	if(!shopid.equals(vo.getShopid()))
    		throw new NoRightException("--no right to do that");
    	if(TestinfoConstant.Teststatus_unStart.equals(vo.getTeststatus())
    	   ||TestinfoConstant.Teststatus_allChecked.equals(vo.getTeststatus())
    	   ||TestinfoConstant.Teststatus_openExam.equals(vo.getTeststatus()))
    	{
    		testuserDao.deleteByTest(testid, shopid);
    		testdeptDao.deleteByTest(testid, shopid);
    		checkerDao.deleteByTest(testid, shopid);
    		if(!TestinfoConstant.Teststatus_unStart.equals(vo.getTeststatus())){
    			// TODO 需要删除考试人员的答案表和考试成绩统计表
    			useranswerDao.deleteByTest(testid, shopid);
    		}
    		testDao.deleteByPK(testid);
    	}else
    		rtn = -2;
    	return rtn;
    }
    
    /**
     * 根据productid删除考试信息, 一般要用异步线程的job来处理
     * @param productid
     * @param shopid
     * @return
     */
    public void deleteTestByProd(Long productid, Long shopid){
    	TestinfoQuery queryVO = new TestinfoQuery();
    	queryVO.setProductbaseid(productid);
    	queryVO.setShopid(shopid);
    	List list = testDao.selectByVO(queryVO);
    	if(list!=null) {
    		Testinfo votemp = null;
    		for(int i=0; i<list.size(); i++){
    			votemp = (Testinfo)list.get(i);
    			deleteTest(votemp.getProductbaseid(), votemp, votemp.getShopid(), null);
    		}
    	}
    }
    
    /**
     * 根据产品id查询考试数量
     * @param productid
     * @param shopid
     * @param teststatus
     * @return
     */
    public int countTestinfoNumber(Long productid, Long shopid, Short teststatus){
    	int count = testDao.countTestinfoNumber(productid, shopid, teststatus);
    	return count;
    }
    
	/**
	 * 新增考试时判断用户输入的考试时间是否和本单位已有的考试有时间冲突
	 * @param shopidStr
	 * @param orgbaseidStr
	 * @param teststartdateStr
	 * @param testenddateStr
	 * @return
	 */
	public String qryTestCollideAddTest(String shopidStr,String orgbaseidStr,
			                            String teststartdateStr,String testenddateStr,String testidStr){
		if(shopidStr==null||shopidStr.trim().length()<1||orgbaseidStr==null||orgbaseidStr.trim().length()<1
				||teststartdateStr==null||teststartdateStr.trim().length()<1
				||testenddateStr==null||testenddateStr.trim().length()<1)
			throw new NeedParamsException("--TestinfoLogicImpl.qryTestCollideAddTest--");
		StringBuffer buffer = new StringBuffer();
		Long shopid = new Long(shopidStr.trim());
		Long orgbaseid = new Long(orgbaseidStr.trim());
		Long testid = null;
		if(testidStr!=null&&testidStr.trim().length()>0){
			testid = new Long(testidStr);
		}
		Date teststartdate = DateUtil.parseStrToDate(teststartdateStr, DateUtil.DATE_TIME_FORMAT);
		Date testenddate = DateUtil.parseStrToDate(testenddateStr, DateUtil.DATE_TIME_FORMAT);
		List list = BOFactory.getTestinfoDao().qryTestCollideAddTest(shopid, teststartdate, testenddate, orgbaseid,testid);
		if(list!=null&&list.size()>0){
			Testinfo infoVO = null;
			for(int i=0;i<list.size();i++){
				infoVO = (Testinfo)list.get(i);
				if(infoVO!=null){
					buffer.append(infoVO.getTestname()).append("(")
					      .append(DateUtil.printDate(infoVO.getTeststartdate(), DateUtil.DATE_TIME_FORMAT_1))
				          .append(" - ").append(DateUtil.printDate(infoVO.getTestenddate(), DateUtil.DATE_TIME_FORMAT_1))
				          .append(")").append("; ");
				}
			}
		}
		return buffer.toString();
	}
	
//    /**
//     * 在考试信息列表页面显示某个单位是否有时间冲突的考试
//     * @param shopidStr
//     * @param orgbaseidStr
//     * @return
//     */
//    public String qryTestCollideForDept(String shopidStr,String orgbaseidStr){
//    	if(shopidStr==null||shopidStr.trim().length()<1||orgbaseidStr==null||orgbaseidStr.trim().length()<1)
//			throw new NeedParamsException("--TestinfoLogicImpl.qryTestCollideAddTest--");
//    	StringBuffer buffer = new StringBuffer();
//    	Long shopid = new Long(shopidStr.trim());
//		Long orgbaseid = new Long(orgbaseidStr.trim());
//		List list = BOFactory.getTestinfoDao().qryTestCollideForDept(shopid,orgbaseid);
//		if(list!=null&&list.size()>0){
//			Testinfo infoVO = null;
//			for(int i=0;i<list.size();i++){
//				infoVO = (Testinfo)list.get(i);
//				if(infoVO!=null){
//					buffer.append(infoVO.getTestname()).append("(")
//				      .append(DateUtil.printDate(infoVO.getTeststartdate(), DateUtil.DATE_TIME_FORMAT_1))
//			          .append(" - ").append(DateUtil.printDate(infoVO.getTestenddate(), DateUtil.DATE_TIME_FORMAT_1))
//			          .append(")").append("; ");
//				}
//			}
//		}
//		return buffer.toString();
//	}
	
    /**
     * 查找单场考试信息，会保存在缓存中的TODO
     * @param testid
     * @return
     */
    public Testinfo qryTestInfo(Long testid){
    	if(testid==null)
    		return null;
    	Testinfo vo = testDao.selectByPK(testid);
    	if(vo!=null&&vo.getTeststatus()!=null){
    		Short nextteststatus = TestinfoConstant.getNextTeststatus(vo.getTeststatus());
    		// 如果下一个状态是：主观题评阅中，则不显示，即：不用操作者启动这个状态
    		if(TestinfoConstant.Teststatus_manualChecking.equals(nextteststatus)){
    			nextteststatus = null;
    		}
    		vo.setNextteststatus(nextteststatus);
    	}
    	return vo;
    }
    
    /**
     * 改变考试的状态
     * @param testid
     * @param userorgid
     * @return 考试的下一个状态
     */
    public Short changeStatus(Long testid,Long userorgid,Long shopid){
    	Testinfo vo = testDao.selectByPK(testid);
    	if(vo==null)
    		throw new NoSuchRecordException(ExceptionConstant.Error_Testinfo_notExist);
    	if(!userorgid.equals(vo.getCreateorgid()))
    		throw new NoRightException();
    	Short nextteststatus = TestinfoConstant.getNextTeststatus(vo.getTeststatus());        
        Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
        if(nextteststatus.equals(TestinfoConstant.Teststatus_started))
        {   // 记录试卷的版本信息
        	nextteststatus = statusStartTest(testid,vo.getPaperid());
        } else if(nextteststatus.equals(TestinfoConstant.Teststatus_ended))
        {
        	nextteststatus = statusEndTest(testid,curDate);
        } else if(nextteststatus.equals(TestinfoConstant.Teststatus_beginCheck)) // 开始客观题评阅
        {
       	  //TODO 建立一个类处理阅卷。该类会接收本次考试信息，如果现在处理的阅卷次数小于规定的次数，
    	   // 则修改考试状态为自动评阅中，新启动一个线程开始阅卷（如果阅卷结束会修改考试状态信息）。
    	   // 如果次数大于规定的次数，则把本次考试阅卷信息插入一张表中。当有试卷评阅完毕时会触发该类中的另一个方法，
    	   // 该方法会去表中查询出一些要评阅的试卷信息，为每个评阅的考试起新线程处理。
        	// 目前仅仅是采用了异步处理方式自动阅卷，返回的状态是自动阅卷中
        	nextteststatus = checkpaperLogic.autoCheckAllTestpaper(testid);
        } else if(nextteststatus.equals(TestinfoConstant.Teststatus_allChecked)) // 设置评阅结束
        {
    	    testDao.updTeststatus(testid, nextteststatus, curDate);
        } else if(nextteststatus.equals(TestinfoConstant.Teststatus_openExam))
        {
    	    int rows = testDao.updTeststatus(testid, nextteststatus, curDate);
    		if(rows>0){
    	       // 考试开放考试结果的时候发送消息，记录该产品完成一场考试
    		   Map<String,String> paraMap = new HashMap<String,String>();
    		   paraMap.put("productid", vo.getProductbaseid().toString());
    		   paraMap.put("number", "1");
    		   Event event = new Event(EventHandlerNetTest.EventType_TestInfo_Finished, paraMap);
    		   EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
    		}
    	} 

        return nextteststatus;
    }
    
    /**
     * 更改考试状态:开始考试。
     * @param testid
     * @param paperid
     * @return
     */
    public Short statusStartTest(Long testid,Long paperid){
    	if(testid==null||paperid==null)
    		throw new NeedParamsException("-- testinfo is lost --");
    	Paper vo = paperDao.selectByPK(paperid);
    	AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Paper_notExist);
    	
    	Testinfo testinfoVO = new Testinfo();
    	testinfoVO.setTestid(testid);
    	testinfoVO.setPaperid(paperid);
    	testinfoVO.setPaperversion(vo.getVersion());
    	testinfoVO.setTeststatus(TestinfoConstant.Teststatus_started);
    	testDao.updateByPK(testinfoVO);
    	return TestinfoConstant.Teststatus_started;
    }
    
    /**
     * 更改考试状态:结束考试
     * @param testid
     * @return
     */
    public Short statusEndTest(Long testid, Date curDate){
    	if(testid==null)
    		throw new NeedParamsException("-- testinfo is lost --");
    	testDao.updTeststatus(testid,TestinfoConstant.Teststatus_ended,curDate);
    	// change all examing status users 
    	testuserDao.updateStatusByTestinfo(testid, TestuserConstant.Status_examing, TestuserConstant.Status_endExam);
 	    return TestinfoConstant.Teststatus_ended;
    }
 
    /**
     * 查询考试信息，该方法的结果将被缓存
     * @param testid
     * @return
     */
    public Testinfo getTestinfo(Long testid){
    	if(testid==null)
    		return null;
    	Testinfo vo = testDao.selectByPK(testid);
    	return vo;
    }
    
    /**
     * 加载用户在test中的权限，并且加载test所属的product中的权限
     */
    public GrantedAuthority[] loadContainerAuthority(Long userid, Long testid, Long userShopid){
    	Testinfo vo = testDao.selectByPK(testid);
    	if(vo==null){
    		return null;
    	}
    	GrantedAuthority[] rtnArr = null;
    	Testchecker checkvo = checkerDao.selectByLogicPK(userid, testid);
    	if(checkvo!=null){
    		rtnArr = RolesConstant.GrantedAuthority_TestInfoAdmin;
    	}else {
    		Testuser testuser = testuserDao.selectByLogicPK(testid, userid);
    		if(testuser!=null){
    			rtnArr = RolesConstant.GrantedAuthority_TestInfoAccessor;
    		}
    	}
    	// load test product auth, TODO 暂时不加载，如果出错了，再在此加上

    	return rtnArr;
    }
    
	
	/**
	 * static spring getMethod
	 */
	 public static TestinfoLogic getInstance(){
		 TestinfoLogic logic = (TestinfoLogic)BeanFactory.getBeanFactory().getBean("testinfoLogic");
	     return logic;
	 }

	public TestinfoDao getTestDao() {
		return testDao;
	}

	public void setTestDao(TestinfoDao testDao) {
		this.testDao = testDao;
	}

	public TestcheckerDao getCheckerDao() {
		return checkerDao;
	}

	public void setCheckerDao(TestcheckerDao checkerDao) {
		this.checkerDao = checkerDao;
	}

	public TestdeptDao getTestdeptDao() {
		return testdeptDao;
	}

	public void setTestdeptDao(TestdeptDao testdeptDao) {
		this.testdeptDao = testdeptDao;
	}

	public TestuserDao getTestuserDao() {
		return testuserDao;
	}

	public void setTestuserDao(TestuserDao testuserDao) {
		this.testuserDao = testuserDao;
	}

	public PaperDao getPaperDao() {
		return paperDao;
	}

	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	public CheckPaperLogic getCheckpaperLogic() {
		return checkpaperLogic;
	}

	public void setCheckpaperLogic(CheckPaperLogic checkpaperLogic) {
		this.checkpaperLogic = checkpaperLogic;
	}

	public UseranswerDao getUseranswerDao() {
		return useranswerDao;
	}

	public void setUseranswerDao(UseranswerDao useranswerDao) {
		this.useranswerDao = useranswerDao;
	}


}
