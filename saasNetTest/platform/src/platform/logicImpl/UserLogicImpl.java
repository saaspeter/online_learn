package platform.logicImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.constant.AccountConstant;
import platform.constant.CustomerConstant;
import platform.dao.BaseaccountDao;
import platform.dao.CustinfoexDao;
import platform.dao.CuststatusDao;
import platform.dao.UserDao;
import platform.dao.UseraccountsettingDao;
import platform.dao.UsercontactinfoDao;
import platform.dao.UsershopDao;
import platform.daoImpl.UserDaoImpl;
import platform.exception.ExceptionConstant;
import platform.logic.UserLogic;
import platform.mail.logic.UserMailUtil;
import platform.user.dao.AccountvalidatetaskDao;
import platform.vo.Baseaccount;
import platform.vo.Custinfoex;
import platform.vo.Custstatus;
import platform.vo.User;
import platform.vo.Useraccountsetting;
import platform.vo.Usercontactinfo;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.exception.DaoException;
import commonTool.exception.DuplicateException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;


public class UserLogicImpl implements UserLogic{

	static Logger log = Logger.getLogger(UserLogicImpl.class.getName());
	
	private UserDao userDao;
    private BaseaccountDao accountDao;
    private UsercontactinfoDao contactDao;
    private CustinfoexDao custinfoexDao;
    private CuststatusDao custstatusDao;
    private UseraccountsettingDao useraccountsettingDao;
    private AccountvalidatetaskDao accountvalidatetaskDao;
	

	/* 检查用户是否存在。为dwr界面使用
	 * @see platform.logicImpl.UserLogic#isExistsLoginname(java.lang.String)
	 */
	public boolean isExistsLoginname(String loginname) {
		if(loginname==null||loginname.trim().length()==0)
			return false;
		//for the dwr,so init dao directly
		User uservo = UserDaoImpl.getInstance().selectByLogin(loginname);
		if(uservo!=null)
			return true;
		else 
			return false;
	}
    
	/* 检查用户是否存在。为dwr界面使用
	 * @see platform.logicImpl.UserLogic#isExistsEmail(java.lang.String)
	 */
	public boolean isExistsEmail(String email) {
		if(email==null||email.trim().length()==0)
			return false;
		//for the dwr,so init dao directly
		User vo = UserDaoImpl.getInstance().selectByEmail(email);
		if(vo!=null)
			return true;
		else 
			return false;
	}
	
	/**
	 * 
	 */
	public Map<Long, User> qryBatchUser(Collection<Long> useridList){
		if(useridList==null||useridList.size()<1)
			return null;
		Map<Long, User> userMap = new HashMap<Long, User>();
		User vo = null;
		for(Long id : useridList){
			vo = userDao.selectByPK(id);
			userMap.put(id, vo);
		}
		return userMap;
	}
	
	/**
	 * 新增一个用户，包括增加 User,Contactinfo,Custinfoex,Custstatus
	 * 同时增加相应的账户BaseAccount。设置他们的状态为正常使用状态
	 * @param User
	 * @param newusertype: 参见CustomerConstant.NewCreateUserType
	 * @return 新增的用户，如果该用户名已经存在则抛异常
	 */
    public User insertUser(User user, Short newusertype) throws DuplicateException{
    	if(user==null)
    		return null;
    	Usercontactinfo contactVO = user.getContactinfo();
    	Custinfoex custinfoexVO = user.getCustinfoex();
    	//在此设置用户的状态为正常使用状态，如果用户还需要激活，则以后可以改为CustomerStatus_inactive
    	if(user.getStatus()==null){
    	   user.setStatus(CustomerConstant.CustomerStatus_active);
    	}
    	// 设置注册时间
    	if(user.getRegistime()==null){
    	   user.setRegistime(DateUtil.getInstance().getNowtime_GLNZ());
    	}
    	if(user.getUsertype()==null){
    	   user.setUsertype(CustomerConstant.UserType_user);
    	}
    	try {
			if(isExistsLoginname(user.getLoginname())) {
				throw new DuplicateException("the user loginname has already existed, loginname:"
					         + user.getLoginname()+" , email:"+user.getEmail());
			}
			if(isExistsEmail(user.getEmail())){
				throw new DuplicateException("the user email has already existed, loginname:"
				             + user.getLoginname()+" , email:"+user.getEmail());
			}
			// 用MD5加密密码
			String pwd = null;
			if(user.getPass()!=null){
				pwd = StringUtil.md5_saltSource(user.getPass(), user.getLoginname());
			}
			user.setPass(pwd);
			// 增加user
			Long userid = userDao.insert(user);
			user.setUserid(userid);
			// 增加Custstatus
			Custstatus custstatusVO = new Custstatus();
			custstatusVO.setUserid(userid);
			custstatusVO.setBfstatus(CustomerConstant.CustomerStatus_register);
			custstatusVO.setAfstatus(user.getStatus());
			custstatusVO.setStatusdisc("register completed!");//以后再决定用什么语言什么文字
			custstatusVO.setStatustime(user.getRegistime());
			custstatusDao.insert(custstatusVO);
			// 增加Contactinfo,如果没有填写任何东西，仍然添加一条空白记录
			if(contactVO!=null){
			   contactVO.setUserid(userid);
			   contactDao.insert(contactVO);
			}
			// 增加Custinfoex,如果没有填写任何东西，仍然添加一条空白记录
			if(custinfoexVO!=null){
				custinfoexVO.setUserid(userid);
				custinfoexDao.insert(custinfoexVO);
			}
			// 添加user的帐号设置情况
			Useraccountsetting accountsettingVO = new Useraccountsetting();
			accountsettingVO.setUserid(userid);
			accountsettingVO.setShopcreateable(Useraccountsetting.Shopcreateable_AllowOne);
			useraccountsettingDao.insert(accountsettingVO);
			// 给客户添加一个账户
			String accountcode = userid.toString(); // 帐号编码规则确定时，再修改此处的编码
			Baseaccount account = new Baseaccount();
			account.setObjectid(userid);
			account.setAccountnum(0d);
			account.setAccountcode(accountcode);
			account.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
			// 设置用户的帐户状态为正常使用状态
			account.setStatus(AccountConstant.Status_active);
			account.setAccounttype(AccountConstant.AccountType_user);
			accountDao.insert(account);
    	} catch(DuplicateException e1){
    		throw e1;
		} 
    	// send a mail to user 
    	// self register new user mail is different with orgAdmin new user mail
    	Locale locale = I18nLogicImpl.getLocale(user.getLocaleid());
    	if(user.getEmail()!=null && !"".equals(user.getEmail())){
    	    UserMailUtil.sendWelcomeNewUserMail(user.getUserid(), user.getEmail(), user.getLoginname(), locale,
				          "/customers/activenewuserpage.do?email="+user.getEmail()+"&validatecode=",
				          newusertype);
    	}else {
    		log.warn("warn in UserLogic.insertUser, user don't have email, userid:"+user.getUserid()
    				 +" , loginname:"+user.getLoginname());
    	}
    	return user;
    }
    
    /**
     * 更新用户信息，同时更新basic,user,contact,custinfo的信息
     * 用户状态和用户的账户信息不在此修改
     */
    public boolean updateUser(User user) throws Exception{
    	if(user==null)
    		return true;
		Usercontactinfo contactVO = user.getContactinfo();
		Custinfoex custinfoexVO = user.getCustinfoex();
		// 设置userid
		if(contactVO!=null)
			contactVO.setUserid(user.getUserid());
		if(custinfoexVO!=null)
			custinfoexVO.setUserid(user.getUserid());
		//在此不能修改用户的状态
		user.setStatus(null);
		userDao.updateByPK(user);
		contactDao.save(contactVO);
		custinfoexDao.save(custinfoexVO);
		return true;
    }
    
    /**
     * 更新用户密码，现对比original密码，用户输入的密码需要加密
     * @param oldpass
     * @param newpass
     * @param userid
     */
    public void updatePassword(String oldpass, String newpass, Long userid){
    	if(userid==null||newpass==null){
    		return;
    	}
    	User vo = userDao.selectByPK(userid);
    	AssertUtil.assertNotNull(vo, null);
		String passInDB = vo.getPass();
        // 用MD5加密密码
		oldpass = StringUtil.md5_saltSource(oldpass, vo.getLoginname());
		newpass = StringUtil.md5_saltSource(newpass, vo.getLoginname());
		if(passInDB.equals(oldpass)){
			userDao.updatePassword(newpass, userid);
		}else {
			throw new LogicException(ExceptionConstant.Error_UpdateUserPassword_OldPasswordNotRight);
		}
    }
        
    /**
     * 更新用户loginame和password, 用于被shop admin创建的用户第一次登录时的修改用户的loginame
     * 或者 更新用户的Loginname/password/email, 当用户通过social 方式登录的，而又没有设置loginname，用来修改用户初始的设置
     * @param userid
     * @param userid
     */
    public void firstSetUserAccountInfo(Long userid, String newloginame, 
    		            String newpassword, String email, Short status)
    {
    	if(userid==null || newloginame==null || newloginame.trim().length()<1){
    		throw new LogicException(ExceptionConstant.Error_Need_Paramter);
    	}
    	User vo = userDao.selectByPK(userid);
    	Short stagestatus = vo.getStagestatus();
		if(!CustomerConstant.StageStatus_notSetLoginName.equals(stagestatus)){
			throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
		}
		// 更新用户在shop中的loginame
		if(!vo.getLoginname().equals(newloginame)){
			UsershopDao usershopDao = BOFactory_Platform.getUsershopDao();
			usershopDao.updateUserLoginame(userid, newloginame);
		}
		// 加密密码
		if(newpassword!=null&&newpassword.trim().length()>0){
		   newpassword = StringUtil.md5_saltSource(newpassword, newloginame);
		}
        // 更新loginname, pass, status
		userDao.updateLoginAccountInfo(newloginame, newpassword, email, status, userid);
    }
   
	/**
	 * 处理用户状态的改变，暂时做了在用户状态记录表里插入一条用户状态改变的记录。
	 * 把用户基础表里的用户状态改变。以后还要加入别的判断逻辑
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean changeStatus(User vo, String changeReason) {
		if(vo==null||vo.getUserid()==null||vo.getStatus()==null)
			return true;
		Short status = vo.getStatus();

		User vo2 = userDao.selectByPK(vo.getUserid());
		Short status2 = vo2.getStatus();
		if(status2!=null&&(status.shortValue()==status2.shortValue()))
			return true;
		// 插入一条状态改变的记录
		Custstatus voInsert = new Custstatus();
		voInsert.setAfstatus(status);
		voInsert.setBfstatus(status2);
		voInsert.setStatustime(DateUtil.getInstance().getNowtime_GLNZ());
		voInsert.setUserid(vo.getUserid());
		voInsert.setStatusdisc(changeReason);
		custstatusDao.insert(voInsert);
		// 改变user中的用户状态
		vo2.setStatus(status);
		userDao.updateByPK(vo2);

		return true;
	}
	
	/**
	 * 根据用户在商店中的登录名和商店编码生成默认用户全局登录名
	 * @param loginnamedept
	 * @param shopcode
	 * @return
	 */
	public String geneShopUserLoginName(String loginnamedept,String shopcode,String syscode){
		if(loginnamedept==null||loginnamedept.trim().length()<1||shopcode==null||shopcode.trim().length()<1
				||syscode==null||syscode.trim().length()<1)
			return "";
		String rtn = "_"+shopcode+"_"+CommonConstant.getWebContext(syscode)+"_"+loginnamedept;
		return rtn;
	}
	
	/**
	 * 根据social帐号(一般是email)生成default loginname
	 * @param email: social帐号的email
	 * @return
	 */
	public String geneSocialUserLoginName(String rawloginname){
		if(rawloginname==null||rawloginname.length()<1)
			rawloginname = generateRandomLoginname();
		String rtn = "_Social_"+rawloginname;
		return rtn;
	}
	
	private String generateRandomLoginname(){
		String rawloginname = String.valueOf(System.currentTimeMillis())+"_"+StringUtil.getRandomNumber();
	    if(rawloginname.length()>64){
	    	rawloginname = rawloginname.substring(0, 64);
	    }
	    return rawloginname;
	}
    
    /**
     *  删除用户比较麻烦,暂时先不处理
     *  在此调用在商店里删除人员的逻辑方法，采用调用各校模块的逻辑删除方法删除相关逻辑数据，而不是直接删除相关逻辑表
     *  关于用户的学习系统相关的数据应该由调用相关webservices方法来完成。
     * @param userID
     * @return
     */
    public boolean delUser(Long userID, String loginname) {
    	if(userID==null)
    		return false;
    	// TODO

		// 查询出用户属于哪些系统，调用商店删除用户的逻辑，UsershopLogicImpl类中的delUserInShop方法（删除了用户在商店的所有业务数据）
			
		// 删除activate用户信息
    	accountvalidatetaskDao.deleteByUserID(userID);
		
		// 删除用户帐号配置情况Useraccountsetting
		useraccountsettingDao.deleteByPK(userID);
		// 删除用户基本信息表CustStatus、ContactInfo、CustInfoEx
		custstatusDao.deleteByUserID(userID);
		contactDao.deleteByPK(userID);
		custinfoexDao.deleteByPK(userID);
		// 删除User表
		userDao.deleteByPK(userID);

    	return true;
    }
    	
    /**
     * static spring getMethod
     */
    public static UserLogic getInstance() throws DaoException {
      try{
    	  UserLogic logic = (UserLogic)BeanFactory.getBeanFactory().getBean("userLogic");
          return logic;
      }catch(Exception e) {
      	 log.error("UserLogicImpl方法getInstance()时出错误!", e);
            throw new DaoException("Errors occur while getInstance() in UserLogicImpl!",e) ;
      }
   }

	public BaseaccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(BaseaccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public UsercontactinfoDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(UsercontactinfoDao contactDao) {
		this.contactDao = contactDao;
	}

	public CustinfoexDao getCustinfoexDao() {
		return custinfoexDao;
	}

	public void setCustinfoexDao(CustinfoexDao custinfoexDao) {
		this.custinfoexDao = custinfoexDao;
	}

	public CuststatusDao getCuststatusDao() {
		return custstatusDao;
	}

	public void setCuststatusDao(CuststatusDao custstatusDao) {
		this.custstatusDao = custstatusDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UseraccountsettingDao getUseraccountsettingDao() {
		return useraccountsettingDao;
	}


	public void setUseraccountsettingDao(UseraccountsettingDao useraccountsettingDao) {
		this.useraccountsettingDao = useraccountsettingDao;
	}
	
	public AccountvalidatetaskDao getAccountvalidatetaskDao() {
		return accountvalidatetaskDao;
	}

	public void setAccountvalidatetaskDao(
			AccountvalidatetaskDao accountvalidatetaskDao) {
		this.accountvalidatetaskDao = accountvalidatetaskDao;
	}
	
}
