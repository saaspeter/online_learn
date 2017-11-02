package platform.social.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import platform.exception.ExceptionConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.StringUtil;

public class SocialoathtokenConstant {

	public static final Short IdentityType_UserID = 1;
	public static final Short IdentityType_ShopID = 2;
		
	public static final Short ServiceType_Dropbox = 5;
	public static final Short ServiceType_Google = 6;
	public static final Short ServiceType_MSN = 7;
	public static final Short ServiceType_Facebook = 8;
	public static final Short ServiceType_QQ = 9;
	
	public static final String ServiceType_Google_Name = "Google";
	public static final String ServiceType_MSN_Name = "MSN";
	public static final String ServiceType_Facebook_Name = "Facebook";
	public static final String ServiceType_Dropbox_Name = "Dropbox";
	public static final String ServiceType_QQ_Name = "QQ";
	
	
	public static Integer Oauth_Dropbox_AccessToken_ExpireSecond;
	
//	private final static String OAuthToken_Endpoint_MSN = "https://login.live.com/oauth20_token.srf";
//	private final static String OAuthToken_Endpoint_Dropbox = "https://login.live.com/oauth20_token.srf";
//	
//	// 
//	private final static String MSN_URI_AccessFile = "https://apis.live.net/v5.0/";
	
	
	public static String getServiceTypeName(Short servicetype){
		if(ServiceType_Google.equals(servicetype)){
			return ServiceType_Google_Name;
		}else if(ServiceType_MSN.equals(servicetype)){
			return ServiceType_MSN_Name;
		}else if(ServiceType_Facebook.equals(servicetype)){
			return ServiceType_Facebook_Name;
		}else if(ServiceType_Dropbox.equals(servicetype)){
			return ServiceType_Dropbox_Name;
		}else if(ServiceType_QQ.equals(servicetype)){
			return ServiceType_QQ_Name;
		}
		return "";
	}
	
	public static boolean supportService(Short servicetype){
		if(ServiceType_Google.equals(servicetype)||ServiceType_MSN.equals(servicetype)
			||ServiceType_Dropbox.equals(servicetype)
			||ServiceType_Facebook.equals(servicetype)
			||ServiceType_QQ.equals(servicetype)){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean supportRefreahToken(Short servicetype){
		if(!supportService(servicetype))
			return false;
		if(ServiceType_Dropbox.equals(servicetype)||ServiceType_QQ.equals(servicetype)){
			return false;
		}else {
			return true;
		}
	}
	
	public static String getOauthAppID(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String appidKey = "Oauth."+getServiceTypeName(servicetype)+".AppID";
		return SysparamConstant.getProperty(appidKey);
	}
	
	public static String getOauthAppSecret(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String appKey = "Oauth."+getServiceTypeName(servicetype)+".AppSecret";
		return SysparamConstant.getProperty(appKey);
	}
	
	/**
	 * about the api key, please see: https://developers.google.com/console/help/new/?hl=en_US#usingkeys
	 * @param servicetype
	 * @return
	 */
	public static String getAPIKey(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String appKey = "Oauth."+getServiceTypeName(servicetype)+".APIKey";
		return SysparamConstant.getProperty(appKey);
	}
	
	/**
	 * get oauth authenticat url, not include: redirect_url
	 * @param servicetype
	 * @return
	 */
	public static String getAuthenUrl(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String urlKey = "Oauth."+getServiceTypeName(servicetype)+".AuthenUrl";
		return SysparamConstant.getProperty(urlKey);
	}
	
	/**
	 * 返回get token(access token/refreash token)的URL地址
	 * @param servicetype
	 * @return
	 */
	public static String getTokenGetUrl(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String urlKey = "Oauth."+getServiceTypeName(servicetype)+".TokenGetUrl";
		return SysparamConstant.getProperty(urlKey);
	}
	
	/**
	 * 返回validate token是否有效的URL地址
	 * @param servicetype
	 * @return
	 */
	public static String getValidateTokenUrl(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String urlKey = "Oauth."+getServiceTypeName(servicetype)+".ValidateTokenUrl";
		return SysparamConstant.getProperty(urlKey);
	}
	
	/**
	 * 返回call api的地址
	 * @param servicetype
	 * @return
	 */
	public static String getFileApiCallUri(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String urlKey = "Oauth."+getServiceTypeName(servicetype)+".FileApiCallUri";
		return SysparamConstant.getProperty(urlKey);
	}
	
	public static int getAccesstokenLiveTime(Short servicetype){
		int livetime = -1;
		if(ServiceType_Dropbox.equals(servicetype)){
			if(Oauth_Dropbox_AccessToken_ExpireSecond==null){
				String strtemp = SysparamConstant.getProperty("Oauth.Dropbox.AccessToken.Expire.Day");
				if(strtemp!=null){
				   Oauth_Dropbox_AccessToken_ExpireSecond = Integer.parseInt(strtemp);
				}
			}
			livetime = Oauth_Dropbox_AccessToken_ExpireSecond;
		}else {
			livetime = SysparamConstant.Oauth_AccessToken_ExpireSecond;
		}
		return livetime;
	}
	
	/**
	 * 得到redirect url
	 * @param servicetype
	 * @return
	 */
	public static String getFileRedirectUrl(Short servicetype, boolean isencode){
		String str = "";
		if(servicetype!=null)
			str = servicetype.toString();
		
		String basestr = SysparamConstant.getProperty("Oauth.SelectFile.RedirectUrl");
		if(isencode){
			basestr = StringUtil.encodeString(basestr, null);
		}
		return basestr+str;
	}
	
	/**
	 * 得到oauth选择的文件的id, 对于dropbox，要从其之前的file link中截取，dropbox api是怪胎
	 * @param servicetype
	 * @param linkfileid
	 * @param linkfilesource
	 * @return
	 */
	public static String getFileSourceUrl(Short servicetype, String linkfileid, String linkfilesource){
		if(ServiceType_Dropbox.equals(servicetype) && linkfilesource!=null && linkfilesource.trim().length()>0){
			String patten = "(https|http)://dl.dropboxusercontent.com/\\d+/\\w+/[^/]+/"; 
			Pattern p = Pattern.compile(patten);
			Matcher m = p.matcher(linkfilesource);
			if(m.find()){
				linkfileid = linkfilesource.substring(m.end());
			}
		}
		return linkfileid;
	}
	
	/**
	 * 返回当前token代表的user的call url
	 * @param servicetype
	 * @return
	 */
	public static String getCurrentUserCallUri(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String urlKey = "Oauth."+getServiceTypeName(servicetype)+".UserApiCallUri";
		return SysparamConstant.getProperty(urlKey);
	}
	
	/**
	 * 返回当前token代表的userid, 目前只有奇怪的qq需要单独一个url call去得到当前userid
	 * 其他的social provider直接得到用户信息的结果中就有userid
	 * @param servicetype
	 * @return
	 */
	public static String getSocialUserIdCallUri(Short servicetype){
		if(!supportService(servicetype))
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		String urlKey = "Oauth."+getServiceTypeName(servicetype)+".SocialUserIdGetUrl";
		return SysparamConstant.getProperty(urlKey);
	}
	
	public static void main(String[] args){
		String str1 = "https://dl.dropboxusercontent.com/1/view/qamvgiy5g0fajik/Photos/%E5%9C%B0%E4%B8%8B%E9%80%9A%E9%81%93%E5%A5%B3%E5%AD%A9%E7%BB%9D%E5%AF%B9%E5%A4%A9%E7%B1%81%E8%88%AC%E7%9A%84%E5%A3%B0%E9%9F%B3.flv";
		System.out.println(SocialoathtokenConstant.getFileSourceUrl(Short.parseShort("5"), "11", str1));
	}
	
}
