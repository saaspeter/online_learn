package netTest.order.logic;

import java.util.Date;
import java.util.List;

import netTest.order.dto.OrderproductQuery;
import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;


import commonTool.base.Page;

public interface OrderproductLogic {

	/**
	 * 查询订单条目信息
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryOrderprods(OrderproductQuery queryVO,int pageIndex,int pageSize,Integer total)throws Exception;
	
	/** 返回订单所涉及的产品目录名称字符串
	 * @param orderIdStr: shopid组成的字符串，其中以逗号隔开
	 * @param localeid
	 * @return
	 */
	public String[] getOrderproductNames(String orderIdStr);

    /**
     * 过滤用户已经在使用的产品(userproduct)
     * 过滤用户已经在订单中订购过的产品(这些订单还未处理)
     * @param productbaseid:产品id
     * @param userid:
     * @return 0:可以选购该产品。1:该产品用户已经在使用中了；2:该产品用户已经订购过了，正待处理
     */
	public int filterOrderProd(Long productbaseid, Long userid, Custorder custorder);
	
	/**
	 * 把用户新增的订单条目新增到初始化并加入到购物车中
	 * @param orderid
	 * @param itemList
	 * @return
	 * @throws Exception
	 */
	public Custorder addProdToOrder(Custorder orderVO,Long productid);
	
	/**
	 * 从订单中删除一个课程
	 * @param orderVO
	 * @param voTemp
	 * @return
	 */
    public Custorder removeProdFromOrder(Custorder orderVO, Orderproduct voTemp);
	
	/**
	 * 审批通过用户的请求购买商品。同时处理分配产品给用户，记录日志等
	 * @param itemList
	 * @return
	 */
	public int approveOrderProds(Long orderid,List<Orderproduct> orderprodList, Short orderstatus, Date approvedate,Long opertorid) throws Exception;
	
}
