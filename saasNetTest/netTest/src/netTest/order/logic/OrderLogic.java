package netTest.order.logic;

import netTest.order.vo.Custorder;

public interface OrderLogic {

	/**
	 * 根据订单id查询完整的订单信息
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	public Custorder qryCustorder(Long orderid) throws Exception;
	
	/**
	 * 生成并插入客户订单
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Custorder submitOrder(Custorder order) throws Exception;
	
	/**
	 * 取消订单，前提是该订单还没有被处理
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	public int cancelOrder(Long orderid);
	
	/**
	 * 商店管理员审批订单，并且逐一处理订单条目，并提供产品服务给订单条目
	 * 如果其中任何一步出错，系统都要回滚
	 * @param userid: 会把用户放入到orgid中 
	 * @param orderidArr
	 * @param orderStatus
	 * @param orgid
	 * @return 订单的处理状态
	 */
	public int approveCustOrder(Long userid, Long[] orderidArr, 
			Short orderStatus, String nickname, String appnotes) throws Exception;

	
}
