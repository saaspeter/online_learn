package platform.dao;

import java.util.List;

import platform.dto.ShopvalueQuery;
import platform.vo.Shopvalue;

public interface ShopvalueDao {
	
	public Shopvalue selectByPK(Long pk);
	
    /**
     * 检查是否有重名的商店
     * @return true:有重名商店, false:没有重名商店
     */
    public boolean existcheckByname(String shopname);
   
    /**
     * select some record by PK
     */
    public Shopvalue selectByPK(Long pk, Long localeid) throws Exception ;
    
    /**
     * select records by queryVO
     */
    public List selectByVO(ShopvalueQuery queryVO) throws Exception ;
    
    /**
     * select records withoutBlobs by queryVO
     */
    public List selectByVOWithoutBLOBs(ShopvalueQuery queryVO) throws Exception ;

    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Shopvalue record) throws Exception ;

    /**
     * update a record By PK
     */
    public int updateShopnameByPK(Shopvalue record) throws Exception;

    /**
     * update a record By PK
     */
    public int updateShopDescByPK(Shopvalue record) throws Exception;

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) throws Exception ;
    
    /**
     * delete records by queryVO
     */
    public int deleteByVO(ShopvalueQuery queryVO) throws Exception ;

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
	
}
