package platform.shop.logic;

import java.util.List;

public interface ProdcateshopLogic {

	/**
	 * 得到商店目录列表，如果没有指定pid则用各个子系统自己的默认pid
	 */
	public List getShopCateChildNodes(Long pid, Long shopid, Long localeid, String syscode);
	
	/**
	 * 返回商店所涉及的产品目录名称字符串
	 * @param shopStr: shopid组成的字符串，其中以逗号隔开
	 * @param localeid
	 * @return
	 */
	public String[] getShopCategoryNames(String shopStr, Long localeid);
	
	/**
	 * 商店新增产品目录，过滤掉已经存在的目录
	 * @param categoryIdStr
	 * @param shopid
	 * @param syscode
	 * @return
	 */
    public int addCategory(String categoryIdStr, Long shopid, String syscode);
	
    /**
     * 删除shop的产品目录。先判断是否有子目录，然后判断目录下是否有产品
     */
    public int delCate(Long categoryid, Long shopid, String syscode);
	
}
