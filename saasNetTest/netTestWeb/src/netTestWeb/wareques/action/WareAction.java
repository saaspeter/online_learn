
package netTestWeb.wareques.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.vo.Productbase;
import netTest.wareques.dao.WareDao;
import netTest.wareques.dto.WareQuery;
import netTest.wareques.logic.WareLogic;
import netTest.wareques.vo.Ware;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.wareques.form.WareForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;


public class WareAction extends BaseAction {
	
	static Logger log = Logger.getLogger(WareAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		WareForm theForm = (WareForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listWare1".equals(myaction)) {
			theForm.setListType("1");
			myforward = listware(mapping, actionform, request,response);
		} else if ("listWareSelect".equals(myaction)) {
			theForm.setListType("2");
			myforward = listware(mapping, actionform, request,response);
		} else if ("saveWare".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editWare".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewWare".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addWare".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteWare".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list_oper:操作员查询题库列表
	 */
	private ActionForward listware(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WareForm theForm = (WareForm) form;
		Long shopid = theForm.getShopid();
		WareQuery queryVO = theForm.getQueryVO();
		Long productid = theForm.getProductbaseid();
		String listType = theForm.getListType();
		String url = "list_oper";
		
		switchShop(shopid, queryVO, true);
        // 处理切换用户产品，因为除了产品管理者，学习管理者可以查看题库，并且新增题目到题库中，但不能编辑别人创建的题目
		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_userNormal);
		Page page = Page.EMPTY_PAGE;
		if(queryVO.getProductbaseid()!=null){
		   WareLogic logic = BOFactory.getWareLogic();
		   page = logic.qryWarePage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		   Productbase productvo = BOFactory.getProductbaseDao().selectByPK(queryVO.getProductbaseid());
		   theForm.setProductvo(productvo);
		}
		this.setPage(request, page);
		
		if(listType!=null&&"2".equals(listType))
			url = "sel_oper";
		return mapping.findForward(url);
	}
	
	/** 
	 * Method save
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		WareForm theForm = (WareForm) form;
		Ware vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&vo.getWareid()==null){
        	AssertUtil.assertNotNull(vo.getProductbaseid(), null);
        	Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(vo.getProductbaseid());
        	messCode = KeyInMemoryConstant.AddSuccess;
            vo.setShopid(prodvo.getShopid());
            vo.setCreatorid(getLoginInfo().getUserid());
        }
        WareLogic logic = BOFactory.getWareLogic();
        logic.saveWare(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		WareForm theForm = (WareForm) form;
		WareQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getWareid();
		
		WareDao dao = BOFactory.getWareDao();
		Ware vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		// set user loginame
		UsershopLogic usershoplogic = BOFactory_Platform.getUsershopLogic();
		String creatorname = usershoplogic.qryUsernameById(vo.getShopid(), vo.getCreatorid());
		vo.setCreatorname(creatorname);
		theForm.setVo(vo);
		
		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LoginInfoEdu loginfo = getLoginInfo();
		Long productid = loginfo.getProductid();
		String productname = loginfo.getProductname();
		WareForm theForm = (WareForm) form;
		theForm.getVo().setProductbaseid(productid);
		theForm.getVo().setProductname(productname);
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		WareForm theForm = (WareForm) form;
		Long wareid = theForm.getVo().getWareid();
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			WareLogic logic = BOFactory.getWareLogic();
			logic.delWare(wareid);
			info = KeyInMemoryConstant.deleteSuccessCommon;
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		// 得到错误信息
		info = getResources(request, "BizKey").getMessage(info);
		this.writeAjaxRtn(result, null, info, response);
		return null;
	}
	
}
