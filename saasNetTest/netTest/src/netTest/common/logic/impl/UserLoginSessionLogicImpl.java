package netTest.common.logic.impl;

import java.util.ArrayList;
import java.util.List;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.common.logic.UserLoginSessionLogic;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.orguser.logic.OrgLogic;
import netTest.orguser.vo.Orgbase;
import netTest.orguser.vo.Orguser;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.ProductbaseDao;
import netTest.product.logic.ProductLogic;
import netTest.product.vo.Productbase;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import platform.constant.ShopConstant;
import platform.constant.UsershopConstant;
import platform.dao.ShopDao;
import platform.dao.UsershopDao;
import platform.daoImpl.ShopDaoImpl;
import platform.daoImpl.UsershopDaoImpl;
import platform.logic.UsershopLogic;
import platform.logicImpl.UsershopLogicImpl;
import platform.vo.Shop;
import platform.vo.ShopMini;
import platform.vo.Usershop;

import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonWeb.security.authorized.AuthorizeCheckUtil;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dao.impl.UserRoleDaoImpl;
import commonWeb.security.vo.Roles;

public class UserLoginSessionLogicImpl implements UserLoginSessionLogic {
   
	static Logger log = Logger.getLogger(UserLoginSessionLogicImpl.class.getName());
	
	private static UserRoleDao userroleDao = null;
	private static UsershopDao usershopDao = null;
	private static ShopDao shopDao = null;
	private static UsershopLogic usershoplogic = null;
	
		
	/**
	 * 得到用户参加了哪些shop列表
	 * @param userid
	 * @param localeid
	 * @return
	 */
	@Cacheable(value="netTest.userSessionCache", key="'UserSessionCache.UserJoinedShops~'+#userid")
	public List<ShopMini> getUserJoinedShops(Long userid, Long localeid){
		List<ShopMini> shopList = null;
		List<Usershop> list = usershoplogic.qryUserShop(userid, UsershopConstant.UserShopStatus_active, localeid);
		if(list!=null&&list.size()>0){
			shopList = new ArrayList<ShopMini>();
			for(Usershop temvo : list){
				ShopMini miniVO = new ShopMini();
				miniVO.setShopcode(temvo.getShopcode());
				miniVO.setShopid(temvo.getShopid());
				miniVO.setShopname(temvo.getShopname());
				shopList.add(miniVO);
			}
		}
		return shopList;
	}
	
	/**
	 * 当用户进入一个商店进行管理时，加载用户在该商店中的权限
	 * 管理员管理商店的时候是不能动态切换商店的，只有通过‘已开设商店列表’进入商店
	 * @param userinfo
	 * @param shopid
	 * @throws Exception
	 */
	public void loadAuthorizeAndProductInShop(LoginInfoEdu userinfo, Long shopid) {
		if(userinfo==null || shopid==null || shopid.equals(userinfo.getShopid())){
			return;
		}
		String syscode = CommonConstant.SysCode_Education;
		// 查找商店并检查商店的状态。并且加载商店信息，包括可以访问的商店列表
		Shop shop = shopDao.selectByPK(shopid, userinfo.getLocaleid());
		AssertUtil.assertNotNull(shop, ExceptionConstant.Error_Record_Not_Exists);
		if(!ShopConstant.ShopStatus_run.equals(shop.getShopstatus())){
			throw new LogicException(platform.exception.ExceptionConstant.Error_ShopStatus_Abnormal);
		}		
		// get usershop object
		Usershop usershopVO = usershopDao.selectByLogicPK(shopid, userinfo.getUserid());
		if(usershopVO==null){
			return;
		}
		// TODO 验证用户在商店中的状态是否是可用状态，否则抛异常
		userinfo.setShopid(shopid);
		userinfo.setShopcode(shop.getShopcode());
		userinfo.setShopname(shop.getShopname());
		userinfo.setAreainproduct(usershopVO.getAreainproduct());
        // 设置用户在该商店中的显示名称
		if(usershopVO.getNickname()!=null){
			userinfo.setNickname(usershopVO.getNickname());
		}
		
		// 检查用户在shop中的权限
		List<Long> authorizeidlist = userroleDao.selectUserRole(userinfo.getUserid(),shopid,syscode);
		// 把用户在商店中的权限和用户在系统中的已有的权限合并。(在UserDetailService中会加载用户在系统中的权限)
		// 只有在用户是shop member的前提下调用该方法
		GrantedAuthority[] shopAuth = transToAuthFromRoles(authorizeidlist);
	    // 设置shop authorities
		userinfo.setShopauthorities(shopAuth);
		
        // 查询并设置用户在商店中可以管理的单位，管理员在商店中只能管理一个单位
		if(AuthorizeCheckUtil.containRole(shopAuth, RolesConstant.ROLE_ShopAdmin)
			||AuthorizeCheckUtil.containRole(shopAuth, RolesConstant.ROLE_ShopCreator)){
			OrgLogic orglogic = netTest.bean.BOFactory.getOrgLogic();
			Orgbase orgbase = orglogic.qryTopDept(usershopVO.getShopid());
			if(orgbase!=null){
				userinfo.setOrgbaseid(orgbase.getOrgbaseid());
			    userinfo.setOrgname(orgbase.getOrgname());
			}
		}else {
			Orguser temVO = netTest.bean.BOFactory.getOrguserLogic().selectOrgForUser(userinfo.getUserid(), shopid);
			if(temVO!=null){
			   userinfo.setOrgbaseid(temVO.getOrgbaseid());
			   userinfo.setOrgname(temVO.getOrgname());
			}
		}
		
		// load 用户在商店中可以使用的产品
		loadUserProduct(userinfo, shopid);
	}
	
	/**
	 * 加载用户可以使用的产品信息
	 * @param userinfo
	 * @param shopid
	 */
	public void loadUserProduct(LoginInfoEdu userinfo, Long shopid){
		String[] prodidStrArr = null;
		if(!UsershopConstant.AreaInProduct_all.equals(userinfo.getAreainproduct()))
		{
			prodidStrArr = BOFactory.getUserproductLogic().
			                            getUserprodStr(userinfo.getUserid(), shopid);
		}else if(shopid!=null){
			// 查询商店中所有的商品
			List<Productbase> list = BOFactory.getProductbaseDao().selectByShop(shopid, null, null, null, null, null);
			StringBuffer buffer = new StringBuffer();
			String rtnStr = null;
			if(list.size()>0){
				// 第一个元素是可以use的productid, 第2个元素是可以manage的productid, 第3个是在选中产品的name
				prodidStrArr = new String[3];
				if(list.size()==1){
					prodidStrArr[0] = list.get(0).getProductbaseid().toString();
					prodidStrArr[1] = list.get(0).getProductbaseid().toString();
					prodidStrArr[2] = list.get(0).getProductname();
				} else {
					for(Productbase vo : list){
						buffer.append(vo.getProductbaseid().toString()).append(",");
					}
					rtnStr = buffer.toString();
					rtnStr = rtnStr.substring(0, rtnStr.length()-1);
					prodidStrArr[0] = rtnStr;
					prodidStrArr[1] = rtnStr;
					prodidStrArr[2] = null;
				}
			}
		}
		if(prodidStrArr!=null){
			userinfo.setProdIdUseStr(prodidStrArr[0]);
			userinfo.setProdIdMagStr(prodidStrArr[1]);
            // 如果只有一个产品，则设置该产品为默认选中的产品
			if(prodidStrArr[2]!=null&&!prodidStrArr[2].equals("")){
				userinfo.setProductid(new Long(prodidStrArr[0]));
				userinfo.setProductname(prodidStrArr[2]);
			}
		}
	}
	
	/**
	 * 加载用户在shop中的角色
	 * @param userid
	 * @param shopid
	 * @param sessShopID: shopid in session
	 * @param sessShopAuthArr: session shop authority
	 */
	public GrantedAuthority[] loadContainerAuthority(Long userid, Long shopid, 
			Long sessShopID, GrantedAuthority[] sessShopAuthArr) {
		if(shopid==null || userid==null){
			return null;
		}
		if(sessShopID!=null && shopid.equals(sessShopID) && sessShopAuthArr!=null){
			return sessShopAuthArr;
		}
		String syscode = CommonConstant.SysCode_Education;
		// 查找商店并检查商店的状态。并且加载商店信息，包括可以访问的商店列表
		Shop shop = (Shop)shopDao.selectByPK(shopid, null);
		AssertUtil.assertNotNull(shop, ExceptionConstant.Error_Record_Not_Exists);
		if(!ShopConstant.ShopStatus_run.equals(shop.getShopstatus())){
			throw new LogicException(platform.exception.ExceptionConstant.Error_ShopStatus_Abnormal);
		}		
		// get usershop object
		Usershop usershopVO = usershopDao.selectByLogicPK(shopid, userid);
		if(usershopVO==null){
			return null;
		}
        
		// 检查用户在shop中的权限
		List<Long> authorizeidlist = userroleDao.selectUserRole(userid,shopid,syscode);
		// 把用户在商店中的权限和用户在系统中的已有的权限合并。(在UserDetailService中会加载用户在系统中的权限)
		// 只有在用户是shop member的前提下调用该方法
		GrantedAuthority[] shopAuth = transToAuthFromRoles(authorizeidlist);
	    return shopAuth;
	}
	
	// 将role转换成Authority, 并且添加默认的用户在商店中的角色
	// 该方法必须是用户是shop的Member的情况下才能调用
	private GrantedAuthority[] transToAuthFromRoles(List<Long> idlist){
		if(idlist==null||idlist.size()<1)
			return new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_ShopMember)}; 
		
		GrantedAuthorityImpl[] auths = new GrantedAuthorityImpl[idlist.size()+1];
		Long roleid = null;
		Roles rolevo = null;
		RolesDao roledao = commonWeb.security.logic.BOFactory.getRolesDao();
		for(int i=0;i<idlist.size();i++){
			roleid = idlist.get(i);
			rolevo = roledao.selectByPK(roleid);
			auths[i] = new GrantedAuthorityImpl(rolevo.getCode());
		}
		auths[idlist.size()] = new GrantedAuthorityImpl(RolesConstant.ROLE_ShopMember);
		return auths;
	}
	
	/**
	 * 查找用户可用的产品集合，如果是可以查看商店所有产品则查询该商店的所有产品
	 * usetype: 参见UserproductConstant中produsertype
	 */
	public List<Productbase> qryCanuseProd(Long shopid,Long userid,Short areainproduct, Long localeid, 
			Short usetype, String prodIdUseStr, String prodIdMagStr){
		List<Productbase> list = null;
		ProductbaseDao dao = BOFactory.getProductbaseDao();
		ProductLogic prodLogic = BOFactory.getProductLogic();
		if(areainproduct!=null&&UsershopConstant.AreaInProduct_all.equals(areainproduct)){
			list = (List<Productbase>)dao.selectByShop(shopid, null, null, null, null, null);
		}else {
			String pkStr = null;
			if(UserproductConstant.ProdUseType_userNormal.equals(usetype)){
				pkStr = prodIdUseStr;
			}else if(UserproductConstant.ProdUseType_operatorMag.equals(usetype)){
				pkStr = prodIdMagStr;
			}else {
				if(prodIdUseStr!=null&&prodIdUseStr.trim().length()>0){
					if(prodIdUseStr.endsWith(",")){
						pkStr = prodIdUseStr;
					}else{
						pkStr = prodIdUseStr + ",";
					}
				}
				if(prodIdMagStr!=null&&prodIdMagStr.trim().length()>0){
					if(prodIdMagStr.startsWith(",")){
						pkStr = pkStr + prodIdMagStr.substring(1);
					}else {
						pkStr += prodIdMagStr;
					}
				}
			}
			list = prodLogic.selectByPkStr(pkStr, localeid);
		}

		// TODO 按目录排序下产品
		
		return list;
	}
	
    /**
     * static spring getMethod
     */
     public static UserLoginSessionLogic getInstance() {
    	 UserLoginSessionLogic logic = (UserLoginSessionLogic)BeanFactory.getBeanFactory().getBean("userLoginSessionLogic");
         if(userroleDao==null){
        	 userroleDao = UserRoleDaoImpl.getInstance();
         }
         if(usershopDao==null){
        	 usershopDao = UsershopDaoImpl.getInstance();
         }
         if(shopDao==null){
        	 shopDao = ShopDaoImpl.getInstance();
         }
         if(usershoplogic==null){
        	 usershoplogic = UsershopLogicImpl.getInstance();
         }
    	 return logic;
     }
	
}
