package platform.logic;

import java.util.List;

import platform.vo.Hotcategory;

import commonTool.exception.LogicException;

public interface HotcategoryLogic {
  
    /**
     * 查询某个国家的热门目录，返回热门目录列表，每个热门目录之中包含其子热门目录集合
     * @param localeid
     * @return
     */
	public List<Hotcategory> qryHotcategory(Long localeid, String syscode, boolean isforadmin);
	
	/**
	 * 新增热门目录，如果已经存在的热门目录，则抛出异常
	 * @param categoryid
	 * @param pid
	 * @param localeid
	 * @return
	 * @throws LogicException
	 */
	public int addHotCategory(Hotcategory vo) throws LogicException;
	
	/**
	 * 删除热门目录设置，如果localeid为空，则说明要删除该热门目录的全部国家语言设置
	 * @param categoryid
	 * @param pid
	 * @param localeid
	 * @return
	 * @throws LogicException
	 */
	public int delHotCategory(Long categoryid, Long localeid, String syscode) throws LogicException;
    
}