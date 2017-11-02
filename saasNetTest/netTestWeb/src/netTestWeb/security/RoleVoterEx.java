package netTestWeb.security;

import java.util.Map;
import netTest.bean.BOFactory;
import netTest.bean.EntityManager;
import netTest.common.session.LoginInfoEdu;
import netTest.exam.vo.Testinfo;
import netTest.learncont.vo.Learncontent;
import netTest.product.vo.ProductMini;
import org.springframework.security.GrantedAuthority;
import platform.logic.Container;
import platform.vo.Shop;
import platform.vo.ShopMini;
import commonTool.exception.ExceptionConstantBase;
import commonTool.util.AssertUtil;
import commonWeb.security.authentication.UserinfoSession;

public class RoleVoterEx extends commonWeb.security.voter.RoleVoterEx {

	public static final String Key_Param_ShopID = "shopid";
	
	@Override
	public Container getContainer(Object targetObject, Map<String, String[]> paramap) {
		if(targetObject!=null && targetObject instanceof Container){
			return (Container)targetObject;
		}else if(paramap!=null && paramap.get(Key_Param_ShopID)!=null
				  && paramap.get(Key_Param_ShopID).length>0)
		{
			ShopMini shopvo = null;
			String shopidStr = paramap.get(Key_Param_ShopID)[0];
			if(shopidStr!=null && shopidStr.trim().length()>0 && !"null".equals(shopidStr)){
				shopvo = new ShopMini();
				shopvo.setShopid(new Long(shopidStr));
			}
			return shopvo;
		}else {
		    return null;
		}
	}

	@Override
	public GrantedAuthority[] loadContainerAuth(UserinfoSession sessinfo, Container container) {
		GrantedAuthority[] authArr = null;
		if(container!=null && container.getContainerID()!=null && 
				sessinfo!=null && (sessinfo instanceof LoginInfoEdu)){
			Long containerID = new Long(container.getContainerID());
			if(Shop.ObjectType.equals(container.getContainerType())){
				authArr = BOFactory.getUserLoginSessionLogic().loadContainerAuthority(sessinfo.getUserid(), 
						                                                              containerID, sessinfo.getShopid(), sessinfo.getShopauthorities());
			}else if(Testinfo.ObjectType.equals(container.getContainerType())){
				authArr = BOFactory.getTestinfoLogic().loadContainerAuthority(sessinfo.getUserid(), 
															containerID, sessinfo.getShopid());
			}else if(ProductMini.ObjectType.equals(container.getContainerType())){
				authArr = BOFactory.getProductLogic().loadContainerAuthority(sessinfo.getUserid(), 
										containerID, sessinfo.getShopid(), sessinfo.getAuthorities());
			}else if(Learncontent.ObjectType.equals(container.getContainerType())){
				authArr = BOFactory.getLearncontentLogic().loadContainerAuthority(sessinfo.getUserid(), 
										containerID, sessinfo.getShopid(), sessinfo.getAuthorities());
			}
		}
		return authArr;
	}

	@Override
	public Object loadTargetObject(String objecttype, String objectid) {
		Object entity = null;
		if(objecttype!=null && objecttype.trim().length()>0 
			 && (objectid!=null && objectid.trim().length()>0 && !"null".equals(objectid)))
		{
		   entity = EntityManager.getInstance().getEntity(objecttype, objectid, null);
		   AssertUtil.assertNotNull(entity, ExceptionConstantBase.Error_Record_Not_Exists);
		}
		return entity;
	}

	@Override
	public GrantedAuthority[] loadObjectAuth(UserinfoSession sessinfo, Object targetObject) {
		GrantedAuthority[] objArr = null;
		if(targetObject!=null && (targetObject instanceof Learncontent)){
			objArr = BOFactory.getLearncontentLogic().loadObjectAuthority(sessinfo.getUserid(), null, targetObject);
		}
		return objArr;
	}

}
