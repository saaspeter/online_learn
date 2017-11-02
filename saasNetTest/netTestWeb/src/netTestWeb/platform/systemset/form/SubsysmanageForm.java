
package netTestWeb.platform.systemset.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class SubsysmanageForm extends BaseForm {
	
    private List subsysList; 

    /** 需要过滤掉的子系统字符串，用","隔开 **/
    private String filterStrs;

	public String getFilterStrs() {
		return filterStrs;
	}

	public void setFilterStrs(String filterStrs) {
		this.filterStrs = filterStrs;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		subsysList = new ArrayList();
		filterStrs = null;
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

	public List getSubsysList() {
		return subsysList;
	}

	public void setSubsysList(List subsysList) {
		this.subsysList = subsysList;
	}
		
}
