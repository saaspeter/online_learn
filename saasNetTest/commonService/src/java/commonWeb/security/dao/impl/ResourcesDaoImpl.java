package commonWeb.security.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.exception.ExceptionConstant;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonWeb.security.bean.BeanFactory;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.RoleRescDao;
import commonWeb.security.dto.ResourcesQuery;
import commonWeb.security.dto.RoleRescQuery;
import commonWeb.security.dto.UserRoleQuery;
import commonWeb.security.vo.Resources;
import commonWeb.security.vo.Resourcesvalue;

public class ResourcesDaoImpl extends BaseDao implements ResourcesDao {

	static Logger log = Logger.getLogger(ResourcesDaoImpl.class.getName());
	
    /**
     *
     */
    public ResourcesDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Resources selectByPK(Long pk){
    	if(pk==null)
    		return null;
    	Resources record = null;
    	if(record==null||record.getId()==null)
		   record = (Resources) this.queryForObject("Resources.selectByPK_original", pk);
		return record;
    }
    
    /**
     * select some record by PK
     */
    public Resources selectByPK(Long pk,Long localeid){
    	if(pk==null)
    		return null;
    	Resources vo = new Resources();
    	vo.setId(pk);
    	vo.setLocaleid(localeid);
    	vo = (Resources) this.queryForObject("Resources.selectByPK", vo);
		return vo;
    }
    
    /**
     * 根据连接地址查找资源
     * @param syscode
     * @param resString
     * @return
     */
    @Cacheable(value="commonService.SecurityCache", key="'Resources.selectByRes~'+#syscode+'~'+#resString")
    public Resources selectByRes(String syscode, String resString){
    	if(resString==null||resString.trim().length()<1)
    		return null;
    	Resources res = new Resources();
    	res.setSyscode(syscode);
    	res.setResString(resString);
    	Resources record = null;
    	Object obj = this.queryForObject("Resources.selectByRes", res);
    	if(obj!=null){
    		record = (Resources)obj;
    	}
    	return record;
    }
    
    /**
     * 根据系统编码查找所有的资源
     * @param syscode
     * @return
     */
    public List<Resources> qryAllResource(String syscode){
    	if(syscode==null||syscode.trim().length()<1)
    		return null;
    	List<Resources> resList = null;
    	List obj = this.queryForList("Resources.qryAllResource", syscode);
    	if(obj!=null)
    		resList = (List<Resources>)obj;
    	return resList;
    }
    
    /**
     * 根据id集合得到resString的集合
     * @param idStr
     * @return
     */
    public List<String> getRestringByIdStr(String idStr){
    	if(idStr==null||idStr.trim().length()<1)
    		return null;
    	List<String> list = (List<String>)this.queryForList("Resources.getRestringByIdStr", idStr);
    	return list;
    }
    
    /**
     * 根据资源连接查询其对应的Roles
     * @param syscode
     * @param resString
     * @return
     */
    @Cacheable(value="commonService.SecurityCache", key="'Resources.qryRolesByRes~'+#syscode+'~'+#resString")
    public List<String> qryRolesByRes(String syscode, String resString){
    	if(resString==null||resString.trim().length()<1)
    		return null;
    	Resources res = new Resources();
    	res.setSyscode(syscode);
    	res.setResString(resString);
    	List<String> retList = this.queryForList("Resources.qryRolesByRes", res);
    	return retList;
    }
    
    /**
     * select all records
     * @return
     */
    public List selectAll(){
		List list = this.queryForList("Resources.selectAll", null);
		return list;
    }
        
    /**
     * select records by queryVO
     */
    public List selectByVO(ResourcesQuery queryVO){
		if(queryVO==null)
			return selectAll();
		List list = this.queryForList("Resources.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询资源权限目录树
     * @param pid
     * @return
     */
    @Cacheable(value="commonService.SecurityCache", key="'Resources.qryRescfoldTree~'+#syscode+'~'+#pid+'~'+#localeid")
    public List qryRescfoldTree(String syscode,Long pid,Long localeid){
    	if(pid==null)
    		pid = CommonConstant.TreeTopnodePid;
    	ResourcesQuery queryVO = new ResourcesQuery();
    	queryVO.setSyscode(syscode);
    	queryVO.setPid(pid);
    	queryVO.setLocaleid(localeid);
    	List list = this.queryForList("Resources.qryRescfoldTree", queryVO);
		return list;
    }
    
    /**
     * 查询资源id及其子资源的resString连接
     * @param parentRescId
     * @return
     */
    public List<String> qryRescAndChild(Long parentRescId){
    	if(parentRescId==null)
    		return null;
    	List list = this.queryForList("Resources.qryRescAndChild", parentRescId.toString());
    	if(list!=null){
    		return (List<String>)list;
    	}else
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
    public Page selectByVOPage(ResourcesQuery queryVO,int pageIndex,int pageSize,Integer total){
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "";
        if(queryVO==null)
        	sqlStr = "Resources.selectAll";
        else 
        	sqlStr = "Resources.selectByVO";
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }
    
    /**
     * 根据角色id选择该角色下的资源列表
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public List selectByRoleId(ResourcesQuery queryVO){
        if(queryVO==null||queryVO.getRoleId()==null)
        	return null;
        String sqlStr = "Resources.selectByRoleId";
        return queryForList(sqlStr, queryVO);
    }
    
    /**
     * 选择菜单目录下可选资源列表，即还没有选择的资源列表.
     * 本方法会过滤该用户所能看到有权限的权限
     * @param queryVO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List selectRescsForRoleAddRestrict(UserRoleQuery queryVO){
    	if(queryVO==null||queryVO.getUserid()==null||queryVO.getPid()==null
    		||queryVO.getSyscode()==null||queryVO.getLocaleid()==null)
    		return null;
        String sqlStr = "Resources.selectRescsForRoleAddRestrict";
        return this.queryForList(sqlStr, queryVO);
    }
    
    /**
     * 选择菜单目录下可选资源列表，即还没有选择的资源列表. 不进行过滤
     * @param queryVO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List selectRescsForRoleAdd(UserRoleQuery queryVO){
    	if(queryVO==null||queryVO.getUserid()==null||queryVO.getPid()==null
    		||queryVO.getSyscode()==null||queryVO.getLocaleid()==null)
    		return null;
        String sqlStr = "Resources.selectRescsForRoleAdd";
        return this.queryForList(sqlStr, queryVO);
    }
    
    /**
     * 查询资源的所有关联资源的resString
     * @param syscode
     * @param resString
     * @return
     */
    public List<String> getLinkRescString(String linkIdStr, String syscode){
    	if(linkIdStr==null||linkIdStr.trim().length()<1||syscode==null||syscode.trim().length()<1)
    		return null;
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("linkIdStr", linkIdStr);
    	map.put("syscode", syscode);
    	List<String> retList = (List<String>)this.queryForList("Resources.getLinkRescString", map);
    	return retList;
    }
       
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Resources record){
    	if(record==null)
    		return null;
    	if(record.getPid()==null)
     	   record.setPid(CommonConstant.TreeTopnodePid);
		Long pk = (Long)super.insert("Resources.insert", record);
		Resourcesvalue resValue = new Resourcesvalue();
		resValue.setId(pk);
		resValue.setLocaleid(record.getLocaleid());
		resValue.setName(record.getName());
		resValue.setDescn(record.getDescn());
		super.insert("Resourcesvalue.insert", resValue);
		Resources pVO = this.selectByPK(record.getPid());
		if(pVO!=null&&pVO.getPath()!=null){
			record.setPath(pVO.getPath()+String.valueOf(pk.longValue())+",");
			// 只更新路径
			record.setPid(null);
			super.update("Resources.updatePath", record);
		}
		
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Resources record){
    	if(record==null||record.getId()==null)
    		return 0;
    	if(record.getPid()==null)
     	   record.setPid(CommonConstant.TreeTopnodePid);
		int rows = super.update("Resources.updateByPK", record);
		// 刷新资源缓存
		CacheSynchronizer.getInstance().flushCache(null, record.getResString());
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Resources save(Resources record){
    	if(record==null)
    		return null;
		if(record.getId()==null||record.getId().intValue()==0){
			Long pkValue = this.insert(record);
			record.setId(pkValue);
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
    	Resources resc = selectByPK(pk);
    	if(resc==null)
    		return 0;
		// 刷新资源缓存
		CacheSynchronizer.getInstance().flushCache(null, resc.getResString());
    	super.delete("Resourcesvalue.deleteByFK", pk);
		int rows = super.delete("Resources.deleteByPK", pk);
		return rows;
    }
    
    /**
     * delete records by queryVO
     */
    public int deleteByVO(ResourcesQuery queryVO){
    	if(queryVO==null)
    		return 0;
		int rows = super.delete("Resources.deleteByVO",queryVO);
		return rows;
    }
    
	/**
     * insertBatch records of List
     */
    public int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	//rows = super.insertBatch("Resources.insert", list);
    	Resources vo = null;
    	for(int i=0;i<list.size();i++){
    		vo = (Resources)list.get(i);
    		this.insert(vo);
    		rows++;
    	}
       	return rows;
    }
    
    /**
     * 批量删除pid和path
     */
    public int updatePathBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.updateBatch("Resources.updatePath", list);
       	return rows;
    }
    
    /**
     * 检查资源是否有被引用
     * @param pkArray
     * @param localeid
     * @return
     */
    public Map checkRescRef(Long[] pkArray,Long localeid){
    	Map<String,String> map = new HashMap<String,String>();
    	String result = "1";
    	StringBuffer buffer = new StringBuffer();
    	if(pkArray==null||pkArray.length<1)
    	{
    		map.put("result", "1");
    	}else{
    		RoleRescDao rrDao = RoleRescDaoImpl.getInstance();
    		RoleRescQuery queryVO = new RoleRescQuery();
    		Resources vo = null;
    		List list = null;
    		for(int i=0;i<pkArray.length;i++){
    			queryVO.setRescId(pkArray[i]);
    			list = rrDao.selectByVO(queryVO);
    			if(list!=null&&list.size()>0){
    				result = "0";
    				vo = this.selectByPK(pkArray[i],localeid);
    				buffer.append(vo.getName()).append(",");
    			}
    		}
    		map.put("result", result);
    		if(result!=null&&"0".equals(result)){
    			map.put("info", buffer.toString());
    		}
    	}
    	return map;
    }
    
    /**
     * deleteBatch records by the Long Array of PK
     */
    public Map deleteBatchByPK(Long[] pkArray,Long localeid){
    	if(pkArray==null||pkArray.length<=0){
      	   throw new LogicException(ExceptionConstant.Error_Need_Paramter);
      	}
    	
    	int rows = 0;
    	ResourcesDao rescDao = ResourcesDaoImpl.getInstance();
    	Map map = rescDao.checkRescRef(pkArray, localeid);
    	if(map==null||map.get("result")==null||!"1".equals((String)map.get("result")))
    	   return map;
    	String[] sqlArr = new String[]{"Resourcesvalue.deleteByFK","Resources.deleteByPK"};
    	rows = super.deleteBatchMutiTable(sqlArr, pkArray);
    	rows = pkArray.length;
    	map.put("info", String.valueOf(rows));
    	
    	// 刷新缓存
    	StringBuffer buffer = new StringBuffer();
    	for(Long pk : pkArray){
    		buffer.append(String.valueOf(pk.longValue())).append(",");
    	}
    	String idStr = buffer.toString();
    	idStr = idStr.substring(0, idStr.length()-1);
    	List<String> urls = getRestringByIdStr(idStr);
    	CacheSynchronizer.getInstance().batchFlush(null, urls);
       	return map;
    }
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public Map deleteBatchByPK(String[] pkArray,Long localeid){
    	if(pkArray==null||pkArray.length<=0){
    		throw new LogicException(ExceptionConstant.Error_Need_Paramter);
      	}
    	Long[] arrs = new Long[pkArray.length];
		for(int i=0;i<pkArray.length;i++){
			if(pkArray[i]!=null)
			   arrs[i] = new Long(Long.parseLong(pkArray[i]));
		}
		Map map = this.deleteBatchByPK(arrs,localeid);
		return map;
    }
    
    /**
     * static spring getMethod
     */
     public static ResourcesDao getInstance(){
       	 ResourcesDao dao = (ResourcesDao)BeanFactory.getBeanFactory().getBean("resourcesDao");
         return dao;
     }
    

}
