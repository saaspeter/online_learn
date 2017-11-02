
package platformWeb.user.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import platform.dto.UserQuery;
import platform.vo.Custinfoex;
import platform.vo.User;
import platform.vo.Usercontactinfo;
import platformWeb.base.BaseForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class UserForm extends BaseForm {
	
	private User vo;
	private UserQuery query;
	/** 用户扩展信息 **/
	private Custinfoex custinfoex;
	/** 用户联系信息，用于保存用户信息时 **/
	private Usercontactinfo contactinfo;
	
	private String[] keys;
	private List localesList;
	private List statusList;
	
	private int editType;
	/** 搜索类型??1为只搜索本目录，2为包括下级数据的搜索??3为包含所有级别的数据 **/
	private String complexSearchDivStatus;
	
	/** 0验证码不通过，1验证码校验通过 **/
	private String validatecodeCheck;
	/** 原密码 */
	private String oldpassword;
	/** 新密码 */
	private String newpassword;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new User();
		query = new UserQuery();
		contactinfo = new Usercontactinfo();
		custinfoex = new Custinfoex();
		validatecodeCheck = "";
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
		if("saveUser".equals(myaction)&&vo.getUserid()==null){
			boolean rtn = isJcaptchaOK(request);
			if(!rtn){
				errors = new ActionErrors();
				errors.add("jcaptcha_error_msg" , new ActionMessage("error.jcaptcha.error.inputInvalid"));
				validatecodeCheck = "0";
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

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public List getLocalesList() {
		return localesList;
	}

	public void setLocalesList(List localesList) {
		this.localesList = localesList;
	}

	public UserQuery getQuery() {
		return query;
	}

	public void setQuery(UserQuery query) {
		this.query = query;
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

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}
	
	public String getValidatecodeCheck() {
		return validatecodeCheck;
	}

	public void setValidatecodeCheck(String validatecodeCheck) {
		this.validatecodeCheck = validatecodeCheck;
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
		
}
