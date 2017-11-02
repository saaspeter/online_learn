package netTest.product.dao;

import java.util.List;

import netTest.product.vo.Productlog;

public interface ProductlogDao {

	public List<Productlog> selectByVO(Productlog queryVO);
	
	public void insert(Productlog record);
	
	public int deleteByProd(Long productid);
	
}
