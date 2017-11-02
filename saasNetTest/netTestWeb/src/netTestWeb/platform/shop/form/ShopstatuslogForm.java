
package netTestWeb.platform.shop.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.vo.Shopstatuslog;


public class ShopstatuslogForm extends BaseForm {
	
	private Shopstatuslog vo;
	
	private String shopname;
	
	private List logList;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopstatuslog();
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

	public Shopstatuslog getVo() {
		return vo;
	}

	public void setVo(Shopstatuslog vo) {
		this.vo = vo;
	}

	public List getLogList() {
		return logList;
	}

	public void setLogList(List logList) {
		this.logList = logList;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
		
}
