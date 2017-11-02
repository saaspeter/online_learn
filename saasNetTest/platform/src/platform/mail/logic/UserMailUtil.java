package platform.mail.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.DateUtil;
import platform.constant.CustomerConstant;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.user.logic.impl.AccountvalidatetaskLogicImpl;
import platform.user.vo.Accountvalidatetask;
import platform.user.vo.Useractivity;

public class UserMailUtil {

	public static final String EmailTemplate_WelcomeNewUser_SelfRegister = "User_welcomeNewUser_selfRegister";
	public static final String EmailTemplate_WelcomeNewUser_AdminAdd = "User_welcomeNewUser_adminAdd";
	public static final String EmailTemplate_ResetPassword = "User_resetPassword";
	
	public static final String MailVariable_LoginName = "%loginname%";
	public static final String MailVariable_ActiveLink = "%activeLink%";
	
	
	public static void sendWelcomeNewUserMail(Long userid, String toEmail, String loginname,
			                           Locale locale, String linkurl, Short newusertype){
		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put(MailVariable_LoginName, loginname);
		locale = I18nLogicImpl.getInstance().getSupportLocale(locale);
		String emailTemplate = EmailTemplate_WelcomeNewUser_SelfRegister;
		if(CustomerConstant.NewCreateUserType_OrgAdminAdd.equals(newusertype)){
			emailTemplate = EmailTemplate_WelcomeNewUser_AdminAdd;
			
			String validatecode = BOFactory_Platform.getAccountvalidatetaskLogic()
					.generateActiveTask(userid, toEmail, Accountvalidatetask.Validatetype_NewUserValidate, SysparamConstant.Email_ActivateLink_Day);
			String activelink = generateActiveUrl(validatecode, linkurl);
			replaceMap.put(MailVariable_ActiveLink, activelink);
		}
		
		BOFactory_Platform.getDefaultMailUtil().sendMail(null, emailTemplate, toEmail,
				 SysparamConstant.Email_Address_ReplyToMail, SysparamConstant.Email_Address_ReplyToMail, locale, replaceMap);
	}
	
	public static void sendRetsetPassMail(Long userid, String toEmail,  
										  Locale locale, String linkurl){
		if(toEmail==null||linkurl==null||"".equals(linkurl)){
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		}
		Accountvalidatetask taskVO = BOFactory_Platform.getAccountvalidatetaskDao().selectLastUserTask(toEmail, 
				                         Accountvalidatetask.Validatetype_ForgetPassValidate, null);
		Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		if(taskVO!=null){
			// 如果距上次申请重置密码时间在24小时内，则不允许再次申请重置密码
			if(currentDate.compareTo(DateUtil.dateAddDays(taskVO.getCreatedate(), 1))<0){
				throw new LogicException(ExceptionConstant.Error_ResetPassMail_CannotApplyAgain);
			}
		}
		
		Useractivity activityVO = BOFactory_Platform.getUseractivityDao().selectLastUserActivity(userid, Useractivity.Actiontype_UpdateForgotPassword);
		if(activityVO!=null){
			// 如果距上次成功重置密码时间在24小时内，则不允许再次申请重置密码
			if(currentDate.compareTo(DateUtil.dateAddDays(activityVO.getCreatetime(), 1))<0){
				throw new LogicException(ExceptionConstant.Error_ResetPassMail_CannotApplyAgain);
			}
		}
		
		Map<String, String> replaceMap = new HashMap<String, String>();
		String validatecode = AccountvalidatetaskLogicImpl.getInstance().generateActiveTask(userid, 
				toEmail, Accountvalidatetask.Validatetype_ForgetPassValidate, SysparamConstant.Email_ActivateLink_Day);
		String activelink = generateActiveUrl(validatecode,linkurl);
		replaceMap.put(MailVariable_ActiveLink, activelink);
		locale = I18nLogicImpl.getInstance().getSupportLocale(locale);
		 
		BOFactory_Platform.getDefaultMailUtil().sendMail(null, EmailTemplate_ResetPassword, 
												toEmail, SysparamConstant.Email_Address_ReplyToMail, SysparamConstant.Email_Address_ReplyToMail, 
												locale, replaceMap);
		
	}
	
	private static String generateActiveUrl(String validatecode, String linkurl){
		String activeAction = SysparamConstant.SiteURL+CommonConstant.WebContext_Education+linkurl;
		return activeAction+validatecode;
	}
	
}
