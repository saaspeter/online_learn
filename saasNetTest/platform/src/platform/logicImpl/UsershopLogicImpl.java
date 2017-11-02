package platform.logicImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.constant.CustomerConstant;
import platform.constant.UsershopConstant;
import platform.dao.ShopDao;
import platform.dao.ShopstyleconfigDao;
import platform.dao.UserDao;
import platform.dao.UsershopDao;
import platform.daoImpl.UserDaoImpl;
import platform.daoImpl.UsershopDaoImpl;
import platform.dto.UserQuery;
import platform.dto.UsershopQuery;
import platform.exception.ExceptionConstant;
import platform.logic.UserLogic;
import platform.logic.UsershopLogic;
import platform.vo.Shop;
import platform.vo.Shopstyleconfig;
import platform.vo.User;
import platform.vo.Usershop;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.exception.DaoException;
import commonTool.exception.LogicException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;

public class UsershopLogicImpl implements UsershopLogic, EventHandle{

	static Logger log = Logger.getLogger(UsershopLogicImpl.class.getName());
	
	private UsershopDao dao;
	private UserDao userDao;
	private ShopDao shopDao;
	
	public static final String HandleType_DeleteUserInShop = "DeleteUserInShop";
	
	
	/**
	 * 更新商店中的用户信息，暂时只更新基本信息，以后可能更新用户产品信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Usershop update(Usershop vo){
		if(vo!=null){
			UsershopDao daoLocale = BOFactory_Platform.getUsershopDao();
			UserDao userDaoLocale = BOFactory_Platform.getUserDao();
			daoLocale.updateByPK(vo);
			userDaoLocale.updateByPK(vo);
		}
		return vo;
	}
	
	/**
	 * 更新user在shop中的nickname
	 * @param shopid
	 * @param userid
	 * @param nickname
	 * @return 1: 更新成功 -2: nickname已经存在，更新失败
	 */
	public int updateNickname(Long shopid, Long userid, String nickname, String syscode){
		Usershop vo = dao.selectByLogicPK(shopid, userid);
		if(vo==null){
			throw new NoSuchRecordException("shopid:"+shopid+" userid:"+userid);
		}
		if(nickname==null){
			nickname = "";
		}
		if(nickname.equals(vo.getNickname())){
			return 1;
		}
		boolean exist = dao.existsNicknameInShop(nickname, shopid, syscode);
		if(!exist){
			Usershop emptyVO = new Usershop();
			emptyVO.setUsershopid(vo.getUsershopid());
			emptyVO.setNickname(nickname);
			dao.updateByPK(emptyVO);
			return 1;
		}else
			return -2;
	}
	
	/**
	 * 把订单条目中的购买人加入商店，如果商店中已经存在该学员则只用更新该学员的产品数，
	 * 否则直接插入一条记录
	 * @param itemVO
	 * @return -1: 缺少参数  1:新建用户进入商店数据  2:用户已经在商店中存在
	 * @throws Exception
	 */
	public int putUserIntoShop(Long shopid, Long userid, String nickname, Short usershoptype){
		if(shopid==null||userid==null)
			return -1;
		int rtn = 1;
		Usershop usershopVO = null;
		
		// 查询该用户是否在该商店中已经存在
		usershopVO = dao.selectByLogicPK(shopid, userid);
		if(usershopVO!=null){
			rtn = 2;
		}else{
			User userVO = userDao.selectByPK(userid);
			if(nickname==null||nickname.trim().length()<1){
				nickname = userVO.getDisplayname();
			}
		    usershopVO = new Usershop();
		    usershopVO.setShopid(shopid);
		    usershopVO.setUserid(userid);
		    usershopVO.setLoginname(userVO.getLoginname());
		    usershopVO.setJoindate(DateUtil.getInstance().getNowtime_GLNZ());
		    usershopVO.setJoinway(UsershopConstant.JoinWay_apply);
		    usershopVO.setAreainproduct(UsershopConstant.AreaInProduct_some);
		    usershopVO.setUsershoptype(usershoptype);
		    usershopVO.setUsershopstatus(UsershopConstant.UserShopStatus_active);
		    usershopVO.setNickname(nickname);
		    dao.insert(usershopVO);
		    rtn = 1;
		}

		return rtn;	
	}
    
	/**
	 * 用于商店管理员往商店里添加人员，此时要新增一个人员信息，还要增加人员加入商店的信息
	 * @param usershop
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Long addUserAndIntoShop(Usershop usershop) {
		if(usershop==null)
			return null;
		Long userid = null;
		//TODO 肯定不完善,用户在商店中的各个属性没有设置
		UsershopDao daoLocal = BOFactory_Platform.getUsershopDao();
		UserLogic userlogic = BOFactory_Platform.getUserLogic();
		usershop.setRegistime(DateUtil.getInstance().getNowtime_GLNZ());
		usershop.setUsertype(CustomerConstant.UserType_user);
		// 增加user
		User tempUser = userlogic.insertUser(usershop, CustomerConstant.NewCreateUserType_OrgAdminAdd);
		userid = tempUser.getUserid();
		if(userid!=null){
		   usershop.setLoginname(tempUser.getLoginname());
		   usershop.setUserid(userid);
		   daoLocal.insert(usershop);
		}
		return userid;
	}
	
	/**
	 * 从shopid商店中删除userID用户。这个方法原则上是提供给各个子系统调用，商店本身并不提供删除用户的操作。
	 * 如果用户的“阶段状态”是未设置全局帐号，则在此可以删除用户的基本信息，否则不能删除
	 * 至于各个子系统中的业务数据信息，由各个子系统自己删除。
	 * TODO 这个方法有问题，为什么传shopid
	 * @param userid
	 * @param shopid
	 * @param syscode
	 * @return
	 */
	public boolean delUserInShop(Long userid,Long shopid) {
		UsershopDao usershopDao = BOFactory_Platform.getUsershopDao();
		UserLogic userlogic = BOFactory_Platform.getUserLogic();
		UserDao userDao = BOFactory_Platform.getUserDao();
		
		// TODO
		// 删除UserProduct（同时包括UserProduct表）,删除是很复杂的逻辑
		
    	// 删除UserShop
		UsershopQuery queryVO = new UsershopQuery();
		queryVO.setUserid(userid);
		queryVO.setShopid(shopid);
		usershopDao.deleteUserInShop(userid, shopid, null);
		// 如果用户的“阶段状态”是未设置全局帐号，则在此可以删除用户的基本信息，否则不能删除
		User user = userDao.selectByPK(userid);
		if(user!=null&&CustomerConstant.StageStatus_notSetLoginName.equals(user.getStagestatus())){
			userlogic.delUser(userid, user.getLoginname());
		}
		
        return true;
	}
	
	/**
	 * 从shopid商店中删除多个用户
	 * @param userArr
	 * @param shopid
	 * @param syscode
	 * @return map：result：0失败，1成功；info:成功时返回成功删除的记录数，失败时返回错误编码
	 * @throws Exception
	 */
	public Map delBatchUserInShop(Long[] userArr,Long shopid){
		Map map = new HashMap();
		String result = "0";
		String info = "";
		if(userArr==null||userArr.length<1||shopid==null)
		{
			result = "0";
			info = "error.OrguserAction.deleteOrguserDept.noParamter";
		}
		for(int i=0;i<userArr.length;i++){
			delUserInShop(userArr[i], shopid);
		}
		info = String.valueOf(userArr.length);
		result = "1";
		map.put("result", result);
		map.put("info", info);
		return map;
	}
	
	/**
	 * 根据shopid删除用户商店中的数据
	 * @param shopid
	 * @return
	 * @throws Exception
	 */
	public int delUserByShop(Long shopid){
		if(shopid==null||shopid==null)
			return 0;
		int rows = 0;
		rows = dao.deleteByShop(shopid);
		return rows;
	}
	
	/* 检查用户是否存在,包括用户在某一系统商店中是否存在和用户的全局帐号是否存在。为页面dwr使用
	 */
	public Map isExists(String nickname,String shopcode,String syscode) {
		String result = "1"; // 0代表不存在，1代表已经存在该用户
		String loginname = ""; // 全局用户登陆名
		Map map = new HashMap();
		if(nickname==null||nickname.trim().length()<1
			||shopcode==null||shopcode.trim().length()<1||syscode==null||syscode.trim().length()<1)
		{
			result = "0";
		}else {
			Shop shopvo = shopDao.selectByCode(shopcode);
			AssertUtil.assertNotNull(shopvo, null);
			
			dao = UsershopDaoImpl.getInstance();
			boolean inshop = dao.existsNicknameInShop(nickname, shopvo.getShopid(), syscode);
			if(!inshop){
				loginname = BOFactory_Platform.getUserLogic().geneShopUserLoginName(nickname, shopcode, syscode);
				UserQuery basequeryVO = new UserQuery();
				basequeryVO.setLoginname(loginname);
				userDao = UserDaoImpl.getInstance(); //for the dwr,so init directly
				User uservo = userDao.selectByLogin(loginname);
				if(uservo==null){
					result = "0";
				}				
			}
		}
		map.put("result", result);
		map.put("loginname", loginname);
		return map;
	}
	
	/**
	 * 查询用户加入的所有有效商店记录
	 * @param userid
	 * @param localeid
	 * @return
	 */
	public List<Usershop> qryUserShop(Long userid, Short usershopstatus, Long localeid){
		if(userid==null||localeid==null)
			return new ArrayList<Usershop>();
		List list = dao.qryUserShop(userid, usershopstatus);
		
		if(list!=null&&list.size()>0){
			ShopstyleconfigDao configDao = platform.logicImpl.BOFactory_Platform.getShopstyleconfigDao();
			Shopstyleconfig configVO = null;
			Usershop vo = null;
			Shop shopVO = null;
			for(int i=0;i<list.size();i++){
				vo = (Usershop)list.get(i);
				shopVO = shopDao.selectByPK(vo.getShopid(), localeid);
				vo.setShopname(shopVO.getShopname());
				vo.setShopcode(shopVO.getShopcode());
				//
				configVO = configDao.selectByPK(vo.getShopid());
				if(configVO!=null){
					vo.setShopbannerimg(configVO.getBannerimg());
				}
			}
		}
		return list;
	}
	
	/**
     * 检查用户是否可以访问某个商店，对于可以匿名访问的资源不要调用该方法
     */
    public Usershop checkAccessShop(Long userid, Long shopid, String syscode, Long localeid){
    	if(shopid==null||userid==null)
    		new LogicException(ExceptionConstant.Error_NoAccess_Shop);
    	Usershop vo = dao.selectByLogicPK(shopid, userid);
    	if(vo!=null && UsershopConstant.UserShopStatus_active.equals(vo.getUsershopstatus())){
    		Shop shopVO = shopDao.selectByPK(vo.getShopid(), localeid);
    		vo.setShopcode(shopVO.getShopcode());
    		vo.setShopname(shopVO.getShopname());
    		return vo;
    	}
    	throw new LogicException(ExceptionConstant.Error_NoAccess_Shop);
    }
    
    /**
     * 根据用户id查询用户在商店中的名称，如果不在商店中就返回用户的displayname
     * TODO 此函数以后要改造并且要缓存其中的结果
     * @param shopid
     * @param userid
     * @return String  用户的displayname(loginname)
     */
    public String qryUsernameById(Long shopid, Long userid){
    	if(shopid==null || userid==null)
    		return null;
    	Usershop usershopVO = dao.selectByLogicPK(shopid, userid);
    	String retname = "";
    	if(usershopVO!=null){
    		retname = usershopVO.getNickname()+"("+usershopVO.getLoginname()+")";
    	}else {
    		User userTemp = userDao.selectByPK(userid);
    		retname = userTemp.getDisplayname()+"("+userTemp.getLoginname()+")";
    	}
    	return retname;
    }
    
    /**
     * 根据用户id list查询用户在商店中的名称，如果在就返回nickname，如果用户不在商店中就返回用户的displayname
     * TODO 此函数以后要改造并且要缓存其中的结果
     * @param shopid
     * @param userList
     * @return map, userid为key, value是nickname(loginname)
     */
    public Map<Long,String> qryUsernameByIdList(Long shopid, List<Long> userList){
    	if(userList==null || userList.size()==0)
    		return null;
    	List<Usershop> list = dao.selectByUserId(shopid, userList);
    	Map<Long,String> map = new HashMap<Long,String>();
    	int returncount = 0;
    	if(list!=null){
    	   for(Usershop vo : list){
    		   map.put(vo.getUserid(), vo.getNickname()+"("+vo.getLoginname()+")");
    		   returncount++;
    	   }
    	}
    	// 如果传入的user和查询返回的user数量不同，说明有些user不是该shop中的人员
    	// 这时需要查询user，使用用户的displayname作为返回值
    	if(userList.size()>returncount){
    		User userTemp = null;
    		for(Long useridTemp : userList){
    			if(map.get(useridTemp)==null){
    				userTemp = userDao.selectByPK(useridTemp);
    				if(userTemp!=null){
    					map.put(useridTemp, userTemp.getDisplayname()+"("+userTemp.getLoginname()+")");
    				}
    			}
    		}
    	}
    	return map;
    }
    
    
    /**
     * 根据shopid和userid查询出对应的用户商店对象，同时包括用户的基本user信息
     * @param shopid
     * @param userid
     * @return
     * @throws Exception
     */
    public Usershop selectByLogicPK(Long shopid,Long userid) {
    	Usershop usershopvo = dao.selectByLogicPK(shopid, userid);
    	User uservo = userDao.selectByPK(userid);
    	usershopvo.setDisplayname(uservo.getDisplayname());
    	usershopvo.setEmail(uservo.getEmail());
    	usershopvo.setRegistime(uservo.getRegistime());
    	usershopvo.setTimezone(uservo.getTimezone());
    	usershopvo.setLocaleid(uservo.getLocaleid());
    	return usershopvo;
    }
    
//    /**
//     * 根据用户的名称查询其在商店中的userid, 返回的是userid字符串以','隔开
//     * 如果在商店中没有该用户，会去查询系统用户表
//     * @param displayname
//     * @param shopid
//     * @param onlyInshop 是否只在shop中查询,true表示仅仅查询商店中用户
//     * @return
//     */
//    public String qryUseridByDisplayname(String displayname, Long shopid, boolean onlyInshop){
//    	if(displayname==null||displayname.trim().length()<1){
//    		return null;
//    	}
//    	String useridStr = null;
//    	if(shopid!=null){
//    		useridStr = dao.qryUseridByDisplayname(displayname, shopid);
//    	}
//    	//TODO 说明在本商店中没有该用户，暂时不会查询所有用户表
////    	if(useridStr==null){
////    		
////    	}
//    	return useridStr;
//    }
    
	// 处理event消息
	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String eventType = event.getEventType();
		try {
			String handleType = (String)paraMap.get("handleType");
			if(HandleType_DeleteUserInShop.equals(handleType)){
				Long userid = (Long)paraMap.get("userid");
				String userStr = (String)paraMap.get("useridStr");
				Long shopid = (Long)paraMap.get("shopid");
				Long[] useridArr = null;
				if(userStr!=null && userStr.trim().length()>0){
					String[] useridstrArr = StringUtil.splitString(userStr, ",");
					if(useridstrArr!=null && useridstrArr.length>0){
						useridArr = new Long[useridstrArr.length];
						for(int i=0;i<useridstrArr.length;i++){
							useridArr[i] = new Long(useridstrArr[i]);
						}
					}
				}
				if(useridArr!=null){
					delBatchUserInShop(useridArr, shopid);
				}else if(userid!=null){
					delUserInShop(userid, shopid);
				}	
			}
		} catch (Exception e) {
			log.error("UsershopLogic方法onEvent时出错误, eventType:"+eventType+"|paraMap:"+paraMap, e);
		}
	}
	
    /**
     * static spring getMethod
     */
     public static UsershopLogic getInstance() throws DaoException {
         try{
        	 UsershopLogic logic = (UsershopLogic)BeanFactory.getBeanFactory().getBean("usershopLogic");
             return logic;
         }catch(Exception e) {
        	 log.error("UsershopLogicImpl方法getInstance()时出错误!", e);
             throw new DaoException(e) ;
         }
     }

	public UsershopDao getDao() {
		return dao;
	}

	public void setDao(UsershopDao dao) {
		this.dao = dao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}
	
}
