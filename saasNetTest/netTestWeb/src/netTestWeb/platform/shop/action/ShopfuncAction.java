
package netTestWeb.platform.shop.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.shop.form.ShopfuncForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import platform.constant.ShopfuncConstant;
import platform.constant.SysfunctionitemConstant;
import platform.dao.ShopfuncDao;
import platform.dao.SysfunctionitemDao;
import platform.dto.ShopfuncQuery;
import platform.exception.ExceptionConstant;
import platform.logic.ShopfuncLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shopfunc;

import commonTool.constant.CommonConstant;
import commonTool.constant.PayTypeConstant;
import commonTool.exception.DuplicateException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;

/** 
 * 
 */
public class ShopfuncAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopfuncAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		//ShopForm theForm = (ShopForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("showorderapproveconfig".equals(myaction)) {
			myforward = showOrderApproveConfig(mapping, actionform, request,response);
		} else if ("saveorderapprovefunc".equals(myaction)) {
			myforward = saveorderapprovefunc(mapping, actionform, request,response);
		} else if ("deletefunction".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	private ActionForward showOrderApproveConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopfuncForm theForm = (ShopfuncForm) form;
		Long shopid = theForm.getShopid();
		String result;
		String info = "";
		try {
			if(shopid==null){
			   shopid = getLoginInfo().getShopid();
			}
			ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
			Shopfunc vo = dao.selectByLogicPK(shopid, SysfunctionitemConstant.Edu_OrderAutoApprove);
			result = "0";
			if(vo!=null){
				result = "1";
				info = vo.getShopfuncid().toString();
			}
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	private ActionForward saveorderapprovefunc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopfuncForm theForm = (ShopfuncForm) form;
		Long shopid = theForm.getShopid();
		if(shopid==null){
		   shopid = getLoginInfo().getShopid();
		}
		String result;
		String info = "";
		try {
			ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
			Shopfunc vo = new Shopfunc();
			vo.setFunctioncode(SysfunctionitemConstant.Edu_OrderAutoApprove);
			vo.setPaytype(PayTypeConstant.PayType_free);
			vo.setShopid(shopid);
			vo.setSyscode(CommonConstant.SysCode_Education);
			vo.setStatus(ShopfuncConstant.Status_valide);
			dao.insert(vo);
			result = "0";
			if(vo!=null){
				result = "1";
			}
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	private ActionForward shopsavefunc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopfuncForm theForm = (ShopfuncForm) form;
		Long shopid = theForm.getShopid();
		if(shopid==null){
		   shopid = getLoginInfo().getShopid();
		}
		String result;
		String info = "";
		try {
			ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
			Shopfunc vo = theForm.getVo();
			AssertUtil.assertNotNull(vo, null);
			if(!SysfunctionitemConstant.isShopCanSelfSet(vo.getFunctioncode())){
				throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
			}
			dao.insert(vo);
			result = "0";
			if(vo!=null){
				result = "1";
			}
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * Method list
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopfuncForm theForm = (ShopfuncForm) form;
		ShopfuncQuery queryVO = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();

		ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
		List list = dao.selectByVO(queryVO);
		theForm.setDatalist(list);

		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopfuncForm theForm = (ShopfuncForm) form;
		Shopfunc vo = theForm.getVo();
        try {
        	ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
        	dao.updateByPK(vo);
		} catch (DuplicateException ex) {
			MessageResources messRes = this.getResources(request,"BizKey");
			String message = messRes.getMessage("commonWeb.logic.duplicateUser");
			request.setAttribute(KeyInMemoryConstant.DisMessKey, message);
			mapping.findForward("toUrl");
		} 
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method insert
	 */
	private ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopfuncForm theForm = (ShopfuncForm) form;
		Shopfunc vo = theForm.getVo();
		String[] funcArr = theForm.getFuncArr();

       	ShopfuncLogic logic = BOFactory_Platform.getShopfuncLogic();
       	logic.insertFuncs(vo.getShopid(), funcArr);

       	String messCode = KeyInMemoryConstant.AddSuccess;
       	super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopfuncForm theForm = (ShopfuncForm) form;
		ShopfuncQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getShopfuncid();
		Locale locale = getLoginInfo().getLocale();

        AssertUtil.assertNotNull(pk, null);
		ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
		Shopfunc vo = dao.selectByPK(pk);
		String funcname = SysfunctionitemConstant.qryFuncName(vo.getFunctioncode(), locale);
		vo.setFunctionname(funcname);
		theForm.setVo(vo);

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("editPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopfuncForm theForm = (ShopfuncForm)form;
		Shopfunc vo = theForm.getVo();

		ShopfuncLogic logic = BOFactory_Platform.getShopfuncLogic();
		String[] funcArr = logic.qryShopfuncArr(vo.getShopid());
		// 设置系统功能列表
		SysfunctionitemDao fundao = platform.logicImpl.BOFactory_Platform.getSysfunctionitemDao();
		theForm.setFuncPlatList(fundao.selectBySyscode(CommonConstant.SysCode_Platform));
		theForm.setFuncEduList(fundao.selectBySyscode(CommonConstant.SysCode_Education));
		theForm.setFuncArr(funcArr);
		
		return mapping.findForward("addPage");
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ShopfuncForm theForm = (ShopfuncForm) form;
		Long shopid = theForm.getShopid();
		String fucntioncode = theForm.getFunctioncode();
		String result = String.valueOf(CommonConstant.success);
		String info = "1";
		try{
			if(shopid==null){
			   shopid = getLoginInfo().getShopid();
			}
		    ShopfuncDao dao = BOFactory_Platform.getShopfuncDao();
		    int rows = dao.deleteByLogicPK(shopid, fucntioncode);
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
