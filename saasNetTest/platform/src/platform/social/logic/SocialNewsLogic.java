package platform.social.logic;

import java.util.List;

import platform.social.vo.SocialNews;

public interface SocialNewsLogic {
	
	public void autoReadFromServiceAndSave();
	
	public List<SocialNews> selectNewsList(Long newscategoryid, Integer offset);
	
}
