package platform.shop.dao;

import platform.shop.vo.Shopext;

import commonTool.base.Page;

public interface ShopextDao {
   

	public Shopext selectByPK(Long shopid);
	
	public Page selectByVOPage(Shopext queryVO, int pageIndex, int pageSize,Integer total);
            
	public void insert(Shopext record);
    
	public int updateByPK(Shopext vo);

	public void save(Shopext vo);
    
	public int deleteByPK(Long shopid);
	
}
