package commonWeb.security.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import commonTool.cache.CacheInf;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.impl.ResourcesDaoImpl;
import commonWeb.security.vo.Resources;

/**
 * 处理资源的缓存与刷新
 * 不足之处：如果以后允许客户自己修改Role,那么同一个Resource对应的Roles也包含他们自己建立的Role,如果客户仅仅在修改
 * 自己商店的Role，也会造成该Role对应的Resource被刷新，这样肯定不好，如果每家商店都这么做，那么统一Resource就会被经常刷新
 * @author peter
 *
 */
public class ResourceLoader {

	// ~ Static fields/initializers
	// =============================================

	private static final Log logger = LogFactory.getLog(ResourceLoader.class);
	
	public static final String CachePrefix = "RES";
	
	private String syscode;
	

	// ~ Instance fields
	// ========================================================

	CacheInf cache;
	
	private ResourcesDao resDao;

	// ~ Methods
	// ================================================================

	public ResourcesDao getResDao() {
		if(resDao==null){
			resDao = ResourcesDaoImpl.getInstance();
		}
		return resDao;
	}

	public void setResDao(ResourcesDao resDao) {
		this.resDao = resDao;
	}

	public void setCache(CacheInf cache) {
		this.cache = cache;
	}

	public CacheInf getCache() {
		return this.cache;
	}


	public ResourceInf get(String resString) {
		if(resString==null||resString.trim().length()<1)
			return null;
		String cachekey = makeCacheKey(resString);
		ResourceInf resource = (ResourceInf)cache.get(cachekey);
		if(resource==null){ // 如果cache中为空，则重新在数据库中查询该链接地址的资源，并且放入cache中
			synchronized(cachekey){
			   if(!isAlreadyLoad(cachekey)){
			      Resources resc = getRescByResStringFromDB(resString);
				  if(resc!=null){
				     put(resString,resc);
					 resource = resc;
				  }
			   }else {
				  resource = (ResourceInf)cache.get(cachekey);
			   }
			}
		}
		return resource;
	}

	private void put(String resString, Object obj) {
		if(resString==null||resString.trim().length()<1||obj==null)
			return;
		String cacheKey = makeCacheKey(resString);
		Resources resc = (Resources)obj;
		cache.put(cacheKey, obj);
		// 更新资源类型和资源集合的对应关系
		String typecacheKey = makeTypeKey(resc.getResType());
		Object typeObj = cache.get(typecacheKey);
		List<String> restringList = null;
		if(typeObj!=null){
			restringList = (List<String>)typeObj;
		}else{
			restringList = new ArrayList<String>();
		}
		restringList.add(resc.getResString());
		cache.put(typecacheKey, restringList);
		// 更新已经加载的对象列表
		putAlreadyLoad(cacheKey);
	}

	public void flushCache(String resString) {
		String cachekey = makeCacheKey(resString);
		ResourceInf resc = this.get(resString);
		if(resc!=null){
			// 更新资源类型和资源集合的对应关系
			String typecacheKey = makeTypeKey(resc.getResType());
			Object typeObj = cache.get(typecacheKey);
			List<String> restringList = (List<String>)typeObj;
			restringList.remove(resString);
			cache.put(typecacheKey, restringList);
			// 移除对应的资源
			cache.flushCache(cachekey);
			if (logger.isDebugEnabled()) {
				logger.debug("--Cache remove: " + cachekey);
			}
		}
		// 移除已经加载过的对象
		removeKeyFromAlreayLoad(cachekey);
	}

//	public List getAllResources() {
//		return this.cache.getKeys();
//	}
	
	public void flushAll() {
		this.cache.flushAll();
	}
	
	/* 
	 * 根据资源连接查找资源
	 * (non-Javadoc)
	 * @see org.springside.components.acegi.service.AuthenticationService#getResources()
	 */
	public Resources getRescByResStringFromDB(String resString) {
		if(resString==null||resString.trim().length()<1)
			return null;
		// 根据resString查询所属的Role集合,一般来说只可能查询到一个结果
		Collection<GrantedAuthority> auths = null;
		ResourcesDao resDao = ResourcesDaoImpl.getInstance();
		Resources vo = resDao.selectByRes(syscode, resString);
		if(vo!=null){
			List<String> roleList = resDao.qryRolesByRes(syscode, resString); 
			if(roleList!=null && roleList.size()>0){
				auths = new ArrayList<GrantedAuthority>();
				for(String roleName : roleList){
					auths.add(new GrantedAuthorityImpl(roleName));
				}
			}
			if(auths!=null&&auths.size()>0){
				GrantedAuthority[] authArr = new GrantedAuthority[auths.size()];
				auths.toArray(authArr);
				vo.setAuthorities(authArr);
			}
		}
		return vo;
	}   
	
	/**
	 * 根据资源类新取得资源的连接地址resString
	 * @param type
	 * @return
	 */
	public List<String> getRestringByType(String type){
		if(type==null||type.trim().length()<1)
			return null;
		List<String> resList = null;
		String typeCacheKey = makeTypeKey(type);
		Object obj = cache.get(typeCacheKey);
		if(obj!=null){
			resList = (List<String>)obj;
		}else {
			try {
				initData();
				obj = cache.get(typeCacheKey);
				if(obj!=null){
					resList = (List<String>)obj;
				}
			} catch (Exception e) {
				logger.error("error in getRestringByType, initData error:", e);
			}
		}
		return resList;
	}
	
	private List<Resources> getAllResourceFromDB(){
		ResourcesDao resDao = getResDao();
		List<Resources> rescList = resDao.qryAllResource(syscode);
		if(rescList!=null&&rescList.size()>0){
			List<String> roleList = null;
			GrantedAuthority[] auths = null;
			for(Resources vo : rescList){
				//TODO 此处有问题，性能不好，每个resc需要查询一次db
				roleList = resDao.qryRolesByRes(syscode, vo.getResString()); 
				if(roleList!=null && roleList.size()>0){
					auths = new GrantedAuthority[roleList.size()];
					for(int i=0;i<roleList.size();i++){
						auths[i] = new GrantedAuthorityImpl(roleList.get(i));
					}
				}
				vo.setAuthorities(auths);
			}
		}
		return rescList;
	}
	
	/**
	 * 判断key是否已经被加载过了，如果加载过了就不用再次查询数据库加载资源了。
	 * 但是，如果这个already load的对象由于过期或别的原因而丢失了，可能会多加载的情况
	 * (某个连接不在定义的资源中，并且已经加载过了，但是already load对象丢失了，所以要多加载一次，但是性能应该影响不大)
	 * 调用该方法，一定是在：key对应的资源在cache中不存在
	 * @param key
	 * @return
	 */
	private boolean isAlreadyLoad(String cachekey){
		boolean result = false;
		Map<String,String> map = null;
		String alreayKey = makeAlreadyLoadKey();
		Object obj = cache.get(alreayKey);
		if(obj!=null){
			map = (Map)obj;
		}else {
			map = new HashMap<String,String>();
			cache.put(alreayKey, map);
		}
		String stringObj = map.get(cachekey);
		if(stringObj!=null){
			result = true;
		}
		return result;
	}
	
	private boolean isAlreadyLoad(){
		String alreayKey = makeAlreadyLoadKey();
		Object obj = cache.get(alreayKey);
		if(obj!=null){
			return true;
		}else {
			return false;
		}
	}
	
	private void putAlreadyLoad(String cachekey){
		Map<String,String> map = null;
		String alreayKey = makeAlreadyLoadKey();
		Object obj = cache.get(alreayKey);
		if(obj!=null){
			map = (Map)obj;
		}else {
			map = new HashMap<String,String>();
			cache.put(alreayKey, map);
		}
		map.put(cachekey, cachekey);
	}
	
	/**
	 * 从已加载的对象列表中删除某个资源连接
	 * @param resString
	 */
	private void removeKeyFromAlreayLoad(String cacheKey) {
		String alreayKey = makeAlreadyLoadKey();
		Object obj = cache.get(alreayKey);
		Map<String,String> map = null;
		if(obj!=null){
			map = (Map)obj;
			map.remove(cacheKey);
		}else {
			map = new HashMap<String,String>();
			cache.put(alreayKey, map);
		}
	}
	
	// initial resource data, call by DataBaseMethodInvocationDefinitionSource or CacheBaseUrlDefinitionSource
	public synchronized void initData() throws Exception {
		if(!isAlreadyLoad()){
			List<Resources> rescList = getAllResourceFromDB();
			if(rescList!=null&&rescList.size()>0){
				for(Resources resc : rescList){
					put(resc.getResString(),resc);
				}
			}
		}
	}
	
	/**
	 * 产生cache key
	 * @param resString
	 * @return
	 */
	public String makeCacheKey(String resString){
		return CachePrefix+CacheInf.CacheSplitChar+resString;
	}
	
	private String makeTypeKey(String restype){
		return CachePrefix+CacheInf.CacheSplitChar+"ResType"+CacheInf.CacheSplitChar+restype;
	}
	
	private String makeAlreadyLoadKey(){
		return CachePrefix+CacheInf.CacheSplitChar+"AlreadyLoad"+CacheInf.CacheSplitChar+"ResourceCache";
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

}