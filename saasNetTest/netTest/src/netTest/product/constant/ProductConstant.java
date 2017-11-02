package netTest.product.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;

import platform.util.ResourceBundleUtil;

public class ProductConstant {

	//////////////////////////////产品类型，对应数据表中的继承基础产品表的子产品表/////////////////////
	/**  产品类型：培训科目产品  **/
//	public static final Integer ProductType_test = 1;
//	/**  产品类型：资料产品 **/
//	public static final Integer ProductType_doc = 2;
	
	
	//////////////////////////////产品是否免费/////////////////////////////////////////////////////
//	/**  产品是否免费：免费  **/
//	public static final Short IsFree_free = 1;
//	/**  产品是否免费：不免费 **/
//	public static final Short IsFree_notFree = 2;
	
	//////////////////////////////是否可以试用//////////////////////////////////////////////////////
//	/**  是否可以试用：可以试用  **/
//	public static final Short CanTry_can = 1;
//	/**  是否可以试用：不可以试用  **/
//	public static final Short CanTry_cannot = 2;
	
	/////////////////////////////购买是否要审批//////////////////////////////////////////////////////
	/** 购买是否要审批:需要审批才能购买通过 **/
	public static final Short IsNeedApprove_need = 1;
	/** 购买是否要审批:不需要审批 **/
	public static final Short IsNeedApprove_notNeed = 2;
	
	/////////////////////////////试用方式，按天或按次////////////////////////////////////////////////
	/** 试用方式，按天计算||暂时先按天 **/
	public static final Short TryUseType_dayNum = 1;
	/** 试用方式，按试用次数算 **/
	public static final Short TryUseType_useNum = 2;
	
	///////////////////////////// 产品有效性状态 ////////////////////////////////////////////////////
	/** 产品状态：准备中 **/
	public static final Short Status_preparing = 0;
	/** 产品有效性状态：有效 **/
	public static final Short Status_valid = 1;
	/** 产品有效性状态：失效 **/
	public static final Short Status_invalid = 2;
	/** 产品状态: 删除中 **/
	public static final Short Status_deleting = 4;
	
	//////////////////////////////产品是否是推荐产品/////////////////////////////////////////////////////
	/**  产品是否免费：免费  **/
	public static final Short Promotable_yes = 1;
	/**  产品是否免费：不免费 **/
	public static final Short Promotable_no = 2;
	
	
	//////////////////////////////产品是否开放/////////////////////////////////////////////////////
	/**  产品是否开放：开放  **/
	public static final Short Isopen_yes = 1;
	/**  产品是否开放：不开放 **/
	public static final Short Isopen_no = 2;
	
	
	//////////////////////////////产品是否是被推荐产品/////////////////////////////////////////////////////
	/**  推荐产品  **/
	public static final Short Issysgoodproduct_yes = 1;
	/**  非推荐产品 **/
	public static final Short Issysgoodproduct_no = 0;
	
	/** 默认课程图片 **/
	public static final String Courses_Default_Logo = "/styles/imgs/courses.png";
	
//	/**
//	 * 根据产品类类型数值和用户的Locale得到产品类型名称
//	 * @param userType
//	 * @param locale
//	 * @return
//	 */
//	public static String getProductTypeName(Integer productType,Locale locale){
//		if(productType.equals(ProductConstant.ProductType_test))
//			return ResourceBundleUtil.getValue("ProductConstant.ProductType.test", locale);
//		else if(productType.equals(ProductConstant.ProductType_doc))
//			return ResourceBundleUtil.getValue("ProductConstant.ProductType.doc", locale);
//		else 
//			return "";
//	}
//	
//	/**
//	 * 根据用户的locale得到产品类型的列表
//	 * @param locale
//	 * @return
//	 */
//	public static List getProductTypeLabels(Locale locale){
//		String productType_testName = ResourceBundleUtil.getValue("ProductConstant.ProductType.test", locale);
//		String productType_docName = ResourceBundleUtil.getValue("ProductConstant.ProductType.doc", locale);
//		
//		List listRtn = new ArrayList();
//		LabelValueBean labelTest = new LabelValueBean(productType_testName,ProductConstant.ProductType_test.toString());
//		LabelValueBean labelDoc = new LabelValueBean(productType_docName,ProductConstant.ProductType_doc.toString());
//		
//		listRtn.add(labelTest);
//		listRtn.add(labelDoc);
//		return listRtn;
//	}
	
//	/**
//	 * 根据是否免费数值和用户的Locale得到是否免费产品名称
//	 * @param userType
//	 * @param locale
//	 * @return
//	 */
//	public static String getIsFreeName(Short isFree,Locale locale){
//		if(isFree.equals(ProductConstant.IsFree_free))
//			return ResourceBundleUtil.getValue("ProductConstant.IsFree.free", locale);
//		else if(isFree.equals(ProductConstant.IsFree_notFree))
//			return ResourceBundleUtil.getValue("ProductConstant.IsFree.notFree", locale);
//		else 
//			return "";
//	}
	
	/**
	 * 根据是否免费数值和用户的Locale得到是否免费产品名称
	 * @param userType
	 * @param locale
	 * @return
	 */
//	public static String getCanTryName(Short isFree,Locale locale){
//		if(isFree.equals(ProductConstant.IsFree_free))
//			return ResourceBundleUtil.getValue("ProductConstant.IsFree.free", locale);
//		else if(isFree.equals(ProductConstant.IsFree_notFree))
//			return ResourceBundleUtil.getValue("ProductConstant.IsFree.notFree", locale);
//		else 
//			return "";
//	}
	
	/**
	 * 根据用户的locale得到产品是否免费的列表
	 * @param locale
	 * @return
	 */
//	public static List getIsFreeLabels(Locale locale){
//		String isFree_freeName = ResourceBundleUtil.getValue("ProductConstant.IsFree.free", locale);
//		String isFree_notFreeName = ResourceBundleUtil.getValue("ProductConstant.IsFree.notFree", locale);
//		List listRtn = new ArrayList();
//		LabelValueBean labelFree = new LabelValueBean(isFree_freeName,ProductConstant.IsFree_free.toString());
//		LabelValueBean labelNotFree = new LabelValueBean(isFree_notFreeName,ProductConstant.IsFree_notFree.toString());
//		listRtn.add(labelNotFree);
//		listRtn.add(labelFree);
//		return listRtn;
//	}
	
	
	/**
	 * 根据用户的locale得到是否可以试用的列表
	 * @param locale
	 * @return
	 */
//	public static List getCanTryLabels(Locale locale){
//		String canTry_canName = ResourceBundleUtil.getValue("ProductConstant.CanTry.can", locale);
//		String canTry_cannotName = ResourceBundleUtil.getValue("ProductConstant.CanTry.cannot", locale);
//		List listRtn = new ArrayList();
//		LabelValueBean labelCan = new LabelValueBean(canTry_canName,ProductConstant.CanTry_can.toString());
//		LabelValueBean labelCannot = new LabelValueBean(canTry_cannotName,ProductConstant.CanTry_cannot.toString());
//		listRtn.add(labelCan);
//		listRtn.add(labelCannot);
//		return listRtn;
//	}
	
	/**
	 * 根据是否需要审批数值和用户的Locale得到是否需要审批名称
	 * @param userType
	 * @param locale
	 * @return
	 */
	public static String getIsNeedApproveName(Integer isNeedApprove,Locale locale){
		if(isNeedApprove.equals(ProductConstant.IsNeedApprove_need))
			return ResourceBundleUtil.getInstance().getValue("ProductConstant.IsNeedApprove.need", locale);
		else if(isNeedApprove.equals(ProductConstant.IsNeedApprove_notNeed))
			return ResourceBundleUtil.getInstance().getValue("ProductConstant.IsNeedApprove.notNeed", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户的locale得到是否需要审批的列表
	 * @param locale
	 * @return
	 */
	public static List getIsNeedApproveLabels(Locale locale){
		String isNeedApprove_needName = ResourceBundleUtil.getInstance().getValue("ProductConstant.IsNeedApprove.need", locale);
		String isNeedApprove_notNeedName = ResourceBundleUtil.getInstance().getValue("ProductConstant.IsNeedApprove.notNeed", locale);
		
		List listRtn = new ArrayList();
		LabelValueBean labelNeed = new LabelValueBean(isNeedApprove_needName,ProductConstant.IsNeedApprove_need.toString());
		LabelValueBean labelNotNeed = new LabelValueBean(isNeedApprove_notNeedName,ProductConstant.IsNeedApprove_notNeed.toString());
		
		listRtn.add(labelNeed);
		listRtn.add(labelNotNeed);
		return listRtn;
	}
	
	public static boolean hasProduct(String prodStr, Long productid){
		if(prodStr==null||productid==null){
			return false;
		}else if((prodStr+",").indexOf(productid.toString()+",")!=-1){
			return true;
		}else {
			return false;
		}
	}
	
}
