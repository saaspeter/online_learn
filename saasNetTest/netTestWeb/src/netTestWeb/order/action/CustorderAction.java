package netTestWeb.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netTest.order.constant.OrderConstant;
import netTest.order.dao.CustorderDao;
import netTest.order.dto.CustorderQuery;
import netTest.order.logic.OrderLogic;
import netTest.order.vo.Custorder;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.ShopDao;
import platform.dao.UsershopDao;
import platform.logicImpl.BOFactory_Platform;
import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import platform.vo.Shop;
import platform.vo.Usershop;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.order.form.CustorderForm;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;


public class CustorderAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CustorderAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		CustorderForm theForm = (CustorderForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listOrderMag".equals(myaction)) {
			// listOrderMag在后端的平台系统中使用
			theForm.setListType(1);
			myforward = list(mapping, actionform, request,response);
		} else if ("myOrderlist".equals(myaction)) {
			theForm.setListType(2);
			myforward = list(mapping, actionform, request,response);
		} else if ("shopOrderlist".equals(myaction)) {
			theForm.setListType(3);
			myforward = list(mapping, actionform, request,response);
		} else if ("viewOrderUser".equals(myaction)) {
			myforward = viewOrder(mapping, actionform, request,response);
		} else if ("viewOrderMag".equals(myaction)) {
			myforward = viewOrder(mapping, actionform, request,response);
		} else if ("geneOrder".equals(myaction)) {
			myforward = geneOrder(mapping, actionform, request,response);
		} else if ("cancelOrder".equals(myaction)) {
			myforward = cancelOrder(mapping, actionform, request,response);
		} else if ("editCustNotes".equals(myaction)) {
			myforward = updateCustNotes(mapping, actionform, request,response);
		} else if ("toApproveOrderPage".equals(myaction)) {
			myforward = toApprovePage(mapping, actionform, request,response);
		} else if ("approveOrder".equals(myaction)) {
			myforward = approveCustOrder(mapping, actionform, request,response);
		} else { 
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * 
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CustorderForm theForm = (CustorderForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long userid = loginfo.getUserid();
		Long shopid = loginfo.getShopid();
		int listType = theForm.getListType();
		CustorderQuery queryVO = theForm.getQueryVO();
		String url = "listOrder";
		if(listType==1){

		} else if(listType==2){
			queryVO.setUserid(userid);
		} else if(listType==3){
			queryVO.setShopid(shopid);
		}

		CustorderDao dao = BOFactory.getCustorderDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward(url);
	}
		
	/** 
	 * 查看订单
	 * voter数据检查:orderid是否是用户的
	 */
	private ActionForward viewOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CustorderForm theForm = (CustorderForm) form;
		CustorderQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getOrderid();
		OrderLogic logic = BOFactory.getOrderLogic();
		Custorder vo = logic.qryCustorder(pk);
	    AssertUtil.assertNotNull(vo, null);
		//
		ShopDao shopdao = platform.logicImpl.BOFactory_Platform.getShopDao();
		Shop shopvo = shopdao.selectByPK(vo.getShopid(), null);
		vo.setShopname(shopvo.getShopname());
		String displayname = BOFactory_Platform.getUsershopLogic().qryUsernameById(vo.getShopid(), vo.getUserid());
		vo.setUserdisplayname(displayname);
		
		theForm.setVo(vo);
  	    return mapping.findForward("viewOrder");
	}
	
	/** 
	 * 当订单在db中，还未被处理时可以取消该订单，如果已经处理了订单这不可以取消
	 * voter数据检查:orderid是否是用户的
	 */
	private ActionForward cancelOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CustorderForm theForm = (CustorderForm) form;
		Custorder order = theForm.getVo();
		Long orderid = order.getOrderid();
		int result = CommonConstant.success;
        String message = "";
		try {
			OrderLogic logic = BOFactory.getOrderLogic();
			result = logic.cancelOrder(orderid);
		} catch (Exception e) {
			log.error("oops, got an exception: ", e);
			result = CommonConstant.fail;
			message = e.getMessage();
		}

		this.writeAjaxRtn(String.valueOf(result), message, null, response);
		return null;
	}
	
	/** 
	 * Method geneOrder
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward geneOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Long userid = getLoginInfo().getUserid();
		CustorderForm theForm = (CustorderForm)form;
		String notes = theForm.getNotes();
		HttpSession session = this.getSession(request);
		Custorder custorder = null;
		if(session.getAttribute(KeyInMemoryConstant.CustorderKey)!=null)
			custorder = (Custorder)session.getAttribute(KeyInMemoryConstant.CustorderKey);
        if(custorder==null||custorder.getOrderProdList()==null||custorder.getOrderProdList().size()<1){
			return this.forwardErrorPage(mapping, request, null, "platformWeb.CustorderAction.geneOrder.errors.NoProductInOrder");
        }
        custorder.setOrderid(null);
        custorder.setNotes(notes);
		custorder.setUserid(userid);
		OrderLogic logic = BOFactory.getOrderLogic();
		// 该用户订购产品的约束判断已经在用户选购产品的购物车中实现。每个用户帐号不能同时有两个session，所以在此可以不用判断该逻辑
		custorder = logic.submitOrder(custorder);

		// 清除session中的订单
		if(session.getAttribute(KeyInMemoryConstant.CustorderKey)!=null){
		   session.setAttribute(KeyInMemoryConstant.CustorderKey,null);
		}
		theForm.setVo(custorder);
		return mapping.findForward("successfulTipPage");
	}
	
	/** 
	 * 编辑订单备注
	 * voter数据检查:orderid是否是用户自己的
	 */
	private ActionForward updateCustNotes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CustorderForm theForm = (CustorderForm) form;
		Custorder vo = theForm.getVo();
		vo.setAppnotes(null);
		String result = String.valueOf(CommonConstant.success);
		String messCode = "CommonSystem.commonAction.operation.succeed";
		try {
			CustorderDao dao = BOFactory.getCustorderDao();
			dao.updateOrderExt(vo);
		} catch (RuntimeException e) {
			result = String.valueOf(CommonConstant.fail);
			messCode = "CommonSystem.commonAction.operation.failed";
			log.error(e);
		}
		
		String message = getResources(request).getMessage(messCode);
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
		
	/**
	 * 转向审批订单页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ActionForward toApprovePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CustorderForm theForm = (CustorderForm) form;
		Custorder vo = theForm.getVo();
		LoginInfoEdu loginfo = getLoginInfo();
		
		CustorderDao orderDao = BOFactory.getCustorderDao();
		vo = orderDao.selectByPK(vo.getOrderid());
		if(vo==null){
			throw new NoSuchRecordException("--orderID:"+vo.getOrderid());
		}
	    UsershopDao usershopDao = platform.logicImpl.BOFactory_Platform.getUsershopDao();
	    Usershop usershopVO = usershopDao.selectByLogicPK(vo.getShopid(), vo.getUserid());
		theForm.setUsershopVO(usershopVO);
		
		if(usershopVO==null){
			String orgname = loginfo.getOrgname();
			Long defaultOrgid = loginfo.getOrgbaseid();
			theForm.setOrgid(defaultOrgid);
			theForm.setOrgname(orgname);
		}
		return mapping.findForward("approvePage");
	}
	
	/** 
	 * 商店管理员审批客户的订单
	 */
	private ActionForward approveCustOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		CustorderForm theForm = (CustorderForm) form;
		Long userid = getLoginInfo().getUserid();
		Custorder vo = theForm.getVo();
		String nickname = theForm.getNickname();
		AssertUtil.assertNotNull(vo.getOrderid(), null);
		AssertUtil.assertNotNull(vo.getOrderstatus(), null);
		Long[] orderIdArray = new Long[]{vo.getOrderid()};
		String messCode = "netTest.CustorderAction.approveCustOrder.approve";
		OrderLogic logic = BOFactory.getOrderLogic();
		logic.approveCustOrder(userid, orderIdArray, vo.getOrderstatus(), 
						       nickname, vo.getAppnotes());
	
		if(OrderConstant.OrderStatus_deny.equals(vo.getOrderstatus())){
			messCode = "netTest.CustorderAction.approveCustOrder.deny";
		}
		request.setAttribute("pageAction", "closeDiv");
		this.setMessagePage(request, theForm, messCode, null, "BizKey");
		return mapping.findForward("toUrl");
	}
	
}
