package platform.daoImpl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import commonTool.base.BaseDao;
import commonTool.base.Page;
import platform.constant.SysfunctionitemConstant;
import platform.dao.SysfunctionitemDao;
import commonTool.constant.CommonConstant;
import platform.vo.Sysfunctionitem;
import platform.dto.SysfunctionitemQuery;
import platform.bean.BeanFactory;

public class SysfunctionitemDaoImpl extends BaseDao implements SysfunctionitemDao {

	static Logger log = Logger.getLogger(SysfunctionitemDaoImpl.class.getName());
	
    /**
     *
     */
    public SysfunctionitemDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Sysfunctionitem selectByPK(String functioncode) {
    	if(functioncode==null||functioncode.trim().length()<1)
    		return null;
		Sysfunctionitem record = (Sysfunctionitem) this.queryForObject("SysFunctionItem.selectByPK", functioncode);
		return record;
    }
        
        
    /**
     * select records by queryVO
     */
    public List selectByVO(SysfunctionitemQuery queryVO) {
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("SysFunctionItem.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 根据系统的id号获取功能列表
     * @param systemid
     * @return
     * @throws Exception
     */
    public List selectBySyscode(String syscode) {
    	if(syscode==null)
    		return null;
    	SysfunctionitemQuery queryVO = new SysfunctionitemQuery();
    	queryVO.setSyscode(syscode);
		return this.selectByVO(queryVO);
    }
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(SysfunctionitemQuery queryVO,int pageIndex,int pageSize,Integer total) {
    	if(queryVO==null)
    		return Page.EMPTY_PAGE;
    	if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "SysFunctionItem.selectByVO";
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Sysfunctionitem record) {
    	if(record==null||record.getFunctioncode()==null)
    		return 0;
		int rows = super.update("SysFunctionItem.updateByPK", record);
		return rows;
    }

    
    /**
     * static spring getMethod
     */
     public static SysfunctionitemDao getInstance() {
       	 SysfunctionitemDao dao = (SysfunctionitemDao)BeanFactory.getBeanFactory().getBean("sysfunctionitemDao");
         return dao;
     }

//	@Override
//	public Object selectByPK(Long pk) {
//		return null;
//	}
    
}
