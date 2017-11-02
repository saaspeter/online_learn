package netTest.product.dao.impl;

import java.util.Date;

import netTest.bean.BeanFactory;
import netTest.product.dao.CoursepostDao;
import netTest.product.vo.Coursepost;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.util.DateUtil;

public class CoursepostDaoImpl extends BaseDao implements CoursepostDao {

	static Logger log = Logger.getLogger(CoursepostDaoImpl.class.getName());
	
	private final static String CoursepostProdidType = "postprodidcache";
	
    /**
     *
     */
    public CoursepostDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value="netTest.productCache", key="'CoursepostDao.selectByPK~coursepost:'+#pk", unless="#result==null")
    public Coursepost selectByPK(Long pk){
    	if(pk==null)
    		return null;
    	Coursepost record = (Coursepost)this.queryForObject("Coursepost.selectByPK", pk);
		return record;
    }
    
    /**
     * 
     */
    @Cacheable(value="netTest.productCache", 
    		key="'CoursepostDao.selectByVOPage~postprodidcache:'+#productid+'~'+#pageIndex+'~'+#pageSize", 
    		   condition="#caption==null or #caption==''")
    public Page selectByVOPage(Long productid, String caption, 
				               int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;

        Page page = Page.EMPTY_PAGE;
        if(productid!=null){
        	Coursepost queryVO = new Coursepost();
            queryVO.setProductbaseid(productid);
            queryVO.setCaption(caption);
        	page = queryForPage("Coursepost.selectByVO", queryVO, pageIndex, pageSize, total);
        }
        // add cache key into key assoc map
		if(caption==null || caption.trim().length()<1){
		    CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
		    			"CoursepostDao.selectByVOPage~"+CoursepostProdidType+":"+productid
		    			+"~"+pageIndex+"~"+pageSize);
		}
        return page;
    }
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Coursepost record){
    	if(record==null)
    		return null;
		Long pk = (Long)super.insert("Coursepost.insert", record);
		record.setId(pk);
		if(record.getCreatetime()==null) {
			Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
			record.setCreatetime(curDate);
			record.setUpdatetime(curDate);
		}
		
		// flush page cache
		CacheSynchronizer.getInstance().flushCache("netTest.productCache", "CoursepostDao.selectByPK~coursepost:"+pk);
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", CoursepostProdidType, record.getProductbaseid().toString());
        
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Coursepost record){
    	if(record==null||record.getId()==null)
    		return 0;
		int rows = super.update("Coursepost.updateByPK", record);
		
		// flush page cache
		Long productid = record.getProductbaseid();
		if(productid==null){
			record = getInstance().selectByPK(record.getId());
			productid = record.getProductbaseid();
		}
		if(record.getUpdatetime()==null) {
			record.setUpdatetime(DateUtil.getInstance().getNowtime_GLNZ());
		}
		CacheSynchronizer.getInstance().flushCache("netTest.productCache", "CoursepostDao.selectByPK~coursepost:"+record.getId());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", CoursepostProdidType, productid.toString());
        		
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Coursepost save(Coursepost record){
    	if(record==null)
    		return null;
		if(record.getId()==null||record.getId().intValue()==0){
			Long pkValue = this.insert(record);
			record.setId(pkValue);
    		return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk){
    	if(pk==null)
    		return 0;
    	
    	Coursepost vo = selectByPK(pk);
    	if(vo==null){
    		return 0;
    	}
		int rows = super.delete("Coursepost.deleteByPK", pk);
		
		// flush page cache
		CacheSynchronizer.getInstance().flushCache("netTest.productCache", "CoursepostDao.selectByPK~course:"+pk);
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", CoursepostProdidType, vo.getProductbaseid().toString());
        		
		return rows;
    }
           
    /**
     * static spring getMethod
     */
     public static CoursepostDao getInstance(){
    	 CoursepostDao dao = (CoursepostDao)BeanFactory.getBeanFactory().getBean("coursepostDao");
         return dao;
     }
    
}