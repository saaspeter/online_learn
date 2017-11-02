package commonWeb.security.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import platform.exception.ExceptionConstant;

import commonTool.base.BaseDao;
import commonTool.cache.CacheSynchronizer;
import commonTool.exception.LogicException;
import commonWeb.security.bean.BeanFactory;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.constant.UserRolesConstant;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dto.UserRoleQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Roles;
import commonWeb.security.vo.UserRole;


public class UserRoleDaoImpl extends BaseDao implements UserRoleDao {

	static Logger log = Logger.getLogger(UserRoleDaoImpl.class.getName());
	
    /**
     *
     */
    public UserRoleDaoImpl() {
        super();
    }

           
//    /**
//     * select records by queryVO
//     */
//    public List selectByVO(UserRoleQuery queryVO){
//		if(queryVO==null)
//			return null;
//		List list = this.queryForList("UserRole.selectByVO", queryVO);
//		return list;
//    }
    
    /**
     * 查询用户在商店中的角色列表, 返回角色id list
     */
//    public List<Long> selectByUserShop(Long userid, Long shopid, String syscode){
//		if(userid==null||shopid==null)
//			return null;
//		UserRoleQuery queryVO = new UserRoleQuery();
//		queryVO.setUserId(userid);
//		queryVO.setShopid(shopid);
//		queryVO.setSyscode(syscode);
//		List<Long> list = this.queryForList("UserRole.selectByUserShop", queryVO);
//		return list;
//    }
    
    /**
     * select the user's roles by userid,shopid,syscode
     */
    @Cacheable(value="commonService.UserRoleCache", key="'selectUserRole~'+#userid+'~'+#shopid+'~'+#syscode")
    public List<Long> selectUserRole(Long userid,Long shopid,String syscode){
    	if(userid==null||syscode==null||syscode.trim().length()<1)
    		return null;
    	UserRoleQuery queryVO = new UserRoleQuery();
    	queryVO.setUserid(userid);
    	queryVO.setShopid(shopid);
    	queryVO.setSyscode(syscode);
    	
    	List<Long> roleidList = (List<Long>)this.queryForList("UserRole.selectUserRole", queryVO);
    	return roleidList;
    }
    
    /**
     * 查询在商店中具有某种role的用户id列表
     */
    public List<Long> selectUserByShopRole(Long shopid, String roleidStr, String syscode){
		if(shopid==null||roleidStr==null||roleidStr.length()<1)
			return null;
		UserRoleQuery queryVO = new UserRoleQuery();
		queryVO.setShopid(shopid);
		if(roleidStr.endsWith(",")){
			roleidStr = roleidStr.substring(0, roleidStr.length()-1);
		}
		queryVO.setRoleidstr(roleidStr);
		queryVO.setSyscode(syscode);
		List<Long> list = this.queryForList("UserRole.selectUserByShopRole", queryVO);
		return list;
    }
    
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    @Caching(evict={@CacheEvict(value="commonService.UserRoleCache", key="'selectUserRole~'+#record.userid+'~'+#record.shopid+'~'+#record.syscode")})
    public Long insert(UserRole record){
    	if(record==null)
    		return null;
		return (Long)super.insert("UserRole.insert", record);
    }

    /**
     * delete a record by PK
     */
    @Caching(evict={@CacheEvict(value="commonService.UserRoleCache", key="'selectUserRole~'+#userid+'~'+#shopid+'~'+#syscode")})
    public int deleteByPK(Long userid, Long roleid, Long shopid, String syscode){
    	if(userid==null||roleid==null||shopid==null||syscode==null)
    		return 0;
    	// 如果要删除的是shoplevel role, 必须指定shopid
    	Roles rolevo = BOFactory.getRolesDao().selectByPK(roleid);
    	if(rolevo.getRolelevel().equals(RolesConstant.RoleLevel_ShopLevel)
    		&& (shopid==null||UserRolesConstant.NonShopID.equals(shopid))){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
    	}
    	UserRole userroleVO = new UserRole();
    	userroleVO.setUserid(userid);
    	userroleVO.setRoleid(roleid);
    	userroleVO.setShopid(shopid);
    	userroleVO.setSyscode(syscode);
		int rows = super.delete("UserRole.deleteByPK", userroleVO);
		return rows;
    }
    
    public int deleteUserRoleInShop(Long userid, Long shopid, String syscode){
    	if(userid==null||shopid==null||syscode==null)
    		return 0;
    	UserRole userroleVO = new UserRole();
    	userroleVO.setUserid(userid);
    	userroleVO.setShopid(shopid);
    	userroleVO.setSyscode(syscode);
		int rows = super.delete("UserRole.deleteUserRoleInShop", userroleVO);
		return rows;
    }
    
    /**
     * 一次插入多个用户角色记录，一般是：给一个用户插入多个角色或给一个角色分配多个用户
     * @param userIds
     * @param roleIds
     * @return
     */
    public int insertMoreBatch(Long[] userIds,Long[] roleIds, UserRole vo){
    	if(userIds==null||userIds.length==0||roleIds==null||roleIds.length==0)
    	   return 0;
    	List list = new ArrayList();
    	List<String> cacheKeyList = new ArrayList<String>();
    	for(int i=0;i<userIds.length;i++){
    		for(int j=0;j<roleIds.length;j++){
    			if(roleIds[j]!=null){
    				UserRole voTemp = new UserRole();
        			voTemp.setUserid(userIds[i]);
        			voTemp.setRoleid(roleIds[j]);
        			voTemp.setShopid(vo.getShopid());
        			voTemp.setSyscode(vo.getSyscode());
        			voTemp.setUsetype(vo.getUsetype());
        			list.add(voTemp);
        			// prepare cache key
        			cacheKeyList.add("selectUserRole~"+userIds[i]+"~"+vo.getShopid()+"~"+vo.getSyscode());
    			}
    		}
    	}
    	int nums = this.insertBatch(list);
    	// flush cache
    	CacheSynchronizer.getInstance().batchFlush("commonService.UserRoleCache", cacheKeyList);
    	
    	return nums;
    }
    
	/**
     * insertBatch records of List
     */
    private int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("UserRole.insert", list);
       	return rows;
    }
    
        
    /**
     * deleteBatch records by the List of vo
     */
    public int deleteBatchByPK(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
        UserRole vo = null;
        List<String> cacheKeyList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
			   vo = (UserRole)list.get(i);
			   this.deleteByPK(vo.getUserid(), vo.getRoleid(), vo.getShopid(), vo.getSyscode());
				rows++;
				// prepare cache key
				cacheKeyList.add("selectUserRole~"+vo.getUserid()+"~"+vo.getShopid()+"~"+vo.getSyscode());
			}
		}
		// flush cache
    	CacheSynchronizer.getInstance().batchFlush("commonService.UserRoleCache", cacheKeyList);
		return rows;
    }
    
    /**
     * 查询传入的role中有哪些role下面有人员。返回有人员的role，其中用逗号隔开的返回结果。
     * @param roles
     * @return
     */
    public List<Long> selectUserUnderRole(Long roleid){
    	if(roleid==null)
    		return null;
    	
    	List<Long> list = (List<Long>)super.queryForList("UserRole.selectUserUnderRole", roleid);
    	return list;
    }
    
    /**
     * static spring getMethod
     */
     public static UserRoleDao getInstance(){
       	 UserRoleDao dao = (UserRoleDao)BeanFactory.getBeanFactory().getBean("userRoleDao");
         return dao;
     }
     
     public static void main(String[] args){
    	 UserRoleDao dao = UserRoleDaoImpl.getInstance();
     }
    
}