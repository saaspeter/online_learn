package platform.social.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.social.dao.SocialNewsDao;
import platform.social.vo.SocialNews;
import platform.util.MongoDBClient;

import commonTool.cache.CacheSynchronizer;

public class SocialNewsDaoImpl extends BasicDAO<SocialNews, ObjectId> implements SocialNewsDao{
	
	private Logger log = Logger.getLogger(SocialNewsDaoImpl.class.getName());

	public final static String MongoDB_Name = "socialDB";
	
	private final static String CacheType_NewsCategory = "NewsCategoryNewsType";
	
	
	public SocialNewsDaoImpl(){
		super(MongoDBClient.getInstance().getClient(), MongoDBClient.getInstance().getMorphiaInstance(), MongoDB_Name);
		ensureIndexes();
	}
		
	public SocialNews newOrSave(SocialNews vo){
		if(vo!=null) {
			// save vo, according after save, vo will be filled with objectId
		    save(vo);
		    CacheSynchronizer.getInstance().pubFlush("platform.infonewsCache", CacheType_NewsCategory, vo.getNewscategoryid().toString());
		}
		return vo;
	}
	
	public Iterable<Key<SocialNews>> saveBatch(List<SocialNews> list, Long newscategoryid){
		if(list!=null && list.size()>0 && newscategoryid!=null){
		    CacheSynchronizer.getInstance().pubFlush("platform.infonewsCache", CacheType_NewsCategory, newscategoryid.toString());
		    return getDs().save(list);
		}else {
		    return null;
		}
	}
	
	@Cacheable(value="platform.infonewsCache", key="'SocialNewsDao.selectNewsList~NewsCategoryNewsType:'+#newscategoryid+'~'+#offset+'~'+#pageSize")
	public List<SocialNews> selectNewsList(Long newscategoryid, Integer offset, Integer pageSize){
		long time1 = System.currentTimeMillis();
		Query<SocialNews> query = getDs().find(SocialNews.class, "newscategoryid", newscategoryid).order("-createdTime");
		if(query!=null && offset!=null){
			query.offset(offset);
		}
		if(query!=null && pageSize!=null){
			query.limit(pageSize);
		}
		
		// add cache key into key assoic map
		CacheSynchronizer.getInstance().buildAssoc("platform.infonewsCache", 
				 "SocialNewsDao.selectNewsList~"+CacheType_NewsCategory+":"+newscategoryid+"~"+offset+"~"+pageSize);
		
		List<SocialNews> rtnList = null;
		if(query!=null){
		    rtnList = query.asList();
		}
		log.info("-- read from mongoDB, newscategoryid:"+newscategoryid+" , time cost:"+(System.currentTimeMillis()-time1));
		return rtnList;
	}
	
	public static SocialNewsDao getInstance(){
		return (SocialNewsDao)BeanFactory.getBeanFactory().getBean("socialNewsDao");
	}
	
}
