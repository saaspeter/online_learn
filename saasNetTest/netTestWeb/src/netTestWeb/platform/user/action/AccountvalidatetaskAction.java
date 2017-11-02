package netTestWeb.platform.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.platform.user.form.AccountvalidatetaskForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.mail.logic.UserMailUtil;
import platform.user.logic.impl.AccountvalidatetaskLogicImpl;
import platform.user.vo.Accountvalidatetask;
import platform.vo.User;
import commonTool.constant.CommonConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonWeb.base.BaseActionBase;

/** 
 */
public class AccountvalidatetaskAction extends BaseAction {
	
	static Logger log = Logger.getLogger(AccountvalidatetaskAction.class.getName());
	
	public static final String RequestResetPass_ActionName = "requestresetpassemail";
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("activenewuserpage".equals(myaction)) {
			AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) actionform;
			theForm.setActivenewuser(true);
			myforward = toResetPassMailPage(mapping, actionform, request,response);
		} else if ("requestresetpassemail".equals(myaction)) {
			myforward = requestResetPassMail(mapping, actionform, request,response);
		} else if ("toresetpassmailpage".equals(myaction)) {
			myforward = toResetPassMailPage(mapping, actionform, request,response);
		} else if ("saveresetpassmail".equals(myaction)) {
			myforward = saveResetPassMail(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	
    // 发送密码重置邮件
	private ActionForward requestResetPassMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		AssertUtil.assertNotNull(theForm.getEmail(), ExceptionConstantBase.Error_Need_Paramter);
		
		String messcode = "UserAction.RequestResetPassMail.Success";
		User vo = BOFactory_Platform.getUserDao().selectByEmail(theForm.getEmail());
		if(vo!=null){
			// generate active mail and send mail
			UserMailUtil.sendRetsetPassMail(vo.getUserid(), theForm.getEmail(), request.getLocale(),
					   "/customers/toresetpassmailpage.do?email="+theForm.getEmail()+"&validatecode=");
		}else {
			// 抛出email不存在的信息
			messcode = "UserAction.RequestResetPassMail.Fail.NoSuchEmail";
		}
		request.setAttribute("pageAction", "closeWindow");
		super.setMessagePage(request, theForm, messcode, null, "BizKey");
		return mapping.findForward("messagePage");
	}
	
	// 点击邮件中的验证链接并前往reset password页面, 
	// 当orgAdmin创建了user后，user需要点击邮件链接激活帐号，也用这个方法(self register用户不需要激活)
	private ActionForward toResetPassMailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		AssertUtil.assertNotNull(theForm.getValidatecode(), ExceptionConstantBase.Error_Need_Paramter);
		AssertUtil.assertNotNull(theForm.getEmail(), ExceptionConstantBase.Error_Need_Paramter);

		String jsSuffix = BaseActionBase.getLoginInfo(true).getJsSuffix();
		String validatetype = Accountvalidatetask.Validatetype_ForgetPassValidate;
		if(theForm.isActivenewuser()){
			validatetype = Accountvalidatetask.Validatetype_NewUserValidate;
		}
		
		Accountvalidatetask vo = BOFactory_Platform.getAccountvalidatetaskDao().selectUserTask(theForm.getEmail(), 
									validatetype, theForm.getValidatecode(), Accountvalidatetask.Status_Created);
	    
		String messcode = "";
		String forwardurl = "messagePage";
		if(vo!=null){
			User uservo = BOFactory_Platform.getUserDao().selectByEmail(theForm.getEmail());
			
			// 如果reset password的最迟验证日期过了，则提示错误
			if(vo.getAliveenddate()!=null && 
					DateUtil.getInstance().getNowtime_GLNZ().compareTo(vo.getAliveenddate())>0){
				messcode = "UserAction.ResetPassMail.ActiveTimeOut";
				request.setAttribute("pageAction", "closeWindow");
				// delete the expired data
				BOFactory_Platform.getAccountvalidatetaskDao().deleteUserTask(theForm.getEmail(), Accountvalidatetask.Validatetype_ForgetPassValidate, theForm.getValidatecode());
			}else {
				AssertUtil.assertNotNull(uservo, ExceptionConstant.Error_User_NotExists);
				// 如果用户是admin创建的，是第一次登录，还没有初始化设置，则跳转设置页面
				if(theForm.isActivenewuser()
					||CustomerConstant.StageStatus_notSetLoginName.equals(uservo.getStagestatus())){
					request.setAttribute("loginname", uservo.getLoginname());
					request.setAttribute("email", theForm.getEmail());
	            	forwardurl = "activeregisterpage";
				}else {
					forwardurl = "resetpass";
				}
			}
	    }else {
	    	messcode = ExceptionConstantBase.Error_Invalid_Visit;
	    	request.setAttribute("pageAction", "closeWindow");
			super.setMessagePage(request, theForm, messcode, null, "BizKey");
	    }
		request.setAttribute("jsSuffix", jsSuffix);
		return mapping.findForward(forwardurl);
	}
	
	// 输入密码重置,通过邮件导向密码重置页面，然后点击保存触发的action
	private ActionForward saveResetPassMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		String email = theForm.getEmail();
		String newpass = theForm.getPassword();
		AssertUtil.assertNotNull(email, ExceptionConstantBase.Error_Need_Paramter);
		AssertUtil.assertNotNull(newpass, ExceptionConstantBase.Error_Need_Paramter);
		AssertUtil.assertNotNull(theForm.getValidatecode(), ExceptionConstantBase.Error_Need_Paramter);
		
        // 根据email和validatecode检查验证码是否正确
		Accountvalidatetask vo = BOFactory_Platform.getAccountvalidatetaskDao().selectUserTask(theForm.getEmail(), 
				 Accountvalidatetask.Validatetype_ForgetPassValidate, theForm.getValidatecode(), Accountvalidatetask.Status_Created);
		if(vo!=null){
			// save new password
			AccountvalidatetaskLogicImpl.getInstance().saveResetPasswordByMail(email, newpass);
			String messcode = "UserAction.ResetPassword.Success";
			setMessUrlPage(request, "/"+CommonConstant.WebContext_Education+"/tologin.do?messcode="+messcode, "", null, "BizKey");
			return mapping.findForward("toUrl");
		}else {
			throw new LogicException(ExceptionConstantBase.Error_Invalid_Visit);
		}
	}
	
}
