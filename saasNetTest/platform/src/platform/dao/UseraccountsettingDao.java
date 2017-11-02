package platform.dao;

import platform.vo.Useraccountsetting;

public interface UseraccountsettingDao {

	/**
     * select some record by PK
     */
    public Useraccountsetting selectByPK(Long pk);
    
    /**
     * insert a record
     */
    public Long insert(Useraccountsetting record);
    
    public int updateByPK(Useraccountsetting record);
    
    public int deleteByPK(Long pk);
	
}
