package platform.social.dao;

import java.util.Date;
import java.util.List;

import platform.social.vo.SocialNewsSource;

public interface SocialNewsSourceDao {
   
    public SocialNewsSource selectByPK(Long pk);
    
    public List<SocialNewsSource> selectAll();
    
    public Long insert(SocialNewsSource vo);

    public void updateSyncTime(Long id, Date syncTime);
    
    public int deleteByPK(Long pk);
    
    public int deleteByCategory(Long newscategoryid);
    
}
