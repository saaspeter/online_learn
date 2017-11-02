package platform.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;
import platform.util.ResourceBundleUtil;
import commonTool.constant.CommonConstant;


public class SysfunctionitemConstant {

	static Logger log = Logger.getLogger(SysfunctionitemConstant.class.getName());
	
	/**
	 *  0: free to use
		1: only paid shop can use
		2. once pay, free to use all
	 */
	public static final Short PayType_Free = 0;
	public static final Short PayType_PaidShop_UseOnly = 1;
	public static final Short PayType_once_nolimit = 2;
	
	////////////////////////平台系统功能部分/////////////////////////////////////////
	/** 多地区语言设置功能 **/
//	public static final String Com_MultiLocale = "00000001";
//	   private static final String Code_Com_MultiLocale = "FunctionConstant.Common.MultiLocale";
	
	/** 数据备份功能 **/
//    public static final String Com_DataBackup = "00000002";
//       private static final String Code_Com_DataBackup = "FunctionConstant.Common.DataBackup";
	
	////////////////////////网上教育系统的功能列表/////////////////////////////////////
	
	/**  订单自动审批功能 **/
	public static final String Edu_OrderAutoApprove = "10000001";
	private static final String Code_Edu_OrderAutoApprove = "FunctionConstant.Education.OrderAutoApprove";
	
	/**  单位导入人员功能 **/
	public static final String Edu_OrguserImport = "10000002";
	private static final String Code_Edu_OrguserImport = "FunctionConstant.Education.OrguserImport";
	
	/**
	 * 根据功能编码和用户的locale得到功能的名称
	 * @param code
	 * @param locale
	 * @return
	 */
	public static String qryFuncName(String code,Locale locale){
		if(code==null)
			return "";
		String valueCode = null;
		String name = "";
//        if(SysfunctionitemConstant.Com_DataBackup.equals(code))
//        	valueCode = Code_Com_DataBackup;
//        else if(SysfunctionitemConstant.Com_MultiLocale.equals(code))
//        	valueCode = Code_Com_MultiLocale;
        if(SysfunctionitemConstant.Edu_OrderAutoApprove.equals(code)){
        	valueCode = Code_Edu_OrderAutoApprove;
        }else if(Edu_OrguserImport.equals(code)){
        	valueCode = Code_Edu_OrguserImport;
        }
        if(valueCode!=null)
        	name = ResourceBundleUtil.getInstance().getValue(valueCode, locale);
         return name;
	}
	
    /**
     * 根据系统编码和用户的Locale得到功能下拉列表
     * @param locale
     * @return
     */
	public static List qryFunctions(String sysCode,Locale locale){
		List list = new ArrayList();   
		if(CommonConstant.SysCode_Platform.equals(sysCode)){
//			String multiLocaleName = ResourceBundleUtil.getInstance().getValue(Code_Com_MultiLocale, locale);
//			LabelValueBean labelMultiLocale = new LabelValueBean(multiLocaleName,SysfunctionitemConstant.Com_MultiLocale);
//			
//			String dataBackupName = ResourceBundleUtil.getInstance().getValue(Com_DataBackup, locale);
//			LabelValueBean labelDataBackup = new LabelValueBean(dataBackupName,SysfunctionitemConstant.Com_DataBackup);
//		    
//		    list.add(labelMultiLocale);
//		    list.add(labelDataBackup);
		}else if(CommonConstant.SysCode_Education.equals(sysCode)){
			String orderName = ResourceBundleUtil.getInstance().getValue(Edu_OrderAutoApprove, locale);
			LabelValueBean labelDeptLevel = new LabelValueBean(orderName,SysfunctionitemConstant.Edu_OrderAutoApprove);
			
			list.add(labelDeptLevel);
		}
	    return list;
	}
	
	/**
	 * 得到系统编码和系统功能LabelList对应的Map
	 * @param locale
	 * @return
	 */
	public static Map getLabelMap(Locale locale){
		Map map = new HashMap();
		List listPlatform = SysfunctionitemConstant.qryFunctions(CommonConstant.SysCode_Platform, locale);
		map.put(CommonConstant.SysCode_Platform, listPlatform);
		
		List listEducation = SysfunctionitemConstant.qryFunctions(CommonConstant.SysCode_Education, locale);
	    map.put(CommonConstant.SysCode_Education, listEducation);
	        
		return map;
	}
	
	/**
	 * 是否是商店管理员可以自己添加管理的功能code
	 * @param functioncode
	 * @return
	 */
	public static boolean isShopCanSelfSet(String functioncode){
		if(Edu_OrderAutoApprove.equals(functioncode)){
			return true;
		}else {
			return false;
		}
	}
	
		
}
