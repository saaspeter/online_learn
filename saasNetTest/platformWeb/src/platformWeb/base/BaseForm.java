package platformWeb.base;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import commonWeb.base.BaseFormBase;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class BaseForm extends BaseFormBase {
	
	static Logger log = Logger.getLogger(BaseForm.class.getName());

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	
}
