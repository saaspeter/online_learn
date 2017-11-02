package netTestWeb.exam.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.logic.CheckPaperLogic;
import netTest.exam.logic.ExampaperLogic;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.logic.TestuserLogic;
import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTest.exam.vo.Useranswer;
import netTest.exception.ExceptionConstant;
import netTest.paper.constant.PaperConstant;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperquestype;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.exam.form.SelfExamForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.exception.InvalideVisitException;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

public class SelfExamAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SelfExamAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("selfExamInit".equals(myaction)) {
			myforward = selfExamInit(mapping, actionform, request,response);
		} else if ("testExamInit".equals(myaction)) {
			myforward = testExamInit(mapping, actionform, request,response);
		} else if ("selfExamShift".equals(myaction)) {
			myforward = selfExamShift(mapping, actionform, request,response);
		} else if ("selfExamSubmit".equals(myaction)) {
			myforward = selfExamSubmit(mapping, actionform, request,response);
		} else if ("examResultShift".equals(myaction)) {
			myforward = examResultShift(mapping, actionform, request,response);
		} else if ("cleanUseranswer".equals(myaction)) {
			myforward = cleanUseranswer(mapping, actionform, request,response);
		} else if ("seeExamerResult".equals(myaction)) {
			myforward = seeExamerResult(mapping, actionform, request,response);
		}
		
		return myforward; 
	}
	
	/**
	 * 自我测验时的初始化, 目前只在商店管理中可以被使用
	 */
    public ActionForward selfExamInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	LoginInfoEdu loginfo = getLoginInfo();
    	Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();
		SelfExamForm theForm = (SelfExamForm) form;
		Paper paperVO = theForm.getPaperVO();
		int needRandom = PaperConstant.Ques_Random; // 暂时不乱序，no enough time, need store it in paper table later
        if(paperVO.getPaperid()==null){
        	throw new NeedParamsException();
		}
    	HttpSession session = this.getSession(request);
    	// 从缓存中得到试卷，这里所有要使用这个试卷的考生用的应该都是这个vo实例，因此该vo不能做任何改动。
    	// 这也是为什么把根据用户useranswer得到试卷中试题的工作放在页面做的原因
    	ExampaperLogic logic = BOFactory.getExampaperLogic();
    	paperVO = logic.getTestPaperAnswer(paperVO.getPaperid(), 0);
    	if(paperVO==null)
			throw new NoSuchRecordException("--class:TestExamAction;--method:selfExamInit;");
    	theForm.setPaperVO(paperVO);
    	theForm.setExamnameshow(paperVO.getPapername());
    	theForm.setPaperversion(paperVO.getVersion());
        // 得到用户的答题的题目顺序和内容,Map是题目类型questype和Answerquestype的组合
		// 根据试卷构造
		Map<Long,Answerquestype> usranwMap = logic.initTestUseranswer(null, userid, paperVO.getPaperid(), shopid, paperVO,needRandom);
		session.setAttribute(getUseranswerSessKey(null,paperVO.getPaperid()), usranwMap);
		// 在session中放置合法进入考试的标志
		session.setAttribute(KeyInMemoryConstant.ValideEnterTestKey, true);
		// 得到页面上下页要显示的题型，不包含每页要显示的题目
		List<Paperquestype> questypeList = paperVO.getQuestypeList();
		setQuestypeShow(theForm,questypeList,usranwMap);
		// 显示和设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
		doSelfExamShow(theForm,usranwMap,paperVO);
		// 设置考试试卷时间
		int remainTime = paperVO.getPapertime().intValue()*60;
		theForm.setRemainTime(remainTime);
		
    	return mapping.findForward("examPaper");
    }
    
	/**
	 * 学员刚开始考试，初始化考卷。正式考试时的初始化
	 */
    public ActionForward testExamInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
		Long userid = getLoginInfo().getUserid();
		SelfExamForm theForm = (SelfExamForm) form;
		Long testid = theForm.getTestid();
		int needRandom = PaperConstant.Ques_Random; // 暂时不乱序，no enough time, need store it in paper table later
		AssertUtil.assertNotNull(testid, null);
		TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
		TestuserDao userDao = BOFactory.getTestuserDao();
		Testinfo testVO = testLogic.getTestinfo(testid);
		AssertUtil.assertNotNull(testVO, null);
		Testuser userVO = userDao.selectByLogicPK(testid, userid);
		// 检查考试和考生是否合法
		checkAbleExam(userVO, testVO);
		Long shopid = testVO.getShopid();
		Long paperid = userVO.getPaperid();
		theForm.setTestuserid(userVO.getTestuserid());
    	HttpSession session = this.getSession(request);
    	// 从缓存中得到试卷，这里所有要使用这个试卷的考生用的应该都是这个vo实例，因此该vo不能做任何改动。
    	// 这也是为什么把根据用户useranswer得到试卷中试题的工作放在页面做的原因
    	ExampaperLogic logic = BOFactory.getExampaperLogic();
    	Paper paperVO = logic.getTestPaperAnswer(paperid, 0);
    	if(paperVO==null)
			throw new NoSuchRecordException("--class:TestExamAction;--method:selfExamInit;");
    	theForm.setPaperVO(paperVO);
    	theForm.setPaperversion(paperVO.getVersion());
    	// 设置考卷上显示的名称
    	theForm.setExamnameshow(testVO.getTestname());
        // 得到用户的答题的题目顺序和内容,Map是题目类型questype和Answerquestype的组合
		Map<Long,Answerquestype> usranwMap = logic.qryTestUseranswer(testid, userid, paperid, paperVO, needRandom);
		session.setAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()), usranwMap);
		// 在session中放置合法进入考试的标志
		session.setAttribute(KeyInMemoryConstant.ValideEnterTestKey, true);
		// 得到页面上下页要显示的题型，不包含每页要显示的题目
		List<Paperquestype> questypeList = paperVO.getQuestypeList();
		setQuestypeShow(theForm,questypeList,usranwMap);
		// 显示和设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
		doSelfExamShow(theForm,usranwMap,paperVO);
		// 设置考试试卷时间
		int remainTime = testVO.getPapertime().intValue()*60;
		// 更新考生所剩的考试时间和考试状态
//		if(TestuserConstant.Status_suspend.equals(userVO.getStatus())){
//			remainTime = userVO.getLefttime();
//		}
		
		TestuserLogic userlogic = BOFactory.getTestuserLogic();
		TestdeptDao deptDao = BOFactory.getTestdeptDao();
		remainTime = (int)userlogic.getTestLefttime(remainTime, testVO.getTestenddate());
		if(remainTime<0){
			throw new LogicException(ExceptionConstant.Error_ExamAction_TestAlreadyEnded);
		}
		if(TestuserConstant.Status_unStart.equals(userVO.getStatus())){
			// 更新单位考试人数
			Testdept deptVO = new Testdept();
			deptVO.setShopid(shopid);
			deptVO.setTestid(testid);
			deptVO.setExamingnum(1);
			deptVO.setNotexamnum(-1);
			deptDao.updateExamNumSat(deptVO);
		}
		// 更新考生的状态
		Testuser updatevo = new Testuser();
		updatevo.setTestuserid(userVO.getTestuserid());
		updatevo.setStarttime(DateUtil.getInstance().getNowtime_GLNZ());
		updatevo.setStatus(TestuserConstant.Status_examing);
		updatevo.setLefttime(remainTime);
		userDao.updateByPK(updatevo);
		theForm.setRemainTime(remainTime);
		
    	return mapping.findForward("examPaper");
    }
	
	/**
	 * 试卷上每页的翻页操作,考生考试的时候用到
	 */
    public ActionForward selfExamShift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	SelfExamForm theForm = (SelfExamForm) form;
    	Paper paperVO = theForm.getPaperVO();
    	Long testid = theForm.getTestid();
    	HttpSession session = this.getSession(request);
    	checkValideEnterTest(session);
    	Object usranwMapObj = session.getAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()));
    	
    	// 从缓存中得到试卷
    	ExampaperLogic logic = BOFactory.getExampaperLogic();
    	paperVO = logic.getTestPaperAnswer(paperVO.getPaperid(), 0);
    	// 检查是否有各种不正常的异常，包括试卷不存在，试卷失效，试卷内容已经被管理员更新等
    	ActionForward forward = this.checkIsNormal(mapping, request, paperVO, usranwMapObj, theForm.getPaperversion());
    	if(forward!=null){
	   		session.removeAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()));
	   		return forward;
	   	}
    	theForm.setPaperVO(paperVO);
    	theForm.setPaperversion(paperVO.getVersion());
    	// 得到当页正在使用的题型
    	Long questypeidUse = theForm.getQuestypeidUse();
    	Integer questypeUse = theForm.getQuestypeUse();
    	// 收集当页用户的答案，并更新用户放在内存中的答案
    	List<Useranswer> answersPage = theForm.getUsranwlist();
    	Answerquestype useransquestypeVO = getUserAnswerquestype(session,questypeidUse,testid,paperVO.getPaperid());
    	updUseranswerFromPage(useransquestypeVO,answersPage,questypeUse);
    	// 查找设置即将展示的题型等题型
        List<Paperquestype> questypeList = paperVO.getQuestypeList();
        setQuestypeShow(theForm,questypeList,(Map<Long,Answerquestype>)usranwMapObj);
        // 显示和设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
    	doSelfExamShow(theForm,(Map<Long,Answerquestype>)usranwMapObj,paperVO);
        
        return mapping.findForward("examPaper");
    }
	    
    /**
     * 提交试卷
     */
    public ActionForward selfExamSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	SelfExamForm theForm = (SelfExamForm) form;
    	Long testid = theForm.getTestid();
    	String displayname = getLoginInfo().getDisplayname();
    	Paper paperVO = theForm.getPaperVO();
    	HttpSession session = this.getSession(request);
    	checkValideEnterTest(session);
    	Object usranwMapObj = session.getAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()));
    	ExampaperLogic logic = BOFactory.getExampaperLogic();
    	TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
    	CheckPaperLogic checklogic = BOFactory.getCheckPaperLogic();
		Testinfo testVO = null;
		if(testid!=null){
		   testVO = testLogic.getTestinfo(testid);
		   if(testVO!=null) {
			   theForm.setTestviewresulttype(testVO.getViewresulttype());
		   }
		}
		// 是否需要评测试卷
		boolean needcheckpaper = false;
    	// 如果是自己测试试卷或者考试设置是允许用户立即查看答案的
    	if(testVO==null || 
    			TestinfoConstant.ViewResultType_ViewScoreInstant.equals(testVO.getViewresulttype())
    		||TestinfoConstant.ViewResultType_ViewScorePaperInstant.equals(testVO.getViewresulttype())){
    		needcheckpaper = true;
    	}
    	if(needcheckpaper){
    		// 查询的试卷vo中带有答案，type为1，个人单个试卷评测
    		paperVO = logic.getTestPaperAnswer(paperVO.getPaperid(), 1);
    	}else {
    		paperVO = logic.getTestPaperAnswer(paperVO.getPaperid(), 0);
    	}
    	
    	
	    // 检查是否有各种不正常的异常，包括试卷不存在，试卷失效，试卷内容已经被管理员更新等
	   	ActionForward forward = this.checkIsNormal(mapping, request, paperVO, usranwMapObj, theForm.getPaperversion());
	   	if(forward!=null){
	   		session.removeAttribute(getUseranswerSessKey(null,paperVO.getPaperid()));
	   		return forward;
	   	}
	   	Map<Long,Answerquestype> usranwMap = (Map<Long,Answerquestype>)usranwMapObj;
    	// 得到当页正在使用的题型
    	Integer questypeUse = theForm.getQuestypeUse();
    	Long questypeidUse = theForm.getQuestypeidUse();
        // 收集当页用户的答案，并更新用户放在内存中的答案
    	List<Useranswer> answersPage = theForm.getUsranwlist();
    	Answerquestype useransquestypeVO = getUserAnswerquestype(session,questypeidUse,testid,paperVO.getPaperid());
    	updUseranswerFromPage(useransquestypeVO,answersPage,questypeUse);
    	String rtnPage = "userendtesttip";
    	
    	Float userpaperScore = null;
    	if(needcheckpaper){ // 如果需要立即自动评测试卷
    		boolean updateDB = true;
    		if(theForm.getTestuserid()==null){
    			updateDB = false;
    		}
	    	// 计算试卷的得分情况 
    		userpaperScore = checklogic.checkUserpaper(paperVO, usranwMap, updateDB);
	    	theForm.setTotalscore(userpaperScore);
	    	if(testVO==null ||
	    		TestinfoConstant.ViewResultType_ViewScorePaperInstant.equals(testVO.getViewresulttype()))
	    	{
	    		theForm.setPaperVO(paperVO);
		    	theForm.setPaperversion(paperVO.getVersion());
		    	// 转向显示用户的答卷情况
		    	// 查找设置即将展示的题型等题型
		        List<Paperquestype> questypeList = paperVO.getQuestypeList();
		        setQuestypeShow(theForm,questypeList,usranwMap);
		        // 显示和设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
		    	doSelfExamShow(theForm,usranwMap,paperVO);
	    	}
	    	Testuser testuserVO = new Testuser();
	    	testuserVO.setDisplayname(displayname);
	    	theForm.setTestuserVO(testuserVO);
    	}
    	
    	if(theForm.getTestuserid()!=null){
    	    // 更新用户的考试信息
    		Testuser userVO = new Testuser();
    		userVO.setObjectscore(userpaperScore);
    		userVO.setTotalscore(userpaperScore);
    		userVO.setTestuserid(theForm.getTestuserid());
    		userVO.setLefttime(theForm.getRemainTime());
    		userVO.setStatus(TestuserConstant.Status_endExam);
    		userVO.setEndtime(DateUtil.getInstance().getNowtime_GLNZ());
    		TestuserDao userDao = BOFactory.getTestuserDao();
    		userDao.updateByPK(userVO);
    		// 更新单位考试人数
    		TestdeptDao deptDao = BOFactory.getTestdeptDao();
    		Testdept deptVO = new Testdept();
    		deptVO.setExamingnum(-1);
    		deptVO.setEndexamnum(1);
    		deptVO.setShopid(testVO.getShopid());
    		deptVO.setTestid(testid);
    		deptDao.updateExamNumSat(deptVO);
    	}
    	// 设置转向页面
    	if(theForm.getTestuserid()==null || 
    			TestinfoConstant.ViewResultType_ViewScorePaperInstant.equals(testVO.getViewresulttype())){
    		rtnPage = "examResultPaper";
    	}else {
    		rtnPage = "userendtesttip";
    	}
    	return mapping.findForward(rtnPage);
    }
    
    /**
     * 在考生考试结果界面，点击查看考生的考卷情况
     * voter数据检查:检查testid是否是用户的
     */
    public ActionForward seeExamerResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	SelfExamForm theForm = (SelfExamForm) form;
    	Long testid = theForm.getTestid();
    	Paper paperVO = theForm.getPaperVO();
        Long testuserid = theForm.getTestuserid();
        
        if(testid==null||paperVO.getPaperid()==null||testuserid==null)
        	throw new InvalideVisitException();
        
        Testuser userVO = BOFactory.getTestuserDao().selectByPK(testuserid);
        AssertUtil.assertNotNull(userVO, null);
        if(theForm.getTestuserVO()!=null&&theForm.getTestuserVO().getDisplayname()!=null){
        	userVO.setDisplayname(theForm.getTestuserVO().getDisplayname());
        }
        
        theForm.setUserid(userVO.getUserid());
        theForm.setTestuserVO(userVO);
        
        TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
        Testinfo testVO = testLogic.getTestinfo(testid);
        theForm.setExamnameshow(testVO.getTestname());
        
    	ExampaperLogic logic = BOFactory.getExampaperLogic();
        // 从缓存中取得试卷VO
    	paperVO = logic.getTestPaperAnswer(paperVO.getPaperid(), 0);
    	// 得到用户的答题的题目顺序和内容,Map是题目类型questype和Answerquestype的组合
    	int needRandom = 0; // 不需要乱序
		Map<Long,Answerquestype> usranwMap = logic.qryTestUseranswer(testid, userVO.getUserid(), paperVO.getPaperid(), paperVO, needRandom);
    	// 检查是否有各种不正常的异常，包括试卷不存在，试卷失效，试卷内容已经被管理员更新等
	   	ActionForward forward = this.checkIsNormal(mapping, request, paperVO, usranwMap, theForm.getPaperversion());
	   	if(forward!=null){
	   		return forward;
	   	}
   	
    	theForm.setTotalscore(userVO.getTotalscore());
    	theForm.setPaperVO(paperVO);
    	theForm.setPaperversion(paperVO.getVersion());
    	// 转向显示用户的答卷情况
    	// 查找设置即将展示的题型等题型
        List<Paperquestype> questypeList = paperVO.getQuestypeList();
        setQuestypeShow(theForm,questypeList,usranwMap);
        // 显示和设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
    	doSelfExamShow(theForm,usranwMap,paperVO);

    	return mapping.findForward("examResultPaper");
    }
    
    /**
	 * 在显示答案的试卷上的不同题型的翻页动作
	 * voter数据检查:检查testid是否是用户的
	 */
    public ActionForward examResultShift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	SelfExamForm theForm = (SelfExamForm) form;
    	Long testid = theForm.getTestid();
    	Long userid = theForm.getUserid();
    	Paper paperVO = theForm.getPaperVO();
    	HttpSession session = this.getSession(request);
    	if(userid==null){
    		userid = getLoginInfo().getUserid();
    	}
    	
    	ExampaperLogic logic = BOFactory.getExampaperLogic();
    	// 从缓存中得到试卷
    	paperVO = logic.getTestPaperAnswer(paperVO.getPaperid(), 0);
    	Map<Long,Answerquestype> usranwMap = null;
    	if(testid==null){
    		Object usranwMapObj = session.getAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()));
    		if(usranwMapObj!=null){
    			usranwMap = (Map<Long,Answerquestype>)usranwMapObj;
    		}
    	}else {
    		usranwMap = logic.qryTestUseranswer(testid, userid, paperVO.getPaperid(), paperVO, 0);
    	}
    	
    	if(usranwMap==null){
    		throw new LogicException("-- User Exam info is Empty --class:SelfExamAction;--method:examResultShift;");
    	}
    	
	    // 检查是否有各种不正常的异常，包括试卷不存在，试卷失效，试卷内容已经被管理员更新等
	   	ActionForward forward = this.checkIsNormal(mapping, request, paperVO, usranwMap, theForm.getPaperversion());
	   	if(forward!=null){
	   		session.removeAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()));
	   		return forward;
	   	}
    	theForm.setPaperVO(paperVO);
    	theForm.setPaperversion(paperVO.getVersion());
    	// 得到当页正在使用的题型
    	Long questypeidShw = theForm.getQuestypeidShw();
    	
    	// 查找设置即将展示的题型等题型
        List<Paperquestype> questypeList = paperVO.getQuestypeList();
        setQuestypeShow(theForm,questypeList,usranwMap);
        // 显示和设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
    	doSelfExamShow(theForm,usranwMap,paperVO);
        
        return mapping.findForward("examResultPaper");
    }
    
    /**
     * 当用户关闭考试自测的页面或查看完考试结果然后关闭页面触发的动作：清空用户session中用户的答案内容。
     * 这里是仅对用户自测的逻辑。如果是正式的考试测试，则不能这样处理，当非正常关闭窗口时（点击浏览器右上关闭按钮时）还需要保存用户的答案到数据表中
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cleanUseranswer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	SelfExamForm theForm = (SelfExamForm) form;
    	Long testid = theForm.getTestid();
    	Paper paperVO = theForm.getPaperVO();
    	HttpSession session = this.getSession(request);
    	session.removeAttribute(getUseranswerSessKey(testid,paperVO.getPaperid()));
    	
        request.setAttribute("pageAction", "closeWindow");
        return mapping.findForward("toUrl");
    }
    
    /**
     * 考生暂停考试, 暂不提供改功能
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
//    public ActionForward suspendTest(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//    {
//    	SelfExamForm theForm = (SelfExamForm) form;
//    	Long testid = theForm.getTestid();
//    	Long testuserid = theForm.getTestuserid();
//    	int lefttime = theForm.getRemainTime();
//    	if(testid==null||testuserid==null)
//    		throw new NeedParamsException();
//
//    	TestuserDao dao = BOFactory.getTestuserDao();
//    	Paper paperVO = theForm.getPaperVO();
//    	HttpSession session = this.getSession(request);
//    	checkValideEnterTest(session);
//    	// 得到当页正在使用的题型
//    	Integer questypeUse = theForm.getQuestypeUse();
//    	Long questypeidUse = theForm.getQuestypeidUse();
//    	// 收集当页用户的答案，并更新用户放在内存中的答案
//    	List<Useranswer> answersPage = theForm.getUsranwlist();
//    	Answerquestype useransquestypeVO = getUserAnswerquestype(session,questypeidUse,testid,paperVO.getPaperid());
//    	updUseranswerFromPage(useransquestypeVO,answersPage,questypeUse);
//    	
//    	Date nowTime = DateUtil.getInstance().getNowtime_GLNZ();
//    	Testuser userUpd = new Testuser();
//    	userUpd.setTestuserid(testuserid);
//    	userUpd.setStatus(TestuserConstant.Status_suspend);
//    	userUpd.setSuspendtesttime(nowTime);
//    	userUpd.setEndtime(nowTime);
//    	userUpd.setLefttime(lefttime);
//    	dao.updateByPK(userUpd);
//    	
//        request.setAttribute("pageAction", "closeWindow");
//        return mapping.findForward("toUrl");
//    }
    
    /**
     * 检查是否有各种不正常的异常，包括试卷不存在，试卷失效，试卷内容已经被管理员更新等
     * @param mapping
     * @param request
     * @param paperVO
     * @param usranwMapObj
     * @param paperversion
     * @return
     */
    private ActionForward checkIsNormal(ActionMapping mapping, HttpServletRequest request,Paper paperVO,Object usranwMapObj,int paperversion){
    	ActionForward forward = null;
    	Exception e = null;
    	if(paperVO==null){
			throw new NoSuchRecordException("--class:SelfExamAction;--method:checkIsNormal;");
    	}
    	if(usranwMapObj==null){
    		throw new LogicException("-- User Exam info is Empty --class:SelfExamAction;--method:checkIsNormal;");
    	}else if(((Map)usranwMapObj).isEmpty()){
    		throw new LogicException("-- User answer detail has been deleted, when this test is open test and when you've seen your test result or test has been open for 15 days");
    	}
    	// 检查考生用的试卷有没有被管理员更新。（在考生答卷期间修改试卷时，要告知考生试卷已经失效，这个只会发生在自我评测模拟试卷时，正式考试不会有这种情况）
    	if(paperversion!=paperVO.getVersion()){
    		e = new LogicException("-- paper has been updated error --class:SelfExamAction;--method:doSelfExamShow;");
    	    forward = this.forwardErrorPage(mapping, request, e, "netTestWeb.exam.action.SelfExamAction.paperUpdatedErr");
    	    return forward;
    	}
    	return forward;
    }
    
    /**
     * 设置每一页用户题目的显示内容（包括题目顺序和用户答案和当页要显示的题目类型VO）
     * @param form
     * @param session
     * @param questypeShw
     */
	private void doSelfExamShow(SelfExamForm theForm,Map<Long,Answerquestype> usranwMap,Paper paperVO) 
	{
		Long questypeidUse = theForm.getQuestypeidUse();
		// 设置当前要显示的试卷题型VO
		if(paperVO.getQuestypeList().size()>0){
			Paperquestype typeVO = null;
			for(int i=0;i<paperVO.getQuestypeList().size();i++){
				typeVO = (Paperquestype)paperVO.getQuestypeList().get(i);
				if(typeVO!=null&&questypeidUse.equals(typeVO.getQuestypeid())){
					theForm.setTypeUseVO(typeVO);
					break;
				}
			}
		}
		// 得到当前要显示的题目类型
		Answerquestype answerquestypeVO = null;
		if(usranwMap.get(questypeidUse)!=null){
			answerquestypeVO = usranwMap.get(theForm.getQuestypeidUse());
		}
		theForm.setAnswertypeVO(answerquestypeVO);
	}
	
	/**
	 * 根据在用的题目类型从用户的session中取用户的题目答案列表
	 * @param session
	 * @param questypeUse
	 * @return
	 */
    private Answerquestype getUserAnswerquestype(HttpSession session,Long questypeidUse,Long testid,Long paperid){
    	List<Useranswer> answerList = new ArrayList<Useranswer>(); 
    	Answerquestype answerquestypeVO = null;
    	if(session!=null&&questypeidUse!=null&&paperid!=null
    			&&session.getAttribute(getUseranswerSessKey(testid,paperid))!=null)
    	{
    		Object obj = session.getAttribute(getUseranswerSessKey(testid,paperid));
    		if(obj==null){
        		throw new LogicException("-- User Exam info is Empty --class:SelfExamAction;--method:selfExamShift;");
        	}
    		Map<Long,Answerquestype> usranwMap = (Map<Long,Answerquestype>)obj;
    		answerquestypeVO = usranwMap.get(questypeidUse);
    	}
    	return answerquestypeVO;
    }
    
    /**
     * 将用户页面提交的题目答案更新到用户在session中的题目中
     * @param answerSession
     * @param answersPage
     * @param questypeUse
     */
    private List<Useranswer> updUseranswerFromPage(Answerquestype useransquestypeVO,List<Useranswer> answersPage,Integer questypeUse)
    {
    	if(useransquestypeVO==null||useransquestypeVO.getAnswerList()==null||useransquestypeVO.getAnswerList().size()<1
    			||questypeUse==null)
    		return null;
    	List<Useranswer> answerSession = useransquestypeVO.getAnswerList();
    	int startnum = useransquestypeVO.getStartnum()-1;
    	int endnum = useransquestypeVO.getEndnum();
    	
		Useranswer voTemp1 = null;
		Useranswer voTemp2 = null;
		Useranswer voTemp3 = null;
		List list = new ArrayList();
		boolean skip = false;
		for(int i=0;i<answersPage.size();i++){
			skip = false;
			voTemp1 = (Useranswer)answersPage.get(i);
			if(voTemp1!=null&&voTemp1.getQuesid()!=null){ // 循环每个从页面收集的用户的答案
    			for(int j=startnum;j<endnum;j++){ // 循环在session中的答案
    				voTemp2 = answerSession.get(j);
    				if(voTemp2!=null){
    					if(voTemp1.getQuesid().equals(voTemp2.getQuesid())){
    						voTemp2.setAnswer(voTemp1.getAnswerByType(questypeUse));
    						if(voTemp2.getUseranswerid()!=null){
    							list.add(voTemp2);
    						}
    						break;
    					}else if(voTemp2.getSubanswList()!=null&&voTemp2.getSubanswList().size()>0){
    						// 如果不等于用户页面提交的题目id，则判断用户输入的是不是子题目。比较用户页面的题和子题目
    						voTemp3 = null;
    						for(int k=0;k<voTemp2.getSubanswList().size();k++){
    							voTemp3 = voTemp2.getSubanswList().get(k);
    							if(voTemp3!=null&&voTemp3.getQuesid().equals(voTemp1.getQuesid())){
    								voTemp3.setAnswer(voTemp1.getAnswerByType(questypeUse));
    								skip = true;
    								if(voTemp3.getUseranswerid()!=null){
    	    							list.add(voTemp3);
    	    						}
    								break;
    							}
    						}
    						if(skip){
    							break;
    						}
    					}
    				}
    			}
			}
		}
        // 如果有需要更新的用户答案，则更新用户的答案到数据表中
		if(list.size()>0){
			UseranswerDao answerDao = BOFactory.getUseranswerDao();
			answerDao.updBatchUseranswer(list);
		}
    	return answerSession;
    }
	
    /**
     * 查找设置即将展示的题型等题型
     * @param theForm
     */
    private void setQuestypeShow(SelfExamForm theForm,List<Paperquestype> questypeList,Map<Long,Answerquestype> usranwMap){
    	Integer questypeShw = null;
    	int pagenumShw = theForm.getPagenumShw();
    	Long questypeidUse = theForm.getQuestypeidUse();
    	Long questypeidShw = theForm.getQuestypeidShw();
        if(questypeList!=null&&questypeList.size()>0){
        	if(questypeidUse==null){ // 刚开始进入考试的情况
        		questypeidShw = questypeList.get(0).getQuestypeid();
        		questypeShw = questypeList.get(0).getQuestype();
                theForm.setQuestypeidUse(questypeidShw);
                theForm.setQuestypeUse(questypeShw);
                usranwMap.get(questypeidShw).setCurrentpage(1);
                theForm.setPagenumPrv(-3);
                if(usranwMap.get(questypeidShw).getTotalpage()>1){
                	theForm.setPagenumNxt(2);
                }else if(questypeList.size()>1){ // 题型数大于1
                	theForm.setPagenumNxt(-1);
                }else{
                	theForm.setPagenumNxt(-3);
                }
        	}else if(questypeidShw==null&&pagenumShw!=0){ // 考生点击页数或者上下页而没有直接点击题型
        		if(pagenumShw==-1){ // 向后跳到下一个题型
        			boolean flag = false; // 所匹配的是不是最后一个题型
        			for(int i=0;i<questypeList.size();i++){
        				if(questypeidUse.equals(questypeList.get(i).getQuestypeid())){
        					questypeidShw = questypeList.get(i+1).getQuestypeid(); // 逻辑上不会越界
        					questypeShw = questypeList.get(i+1).getQuestype();  // 逻辑上不会越界
        					if(i+1==questypeList.size()-1){
        						flag = true;
        					}
        					break;
        				}
        			}
        			theForm.setQuestypeidUse(questypeidShw);
                    theForm.setQuestypeUse(questypeShw);
                    usranwMap.get(questypeidShw).setCurrentpage(1);
                    theForm.setPagenumPrv(-2);
                    if(usranwMap.get(questypeidShw).getTotalpage()>1){
                    	theForm.setPagenumNxt(2);
                    }else if(flag){
                    	theForm.setPagenumNxt(-3);
                    }else{
                    	theForm.setPagenumNxt(-1);
                    }
        		}else if(pagenumShw==-2){
        			boolean flag = false; // 所匹配的是不是第一个题型
        			for(int i=0;i<questypeList.size();i++){
        				if(questypeidUse.equals(questypeList.get(i).getQuestypeid())){
        					questypeidShw = questypeList.get(i-1).getQuestypeid();
        					questypeShw = questypeList.get(i-1).getQuestype();
        					if(i-1==0){
        						flag = true;
        					}
        					break;
        				}
        			}
        			theForm.setQuestypeidUse(questypeidShw);
                    theForm.setQuestypeUse(questypeShw);
                    int totalpage = usranwMap.get(questypeidShw).getTotalpage();
                    usranwMap.get(questypeidShw).setCurrentpage(totalpage);
                    theForm.setPagenumNxt(-1);
                    if(totalpage>1){
                    	theForm.setPagenumPrv(totalpage-1);
                    }else if(flag){
                    	theForm.setPagenumPrv(-3);
                    }else{
                    	theForm.setPagenumPrv(-2);
                    }
        		}else if(pagenumShw==1){ // 如果查看的页面是本题型的第一页
        			if(questypeidUse.equals(questypeList.get(0).getQuestypeid())){
        				theForm.setPagenumNxt(pagenumShw+1);
        				theForm.setPagenumPrv(-3);
        			}else{
        				theForm.setPagenumNxt(pagenumShw+1);
        				theForm.setPagenumPrv(-2);
        			}
        			usranwMap.get(questypeidUse).setCurrentpage(pagenumShw);
        		}else if(pagenumShw==usranwMap.get(questypeidUse).getTotalpage()){ // 如果查看页面是本页题型的最后一页
        			if(questypeidUse.equals(questypeList.get(questypeList.size()-1).getQuestypeid())){
        				theForm.setPagenumNxt(-3);
        				theForm.setPagenumPrv(pagenumShw-1);
        			}else{
        				theForm.setPagenumNxt(-1);
        				theForm.setPagenumPrv(pagenumShw-1);
        			}
        			usranwMap.get(questypeidUse).setCurrentpage(pagenumShw);
        		}else{
        			theForm.setPagenumNxt(pagenumShw+1);
    				theForm.setPagenumPrv(pagenumShw-1);
    				usranwMap.get(questypeidUse).setCurrentpage(pagenumShw);
        		}
        		
        	}else if(questypeidShw!=null){ // 考生点击某种题型
        		usranwMap.get(questypeidShw).setCurrentpage(1);
        		questypeShw = usranwMap.get(questypeidShw).getQuestype();
        		if(questypeidShw.equals(questypeList.get(0).getQuestypeid())){
        			theForm.setPagenumPrv(-3);
        		}else{
        			theForm.setPagenumPrv(-2);
        		}
        		if(usranwMap.get(questypeidShw).getTotalpage()>1){
        			theForm.setPagenumNxt(2);
        		}else if(questypeidShw.equals(questypeList.get(questypeList.size()-1).getQuestypeid())){
        			theForm.setPagenumNxt(-3);
        		}else{
        			theForm.setPagenumNxt(-1);
        		}
        		theForm.setQuestypeidUse(questypeidShw);
                theForm.setQuestypeUse(questypeShw);
        	}
        }
    }
    
    /**
     *  组成用户试卷答案放在sessioin中的key值
     * @param testid
     * @param paperid
     * @return
     */
    private String getUseranswerSessKey(Long testid,Long paperid){
    	String key = "";
    	if(testid!=null){
    		key = testid.toString()+KeyInMemoryConstant.TestUseranswerKey+paperid.toString();
    	}else{
    		key = KeyInMemoryConstant.TestUseranswerKey+paperid.toString();
    	}
    	return key;
    }
    
    /**
     * 检查考生是否是合法的进入考试的
     * @param session
     */
    private void checkValideEnterTest(HttpSession session){
    	if(session!=null&&session.getAttribute(KeyInMemoryConstant.ValideEnterTestKey)!=null
    	   &&((Boolean)session.getAttribute(KeyInMemoryConstant.ValideEnterTestKey)))
    		return;
    	else
    		throw new LogicException(ExceptionConstant.Error_SelfExamAction_InvalideEnterTest);
    }
    
    /**
     * 检查考生是否是合法的进入考试的
     * @param session
     */
    private void checkAbleExam(Testuser userVO, Testinfo testVO){
    	if(testVO==null)
     	   throw new LogicException(ExceptionConstant.Error_Testinfo_notExist);
    	if(testVO.getTeststatus().shortValue()<TestinfoConstant.Teststatus_started.shortValue())
      	   throw new LogicException(ExceptionConstant.Error_ExamAction_TestNotStart);
    	
    	Date nowdate = DateUtil.getInstance().getNowtime_GLNZ();
    	if(testVO.getTeststatus().shortValue()>TestinfoConstant.Teststatus_started.shortValue()
    			|| testVO.getTestenddate().compareTo(nowdate)<=0)
       	   throw new LogicException(ExceptionConstant.Error_ExamAction_TestAlreadyEnded);
    	
    	if(userVO==null)
    	   throw new LogicException(ExceptionConstant.Error_SelfExamAction_notInTest);
    	if(userVO.getPaperid()==null||
 			   (!TestuserConstant.Status_unStart.equals(userVO.getStatus())))
 			   //&&!TestuserConstant.Status_suspend.equals(userVO.getStatus())))
 				throw new LogicException(ExceptionConstant.Error_SelfExamAction_InvalideEnterTest);
    }
    
}
