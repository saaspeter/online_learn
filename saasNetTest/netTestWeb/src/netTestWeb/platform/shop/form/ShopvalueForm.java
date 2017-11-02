
package netTestWeb.platform.shop.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.ShopvalueQuery;
import platform.vo.Shopvalue;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ShopvalueForm extends BaseForm {
	
	private Shopvalue vo;
	private ShopvalueQuery queryVO;

    /** 记录locales下拉列表  **/
	private List localesList;
	
	private List shopvalueList;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopvalue();
		queryVO = new ShopvalueQuery();
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
	
	public ShopvalueQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopvalueQuery queryVO) {
		this.queryVO = queryVO;
	}

	public List getLocalesList() {
		return localesList;
	}

	public void setLocalesList(List localesList) {
		this.localesList = localesList;
	}

	public Shopvalue getVo() {
		return vo;
	}

	public void setVo(Shopvalue vo) {
		this.vo = vo;
	}

	public List getShopvalueList() {
		return shopvalueList;
	}

	public void setShopvalueList(List shopvalueList) {
		this.shopvalueList = shopvalueList;
	}
		
}
