package platform.social.logic.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import platform.bean.BeanFactory;
import platform.logicImpl.BOFactory_Platform;
import platform.social.dao.SocialNewsDao;
import platform.social.dao.SocialNewsSourceDao;
import platform.social.dto.SocialNewsResult;
import platform.social.logic.SocialNewsLogic;
import platform.social.vo.SocialNews;
import platform.social.vo.SocialNewsSource;

public class SocialNewsLogicImpl implements SocialNewsLogic{
	
	private final static int PageSize = 50;
	
	private Logger log = Logger.getLogger(SocialNewsLogicImpl.class.getName());
	
	private SocialNewsSourceDao socialNewsSourceDao;
	
	private SocialNewsDao socialNewsDao;
	
	public void autoReadFromServiceAndSave(){
		log.info("----begin to read social news");
		List<SocialNewsSource> sourcelist = socialNewsSourceDao.selectAll();
		if(sourcelist!=null){
			for(SocialNewsSource newssource : sourcelist){
				long time1 = System.currentTimeMillis();
				SocialNewsResult readResult1 = BOFactory_Platform.getSocialNewsReader().readSocialNewsFromAPI(newssource.getFromaccount(), 
						newssource.getSource(), newssource.getNewscategoryid(), null);
				log.info("----have read news from:"+newssource.getSource()+" | account:"+newssource.getFromaccount()
						  +" | time cost:"+(System.currentTimeMillis()-time1));
				if(readResult1!=null){
					Date newestDate = filterAndSaveNews(newssource.getNewscategoryid(), readResult1.getNewsList(), newssource.getSynctime());
					// update the sync time
					socialNewsSourceDao.updateSyncTime(newssource.getId(), newestDate);
				}
				// 这里应该使用多线程，但是应该是对每种service使用一个线程，这样比较安全
//				ParallelExecutor.getInstance().executeTask(AbstractParallelExecutor.Module_Default, 
//			            new AbstractTaskRunnable(newssource){
//			                public void run(){
//			                	SocialNewsSource source = (SocialNewsSource)this.getParamObject();
//			                	BOFactory_Platform.getSocialNewsReader().readSocialNewsFromAPI(source.getFromaccount(), 
//			    						source.getSource(), null);
//			                }
//						});
			}
		}
		log.info("----end read social news");
	}
	
	/**
	 * 过滤取到的social news,只有比上次syncTime晚的记录才会被存入DB
	 * @param newsList
	 * @param lastSyncTime
	 * @return Date 本次记录中最晚的news的时间
	 */
	private Date filterAndSaveNews(Long newscategoryid, List<SocialNews> newsList, Date lastSyncTime){
		if(newsList==null || newsList.size()<1){
			return null;
		}
		
		Date newestDate = null;
		int size1 = newsList.size();
		for(int i=newsList.size()-1; i>-1; i--){
			// if the news is before last sync time, then remove it, don't save it to DB
			if(newsList.get(i)==null || newsList.get(i).getCreatedTime()==null 
				 || (lastSyncTime!=null && lastSyncTime.compareTo(newsList.get(i).getCreatedTime())>=0)
			   )
			{
				newsList.remove(i);
			}else {
				if(newestDate==null || newestDate.before(newsList.get(i).getCreatedTime())){
					newestDate = newsList.get(i).getCreatedTime();
				}
			}
		}
		int size2 = newsList.size();
		socialNewsDao.saveBatch(newsList, newscategoryid);
		log.info("--read from source size:"+size1+" , saved into DB size:"+size2);
		return newestDate;
	}
	
	public List<SocialNews> selectNewsList(Long newscategoryid, Integer offset){
		return socialNewsDao.selectNewsList(newscategoryid, offset, PageSize);
	}

	public SocialNewsSourceDao getSocialNewsSourceDao() {
		return socialNewsSourceDao;
	}

	public void setSocialNewsSourceDao(SocialNewsSourceDao socialNewsSourceDao) {
		this.socialNewsSourceDao = socialNewsSourceDao;
	}
	
	public SocialNewsDao getSocialNewsDao() {
		return socialNewsDao;
	}

	public void setSocialNewsDao(SocialNewsDao socialNewsDao) {
		this.socialNewsDao = socialNewsDao;
	}

	public static SocialNewsLogic getInstance(){
		SocialNewsLogic logic = (SocialNewsLogic)BeanFactory.getBeanFactory().getBean("socialNewsLogic");
        return logic;
    }
	
}
