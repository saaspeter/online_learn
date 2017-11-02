
package platformWeb.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platformWeb.base.BaseForm;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class LoginForm extends BaseForm {
	
	private String loginname;
	private String pass;
	private Short userType;
	
	private List userTypeList;
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

		
}
