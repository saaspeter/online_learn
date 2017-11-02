package platform.logic;

import java.util.Collection;
import java.util.Map;

import platform.vo.User;
import commonTool.exception.DuplicateException;


public interface UserLogic {
	
	/* 检查用户是否存在。为dwr界面使用
	 * @see platform.logicImpl.UserLogic#isExistsLoginname(java.lang.String)
	 */
	public boolean isExistsLoginname(String loginname);
    
	/* 检查用户是否存在。为dwr界面使用
	 * @see platform.logicImpl.UserLogic#isExistsEmail(java.lang.String)
	 */
	public boolean isExistsEmail(String email);
	
	public Map<Long, User> qryBatchUser(Collection<Long> useridList);
	
	/**
	 * 处理用户状态的改变，暂时做了在用户状态记录表里插入一条用户状态改变的记录。
	 * 把用户基础表里的用户状态改变。以后还要加入别的判断逻辑
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean changeStatus(User vo, String changeReason);
	
	/**
	 * 新增一个用户，包括增加User,Contactinfo,Custinfoex,Custstatus
	 * 同时增加相应的账户。设置他们的状态为正常使用状态
	 * @param User
	 * @return 新增的用户，如果该用户名已经存在则抛异常
	 */
    public User insertUser(User user, Short newusertype) throws DuplicateException;
    
    /**
     * 更新用户信息，同时更新basic,user,contact,custinfo的信息
     * 用户状态和用户的账户信息不在此修改
     * @param User
     * @return
     * @throws Exception
     */
    public boolean updateUser(User user) throws Exception;
    
    /**
     * 更新用户loginame, 用于被shop admin创建的用户第一次登录时的修改用户的loginame
     * @param userid
     * @param userid
     */
    public void firstSetUserAccountInfo(Long userid, String newloginame, String newpassword, String email, Short status);
    
    /**
     * 更新用户密码，现对比original密码，用户输入的密码需要加密(todo)
     * @param oldpass
     * @param newpass
     * @param userid
     */
    public void updatePassword(String oldpass, String newpass, Long userid);
    
    /**
     *  删除用户比较麻烦,暂时先不处理
     * @param userID
     * @return
     */
    public boolean delUser(Long userID, String loginname);
    
    /**
	 * 根据用户在商店中的登录名和商店编码生成默认用户全局登录名
	 * @param loginnamedept
	 * @param shopcode
	 * @return
	 */
	public String geneShopUserLoginName(String loginnamedept,String shopcode,String syscode);
	
	/**
	 * 根据social帐号(一般是email)生成default loginname
	 * @param email: social帐号的email
	 * @return
	 */
	public String geneSocialUserLoginName(String email);
	
}
