
package platformWeb.shop.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.ShopappConstant;
import platform.dao.ShopappDao;
import platform.dto.ShopappQuery;
import platform.exception.ExceptionConstant;
import platform.logic.ShopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shopapp;
import platformWeb.base.BaseAction;
import platformWeb.shop.form.ShopappForm;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.DuplicateException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;


public class ShopappAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopappAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopappForm theForm = (ShopappForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listmyshopapp".equals(myaction)) {
			myforward = listmyapp(mapping, actionform, request,response);
		} else if ("listshopapp".equals(myaction)) {
			myforward = listapp(mapping, actionform, request,response);
		} else if ("applyNewShop".equals(myaction)) {
			myforward = applyNewShop(mapping, actionform, request,response);
		} else if ("saveApplyNewShop".equals(myaction)) {
			myforward = saveApplyNewShop(mapping, actionform, request,response);
		} else if ("approveshopappbatch".equals(myaction)) {
			myforward = approveShopappBatch(mapping, actionform, request,response);
		} else if ("approveshopapp".equals(myaction)) {
			myforward = approveshopapp(mapping, actionform, request,response);
		} 
//		else if ("editmyshopappPage".equals(myaction)) {
//			theForm.setEditType(CommonConstant.editType_edit);
//			myforward = editShopappPage(mapping, actionform, request,response);
//		} 
		else if ("editshopappPage".equals(myaction)) {
			theForm.setEditType(5);
			myforward = editShopappPage(mapping, actionform, request,response);
		} else if ("viewshopappPage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editShopappPage(mapping, actionform, request,response);
		} else if ("delshopapp".equals(myaction)) {
			myforward = deleteShopapp(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	
	/** 
	 * Method listall：为租户使用，可以列出该租户所有申请商店记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listmyapp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopappForm theForm = (ShopappForm) form;
		ShopappQuery queryVO = theForm.getQueryVO();
		// 设置查询的用户id
		queryVO.setUserid(getLoginInfo().getUserid());
		// 查询
		ShopappDao dao = BOFactory_Platform.getShopappDao();
		List shopapplist = dao.selectByVO(queryVO);
		theForm.setShopapplist(shopapplist);
		return mapping.findForward("listmyapp");
	}
	
	/** 
	 * 管理员使用，查询系统中的商店申请信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listapp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopappForm theForm = (ShopappForm) form;
		ShopappQuery queryVO = theForm.getQueryVO();
		Short appstatus = queryVO.getAppstatus();
		if(appstatus==null){
			queryVO.setAppstatus(ShopappConstant.AppStatus_needApprove);
		}

		ShopappDao dao = BOFactory_Platform.getShopappDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
		this.setPage(request, page);
		return mapping.findForward("listapp");
	}
	
	/** 
	 * 申请商店页面
	 */
	public ActionForward applyNewShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopappForm theForm = (ShopappForm) form;
		Shopapp vo = theForm.getVo();
		Locale locale = getLoginInfo().getUseLocale();
		Long localeid = getLoginInfo().getUseLocaleid();
		// 设置用户的默认Locale
		vo.setLocaleid(localeid);
		theForm.setLocale(locale);

		return mapping.findForward("addpage");
	}
	
	/** 
	 * 保存用户申请的商店信息
	 */
	public ActionForward saveApplyNewShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopappForm theForm = (ShopappForm) form;
        Shopapp vo = theForm.getVo();
        String prodcates = theForm.getProdcates();
        Long userid = getLoginInfo().getUserid();
        Long localeid = getLoginInfo().getLocaleid();
        vo.setUserid(userid);
        vo.setLocaleid(localeid);
        ShopLogic logic = BOFactory_Platform.getShopLogic();
		try {
			logic.saveApplyNewShop(vo, prodcates);
		} catch (DuplicateException e) {
			super.setMessagePage(request, theForm, e.getMessage(), null, null);
		}
		// set messag page parameters.
		theForm.setHasUrlSubmit(0);
		request.setAttribute("pageAction", "closeWindow"); // 在申请商店成功的页面上显示 关闭窗口 的链接
		super.setMessagePage(request, theForm, "platformWeb.ShopAction.applyNewShop.success",null,null);
		return mapping.findForward("messagePage");
	}
	
	/** 
	 * Method saveApproveBatch:批量审核商店,保存批量处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward approveShopappBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopappForm theForm = (ShopappForm) form;
		String[] keys = theForm.getKeys();
		Shopapp appvo = theForm.getVo();
		int result = 0;
		ShopLogic logic = BOFactory_Platform.getShopLogic();
		result = logic.approveShopBatch(keys, appvo);
		// set messag page parameters.
		if(result>0)
		    super.setMessagePage(request, theForm, "platformWeb.ShopappAction.approveShop.success",null,null);
		else
			super.setMessagePage(request, theForm, "platformWeb.ShopappAction.approveShop.fail",null,null);
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * 审核商店
	 */
	public ActionForward approveshopapp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopappForm theForm = (ShopappForm) form;
		Shopapp vo = theForm.getVo();
        Long userid = getLoginInfo().getUserid();
        vo.setUserid(userid);
        String result = String.valueOf(CommonConstant.success);
		String messcode = "platformWeb.ShopappAction.approveShop.success";
		try{
			ShopLogic logic = BOFactory_Platform.getShopLogic();
			boolean approveRtn = logic.approveShop(vo);
			// set messag page parameters.
			if(!approveRtn){
				throw new LogicException("platformWeb.ShopappAction.approveShop.fail");
			}
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			messcode = e.getMessage();
		}
		String info = getResources(request).getMessage(messcode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * 商店申请编辑
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editShopappPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopappForm theForm = (ShopappForm) form;
		ShopappQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getShopappid();
		int edittype = theForm.getEditType();
		AssertUtil.assertNotNull(pk, "commonWeb.java.commonaction.errors.editPage.noPK");
		
		String url = "viewpage";
		if(CommonConstant.editType_edit==edittype){
			url = "editpage";
		}else if(CommonConstant.editType_view==edittype){
			url = "viewpage";
		}else if(5==edittype){
			url = "approvepage"; // 系统管理员审批商店申请的界面
		}

        // 查询审批表
		ShopappDao dao = BOFactory_Platform.getShopappDao();
		Shopapp vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		if(ShopappConstant.AppStatus_deny_del.equals(vo.getAppstatus())||
		   ShopappConstant.AppStatus_pass.equals(vo.getAppstatus())){
		   url = "viewpage";
		}

        return mapping.findForward(url);
	}

	/** 
	 * Method delete
	 */
	public ActionForward deleteShopapp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopappForm theForm = (ShopappForm) form;
		Long pk = theForm.getVo().getShopappid();
		String result = String.valueOf(CommonConstant.success);
		String messcode = "CommonSystem.commonAction.operation.succeed";
		try {
			AssertUtil.assertNotNull(pk, null);
			ShopappDao dao = BOFactory_Platform.getShopappDao();
			Shopapp appvo = dao.selectByPK(pk);
			AssertUtil.assertNotNull(appvo, null);
			if(ShopappConstant.AppStatus_pass.equals(appvo.getAppstatus())){
				throw new LogicException(ExceptionConstant.Error_ShopappStatus_CannotDelete);
			}
			dao.deleteByPK(pk);
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			messcode = e.getMessage();
			
		}
		String info = getResources(request).getMessage(messcode);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
