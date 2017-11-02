package netTest.product.dao;

import java.util.List;

import netTest.product.vo.Openactivitymember;
import commonTool.base.Page;

public interface OpenactivitymemberDao {
   
    /**
     * select some record by PK
     */
    public Openactivitymember selectByPK(Long activityid, Long userid);
    
    public Page selectByVOPage(Long activityid, Long userid, Short joinstatus,
            int pageIndex, int pageSize, Integer total);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Openactivitymember record);

    /**
     * update a record By PK
     */
    public int updateByPK(Openactivitymember record);

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Openactivitymember save(Openactivitymember record);

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long activityid, Long userid);
    
    public int deleteByActivity(Long activityid);
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list);
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatch(Long activityid, Long[] userIdArray);
	
}