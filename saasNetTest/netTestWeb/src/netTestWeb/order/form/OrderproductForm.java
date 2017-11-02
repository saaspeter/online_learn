
package netTestWeb.order.form;

import javax.servlet.http.HttpServletRequest;
import netTest.order.dto.OrderproductQuery;
import netTest.order.vo.Orderproduct;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;


public class OrderproductForm extends BaseForm {
	
	private Orderproduct vo;
	private OrderproductQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Orderproduct();
		queryVO = new OrderproductQuery();
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
	
	public OrderproductQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(OrderproductQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Orderproduct getVo() {
		return vo;
	}

	public void setVo(Orderproduct vo) {
		this.vo = vo;
	}

		
}
