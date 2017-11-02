package platform.dao;

import java.util.List;
import commonTool.base.Page;
import platform.vo.Shopapp;
import platform.dto.ShopappQuery;

public interface ShopappDao {
   
    /**
     * select some record by PK
     */
    public Shopapp selectByPK(Long pk);
    
    /**
     * select all records
     * @return
     */
    public List selectAll() throws Exception ;
        
    /**
     * select records by queryVO
     */
    public List selectByVO(ShopappQuery queryVO) throws Exception ;
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(ShopappQuery queryVO,int pageIndex,int pageSize,Integer total) throws Exception ;
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Shopapp record) throws Exception ;

    /**
     * update a record By PK
     */
    public int updateByPK(Shopapp record) throws Exception ;

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Shopapp save(Shopapp record) throws Exception ;

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) throws Exception ;
    
    /**
     * delete records by queryVO
     */
    public int deleteByVO(ShopappQuery queryVO) throws Exception ;

	/**
     * insertBatch records of List
     */
    public int insertBatch(List list) throws Exception ;
    
    /**
     * updateBatch records of List
     */
    public int updateBatch(List list) throws Exception ;
    
    /**
     * deleteBatch records by the Long Array of PK
     */
    public int deleteBatchByPK(Long[] pkArray) throws Exception ;
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray) throws Exception ;
    
    /**
     * 查询user申请了多少个商店
     * @param userid
     * @return
     */
    public int selectCountByOwner(Long userid, Short appstatus);
	
}