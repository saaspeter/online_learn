package netTestWeb.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.product.dao.OpenactivitymemberDao;
import netTest.product.dto.OpenactivitymemberQuery;
import netTest.product.logic.OpenactivityLogic;
import netTest.product.vo.Openactivity;
import netTest.product.vo.Openactivitymember;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.product.form.OpenactivitymemberForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.BOFactory_Platform;
import platform.vo.User;
import platform.vo.Usercontactinfo;
import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;

/**
 */
public class OpenactivitymemberAction extends BaseAction {
	
	static Logger log = Logger.getLogger(OpenactivitymemberAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listOpenactivitymember".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("listMyOpenactivity".equals(myaction)) {
			myforward = listmyactivity(mapping, actionform, request,response);
		} else if ("joinOpenactivity".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editOpenactivitymember".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("toJoinActivityPage".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("userleaveactivity".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) form;
		OpenactivitymemberQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getActivityid(), null);
		OpenactivitymemberDao dao = BOFactory.getOpenactivitymemberDao();
		Page page = dao.selectByVOPage(queryVO.getActivityid(), queryVO.getUserid(), queryVO.getJoinstatus(),
				                getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		// 设置是否是管理员权限
		Long userId = getLoginInfo(true).getUserid();
		Long creatorId = queryVO.getCreatorid();
		if(creatorId!=null && creatorId.equals(userId)){
			theForm.setCanadmin(true);
		}
		return mapping.findForward("list");
	}
	
	// list my joined activity
	public ActionForward listmyactivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) form;
		OpenactivitymemberQuery queryVO = theForm.getQueryVO();
		Long userid = getLoginInfo().getUserid();
		queryVO.setUserid(userid);
		
		OpenactivityLogic logic = BOFactory.getOpenactivityLogic();
		Page page = logic.queryActivityByUser(userid, getCurrPageNumber(request), 
				                   getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.saveSuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		Long loginUserid = getLoginInfo().getUserid();
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) form;
		Openactivitymember vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getActivityid(), null);
        if(vo.getUserid()==null){ // new add
        	vo.setUserid(loginUserid);
        }else { // edit member
        	// 
        	Openactivity activityVO = BOFactory.getOpenactivityDao().selectByPK(vo.getActivityid());
        	if(!ableEditActivityMember(loginUserid, vo.getUserid(), activityVO.getCreatorid())){
        		throw new LogicException(ExceptionConstant.Error_Unauthorized);
        	}
        }
        OpenactivityLogic logic = BOFactory.getOpenactivityLogic();
        logic.saveMember(vo);
		// set messag page parameters.
        request.setAttribute("pageAction", "closeDiv");
		return mapping.findForward("toUrl");
	}
	
	// 是自己或活动创办人都可以修改
	private boolean ableEditActivityMember(Long loginUserId, Long memberUserId, Long activityCreator){
		if(loginUserId.equals(memberUserId) || loginUserId.equals(activityCreator)){
			return true;
		}else {
			return false;
		}
	}
	
	/** 
	 * Method edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) form;
		Openactivitymember paramVO = theForm.getVo();
		AssertUtil.assertNotNull(paramVO.getActivityid(), null);
		Long userid = paramVO.getUserid();
		if(userid==null){
			userid = getLoginInfo().getUserid();
		}
		
		OpenactivitymemberDao dao = BOFactory.getOpenactivitymemberDao();
		Openactivitymember vo = dao.selectByPK(paramVO.getActivityid(), userid);
		AssertUtil.assertNotNull(vo, null);
		Openactivity activityVO = BOFactory.getOpenactivityDao().selectByPKSimple(vo.getActivityid());
		vo.setActivityvo(activityVO);
		theForm.setVo(vo);

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) form;
		Openactivitymember membervo = theForm.getVo();
		AssertUtil.assertNotNull(membervo.getActivityid(), null);
		theForm.setEditType(WebConstant.editType_add);
		LoginInfoEdu loginfo = getLoginInfo();
		User user = BOFactory_Platform.getUserDao().selectByPK(loginfo.getUserid());
		membervo.setDisplayname(user.getDisplayname());
		Usercontactinfo infovo = BOFactory_Platform.getUsercontactinfoDao().selectByPK(loginfo.getUserid());
		if(infovo!=null){
			membervo.setPhone(infovo.getTelephone());
		}
		//
		Openactivity activityvo = BOFactory.getOpenactivityDao().selectByPKSimple(membervo.getActivityid());
		membervo.setActivityvo(activityvo);
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OpenactivitymemberForm theForm = (OpenactivitymemberForm) form;
		Long activityid = theForm.getQueryVO().getActivityid();
		// 只能自己删除自己的活动
		Long userid = getLoginInfo().getUserid();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String messKey = "";
		try {
			OpenactivityLogic logic = BOFactory.getOpenactivityLogic();
			logic.leaveActivity(activityid, new Long[]{userid}, false);
			messKey = KeyInMemoryConstant.deleteSuccess;
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof LogicException){
				messKey = e.getMessage();
			}else {
				messKey = ExceptionConstant.Error_System;
			}
		}
		String message = getResources(request).getMessage(messKey, rows);
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
}
