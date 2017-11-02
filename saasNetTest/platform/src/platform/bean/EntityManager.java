package platform.bean;

import java.io.Serializable;

import commonTool.exception.LogicException;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.shop.vo.Shopcontactinfo;
import platform.shop.vo.Shopdescarticle;
import platform.shop.vo.Shoppost;
import platform.social.vo.Comments;
import platform.social.vo.Leavemessage;
import platform.social.vo.Usecomment;
import platform.vo.Infonews;
import platform.vo.Shop;
import platform.vo.User;

public class EntityManager {

	private static EntityManager instance;
	
	public static EntityManager getInstance(){
		if (instance == null) {  
	        synchronized (EntityManager.class) {  
	           if (instance == null) {  
	        	   instance = new EntityManager();  
	           }  
	        }  
	    }  
	    return instance;  
	}
	
	private EntityManager() { }
	
	/**
	 * 
	 * @param objectType
	 * @param pk
	 * @param pk2 有的表是由多主键组成的
	 * @return
	 */
	public Object getEntity(String objectType, Serializable pk, Serializable pk2){
		Object rtnObj = null;
		if(objectType==null || objectType.trim().length()<1 || pk==null)
			return rtnObj;
		Long pklong = null;
		if(pk instanceof String){
			if(((String)pk).trim().length()>0){
			   pklong = new Long((String)pk);
			}else {
				return null;
			}
		}else {
			pklong = (Long)pk;
		}
//		Long pklong2 = null;
//		if(pk2!=null && (pk2 instanceof String)){
//			if(((String)pk2).trim().length()>0){
//			   pklong2 = new Long((String)pk2);
//			}else {
//				pklong2 = null;
//			}
//		}else {
//			pklong2 = (Long)pk2;
//		}
		
		if(Shop.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getShopDao().selectByPK(pklong, null);
		}else if(User.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getUserDao().selectByPK(pklong);
		}else if(Shopcontactinfo.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getShopcontactinfoDao().selectByPK(pklong);
		}else if(Shoppost.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getShoppostDao().selectByPK(pklong);
		}else if(Comments.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getCommentsDao().selectByPK(pklong);
		}else if(Leavemessage.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getLeavemessageDao().selectByPK(pklong);
		}else if(Usecomment.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getUsecommentDao().selectByPK(pklong);
		}else if(Shopdescarticle.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getShopdescarticleDao().selectByPK(pklong);
		}else if(Infonews.ObjectType.equals(objectType)){
			rtnObj = BOFactory_Platform.getInfonewsDao().selectByPK(pklong);
		}else {
			throw new LogicException(ExceptionConstant.Error_Invalid_Visit).appendExtraInfo_Display("--no such object type:"+objectType);
		}
		return rtnObj;
	}
	
}
