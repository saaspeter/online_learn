
package platformWeb.user.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import platform.dao.CustinfoexDao;
import platform.dao.UserDao;
import platform.dao.UsercontactinfoDao;
import platform.dto.UserQuery;
import platform.logic.UserLogic;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Custinfoex;
import platform.vo.User;
import platform.vo.Usercontactinfo;
import platformWeb.base.BaseAction;
import platformWeb.base.KeyInMemoryConstant;
import platformWeb.base.WebConstant;
import platformWeb.user.form.UserForm;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.DuplicateException;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;


public class UserAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		UserForm theForm = (UserForm) actionform;
		
		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listuser".equals(myaction)) {
			myforward = list(mapping, theForm, request, response);
		} else if ("registeruser".equals(myaction)) {
			myforward = registerUser(mapping, actionform, request,response);
		} else if ("saveuser".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("edituser".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editBasicInfo(mapping, actionform, request,response);
		} else if ("viewuser".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editBasicInfo(mapping, actionform, request,response);
		} else if ("adduser".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteuser".equals(myaction)) {
			myforward = deleteUser(mapping, actionform, request,response);
		} else if ("updatepassword".equals(myaction)) {
			myforward = updatePassword(mapping, actionform, request,response);
		} else { 
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserForm theForm = (UserForm) form;
		Locale locale = getLoginInfo().getUseLocale();
		UserQuery query = theForm.getQuery();
	    // 设置客户状态列表
		theForm.setStatusList(CustomerConstant.getCustomerStatusLabels(locale));
		UserLogic logic = BOFactory_Platform.getUserLogic();
		Page page = logic.qryUserPage(query, getCurrPageNumber(request), getPageSize());
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	/** 更新用户信息 **/
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 校验
		UserForm theForm = (UserForm) form;
		User user = theForm.getVo();
		Usercontactinfo contactinfo = theForm.getContactinfo();
		Custinfoex custinfoex = theForm.getCustinfoex();
		user.setContactinfo(contactinfo);
		user.setCustinfoex(custinfoex);
		
		String result = String.valueOf(CommonConstant.success);
		String messcode = KeyInMemoryConstant.modifySuccess;
		try{
       	   UserLogic logic = BOFactory_Platform.getUserLogic();
		   logic.updateUser(user);
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			messcode = e.getMessage();
		}
		
		String info = getResources(request, "BizKey").getMessage(messcode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * 
	 */
	private ActionForward registerUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 校验
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		UserForm theForm = (UserForm) form;
		User user = theForm.getVo();
		Usercontactinfo contactinfo = theForm.getContactinfo();
		Custinfoex custinfoex = theForm.getCustinfoex();
		user.setContactinfo(contactinfo);
		user.setCustinfoex(custinfoex);
		
        try {
        	UserLogic logic = BOFactory_Platform.getUserLogic();
 		    logic.insertUser(user);
		} catch (DuplicateException ex) {
			request.setAttribute("bundle", "BizKey");
			request.setAttribute(KeyInMemoryConstant.DisMessKey, "commonWeb.logic.duplicateUser");
			return this.addPage(mapping, theForm, request, response);
		} 
		// set messag page parameters.
		String messCode = KeyInMemoryConstant.AddSuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("registerCompletePage");
	}
	
	/** 
	 * Method editBasicInfo:暂时包括用户基本信息、用户联系方式、用户扩展信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward editBasicInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserForm theForm = (UserForm) form;
		User vo = theForm.getVo();
		Long pk = vo.getUserid();
		if(pk!=null){
		   //TODO 检查是否可以编辑该用户
		} else{
		   pk = getLoginInfo().getUserid();
		}
		UserDao userdao = BOFactory_Platform.getUserDao();
		UsercontactinfoDao contactDao = BOFactory_Platform.getUsercontactinfoDao();
		CustinfoexDao custinfoexDao = BOFactory_Platform.getCustinfoexDao();
		vo = userdao.selectByPK(pk);
		if(vo==null){
			log.info("----error:in Class:UserAction Method:editPage::pk:"+pk+":no such record!");
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
		}
		Usercontactinfo contactinfo = contactDao.selectByUserid(pk);
		Custinfoex custinfoex = custinfoexDao.selectByPK(pk);
		theForm.setVo(vo);
		if(contactinfo!=null)
		   theForm.setContactinfo(contactinfo);
		if(custinfoex!=null)
		   theForm.setCustinfoex(custinfoex);
		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("editPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		Long localeid = getLoginInfo(true).getUseLocaleid();
		UserForm theForm = (UserForm) form;
		theForm.getVo().setUsertype(CustomerConstant.UserType_user);
		theForm.getVo().setLocaleid(localeid);
		return mapping.findForward("addPage");
	}
	
	/** 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward deleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserForm theForm = (UserForm) form;
		Long pk = theForm.getVo().getUserid();
		int rows = 1;
		UsershopLogic logic = BOFactory_Platform.getUsershopLogic();
		logic.delUserInShop(pk, null, "00000002");
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
	/**
	 * 更新用户密码
	 */
	private ActionForward updatePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserForm theForm = (UserForm) form;
		Long userid = getLoginInfo().getUserid();
		String oldpass = theForm.getOldpassword();
		String newpass = theForm.getNewpassword();
		String result = String.valueOf(CommonConstant.success);
		String messcode = KeyInMemoryConstant.modifySuccess;
		try{
			AssertUtil.assertNotNull(oldpass, ExceptionConstantBase.Error_Need_Paramter);
			AssertUtil.assertNotNull(newpass, ExceptionConstantBase.Error_Need_Paramter);
			UserLogic userlogic = BOFactory_Platform.getUserLogic();
			userlogic.updatePassword(oldpass, newpass, userid);
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			messcode = e.getMessage();
		}
		
		String info = getResources(request, "BizKey").getMessage(messcode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
