package platform.daoImpl;

import java.util.List;
import org.apache.log4j.Logger;
import commonTool.base.BaseDao;
import commonTool.base.Page;
import platform.dao.RechargerecordDao;
import commonTool.constant.CommonConstant;
import platform.vo.Rechargerecord;
import platform.dto.RechargerecordQuery;
import platform.bean.BeanFactory;

public class RechargerecordDaoImpl extends BaseDao implements RechargerecordDao {

	static Logger log = Logger.getLogger(RechargerecordDaoImpl.class.getName());
	
    /**
     *
     */
    public RechargerecordDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Rechargerecord selectByPK(Long pk) {
    	if(pk==null)
    		return null;
		Rechargerecord record = (Rechargerecord) this.queryForObject("Rechargerecord.selectByPK", pk);
		return record;
    }
    
    /**
     * select all records
     * @return
     */
    public List selectAll() throws Exception {
    	try {
			List list = this.queryForList("Rechargerecord.selectAll", null);
			return list;
		} catch (Exception e) {
			log.error("RechargerecordDaoImpl的selectAll时出错误!", e);
			throw e ;
		}
    }
        
    /**
     * select records by queryVO
     */
    public List selectByVO(RechargerecordQuery queryVO) throws Exception {
        try {
			if(queryVO==null)
				return selectAll();
			List list = this.queryForList("Rechargerecord.selectByVO", queryVO);
			return list;
        } catch (Exception e) {
			log.error("RechargerecordDaoImpl的selectByVO时出错误!", e);
			throw e ;
		}
    }
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(RechargerecordQuery queryVO,int pageIndex,int pageSize,Integer total) throws Exception {
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "";
        if(queryVO==null)
        	sqlStr = "Rechargerecord.selectAll";
        else 
        	sqlStr = "Rechargerecord.selectByVO";
        try{
	        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl分页查询selectByVOPage时出错误!", e);
			throw e ;
        }
    }
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Rechargerecord record) throws Exception {
    	if(record==null)
    		return null;
        try {
			return (Long)super.insert("Rechargerecord.insert", record);
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl插入insert时出错误!", e);
			throw e ;
        }
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Rechargerecord record) throws Exception {
    	if(record==null||record.getRechargeid()==null)
    		return 0;
        try {
			int rows = super.update("Rechargerecord.updateByPK", record);
			return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl更新updateByPK时出错误!", e);
			throw e ;
        }
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Rechargerecord save(Rechargerecord record) throws Exception {
    	if(record==null)
    		return null;
    	try {
			if(record.getRechargeid()==null||record.getRechargeid().intValue()==0){
				Long pkValue = this.insert(record);
				record.setRechargeid(pkValue);
				return record;
			}else{
				this.updateByPK(record);
				return record;
			}
		} catch (RuntimeException e) {
			log.error("RechargerecordDaoImpl保存save时出错误!", e);
			throw e ;
		}
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) throws Exception {
    	if(pk==null)
    		return 0;
        try {
			int rows = super.delete("Rechargerecord.deleteByPK", pk);
			return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl删除deleteByPK时出错误!", e);
			throw e ;
        }
    }
    
    /**
     * 根据用户userID删除记录
     * @param userID
     * @return
     * @throws Exception
     */
    public int delByUserID(Long userID) throws Exception{
    	if(userID==null)
    		return 0;
        try {
			int rows = super.delete("Rechargerecord.delByUserID", userID);
			return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl删除delByUserID时出错误!", e);
			throw e ;
        }
    }
    
    /**
     * 根据帐户ID删除记录
     * @param userID
     * @return
     * @throws Exception
     */
    public int delByAccountID(Long userID) throws Exception{
    	if(userID==null)
    		return 0;
        try {
			int rows = super.delete("Rechargerecord.delByAccountID", userID);
			return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl删除delByAccountID时出错误!", e);
			throw e ;
        }
    }
    
    /**
     * delete records by queryVO
     */
    public int deleteByVO(RechargerecordQuery queryVO) throws Exception {
    	if(queryVO==null)
    		return 0;
        try {
			int rows = super.delete("Rechargerecord.deleteByVO",queryVO);
			return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl删除deleteByVO时出错误!", e);
			throw e ;
        }
    }
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list) throws Exception {
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
        try {
        	rows = super.insertBatch("Rechargerecord.insert", list);
        	return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl批量新增insertBatch(List list)时出错误!", e);
			throw e ;
        }
    }
    
    /**
     * updateBatch records of List
     */
    public int updateBatch(List list) throws Exception {
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
        try {
        	rows = super.updateBatch("Rechargerecord.updateByPK", list);
        	return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl批量修改updateBatch(List list)时出错误!", e);
			throw e ;
        }
    }
    
    /**
     * deleteBatch records by the Long Array of PK
     */
    public int deleteBatchByPK(Long[] pkArray) throws Exception {
    	if(pkArray==null||pkArray.length<=0)
    		return 0;
    	int rows = 0;
        try {
        	rows = super.deleteBatch("Rechargerecord.deleteByPK", pkArray);
        	return rows;
        }catch(Exception e) {
            log.error("RechargerecordDaoImpl删除deleteBatchByPK(Long[] pkArray)时出错误!", e);
			throw e ;
        }
    }
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray) throws Exception {
    	if(pkArray==null||pkArray.length<=0)
    		return 0;
    	int rows = 0;
    	Long[] arrs = new Long[pkArray.length];
    	try {
			for(int i=0;i<pkArray.length;i++){
				if(pkArray[i]!=null)
					arrs[i] = new Long(Long.parseLong(pkArray[i]));
			}
			rows = this.deleteBatchByPK(arrs);
			return rows;
		} catch (NumberFormatException e) {
			log.error("RechargerecordDaoImpl删除deleteBatchByPK(String[] pkArray)时出现转换错�?!", e);
			throw new Exception("NumberFormatException：from String to Long!") ;
		}catch(Exception e) {
            log.error("RechargerecordDaoImpl删除deleteBatchByPK(String[] pkArray)时出错误!", e);
			throw e ;
        }
    	
    }
    
    /**
     * static spring getMethod
     */
     public static RechargerecordDao getInstance() {
       	 RechargerecordDao dao = (RechargerecordDao)BeanFactory.getBeanFactory().getBean("rechargerecordDaoProxy");
         return dao;
     }
    
}