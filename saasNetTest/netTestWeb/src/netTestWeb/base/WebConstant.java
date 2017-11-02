package netTestWeb.base;

import netTest.product.constant.ProductConstant;
import netTest.product.vo.Productbase;
import platform.constant.ShopConstant;
import platform.vo.Shop;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.util.StringUtil;

/**
 * constants is used to compare in case of use the characters 
 * constant like "".
 * @author fan
 */
public class WebConstant extends CommonConstant{

	public static final String WebContext = "/netTest";
	
	/** 文件访问的http上下文 **/
	public static final String FileContext = WebContext+"/"+SysparamConstant.httpFileContext+"/";
	
	/** 文件访问的http上下文 **/
	public static final String FileContext_WithoutRootContext = "/"+SysparamConstant.httpFileContext+"/";
	
	/**
	 * 得到图片地址
	 * @param imgSrc
	 * @param objectype
	 * @return
	 */
	public static String getDefaultLogoImg(String imgSrc, String objectype){
		String rtnImgSrc = "";
		if(StringUtil.isEmpty(objectype)){
			return rtnImgSrc;
		}
		if(StringUtil.isEmpty(imgSrc)){
			if(Shop.ObjectType.equals(objectype)){
				rtnImgSrc = WebContext + ShopConstant.ShopLogo_Default;
			}else if(Productbase.ObjectType.equals(objectype)){
				rtnImgSrc = WebContext + ProductConstant.Courses_Default_Logo;
			}
		}else {
			rtnImgSrc = WebConstant.FileContext + imgSrc;
		}
		return rtnImgSrc;
	}
	
	/**
	 * 处理url
	 * @param url
	 * @return
	 */
	public static String doContextUrl(String url){
    	if(url!=null && url.length()>0){
	    	if(url.indexOf("#")==0&&url.length()>1){
	    	   url = url.substring(1,url.length());
	    	}else if(url.indexOf("/")==0&&url.indexOf(WebContext)!=0){
			   url = WebContext+url;
	        }
    	}
        return url;
    }
	
}
