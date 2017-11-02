
package netTestWeb.paper.form;

import javax.servlet.http.HttpServletRequest;
import netTest.paper.dto.PaperquestypeQuery;
import netTest.paper.vo.Paperquestype;
import netTestWeb.base.BaseForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class PaperquestypeForm extends BaseForm {
	
	/** 一般是子题目的vo **/
	private Paperquestype vo;
	private PaperquestypeQuery queryVO;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		vo = new Paperquestype();
		queryVO = new PaperquestypeQuery();
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

	public Paperquestype getVo() {
		return vo;
	}

	public void setVo(Paperquestype vo) {
		this.vo = vo;
	}

	public PaperquestypeQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(PaperquestypeQuery queryVO) {
		this.queryVO = queryVO;
	}

		
}
