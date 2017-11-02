
package netTestWeb.platform.shop.form;

import javax.servlet.http.HttpServletRequest;
import netTestWeb.base.BaseForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import platform.shop.dto.ShopdescarticleQuery;
import platform.shop.vo.Shopdescarticle;

public class ShopdescarticleForm extends BaseForm {
	
	private Shopdescarticle vo;
	private ShopdescarticleQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		vo = new Shopdescarticle();
		queryVO = new ShopdescarticleQuery();
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
	
	public ShopdescarticleQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopdescarticleQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopdescarticle getVo() {
		return vo;
	}

	public void setVo(Shopdescarticle vo) {
		this.vo = vo;
	}

		
}
