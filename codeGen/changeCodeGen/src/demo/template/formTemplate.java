
package {[#ProjectWeb#]}.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import {[#Project#]}Web.base.BaseForm;
import {[#PackageDto#]}.{[#className#]}Query;
import {[#PackageVO#]}.{[#className#]};

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class {[#className#]}Form extends BaseForm {
	
	private {[#className#]} vo;
	private {[#className#]}Query queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new {[#className#]}();
		queryVO = new {[#className#]}Query();
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
	
	public {[#className#]}Query getQueryVO() {
		return queryVO;
	}

	public void setQueryVO({[#className#]}Query queryVO) {
		this.queryVO = queryVO;
	}

	public {[#className#]} getVo() {
		return vo;
	}

	public void setVo({[#className#]} vo) {
		this.vo = vo;
	}

		
}