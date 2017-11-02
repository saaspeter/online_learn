
package netTestWeb.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.product.dto.ShopopenactivitymemberQuery;
import netTest.product.vo.Shopopenactivitymember;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ShopopenactivitymemberForm extends BaseForm {
	
	private Shopopenactivitymember vo;
	private ShopopenactivitymemberQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopopenactivitymember();
		queryVO = new ShopopenactivitymemberQuery();
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
	
	public ShopopenactivitymemberQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopopenactivitymemberQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopopenactivitymember getVo() {
		return vo;
	}

	public void setVo(Shopopenactivitymember vo) {
		this.vo = vo;
	}

		
}
