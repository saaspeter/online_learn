package platform.daoImpl;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import platform.bean.BeanFactory;
import platform.dao.ProductcategoryDao;
import platform.dto.ProductcategoryQuery;
import platform.logicImpl.HotcategoryLogicImpl;
import platform.util.ResourceBundleUtil;
import platform.vo.Productcategory;
import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.HasChildException;


public class ProductcategoryDaoImpl extends BaseDao implements ProductcategoryDao {

	static Logger log = Logger.getLogger(ProductcategoryDaoImpl.class.getName());
	
    /**
     *
     */
    public ProductcategoryDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="platform.categoryCache", key="'Productcategory.selectByPK~productcategory:'+#pk", unless="#result==null")
    public Productcategory selectByPK(Long pk){
    	if(pk==null)
    		return null;
		Productcategory record = (Productcategory) this.queryForObject("Productcategory.selectByPK", pk);
		
		// add cache key into key associate map
		if(record!=null){
		   CacheSynchronizer.getInstance().buildAssoc("platform.categoryCache", "Productcategory.selectByPK~"+Productcategory.ObjectType+":"+pk);
		}
		return record;
    }
    
    /**
     * select some record by logic PK
     */
    @Cacheable(value="platform.categoryCache", key="'Productcategory.selectByLogicPK~productcategory:'+#pk+'~'+#localeid", unless="#result==null")
    public Productcategory selectByLogicPK(Long pk, Long localeid){
    	if(pk==null)
    		return null;
    	Productcategory vo = null;
    	if(CommonConstant.TreeTopnodePid.equals(pk)){
			vo = new Productcategory();
		    vo.setCategoryid(CommonConstant.TreeTopnodePid);
		    Locale locale = I18nLogicImpl.getLocale(localeid);
			String topcategoryname = ResourceBundleUtil.getInstance().getValue("CommonConstant.TreeTopnodeName", locale);
		    vo.setCategoryname(topcategoryname);
		}else {
			if(localeid==null){
	    		vo=this.selectByPK(pk);
	    	}else{
	    		ProductcategoryQuery queryVO = new ProductcategoryQuery();
	    		queryVO.setCategoryid(pk);
	    		queryVO.setLocaleid(localeid);
	    		vo = (Productcategory) this.queryForObject("Productcategory.selectByLoigcPK", queryVO);
	    		if(vo!=null&&vo.getPid()!=null&&!CommonConstant.TreeTopnodePid.equals(vo.getPid()))
	    		{
	    			queryVO.setCategoryid(vo.getPid());
	    			Productcategory parentRec = (Productcategory) this.queryForObject("Productcategory.selectByLoigcPK", queryVO);
	    			if(parentRec!=null){
	    				vo.setParentName(parentRec.getCategoryname());
	    			}
	    		}
	    	}
		}

    	// add cache key into key associate map
    	if(vo!=null){
		   CacheSynchronizer.getInstance().buildAssoc("platform.categoryCache", "Productcategory.selectByLogicPK~"
    	                                  +Productcategory.ObjectType+":"+pk+"~"+localeid);
    	}
		return vo;
    }
    
    /**
     * 根据产品目录查询 引用该目录的产品数量 
     * @param categoryid
     * @return
     */
    public Integer selectProdnumByCate(Long categoryid, boolean includeChild){
    	if(categoryid==null)
    		return null;
    	ProductcategoryQuery queryVO = new ProductcategoryQuery();
    	queryVO.setCategoryid(categoryid);
    	if(includeChild){
    		queryVO.setIsIncludeChild(1);
    	}else {
    		queryVO.setIsIncludeChild(2);
    	}
		Integer num = (Integer) this.queryForObject("Productcategory.selectProdnumByCate", queryVO);
		int numrtn = 0;
		if(num!=null){
			numrtn = num.intValue();
		}
		return numrtn;
    }
    
    /**
     * select all records
     * @return
     */
//    public List selectAll(){
//		List list = this.queryForList("Productcategory.selectAll", null);
//		return list;
//    }
    
   
    /**
     * select records by queryVO
     */
//    public List selectByVO(ProductcategoryQuery queryVO) {
//		if(queryVO==null)
//			return selectAll();
//		List list = this.queryForList("Productcategory.selectByVO", queryVO);
//		return list;
//    }
    
    /**
     * select page by queryVO, 
     * currently only used in management page, so current no cache
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(ProductcategoryQuery queryVO,int pageIndex,int pageSize,Integer total) {
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "";
        if(queryVO==null)
        	sqlStr = "Productcategory.selectAll";
        else 
        	sqlStr = "Productcategory.selectByVO";
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }
    
    /**
     * 查询子系统的最上级目录id
     * @param syscode
     * @return
     */
    @Cacheable(value="platform.categoryCache", key="'Productcategory.getSystemTopCategory~'+#syscode")
    public Long getSystemTopCategory(String syscode){
    	if(syscode==null||syscode.trim().length()<1){
    		return null;
    	}
    	if(CommonConstant.SysCode_Platform.equals(syscode)){
    		return CommonConstant.TreeTopnodePid;
    	}
    	// sql的查询逻辑: 查询该系统所属目录中categorylevel最小值对应的category记录
    	List list = this.queryForList("Productcategory.getSystemTopCategory", syscode);
    	Long categoryid = null;
    	if(list!=null && list.size()>0){
   			categoryid = (Long)list.get(0);   		
    	}
    	return categoryid;
    }

    /**
     * insert a record,
     * @return Object with the PK(generated by DB)
     */
    @Caching(evict={@CacheEvict(value="platform.categoryCache", key="'Productcategory.selectByPK~'+#record.categoryid")})
    public Long insert(Productcategory record) {
    	if(record==null)
    		return null;
    	if(record.getPid()==null)
     	   record.setPid(CommonConstant.TreeTopnodePid);
		Long pk = (Long)super.insert("Productcategory.insert", record);
		record.setCategoryid(pk);
			
		String path = "";
		if(CommonConstant.TreeTopnodePid.equals(record.getPid())){
			path = String.valueOf(pk);
		}else{
			if(record.getPath()!=null&&record.getPath().trim().length()>0)
			   path = record.getPath()+","+String.valueOf(pk);
			else
			   path = String.valueOf(pk);
		}
		record.setPath(path);
		this.updateByPK(record);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", Productcategory.ObjectType, record.getCategoryid().toString());
		// flush parent cache
		CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", Productcategory.ObjectType, record.getPid().toString());
		
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Productcategory record){
    	if(record==null||record.getCategoryid()==null)
    		return 0;
		int rows = super.update("Productcategory.updateByPK", record);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", Productcategory.ObjectType, record.getCategoryid().toString());
		// flush parent cache
		Productcategory vo = getInstance().selectByPK(record.getCategoryid());
		if(vo.getPid()!=null){
			CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", Productcategory.ObjectType, vo.getPid().toString());
		}
		// if this category is hotcategory, then flush it
		int hotnum = HotcategoryDaoImpl.getInstance().exists(record.getCategoryid(), null, vo.getSyscode());
		if(hotnum>0){
			CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", HotcategoryLogicImpl.CacheObjectType_Hotcategory_Localeid, "all");
		}
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Productcategory save(Productcategory record) {
    	if(record==null)
    		return null;

		if(record.getCategoryid()==null||record.getCategoryid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setCategoryid(pkValue);
			return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * 从产品目录表中删除该记录(包括两张表中的记录)
     * 如果有引用则删除会报错
     */
    //@Caching(evict={@CacheEvict(value="platform.categoryCache", key="'Productcategory.selectByPK~'+#pk")})
    public int deleteByPK(Long pk) {
    	if(pk==null)
    		return 0;
    	//
    	Productcategory oldvo = selectByPK(pk);
    	
    	int childNum = selectChildNum(pk, null);
		if(childNum>0){
	       throw new HasChildException("category-Reference");
		}else{ 
			// 检查目录是否被产品引用
			int nums = selectProdnumByCate(pk, false);
			if(nums>0){
			   throw new HasChildException("product-Reference");
			}
			
	    	// 删除productcategoryvalue表中的数据,包括下级的目录的productcategoryvalue中的数据
	    	super.delete("Productcategoryvalue.deleteByCategoryidChild", pk);
	    	super.delete("Sysproductcate.deleteByCate", pk);
			super.delete("Productcategory.deleteByPK", pk);
		}
    	
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", Productcategory.ObjectType, pk.toString());
		// flush parent
		if(oldvo.getPid()!=null){
			CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", Productcategory.ObjectType, oldvo.getPid().toString());
		}
		// if this category is hotcategory, then flush it
		int hotnum = HotcategoryDaoImpl.getInstance().exists(pk, null, oldvo.getSyscode());
		if(hotnum>0){
			CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", HotcategoryLogicImpl.CacheObjectType_Hotcategory_Localeid, "all");
		}
		
		return 1;
    }
    

	/**
     * insertBatch records of List，同时插入这些记录的上下级关系记录
     */
//    public int insertBatch(List list) {
//    	if(list==null||list.size()<=0)
//    		return 0;
//    	int rows = 0;
//    	rows = super.insertBatch("Productcategory.insert", list);
//    	//TODO
//    	
//    	return rows;
//    }
    
    /**
     * 
     */
    @Cacheable(value="platform.categoryCache", key="'Productcategory.getChildNodes~productcategory:'+#pid+'~'+#syscode+'~'+#localeid+'~'+#isforAdminUse", unless="#result==null")
    public List getChildNodes(Long pid,String syscode,Long localeid, boolean isforAdminUse){
    	if(pid==null){
    		pid = CommonConstant.TreeTopnodePid;
    	}
    	ProductcategoryQuery queryVO = new ProductcategoryQuery();
    	queryVO.setPid(pid);
    	queryVO.setSyscode(syscode);
    	queryVO.setLocaleid(localeid);
    	if(!isforAdminUse){
    	   queryVO.setStatus(CommonConstant.Status_valide); // only search active record
    	}
		List list = this.queryForList("Productcategory.selectByVOChildnum", queryVO);
		
		if(list!=null){
			CacheSynchronizer.getInstance().buildAssoc("platform.categoryCache", "Productcategory.getChildNodes~"
		                              +Productcategory.ObjectType+":"+pid+"~"+syscode+"~"+localeid+"~"+isforAdminUse);
	    }
		
		return list;
    }
    
    /**
     * 根据父目录id，查找该目录下一级有多少子目录
     * @param parentid
     * @return
     * @throws Exception
     */
    public int selectChildNum(Long parentid, Short status){
    	if(parentid==null||parentid.longValue()<=0)
    		return 0;
    	ProductcategoryQuery queryVO = new ProductcategoryQuery();
    	queryVO.setPid(parentid);
    	queryVO.setStatus(status);
		Object num = this.queryForObject("Productcategory.selectChildNum", queryVO);
		if(num!=null)
		    return ((Integer)num).intValue();
		else 
			return 0;
    }
    
    /**
     * static spring getMethod
     */
     public static ProductcategoryDao getInstance() {
       	 ProductcategoryDao dao = (ProductcategoryDao)BeanFactory.getBeanFactory().getBean("productcategoryDaoProxy");
         return dao;
     }
    
}
