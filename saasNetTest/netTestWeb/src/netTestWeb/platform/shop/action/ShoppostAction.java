
package netTestWeb.platform.shop.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.shop.form.ShoppostForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.ShopConstant;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.logicImpl.UsershopLogicImpl;
import platform.shop.dao.ShoppostDao;
import platform.shop.dto.ShoppostQuery;
import platform.shop.logic.ShoppostLogic;
import platform.shop.vo.Shoppost;
import platform.vo.Shop;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.DateUtil;
import commonWeb.security.authentication.UserinfoSession;


public class ShoppostAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShoppostAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShoppostForm theForm = (ShoppostForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listShoppost".equals(myaction)) {
			theForm.setUrltype(1);
			myforward = list(mapping, actionform, request,response);
		} else if ("shoppostviewlist".equals(myaction)) {
			theForm.setUrltype(2);
			myforward = list(mapping, actionform, request,response);
		} else if ("saveShoppost".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editShoppost".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewShoppost".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addShoppost".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteShoppost".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShoppostForm theForm = (ShoppostForm) form;
		int urltype = theForm.getUrltype();
		Long shopid = theForm.getShopid();
		Long userid = getLoginInfo(true).getUserid();
		if(shopid==null){
			shopid = getLoginInfo(true).getShopid();
		}
		ShoppostQuery queryVO = theForm.getQueryVO();
		if(shopid!=null){
		   queryVO.setShopid(shopid);
		}
		ShoppostDao dao = BOFactory_Platform.getShoppostDao();
		Page page = dao.selectByVOPage(shopid, queryVO.getCaption(), 
				CommonConstant.Status_valide, SysparamConstant.syscode, 
				getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		
		String url = "list";
		if(urltype==2){
			url = "listview";
			// if shop is not open, need check whether user is member of the shop
			Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
			if(ShopConstant.openType_no.equals(shopvo.getOpentype())){
				UsershopLogicImpl.getInstance().checkAccessShop(userid, 
				    							shopid, CommonConstant.SysCode_Education, null);
			}
		}
		return mapping.findForward(url);
	}
	

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		UserinfoSession loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();
		ShoppostForm theForm = (ShoppostForm) form;
		Shoppost vo = theForm.getVo();
		vo.setShopid(shopid);
		vo.setCreator(userid);
		Date createDate = DateUtil.getInstance().getNowtime_GLNZ();
		vo.setCreatetime(createDate);
        if(vo.getId()==null||vo.getId()==0){
        	messCode = KeyInMemoryConstant.AddSuccess;
            if(vo.getType()==null){
            	vo.setType(Shoppost.TYPE_ShopPost);
            }
        }
        vo.setSyscode(SysparamConstant.syscode);
        vo.setStatus(CommonConstant.Status_valide);
        ShoppostDao dao = BOFactory_Platform.getShoppostDao();
		dao.save(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return list(mapping, form, request, response);
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ShoppostForm theForm = (ShoppostForm) form;
		ShoppostQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getId();
		
		ShoppostDao dao = BOFactory_Platform.getShoppostDao();
		Shoppost vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:ShoppostAction;--method:editPage;");
		theForm.setVo(vo);

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ShoppostForm theForm = (ShoppostForm) form;
		theForm.setEditType(WebConstant.editType_add);
		// insert a new post record first then get PK, then
		// if upload picture in the page, the upload path need use this PK
		ShoppostDao dao = BOFactory_Platform.getShoppostDao();
		Shoppost vo = new Shoppost();
		UserinfoSession loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();
		vo.setShopid(shopid);
		vo.setCreator(userid);
		vo.setSyscode(SysparamConstant.syscode);
		vo.setStatus(Shoppost.Status_AutoSave);
		vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		dao.insert(vo);
		theForm.setVo(vo);
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShoppostForm theForm = (ShoppostForm) form;
		Long pk = theForm.getVo().getId();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "1";
		try{
		    ShoppostLogic logic = BOFactory_Platform.getShoppostLogic();
		    rows = logic.delete(pk);
		    info = String.valueOf(rows);
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
