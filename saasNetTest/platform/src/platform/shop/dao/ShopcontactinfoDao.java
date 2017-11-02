package platform.shop.dao;

import java.util.List;
import platform.shop.vo.Shopcontactinfo;

public interface ShopcontactinfoDao {
   
    /**
     * select some record by PK
     */
    public Shopcontactinfo selectByPK(Long pk) ;
    
    /**
     * select some record by Userid
     */
    public Shopcontactinfo selectDefaultByShopid(Long shopid) ;
    
       
    /**
     * select records by queryVO
     */
    public List selectByVO(Long shopid, Long localeid) ;
    
    /**
     * 查询商店已经存在的regionCode
     * @param shopid
     * @param localeid
     * @return
     */
    public String[] selectExistRegionCode(Long shopid, Long localeid);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Shopcontactinfo record) ;

    /**
     * update a record By PK
     */
    public int updateByPK(Shopcontactinfo record) ;

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Shopcontactinfo save(Shopcontactinfo record) ;

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) ;
    
    /**
     * delete a record by userID
     */
    public int deleteByShopID(Long shopid) ;

}