
package netTestWeb.wareques.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.wareques.vo.Questionitem;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class QuestionitemForm extends BaseForm {
	
	private Questionitem vo;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Questionitem();
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

	public Questionitem getVo() {
		return vo;
	}

	public void setVo(Questionitem vo) {
		this.vo = vo;
	}

		
}
