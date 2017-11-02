package netTestWeb.category.action;

import java.util.Locale;
import platform.constant.ProductcategoryConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Productcategory;
import commonTool.constant.CommonConstant;


public class ProductcategoryActionLogic {

    /**
     * 新增目录名时，需要初始化父目录的值，同时初始化目录的级别
     * 初始化会自动跟新voParent中相关字段
     * @param voParent
     * @param request
     * @return Map:
     * @throws Exception
     */
	public static void initAddPage(Productcategory vo,Locale locale,Long localeid) throws Exception{
		if(vo!=null){
			// 初始化上级目录名称
			if(vo.getPid()==null){
				vo.setPid(CommonConstant.TreeTopnodePid);
				vo.setParentName(ProductcategoryConstant.getNoParentCategoryName(locale));
            // 设置默认目录级别
				vo.setCategorylevel(ProductcategoryConstant.CategoryLevelTop);
			}else if(vo.getPid().equals(CommonConstant.TreeTopnodePid)){
				vo.setParentName(ProductcategoryConstant.getNoParentCategoryName(locale));
            // 设置默认目录级别
				vo.setCategorylevel(ProductcategoryConstant.CategoryLevelTop);
			}else{
				Productcategory cateVO = BOFactory_Platform.getProductcategoryDao().selectByLogicPK(vo.getPid(), localeid);
			    if(cateVO!=null&&cateVO.getCategoryname()!=null)
			    	vo.setParentName(cateVO.getCategoryname());
            // 设置默认目录级别
			    if(cateVO!=null&&cateVO.getCategorylevel()!=null)
			    	vo.setCategorylevel(new Short(String.valueOf(cateVO.getCategorylevel().intValue()+1)));
			}
		}
		return;
	}
	
}
