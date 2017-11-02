package platform.constant;

import java.util.Locale;

import platform.util.ResourceBundleUtil;

public class ProductcategoryConstant {

	/** 顶级目录的级别值，top level value,the children levels is above 1  **/
	public static final Short CategoryLevelTop = new Short("1");
	
	
	/**
	 * 得到没有上级产品目录的名字，即"无上级目录”
	 * @param locale:用户需要得到的语言类型
	 * @return
	 */
	public static String getNoParentCategoryName(Locale locale){
		return ResourceBundleUtil.getInstance().getValue("ProductCategoryConstant.noParent", locale);
	}
	
	/**
	 * 是否允许在给定的category level上添加课程，只有3级目录及以下才允许
	 * @param categorylevel
	 * @return
	 */
	public static boolean allowAddProductInCategory(Short categorylevel){
		if(categorylevel!=null && categorylevel>2){
			return true;
		}else {
			return false;
		}
	}
	
}
