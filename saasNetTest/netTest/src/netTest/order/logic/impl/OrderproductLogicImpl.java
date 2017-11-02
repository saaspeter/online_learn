package netTest.order.logic.impl;

import java.util.Date;
import java.util.List;

import netTest.bean.BeanFactory;
import netTest.order.constant.OrderConstant;
import netTest.order.dao.CustorderDao;
import netTest.order.dao.OrderproductDao;
import netTest.order.dto.OrderproductQuery;
import netTest.order.logic.OrderproductLogic;
import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;
import netTest.product.dao.ProductbaseDao;
import netTest.product.dao.UserproductDao;
import netTest.product.dao.impl.ProductbaseDaoImpl;
import netTest.product.logic.UserprodstatuslogLogic;
import netTest.product.logic.UserproductLogic;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userproduct;

import org.apache.log4j.Logger;

import platform.dao.ShopDao;
import platform.daoImpl.ShopDaoImpl;
import platform.vo.Shop;

import commonTool.base.Page;
import commonTool.exception.NeedParamsException;
import commonTool.util.StringUtil;

public class OrderproductLogicImpl implements OrderproductLogic{

	static Logger log = Logger.getLogger(OrderproductLogicImpl.class.getName());
	private CustorderDao orderDao;
	private OrderproductDao orderprodDao;
	private UserproductDao usrproDao;
	private UserproductLogic usrprologic;
	private UserprodstatuslogLogic statusloglogic;

	
	/**
	 * 查询订单产品信息
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryOrderprods(OrderproductQuery queryVO,int pageIndex,int pageSize,Integer total){
		if(queryVO==null)
			queryVO = new OrderproductQuery();
		Page page = Page.EMPTY_PAGE;

		page = orderprodDao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		List listTemp = page.getList();
		// 设置订单应该支付的数额
		if(listTemp!=null&&listTemp.size()>0){
		  Orderproduct prodVO = null;
		  int quentity = 1;
		  for(int i=0;i<listTemp.size();i++){
			  prodVO = (Orderproduct)listTemp.get(i);
			  if(prodVO!=null&&(prodVO.getCost()==null||prodVO.getCost().doubleValue()<=0d)){
				  if(prodVO.getQuantity()==null)
					  quentity = 1;
				  prodVO.setCost(quentity*prodVO.getProductprice());
			  }
		  }
		}
		
		return page;
	}
	
	/**
	 * 返回订单所涉及的产品目录名称字符串
	 * @param orderIdStr: shopid组成的字符串，其中以逗号隔开
	 * @param localeid
	 * @return
	 */
	public String[] getOrderproductNames(String orderIdStr){
		if(orderIdStr==null||orderIdStr.trim().length()<1)
			return null;
		String[] orderIdArr = StringUtil.splitString(orderIdStr, ",");
		String[] rtnArr = new String[orderIdArr.length];
		for(int i=0; i<orderIdArr.length; i++){
			if(orderIdArr[i]==null||orderIdArr[i].trim().length()<1){
				continue;
			}
			StringBuffer buffer = new StringBuffer();
			List<Orderproduct> list = orderprodDao.selectByOrderid(new Long(orderIdArr[i]));
			if(list!=null&&list.size()>0){
				Orderproduct tempVO = null;
				for(int j=0; j<list.size(); j++){
					tempVO = list.get(j);
					if(tempVO!=null){
						buffer.append(tempVO.getProductname()).append(", ");
					}
				}
			}
			rtnArr[i] = StringUtil.trimComma(buffer.toString());
		}
		return rtnArr;
	}
				
	/**
	 * 判断用户选定的产品是否是用户已经正在使用的产品，或者在使用但欠费的产品
	 * @param list
	 * @param userid
	 * @return true:已经在使用  false:没有在使用
	 * @throws Exception
	 */
	private boolean IsUserProds(Long productbaseid, Long userid) {
		if(productbaseid==null||userid==null)
			return false;
		boolean result = false;
		Userproduct userprodVO = usrproDao.selectByLogicPk(userid, productbaseid);
		if(userprodVO!=null){
           result = true;
		}
		return result;
	}
	
	
    /**
     * 过滤用户已经在使用的产品(userproduct)
     * 过滤用户已经在订单中订购过的产品(这些订单还未处理)
     * @param productbaseid:产品id
     * @param userid:
     * @return 0:可以选购该产品。1:用户购物车中存在该课程；2:该产品用户已经在使用中了；3:该产品用户已经订购过了，正待处理
     */
	public int filterOrderProd(Long productbaseid, Long userid, Custorder custorder){
        if(productbaseid==null||userid==null)
        	return 0;
		int ret = 0;
		// 过滤购物车中已有的产品
		if(custorder!=null&&custorder.getOrderProdList()!=null){
			List orderprodList = custorder.getOrderProdList();
			Orderproduct prodTemp = null;
			for(int i=0;i<orderprodList.size();i++){
				prodTemp = (Orderproduct)orderprodList.get(i);
				if(productbaseid.equals(prodTemp.getProductbaseid())){
					ret = 1;
					break;
				}
			}
		}
		if(ret==0){ // 正常购买的产品，需要进一步检查
			// 检查要添加的产品和商品中的其他产品是否是同一个商店的产品
			Productbase prodVO = getProdDao().selectByPK(productbaseid);	
			if(custorder.getShopid()!=null&&!custorder.getShopid().equals(prodVO.getShopid())){
				ret = 4;
				return ret;
			}
			
			// 过滤用户已经在使用的产品(userproduct)，记录下这些用过产品的名称
			boolean retboo1 = this.IsUserProds(productbaseid,userid);
			if(retboo1){
				ret = 2;
			}else{
	        // 过滤用户已经在订单中订购过的产品(这些订单还未处理)
				retboo1 = orderprodDao.existUnhandleOrderProd(userid, productbaseid, OrderConstant.OrderStatus_submit);
				if(retboo1){
				   ret = 3;
				}
			}
		}
        return ret;
	}
		
	/**
	 * 把用户新增的订单条目新增到初始化并加入到购物车中的订单产品列表中
	 * @param orderid
	 * @param itemList
	 * @return
	 * @throws Exception
	 */
	public Custorder addProdToOrder(Custorder orderVO,Long productid) {
		if(orderVO==null||productid==null)
			return orderVO;
		Orderproduct voTemp = new Orderproduct();
		Productbase prodVO = getProdDao().selectByPK(productid);	
		Shop shopVO = getShopdao().selectByPK(prodVO.getShopid(), null);
		voTemp.setProductbaseid(productid);
		voTemp.setProductname(prodVO.getProductname());
		voTemp.setProductprice(prodVO.getProductprice());
		voTemp.setQuantity(1);
		voTemp.setUserid(orderVO.getUserid());
		voTemp.setShopid(prodVO.getShopid());
		voTemp.setShopname(shopVO.getShopname());
		voTemp.setPaytype(prodVO.getPaytype());
		voTemp.setUselimitnum(prodVO.getUselimitnum());
		orderVO.addOrderproduct(voTemp);
        
		if(orderVO.getShopid()==null){
			orderVO.setShopid(shopVO.getShopid());
		}
		return orderVO;
	}
	
	/**
	 * 从订单中删除一个课程
	 * @param orderVO
	 * @param voTemp
	 * @return
	 */
    public Custorder removeProdFromOrder(Custorder orderVO, Orderproduct voTemp){
    	if(orderVO==null||voTemp==null||voTemp.getProductbaseid()==null)
			return orderVO;
    	Productbase prodVO = getProdDao().selectByPK(voTemp.getProductbaseid());	
		voTemp.setProductprice(prodVO.getProductprice());
		voTemp.setQuantity(1);
		orderVO.delOrderproduct(voTemp);
		return orderVO;
    }

	/**
	 * 审批通过用户的请求购买商品。同时处理分配产品给用户，记录日志等
	 * @param itemList
	 * @return
	 * @throws Exception 
	 */
	public int approveOrderProds(Long orderid,List<Orderproduct> orderprodList, 
			Short orderstatus, Date approvedate, Long opertorid) throws Exception 
	{
		if(orderid==null||orderstatus==null)
			return -1;
		int rtn = 1;
		Orderproduct voTemp = null;
		if(orderprodList==null||orderprodList.size()==0){
		   orderprodList = orderprodDao.selectByOrderid(orderid);
		}
		// 对每一个订单条目审批。检查每个订单条目是否审批过，如果没有审批则审批该订单条目。
		if(orderprodList!=null && orderprodList.size()>0){
			if(OrderConstant.OrderStatus_approve.equals(orderstatus)){
				for(int i=0;i<orderprodList.size();i++){
					voTemp = (Orderproduct)orderprodList.get(i);
					if(voTemp!=null)
					{
					    // 根据支付状态和审批状态(如果需要审批)处理用户和产品订单条目的事务
					    rtn = doItemApprove(voTemp,orderstatus,approvedate, opertorid);
					}else {
						rtn = 0;
					}
					if(rtn!=1){
						break;
					}
				}
			}
			// update all order product status
			if(OrderConstant.OrderStatus_approve.equals(orderstatus) 
					|| OrderConstant.OrderStatus_deny.equals(orderstatus)){
				orderprodDao.updateUnhandleStatusByOrder(orderid, orderstatus);
			}
		}

		return rtn;
	}
	
	
	/**
	 * 处理更新订单条目中的审批数据，更新购买日志中的审批信息，分配产品给用户使用
	 * @param vo
	 * @return -1:缺少参数；-2：处理支付时发生错误；-3：分配产品给用户时出错
	 */
	private int doItemApprove(Orderproduct vo,Short orderstatus,Date approvedate, Long opertorid) throws Exception{
		if(vo==null||vo.getProductbaseid()==null)
		   throw new NeedParamsException();
		// 如果订单已经处理过了则返回
		if(!OrderConstant.OrderStatus_submit.equals(vo.getStatus())){
			return 0;
		}
		int rtn = 1;
		// 订单是审核通过
		if(OrderConstant.OrderStatus_approve.equals(orderstatus))
		{
		   Userproduct usrProd = null;
		   // 如果需要则分配产品给用户
		   usrProd = usrprologic.deliverProdToUsr(vo, opertorid);
		   if(usrProd==null)
			   return -3;
		}
		return rtn;
	}
	
	
	/**
     * static spring getMethod
     */
     public static OrderproductLogic getInstance() {
       	 OrderproductLogic logic = (OrderproductLogic)BeanFactory.getBeanFactory().getBean("orderproductLogic");
         return logic;
     }

	public UserproductLogic getUsrprologic() {
		return usrprologic;
	}

	public void setUsrprologic(UserproductLogic usrprologic) {
		this.usrprologic = usrprologic;
	}

	public UserprodstatuslogLogic getStatusloglogic() {
		return statusloglogic;
	}

	public void setStatusloglogic(UserprodstatuslogLogic statusloglogic) {
		this.statusloglogic = statusloglogic;
	}

	public CustorderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(CustorderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderproductDao getOrderprodDao() {
		return orderprodDao;
	}

	public void setOrderprodDao(OrderproductDao orderprodDao) {
		this.orderprodDao = orderprodDao;
	}

	public UserproductDao getUsrproDao() {
		return usrproDao;
	}

	public void setUsrproDao(UserproductDao usrproDao) {
		this.usrproDao = usrproDao;
	}

	public ProductbaseDao getProdDao() {
		return ProductbaseDaoImpl.getInstance();
	}

	public ShopDao getShopdao() {
		return ShopDaoImpl.getInstance();
	}

	
}
