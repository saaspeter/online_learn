package platform.social.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.social.dao.SocialNewsSourceDao;
import platform.social.vo.SocialNewsSource;

import commonTool.base.BaseDao;
import commonTool.util.DateUtil;

public class SocialNewsSourceDaoImpl extends BaseDao implements SocialNewsSourceDao {

	static Logger log = Logger.getLogger(SocialNewsSourceDaoImpl.class.getName());
	
    /**
     *
     */
    public SocialNewsSourceDaoImpl() {
        super();
    }
    
    public SocialNewsSource selectByPK(Long pk){
    	if(pk==null)
    		return null;
    	SocialNewsSource record = (SocialNewsSource) this.queryForObject("SocialNewsSource.selectByPK", pk);
		return record;
    }
    
    public List<SocialNewsSource> selectAll(){
    	List<SocialNewsSource> list = (List<SocialNewsSource>)this.queryForList("SocialNewsSource.selectAll", null);
    	return list;
    }
    
//    public List<SocialNewsSource> selectByCategory(Long newscategoryid){
//    	if(newscategoryid==null)
//    		return null;
//    	List<SocialNewsSource> list = (List<SocialNewsSource>)this.queryForList("SocialNewsSource.selectByCategory", newscategoryid);
//    	return list;
//    }
    
    public Long insert(SocialNewsSource vo){
    	if(vo==null)
    		return null;
    	Date curTime = DateUtil.getInstance().getNowtime_GLNZ();
    	vo.setLastupdatetime(curTime);
    	
    	Long pk = (Long)super.insert("SocialNewsSource.insert", vo);
		return pk;
    }

//    /**
//     * update a record By PK
//     */
//    public int updateByPK(SocialNewsSource vo){
//    	if(vo==null||vo.getId()==null)
//    		return 0;
//    	if(vo.getLastupdatetime()==null){
//    	   Date curTime = DateUtil.getInstance().getNowtime_GLNZ();
//    	   vo.setLastupdatetime(curTime);
//    	}
//		int rows = super.update("SocialNewsSource.updateByPK", vo);
//		return rows;
//    }
    
    /**
     * 更新取完一次消息后的syncTime
     * @param SyncTime
     */
    public void updateSyncTime(Long id, Date syncTime){
    	if(id!=null && syncTime!=null){
    		SocialNewsSource vo = new SocialNewsSource();
    		vo.setId(id);
    		vo.setSynctime(syncTime);
    		Date curTime = DateUtil.getInstance().getNowtime_GLNZ();
     	    vo.setLastupdatetime(curTime);
     	    super.update("SocialNewsSource.updateByPK", vo);
    	}
    }
    
    public int deleteByPK(Long pk){
    	if(pk==null){
    		return 0;
    	}
    	return super.delete("SocialNewsSource.deleteByPK", pk);
    }
    
    public int deleteByCategory(Long newscategoryid){
    	if(newscategoryid==null){
    		return 0;
    	}
    	return super.delete("SocialNewsSource.deleteByCategory", newscategoryid);
    }
    
    
    /**
     * static spring getMethod
     */
     public static SocialNewsSourceDao getInstance(){
    	 SocialNewsSourceDao dao = (SocialNewsSourceDao)BeanFactory.getBeanFactory().getBean("socialNewsSourceDao");
         return dao;
     }
    
}
