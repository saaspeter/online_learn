package netTestWeb.platform.user.action;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.user.form.UserForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonWeb.security.authentication.UserinfoSession;
import platform.constant.CustomerConstant;
import platform.constant.ShopappConstant;
import platform.dao.CustinfoexDao;
import platform.dao.UserDao;
import platform.dao.UsercontactinfoDao;
import platform.dto.UserQuery;
import platform.exception.ExceptionConstant;
import platform.logic.UserLogic;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.user.vo.Accountvalidatetask;
import platform.vo.Custinfoex;
import platform.vo.Custstatus;
import platform.vo.User;
import platform.vo.Useraccountsetting;
import platform.vo.Usercontactinfo;
import commonTool.base.Page;
import commonTool.biz.logic.CountryregionLogic;
import commonTool.biz.logicImpl.CountryregionLogicImpl;
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
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listuser".equals(myaction)) {
			myforward = list(mapping, theForm, request, response);
		} else if ("registeruser".equals(myaction)) {
			myforward = registerUser(mapping, actionform, request,response);
		} else if ("activeuserregister".equals(myaction)) {
			myforward = activeuserregister(mapping, actionform, request,response);
		} else if ("saveuser".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("edituser".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editBasicInfo(mapping, actionform, request,response);
		} else if ("viewuser".equals(myaction)) {
			theForm.setEditType(-2);
			myforward = editBasicInfo(mapping, actionform, request,response);
		} else if ("viewprofile".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editBasicInfo(mapping, actionform, request,response);
		} else if ("adduser".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteuser".equals(myaction)) {
			myforward = deleteUser(mapping, actionform, request,response);
		} else if ("updatepassword".equals(myaction)) {
			myforward = updatePassword(mapping, actionform, request,response);
		} else if ("showuseraccountsetting".equals(myaction)) {
			myforward = showUserAccountSetting(mapping, actionform, request,response);
		} else if ("updateuseraccountsetting".equals(myaction)) {
			myforward = updateuseraccountsetting(mapping, actionform, request,response);
		} else if ("statuschangepage".equals(myaction)) {
			myforward = statusChangePage(mapping, actionform, request, response);
		} else if ("savestatus".equals(myaction)) {
			myforward = saveStatus(mapping, actionform, request,response);
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
		UserQuery query = theForm.getQueryVO();
	    // 设置客户状态列表
//		theForm.setStatusList(CustomerConstant.getCustomerStatusLabels(locale));
		UserDao dao = BOFactory_Platform.getUserDao();
		Page page = dao.selectByVOPage(query, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	/** 修改用户的登录名密码，用于商店用户创建的用户首次登录系统，需要更改自己的登录名
	 * 或者使用social login后，如果用户还没有设置过loginname, 则跳转到设置页面，并且保存设置 **/
	private ActionForward activeuserregister(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserForm theForm = (UserForm) form;
		String result = String.valueOf(CommonConstant.success);
		String messcode = KeyInMemoryConstant.modifySuccess;
		User vo = theForm.getVo();
		String newpassword = theForm.getNewpassword();
		try{
			AssertUtil.assertNotEmpty(vo.getLoginname(), ExceptionConstant.Error_Need_Paramter);
			
			UserLogic userlogic = BOFactory_Platform.getUserLogic(); 
			// 验证loginname是否已经存在
			boolean checkloginameresult = userlogic.isExistsLoginname(vo.getLoginname());
			if(checkloginameresult){
				throw new LogicException(ExceptionConstant.Error_UserLoginname_alreadyExist);
			}
			
			boolean isSocialLogin = false;
			String changedEmail = null;
			User dbvo = null;
			Short userstatus = null;
			if(theForm.getValidatecode()!=null && theForm.getValidatecode().trim().length()>0){
				AssertUtil.assertNotEmpty(vo.getEmail(), ExceptionConstant.Error_Need_Paramter);
				// 根据email和validatecode检查验证码是否正确, security check in case of hack attack
				Accountvalidatetask validateVO = BOFactory_Platform.getAccountvalidatetaskDao().selectUserTask(vo.getEmail(), 
						 Accountvalidatetask.Validatetype_NewUserValidate, theForm.getValidatecode().trim(), Accountvalidatetask.Status_Created);
				if(validateVO==null) {
					throw new LogicException(ExceptionConstantBase.Error_Invalid_Visit);
				}
				userstatus = CustomerConstant.CustomerStatus_active;
				dbvo = BOFactory_Platform.getUserDao().selectByEmail(vo.getEmail());
			}else {
				// check if is social login case, 当用户social login成功后，需要转向设置用户的login/password页面
				UserinfoSession userloginfo = getLoginInfo();
				if(!userloginfo.isAnonymous() 
						&& CommonConstant.isSocialLogin(userloginfo.getLogintype())){
					isSocialLogin = true;
				}
				if(!isSocialLogin){
					throw new LogicException(ExceptionConstantBase.Error_Invalid_Visit);
				}
				// 只能修改当前用户自己的信息
				vo.setUserid(userloginfo.getUserid());
				dbvo = BOFactory_Platform.getUserDao().selectByPK(vo.getUserid());
				// 验证email是否已经存在了
				if(vo.getEmail()!=null && !"".equals(vo.getEmail())){
					boolean existEmail = userlogic.isExistsEmail(vo.getEmail());
					if(existEmail && !vo.getEmail().equals(dbvo.getEmail())){
						throw new LogicException(ExceptionConstant.Error_Email_alreadyExist);
					}else {
						changedEmail = vo.getEmail();
					}
				}
			}
			AssertUtil.assertNotNull(dbvo, null);
			
			// activate user information
			userlogic.firstSetUserAccountInfo(dbvo.getUserid(), vo.getLoginname(), 
											  newpassword, changedEmail, userstatus);
			// if activate user through email link, then here delete validate task
			if(theForm.getValidatecode()!=null && theForm.getValidatecode().trim().length()>0){
			   // delete active data
			   BOFactory_Platform.getAccountvalidatetaskDao().deleteUserTask(vo.getEmail(), Accountvalidatetask.Validatetype_NewUserValidate, null);
			}else if(isSocialLogin){
				// 如果是social登录，则设置完信息后，把session中的信息也更新
				UserinfoSession userloginfo = getLoginInfo();
				if(vo.getLoginname()!=null){
					userloginfo.setLoginname(vo.getLoginname());
				}
				if(changedEmail!=null){
					userloginfo.setEmail(changedEmail);
				}
			}
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			messcode = e.getMessage();
			log.error("ooops, error in activeuserregister1: email:"
					+vo.getEmail()+" | loginame:"+vo.getLoginname()+" | userid:"+vo.getUserid(), e);
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			messcode = ExceptionConstant.Error_System;
			log.error("ooops, error in activeuserregister2:email:"
					+vo.getEmail()+" | loginame:"+vo.getLoginname(), e);
		}
		
		String info = getResources(request, "BizKey").getMessage(messcode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 更新用户信息 **/
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		UserForm theForm = (UserForm) form;
		User user = theForm.getVo();
		AssertUtil.assertNotNull(user, null);
		user.setUserid(userid);
		Usercontactinfo contactinfo = theForm.getContactinfo();
		contactinfo.setRegioncode(theForm.getRealRegionCode());
		Custinfoex custinfoex = theForm.getCustinfoex();
		user.setContactinfo(contactinfo);
		user.setCustinfoex(custinfoex);

		UserLogic logic = BOFactory_Platform.getUserLogic();
		logic.updateUser(user);
		
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");

		return mapping.findForward("viewedit");
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
		// set region code
		contactinfo.setRegioncode(theForm.getRealRegionCode());
		
		Custinfoex custinfoex = theForm.getCustinfoex();
		user.setContactinfo(contactinfo);
		user.setCustinfoex(custinfoex);
		user.setStagestatus(CustomerConstant.StageStatus_settedLoginName);
		String messCode = KeyInMemoryConstant.AddSuccess;
        try {
        	UserLogic logic = BOFactory_Platform.getUserLogic();
 		    logic.insertUser(user, CustomerConstant.NewCreateUserType_SelfRegis);
		} catch (DuplicateException ex) {
			request.setAttribute("bundle", "BizKey");
			request.setAttribute(KeyInMemoryConstant.DisMessKey, "commonWeb.logic.duplicateUser");
			return this.addPage(mapping, theForm, request, response);
		} 
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("registerCompletePage");
	}
	
	/** 
	 * Method editBasicInfo:暂时包括用户基本信息、用户联系方式、用户扩展信息
	 */
	private ActionForward editBasicInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserForm theForm = (UserForm) form;
		User vo = theForm.getVo();
		Long sessUserID = getLoginInfo().getUserid();
		Long pk = vo.getUserid();
		if(pk!=null){
		   // 如果不是viewuserprofile，则暂时只允许自己修改自己
			if(!pk.equals(sessUserID)&&(WebConstant.editType_view!=theForm.getEditType())){
				throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
			}
		} else{
		   pk = getLoginInfo().getUserid();
		}
		UserDao userdao = BOFactory_Platform.getUserDao();
		UsercontactinfoDao contactDao = BOFactory_Platform.getUsercontactinfoDao();
		CustinfoexDao custinfoexDao = BOFactory_Platform.getCustinfoexDao();
		vo = userdao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
		Usercontactinfo contactinfo = contactDao.selectByPK(pk);
		Custinfoex custinfoex = custinfoexDao.selectByPK(pk);
		theForm.setVo(vo);
		// 如果用户的状态是inactive, 查询出原因
		if(CustomerConstant.CustomerStatus_inactive.equals(vo.getStatus())){
			Custstatus statusvo = BOFactory_Platform.getCuststatusDao().selectUserLastChange(pk);
			theForm.setStatusvo(statusvo);
		}
		if(contactinfo!=null)
		   theForm.setContactinfo(contactinfo);
		if(custinfoex!=null){
		   theForm.setCustinfoex(custinfoex);
		   CountryregionLogic regionLogin = CountryregionLogicImpl.getInstance();
		   String[] regionArr = regionLogin.getPrivCityCode(contactinfo.getRegioncode(), vo.getLocaleid().toString());
		   theForm.setPrivCode(regionArr[0]);
		   theForm.setCityCode(regionArr[1]);
		}
		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("editPage");
		else if(theForm.getEditType()==-2)
		   return mapping.findForward("vieweditPage");
		else if(theForm.getEditType()==WebConstant.editType_view)
		   return mapping.findForward("viewPage");
		else 
		   return null;
	}
	
	/** 
	 * Method add
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		UserForm theForm = (UserForm) form;
		Long localeid = theForm.getVo().getLocaleid();
		Long sessLocaleid = getLoginInfo(true).getLocaleid();
		if(localeid==null){
			localeid = sessLocaleid;
		}else if(!localeid.equals(sessLocaleid)){
			switchUseLocale(localeid, request);
		}
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
		String messCode = KeyInMemoryConstant.deleteSuccess;
		UsershopLogic logic = BOFactory_Platform.getUsershopLogic();
		logic.delUserInShop(pk, null);
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
	/////////////////////////// below is user self management action //////////////////////
	

	
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
		}catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			messcode = ExceptionConstant.Error_System;
		}
		
		String info = getResources(request, "BizKey").getMessage(messcode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/**
	 * 查看用户的功能帐号设置
	 */
	private ActionForward showUserAccountSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserForm theForm = (UserForm) form;
		Long pk = theForm.getSettingvo().getUserid();
		
		Useraccountsetting settingVO = BOFactory_Platform.getUseraccountsettingDao().selectByPK(pk);
		AssertUtil.assertNotNull(settingVO, null);
		theForm.setSettingvo(settingVO);
		// 设置创建商店数目
		int createdshopnum = BOFactory_Platform.getShopDao().selectCountByOwner(pk);
		int inappshopnum = BOFactory_Platform.getShopappDao().selectCountByOwner(pk, ShopappConstant.AppStatus_needApprove);
		settingVO.setCreatedShopNum(createdshopnum);
		settingVO.setInapplyShopNum(inappshopnum);
		return mapping.findForward("usersettingpage");
	}
	
	/**
	 * 修改用户的功能帐号设置
	 */
	private ActionForward updateuseraccountsetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserForm theForm = (UserForm) form;
		Useraccountsetting settingvo = theForm.getSettingvo();
		AssertUtil.assertNotNull(settingvo.getUserid(), null);
		BOFactory_Platform.getUseraccountsettingDao().updateByPK(settingvo);
		theForm.setSettingvo(settingvo);
		String actionUrl = "showuseraccountsetting.do?settingvo.userid="+settingvo.getUserid()+"&vo.loginname="+theForm.getVo().getLoginname();
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessUrlPage(request, actionUrl, messCode, "1", "BizKey");
		return mapping.findForward("toUrl");
	}
	
	private ActionForward statusChangePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm theForm = (UserForm) form;
		Locale locale = getLoginInfo().getLocale();
		UserQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getUserid();
		if(pk==null){
		   log.info("----error:in Class:UserAction Method:statusChangePage::no pk");
		   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
		}
		UserDao dao = BOFactory_Platform.getUserDao();
		User vo = dao.selectByPK(pk);
		theForm.setVo(vo);
		// 设置原状态名字
		theForm.setOrginStatusName(CustomerConstant.getCustomerStatusName(vo.getStatus(), locale));

        return mapping.findForward("statusChangePage");
	}
	
	private ActionForward saveStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserForm theForm = (UserForm) form;
		User vo = theForm.getVo();

		UserLogic logic = BOFactory_Platform.getUserLogic();
		boolean result = logic.changeStatus(vo, theForm.getStatuschangereason());
		if(result)
			this.setMessagePage(request,theForm,"platformWeb.UserAction.changeStatus.success",null,"platformKey");
		else
			this.setMessagePage(request,theForm,"platformWeb.UserAction.changeStatus.failure",null,"platformKey");

		return mapping.findForward("toUrl");
	}
	
}
