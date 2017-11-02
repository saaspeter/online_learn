
package netTestWeb.common.form;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import netTestWeb.base.BaseForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class ShopForUserForm extends BaseForm {
	
	static Logger log = Logger.getLogger(ShopForUserForm.class.getName());
	

	private List shopList;
    
    
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

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

	public List getShopList() {
		return shopList;
	}

	public void setShopList(List shopList) {
		this.shopList = shopList;
	}


}
