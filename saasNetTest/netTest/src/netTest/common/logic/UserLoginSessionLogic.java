package netTest.common.logic;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.GrantedAuthority;

import platform.vo.ShopMini;

import netTest.common.session.LoginInfoEdu;
import netTest.product.vo.Productbase;


public interface UserLoginSessionLogic {

	/**
	 * 查找用户可用的产品集合
	 */
	public List<Productbase> qryCanuseProd(Long shopid,Long userid,Short areainproduct, Long localeid, Short usetype, String prodIdUseStr, String prodIdMagStr);
	
	/**
	 * 得到用户参加了哪些shop列表
	 * @param userid
	 * @param localeid
	 * @return
	 */
	public List<ShopMini> getUserJoinedShops(Long userid, Long localeid);
	
	/**
	 * 当用户进入一个商店进行管理时，加载用户在该商店中的权限
	 * 管理员管理商店的时候是不能动态切换商店的，只有通过‘已开设商店列表’进入商店
	 * @param userinfo
	 * @param shopid
	 * @throws Exception
	 */
	public void loadAuthorizeAndProductInShop(LoginInfoEdu userinfo, Long shopid);
	
	/**
	 * 加载用户在shop中的角色
	 * @param userid
	 * @param shopid
	 * @param sessShopID: shopid in session
	 * @param sessShopAuthArr: session shop authority
	 */
	public GrantedAuthority[] loadContainerAuthority(Long userid, Long shopid, 
			Long sessShopID, GrantedAuthority[] sessShopAuthArr) ;
	
	/**
	 * 加载用户可以使用的产品信息
	 * @param userinfo
	 * @param shopid
	 */
	public void loadUserProduct(LoginInfoEdu userinfo, Long shopid);
	
}
