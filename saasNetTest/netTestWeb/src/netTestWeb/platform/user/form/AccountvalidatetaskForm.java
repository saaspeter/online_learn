package netTestWeb.platform.user.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import netTestWeb.base.BaseForm;
import netTestWeb.platform.user.action.AccountvalidatetaskAction;
import platform.user.vo.Accountvalidatetask;

/** 
 */
public class AccountvalidatetaskForm extends BaseForm {
	
	private Accountvalidatetask vo;
	private String email;
	private String validatecode;
	/** 新密码 */
	private String password;
	private String loginname;
	
	/** 0验证码不通过，1验证码校验通过 **/
	private String captchacodecheck;
	/** 是否是激活新user **/
	private boolean activenewuser = false;



	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Accountvalidatetask();
		activenewuser = false;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = null;
		String myaction = mapping.getParameter();
		// 新增user或忘记密码的时候需要输入验证码来验证
		if(AccountvalidatetaskAction.RequestResetPass_ActionName.equals(myaction)){
			boolean rtn = iscaptchaOK(request);
			if(!rtn){
				errors = new ActionErrors();
				errors.add("captcha_error_msg" , new ActionMessage("error.captcha.error.inputInvalid"));
				captchacodecheck = "0";
				request.setAttribute("captchacodecheck", "0");
			}
        }
		return errors;
	}

	public Accountvalidatetask getVo() {
		return vo;
	}

	public void setVo(Accountvalidatetask vo) {
		this.vo = vo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidatecode() {
		return validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getCaptchacodecheck() {
		return captchacodecheck;
	}

	public void setCaptchacodecheck(String captchacodecheck) {
		this.captchacodecheck = captchacodecheck;
	}

	public boolean isActivenewuser() {
		return activenewuser;
	}

	public void setActivenewuser(boolean activenewuser) {
		this.activenewuser = activenewuser;
	}
		
}
