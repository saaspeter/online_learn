
package netTestWeb.security.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import netTestWeb.base.BaseForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class LoginForm extends BaseForm {
	
	private String loginname;
	private String pass;
	private Short userType;
	private Long shopid;
	private Long localeid;
	
	private List userTypeList;
	/** 登录帐号类型，分为平台系统帐号(1)和教育系统帐号(2) **/
	private String loginnameType;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
        
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
        if(shopid==null||userType==null||loginnameType==null)
           errors.add("lack of parameters!", new ActionMessage("netTest.login.lackOfParameters"));
		return null;
	}

	public String getPass() {
		return pass;
	}

	public Short getUserType() {
		return userType;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public List getUserTypeList() {
		return userTypeList;
	}

	public void setUserTypeList(List userTypeList) {
		this.userTypeList = userTypeList;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginnameType() {
		return loginnameType;
	}

	public void setLoginnameType(String loginnameType) {
		this.loginnameType = loginnameType;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

		
}
