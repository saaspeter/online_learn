package platform.logic;

import commonTool.exception.DuplicateException;

public interface ShopfuncLogic {

	
	/**
	 * 查询商店已经设置了哪些功能
	 * @param shopid
	 * @return 功能id的数组
	 */
	public abstract String[] qryShopfuncArr(Long shopid) ;
	
	/**
	 * 添加商店shopid选择的商店功能
	 * @param shopid
	 * @param funcArr
	 * @return
	 * @throws Exception
	 */
	public abstract int insertFuncs(Long shopid,String[] funcArr) throws DuplicateException;

}