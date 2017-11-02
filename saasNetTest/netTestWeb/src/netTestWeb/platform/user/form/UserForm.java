
package netTestWeb.platform.user.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import platform.dto.UserQuery;
import platform.vo.Custinfoex;
import platform.vo.Custstatus;
import platform.vo.User;
import platform.vo.Useraccountsetting;
import platform.vo.Usercontactinfo;


/** 
 * 
 */
public class UserForm extends BaseForm {
	
	private User vo;
	private UserQuery queryVO;
	/** 用户扩展信息 **/
	private Custinfoex custinfoex;
	/** 用户联系信息，用于保存用户信息时 **/
	private Usercontactinfo contactinfo;
	
	private List statusList;
	
	/** 0验证码不通过，1验证码校验通过 **/
	private String captchacodecheck;
	/** 原密码 */
	private String oldpassword;
	/** 新密码 */
	private String newpassword;
	
	private String privCode;
	
	private String cityCode;
	
	private Custstatus statusvo;
	
	private Useraccountsetting settingvo;
	
	private String email;
	
	
	private String orginStatusName;// 记录原状态名
	// 用户状态改变原因
	private String statuschangereason;
	/** 通过邮件跳转到系统来修改password或acive帐号时需要提供 validatecode **/
	private String validatecode;
	/** 用户通过社交方式登录，提供accesstoken **/
	private String accesstoken;


	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new User();
		queryVO = new UserQuery();
		contactinfo = new Usercontactinfo();
		custinfoex = new Custinfoex();
		captchacodecheck = "";
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
		if("registeruser".equals(myaction)&&vo.getUserid()==null){
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

	public User getVo() {
		return vo;
	}

	public void setVo(User vo) {
		this.vo = vo;
	}

	public Usercontactinfo getContactinfo() {
		return contactinfo;
	}

	public void setContactinfo(Usercontactinfo contactinfo) {
		this.contactinfo = contactinfo;
	}

	public Custinfoex getCustinfoex() {
		return custinfoex;
	}

	public void setCustinfoex(Custinfoex custinfoex) {
		this.custinfoex = custinfoex;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getPrivCode() {
		return privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}
	
	public String getRealRegionCode() {
		if(cityCode!=null&&cityCode.trim().length()>0){
			return cityCode;
		}else if(privCode!=null&&privCode.trim().length()>0){
			return privCode;
		}else {
			return null;
		}
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public String getCaptchacodecheck() {
		return captchacodecheck;
	}

	public void setCaptchacodecheck(String captchacodecheck) {
		this.captchacodecheck = captchacodecheck;
	}

	public Custstatus getStatusvo() {
		return statusvo;
	}

	public void setStatusvo(Custstatus statusvo) {
		this.statusvo = statusvo;
	}

	public Useraccountsetting getSettingvo() {
		if(settingvo==null){
			settingvo = new Useraccountsetting();
		}
		return settingvo;
	}

	public void setSettingvo(Useraccountsetting settingvo) {
		this.settingvo = settingvo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UserQuery queryVO) {
		this.queryVO = queryVO;
	}

	public String getOrginStatusName() {
		return orginStatusName;
	}

	public void setOrginStatusName(String orginStatusName) {
		this.orginStatusName = orginStatusName;
	}

	public String getStatuschangereason() {
		return statuschangereason;
	}

	public void setStatuschangereason(String statuschangereason) {
		this.statuschangereason = statuschangereason;
	}
	
	public String getValidatecode() {
		return validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
}
