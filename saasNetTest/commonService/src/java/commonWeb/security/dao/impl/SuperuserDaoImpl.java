package commonWeb.security.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.constant.SysparamConstant;
import commonWeb.security.bean.BeanFactory;
import commonWeb.security.dao.SuperuserDao;
import commonWeb.security.vo.Superuser;

public class SuperuserDaoImpl extends BaseDao implements SuperuserDao {

	static Logger log = Logger.getLogger(SuperuserDaoImpl.class.getName());
	private static Map<String,Superuser> dataMap = null;
	
    /**
     *
     */
    public SuperuserDaoImpl() {
        super();
    }
    
    @Cacheable(value="commonService.UserRoleCache", key="'Superuser~'+#username")
    public Superuser getSuperuser(String username){
    	if(username==null||username.trim().length()<1)
    		return null;
    	Superuser user = null;
    	if(dataMap==null){
    		dataMap = new HashMap<String,Superuser>();
    		// 此时syscode一定已经被加载了
    		String syscode = SysparamConstant.syscode;
    		List list = this.selectAll(syscode);
    		Superuser temp = null;
    		for(int i=0;i<list.size();i++){
    			temp = (Superuser)list.get(i);
    			dataMap.put(temp.getLoginname(), temp);
    		}
    	}
    	user = dataMap.get(username);
    	return user;
    }
    
    /**
     * select some record by PK
     */
    public List selectAll(String syscode){
    	if(syscode==null||syscode.trim().length()<1)
    		return null;
		List list = this.queryForList("Superuser.selectAll", syscode);
		return list;
    }
    
    /**
     * 判断用户是否是合法的超级用户。
     * @param loginname
     * @param ip
     * @param syscode
     * @param date
     * @return 1：超级用户。0：普通用户
     */
    public int validateSuperAdmin(String loginname,String ip,String syscode,Date date){
    	int rtn = 0;
    	if(loginname==null||loginname.trim().length()<1||ip==null||ip.trim().length()<1
    		||syscode==null||syscode.trim().length()<1||date==null){
    		return rtn;
    	}
    	Superuser user = getInstance().getSuperuser(loginname);
    	if(user!=null){
    		// check ip restriction
    		if((user.getSyscodestr().indexOf(syscode)!=-1) 
    			&& (date.compareTo(user.getDuetime())<=0)	
    			&& (user.getIpstr()==null||user.getIpstr().trim().length()<1||user.getIpstr().indexOf(ip)!=-1))
    		{
    			rtn=1;
    		}else {
    			log.info("super user:"+loginname
    					+" login, but cannot be treated as superAdmin. From ip: "+ip+" , date:"+date);
    		}
    	}
    	return rtn;
    }
           
    
    /**
     * static spring getMethod
     */
     public static SuperuserDao getInstance(){
       	 SuperuserDao dao = (SuperuserDao)BeanFactory.getBeanFactory().getBean("superuserDao");
         return dao;
     }
    
}
