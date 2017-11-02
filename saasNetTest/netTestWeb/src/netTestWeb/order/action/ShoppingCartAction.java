
package netTestWeb.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netTest.order.logic.OrderproductLogic;
import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.exception.LogicException;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.shop.vo.Shopext;
import netTest.bean.BOFactory;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.order.form.ShoppingCartForm;


public class ShoppingCartAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShoppingCartAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("shoppingcartList".equals(myaction)) {
			myforward = shoppingcartList(mapping, actionform, request,response);
		} else if ("addProdToCart".equals(myaction)) {
			myforward = addProdToCart(mapping, actionform, request,response);
		} else if ("delProdFromCart".equals(myaction)) {
			myforward = delProdFromCart(mapping, actionform, request,response);
		} else if ("cancelShoppingOrder".equals(myaction)) {
			myforward = cancelShoppingOrder(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method shoppingcartList
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward shoppingcartList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ShoppingCartForm theForm = (ShoppingCartForm)form;
		HttpSession session = this.getSession(request);
		Custorder custorder = null;
		if(session.getAttribute(KeyInMemoryConstant.CustorderKey)!=null){
			custorder = (Custorder)session.getAttribute(KeyInMemoryConstant.CustorderKey);
		}else {
			custorder = theForm.getCustorder();
		}
		theForm.setCustorder(custorder);
		if(custorder!=null){
		   Long ordershopid = null;
		   String shopname = null;
		   for(Orderproduct temvo : custorder.getOrderProdList()){
			   if(ordershopid==null){
				   ordershopid = temvo.getShopid();
				   shopname = temvo.getShopname();
			   }else if(!ordershopid.equals(temvo.getShopid())){
				   log.error("netTest.ShoppingCartAction.addProdToCart.errors.AllProdNotInOneShop, userid:"+custorder.getUserid());
				   throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
			   }
		   }
		   theForm.setProdList(custorder.getOrderProdList());
		   // 设置商店支付信息
		   Shopext shopext = BOFactory_Platform.getShopextDao().selectByPK(ordershopid);
		   if(shopext!=null){
			  theForm.setPayinfo(shopext.getPayinfo());
			  theForm.setShopname(shopname);
		   }
		}
		
        return mapping.findForward("shopping_cart");
	}
	
	/** 
	 * Method addProdToCart
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward addProdToCart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ShoppingCartForm theForm = (ShoppingCartForm) form;
		Long userid = getLoginInfo().getUserid();
		Long orderprodid = theForm.getOrderprodid();
		HttpSession session = this.getSession(request);
		Custorder custorder = null;
		if(session.getAttribute(KeyInMemoryConstant.CustorderKey)!=null)
			custorder = (Custorder)session.getAttribute(KeyInMemoryConstant.CustorderKey);
		else
			custorder = theForm.getCustorder();

		//过滤用户已经在使用的产品(userproduct)，记录下这些用过产品的名称
	    // 过滤用户已经在订单中订购过的产品(这些订单还未处理)，并且记录下这些存在的产品的名称
	    // 处理所选产品中可以试用的产品，并设置这些产品的“产品试用状态”
		OrderproductLogic orderprodLogic = BOFactory.getOrderproductLogic();
		int filterRtn = orderprodLogic.filterOrderProd(orderprodid, userid, custorder);
		// 组装错误消息
		String messCode = "netTest.ShoppingCartAction.addProdToCart.succeed";
	    if(filterRtn==0){
			// 新增所选订单条目集合
	    	orderprodLogic.addProdToOrder(custorder, orderprodid);
	    	theForm.setCustorder(custorder);
			session.setAttribute(KeyInMemoryConstant.CustorderKey, custorder);
	    }else if(filterRtn==1){
			messCode = "netTest.ShoppingCartAction.addProdToCart.errors.alreadyInCart";
		}else if(filterRtn==2){
		    messCode = "netTest.ShoppingCartAction.addProdToCart.errors.usingProds";
		}else if(filterRtn==3){
		    messCode = "netTest.ShoppingCartAction.addProdToCart.errors.inOrders";
		}else if(filterRtn==4){
		    messCode = "netTest.ShoppingCartAction.addProdToCart.errors.AllProdNotInOneShop";
		}
		this.setMessagePage(request, theForm, messCode, null, null);
		return this.shoppingcartList(mapping, theForm, request, response);
	}
	
	/** 
	 * Method delProdFromCart
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward delProdFromCart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ShoppingCartForm theForm = (ShoppingCartForm) form;
		Orderproduct orderprod = theForm.getOrderprodVO();
		HttpSession session = this.getSession(request);
		Custorder custorder = null;
		if(session.getAttribute(KeyInMemoryConstant.CustorderKey)!=null){
			custorder = (Custorder)session.getAttribute(KeyInMemoryConstant.CustorderKey);
		}else {
			custorder = theForm.getCustorder();
		}
		OrderproductLogic orderprodLogic = BOFactory.getOrderproductLogic();
		custorder = orderprodLogic.removeProdFromOrder(custorder, orderprod);
		if(custorder.getOrderProdList()==null || custorder.getOrderProdList().size()<1){
			custorder = null;
			session.removeAttribute(KeyInMemoryConstant.CustorderKey);
		}else {
			session.setAttribute(KeyInMemoryConstant.CustorderKey, custorder);
		}
		theForm.setCustorder(custorder);
		
		return this.shoppingcartList(mapping, theForm, request, response);
	}
	
	/** 
	 * 删除在session中的购物车订单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward cancelShoppingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ShoppingCartForm theForm = (ShoppingCartForm) form;
		HttpSession session = this.getSession(request);
		if(session.getAttribute(KeyInMemoryConstant.CustorderKey)!=null)
			session.removeAttribute(KeyInMemoryConstant.CustorderKey);
		String messCode = "netTest.ShoppingCartAction.cancelShoppingOrder.successfully";
		this.setMessagePage(request, theForm, messCode, null, null);
		return this.shoppingcartList(mapping, theForm, request, response);
	}
	
}
