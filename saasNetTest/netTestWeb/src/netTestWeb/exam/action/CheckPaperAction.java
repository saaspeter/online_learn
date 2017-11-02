
package netTestWeb.exam.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.constant.UseranswerConstant;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.dto.UseranswerQuery;
import netTest.exam.logic.CheckPaperLogic;
import netTest.exam.logic.ExampaperLogic;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Useranswer;
import netTest.exception.ExceptionConstant;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTest.wareques.constant.QuestionConstant;
import netTestWeb.base.BaseAction;
import netTestWeb.exam.form.CheckPaperForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.exception.InvalideVisitException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;

/** 
 *
 */
public class CheckPaperAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CheckPaperAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("checktestinfo".equals(myaction)) {
			myforward = beginchecktestinfo(mapping, actionform, request,response);
		} else if ("manaualCheckPaper".equals(myaction)) {
			myforward = showManaualCheckPaper(mapping, actionform, request,response);
		} else if ("checkanswerPage".equals(myaction)) {
			myforward = checkUseranswerPage(mapping, actionform, request,response);
		} else if ("saveCheckResult".equals(myaction)) {
			myforward = saveCheckResult(mapping, actionform, request,response);
		} else if ("finishcheckpaper".equals(myaction)) {
			myforward = finishCheckPaper(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;  
	}
	
	/** 
	 * 需要评阅考试列表，暂时不需要了，左侧菜单只显示考试列表，不需要专门的阅卷列表和结果列表
	 */
//	private ActionForward checktestlist(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) 
//	{
//		CheckPaperForm theForm = (CheckPaperForm) form;
//		LoginInfoEdu loginfo = getLoginInfo();
//		Long shopid = loginfo.getShopid();
//		Long userid = loginfo.getUserid();
//		Long productid = theForm.getProductbaseid();
//		TestcheckerQuery queryVO = theForm.getQueryVO();
//		
//        // 处理切换用户的商店
//		shopid = switchShop(shopid,queryVO);
//		// 处理切换用户产品
//		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_operatorMag);
//		
//		String teststatusStr = null;
//		if(queryVO.getTeststatus()==null){
//			teststatusStr = TestinfoConstant.Teststatus_ended.toString()+","
//			                + TestinfoConstant.Teststatus_manualChecking;
//		}
//		TestcheckerDao dao = BOFactory.getTestcheckerDao();
//		// 只查询需要手动评阅的考试信息
//		List list = dao.qryCheckTestListByVO(userid, shopid, queryVO.getProductbaseid(),
//											 queryVO.getTeststatus(), teststatusStr, "b.testEndDate desc");
//		theForm.setChecktestList(list);
//		
//		return mapping.findForward("checktestlist");
//	}

	/**
	 * 开始考试阅卷，如果考试刚结束，就进行自动评阅。如果是手动评阅，则进入手动评阅页面
	 */
	private ActionForward beginchecktestinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CheckPaperForm theForm = (CheckPaperForm) form;
		Long testid = theForm.getTestVO().getTestid();
		LoginInfoEdu loginfo = getLoginInfo();
		AssertUtil.assertNotNull(testid, null);
		TestinfoLogic testlogic = BOFactory.getTestinfoLogic();
		Testinfo testVO = testlogic.getTestinfo(testid);
		AssertUtil.assertNotNull(testVO, null);
		Long orgbaseid = loginfo.getOrgbaseid();
		Long shopid = loginfo.getShopid();
        
		String messCode = null;
		String actionUrl = null;
		// 考试状态是:考试结束
		if(TestinfoConstant.Teststatus_ended.equals(testVO.getTeststatus())){
			// 处理自动阅卷
			Short nextstatus = testlogic.changeStatus(testid, orgbaseid, shopid);
			
			if(TestinfoConstant.Teststatus_autoChecking.equals(nextstatus)){
				messCode = "netTestWeb.exam.action.CheckPaperAction.AutoCheckingPaper";
				actionUrl = "listTodoTestinfo.do?queryVO.teststatuspage="+TestinfoConstant.Teststatus_checkPaper;
				super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
			}else if(TestinfoConstant.Teststatus_manualChecking.equals(nextstatus)){
				// 如果自动阅卷后状态是:手动阅卷中，则转到手动阅卷页面，提示信息:自动阅卷完毕，进入手动评阅考试阶段
				messCode = "netTestWeb.exam.action.CheckPaperAction.AutoCheckPaperFinish";
				actionUrl = "manaualCheckPaper.do?testVO.testid="+testid;
				super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
			}else if(TestinfoConstant.Teststatus_allChecked.equals(nextstatus)){
				// 如果自动阅卷后状态是:阅卷结束，则转到:阅卷试卷列表，提示信息是:试卷评阅完毕，可以在考试设置中开放本次考试结果
				messCode = "netTestWeb.exam.action.CheckPaperAction.AllPaperQuesChecked";
				actionUrl = "monitorTest.do?queryVO.testid="+testid;
				super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
			}
		}else if(TestinfoConstant.Teststatus_manualChecking.equals(testVO.getTeststatus())){
			// 如果考试已经是手动阅卷状态，则直接转入手动阅卷页面
			actionUrl = "manaualCheckPaper.do?testVO.testid="+testid;
			super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
		}else {
			throw new LogicException();
		}
		return mapping.findForward("toUrl");
	}
	
	/**
	 * 展现手动判阅试卷的界面
	 */
	private ActionForward showManaualCheckPaper(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CheckPaperForm theForm = (CheckPaperForm) form;
		Long testid = theForm.getTestVO().getTestid();
		Long shopid = getLoginInfo().getShopid();
		if(testid==null){
			throw new InvalideVisitException(ExceptionConstant.Error_Invalid_Visit);
		}
		ExampaperLogic examlogic = BOFactory.getExampaperLogic();
		TestinfoLogic testlogic = BOFactory.getTestinfoLogic();
		UseranswerDao dao = BOFactory.getUseranswerDao();
		Testinfo testVO = testlogic.qryTestInfo(testid);
		// 检查考试状态是否符合评阅考试的条件
		if(testVO==null||testVO.getTeststatus()==null||
				!TestinfoConstant.Teststatus_manualChecking.equals(testVO.getTeststatus())){
			throw new InvalideVisitException(ExceptionConstant.Error_Invalid_Visit);
		}
		// 如果传入的quesid不为空，并且从界面传入的该题状态不是“在评阅中”，则把该题目的评阅状态改为原来的状态(未评阅或预评阅)。
		// 显示出来的待评阅的考生答案在数据库中的状态时正在评阅，但是在界面上是“未评阅”或“预评阅”
		if(theForm.getQuesid()!=null&&theForm.getUseranswerList()!=null&&theForm.getUseranswerList().size()>0){
			Useranswer voTemp = null;
			List<Useranswer> tempList = theForm.getUseranswerList();
			for(int i=tempList.size()-1;i>-1;i--){
				voTemp = tempList.get(i);
				if(!UseranswerConstant.ExamineStatus_UnJudge.equals(voTemp.getExaminestatus())
				   &&!UseranswerConstant.ExamineStatus_PreJudge.equals(voTemp.getExaminestatus()))
				{
					tempList.remove(i);
				}
			}
			dao.updStatusBatchByPK(tempList);
		}
		// 查询考试评阅情况
		Long paperid = testVO.getPaperid();
		theForm.setTestVO(testVO);
		Paper vo = examlogic.getTestPaperAnswer(paperid,3);
		theForm.setPaperVO(vo);
		// 查找各个题型的评阅情况
		Map<Long,UseranswerQuery> map = dao.countQuestypeAnswer(testid, paperid, shopid, 5);
		theForm.setQuescheckMap(map);

		return mapping.findForward("checkpaperMain");
	}
	
	/**
	 * 进入每一题的手动检查页面
	 */
	private ActionForward checkUseranswerPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CheckPaperForm theForm = (CheckPaperForm) form;
		Long testid = theForm.getTestVO().getTestid();
		Long paperid = theForm.getPaperVO().getPaperid();
		Long quesid = theForm.getQuesid();
		Long pid = theForm.getPid();
				
		ExampaperLogic examlogic = BOFactory.getExampaperLogic();
		// 查询缓存中的试卷
		Paper vo = examlogic.getTestPaperAnswer(paperid,3);
		theForm.setPaperVO(vo);
		// 查找试题,如果有父题目，则查询父题目
		Paperques quesVO = null;
		if(pid!=null)
		    quesVO = vo.getQuesanswerMap().get(pid);
		else
			quesVO = vo.getQuesanswerMap().get(quesid);
		theForm.setQuesVO(quesVO);
		// 查询5个用户答题答案   	
    	List<Useranswer> answerList = qryUseranswerForCheck(testid,paperid,quesid,quesVO.getQuestype());
    	theForm.setUseranswerList(answerList);
    	
		return mapping.findForward("checkUseranswerPage");
	}
	
	/**
	 * 保存阅卷结果
	 */
	private ActionForward saveCheckResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CheckPaperForm theForm = (CheckPaperForm) form;
		Long testid = theForm.getTestVO().getTestid();
        int tourltype = theForm.getTourltype();
        List<Useranswer> useranswerList = theForm.getUseranswerList();
        Integer questype = theForm.getQuesVO().getQuestype();
        Long quesid = theForm.getQuesid();
        Long pid = theForm.getPid();
        Long paperid = theForm.getPaperVO().getPaperid();
        float quesscore = theForm.getQuesscore(); // 每题/每空的分值
        
        int ret = 0;
    	String retJspUrl = "";
    	String actionUrl = "";
    	boolean needSatis = false; // 是否需要统计评阅考试信息
    	String messCode = "";
        TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
        Testinfo testVO = testLogic.qryTestInfo(testid);
        CheckPaperLogic logic = BOFactory.getCheckPaperLogic();
        // 保存评阅的结果
        logic.saveCheckResult(questype, quesscore, useranswerList);
        
        if(tourltype==1){ // 读取下一页考生试题答案
        	ExampaperLogic examlogic = BOFactory.getExampaperLogic();
    		// 查询缓存中的试卷
    		Paper vo = examlogic.getTestPaperAnswer(paperid,3);
    		theForm.setPaperVO(vo);
    		// 查找试题,如果有父题目，则查询父题目
    		Paperques quesVO = null;
    		if(pid!=null)
    		    quesVO = vo.getQuesanswerMap().get(pid);
    		else
    			quesVO = vo.getQuesanswerMap().get(quesid);
    		theForm.setQuesVO(quesVO);
    		// 查询5个用户答题答案   	
        	List<Useranswer> answerList = qryUseranswerForCheck(testid,testVO.getPaperid(),quesid,questype);
        	if(answerList!=null&&answerList.size()>0){
        	   theForm.setUseranswerList(answerList);
        	   retJspUrl = "checkUseranswerPage";
        	}else{
        		needSatis = true;
        	}
        }else{ // 返回手动阅卷主界面。 统计考试
        	needSatis = true;
        }
        
    	if(needSatis){
    		// 统计考试情况
    		ret = logic.satisTestinfo(testid, testVO.getPaperid(), testVO);
    		retJspUrl = "toUrl";
    		actionUrl = "manaualCheckPaper.do?testVO.testid="+testid;
    		if(ret==1){
    			// 该题已经评阅完毕
    			messCode = "netTestWeb.exam.action.CheckPaperAction.ThisQuesAnswerFinish";
    		}else if(ret==2||ret==3){
    			// 所有试题已经评阅完毕，正在进行考试成绩统计 或者 统计已完成
    			messCode = "netTestWeb.exam.action.CheckPaperAction.AllPaperQuesChecked";
    			actionUrl = "monitorTest.do?queryVO.testid="+testid;
    		}
    		super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
    	}
		return mapping.findForward(retJspUrl);
	}
	
	
	/**
	 * 手动结束试卷评阅，发生在如果刚巧所有试题已经评阅完毕，但是还没有出发试卷评阅完毕的动作
	 * 这时可以选择手动结束试卷评阅. 用户也可以在该已经评阅完的试卷上修改已经评阅的试题
	 */
	private ActionForward finishCheckPaper(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CheckPaperForm theForm = (CheckPaperForm) form;
		Long testid = theForm.getTestVO().getTestid();
		TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
		Testinfo testVO = testLogic.qryTestInfo(testid);
		AssertUtil.assertNotNull(testVO, null);
        CheckPaperLogic logic = BOFactory.getCheckPaperLogic();
		int ret = logic.satisTestinfo(testid, testVO.getPaperid(), testVO);
		// 转到考试监控页面
		String actionUrl = "monitorTest.do?queryVO.testid="+testid;
		String messCode = "";
		if(ret==1){
			// 还存在没有评阅的试题
			messCode = "netTestWeb.exam.action.CheckPaperAction.ThisQuesAnswerFinish";
			actionUrl = "manaualCheckPaper.do?testVO.testid="+testid;
		}else if(ret==2||ret==3){
			// 所有试题已经评阅完毕，正在进行考试成绩统计 或者 统计已完成
			messCode = "netTestWeb.exam.action.CheckPaperAction.AllPaperQuesChecked";
		}
		super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
		return mapping.findForward("toUrl");
	}
	
	// 查询考生的答案出来以供评阅
	private List<Useranswer> qryUseranswerForCheck(Long testid,Long paperid,Long quesid,Integer questype){
		UseranswerDao dao = BOFactory.getUseranswerDao();
		UseranswerQuery queryVO = new UseranswerQuery();
    	queryVO.setTestid(testid);
    	queryVO.setPaperid(paperid);
    	queryVO.setQuesid(quesid);
    	// 查询未评阅的考生答案
    	if(QuestionConstant.Queschecktype_both.
    			equals(QuestionConstant.getQueschecktype(questype))){
    		// 如果是填空题则查询预评阅的试题，否则查询未评阅的试题
    	    queryVO.setExaminestatus(UseranswerConstant.ExamineStatus_PreJudge);
    	}else{
    		queryVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
    	}
    	queryVO.setPagesize(2); // 每页只显示5个考生答案
    	queryVO.setStartnum(0l);
    	List<Useranswer> answerList = dao.selectByVO(queryVO);
    	
    	if(answerList!=null&&answerList.size()>0){ // 更改这些题目的状态为评阅中
    		// 注意：显示在界面上的考生的答案却仍是原来的状态(未评阅，已评阅等)
    		List<Useranswer> updList = new ArrayList<Useranswer>();
    		Useranswer voTemp = null;
    		Useranswer voTemp2 = null;
    		for(int i=0;i<answerList.size();i++){
    			voTemp2 = new Useranswer();
    			voTemp = (Useranswer)answerList.get(i);
    			voTemp2.setUseranswerid(voTemp.getUseranswerid());
    			voTemp2.setExaminestatus(UseranswerConstant.ExamineStatus_Judging);
    			updList.add(voTemp2);
    		}
    		dao.updStatusBatchByPK(updList);
    	}else{  // 如果待评阅答案没有了，则查询正在评阅的考生答案
    		queryVO.setExaminestatus(UseranswerConstant.ExamineStatus_Judging);
    		answerList = dao.selectByVO(queryVO);
    	}
    	return answerList;
	}
	
	
}
