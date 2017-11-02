package netTest.orguser.constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import netTest.util.ResourceBundleUtil;

import org.apache.struts.util.LabelValueBean;

import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.LabelValueVO;

public class OrgbaseConstant {

	/** 需导入机构(包括单位和小组)列表的excel文件的列定义 **/
	public static final String[] OrgbaseColumn = 
	       new String[]{"orgname","createtime","pid","deptlevelname","relationProduct","orgdesc"};
		
	
	/** 最高级别的单位的pid值 **/
	public static final Long OrgPidTop = new Long(-1);
	
	/** 小组和产品的关系类别--仅应用于一部分产品 **/
	public static final Short RelationProduct_PartProducts = 1;
	/** 小组和产品的关系类别--自动应用于所有产品 **/
	public static final Short RelationProduct_AllProductsAuto = 2;
	
	
	/**
	 * 根据用户类型数值和用户的Locale得到机构类型名称
	 * @param relationProduct:小组和产品的关系
	 * @param locale
	 * @return
	 */
	public static String getRelationProductName(Short relationProduct,Locale locale){
		if(relationProduct.equals(OrgbaseConstant.RelationProduct_PartProducts))
			return ResourceBundleUtil.getInstance().getValue("OrgbaseConstant.RelationProduct.PartProducts", locale);
		else if(relationProduct.equals(OrgbaseConstant.RelationProduct_AllProductsAuto))
			return ResourceBundleUtil.getInstance().getValue("OrgbaseConstant.RelationProduct.AllProductsAuto", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户Locale得到小组和产品关系的下拉列表
	 * @param locale
	 * @return
	 */
	public static List getLabelRelationProduct(Locale locale){
		String partProducts_name = OrgbaseConstant.getRelationProductName(OrgbaseConstant.RelationProduct_PartProducts, locale);
		String allProductsAuto_name = OrgbaseConstant.getRelationProductName(OrgbaseConstant.RelationProduct_AllProductsAuto, locale);
		LabelValueBean labelValueBean1 = new LabelValueBean(partProducts_name,OrgbaseConstant.RelationProduct_PartProducts.toString());
		LabelValueBean labelValueBean2 = new LabelValueBean(allProductsAuto_name,OrgbaseConstant.RelationProduct_AllProductsAuto.toString());
	    List list = new ArrayList();
	    list.add(labelValueBean1);
	    list.add(labelValueBean2);
	    return list;
	}

	
	public List getConstItems(){
		return null;
	}
	
}
