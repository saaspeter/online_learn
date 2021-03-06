package netTest.order.dao;

import java.util.List;

import netTest.order.dto.OrderproductQuery;
import netTest.order.vo.Orderproduct;


import commonTool.base.Page;

public interface OrderproductDao {
   
    /**
     * select some record by PK
     */
    public Orderproduct selectByPK(Long orderid, Long productbaseid);
            
    /**
     * select records by queryVO
     */
    public List<Orderproduct> selectByVO(OrderproductQuery queryVO);
    
    /**
     * 根据订单id查询该订单的所有订单产品
     * @param orderid
     * @return
     * @
     */
    public List<Orderproduct> selectByOrderid(Long orderid);
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(OrderproductQuery queryVO,int pageIndex,int pageSize,Integer total);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Orderproduct record);

    /**
     * update a record By PK
     */
    public int updateByPK(Orderproduct record);
    
    /**
     * 更新未经处理的订单产品的状态
     */
    public int updateUnhandleStatusByOrder(Long orderid, Short orderstatus);

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Orderproduct save(Orderproduct record);

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long orderid, Long productbaseid);
    
    /**
     * 根据订单id删除订单条目
     * @param orderid
     * @return
     * @
     */
    public int deleteByOrderid(Long orderid);
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list);
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray);
    
    /**
     * 查询是否有用户订购的在某个状态的产品
     * @param userid
     * @param prodid
     * @param status
     * @return
     */
    public boolean existUnhandleOrderProd(Long userid, Long prodid, Short status);
	
}
