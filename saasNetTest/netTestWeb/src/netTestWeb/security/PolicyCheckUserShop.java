package netTestWeb.security;

import java.util.Map;

import netTest.bean.EntityManager;
import netTest.common.session.LoginInfoEdu;
import platform.logic.Container;
import platform.vo.Shop;
import commonTool.condexec.CallInterface;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;
import commonWeb.base.BaseActionBase;
import commonWeb.security.constant.RolesConstant;

public class PolicyCheckUserShop implements CallInterface{

	public boolean callReturningBoolean(Map inputParams) throws LogicException {
		//ContextObj contObj = (ContextObj)inputParams.get(ConditionalExec.PARAM_NAME_CECONTEXT);
		LoginInfoEdu loginInfo = (LoginInfoEdu)BaseActionBase.getLoginInfo();
		if(loginInfo.isRootLogin()||RolesConstant.hasRole(RolesConstant.ROLE_SysAdmin, loginInfo.getAuthorities())
           	 ||RolesConstant.hasRole(RolesConstant.ROLE_BizDataAdmin, loginInfo.getAuthorities()))
       {
       	   return true;
       }
		
		// 用sessShopid是不是不合适?应该看用户是否属于该商店? 用户如果没有加入商店?
		String sessShopid = (String) inputParams.get("sessShopid");
		String objectID = (String) inputParams.get("objectID");
		String objectID2 = (String) inputParams.get("objectID2");
		// 多个id的字符串，中间以逗号隔开
		String objectIDStr = (String) inputParams.get("objectIDStr");
		String objectType = (String) inputParams.get("objectType");
		
		if(objectType==null || objectType.trim().length()<1 || sessShopid==null || sessShopid.length()<1){
			throw new LogicException("-------- error in PolicyCheckUserShop, no objectType or sessShopid, please check policyaction expression!");
		}
		
		boolean result = false;
		if(objectID!=null && objectID.trim().length()>0){
			result = checkOneEntityInShop(sessShopid, objectType, objectID, objectID2);
		}else if(objectIDStr!=null && objectIDStr.trim().length()>0){
			String[] objectIDArr = StringUtil.splitString(objectIDStr, ",");
			for(String objID : objectIDArr){
				result = checkOneEntityInShop(sessShopid, objectType, objID, null);
				if(!result){
					break;
				}
			}
		}else {
			throw new LogicException("-------- error in PolicyCheckUserShop, no objectID or objectIDStr, please check policyaction expression!");
		}
		return result;
	}

	private boolean checkOneEntityInShop(String sessShopid, String objectType, String objectID, String objectID2){
		Object entity = EntityManager.getInstance().getEntity(objectType, objectID, objectID2);
		AssertUtil.assertNotNull(entity, ExceptionConstantBase.Error_Record_Not_Exists);
		boolean rtn = false;
		if(entity!=null && entity instanceof Container){
			Long shopid = ((Container)entity).getContainerID();
			String containerType = ((Container)entity).getContainerType();
			if(sessShopid!=null && Shop.ObjectType.equals(containerType) && shopid.equals(new Long(sessShopid))){
				rtn = true;
			}
		}
		return rtn;
	}
	
	public String callReturningString(Map inputParams) throws LogicException {
		// TODO Auto-generated method stub
		return null;
	}

}
