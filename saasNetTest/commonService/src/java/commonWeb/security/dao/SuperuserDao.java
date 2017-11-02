package commonWeb.security.dao;

import java.util.Date;

import commonWeb.security.vo.Superuser;

public interface SuperuserDao {
   
	public Superuser getSuperuser(String username);
	
    /**
     * 判断用户是否是合法的超级用户。
     * @param loginname
     * @param ip
     * @param syscode
     * @param date
     * @return 1：超级用户。0：普通用户
     */
    public int validateSuperAdmin(String loginname,String ip,String syscode,Date date);
    
}
