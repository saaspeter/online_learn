
package platformWeb.shop.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springside.components.acegi.vo.UserinfoSession;

import platform.constant.ShopConstant;
import platform.dao.ShopDao;
import platform.dto.ShopQuery;
import platform.exception.ExceptionConstant;
import platform.logic.ShopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shop;
import platform.vo.Shopstatuslog;
import platformWeb.base.BaseAction;
import platformWeb.base.KeyInMemoryConstant;
import platformWeb.shop.form.ShopForm;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;


public class ShopAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopForm theForm = (ShopForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listshop".equals(myaction)) {
			myforward = listshop(mapping, actionform, request,response);
		} else if ("myOwnShops".equals(myaction)) {
			myforward = myOwnShops(mapping, actionform, request,response);
		} else if ("listShopIndex".equals(myaction)) {
			myforward = listShopIndex(mapping, actionform, request,response);
		} else if ("updateSaveShop".equals(myaction)) {
			myforward = updateSaveShop(mapping, actionform, request,response);
		} else if ("editshoppage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editShopPage(mapping, actionform, request,response);
		} else if ("viewshoppage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editShopPage(mapping, actionform, request,response);
		} else if ("shopStatusChangePage".equals(myaction)) {
			myforward = shopStatusChangePage(mapping, actionform, request,response);
		} else if ("saveShopStatus".equals(myaction)) {
			myforward = saveShopStatus(mapping, actionform, request,response);
		} else if ("deleteShop".equals(myaction)) {
			myforward = deleteShop(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list:商店列表，为管理平台查询用，可以查询所有商店
	 * 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward listshop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		ShopQuery queryVO = theForm.getQueryVO();
		Long localeid = getLoginInfo(true).getUseLocaleid();
		// 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
		// 选择商店
		ShopDao dao = BOFactory_Platform.getShopDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
		this.setPage(request, page);

		return mapping.findForward("list1");
	}
	
	/** 
	 * Method list:商店列表，为租户查询用，显示的Locale列表只是用户设置的Locale集合
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward myOwnShops(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Long localeid = getLoginInfo().getUseLocaleid();
		ShopQuery queryVO = theForm.getQueryVO();
		queryVO.setUserid(getLoginInfo().getUserid());
		// 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
		// 选择商店
        ShopDao dao = BOFactory_Platform.getShopDao();
    	List list = dao.selectByVO(queryVO);
		theForm.setShopList(list);
		return mapping.findForward("usershoplist");
	}
	
	/** 
	 * 首页上查询商店列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward listShopIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		int searchshoptype = theForm.getSearchshoptype();
		String searchtext = theForm.getSearchtext();
		Long localeid = getLoginInfo(true).getUseLocaleid();
		Long newscategoryid = theForm.getNewscategoryid();
		ShopQuery queryVO = theForm.getQueryVO();
		// 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        	theForm.setQueryVO(queryVO);
        }
        if(searchshoptype==1){ // 按商店名查询
        	queryVO.setShopname(searchtext);
        	queryVO.setShopcode(null);
        }else{ // 按商店编码查询
        	queryVO.setShopcode(searchtext);
        	queryVO.setShopname(null);
        }
		// 选择商店
        queryVO.setShopstatus(ShopConstant.ShopStatus_run);
        queryVO.setCategoryid(theForm.getCategoryid());
        ShopDao dao = BOFactory_Platform.getShopDao();
        Page page = null;
        if(CommonConstant.SearchTabPK.equals(newscategoryid)&&(searchtext==null||searchtext.trim().length()<1))
		{
			page = Page.EMPTY_PAGE;
		} else {
			page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
		}
		this.setPage(request, page);

		return mapping.findForward("shoplistIndex");
	}
	

		
	/** 
	 * Method updateSave
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward updateSaveShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
        Shop vo = theForm.getVo();
        Long userid = getLoginInfo().getUserid();
        vo.setUserid(userid);
		ShopLogic logic = BOFactory_Platform.getShopLogic();
		logic.updateShop(vo);
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	private ActionForward editShopPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		UserinfoSession userinfo = getLoginInfo();
		Long localeid = userinfo.getUseLocaleid();
		ShopQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getShopid();
		if(pk==null){
			pk = userinfo.getShopid();
		}
		AssertUtil.assertNotNull(pk, null);
		ShopDao dao = BOFactory_Platform.getShopDao();

        // 查询商店信息,如果默认locale没有设置，则用该商店设置的第一个locale作为查询locale
		Shop vo = dao.selectByPK(pk, localeid);
		if(vo==null){
            List list = dao.selectByVO(queryVO);
            if(list!=null&&list.size()>0)
			   vo = (Shop)list.get(0);
		}
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);
		int edittype = theForm.getEditType();
		String url = "viewPage";
		if(CommonConstant.editType_edit == edittype){
			url = "editPage";
		}
		return mapping.findForward(url);
	}
	
	/** 
	 * Method statusChangePage
	 */
	private ActionForward shopStatusChangePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Locale locale = getLoginInfo().getUseLocale();
		Long localeid = getLoginInfo().getUseLocaleid();
		ShopQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getShopid();
		if(pk==null){
		   log.info("----error:in Class:ShopAction Method:statusChangePage::no pk");
		   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
		}
		ShopDao dao = BOFactory_Platform.getShopDao();
        // 查询商店信息,如果默认locale没有设置，则用该商店设置的第一个locale作为查询locale
		Shop vo = dao.selectByPK(pk, localeid);
		if(vo==null){
            List list = dao.selectByVO(queryVO);
            if(list!=null&&list.size()>0)
			   vo = (Shop)list.get(0);
		}
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);
        return mapping.findForward("statusChangePage");
	}
	
	
	private ActionForward saveShopStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Shop vo = theForm.getVo();
		Shopstatuslog logVO = theForm.getLogVO();
		ShopLogic logic = BOFactory_Platform.getShopLogic();
		boolean result = logic.changeStatus(vo,logVO);
		String messCode = "platformWeb.BaseuserAction.changeStatus.success";
		if(!result)
		   messCode = "platformWeb.BaseuserAction.changeStatus.failure";
		
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 //TODO 删除很复杂 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		ShopLogic logic = BOFactory_Platform.getShopLogic();
		rows = logic.delShop(keys);
		
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		
		return mapping.findForward("toUrl");
	}
	

	
}
