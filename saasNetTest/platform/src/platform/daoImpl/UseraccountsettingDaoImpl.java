package platform.daoImpl;

import org.apache.log4j.Logger;
import platform.bean.BeanFactory;
import platform.dao.UseraccountsettingDao;
import platform.vo.Useraccountsetting;
import commonTool.base.BaseDao;

public class UseraccountsettingDaoImpl extends BaseDao implements UseraccountsettingDao {

	static Logger log = Logger.getLogger(UseraccountsettingDaoImpl.class.getName());
	
    /**
     *
     */
    public UseraccountsettingDaoImpl() {
        super();
    }
    
	public Useraccountsetting selectByPK(Long pk) {
		if(pk==null)
    		return null;
		Useraccountsetting record = (Useraccountsetting)this.queryForObject("Useraccountsetting.selectByPK", pk);
		return record;
	}

	public int deleteByPK(Long pk) {
		if(pk==null)
    		return 0;
    	int rows = 0;
	    rows = super.delete("Useraccountsetting.deleteByPK", pk);
		return rows;
	}

	public Long insert(Useraccountsetting record) {
		if(record==null)
    		return null;
    	super.insert("Useraccountsetting.insert", record);
		return record.getUserid();
	}

	public int updateByPK(Useraccountsetting record) {
		if(record==null||record.getUserid()==null||record.getShopcreateable()==null)
    		return 0;
    	int rows = super.update("Useraccountsetting.updateByPK", record);
    	return rows;
	}
	
    /**
     * static spring getMethod
     */
     public static UseraccountsettingDao getInstance() {
    	 UseraccountsettingDao dao = (UseraccountsettingDao)BeanFactory.getBeanFactory().getBean("useraccountsettingDao");
         return dao;
     }
    
}
