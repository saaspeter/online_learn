package netTest.product.dao;

import java.util.Map;

import netTest.product.vo.Goodproduct;

public interface GoodproductDao {
       
    /**
     * select by pk
     * @param productbaseid
     * @param producttype
     * @param categoryid
     * @param localeid
     * @return
     */
//    public Goodproduct selectByPK(Long productbaseid, Long categoryid);
	
    public Map<Long, Goodproduct> selectByPkStr(String productidStr);
    
    /**
     * 查询系统推荐的产品
     * @param producttype
     * @param categoryid
     * @param localeid
     * @return
     */
    public Map<Long, Goodproduct> selGoodproduct(Long categoryid, Long localeid);
	
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Goodproduct record);

//    /**
//     * update a record By PK
//     */
//    public int updateByPK(Goodproduct record);

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long productbaseid, Long categoryid);
	
}