package netTest.learncont.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import netTest.bean.BeanFactory;
import netTest.learncont.constant.LearncontentConstant;
import netTest.learncont.dao.LearncontentDao;
import netTest.learncont.dto.LearncontentQuery;
import netTest.learncont.vo.Learncontent;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

public class LearncontentDaoImpl extends BaseDao implements LearncontentDao {

	static Logger log = Logger.getLogger(LearncontentDaoImpl.class.getName());
	
	public final static String LearncontentChangeCacheType = "LearncontentChangeCacheType";
	
    /**
     *
     */
    public LearncontentDaoImpl() {
        super();
    }
    
    /**
     * select plain record by PK
     * @throws Exception 
     */
    @Cacheable(value="netTest.learncontentCache", key="'LearncontentDao.plain_learncontent~learncontent:'+#pk", unless="#result==null")
    public Learncontent selectPlainByPK(Long pk){
    	if(pk==null)
    		return null;
		Learncontent record = (Learncontent) this.queryForObject("Learncontent.selectPlainByPK", pk);
		// build assoic for cache
		if(record!=null){
    	   CacheSynchronizer.getInstance().buildAssoc("netTest.learncontentCache", 
    			 "LearncontentDao.plain_learncontent~"+Learncontent.ObjectType+":"+pk);
    	}
		return record;
    }
    
    /**
     * select some record by PK
     * including content data
     * @throws Exception 
     */
    @Cacheable(value="netTest.learncontentCache", key="'LearncontentDao.selectByPK~learncontent:'+#pk", unless="#result==null")
    public Learncontent selectByPK(Long pk){
    	if(pk==null)
    		return null;
		Learncontent record = (Learncontent) this.queryForObject("Learncontent.selectByPK", pk);
		// build assoic for cache
		if(record!=null){
    	   CacheSynchronizer.getInstance().buildAssoc("netTest.learncontentCache", 
    			 "LearncontentDao.selectByPK~"+Learncontent.ObjectType+":"+pk);
    	}
		return record;
    }
    
    /**
     * 根据productid查询其下的learncontent的所有contentid,并用逗号隔开
     * @param productid
     * @return
     */
    public String selectIdsByProdId(Long productid){
    	if(productid==null){
    		return null;
    	}
    	String rtnStr = null;
    	StringBuffer buffer = new StringBuffer();
    	List list = this.queryForList("Learncontent.selectIdsByProdId", productid);
    	if(list!=null && list.size()>0){
    		for(int i=0; i<list.size(); i++){
    			buffer.append(String.valueOf((Long)list.get(i))).append(",");
    		}
    		rtnStr = buffer.substring(0, buffer.length()-1);
    	}
    	return rtnStr;
    }
           

    /**
     * get free try learncontent
     * @param productid
     * @return
     */
    @Cacheable(value="netTest.learncontentCache", key="'LearncontentDao.selectTryProduct~'+#productid")
    public List<Learncontent> selectTryProduct(Long productid){
    	if(productid==null){
    		return null;
    	}
    	LearncontentQuery queryVO = new LearncontentQuery();
    	queryVO.setProductbaseid(productid);
    	queryVO.setIstry(LearncontentConstant.IsTry_Try);
		queryVO.setStatus(CommonConstant.Status_valide);
		queryVO.setOrder_By_Clause("orderNo");
		List<Learncontent> list = (List<Learncontent>)this.queryForList("Learncontent.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询出所有linktype类型的learncontent的fileID
     * @param productid
     * @param linktype
     * @return
     */
    public List<String> selectAllLinkedFileID(Long productid, Short linktype){
    	if(productid==null || linktype==null){
    		return null;
    	}
    	LearncontentQuery queryVO = new LearncontentQuery();
    	queryVO.setProductbaseid(productid);
    	queryVO.setLinktype(linktype);
		List<String> list = (List<String>)this.queryForList("Learncontent.selectAllLinkedFileID", queryVO);
		return list;
    }
    
    /**
     * 统计产品下所有learncontent的storage大小
     * @param productid
     * @return
     */
    public long selectAllStorageByProd(Long productid){
    	AssertUtil.assertNotNull(productid, null);
    	Long storage = (Long)this.queryForObject("Learncontent.selectAllStorageByProd", productid);
		if(storage==null){
			return 0;
		}else {
			return storage.longValue();
		}
    }
    
    /**
     * select all learncontent in product and content category
     * @param learntypequery 参见LearncontentConstant.contenttype
     */
    @Cacheable(value="netTest.learncontentCache", key="'LearncontentDao.selectContents~'+#productid+'~'+#contcateid+'~'+#learntypequery", unless="#result==null")
    public List<Learncontent> selectContents(Long productid, Long contcateid, Short learntypequery){
    	if(productid==null){
    		return null;
    	}
    	LearncontentQuery queryVO = new LearncontentQuery();
    	queryVO.setProductbaseid(productid);
    	queryVO.setContentcateid(contcateid);
    	queryVO.setLearntypequery(learntypequery);
    	queryVO.setStatus(LearncontentConstant.Status_Valide);
    	queryVO.setOrder_By_Clause("orderNo");
        List<Learncontent> list = (List<Learncontent>)this.queryForList("Learncontent.selectByVO", queryVO);
        // 整理list, 把其中的child learncontent放到其父learncontent中. parentid功能暂时不使用
//        Map<Long, Learncontent> map = new HashMap<Long, Learncontent>();
//        List<Learncontent> retlist = new ArrayList<Learncontent>();
//        if(list!=null && list.size()>0){
//        	for(Learncontent tempvo : list){
//        		if(tempvo.getParentid()==null){
//        			map.put(tempvo.getContentid(), tempvo);
//        			retlist.add(tempvo);
//        		}
//        	}
//        	//
//        	if(retlist.size()<list.size()){
//	        	Learncontent parenttempvo = null;
//	        	for(Learncontent tempvo : list){
//	        		if(tempvo.getParentid()!=null){
//	        			parenttempvo = map.get(tempvo.getParentid());
//	        			if(parenttempvo!=null){
//	        				if(parenttempvo.getSublist()==null){
//	        					parenttempvo.setSublist(new ArrayList<Learncontent>());
//	        				}
//	        				parenttempvo.getSublist().add(tempvo);
//	        			}
//	        		}
//	        	}
//        	}
//        }
        if(list!=null){
        	CacheSynchronizer.getInstance().buildAssoc("netTest.learncontentCache", 
        			"LearncontentDao.selectContents~"+productid+"~"+contcateid+"~"+learntypequery, 
                    new String[]{LearncontentChangeCacheType+":"+productid});
     	}
        return list;
    }
    
    /**
     * 查询超过给定时间的数据，主要是为清理脏数据使用
     * 仅仅返回前600条数据，需要反复调用处理
     */
    public List<Learncontent> selectLongTimeData(Date date, Short status){
		if(date==null || status==null)
			return new ArrayList<Learncontent>();
		LearncontentQuery queryVO = new LearncontentQuery();
		queryVO.setCreatetime(date);
		queryVO.setStatus(status);
		List<Learncontent> list = this.queryForList("Learncontent.selectLongTimeData", queryVO);
		return list;
    }
       
    /**
     * 删除超过给定时间的数据，主要是为清理脏数据使用
     * 目前全部查询出来，假设开始数据应该是每天小于1万条
     */
    public int deleteLongTimeData(Date date, Short status){
		if(date==null || status==null)
			return 0;
		LearncontentQuery queryVO = new LearncontentQuery();
		queryVO.setCreatetime(date);
		queryVO.setStatus(status);
		int rows = super.delete("Learncontent.deleteLongTimeData", queryVO);
		return rows;
    }
    
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Learncontent record){
    	if(record==null)
    		return null;
    	if(record.getLastupdatetime()==null){
    	   record.setLastupdatetime(record.getCreatetime());
    	}
		Long pk = (Long)super.insert("Learncontent.insert", record);
		record.setContentid(pk);
		super.insert("Learncontent.insertText", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", Learncontent.ObjectType, pk.toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", LearncontentChangeCacheType, record.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", LearncontentChangeCacheType, record.getProductbaseid().toString());
		if(LearncontentConstant.IsTry_Try.equals(record.getIstry())){
			CacheSynchronizer.getInstance().flushCache("netTest.learncontentCache", 
					"LearncontentDao.selectTryProduct~"+record.getProductbaseid());
		}
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Learncontent record){
    	if(record==null||record.getContentid()==null)
    		return 0;
    	if(record.getLastupdatetime()==null){
     	   record.setLastupdatetime(DateUtil.getInstance().getNowtime_GLNZ());
     	}
		int rows = super.update("Learncontent.updateByPK", record);
		super.update("Learncontent.updateTextByPK", record);
		Learncontent plainvo = getInstance().selectPlainByPK(record.getContentid());
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", Learncontent.ObjectType, record.getContentid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", LearncontentChangeCacheType, plainvo.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", LearncontentChangeCacheType, plainvo.getProductbaseid().toString());
		if(record.getIstry()!=null||LearncontentConstant.IsTry_Try.equals(plainvo.getIstry())){
			CacheSynchronizer.getInstance().flushCache("netTest.learncontentCache", 
					"LearncontentDao.selectTryProduct~"+plainvo.getProductbaseid());
		}
		return rows;
    }
    
    /**
     * update content
     */
    public int updateContent(String content, Long pk){
    	if(content==null||content.trim().length()<1||pk==null)
    		return 0;
    	Learncontent record = new Learncontent();
    	record.setContentid(pk);
    	record.setContent(content);
    	record.setLastupdatetime(DateUtil.getInstance().getNowtime_GLNZ());
		int rows = super.update("Learncontent.updateTextByPK", record);
		super.update("Learncontent.updateEditTimeByPK", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", Learncontent.ObjectType, record.getContentid().toString());
		return rows;
    }
    
    /**
     * update file source
     */
    public int updatefilesource(String filesource, Date datefilesource, Long pk){
    	if(filesource==null||filesource.trim().length()<1||pk==null)
    		return 0;
    	Learncontent vo = new Learncontent();
    	vo.setContentid(pk);
    	vo.setLinkfilesource(filesource);
    	vo.setLinkfiledate(datefilesource);
    	vo.setLastupdatetime(datefilesource);
		int rows = super.update("Learncontent.updfilesourceByPK", vo);
		Learncontent plainvo = getInstance().selectPlainByPK(pk);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", Learncontent.ObjectType, vo.getContentid().toString());
		if(LearncontentConstant.IsTry_Try.equals(plainvo.getIstry())){
			CacheSynchronizer.getInstance().flushCache("netTest.learncontentCache", 
					"LearncontentDao.selectTryProduct~"+plainvo.getProductbaseid());
		}
		return rows;
    }
    
    /**
     * update link file property
     */
    public void removeLinkFileByPK(Long pk){
    	if(pk==null)
    		return;
    	Learncontent vo = new Learncontent();
    	vo.setContentid(pk);
    	Date updatetime = DateUtil.getInstance().getNowtime_GLNZ();
    	vo.setLinkfiledate(updatetime);
    	vo.setLastupdatetime(updatetime);
		super.update("Learncontent.removeLinkFileByPK", vo);
		Learncontent plainvo = getInstance().selectPlainByPK(pk);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", Learncontent.ObjectType, vo.getContentid().toString());
		if(LearncontentConstant.IsTry_Try.equals(plainvo.getIstry())){
			CacheSynchronizer.getInstance().flushCache("netTest.learncontentCache", 
					"LearncontentDao.selectTryProduct~"+plainvo.getProductbaseid());
		}
    } 

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Learncontent save(Learncontent record){
    	if(record==null)
    		return null;
		if(record.getContentid()==null||record.getContentid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setContentid(pkValue);
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
    	Learncontent plainvo = getInstance().selectPlainByPK(pk);
    	super.delete("Learncontent.deleteTextByPK", pk);
		int rows = super.delete("Learncontent.deleteByPK", pk);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", Learncontent.ObjectType, pk.toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", LearncontentChangeCacheType, plainvo.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", LearncontentChangeCacheType, plainvo.getProductbaseid().toString());
		if(LearncontentConstant.IsTry_Try.equals(plainvo.getIstry())){
			CacheSynchronizer.getInstance().flushCache("netTest.learncontentCache", 
					"LearncontentDao.selectTryProduct~"+plainvo.getProductbaseid());
		}
		return rows;
    }
    
    /**
     * 根据产品删除learncontent
     * @param productid
     * @param shopid
     * @return
     */
    public int deleteByProd(Long productid){
    	if(productid==null)
    		return 0;
    	super.delete("Learncontent.deleteTextByProduct", productid);
		int rows = super.delete("Learncontent.deleteByProduct", productid);
		CacheSynchronizer.getInstance().pubFlush("netTest.learncontentCache", LearncontentChangeCacheType, productid.toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", LearncontentChangeCacheType, productid.toString());
		return rows;
    }
       
    /**
     * deleteBatch records by the String Array of PK
     */
//    public int deleteBatchByPK(String[] pkArray){
//    	if(pkArray==null||pkArray.length<=0)
//    		return 0;
//    	int rows = 0;
//    	Long[] arrs = new Long[pkArray.length];
//		for(int i=0;i<pkArray.length;i++){
//			if(pkArray[i]!=null)
//				arrs[i] = new Long(Long.parseLong(pkArray[i]));
//		}
//		String[] sqlArr = new String[]{"Learncontent.deleteTextByPK","Learncontent.deleteByPK"};
//		rows = super.deleteBatchMutiTable(sqlArr, arrs);
//		return rows;
//    }
    
    /**
     * static spring getMethod
     */
     public static LearncontentDao getInstance(){
       	 LearncontentDao dao = (LearncontentDao)BeanFactory.getBeanFactory().getBean("learncontentDao");
         return dao;
     }
    
}
