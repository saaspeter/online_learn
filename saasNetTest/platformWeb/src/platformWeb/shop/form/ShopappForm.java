
package platformWeb.shop.form;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.ShopappQuery;
import platform.vo.Shopapp;
import platformWeb.base.BaseForm;


public class ShopappForm extends BaseForm {
	
	//private Shop shopvo;
	private Shopapp vo;
	private ShopappQuery queryVO;
	
	/** 商店选取的商店目录id字符串 **/
	private String prodcates;

	private Locale locale;
	
	/** 记录商店申请List列表 **/
	private List shopapplist;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopapp();
		//shopvo = new Shop();
		queryVO = new ShopappQuery();
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		//if(queryVO!=null){
		//   if(request.getParameter("queryVO.categorylevel")==null
		//      ||(!((String)request.getParameter("queryVO.categorylevel")).matches("^\\d+$")))
		//    	queryVO.setCategorylevel(null);
		//}
		return null;
	}
	
	public ShopappQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopappQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopapp getVo() {
		return vo;
	}

	public void setVo(Shopapp vo) {
		this.vo = vo;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public String getProdcates() {
		return prodcates;
	}

	public void setProdcates(String prodcates) {
		this.prodcates = prodcates;
	}

	public List getShopapplist() {
		return shopapplist;
	}

	public void setShopapplist(List shopapplist) {
		this.shopapplist = shopapplist;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
		
}
