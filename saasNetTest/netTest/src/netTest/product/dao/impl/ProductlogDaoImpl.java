package netTest.product.dao.impl;

import java.util.ArrayList;
import java.util.List;

import netTest.bean.BeanFactory;
import netTest.exception.ExceptionConstant;
import netTest.product.dao.ProductlogDao;
import netTest.product.vo.Productlog;

import org.apache.log4j.Logger;

import commonTool.base.BaseDao;
import commonTool.exception.LogicException;

public class ProductlogDaoImpl extends BaseDao implements ProductlogDao {

	static Logger log = Logger.getLogger(ProductlogDaoImpl.class.getName());
	
    /**
     *
     */
    public ProductlogDaoImpl() {
        super();
    }

    public List<Productlog> selectByVO(Productlog queryVO){
    	if(queryVO==null)
			return new ArrayList<Productlog>();
		List<Productlog> list = (List<Productlog>)this.queryForList("Productlog.selectByVO", queryVO);
		return list;
    }
       
    
    public void insert(Productlog record){
    	if(record==null||record.getProductid()==null
    			||record.getShopid()==null||record.getCreatorid()==null
    			||record.getLogdate()==null||record.getLogtype()==null)
    		throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		super.insert("Productlog.insert", record);
    }

    /**
     * delete a record by PK
     */
    public int deleteByProd(Long productid){
    	if(productid==null)
    		return 0;
		int rows = super.delete("Productlog.deleteByProd", productid);
		return rows;
    }
        
    /**
     * static spring getMethod
     */
     public static ProductlogDao getInstance(){
    	 ProductlogDao dao = (ProductlogDao)BeanFactory.getBeanFactory().getBean("productlogDao");
         return dao;
     }
    
}
