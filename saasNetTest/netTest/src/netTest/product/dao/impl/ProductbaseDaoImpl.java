package netTest.product.dao.impl;

import java.util.ArrayList;
import java.util.List;

import netTest.bean.BeanFactory;
import netTest.product.dao.ProductbaseDao;
import netTest.product.dto.ProductbaseQuery;
import netTest.product.vo.Productbase;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.daoImpl.ShopDaoImpl;
import platform.vo.Shop;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.DaoException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;

public class ProductbaseDaoImpl extends BaseDao implements ProductbaseDao {

	static Logger log = Logger.getLogger(ProductbaseDaoImpl.class.getName());
	
	private final static String ShopInProductType = "ShopInProdCache";
	private final static String ProductSatisType = "ProdSatisCache";
	public final static String CateForProductType = "CateForProdCache";
	//public final static String IsGoodProdTypeCache = "IsGoodProdCache";
	private final static String ProdPaytypeCache = "PaytypeCache";
	private final static String ProdOpentypeCache = "ProdOpentypeCache";
	private final static String ProdStatusCache = "ProdStatusCache";
	private final static String LocaleInProductCache = "LocaleInProdCache";
	
    /**
     *
     */
    public ProductbaseDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="netTest.productCache", key="'ProductDao.selectByPK~'+#pk", unless="#result==null")
    public Productbase selectByPK(Long pk) {
    	if(pk==null)
    		return null;
		Productbase record = (Productbase) this.queryForObject("Productbase.selectByPK", pk);
		
		// add cache key into key associate map
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
				"ProductDao.selectByPK~"+pk, new String[]{Productbase.ObjectType+":"+pk});
		}
		
		return record;
    }
    
    /**
     * select product detail description
     */
    @Cacheable(value="netTest.productCache", key="'ProductDao.selectProdDesc~'+#pk", unless="#result==null")
    public Productbase selectProdDesc(Long pk) {
    	if(pk==null)
    		return null;
		Productbase record = (Productbase) this.queryForObject("Productbase.selectProdDesc", pk);
		
		// add cache key into key associate map
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
				"ProductDao.selectProdDesc~"+pk, new String[]{Productbase.ObjectType+":"+pk});
		}
		
		return record;
    }
    
    /**
     * select product satis information
     */
    @Cacheable(value="netTest.productCache", key="'ProductDao.selectProdSatis~'+#pk", unless="#result==null")
    public Productbase selectProdSatis(Long pk) {
    	if(pk==null)
    		return null;
		Productbase record = (Productbase) this.queryForObject("Productbase.selectProdSatis", pk);
		
		// add cache key into key associate map
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
				"ProductDao.selectProdSatis~"+pk, 
				new String[]{Productbase.ObjectType+":"+pk, ProductSatisType+":"+pk});
		}
		
		return record;
    }
    
    /**
     * 查询商店有的产品数目
     * @param shopid
     * @return
     */
    public Integer selShopProdCount(Long shopid) {
    	if(shopid==null)
    		return null;
    	Integer count = (Integer) this.queryForObject("Productbase.selShopProdCount", shopid);
    	if(count==null)
    		count = 0;
		return count;
    }
    
//    /**
//     * select records by shop
//     */
//    public List<Productbase> selectByShop(Long shopid) {
//		if(shopid==null)
//			return new ArrayList<Productbase>();
//		List<Productbase> list = (List<Productbase>)this.queryForList("Productbase.selectByShop", shopid);
//		return list;
//    }
    
    /**
     * 查询shop内的product
     */
    @Cacheable(value="netTest.productCache", 
    		key="'ProductDao.selectByShop~shop:'+#shopid+'~'+#categoryid+'~'+#promotable+'~'+#paytype+'~'+#isopen+'~'+#status")
    public List<Productbase> selectByShop(Long shopid, Long categoryid, 
    		Short promotable, Short paytype, Short isopen, Short status) 
    {
		AssertUtil.assertNotNull(shopid, null);
		
    	ProductbaseQuery queryVO = new ProductbaseQuery();
    	queryVO.setShopid(shopid);
    	queryVO.setCategoryid(categoryid);
    	queryVO.setPromotable(promotable);
    	queryVO.setPaytype(paytype);
    	queryVO.setIsopen(isopen);
    	queryVO.setStatus(status);
    	List<Productbase> list = (List<Productbase>)this.queryForList("Productbase.selectByVO", queryVO);
    	
    	// add cache assoc
    	CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
				"ProductDao.selectByShop~"+Shop.ObjectType+":"+shopid
				+"~"+categoryid+"~"+promotable+"~"+paytype+"~"+isopen+"~"+status, 
				new String[]{Shop.ObjectType+":"+shopid, ShopInProductType+":"+shopid});
    	
		return list;
    }
        
    /**
     * select records by queryVO
     */
    public List selectByVO(ProductbaseQuery queryVO) {
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Productbase.selectByVO", queryVO);
		return list;
    }
    
    /**
     * select records by pk string, the pkStr joined by comma
     */
    public List<Productbase> selectByPkStr(String pkStr) {
		if(pkStr==null||pkStr.trim().length()<1)
			return new ArrayList<Productbase>();

		List<Productbase> list = new ArrayList<Productbase>();
		Productbase vo = null;
		String[] prodidArr = StringUtil.splitString(pkStr, ",");
		for(String id : prodidArr){
			if(id!=null && id.trim().length()>0){
				vo = getInstance().selectByPK(new Long(id.trim()));
				if(vo!=null){
				   list.add(vo);
				}
			}
		}
		return list;
    }
    
    /**
     * 查询学习目前熟悉人数最多的10门课程，cache不需要自动刷新，时间到后，自动刷新
     * @param categoryid
     * @return
     */
    @Cacheable(value="netTest.productCache", key="'ProductDao.selectMostLearned~'+#categoryid")
    public List<Productbase> selectMostLearned(Long categoryid){
    	List<Productbase> list = (List<Productbase>)this.queryForList("Productbase.selectMostLearned", categoryid);
        return list;
    }
    
    /**
     * 
     * if when need add parameter: promotable, then need flush it when insert/update
     */
    @Cacheable(value="netTest.productCache", 
    		key="'ProductDao.selectByVOPage~'+#categoryid+'~'+#shopid+'~'+#isIncludeChild+'~'+#issysgoodproduct+'~'+#paytype+'~'+#isopen+'~'+#status+'~'+#localeid+'~'+#regioncode+'~'+#pageIndex+'~'+#pageSize", 
    		condition="#productname==null or #productname==''")
    public Page selectByVOPage(Long categoryid, String productname, Long shopid, 
    						   int isIncludeChild, Short issysgoodproduct, Short paytype,
    						   Short isopen, Short status, Long localeid, String regioncode,
    						   int pageIndex,int pageSize,Integer total) 
    {
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        
        ProductbaseQuery queryVO = new ProductbaseQuery();
        queryVO.setCategoryid(categoryid);
        queryVO.setProductname(productname);
        queryVO.setShopid(shopid);
        queryVO.setIsIncludeChild(isIncludeChild);
        queryVO.setIssysgoodproduct(issysgoodproduct);
        queryVO.setPaytype(paytype);
        queryVO.setIsopen(isopen);
        queryVO.setStatus(status);
        queryVO.setLocaleid(localeid);
        queryVO.setRegioncode(regioncode);
                
        Page page = queryForPage("Productbase.selectByVO", queryVO, pageIndex, pageSize, total);
        
        // add cache key into key assoc map
		if(productname==null || productname.trim().length()<1){
		    CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
		    				"ProductDao.selectByVOPage~"+categoryid+"~"+shopid+"~"
		    				+isIncludeChild+"~"+issysgoodproduct+"~"+paytype+"~"+isopen
		    				+"~"+status+"~"+localeid+"~"+regioncode+"~"+pageIndex+"~"+pageSize, 
		    				new String[]{CateForProductType+":"+categoryid, 
		    							 ShopInProductType+":"+shopid,
		    							 ProdPaytypeCache+":"+paytype,
		    							 ProdOpentypeCache+":"+isopen,
		    							 ProdStatusCache+":"+status,
		    							 LocaleInProductCache+":"+localeid,
		    							 ShopDaoImpl.ShopRegionCacheKey+":"+regioncode});
		}
        
        return page;
    }
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Productbase vo) {
    	if(vo==null)
    		return null;
		Long pk = (Long)super.insert("Productbase.insert", vo);
		// 插入统计信息表
		vo.setWarequesnum(0l);
		vo.setLearncontdocnum(0);
		vo.setLearncontmedianum(0);
		vo.setPapernum(0);
		vo.setExercisenum(0);
		vo.setTestinfonum(0);
		vo.setCurrentlearnusernum(0);
		vo.setAlllearnedusernum(0);
		super.insert("Productbase.insertProductSatis", vo);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", Productbase.ObjectType, vo.getProductbaseid().toString());
		
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", CateForProductType, vo.getCategoryid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ShopInProductType, vo.getShopid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdPaytypeCache, vo.getPaytype().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdOpentypeCache, vo.getIsopen().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdStatusCache, vo.getStatus().toString());
		Shop shopvo = ShopDaoImpl.getInstance().selectByPK(vo.getShopid(), null);
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", LocaleInProductCache, shopvo.getLocaleid().toString());
		
		return pk;
    }
    
    /**
     * save product detail description
     */
    public Long saveProdExt(Productbase vo) {
    	if(vo==null || vo.getProductbaseid()==null || !vo.hasExtData())
    		return null;
    	Long id = (Long) this.queryForObject("Productbase.existProdExt", vo.getProductbaseid());
		if(id!=null){
			super.update("Productbase.updateExtByPK", vo);
		}else {
			super.insert("Productbase.insertExt", vo);
		}
		
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", Productbase.ObjectType, vo.getProductbaseid().toString());
		
		return vo.getProductbaseid();
    }
    
    /**
     * save product satis information
     * @param updateway: 1 表示是增量的更新，在原有数量基础上更新
     *                   2 表示直接更新传入数量
     */
    public Long saveProdSatis(Productbase vo, int updateway) {
    	if(vo==null || vo.getProductbaseid()==null || !vo.hasSatisData())
    		return null;
    	if(updateway==1){
    		super.update("Productbase.updateProdSatis1ByPK", vo);
    	}else if(updateway==2){
    		super.update("Productbase.updateProdSatis2ByPK", vo);
    	}
    	
    	CacheSynchronizer.getInstance().flushCache("netTest.productCache", "ProductDao.selectProdSatis~"+vo.getProductbaseid());
    	
		return vo.getProductbaseid();
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Productbase record) {
    	if(record==null||record.getProductbaseid()==null)
    		return 0;
    	
    	Productbase oldvo = selectByPK(record.getProductbaseid());
		int rows = super.update("Productbase.updateByPK", record);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", Productbase.ObjectType, record.getProductbaseid().toString());
		
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ShopInProductType, oldvo.getShopid().toString());
		if(record.getPaytype()!=null && !oldvo.getPaytype().equals(record.getPaytype())){
			CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdPaytypeCache, oldvo.getPaytype().toString());
			CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdPaytypeCache, record.getPaytype().toString());
		}
		if(record.getIsopen()!=null && !oldvo.getIsopen().equals(record.getIsopen())){
			CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdOpentypeCache, oldvo.getIsopen().toString());
			CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdOpentypeCache, record.getIsopen().toString());
		}
		if(record.getStatus()!=null && !oldvo.getStatus().equals(record.getStatus())){
			CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdStatusCache, oldvo.getStatus().toString());
			CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdStatusCache, record.getStatus().toString());
		}
				
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     */
    public Productbase save(Productbase record) {
    	if(record==null)
    		return null;
		if(record.getProductbaseid()==null||record.getProductbaseid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setProductbaseid(pkValue);		
		}else{
			this.updateByPK(record);
		}
		return record;
    }

    /**
     * 删除产品，包括：productExt, productSatis, goodproduct, product
     */
    public int deleteByPK(Long pk) {
    	if(pk==null)
    		return 0;
    	Productbase vo = selectByPK(pk);
    	
		super.delete("Productbase.deleteExtByPK", pk);
		super.delete("Productbase.delProdSatisByPK", pk);
		super.delete("Goodproduct.deleteByProd", pk);
		int rows = super.delete("Productbase.deleteByPK", pk);
		
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", Productbase.ObjectType, vo.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", CateForProductType, vo.getCategoryid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ShopInProductType, vo.getShopid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdPaytypeCache, vo.getPaytype().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdOpentypeCache, vo.getIsopen().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", ProdStatusCache, vo.getStatus().toString());
		Shop shopvo = ShopDaoImpl.getInstance().selectByPK(vo.getShopid(), null);
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", LocaleInProductCache, shopvo.getLocaleid().toString());
		
		return rows;
    }
        
    /**
     * static spring getMethod
     */
     public static ProductbaseDao getInstance() {
         try{
        	 ProductbaseDao dao = (ProductbaseDao)BeanFactory.getBeanFactory().getBean("productbaseDaoProxy");
             return dao;
         }catch(Exception e) {
        	 log.error("ProductbaseDaoImpl方法getInstance()时出错误!", e);
             throw new DaoException(e);
         }
     }
    
}
