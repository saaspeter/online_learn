package netTest.order.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.event.EventHandlerNetTest;
import netTest.exception.ExceptionConstant;
import netTest.order.constant.OrderConstant;
import netTest.order.dao.CustorderDao;
import netTest.order.dao.OrderproductDao;
import netTest.order.logic.OrderLogic;
import netTest.order.logic.OrderproductLogic;
import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;
import netTest.product.dao.UserprodbuylogDao;
import netTest.product.logic.UserprodstatuslogLogic;

import org.apache.log4j.Logger;

import platform.constant.SysfunctionitemConstant;
import platform.constant.UsershopConstant;
import platform.logic.UsershopLogic;
import platform.logicImpl.UsershopLogicImpl;
import platform.vo.Shopfunc;
import platform.vo.Usershop;

import commonTool.constant.CommonConstant;
import commonTool.constant.PayTypeConstant;
import commonTool.constant.UsernotificationConstant;
import commonTool.event.Event;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.DateUtil;

public class OrderLogicImpl implements OrderLogic{

	static Logger log = Logger.getLogger(OrderLogicImpl.class.getName());
	private CustorderDao orderDao;
	private OrderproductDao orderprodDao;
	private UserprodbuylogDao buylogDao; 
	private OrderproductLogic orderprodLogic;
	private UserprodstatuslogLogic statusloglogic;
	
	/**
	 * 根据订单id查询完整的订单信息
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	public Custorder qryCustorder(Long orderid) throws Exception{
		Custorder order = null;
		order = orderDao.qryWholeByPK(orderid);
		List<Orderproduct> prodList = orderprodDao.selectByOrderid(orderid);
		if(order!=null){
			order.setOrderProdList(prodList);
		}
		return order;
	}
	
	/**
	 * 根据订单条目生成客户订单，并且统计订单金额相关信息
	 * @param order
	 * @return
	 */
	private Custorder initOrder(Custorder order){
		if(order==null||order.getOrderProdList()==null||order.getOrderProdList().size()<1)
			return order;
		Date approvedate = DateUtil.getInstance().getNowtime_GLNZ();
		// 初始化客户订单相关字段，不包括订单编号的生成
        order.setOrdertype(OrderConstant.OrderType_infoProd);
        order.setOrdertime(approvedate);
//        order.setPaystatus(OrderConstant.PayStatus_notPay);
        order.setOrderstatus(OrderConstant.OrderStatus_submit);
		
		// 初始化订单条目字段，并且统计订单中相关金额
        double cost = 0d;
		List<Orderproduct> list = order.getOrderProdList();
		Orderproduct prodTemp = null;
		if(list!=null&&list.size()>0)
		for(int i=0;i<list.size();i++){
			prodTemp = (Orderproduct)list.get(i);
			if(prodTemp!=null){
				// 设置订购人
				prodTemp.setUserid(order.getUserid());
				prodTemp.setQuantity(new Integer(1));
				prodTemp.setStatus(order.getOrderstatus());
			// 统计订单条目的订单额度，并且更改Custorder中的相关订单金额的字段
				if(prodTemp.getQuantity()!=null){
					if(prodTemp.getProductprice()==null)
						prodTemp.setProductprice(0d);
					cost += prodTemp.getQuantity().intValue()*prodTemp.getProductprice().doubleValue();
				}
			}
		}
		order.setFullcost(cost);
		return order;
	}
	
	/**
	 * 生成并插入客户订单
	 * 如果是则判断该产品用户是否已经试用过，如果试用过则需要全额购买（记录字段tryUseStatus为3）
	 * 这里订单中的产品具体信息是由session中传入的，因此不需要查询产品再次设置，同时也保证了用户在界面上看到的产品的信息和计入订单产品的信息一致
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Custorder submitOrder(Custorder order) throws Exception{
		if(order==null)
			return null;
		// 初始化字段信息，并统计金额订单
		order = this.initOrder(order);

		// 订单编号
		String orderCode = orderDao.geneOrderCode();
		order.setOrdercode(orderCode);
		Long orderid = orderDao.insert(order);
		order.setOrderid(orderid);
		// 设置订单名称
		order.setOrdername(orderCode);
		// 查询order产品所在的商店是否设置了自动审批, 并且用户需要已经在商店之中
		boolean autoapprove = false;
		if(order.getShopid()!=null){
			Shopfunc funcsetOrderVO = platform.logicImpl.BOFactory_Platform.getShopfuncDao()
			                            .selectByLogicPK(order.getShopid(), SysfunctionitemConstant.Edu_OrderAutoApprove);
		    if(funcsetOrderVO!=null){
		    	Usershop usershopVO = platform.logicImpl.BOFactory_Platform.getUsershopDao()
		    	                        .selectByLogicPK(order.getShopid(), order.getUserid());
		    	if(usershopVO!=null && UsershopConstant.UserShopStatus_active.equals(usershopVO.getUsershopstatus())){
		    		autoapprove = true;
		    	}
		    }
		}
		//
		List<Orderproduct> list = order.getOrderProdList();
		List<Orderproduct> approveList = new ArrayList<Orderproduct>();
		Orderproduct itemTemp = null;
		if(list!=null&&list.size()>0)
		for(int i=0;i<list.size();i++){  // 初始化订单产品
		   itemTemp = (Orderproduct)list.get(i);
		   if(itemTemp!=null){
		   // 设置订单id和订单编号
			   itemTemp.setOrderid(orderid);
			   itemTemp.setOrdercode(orderCode);
			   // 如果是费产品或商店是自动审批状态
			   if(PayTypeConstant.PayType_free.equals(itemTemp.getPaytype())
					  ||autoapprove){
			      approveList.add(itemTemp);
			   }
		   }
		}
		orderprodDao.insertBatch(list);
		// 如果有可以立即审批的产品则处理
		if(approveList.size()>0){
			// 在此开通该产品的服务
			orderprodLogic.approveOrderProds(orderid, approveList, OrderConstant.OrderStatus_approve, order.getOrdertime(), CommonConstant.NullPK_Parameter);
			// 如果所有产品都已审批则修改订单状态为结束
			if(approveList.size()==list.size()){
				orderDao.updateOrderStatus(orderid, OrderConstant.OrderStatus_approve, order.getOrdertime(), null, new Long("-1"));
				order.setOrderstatus(OrderConstant.OrderStatus_approve);
			}
			// 如果用户还不在商店中，且指定了单位，则把用户加入商店
			getUsershopLogic().putUserIntoShop(order.getShopid(), order.getUserid(), null, UsershopConstant.Usershoptype_Productuser);
		}
		return order;
	}
	
	/**
	 * 取消订单，前提是该订单还没有被处理
	 * @param orderid
	 * @return 1:取消成功，2:订单已经处理过，不能删除
	 * @throws Exception
	 */
	public int cancelOrder(Long orderid) {
		if(orderid==null)
			throw new NeedParamsException();
		// 删除订单条目信息
		Custorder vo = orderDao.selectByPK(orderid);
		if(vo==null)
			throw new NoSuchRecordException();
		int result = 1;
		if(OrderConstant.OrderStatus_submit.equals(vo.getOrderstatus())){
		   orderprodDao.deleteByOrderid(orderid);
		   orderDao.deleteByPK(orderid);
		}else{
			result = 2;
		}
		return result;
	}
		
	/**
	 * 商店管理员审批订单，并且逐一处理订单条目，并提供产品服务给订单条目
	 * 如果其中任何一步出错，系统都要回滚
	 * @param orderidArr: 订单id数组
	 * @param orderStatus
	 * @param orgid  把用户加入到指定的单位中
	 * @return 订单的处理状态
	 */
	public int approveCustOrder(Long approveUserid, Long[] orderidArr, 
			Short orderStatus, String nickname, String appnotes) throws Exception {
		if(approveUserid==null||orderidArr==null||orderidArr.length<1
				||orderStatus==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		if(!OrderConstant.OrderStatus_approve.equals(orderStatus)
				&&!OrderConstant.OrderStatus_deny.equals(orderStatus))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		int nums = 0;
		Date approvedate = DateUtil.getInstance().getNowtime_GLNZ();
		Custorder orderVO = null;
		// 需要发出的event
		List eventList = new ArrayList();
		String ordernamecode = null;
		String notifyMess_code = null;
		String messcode = null;
		// String eventContent = null;
		for(int i=0; i<orderidArr.length; i++){
			orderVO = orderDao.selectByPK(orderidArr[i]);
			//TODO 所有的订单的产品都必须来源于一家商店，需要做安全性检查
			if(i==0){ 
				if(orderVO!=null){ // 把用户加入商店
					getUsershopLogic().putUserIntoShop(orderVO.getShopid(), orderVO.getUserid(), nickname, UsershopConstant.Usershoptype_Productuser);
				}
			}
			// 审批订单产品，并更改起状态
			orderprodLogic.approveOrderProds(orderidArr[i],null,orderStatus,approvedate,approveUserid);
			if(OrderConstant.OrderStatus_approve.equals(orderStatus)){
				messcode = UsernotificationConstant.Messcode_CustOrder_ApprovePass;
			}else {
				messcode = UsernotificationConstant.Messcode_CustOrder_ApproveDeny;
			}
			// 更新订单状态
			orderDao.updateOrderStatus(orderidArr[i], orderStatus, approvedate, appnotes, approveUserid);
			// 添加event
			ordernamecode = orderVO.getOrdercode();
			Map<String, String> subeventMap = new HashMap<String, String>();
			subeventMap.put("touserid", orderVO.getUserid().toString());
			subeventMap.put("notifytype", UsernotificationConstant.NotifyType_CustOrder.toString());
			subeventMap.put("messcode", messcode);
			subeventMap.put("creatorid", UsernotificationConstant.CreatorID_System.toString());
			// 仅仅存储code, 具体信息在struts资源文件中，由struts标签来处理
			subeventMap.put("content", notifyMess_code);
			subeventMap.put("objectname", ordernamecode);
			subeventMap.put("linkurl", UsernotificationConstant.getNotifyUrl(messcode)+orderVO.getOrderid());
			subeventMap.put("openlinktype", UsernotificationConstant.OpenlinkType_DirectGOTO.toString());
			eventList.add(subeventMap);
		}
		// 发送event
		Map paraMap = new HashMap();
		paraMap.put(EventHandlerNetTest.Parameter_BatchList, eventList);
		Event event = new Event(EventHandlerNetTest.EventType_Usernotification_UserMessage, paraMap);
		EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
		
		nums = orderidArr.length;
		return nums;
	}
	
	// 检查用户是否在shop中，如果不在则将用户加入商店，并且将用户加入商店
//	private void addUserIntoShopAndIntoDept(Long userid, Long shopid, String nickname){
//        // 检查用户在usershop中是否存在该系统的记录，如果不存在，则将用户加入该shop
//		int rtn = getUsershopLogic().putUserIntoShop(shopid, userid, nickname, UsershopConstant.Usershoptype_Productuser);
//        // put Into org功能暂时不做，在审批订单时：不能指定是否将用户加入本单位中了，页面已经没这个功能了
//		if(rtn==1 && orgid!=null){
//			OrguserLogicImpl.getInstance().putUserIntoOrg(shopid, userid, orgid);
//		}
//	}
		
	/**
     * static spring getMethod
     */
     public static OrderLogic getInstance() {
       	 OrderLogic logic = (OrderLogic)BeanFactory.getBeanFactory().getBean("orderLogic");
         return logic;
     }
	
	public CustorderDao getOrderDao() {
		return orderDao;
	}
	
	public void setOrderDao(CustorderDao orderDao) {
		this.orderDao = orderDao;
	}

	public UserprodbuylogDao getBuylogDao() {
		return buylogDao;
	}

	public void setBuylogDao(UserprodbuylogDao buylogDao) {
		this.buylogDao = buylogDao;
	}

	public UserprodstatuslogLogic getStatusloglogic() {
		return statusloglogic;
	}

	public void setStatusloglogic(UserprodstatuslogLogic statusloglogic) {
		this.statusloglogic = statusloglogic;
	}

	public OrderproductDao getOrderprodDao() {
		return orderprodDao;
	}

	public void setOrderprodDao(OrderproductDao orderprodDao) {
		this.orderprodDao = orderprodDao;
	}

	public OrderproductLogic getOrderprodLogic() {
		return orderprodLogic;
	}

	public void setOrderprodLogic(OrderproductLogic orderprodLogic) {
		this.orderprodLogic = orderprodLogic;
	}

	public UsershopLogic getUsershopLogic() {
		return UsershopLogicImpl.getInstance();
	}
	
}
