package netTest.product.logic;

import netTest.order.vo.Orderproduct;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userproduct;
import commonTool.base.Page;

public interface UserproductLogic {
	
	public Page listMyProduct(Long userid, Long shopid, Short status,
			  				  int pageIndex,int pageSize,Integer total);
	
	/**
	 * 列出userproduct记录，包括user的信息
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page listUserProduct(Long productid, Long userid, Short produsetype, 
			Long shopid, Short status, String orderByStr,
			int pageIndex,int pageSize,Integer total);
	
	/**
	 * 根据订单条目处理用户购买培训类产品的信息.
	 * 生成用户产品记录；并记录用户产品状态日志表；记录用户产品使用时间日志表；
	 * 如果是培训产品则把用户加入商店并把人员加入相关的商店学习单位中
	 * 当用户支付订单条目中不需要审批的培训类产品时调用该方法；当商店管理员审批通过用户购买培训类产品订单条目时调用该方法；
	 * @param itemVO
	 * @return 新生成的用户产品记录，如果为null，说明新增失败
	 * @throws Exception
	 */
	public Userproduct deliverProdToUsr(Orderproduct orderprodVO, Long opertorid) throws Exception;
	
	public Userproduct addUserprodFromEdu_single(Long productbaseid,Long userid,Long shopid,Short produsetype, Long opertorid);
	
	/**
	 * 在教育系统中为用户添加课程。生成类型是“从子系统”，使用类型是“免费使用”
	 * 如果该产品已经存在则不会添加该产品，不报错
	 * @param productidStr
	 * @param useridArr
	 * @param shopid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean addUserprodFromEdu(Long productid,String[] useridArr,Long shopid,Short status, Long opertorid);
	
    
    /**
     * 检查用户是否有被分配的产品关联，主要用于删除用户时的检查
     * @param userids
     * @param shopid
     * @return -1缺少参数 0没有引用 1有产品引用
     */
	public String checkUserProdLink(Long userid, Long shopid);
	
	/**
	 * 查询用户还没有购买的产品的具体信息，如果用户有了这个产品则抛出异常。用于给用户添加产品时dwr调用
	 * @param userid
	 * @param productid
	 * @return
	 */
	public Productbase getNonUserProduct(Long userid, Long productid, Long localeid);
	
	/**
	 * 检查userproduct的状态，如果可以删除，则删除userproduct, 否则抛出错误异常
	 * @param userproductid
	 * @param operatorid
	 */
	public void checkAndDelUserproduct(Long userproductid, Long operatorid);
	
	/**
     * 更新产品使用方式和状态
     * @param pk
     * @param produsetype
     */
    public int updUsetypeStatusByPK(Long userproductid, Short produsetype,
    		      Short status, Long opertorid, String statusdesc);
    
	/**
	 * 根据用户信息返回用户可以使用的产品字符串数组
	 * 用于向用户sessionInfo里写可用的产品字符串
	 * @param userproductList
	 * @return
	 */
	public String[] getUserprodStr(Long userid, Long shopid);
	
	/**
     * get user learning and manage product number
     */
    public int[] getUserprodNumber(Long userid, Long shopid);
	
	/**
	 * 查找快到期的用户产品记录，并生成userNotification
	 * 查找已经过期的用户产品记录，并删除userproduct，并且记录日志
	 */
	public void notifyDueUserProduct();
	
	
}
