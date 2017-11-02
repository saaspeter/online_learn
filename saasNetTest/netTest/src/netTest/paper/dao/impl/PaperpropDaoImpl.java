package netTest.paper.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import commonTool.base.BaseDao;
import commonTool.base.Page;
import netTest.paper.dao.PaperpropDao;
import commonTool.constant.CommonConstant;
import netTest.paper.vo.Paperprop;
import netTest.paper.dto.PaperpropQuery;
import netTest.bean.BeanFactory;

public class PaperpropDaoImpl extends BaseDao implements PaperpropDao {

	static Logger log = Logger.getLogger(PaperpropDaoImpl.class.getName());
	
    /**
     *
     */
    public PaperpropDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Paperprop selectByPK(Long pk){
    	if(pk==null)
    		return null;
		Paperprop record = (Paperprop) this.queryForObject("Paperprop.selectByPK", pk);
		return record;
    }
           
    /**
     * select records by queryVO
     */
    public List selectByVO(PaperpropQuery queryVO){
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Paperprop.selectByVO", queryVO);
		return list;
    }
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(PaperpropQuery queryVO,int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        Page page = null;
        String sqlStr = "";
        if(queryVO==null){
        	page = Page.EMPTY_PAGE;
        } else {
        	sqlStr = "Paperprop.selectByVO";
        	page = queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
        }
        return page;
    }
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Paperprop record){
    	if(record==null)
    		return null;
		return (Long)super.insert("Paperprop.insert", record);
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Paperprop record){
    	if(record==null||record.getPaperid()==null)
    		return 0;
		int rows = super.update("Paperprop.updateByPK", record);
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Paperprop save(Paperprop record){
    	if(record==null)
    		return null;
		if(record.getPaperid()==null||record.getPaperid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setPaperid(pkValue);
    		return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk){
    	if(pk==null)
    		return 0;
		int rows = super.delete("Paperprop.deleteByPK", pk);
		return rows;
    }
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("Paperprop.insert", list);
       	return rows;
    }
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray){
    	if(pkArray==null||pkArray.length<=0)
    		return 0;
    	int rows = 0;
    	Long[] arrs = new Long[pkArray.length];
		for(int i=0;i<pkArray.length;i++){
			if(pkArray[i]!=null)
				arrs[i] = new Long(Long.parseLong(pkArray[i]));
		}
		rows = super.deleteBatch("Paperprop.deleteByPK", arrs);
		return rows;
    }
    
    /**
     * static spring getMethod
     */
     public static PaperpropDao getInstance(){
       	 PaperpropDao dao = (PaperpropDao)BeanFactory.getBeanFactory().getBean("paperpropDao");
         return dao;
     }
    
}