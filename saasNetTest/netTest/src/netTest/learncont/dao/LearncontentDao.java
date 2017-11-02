package netTest.learncont.dao;

import java.util.Date;
import java.util.List;

import netTest.learncont.vo.Learncontent;

public interface LearncontentDao {

	public Learncontent selectPlainByPK(Long pk);
	
	public Learncontent selectByPK(Long pk);
	
	public String selectIdsByProdId(Long productid);
	
	public List<Learncontent> selectTryProduct(Long productid);
	
	public List<String> selectAllLinkedFileID(Long productid, Short linktype);
	
	public List<Learncontent> selectContents(Long productid, Long contcateid, Short learntype);
	
	public List<Learncontent> selectLongTimeData(Date date, Short status);
	
	public int deleteLongTimeData(Date date, Short status);
	
	public Long insert(Learncontent record);
	
	public int updateByPK(Learncontent record);
	
	public int updateContent(String content, Long pk);
	
	public int updatefilesource(String filesource, Date datefilesource, Long pk);
	
	public Learncontent save(Learncontent record);
	
	public int deleteByPK(Long pk);
	
	public int deleteByProd(Long productid);
	
	public long selectAllStorageByProd(Long productid);
	
    /**
     * update link file property
     */
    public void removeLinkFileByPK(Long pk);
	
}
