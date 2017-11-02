
package netTestWeb.platform.shop.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.platform.shop.form.ShopstatuslogForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.ShopstatuslogDao;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shopstatuslog;


public class ShopstatuslogAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopstatuslogAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listshopstatus".equals(myaction)) {
			myforward = listShopStatus(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * voter数据检查:shopid是否是用户自己的
	 */
	public ActionForward listShopStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopstatuslogForm theForm = (ShopstatuslogForm) form;
		Shopstatuslog vo = theForm.getVo();
		ShopstatuslogDao dao = BOFactory_Platform.getShopstatuslogDao();
		List list = dao.selectByShopID(vo.getShopid());
		theForm.setLogList(list);
		return mapping.findForward("list");
	}
		
}
