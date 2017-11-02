package platform.dao;

import java.util.List;
import platform.vo.Reversesearch;

public interface ReversesearchDao {
   
	/**
     * select records by search text
     */
    public List<Long> selectByText(String searchtext, String entitytype);
    
    
    public void insert(Reversesearch record);
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List<Reversesearch> list);

    /**
     * delete a record by entity
     */
    public int deleteByEntity(Long entityID, String entitytype);
    
    /**
     * delete a record by search text
     */
    public int deleteByText(String searchtext, String entitytype);
    	
}
