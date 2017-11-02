package acegi.dao;

import java.util.List;
import commonTool.base.Page;
import acegi.vo.Role_resc;
import acegi.dto.Role_rescQuery;

public interface Role_rescDao {
   
    /**
     * select some record by PK
     */
    public Role_resc selectByPK(Long pk);
    
    /**
     * select all records
     * @return
     */
    public List selectAll();
        
    /**
     * select records by queryVO
     */
    public List selectByVO(Role_rescQuery queryVO);
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(Role_rescQuery queryVO,int pageIndex,int pageSize);
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Role_resc record);

    /**
     * update a record By PK
     */
    public int updateByPK(Role_resc record);

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Role_resc save(Role_resc record);

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) throws Exception ;
    
    /**
     * delete records by queryVO
     */
    public int deleteByVO(Role_rescQuery queryVO);

	/**
     * insertBatch records of List
     */
    public int insertBatch(List list);
    
    /**
     * updateBatch records of List
     */
    public int updateBatch(List list);
    
    /**
     * deleteBatch records by the Long Array of PK
     */
    public int deleteBatchByPK(Long[] pkArray);
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray);
	
}