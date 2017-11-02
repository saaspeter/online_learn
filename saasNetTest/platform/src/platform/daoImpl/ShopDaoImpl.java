package platform.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.dao.ShopDao;
import platform.dto.ShopQuery;
import platform.vo.Shop;
import platform.vo.Shopvalue;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;

// 本类的cache主要是selectByVOPage, 前提是一个shop只能设置一个locale，
// 如果可以有多个locale, 则需要修改cache设置
public class ShopDaoImpl extends BaseDao implements ShopDao {

	static Logger log = Logger.getLogger(ShopDaoImpl.class.getName());
	
	public static final String ShopCategoryCacheKey = "shopCateCache";
	public static final String ShopRegionCacheKey = "shopRegionCache";
	public static final String ShopLocaleCacheKey = "shopLocaleCache";
	public static final String ShopOwnertypeCacheKey = "shopOwnertypeCache";
	public static final String ShopStatusCacheKey = "shopStatusCache";
	public static final String ShopOpentypeCacheKey = "ShopOpentypeCacheKey";
	
    public ShopDaoImpl() {
        super();
    }
    
    /**
     * 根据shopcode查询商店
     */
    @Cacheable(value="platform.shopCache", key="'ShopDao.selectByCode~'+#shopcode", unless="#result==null")
    public Shop selectByCode(String shopcode) {
    	if(shopcode==null||shopcode.trim().length()<1)
    		return null;
    	Shop record = (Shop) this.queryForObject("Shop.selectByCode", shopcode);
    	
    	// add cache key into key associate map
		CacheSynchronizer.getInstance().buildAssoc("platform.shopCache", 
				"ShopDao.selectByCode~"+shopcode, new String[]{Shop.ObjectType+":"+record.getShopid()});
    	
		return record;
    }
    
//  @Cacheable(value="platform.shopCache", key="'ShopDao.selectByPK~'+#pk")
//	public Shop selectByPK(Long pk) {
//		Shop record = (Shop) this.queryForObject("Shop.selectByPK_origin", pk);
//		return record;
//	}
    
    /**
     * select some record by PK
     */
    @Cacheable(value="platform.shopCache", key="'ShopDao.selectByPK~shop:'+#shopid+'~'+#localeid", unless="#result==null")
    public Shop selectByPK(Long shopid,Long localeid) {
    	if(shopid==null)
    		return null;
    	Shop record = null;
       	if(localeid!=null){
        	record = new Shop();
        	record.setShopid(shopid);
        	record.setLocaleid(localeid);
			record = (Shop) this.queryForObject("Shop.selectByPK", record);
			if(record==null){
				record = (Shop) this.queryForObject("Shop.selectByPK_origin", shopid);
			}
       	}else {
       		record = (Shop) this.queryForObject("Shop.selectByPK_origin", shopid);
       	}
       	
        // add cache key into key associate map
		CacheSynchronizer.getInstance().buildAssoc("platform.shopCache", 
				"ShopDao.selectByPK~"+Shop.ObjectType+":"+shopid+"~"+localeid);
		
		return record;
    }
        
    /**
     * select records including all fields by queryVO
     * 
     */
    // No need to cache it
    public List selectByVO(ShopQuery queryVO) {
		if(queryVO==null)
			return null;
		List list = this.queryForList("Shop.selectByVO", queryVO);
		return list;
    }
    
    /**
     * when shop category, shop region, shop ownertype, shop locale, shop status  Changed,
     * then flush this cache
     */
    @Cacheable(value="platform.shopCache", 
    		key="'ShopDao.selectByVOPage~'+#categoryid+'~'+#regioncode+'~'+#ownertype+'~'+#opentype+'~'+#localeid+'~'+#shopstatus+'~'+#pageIndex+'~'+#pageSize", 
    		condition="#searchText==null or #searchText==''")
    public Page selectByVOPage(Long categoryid, String regioncode, Short ownertype, Short opentype, 
							   String searchText, Long localeid, Short shopstatus,	
							   int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        
        ShopQuery queryVO = new ShopQuery();
        queryVO.setCategoryid(categoryid);
        queryVO.setRegioncode(regioncode);
        queryVO.setOwnertype(ownertype);
        queryVO.setOpentype(opentype);
        queryVO.setSearchtext(searchText);
        queryVO.setLocaleid(localeid);
        queryVO.setShopstatus(shopstatus);
       
        Page page = queryForPage("Shop.selectByVO", queryVO, pageIndex, pageSize, total);
        
        // add cache key into key assoc map
		if(searchText==null || searchText.trim().length()<1){
		    CacheSynchronizer.getInstance().buildAssoc("platform.shopCache", 
		    				"ShopDao.selectByVOPage~"+categoryid+"~"+regioncode+"~"+ownertype
		    				+"~"+opentype+"~"+localeid+"~"+shopstatus+"~"+pageIndex+"~"+pageSize, 
		    				new String[]{ShopCategoryCacheKey+":"+categoryid, 
		    		                     ShopRegionCacheKey+":"+regioncode,
		    		                     ShopOwnertypeCacheKey+":"+ownertype,
		    		                     ShopOpentypeCacheKey+":"+opentype,
		    		                     ShopLocaleCacheKey+":"+localeid,
		    		                     ShopStatusCacheKey+":"+shopstatus});
		}
        
        return page;
    }
        
    /**
     * 查询user创建了多少个商店
     * @param userid
     * @return
     */
    public int selectCountByOwner(Long userid){
    	int rtnum = 0;
    	if(userid!=null){
    	   Integer num = (Integer) this.queryForObject("Shop.selectCountByOwner", userid);
    	   if(num!=null){
    		   rtnum = num;
    	   }
    	}
    	return rtnum;
    }
    
    /**
     * 检查是否有重code的商店
     * @return true:有重名商店, false:没有重名商店
     */
    public boolean existcheckByCode(String shopcode) {
    	if(shopcode==null||shopcode.trim().length()<1)
    		return false;
    	shopcode = shopcode.trim();
    	Integer ret = (Integer) this.queryForObject("Shop.existcheckByCode", shopcode);
       	if(ret!=null && ret==1)
		   return true;
       	else
       	   return false;
    }
    
    /**
     * 查询该用户已经设置过商店的Locale集合，用于根据Locales选择商店
     * 用户已经设置了商店的I18n的List集合
     * @param userid
     * @return I18n的List集合
     */
    public List selectUserSetLocales(Long userid) {
    	if(userid==null)
    		return null;
		List list = this.queryForList("Shop.selectUserSetLocales", userid);
		return list;
    }
    
    /**
     * 查询该商店已经设置过的Locale集合，用于根据Locales查看商店信息
     * 商店已经设置的I18n的List集合
     * @param userid
     * @return I18n的List集合
     */
    public List selectShopSetLocales(Long userid) {
    	if(userid==null)
    		return null;
		List list = this.queryForList("Shop.selectShopSetLocales", userid);
		return list;
    }   
       
    /**
     * insert a record include shop and shopValue
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Shop record) {
    	if(record==null)
    		return null;
		Long shopid = (Long)super.insert("Shop.insert", record);
		if(record.getShopvalueid()==null){
			Shopvalue valuevo = new Shopvalue();
			valuevo.setShopid(shopid);
			valuevo.setLocaleid(record.getLocaleid());
			valuevo.setShopdesc(record.getShopdesc());
			valuevo.setShopname(record.getShopname());
			super.insert("Shopvalue.insert", valuevo);
		}
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", Shop.ObjectType, shopid.toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopLocaleCacheKey, record.getLocaleid().toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopOwnertypeCacheKey, record.getOwnertype().toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopOpentypeCacheKey, record.getOpentype().toString());
		
		return shopid;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Shop record) {
    	if(record==null||record.getShopid()==null)
    		return 0;
    	// get original record
    	Shop oldvo = selectByPK(record.getShopid(), null);
    	// update
		int rows = super.update("Shop.updateByPK", record);
		if(record.getShopvalueid()!=null){
			Shopvalue valuevo = new Shopvalue();
			valuevo.setShopvalueid(record.getShopvalueid());
			valuevo.setShopid(record.getShopid());
			valuevo.setLocaleid(record.getLocaleid());
			valuevo.setShopdesc(record.getShopdesc());
			valuevo.setShopname(record.getShopname());
			super.update("Shopvalue.updateByPK", valuevo);
		}
		if(record.getShopcode()==null){
			record = ShopDaoImpl.getInstance().selectByPK(record.getShopid(), null);
		}
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", Shop.ObjectType, record.getShopid().toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopLocaleCacheKey, record.getLocaleid().toString());
		// when ownertype changes, need flush old value and new value related cache
		if(oldvo.getOwnertype()!=null && !oldvo.getOwnertype().equals(record.getOwnertype())){
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopOwnertypeCacheKey, oldvo.getOwnertype().toString());
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopOwnertypeCacheKey, record.getOwnertype().toString());
		}
		if(oldvo.getOpentype()!=null && !oldvo.getOpentype().equals(record.getOpentype())){
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopOpentypeCacheKey, oldvo.getOpentype().toString());
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopOpentypeCacheKey, record.getOpentype().toString());
		}
		return rows;
    }
    
    /**
     * 根据主键更新商店的状态
     * @param vo
     * @return
     */
    public boolean updateStatus(Shop vo)  {
    	if(vo==null||vo.getShopstatus()==null||vo.getShopid()==null)
    		return false;
    	
    	Shop oldvo = selectByPK(vo.getShopid(), null);
    	if(!vo.getShopstatus().equals(oldvo.getShopstatus())){
    		super.update("Shop.updateStatus", vo);
    		
    		// flush cache
    		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", Shop.ObjectType, vo.getShopid().toString());
    		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopStatusCacheKey, oldvo.getShopstatus().toString());
    		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopStatusCacheKey, vo.getShopstatus().toString());
    	}
		return true;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @
     */
    public Shop save(Shop record) {
    	if(record==null)
    		return null;
		if(record.getShopid()==null||record.getShopid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setShopid(pkValue);
			return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * 删除了商店的多国语言值Shopvalue、商店功能Shopfunc、商店选择的课程目录和商店本身shop
     */
    public int deleteByPK(Long pk) {
    	if(pk==null)
    		return 0;
    	
    	Shop vo = selectByPK(pk, null);
    	
		super.delete("Shopvalue.deleteByShopid", pk);
		super.delete("Shopfunc.deleteByShopid", pk);
		super.delete("Shopstyleconfig.deleteByPK", pk);
		int rows = super.delete("Shop.deleteByPK", pk);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", Shop.ObjectType, pk.toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", ShopLocaleCacheKey, vo.getLocaleid().toString());
		
		return rows;
    }
    
    
    /**
     * static spring getMethod
     */
     public static ShopDao getInstance() {
       	 ShopDao dao = (ShopDao)BeanFactory.getBeanFactory().getBean("shopDaoProxy");
         return dao;
     }
    
}
