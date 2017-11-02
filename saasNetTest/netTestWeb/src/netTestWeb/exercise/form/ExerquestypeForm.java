
package netTestWeb.exercise.form;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.dto.ExerquestypeQuery;
import netTest.exercise.vo.Exerquestype;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ExerquestypeForm extends BaseForm {
	
	private Exerquestype vo;
	private ExerquestypeQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Exerquestype();
		queryVO = new ExerquestypeQuery();
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
	
	public ExerquestypeQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ExerquestypeQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Exerquestype getVo() {
		return vo;
	}

	public void setVo(Exerquestype vo) {
		this.vo = vo;
	}

		
}
