package netTest.product.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.product.dao.LearnactivityDao;
import netTest.product.dto.LearnactivityQuery;
import netTest.product.vo.Learnactivity;

import org.apache.log4j.Logger;

import platform.exception.ExceptionConstant;
import commonTool.base.BaseDao;
import commonTool.exception.LogicException;

public class LearnactivityDaoImpl extends BaseDao implements LearnactivityDao {

	static Logger log = Logger.getLogger(LearnactivityDaoImpl.class.getName());
	
    /**
     *
     */
    public LearnactivityDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Learnactivity selectByPK(Long userid, Long objectid, 
    		                        String objecttype, Short actiontype){
    	if(userid==null||objectid==null||objecttype==null||objecttype.trim().length()<1
    			||actiontype==null)
    		return null;
    	Learnactivity parmvo = new Learnactivity();
    	parmvo.setUserid(userid);
    	parmvo.setObjectid(objectid);
    	parmvo.setObjecttype(objecttype);
    	parmvo.setActiontype(actiontype);
		Learnactivity record = (Learnactivity) this.queryForObject("Learnactivity.selectByPK", parmvo);
		return record;
    }
    
    /**
     * 查询用户最后一次学习产品的记录
     * @param userid
     * @param productid
     * @return
     */
    public Learnactivity selectLearnLastest(Long userid, Long productid){
	    if(userid==null||productid==null)
	       return null;
	    Learnactivity parmvo = new Learnactivity();
	    parmvo.setUserid(userid);
	    parmvo.setProductid(productid);
	    Learnactivity record = (Learnactivity) this.queryForObject("Learnactivity.selectLearnLastest", parmvo);
	    return record;
	}
           
    /**
     * select records by queryVO
     */
    public List selectByVO(LearnactivityQuery queryVO){
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Learnactivity.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询用户学习记录
     */
    public Map<String, Learnactivity> selectUserLearnActivity(Long userid, Long productid, String objecttype){
    	if(userid==null||productid==null||objecttype==null||objecttype.trim().length()<1)
    		return null;
    	LearnactivityQuery queryVO = new LearnactivityQuery();
    	queryVO.setUserid(userid);
    	queryVO.setProductid(productid);
    	queryVO.setObjecttype(objecttype);
    	//queryVO.setObjectidList(objectidList);
    	List<Learnactivity> list = (List<Learnactivity>)this.queryForList("Learnactivity.selectByBatchID", queryVO);
    	Map<String,Learnactivity> map = new HashMap<String,Learnactivity>();
    	for(Learnactivity vo : list){
    		map.put(objecttype+":"+vo.getObjectid(), vo);
    	}
    	return map;
    }
           
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Learnactivity record){
    	if(record==null)
    		return null;
    	if(!Learnactivity.checkActiontype(record.getActiontype())
    		|| !Learnactivity.checkLearnstatus(record.getLearnstatus())){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
		return (Long)super.insert("Learnactivity.insert", record);
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Learnactivity record){
    	if(record==null||record.getActiontype()==null)
    		return 0;
    	if(!Learnactivity.checkActiontype(record.getActiontype())
        		|| !Learnactivity.checkLearnstatus(record.getLearnstatus())){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
    	}
		int rows = super.update("Learnactivity.updateByPK", record);
		return rows;
    }
    
    public int save(Learnactivity vo){
    	if(vo==null||vo.getUserid()==null||vo.getObjectid()==null||
    			vo.getObjecttype()==null||vo.getObjecttype().trim().length()<1
    			||vo.getActiontype()==null)
    		return 0;
    	int num = 0;
    	Learnactivity temvo = selectByPK(vo.getUserid(), vo.getObjectid(), vo.getObjecttype(), vo.getActiontype());
        if(temvo==null){
        	this.insert(vo);
        	num = 1;
        }else {
        	this.updateByPK(vo);
        }
        return num;
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long userid, Long objectid, String objecttype, Short actiontype){
    	if(userid==null||objectid==null||objecttype==null||objecttype.trim().length()<1
    			||actiontype==null)
    		return 0;
    	Learnactivity parmvo = new Learnactivity();
    	parmvo.setUserid(userid);
    	parmvo.setObjectid(objectid);
    	parmvo.setObjecttype(objecttype);
    	parmvo.setActiontype(actiontype);
		int rows = super.delete("Learnactivity.deleteByPK", parmvo);
		return rows;
    }
    
    /**
     * delete a record by product
     */
    public int deleteByProduct(Long userid, Long productid){
    	if(userid==null&&productid==null)
    		return 0;
    	Learnactivity parmvo = new Learnactivity();
    	parmvo.setUserid(userid);
    	parmvo.setProductid(productid);
		int rows = super.delete("Learnactivity.deleteByProduct", parmvo);
		return rows;
    }
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("Learnactivity.insert", list);
       	return rows;
    }
    
    /**
     * static spring getMethod
     */
     public static LearnactivityDao getInstance(){
       	 LearnactivityDao dao = (LearnactivityDao)BeanFactory.getBeanFactory().getBean("learnactivityDao");
         return dao;
     }
    
}