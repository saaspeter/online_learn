package platform.dao;

import java.util.List;
import commonTool.base.Page;
import platform.vo.Sysfunctionitem;
import platform.dto.SysfunctionitemQuery;

public interface SysfunctionitemDao {
   
    /**
     * select some record by PK
     */
    public Sysfunctionitem selectByPK(String functioncode);
            
    /**
     * select records by queryVO
     */
    public List selectByVO(SysfunctionitemQuery queryVO) ;
       
    /**
     * 根据系统的id号获取功能列表
     * @param syscode
     * @return
     * @throws Exception
     */
    public List selectBySyscode(String syscode) ;
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(SysfunctionitemQuery queryVO,int pageIndex,int pageSize,Integer total) ;

    /**
     * update a record By PK
     */
    public int updateByPK(Sysfunctionitem record) ;   
    	
}
