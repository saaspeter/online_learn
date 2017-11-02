package commonWeb.security.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import commonTool.base.BaseDao;
import commonTool.cache.CacheSynchronizer;
import commonWeb.security.bean.BeanFactory;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.RoleRescDao;
import commonWeb.security.dto.RoleRescQuery;
import commonWeb.security.vo.RoleResc;

public class RoleRescDaoImpl extends BaseDao implements RoleRescDao {

	static Logger log = Logger.getLogger(RoleRescDaoImpl.class.getName());
	
    /**
     *
     */
    public RoleRescDaoImpl() {
        super();
    }
    
    /**
     * select all records
     * @return
     */
//    public List selectAll(){
//		List list = this.queryForList("RoleResc.selectAll", null);
//		return list;
//    }
        
    /**
     * select records by queryVO
     */
    public List selectByVO(RoleRescQuery queryVO){
		if(queryVO==null)
			return null;
		List list = this.queryForList("RoleResc.selectByVO", queryVO);
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
//    public Page selectByVOPage(RoleRescQuery queryVO,int pageIndex,int pageSize,Integer total){
//        if(pageIndex<=0)
//        	pageIndex = 1;
//        if(pageSize<=0)
//        	pageSize = CommonConstant.PAGESIZE;
//        String sqlStr = "";
//        if(queryVO==null)
//        	sqlStr = "RoleResc.selectAll";
//        else 
//        	sqlStr = "RoleResc.selectByVO";
//        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
//    }
    
    /**
     * 检查角色和权限的关联数目
     * @param rescId
     * @param roleId
     * @return
     */
    public int countExistPriv(Long rescId, Long roleId){
    	if(rescId==null||roleId==null)
    		return 0;
    	int ret = 0;
    	RoleRescQuery queryVO = new RoleRescQuery();
    	queryVO.setRescId(rescId);
    	queryVO.setRoleId(roleId);
    	Object countObj = this.queryForObject("RoleResc.selectByVOCount", queryVO);
    	if(countObj!=null)
    		ret = (Integer)countObj;
    	return ret;
    }
       
    /**
     * 删除角色资源，同时删除该资源的关联不可见的资源
     */
    private int deleteByPK(RoleResc vo){
    	if(vo==null||vo.getRescId()==null||vo.getRoleId()==null)
    		return 0;
    	
		int rows = super.delete("RoleResc.deleteByPK", vo);
		super.delete("RoleResc.delSubPrivByPK", vo);
		return rows;
    }
    
    /**
     * 为某个角色添加权限
     * @param userIds
     * @param roleIds
     * @return
     */
    public int insertMoreBatch(Long[] rescIds,Long roleId,String syscode){
    	if(rescIds==null||rescIds.length==0||roleId==null)
    	   return 0;
    	List<RoleResc> list = new ArrayList<RoleResc>();
    	StringBuffer buffer = new StringBuffer();
    	for(int i=0;i<rescIds.length;i++){
			RoleResc vo = new RoleResc();
			vo.setRescId(rescIds[i]);
			vo.setRoleId(roleId);
			vo.setSyscode(syscode);
			list.add(vo);
			buffer.append(rescIds[i].toString()).append(",");
    	}
    	int nums = this.insertBatch(list);
    	// 批量刷新资源cache
    	ResourcesDao rescDao = ResourcesDaoImpl.getInstance();
    	String rescIdStr = buffer.toString();
    	if(rescIdStr!=null&&rescIdStr.trim().length()>0){
    		rescIdStr = rescIdStr.substring(0, rescIdStr.length()-1);
    	}
    	List<String> urls = rescDao.getRestringByIdStr(rescIdStr);
    	// 查询关联资源的id，这些也需要刷新
    	List<String> rescStrList = rescDao.getLinkRescString(rescIdStr, syscode);
    	urls.addAll(rescStrList);
    	CacheSynchronizer.getInstance().batchFlush(null, urls);
    	
    	return nums;
    }
    
//    /**
//     * delete records by queryVO
//     */
//    public int deleteByVO(RoleRescQuery queryVO){
//    	if(queryVO==null)
//    		return 0;
//		int rows = super.delete("RoleResc.deleteByVO",queryVO);
//		return rows;
//    }
    
	/**
     * 批量插入角色权限关系，并且如果资源有关联资源则同时插入关联的不可见资源
     */
    public int insertBatch(List<RoleResc> list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("RoleResc.insert", list);
       	super.insertBatch("RoleResc.insertSubPriv", list);
       	
       	return rows;
    }
    
//    /**
//     * updateBatch records of List
//     */
//    public int updateBatch(List list){
//    	if(list==null||list.size()<=0)
//    		return 0;
//    	int rows = 0;
//       	rows = super.updateBatch("RoleResc.updateByPK", list);
//       	return rows;
//    }
        
    
    /**
     * deleteBatch records by the List of vo
     */
    public int deleteBatchByPK(List list, String syscode){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
        RoleResc vo = null;
        StringBuffer buffer = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null)
			   vo = (RoleResc)list.get(i);
			this.deleteByPK(vo);
			rows++;
			buffer.append(vo.getRescId()).append(",");
		}

//		 批量刷新资源cache
    	ResourcesDao rescDao = ResourcesDaoImpl.getInstance();
    	String rescIdStr = buffer.toString();
    	if(rescIdStr!=null&&rescIdStr.trim().length()>0){
    		rescIdStr = rescIdStr.substring(0, rescIdStr.length()-1);
    	}
    	List<String> urls = rescDao.getRestringByIdStr(rescIdStr);
    	// 查询关联资源的id，这些也需要刷新
    	List<String> rescStrList = rescDao.getLinkRescString(rescIdStr, syscode);
    	urls.addAll(rescStrList);
    	CacheSynchronizer.getInstance().batchFlush(null, urls);
		
		return rows;
    }
    
    /**
     * 删除该角色对应的该菜单及菜单下的所有资源
     * @param menuId
     * @param roleId
     * @return
     */
    public int delMenuAndResc(Long menuId, Long roleId){
    	if(menuId==null&&roleId==null)
    		return 0;
    	RoleRescQuery queryVO = new RoleRescQuery();
    	queryVO.setRescId(menuId);
    	queryVO.setRoleId(roleId);
    	int rows = super.delete("RoleResc.delMenuAndResc",queryVO);
    	// 批量刷新资源cache
    	List<String> urls = ResourcesDaoImpl.getInstance().qryRescAndChild(menuId);
    	CacheSynchronizer.getInstance().batchFlush(null, urls);
		return rows;
    }
    
    /**
     * static spring getMethod
     */
     public static RoleRescDao getInstance(){
       	 RoleRescDao dao = (RoleRescDao)BeanFactory.getBeanFactory().getBean("roleRescDao");
         return dao;
     }
    
}
