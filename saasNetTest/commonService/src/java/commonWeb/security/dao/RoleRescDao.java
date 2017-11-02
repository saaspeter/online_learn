package commonWeb.security.dao;

import java.util.List;

import commonTool.base.Page;
import commonWeb.security.dto.RoleRescQuery;
import commonWeb.security.vo.RoleResc;

public interface RoleRescDao {
        
    /**
     * select records by queryVO
     */
    public List selectByVO(RoleRescQuery queryVO);
    
    
    /**
     * 检查角色和权限的关联数目
     * @param rescId
     * @param roleId
     * @return
     */
    public int countExistPriv(Long rescId, Long roleId);
    
    /**
     * 
     * @param userIds
     * @param roleIds
     * @return
     */
    public int insertMoreBatch(Long[] rescIds,Long roleId,String syscode);
    
//    /**
//     * updateBatch records of List
//     */
//    public int updateBatch(List list);
 
    
    /**
     * deleteBatch records by the List of vo
     */
    public int deleteBatchByPK(List list, String syscode);
    
    /**
     * 删除该角色对应的该菜单及菜单下的所有资源
     * @param menuId
     * @param roleId
     * @return
     */
    public int delMenuAndResc(Long menuId, Long roleId);
    
    public int insertBatch(List<RoleResc> list);
	
}
