
package netTestWeb.platform.shop.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import platform.shop.dto.ShoppostQuery;
import platform.shop.vo.Shoppost;


public class ShoppostForm extends BaseForm {
	
	private Shoppost vo;
	private ShoppostQuery queryVO;
	
	private Long shopid;
	
	/** 1管理界面上的商店公告列表。2商店主页上的商店公告 **/
	private int urltype;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shoppost();
		queryVO = new ShoppostQuery();
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
	
	public ShoppostQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShoppostQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shoppost getVo() {
		return vo;
	}

	public void setVo(Shoppost vo) {
		this.vo = vo;
	}

	public int getUrltype() {
		return urltype;
	}

	public void setUrltype(int urltype) {
		this.urltype = urltype;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

		
}
