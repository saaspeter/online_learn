
package netTestWeb.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import platformWeb.base.BaseForm;
import platform.user.dto.UseractivityQuery;
import platform.user.vo.Useractivity;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class UseractivityForm extends BaseForm {
	
	private Useractivity vo;
	private UseractivityQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Useractivity();
		queryVO = new UseractivityQuery();
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
	
	public UseractivityQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UseractivityQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Useractivity getVo() {
		return vo;
	}

	public void setVo(Useractivity vo) {
		this.vo = vo;
	}

		
}
