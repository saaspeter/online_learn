package netTestWeb.exercise.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netTest.bean.BOFactory;
import netTest.common.logic.QuesTypeHandle;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.constant.UseranswerConstant;
import netTest.exception.ExceptionConstant;
import netTest.exercise.dao.ExeruserDao;
import netTest.exercise.dao.UserexeranswerDao;
import netTest.exercise.logic.ExerciseLogic;
import netTest.exercise.logic.ExeruserLogic;
import netTest.exercise.logic.JudgeExerUseranswerHandle;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exerquestype;
import netTest.exercise.vo.Exeruser;
import netTest.exercise.vo.Userexeranswer;
import netTest.prodcont.vo.Contentcate;
import netTest.product.vo.Learnactivity;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.exercise.form.ExerExamForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.event.Event;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

public class ExerExamAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ExerExamAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ExerExamForm theForm = (ExerExamForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("beforeDoExercise".equals(myaction)) {
			myforward = beforeDoExercise(mapping, actionform, request,response);
		} else if ("preEnterExercise".equals(myaction)) {
			theForm.setTestway(1);
			myforward = enterExercise(mapping, actionform, request,response);
		} else if ("enterExercise".equals(myaction)) {
			theForm.setTestway(2);
			myforward = enterExercise(mapping, actionform, request,response);
		} else if ("preExamQuesShift".equals(myaction)) {
			theForm.setTestway(1);
			myforward = examQuesShift(mapping, actionform, request,response);
		} else if ("examQuesShift".equals(myaction)) {
			theForm.setTestway(2);
			myforward = examQuesShift(mapping, actionform, request,response);
		} else if ("preCheckQues".equals(myaction)) {
			theForm.setTestway(1);
			myforward = checkQues(mapping, actionform, request,response);
		} else if ("checkQues".equals(myaction)) {
			theForm.setTestway(2);
			myforward = checkQues(mapping, actionform, request,response);
		} else if ("submitExercise".equals(myaction)) {
			myforward = submitExercise(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		
		return myforward; 
	}
	
	/**
	 * 学员刚开始考试，初始化考卷，自我测验时的初始化
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward beforeDoExercise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	Long userid = getLoginInfo().getUserid();
		ExerExamForm theForm = (ExerExamForm) form;
		Long exerid = theForm.getExerid();
		if(exerid==null){
        	throw new NeedParamsException();
		}
    	// 从缓存中得到试卷，这里所有要使用这个试卷的考生用的应该都是这个vo实例，因此该vo不能做任何改动。
    	// 这也是为什么把根据用户useranswer得到试卷中试题的工作放在页面做的原因
    	ExerciseLogic logic = BOFactory.getExerciseLogic();
    	ExeruserLogic exeruserlogic = BOFactory.getExeruserLogic();
    	Exercise exerVO = logic.qryAllExerQues(exerid);
    	AssertUtil.assertNotNull(exerVO, null);
    	List<Exerques> quesList = exerVO.getQuesList();
    	if(quesList==null||quesList.size()<1){
    		theForm.setHasques(0);
    	}
    	// 设置章节内容名称
    	if(exerVO.getContentcateid()!=null){
    		Contentcate contvo = BOFactory.getContentcateDao().selectByPK(exerVO.getContentcateid());
    		exerVO.setContentcatename(contvo.getContentcatename());
    	}
    	
    	// 得到用户的答题的题目顺序和内容,Map是题目类型questype和Answerquestype的组合
		// 根据试卷构造
		Exeruser exeruserVO = exeruserlogic.selectByExer(exerid, userid);
		// 如果用户没有做过该练习，则插入exeruser数据
		if(exeruserVO==null){
			// 插入练习user 记录
			ExeruserDao exeruserDao = BOFactory.getExeruserDao();
			exeruserVO = new Exeruser();
			exeruserVO.setUserid(userid);
			exeruserVO.setExerid(exerid);
			exeruserVO.setShopid(exerVO.getShopid());
			exeruserVO.setStatus(TestuserConstant.Status_unStart);
			Long pk = exeruserDao.insert(exeruserVO);
			exeruserVO.setExeruserid(pk);
            // 初始化用户的练习答案，并插入数据库
			exeruserlogic.initUserAnswer(userid, quesList, true);
		}
		theForm.setExeruserVO(exeruserVO);
		
    	exerVO.setQuesList(null); // 该页面不需要quesList
    	theForm.setExerVO(exerVO);
    	
    	// 每次做练习时都会更新用户 learn activity
    	Map paraMap = new HashMap();
		Learnactivity activityvo = new Learnactivity();
		activityvo.setUserid(userid);
		activityvo.setActiontype(Learnactivity.Actiontype_Learn);
		activityvo.setContentcateid(exerVO.getContentcateid());
		activityvo.setEndtime(null);
		activityvo.setObjectid(exerVO.getExerid());
		activityvo.setObjecttype(Exercise.ObjectType);
		activityvo.setProductid(exerVO.getProductbaseid());
		activityvo.setShopid(exerVO.getShopid());
		activityvo.setStarttime(DateUtil.getInstance().getNowtime_GLNZ());
	    Event event = new Event(EventHandlerNetTest.EventType_Product_LearnActivity, paraMap);
		EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
    
    	return mapping.findForward("viewPage");
    }
	
	/**
	 * 学员刚开始考试，初始化考卷，自我测验时的初始化
	 */
    public ActionForward enterExercise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	Long userid = getLoginInfo().getUserid();
		ExerExamForm theForm = (ExerExamForm) form;
		int testway = theForm.getTestway();
		Long exerid = theForm.getExerid();
		int quesIndex = theForm.getQuesIndex();
		if(exerid==null){
        	throw new NeedParamsException();
		}
    	HttpSession session = this.getSession(request);
    	// 从缓存中得到试卷，这里所有要使用这个试卷的考生用的应该都是这个vo实例，因此该vo不能做任何改动。
    	// 这也是为什么把根据用户useranswer得到试卷中试题的工作放在页面做的原因
    	ExerciseLogic logic = BOFactory.getExerciseLogic();
    	ExeruserLogic exeruserlogic = BOFactory.getExeruserLogic();
    	Exercise exerVO = logic.qryAllExerQues(exerid);
    	AssertUtil.assertNotNull(exerVO, null);
    	theForm.setExername(exerVO.getExername());
    	List<Exerques> quesList = exerVO.getQuesList();
    	if(quesList==null||quesList.size()<1){
    		throw new LogicException(ExceptionConstant.Error_Exercise_NoQues);
    	}
    	if(quesIndex>=quesList.size()){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
    	Exerques quesVO = quesList.get(quesIndex);
    	theForm.setQuesVO(quesVO);
    	theForm.setQuesnum(quesList.size());
    	// 设置题型
    	Exerquestype questypeVO = getExerquestypeShow(exerVO.getQuestypeList(), quesVO.getQuestype());
    	theForm.setTypeUseVO(questypeVO);
        // 得到用户的答题的题目顺序和内容,Map是题目类型questype和Answerquestype的组合
		// 根据试卷构造
    	Map<Long, Userexeranswer> usranwMap = null;
    	// 如果是管理员测试，则根据题目构建用户答案
    	if(testway==1){
    		usranwMap = exeruserlogic.initUserAnswer(userid, quesList, false);
    	}else {
    		// 更新用户状态
    		Exeruser exeruserVO = exeruserlogic.selectByExer(exerid, userid);
    		if(exeruserVO==null||TestuserConstant.Status_unStart.equals(exeruserVO.getStatus())){
	    		exeruserVO = new Exeruser();
				exeruserVO.setStarttime(DateUtil.getInstance().getNowtime_GLNZ());
				exeruserVO.setStatus(TestuserConstant.Status_examing);
				exeruserVO.setProductbaseid(exerVO.getProductbaseid());
				exeruserVO.setShopid(exerVO.getShopid());
				exeruserVO.setContentcateid(exerVO.getContentcateid());
				
				exeruserlogic.updateUserExer(exeruserVO, userid, exerid);
    		}
    		usranwMap = exeruserlogic.qryUserExerAnswer(exerVO.getShopid(), userid, exerid, quesList);
    		theForm.setExeruserStatus(exeruserVO.getStatus());
    	}
		
		session.setAttribute(getUseranswerSessKey(exerVO.getExerid(),userid), usranwMap);
		Userexeranswer answerVO = usranwMap.get(quesVO.getQuesid());
		theForm.setUseranswerVO(answerVO);
				
    	return mapping.findForward("examPage");
    }
    
	
	/**
	 * 试卷上每页的翻页操作,考生考试的时候用到
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward examQuesShift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	ExerExamForm theForm = (ExerExamForm) form;
    	int testway = theForm.getTestway();
    	int quesindex = theForm.getQuesIndex();  // 现在页面的题目序号
    	int direction = theForm.getDirection();  // 下一题还是上一题
    	Long exerid = theForm.getExerid();
    	Userexeranswer useranswerVO = theForm.getUseranswerVO();
    	Long userid = getLoginInfo().getUserid();
    	HttpSession session = this.getSession(request);
        // 收集当页用户的答案，并更新用户放在内存中的答案
    	boolean updateDB = true;
    	if(testway==1){
    		updateDB = false;
    	}
    	Userexeranswer sessUserAnswerVO = getUserAnswerFromSession(session,exerid,userid,useranswerVO.getQuesid(),useranswerVO.getPid());
    	if(!UseranswerConstant.ExamineStatus_DidJudge.equals(useranswerVO.getExaminestatus())){
    	    updateUserAnswer(useranswerVO,sessUserAnswerVO,updateDB,false);
    	}
    	
    	// 从缓存中得到练习，得到要显示的题目
    	ExerciseLogic logic = BOFactory.getExerciseLogic();
    	Exercise exerVO = logic.qryAllExerQues(exerid);
    	List<Exerques> quesList = exerVO.getQuesList();
    	quesindex += direction;
    	if(quesindex>=quesList.size() || quesindex<0){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
    	theForm.setQuesIndex(quesindex);
    	Exerques quesVO = exerVO.getQuesList().get(quesindex);
    	theForm.setQuesVO(quesVO);
    	sessUserAnswerVO = getUserAnswerFromSession(session,exerid,userid,quesVO.getQuesid(),quesVO.getPid());
    	theForm.setUseranswerVO(sessUserAnswerVO);
        // 设置题型
    	Exerquestype questypeVO = getExerquestypeShow(exerVO.getQuestypeList(), quesVO.getQuestype());
    	theForm.setTypeUseVO(questypeVO);
    	// TODO 如果是最后一题，要set练习用户的状态，以方便页面显示练习是否允许再次提交
    	
        return mapping.findForward("examPage");
    }
	    
    /**
     * 提交单个练习题的答案
     * 需要检查用户该题目是否正确，并更新到session答案中和db中
     * @return
     */
    public ActionForward checkQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	ExerExamForm theForm = (ExerExamForm) form;
    	int testway = theForm.getTestway();
    	int quesindex = theForm.getQuesIndex();  // 现在页面的题目序号
    	Long exerid = theForm.getExerid();
    	Userexeranswer useranswerVO = theForm.getUseranswerVO();
    	Long userid = getLoginInfo().getUserid();
    	HttpSession session = this.getSession(request);
    	
    	// 从缓存中得到练习
    	ExerciseLogic logic = BOFactory.getExerciseLogic();
    	Exercise exerVO = logic.qryAllExerQues(exerid);
    	List<Exerques> quesList = exerVO.getQuesList();
    	if(quesindex>=quesList.size() || quesindex<0){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
    	Exerques quesVO = exerVO.getQuesList().get(quesindex);
    	theForm.setQuesVO(quesVO);
    	QuesTypeHandle handle = new JudgeExerUseranswerHandle();
        // 收集当页用户的答案，并更新用户放在内存中的答案,然后check题目
    	boolean updateDB = true;
    	if(testway==1){
    		updateDB = false;
    	}
    	Userexeranswer sessUserAnswerVO = getUserAnswerFromSession(session,exerid,userid,useranswerVO.getQuesid(),useranswerVO.getPid());
    	handle.handleQuesType(quesVO, useranswerVO, quesVO.getQuestype());
    	updateUserAnswer(useranswerVO, sessUserAnswerVO, updateDB,true);
    	
    	theForm.setUseranswerVO(sessUserAnswerVO);
    	
        // 设置题型
    	Exerquestype questypeVO = getExerquestypeShow(exerVO.getQuestypeList(), quesVO.getQuestype());
    	theForm.setTypeUseVO(questypeVO);
    	
//    	// 如果是单个独立问题
//    	if(quesVO.getSubquesList()==null||quesVO.getSubquesList().size()<1){
//    		this.writeAjaxRtn(sessUserAnswerVO.getIsright(), quesVO.getAnswer(), 
//    				quesVO.getAnswerVO().getSolvedesc(), response);
//    	}else { // 如果是复合题目
//    		List<Question> subquesList = quesVO.getSubquesList();
//    		List<Userexeranswer> subanswerList = sessUserAnswerVO.getSubanswList();
//    		String[][] arr = new String[subquesList.size()][3];
//    		Question votemp = null;
//    		for(int i=0;i<subquesList.size();i++){
//    			votemp = subquesList.get(i);
//    			arr[i][0] = subanswerList.get(i).getIsright();
//    			arr[i][1] = votemp.getAnswer();
//    			arr[i][1] = votemp.getAnswerVO().getSolvedesc();
//    		}
//    		this.writeAjaxRtn(arr, response);
//    	}
    	
    	return mapping.findForward("examPage");
    }
    
    /**
     * 用户提交练习，用于最后练习做完时的操作
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward submitExercise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    {
    	ExerExamForm theForm = (ExerExamForm) form;
    	int quesindex = theForm.getQuesIndex();  // 现在页面的题目序号
    	Long exerid = theForm.getExerid();
    	Userexeranswer useranswerVO = theForm.getUseranswerVO();
    	Long userid = getLoginInfo().getUserid();
    	HttpSession session = this.getSession(request);
    	//TODO 如果exeruser的状态已经是完成状态，则不能再次提交
    	
    	// 从缓存中得到练习
    	ExerciseLogic logic = BOFactory.getExerciseLogic();
    	ExeruserLogic exeruserLogic = BOFactory.getExeruserLogic();
    	Exercise exerVO = logic.qryAllExerQues(exerid);
    	List<Exerques> quesList = exerVO.getQuesList();
    	if(quesindex>=quesList.size() || quesindex<0){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
    	
        // 收集当页用户的答案，并更新用户放在内存中的答案
    	Userexeranswer sessUserAnswerVO = getUserAnswerFromSession(session,exerid,userid,useranswerVO.getQuesid(),useranswerVO.getPid());
    	updateUserAnswer(useranswerVO,sessUserAnswerVO,true,false);
    	// 检查用户的答案是否都评阅过了，如果还有没有评阅的试题，则评阅试题，并更新用户的答案表
    	String sessKey = getUseranswerSessKey(exerid,userid);
    	if(session.getAttribute(sessKey)!=null)
    	{
    		Object obj = session.getAttribute(sessKey);
    		Map<Long, Userexeranswer> usranwMap = (Map<Long, Userexeranswer>)obj;
    		exeruserLogic.checkAllExerques(quesList, usranwMap);
    	}
    	// 更新用户练习的状态
    	Exeruser exeruserVO = new Exeruser();
		exeruserVO.setEndtime(DateUtil.getInstance().getNowtime_GLNZ());
		exeruserVO.setStatus(TestuserConstant.Status_endExam);
		exeruserVO.setProductbaseid(exerVO.getProductbaseid());
		exeruserVO.setShopid(exerVO.getShopid());
		exeruserVO.setContentcateid(exerVO.getContentcateid());
		
		exeruserLogic.updateUserExer(exeruserVO, userid, exerid);
		// 清除session
		session.removeAttribute(sessKey);
    	
        return mapping.findForward("resultPage");
    }
        
	
    /**
     * 得到要显示的题目类型
     */
    private Exerquestype getExerquestypeShow(List<Exerquestype> questypeList, Integer questype){
    	Exerquestype typeVORtn = null;
    	for(Exerquestype typeVO : questypeList){
    		if(typeVO.getQuestype().equals(questype)){
    			typeVORtn = typeVO;
    			break;
    		}
    	}
    	return typeVORtn;
    }
    
	/**
	 * 根据在用的题目类型从用户的session中取用户的题目答案列表
	 * @param session
	 * @param questypeUse
	 * @return
	 */
    private Userexeranswer getUserAnswerFromSession(HttpSession session, Long exerid,
    		 Long userid, Long quesid, Long pid)
    {
    	Userexeranswer answerVO = null;
    	String sessKey = getUseranswerSessKey(exerid,userid);
    	if(session!=null&&session.getAttribute(sessKey)!=null)
    	{
    		Object obj = session.getAttribute(sessKey);
    		if(obj==null){
        		throw new LogicException("-- User Exam info is Empty --class:ExerExamAction;--method:getUserAnswerFromSession");
        	}
    		Map<Long, Userexeranswer> usranwMap = (Map<Long, Userexeranswer>)obj;
    		if(pid==null){
    			answerVO = usranwMap.get(quesid);
    		}else { // 如果有pid说明该题目是子题目，例如阅读理解的子题目
    		    answerVO = usranwMap.get(pid);
    		    List<Userexeranswer> sublist = answerVO.getSubanswList();
    		    for(Userexeranswer subVO : sublist){
    		    	if(quesid.equals(subVO.getQuesid())){
    		    		answerVO = subVO;
    		    		break;
    		    	}
    		    }
    		}
    		
    	}
    	return answerVO;
    }
    
    /**
     * 将用户页面提交的题目答案更新到用户在session中的题目中
     * @param answerSession
     * @param 
     * @param isCheckques: 只有当判断问题后才可以更新用户答案的正确性和状态
     */
    private Userexeranswer updateUserAnswer(Userexeranswer useransVO, Userexeranswer sessUseranswVO, 
    		boolean updateDB, boolean isCheckques)
    {
    	if(useransVO==null||sessUseranswVO==null)
    		return null;
    	if(!UseranswerConstant.ExamineStatus_DidJudge.equals(sessUseranswVO.getExaminestatus()))
    	{
    		String userAnswer = useransVO.getAnswer();
    		if(userAnswer==null||userAnswer.trim().length()<1){
    			userAnswer = useransVO.getAnswerByType(sessUseranswVO.getQuestype());
    		}
        	UserexeranswerDao answerDao = BOFactory.getUserexeranswerDao();
    		sessUseranswVO.setAnswer(userAnswer);
    		if(isCheckques){
    		   sessUseranswVO.setIsright(useransVO.getIsright());
    		   sessUseranswVO.setExaminestatus(useransVO.getExaminestatus());
    		}
    		// 更新用户答案db
    		if(updateDB){
    			if(sessUseranswVO.getUseranswerid()!=null){
     			   answerDao.updUseranswerPK(sessUseranswVO);
         		}else {
         			Long pk = answerDao.insert(sessUseranswVO);
         			sessUseranswVO.setUseranswerid(pk);
         		}
    		}
        	
        	// 处理子题目
        	List<Userexeranswer> subanswerList = useransVO.getSubanswList();
        	if(subanswerList!=null && subanswerList.size()>0){
        		Userexeranswer subanswerVO = null;
        		Userexeranswer sessSubanswerVO = null;
        		List<Userexeranswer> updList = new ArrayList<Userexeranswer>();
        		for(int i=0;i<useransVO.getSubanswList().size();i++){
        			subanswerVO = subanswerList.get(i);
        			sessSubanswerVO = sessUseranswVO.getSubanswList().get(i);
        			userAnswer = subanswerVO.getAnswer();
        			if(userAnswer==null||userAnswer.trim().length()<1){
        			   userAnswer = subanswerVO.getAnswerByType(sessSubanswerVO.getQuestype());
        			}
        			sessSubanswerVO.setAnswer(userAnswer);
        			if(isCheckques){
        			   sessSubanswerVO.setIsright(subanswerVO.getIsright());
        			   sessSubanswerVO.setExaminestatus(subanswerVO.getExaminestatus());
        			}
        			// 更新db, 这里需要做改进，因为如果有多个
        			if(updateDB){
            			if(sessSubanswerVO.getUseranswerid()!=null){
            				updList.add(sessSubanswerVO);
             			   answerDao.updUseranswerPK(sessSubanswerVO);
                 		}
            			// 暂时应该不会执行到这里，因为用户在进入练习detail大厅时应该已经把用户的answer插入db了
            			// 此处是针对，后来练习有新增题目的保护，如果新增了练习题，则插入用户答案
            			else {
                 			Long pk = answerDao.insert(sessSubanswerVO);
                 			sessSubanswerVO.setUseranswerid(pk);
                 		}
            			answerDao.batchUpdUseranswerPK(updList);
            		}
        		}
        	}
    	}
		
    	return sessUseranswVO;
    }
	
    
    /**
     *  组成用户试卷答案放在sessioin中的key值
     * @param testid
     * @param paperid
     * @return
     */
    private String getUseranswerSessKey(Long exerid, Long userid){
    	String key = KeyInMemoryConstant.ExerUseranswerKey+"~"+exerid.toString()+"~"+userid;
    	return key;
    }
    
    
}
