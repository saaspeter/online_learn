package netTest.exam.logic.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.logic.ExampaperLogic;
import netTest.exam.logic.TestuserLogic;
import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTest.exam.vo.Useranswer;
import netTest.exception.ExceptionConstant;
import netTest.paper.vo.Paper;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.UserproductDao;
import netTest.product.dao.impl.UserproductDaoImpl;
import netTest.product.vo.Productbase;
import commonTool.constant.PayTypeConstant;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;

/**
 * 处理考试相关信息
 * @author fan
 *
 */
public class TestuserLogicImpl implements TestuserLogic, EventHandle {

	private TestinfoDao testinfoDao;
	private TestuserDao testuserDao;
	private TestdeptDao testdeptDao;
	private UseranswerDao answerDao;
	private ExampaperLogic exampaperlogic;
	
    
	/**
	 * 批量增加考试人员，会过滤掉已经有的考试人员， 同时更新本次考试的总人数
	 * @param shopid
	 * @param testid
	 * @param orgbaseid
	 * @param userIdstr 用英文逗号隔开
	 * @param testdeptid: 考试的统计信息单位
	 * @return
	 */
	public int addTestuser(Long shopid,Long testid,String userIdstr){
		if(testid==null||shopid==null)
			throw new NeedParamsException(" -- need parameters in TestuserLogicImpl.addTestuser");
		int nums = 0;
		if(userIdstr==null||userIdstr.trim().length()<0)
			return nums;
		String[] tempArr1 = StringUtil.splitString(userIdstr, ",");
		String existusers = testuserDao.qryJoinTestusers(testid, userIdstr, shopid);
		
		List list = new ArrayList();
		Testuser vo = null;
		for(int i=0;i<tempArr1.length;i++){ 
			if(!StringUtil.includeStr(existusers, tempArr1[i].trim(), null)){
				vo = new Testuser();
				vo.setTestid(testid);
				vo.setShopid(shopid);
				vo.setUserid(new Long(tempArr1[i]));
				vo.setObjectscore(0f);
				vo.setTotalscore(0f);
				vo.setIsqualify(TestuserConstant.IsQualify_notpassed);
	            vo.setStatus(TestuserConstant.Status_unStart);
	            list.add(vo);
			}
		}
		// 真正新增test user前需要判断resuce limit
		Testinfo testvo = testinfoDao.selectByPK(testid);
		Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(testvo.getProductbaseid());
		Testdept testdeptvo = testdeptDao.selSatTestDept(testid);
		int testuserlimit = SysparamConstantNettest.maxExamUser_freeProduct_num;
		if(!PayTypeConstant.PayType_free.equals(prodvo.getPaytype())) {
			testuserlimit = SysparamConstantNettest.maxExamUser_paidProduct_num;
		}
		if(testuserlimit-testdeptvo.getExamernum()-list.size()<0){
			throw new LogicException(ExceptionConstant.Error_ResourceLimit_MaxExamUser);
		}
		nums = testuserDao.insertBatch(list);
		// 更新考试单位的人数
		Testdept record = new Testdept();
		record.setShopid(shopid);
        record.setTestid(testid);
		record.setExamernum(nums);
		record.setNotexamnum(nums);
		testdeptDao.updateExamNumSat(record);
		return nums;
	}
	
	/**
	 * 添加学习考试课程的所有学员到本次考试中(只添加学员，不加管理员)
	 * @param shopid
	 * @param testid
	 * @param productid
	 * @return
	 */
	public int addAllProductUserIntoTest(Long shopid,Long testid){
		UserproductDao usrprodDao = UserproductDaoImpl.getInstance();
		Testinfo testvo = testinfoDao.selectByPK(testid);
		List<Long> useridList = usrprodDao.selectAllProdUserId(testvo.getProductbaseid(), shopid, UserproductConstant.ProdUseType_userNormal);
		int batchInd = 0;
		StringBuffer buffer = null;
		int allsize = useridList.size();
		// 每500条记录进行一次运算
		while(batchInd<useridList.size()){
			buffer = new StringBuffer();
			for(int j=batchInd;j<allsize;j++){
				buffer.append(useridList.get(j).toString()).append(",");
			}
			this.addTestuser(shopid, testid, buffer.toString());
			batchInd += 500;
		}
		return allsize;
	}
	
	/**
	 * 删除一个考试单位，同时删除该单位下的考试人员
	 * @param testid
	 * @param shopid
	 * @param testdeptid
	 * @param orgbaseid
	 * @return
	 */
	public int removeTestuser(Long testid,Long shopid,String[] keys,Long testdeptid){
		if(testid==null||shopid==null||keys==null||keys.length==0)
			return 0;
		Testinfo testinfoVO = testinfoDao.selectByPK(testid);
		if(testinfoVO==null)
			throw new NoSuchRecordException("-- test info doesn't exists");
		if(!TestinfoConstant.Teststatus_unStart.equals(testinfoVO.getTeststatus()))
			throw new LogicException("-- cannot remove the testuser when test started");
		int nums = testuserDao.deleteBatchByPK(keys);
		// 更新考试单位的人数
		Testdept record = new Testdept();
		record.setShopid(shopid);
		record.setTestid(testid);
		record.setExamernum(-nums);
		record.setNotexamnum(-nums);
		testdeptDao.updateExamNumSat(record);
		return nums;
	}
	
	/**
	 * 新增考试时判断用户输入的考试时间是否和本单位已有的考试有时间冲突
	 * @param shopidStr
	 * @param useridStr
	 * @return
	 */
    public String qryTestCollideUser(String shopidStr,String useridStr){
    	if(useridStr==null||useridStr.trim().length()<1)
			throw new NeedParamsException("--TestuserLogicImpl.qryTestCollideAddTest--");
    	StringBuffer buffer = new StringBuffer();
    	Long shopid = null;
    	if(shopidStr!=null&&shopidStr.trim().length()>0){
    		shopid = new Long(shopidStr.trim());
    	}
		Long userid = new Long(useridStr.trim());
		List list = BOFactory.getTestuserDao().qryTestCollideUser(shopid, userid);
		if(list!=null&&list.size()>0){
			Testuser userVO = null;
			for(int i=0;i<list.size();i++){
				userVO = (Testuser)list.get(i);
				if(userVO!=null){
					buffer.append(userVO.getTestname()).append("(")
				      .append(DateUtil.printDate(userVO.getTeststartdate(), DateUtil.DATE_TIME_FORMAT_1))
			          .append(" - ").append(DateUtil.printDate(userVO.getTestenddate(), DateUtil.DATE_TIME_FORMAT_1))
			          .append(")").append("; ");
				}
			}
		}
		return buffer.toString();
	}
    
    /**
     * 为新加入课程的用户增加正在进行的考试
     * @param productid
     * @param userid
     * @return
     */
    public int autoJoinProductUpcomingTest(Long productid, Long userid){
    	List<Testinfo> testList = BOFactory.getTestinfoDao().selectUpcomingByProduct(productid);
    	int resultNum = 0;
	    if(testList!=null && testList.size()>0){
		   TestuserLogic testuserLogic = BOFactory.getTestuserLogic();
		   for(Testinfo testvo : testList){
			   if(TestinfoConstant.OpenType_ProductUser.equals(testvo.getOpentype())){
			      addTestuser(testvo.getShopid(), testvo.getTestid(), userid.toString());
			      resultNum++;
			   }
		   }
	    }
	    return resultNum;
    }
    
    /**
     * 用户加入open Test，并且初始化用户的答案，为用户生成试题，插入用户答案表
     * @param usranwMap
     * @return
     */
    public Testuser autoJoinTest(Testinfo testVO,Long userid){
    	addTestuser(testVO.getShopid(), testVO.getTestid(), userid.toString());
    	Testuser userVO = testuserDao.selectByLogicPK(testVO.getTestid(), userid);
    	return userVO;
    }
    
    /**
     * 初始化用户的答案，为用户生成试题，插入用户答案表
     * @param usranwMap
     * @return
     */
    public boolean initUseranswer(Long testid,Long paperid,Long userid,Long shopid,int needRandom){
    	if(testid==null||paperid==null||userid==null||shopid==null)
    		return false;
    	boolean ret = false;
    	int rows = 0;
    	Paper paperVO = exampaperlogic.getTestPaperAnswer(paperid, 0);
    	Map<Long,Answerquestype> usranwMap = exampaperlogic.initTestUseranswer(testid, userid, paperid, shopid, paperVO, needRandom);
    	Set<Map.Entry<Long,Answerquestype>> entrys = usranwMap.entrySet();
    	// 存入数据表
    	if(entrys!=null){
    		List<Useranswer> listall = new ArrayList<Useranswer>();
    		Answerquestype typeVO = null;
    		Useranswer voTemp = null;
    	    Iterator<Map.Entry<Long,Answerquestype>> ite = entrys.iterator();
		    while(ite.hasNext()){
		    	typeVO = ite.next().getValue();
		    	if(typeVO.getAnswerList()!=null){
			    	for(int i=0;i<typeVO.getAnswerList().size();i++){
			    		voTemp = typeVO.getAnswerList().get(i);
			    		listall.add(voTemp);
			    		// 如果有子题目，则加入子题目
			    		if(voTemp.getSubanswList()!=null){
			    			listall.addAll(voTemp.getSubanswList());
			    		}
			    	}
		    	}
		    }
		    rows = answerDao.insertBatch(listall);
    	}
    	if(rows>0)
    		ret = true;
    	return ret;
    }
    
    /**
     * 得到考生所剩答卷时间,单位是秒
     * @param papertime
     * @param testenddate
     * @return
     */
    public long getTestLefttime(long lefttime,Date testenddate){
    	Date nowDate = DateUtil.getInstance().getNowtime_GLNZ();
    	long interval = DateUtil.getInterval(testenddate, nowDate, Calendar.SECOND);
    	if(lefttime>interval)
    		return interval;
    	else
    		return lefttime;
    }
	
    /**
     * 更新一场考试的考生得分和是否及格。这是在评阅完考生的答案后执行的。
     * @param testid
     * @param paperid
     * @param queschecktype: 评阅题目类型。见question。null 代表 手动判题和 自动手动判题 的两种情况
     * @param paperquascore: 试卷及格分，如果不为空则检查并更新考生是否及格
     * @return
     */
    public int sumTestuserScore(Long testid,Long paperid,Short queschecktype,Float paperquascore){
    	if(testid==null||paperid==null)
    		return 0;
    	List<Testuser> list = testuserDao.sumUserScore(testid, paperid, queschecktype);
    	int nums = testuserDao.updBatchUserScore(list);
    	if(paperquascore!=null){
    		testuserDao.setUserQualify(testid, paperquascore);
    	}
    	return nums;
    }
    
    /**
     * 供job使用，删除无用的useranswer数据
     * 删除 openTest结束后，开放考试结果15天后的useranswer记录
     * 删除openTest, 查看结果是 '用户考完试就可以查看详细结果', 
     * 并且testuser结束考试的时间超过15天用户的useranser记录被删除
     */
    public void cleanUseranswerJob(){
    	Date date = DateUtil.getInstance().getNowtime_GLNZ();
    	// 取现在时间的前15天的时间
    	date = DateUtil.dateAddDays(date, -15);
    	answerDao.cleanOpentestUseranswer(date);
    }
	
	/**
	 * static spring getMethod
	 */
	 public static TestuserLogic getInstance(){
		 TestuserLogic logic = (TestuserLogic)BeanFactory.getBeanFactory().getBean("testuserLogic");
	     return logic;
	 }

	public TestuserDao getTestuserDao() {
		return testuserDao;
	}


	public void setTestuserDao(TestuserDao testuserDao) {
		this.testuserDao = testuserDao;
	}

	public TestinfoDao getTestinfoDao() {
		return testinfoDao;
	}

	public void setTestinfoDao(TestinfoDao testinfoDao) {
		this.testinfoDao = testinfoDao;
	}

	public TestdeptDao getTestdeptDao() {
		return testdeptDao;
	}

	public void setTestdeptDao(TestdeptDao testdeptDao) {
		this.testdeptDao = testdeptDao;
	}

	public UseranswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(UseranswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public ExampaperLogic getExampaperlogic() {
		return exampaperlogic;
	}

	public void setExampaperlogic(ExampaperLogic exampaperlogic) {
		this.exampaperlogic = exampaperlogic;
	}

	// 当新增test考试时候，如果考试opentype是:，这新增所有课程的学员到考试中
	@Override
	public void onEvent(Event event) {
		String eventType = event.getEventType();
		Map paraMap = event.getParaMap();
		Long testid = new Long(paraMap.get("testid").toString());
		Long shopid = new Long(paraMap.get("shopid").toString());
		if(EventHandlerNetTest.EventType_TestInfo_Added.equals(eventType)){
			Testinfo testvo = testinfoDao.selectByPK(testid);
			if(TestinfoConstant.OpenType_ProductUser.equals(testvo.getOpentype())){
			    addAllProductUserIntoTest(shopid, testid);
			}
		}
	}

}
