
package netTestWeb.platform.shop.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.shop.vo.Shoplicense;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ShoplicenseForm extends BaseForm {
	
	private Shoplicense vo;

	private List datalist;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shoplicense();
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

	public Shoplicense getVo() {
		return vo;
	}

	public void setVo(Shoplicense vo) {
		this.vo = vo;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}

		
}
