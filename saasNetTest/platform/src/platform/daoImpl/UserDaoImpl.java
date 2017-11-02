package platform.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.constant.CustomerConstant;
import platform.dao.UserDao;
import platform.dto.UserQuery;
import platform.exception.ExceptionConstant;
import platform.vo.User;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;

public class UserDaoImpl extends BaseDao implements UserDao {

	static Logger log = Logger.getLogger(UserDaoImpl.class.getName());
	
    /**
     *
     */
    public UserDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="platform.userCache", key="'UserDao.selectByPK~user:'+#pk", unless="#result==null")
    public User selectByPK(Long pk){
    	if(pk==null)
    		return null;
       	User record = (User)this.queryForObject("User.selectByPK", pk);
       	
       	// add cache key into key associate map
       	if(record!=null) {
		   CacheSynchronizer.getInstance().buildAssoc("platform.userCache", 
				"UserDao.selectByPK~"+User.ObjectType+":"+pk);
       	}
		return record;
    }
    
    /**
     * select some record by selectByLogin
     */
    @Cacheable(value="platform.userCache", key="'UserDao.selectByLogin~'+#loginName", unless="#result==null")
    public User selectByLogin(String loginName){
    	if(loginName==null||loginName.trim().length()<1)
    		return null;
    	User record = (User) this.queryForObject("User.selectByLogin", loginName);
    	
    	// add cache key into key associate map
    	if(record!=null) {
		   CacheSynchronizer.getInstance().buildAssoc("platform.userCache", 
				"UserDao.selectByLogin~"+loginName, new String[]{User.ObjectType+":"+record.getUserid()});
    	}
    	return record;
    }
    
    /**
     * select some record by email
     */
    @Cacheable(value="platform.userCache", key="'UserDao.selectByEmail~'+#email", unless="#result==null")
    public User selectByEmail(String email){
    	if(email==null||email.equals(""))
    		return null;
       	User record = (User)this.queryForObject("User.selectByEmail", email);
       	
       	// add cache key into key associate map
       	if(record!=null) {
		   CacheSynchronizer.getInstance().buildAssoc("platform.userCache", 
				"UserDao.selectByEmail~"+email, new String[]{User.ObjectType+":"+record.getUserid()});
       	}
		return record;
    }
    
//    /**
//     * 根据用户账号，用户密码，用户类型查询用户信息，如果没有则返回null
//     * @param account
//     * @param pass
//     * @param userType
//     * @return
//     * @throws Exception
//     */
//    public User checkLoginUser(String loginname,String pass,Short userType) {
//    	UserQuery queryVO = new UserQuery();
//		queryVO.setLoginname(loginname);
//		queryVO.setPass(pass);
//		queryVO.setUsertype(userType);
//		
//		List list = this.selectByVO(queryVO);
//		if(list==null||list.size()!=1)
//			return null;
//		return (User)list.get(0);
//    }
            
    /**
     * select records by queryVO
     */
    public List selectByVO(UserQuery queryVO) {
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("User.selectByVO", queryVO);
		return list;
    }
    
    /**
     * select page by queryVO 
     */
    public Page selectByVOPage(UserQuery queryVO,int pageIndex,int pageSize,Integer total) {
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "User.selectByVO";
        if(queryVO==null)
        	return Page.EMPTY_PAGE;
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }
       
    /**
     * 新增一个用户本表
     * @return userID
     */
    public Long insert(User record) {
    	if(record==null)
    		return null;
    	if(record.getLoginname()!=null)
    		record.setLoginname(record.getLoginname().trim());
		Long pk = (Long)super.insert("User.insert", record);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.userCache", User.ObjectType, pk.toString());
		
		return record.getUserid();
    }

    /**
     * 更新用户
     */
    public int updateByPK(User record) {
    	if(record==null||record.getUserid()==null)
    		return 0;
    	int rows = 0;
    	if(!record.isEmpty()){
    		super.update("User.updateByPK", record);
    		rows = 1;
    	}
    	// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.userCache", User.ObjectType, record.getUserid().toString());
		
		return rows;
    }
    
    public int updatePassword(String newpassword, Long userid) {
    	if(userid==null||(newpassword==null||newpassword.trim().length()<1))
    		throw new LogicException(ExceptionConstant.Error_Need_Paramter);
    	User vo = new User();
    	vo.setUserid(userid);
    	vo.setPass(newpassword);
    	int rows = super.update("User.updatePassword", vo);
    	// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.userCache", User.ObjectType, userid.toString());
		
		return rows;
    }

    /**
     * 更新用户loginame, email(如果email不为空), 并且更新stagestatus;更新password和status
     * 用户用户首次激活自己账户做的修改和用户通过social login来登录第一次设置用户信息
     * @param newloginame
     * @param userid
     * @return
     */
    public int updateLoginAccountInfo(String newloginame, String password, String email, 
    		        Short status, Long userid) 
    {
    	if(userid==null||(newloginame==null||newloginame.trim().length()<1))
    		throw new LogicException(ExceptionConstant.Error_Need_Paramter);
    	User vo = new User();
    	vo.setUserid(userid);
    	vo.setLoginname(newloginame);
    	vo.setPass(password);
    	vo.setEmail(email);
    	vo.setStatus(status);
    	vo.setStagestatus(CustomerConstant.StageStatus_settedLoginName);
    	int rows = super.update("User.updateLoginamePassEmailStatus", vo);
    	// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.userCache", User.ObjectType, userid.toString());
		
		return rows;
    }
    
    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public User save(User record) {
    	if(record==null)
    		return null;
		if(record.getUserid()==null||record.getUserid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setUserid(pkValue);
			return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }
    
    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) {
    	if(pk==null)
    		return 0;
    	int rows = 0;
	    rows = super.delete("User.deleteByPK", pk);
	    // flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.userCache", User.ObjectType, pk.toString());
		
		return rows;
    }
    
    /**
     * insertBatch records of List
     */
    public int insertBatch(List list) {
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("User.insert", list);
       	return rows;
    }
    
    /**
     * deleteBatch records by the Long Array of PK
     */
//    public int deleteBatchByPK(Long[] pkArray) {
//    	if(pkArray==null||pkArray.length<=0)
//    		return 0;
//    	int rows = 0;
//       	rows = super.deleteBatch("User.deleteByPK", pkArray);
//       	return rows;
//    }
    
    /**
     * 根据角色id选择该角色下的用户列表
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByRoleIdPage(UserQuery queryVO,int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "User.selectByRoleId";
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }
    
    /**
     * 选择某角色下可选用户列表，即还没有选择的用户的列表
     * @param queryVO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Page selectUsersForRoleAddPage(UserQuery queryVO,
    		int pageIndex,int pageSize,Integer total){
    	if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        // only select active status user
        queryVO.setStatus(CustomerConstant.CustomerStatus_active);
        String sqlStr = "User.selectUsersForRoleAdd";
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }
    
    /**
     * static spring getMethod
     */
     public static UserDao getInstance() {
    	 UserDao dao = (UserDao)BeanFactory.getBeanFactory().getBean("userDaoProxy");
         return dao;
     }
    
}
