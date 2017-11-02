
package netTestWeb.platform.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.platform.shop.form.ShopstyleconfigForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.ShopstyleconfigDao;
import platform.logic.ShopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shopstyleconfig;
import commonTool.util.AssertUtil;

/** 
 * 
 */
public class ShopstyleconfigAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopstyleconfigAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("saveshopstyle".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editshopstyle".equals(myaction)) {
			myforward = editPage(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;  
	}
	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopstyleconfigForm theForm = (ShopstyleconfigForm) form;
		String messCode = KeyInMemoryConstant.modifySuccess;
		Shopstyleconfig vo = theForm.getVo();
		Long pk = vo.getShopid();
		if(pk==null){
			pk = getLoginInfo().getShopid();
			vo.setShopid(pk);
		}
		AssertUtil.assertNotNull(pk, null);
        
		ShopLogic logic = BOFactory_Platform.getShopLogic();
		logic.saveShopstyle(vo, theForm.getUploadFile());

		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("viewedit");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		ShopstyleconfigForm theForm = (ShopstyleconfigForm) form;
		Shopstyleconfig vo = theForm.getVo();
		Long pk = vo.getShopid();
		if(pk==null){
			pk = getLoginInfo().getShopid();
		}

		AssertUtil.assertNotNull(pk, null);
		ShopstyleconfigDao dao = BOFactory_Platform.getShopstyleconfigDao();
		vo = dao.selectByPK(pk);
		if(vo==null){
			vo = new Shopstyleconfig();
			vo.setShopid(pk);
		}
		theForm.setVo(vo);

	    return mapping.findForward("viewedit");
	}
		
}
