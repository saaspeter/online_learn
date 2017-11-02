package platform.bean;

import org.springframework.beans.factory.InitializingBean;
import commonTool.constant.SysparamConstant;

/**
 * 记录保存平台系统相关的常量的系统编码，这些编码代表的值记录在数据库中
 * 这个应该可以和SysparamConstant.java合并，但暂时先这么做
 * 编码的长度不超过64位
 * @author fan
 */
public class SysparamConstantPlatform extends SysparamConstant implements InitializingBean{
   
//	/** 免费商店的人数限制编码 **/
//	public static final String Key_FreeShop_UserNumLimit = "FreeShop_UserNumLimit";
//	/** 免费商店的存储storage大小,单位是MB **/
//	public static final String Key_FreeShop_StorageLimit = "FreeShop_StorageLimit";
//	/** 付费商店的标准存储storage大小,单位是MB **/
//	public static final String Key_PayShop_StorageLimit = "PayShop_StorageLimit";
	
	
	/** 键：shop profile允许上传文件大小 **/
	public static final String Key_SingleFileSize_ShopProfile = "singlefile.shopprofile.size";
	
	/** 存储shop profile图片的，默认大小，单位是kb **/
	public static Float singleFileSize_shopprofile = -1f;
	
	// 单个上传文件部分的moduleName
	public static final String ModuleName_SingleFileSize_ShopProfile = "modulename.singlefile.shopprofile";
	
	
	/** 商店收费前可以试用的天数，试用期间系统不会统计费用 **/
	public static Integer tryUseDayBeforeCharge_num = 0;
	
	private static final String Key_tryUseDayBeforeCharge_num = "tryUseDayBeforeCharge.num";
	
	
	public void afterPropertiesSet() throws Exception {
		baseLoad();
		// do local load
		if(getProperty(Key_SingleFileSize_ShopProfile)!=null){
			String numStr1 = getProperty(Key_SingleFileSize_ShopProfile);
			if(numStr1!=null){
				singleFileSize_shopprofile = Float.parseFloat(numStr1);
			}
		}
		if(getProperty(Key_tryUseDayBeforeCharge_num)!=null){
			String numStr10 = getProperty(Key_tryUseDayBeforeCharge_num);
			if(numStr10!=null){
				tryUseDayBeforeCharge_num = Integer.parseInt(numStr10);
			}
		}
	}
	
	/**
	 * 根据所处的module返回此处的单个文件大小限制
	 * 例如: 在题目中的单个文件大小和文章中的单个文件大小是不相同的
	 * @param moduleName
	 * @return
	 */
	public static Float getSingleFileSizeLimit(String moduleName){
		Float rtnNum = -1f;
		if(ModuleName_SingleFileSize_ShopProfile.endsWith(moduleName)){
			rtnNum = singleFileSize_shopprofile;
		}
		return rtnNum;
	}
	
}
