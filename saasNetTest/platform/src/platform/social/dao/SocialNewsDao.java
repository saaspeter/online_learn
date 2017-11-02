package platform.social.dao;

import java.util.List;

import org.mongodb.morphia.Key;

import platform.social.vo.SocialNews;

public interface SocialNewsDao {

	public SocialNews newOrSave(SocialNews vo);
	
	public Iterable<Key<SocialNews>> saveBatch(List<SocialNews> list, Long newscategoryid);
	
	public List<SocialNews> selectNewsList(Long newscategoryid, Integer offset, Integer pageSize);
	
}
