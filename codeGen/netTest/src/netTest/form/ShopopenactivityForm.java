
package netTestWeb.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.product.dto.ShopopenactivityQuery;
import netTest.product.vo.Shopopenactivity;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ShopopenactivityForm extends BaseForm {
	
	private Shopopenactivity vo;
	private ShopopenactivityQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopopenactivity();
		queryVO = new ShopopenactivityQuery();
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
	
	public ShopopenactivityQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopopenactivityQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopopenactivity getVo() {
		return vo;
	}

	public void setVo(Shopopenactivity vo) {
		this.vo = vo;
	}

		
}
