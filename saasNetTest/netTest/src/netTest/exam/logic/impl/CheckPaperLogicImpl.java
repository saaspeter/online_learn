package netTest.exam.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.common.logic.QuesTypeHandle;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.constant.UseranswerConstant;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.dto.TestuserQuery;
import netTest.exam.dto.UseranswerQuery;
import netTest.exam.logic.CheckPaperLogic;
import netTest.exam.logic.ExampaperLogic;
import netTest.exam.logic.TestuserLogic;
import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTest.exam.vo.Useranswer;
import netTest.exception.ExceptionConstant;
import netTest.paper.dao.PaperDao;
import netTest.paper.logic.PaperquestypeLogic;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.logic.impl.QuestionUtil;

import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.concurrent.AbstractTaskRunnable;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.DateUtil;

/**
 * 处理考试相关信息
 * @author fan
 *
 */
public class CheckPaperLogicImpl implements CheckPaperLogic {

	private ExampaperLogic examLogic;
	private TestinfoDao testDao;
	private PaperquestypeLogic questypelogic;
	private UseranswerDao answerDao;
	private TestuserDao testuserDao;
	private TestdeptDao testdeptDao;

	/**
	 * 自动判卷，通过对比试题和用户的答案来评卷。返回时试卷的总分
	 * 用于评阅一个考生的答案
	 * @param paperVO
	 * @param usranwMap: 题目quesid和题目列表集合的Map
	 * @return
	 */
    public Float checkUserpaper(Paper paperVO,Map<Long,Answerquestype> usranwMap,boolean updateDB){
    	Float paperscore = 0f;
    	Float tempscore = null;
    	Float patternscore = 0f;
    	if(paperVO==null||paperVO.getQuestypeList()==null||usranwMap==null||usranwMap.isEmpty())
    		return paperscore;
    	QuesTypeHandle handle = JudgePaperUseranswerHandle.getInstance();
    	Map<Long,Paperques> quesMap = paperVO.getQuesanswerMap();
    	if(quesMap==null){
    		paperVO = examLogic.getTestPaperAnswer(paperVO.getPaperid(), 1);
    		quesMap = paperVO.getQuesanswerMap();
    	}
    	Answerquestype answertypeVO = null;
    	Useranswer answerVO = null;
    	Paperques quesVO = null;
    	List<Useranswer> useranswerList = null;
    	// 准备更新用户答案db记录的list
    	List<Useranswer> allUseranswerList = null;
    	if(updateDB){
    		allUseranswerList = new ArrayList<Useranswer>();
    	}
    	Iterator keys = usranwMap.keySet().iterator();
    	while(keys.hasNext()){
    		answertypeVO = usranwMap.get(keys.next());
    		if(answertypeVO!=null&&answertypeVO.getAnswerList()!=null){
    			useranswerList = answertypeVO.getAnswerList();
    			patternscore = 0f;
    			for(int i=0;i<useranswerList.size();i++){
    				answerVO = useranswerList.get(i);
    				if(answerVO!=null&&quesMap.get(answerVO.getQuesid())!=null){
    					quesVO = quesMap.get(answerVO.getQuesid());
    					tempscore = (Float)handle.handleQuesType(quesVO, answerVO, quesVO.getQuestype());
    					if(tempscore!=null){
    						patternscore += tempscore;
    						paperscore += tempscore;
    						// 准备更新用户答案数据
    						if(updateDB && answerVO.getUseranswerid()!=null){
    							allUseranswerList.add(answerVO);
    						}
    					}
    				}
    			}
    			answertypeVO.setAnswerscore(patternscore);
    		}
    	}
    	if(updateDB){
            // 更新考生答案分数等
	    	answerDao.updBatchUseranswer(allUseranswerList);
    	}
    	return paperscore;
    }
    
    /**
     * 自动评阅一场考试的试卷，并且更新考生的分数和考试的阅卷状态
     * @param testid
     * @return 返回自动阅卷后的状态:自动阅卷中，后台会用异步处理来自动阅卷
     */
    public Short autoCheckAllTestpaper(Long testid){
    	// 检查参数是否为空
    	if(testid==null)
    		throw new LogicException(ExceptionConstant.Error_Need_Paramter);
    	Testinfo infovo = testDao.selectByPK(testid);
    	if(infovo==null)
    		throw new NoSuchRecordException(ExceptionConstant.Error_Testinfo_notExist);
    	//TODO 以后一场考试中每个用户的试卷有可能不同的，以后可能要先查询出不同的试卷，再根据不同的试卷批量阅卷
    	Paper papervo = examLogic.getTestPaperAnswer(infovo.getPaperid(),2);
    	if(papervo==null)
    		throw new NoSuchRecordException(ExceptionConstant.Error_Paper_notExist);
    	// 更新考试状态为 自动评阅中，后面会用异步处理过程来处理
    	Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
    	testDao.updTeststatus(testid, TestinfoConstant.Teststatus_autoChecking, curDate);
    	// 从此处以下的代码应该都是异步执行的，以异步方式调用，返回自动阅卷过程中
    	Map paraMap = new HashMap();
		paraMap.put("testVO", infovo);
		paraMap.put("paperVO", papervo);
    	EventHandlerNetTest.getInstance().getThreadExecutor().executeTask(
				AbstractParallelExecutor.Module_Event, 
                new AbstractTaskRunnable(paraMap){
                    public void run(){
                    	Map paraMap2 = (Map)this.getParamObject();
                    	Testinfo testVO = (Testinfo)paraMap2.get("testVO");
                    	Long testid = testVO.getTestid();
                    	Paper papervo2 = (Paper)paraMap2.get("paperVO");
                    	Long paperid = papervo2.getPaperid();
                    	// 判断试卷题型中包含哪种类型的题型  1:客观题；2：主观题；3：客观主观题都有
                    	int hasquestype = questypelogic.paperHasQuestype(testVO.getPaperid());
                    	int pagesize = SysparamConstantNettest.checkPaperAnswer_PageSize.intValue();
                    	// 查询本次考试中共有多少个用户的答案数, TODO 不应该带paperid，因为每个user的paper可能会不同
                    	long totalNumber = answerDao.countAllTestAnswer(testid, papervo2.getPaperid(), testVO.getShopid(),4,UseranswerConstant.ExamineStatus_UnJudge);
                    	if(totalNumber>0&&(hasquestype==1||hasquestype==3)){
                    		QuesTypeHandle handle = JudgePaperUseranswerHandle.getInstance();
                    		// 整理试卷答案
                    		Map<Long,Paperques> quesMap = papervo2.getQuesanswerMap();
                        	Useranswer answerVO = null;
                        	Paperques quesVO = null;
                    		// 查询考生答案
                	    	long totalPage = (totalNumber - 1)/pagesize + 1;
                	    	long startnum = 0;
                	    	List<Useranswer> answerList = null;
                	    	// 分页查询一批用户的答案
                	    	UseranswerQuery queryVO = new UseranswerQuery();
                	    	queryVO.setTestid(testid);
                	    	queryVO.setPaperid(papervo2.getPaperid());
                	    	// 查询客观题和主客观题(填空题)
                	    	queryVO.setSearchCheckType(4);
                	    	queryVO.setPagesize(pagesize);
                	    	// 分批判卷,对每批用户答案阅卷，阅卷完毕后写回数据表每题得分和对错情况
                	    	for(int currentpage=0;currentpage<totalPage;currentpage++){
                	    		startnum = currentpage*pagesize;
                		    	queryVO.setStartnum(startnum);
                		    	answerList = answerDao.selectByVO(queryVO);
                		    	if(answerList!=null)
                		    	for(int i=0;i<answerList.size();i++){
                		    		answerVO = answerList.get(i);
                		    		quesVO = quesMap.get(answerVO.getQuesid());
                		    		// 判阅题目
                		    		handle.handleQuesType(quesVO, answerVO, quesVO.getQuestype());
                		    	}
                		    	// 更新考生答案分数等
                		    	answerDao.updBatchUseranswer(answerList);
                	    	}
                    	}
                    	// 更改考试状态为手动阅卷状态。（如果没有主观题，直接更改为阅卷完毕）
                    	Short nextteststatus = TestinfoConstant.Teststatus_allChecked;
                    	// 试卷自动评阅完毕后，调用新方法为每个考生计算总分，更新考生信息表testuser。
                    	//TODO 这里如果考生大于1000，可能会出现性能问题，暂时不考虑
                    	TestuserLogic testuserlogic = BOFactory.getTestuserLogic();
                    	testuserlogic.sumTestuserScore(testid, paperid, QuestionConstant.Queschecktype_auto, null);
                    	//
                    	if(hasquestype==1){
                    	   // 如果只有客观题，则统计考试情况
                    	   doSatisTestinfo(testVO, paperid);
                    	}else if(hasquestype==3){
                    	   // 主要是统计考生填空题的得分情况，并且设置是否及格。
                    	   testuserlogic.sumTestuserScore(testid, paperid, QuestionConstant.Queschecktype_both, papervo2.getPaperquascore());
                    	   nextteststatus = TestinfoConstant.Teststatus_manualChecking;
                    	}
                    	Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
                    	testDao.updTeststatus(testid, nextteststatus, curDate);
                    }
				});
    	return TestinfoConstant.Teststatus_autoChecking;
    }
    
    /**
     * 保存单个试题考生评阅分数结果
     * @param testid
     * @param questype
     * @param quesscore
     * @param useranswerList
     * @return
     */
    public int saveCheckResult(Integer questype,float quesscore,List<Useranswer> useranswerList){
    	if(questype==null||useranswerList==null||useranswerList.size()<1)
    		return 0;
   		Useranswer voTemp = null;
    	if(QuestionConstant.QuesType_TianKong.equals(questype)){
     		float score = 0f;
    		for(int i=0;i<useranswerList.size();i++){
    			voTemp = useranswerList.get(i);
    			score = QuestionUtil.calScoreTianKong(quesscore, voTemp.getIsright());
    			voTemp.setAnswerscore(score);
    			voTemp.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
    		}
    	}else{
    		for(int i=0;i<useranswerList.size();i++){
    			voTemp = useranswerList.get(i);
    			voTemp.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
    		}
    	}
    	int nums = answerDao.updBatchUseranswer(useranswerList);
    	return nums;
    }
    
    /**
     * 统计考试的情况。如果所有试题都已经评阅完毕，则自动统计考试成绩情况
     * @param testid
     * @param paperid
     * @param testVO
     * @return 1:还存在没有评阅的试题 2:所有试题已经评阅完毕，正在进行考试成绩统计 
     * 3:考试评阅并统计完毕(因为采用异步，不可能出现3)
     */
    public int satisTestinfo(Long testid,Long paperid,Testinfo testVO){
    	if(testid==null||paperid==null)
    		throw new NeedParamsException();
    	if(testVO==null){
    		testVO = testDao.selectByPK(testid);
    	}
    	// 如果状态不是:手动评阅试卷中，则抛出异常
    	if(!TestinfoConstant.Teststatus_manualChecking.equals(testVO.getTeststatus())){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
    	int rtn = 1; // 还存在没有评阅的试题
    	// 查询本次考试中还有多少没有评阅的试题答案数
    	long totalNumber = answerDao.countAllTestAnswer(testid, paperid, testVO.getShopid(), null, UseranswerConstant.ExamineStatus_UnJudge);
    	if(totalNumber==0){
    		// 更新考试状态为 统计考试中，后面会用异步处理过程来处理
        	Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
        	testDao.updTeststatus(testid, TestinfoConstant.Teststatus_statisticTest, curDate);
        	// 统计考试，应该使用异步处理
    		Map paraMap = new HashMap();
    		paraMap.put("testVO", testVO);
    		paraMap.put("paperid", paperid);
    		EventHandlerNetTest.getInstance().getThreadExecutor().executeTask(
    				AbstractParallelExecutor.Module_Event, 
                    new AbstractTaskRunnable(paraMap){
                        public void run(){
                        	Map paraMap2 = (Map)this.getParamObject();
                        	Testinfo testVO2 = (Testinfo)paraMap2.get("testVO");
                        	Long paperid2 = (Long)paraMap2.get("paperid");
                        	doSatisTestinfo(testVO2, paperid2);
                        }
					});
    		rtn = 2;
     	}
    	return rtn;
    }

    /**
     * 执行统计考试情况。包括：统计个人分数，是否及格，统计单位的考试情况
     * @param testid
     * @param paperid
     * @param shopid
     * @return 2: 所有试题已经评阅完毕，正在进行考试成绩统计  3: 考试评阅并统计完毕
     */
    private int doSatisTestinfo(Testinfo testVO, Long paperid){
    	if(testVO==null||paperid==null)
    		return -1;
    	if(TestinfoConstant.Teststatus_allChecked.equals(testVO.getTeststatus()))
    		return 3;
    	Long testid = testVO.getTestid();
    	//
//    	Thread satThread = new Thread(){
//    		public void run(){
    			PaperDao paperDao = BOFactory.getPaperDao();
    	    	Paper paperVO = paperDao.selectByPK_plain(paperid);
    	    	// 试卷自动评阅完毕后，调用新方法为每个考生计算总分，更新考生信息表testuser。TODO 这里如果考生大于1000，可能会出现性能问题，暂时不考虑
    	    	TestuserLogic testuserlogic = BOFactory.getTestuserLogic();
    	    	int rtn = testuserlogic.sumTestuserScore(testid, paperid, null, paperVO.getPaperquascore());
    	    	// 设置考生名次
    	    	int pagesize = SysparamConstantNettest.checkPaperAnswer_PageSize.intValue();
    	    	TestuserQuery queryVO = new TestuserQuery();
    	    	queryVO.setTestid(testid);
    	    	queryVO.setPaperid(paperid);
    	    	long totalNumber = testuserDao.countTestuser(queryVO);
    	    	long totalPage = (totalNumber - 1)/pagesize + 1;
    	    	long startnum = 0;
    	    	queryVO.setPagesize(pagesize);
    	    	queryVO.setOrderColumn("totalscore");
    	    	queryVO.setOrderDirect(2);
    	    	List<Testuser> userList = null;
    	    	Testuser testuserVO = null;
    	    	// 分批设置考生名次，仅仅统计所有考生的排名，而不去统计在单位中的排名
    	    	int orderbase = 1; // 排名基数，每批设置后的名次最大数
    	    	int orderCurrent = 1; // 记录现在排到第几名了，当分数相同时会有并列名次的说法
    	    	float score = -1;
    	    	for(int currentpage=0;currentpage<totalPage;currentpage++){
    	    		startnum = currentpage*pagesize;
    		    	queryVO.setStartnum(startnum);
    		    	userList = testuserDao.selectForOrderByVO(queryVO);
    		    	if(userList!=null){
    			    	for(int i=0;i<userList.size();i++){
    			    		testuserVO = userList.get(i);
    			    		if(score!=-1&&(score==testuserVO.getTotalscore())){
    			    			testuserVO.setOrdernoall(orderCurrent);
    			    		}else{
    			    			testuserVO.setOrdernoall(orderbase);
    			    			score = testuserVO.getTotalscore();
    			    			orderCurrent = orderbase;
    			    		}
    			    		orderbase++;
    			    	}
    			    	// 更新考生答案分数等
    			    	testuserDao.updateTestOrderByPK(userList);
    		    	}
    	    	}
    	    	// 统计单位考试情况
    	    	testdeptDao.satDeptAvescore(testid, testVO.getShopid());
    	    	// 更改考试状态为阅卷完毕
    	    	Short nextteststatus = TestinfoConstant.Teststatus_allChecked;
    	    	Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
    	    	testDao.updTeststatus(testid, nextteststatus, curDate);
//			}
//    	};
//    	satThread.start();
    	return 3;
    }
    
	/**
	 * static spring getMethod
	 */
	 public static CheckPaperLogic getInstance(){
		 CheckPaperLogic logic = (CheckPaperLogic)BeanFactory.getBeanFactory().getBean("checkPaperLogic");
	     return logic;
	 }



	public ExampaperLogic getExamLogic() {
		return examLogic;
	}

	public void setExamLogic(ExampaperLogic examLogic) {
		this.examLogic = examLogic;
	}

	public UseranswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(UseranswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public PaperquestypeLogic getQuestypelogic() {
		return questypelogic;
	}

	public void setQuestypelogic(PaperquestypeLogic questypelogic) {
		this.questypelogic = questypelogic;
	}

	public TestinfoDao getTestDao() {
		return testDao;
	}

	public void setTestDao(TestinfoDao testDao) {
		this.testDao = testDao;
	}

	public TestuserDao getTestuserDao() {
		return testuserDao;
	}

	public void setTestuserDao(TestuserDao testuserDao) {
		this.testuserDao = testuserDao;
	}

	public TestdeptDao getTestdeptDao() {
		return testdeptDao;
	}

	public void setTestdeptDao(TestdeptDao testdeptDao) {
		this.testdeptDao = testdeptDao;
	}

}
