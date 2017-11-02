package platform.social.logic.impl;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import platform.bean.BeanFactory;
import platform.constant.CustomerConstant;
import platform.daoImpl.UserDaoImpl;
import platform.exception.ExceptionConstant;
import platform.logic.UserLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.social.constant.SocialoathtokenConstant;
import platform.social.dao.SocialoathtokenDao;
import platform.social.logic.SocialoathtokenLogic;
import platform.social.vo.Socialoathtoken;
import platform.vo.Custinfoex;
import platform.vo.User;
import platform.vo.Usercontactinfo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import commonTool.exception.DuplicateException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;

public class SocialoathtokenLogicImpl implements SocialoathtokenLogic {
	
	static Logger log = Logger.getLogger(SocialoathtokenLogicImpl.class.getName());
	
	private static ClientConfig config = new DefaultClientConfig();
	private static Client c = Client.create(config);
	
	static{
		c.setConnectTimeout(4000); // set read time 4 second
	}
	
//	private static String client_id;
//	private static String client_secret;
	
	//private final static String redirect_uri = "http://www.mylearn.com/netTest/oath/goselectfile.do?servicetype="+SocialoathtokenConstant.ServiceType_MSN;
	
	private final static String grant_type_1 = "authorization_code";
	private final static String grant_type_2 = "refresh_token";
	
	
	private SocialoathtokenDao socialoathtokenDao;
	
	private UserLogic userLogic;
	

	public User selectUserBySocialAccount(Short servicetype, String socialuserid)
	{
		Socialoathtoken socialVO = socialoathtokenDao.selectBySocialAccount(
				SocialoathtokenConstant.IdentityType_UserID, servicetype, socialuserid);
	    if(socialVO!=null){
	    	return BOFactory_Platform.getUserDao().selectByPK(socialVO.getIdentityid());
	    }else {
	    	return null;
	    }
	}
	
	
	/**
	 * 根据accesstoken得到文件的source,
	 * 如果商店对应的accesstoken过期就用refresh token重新生成一个，并且更新db中的access token
	 * @param shopid
	 * @param servicetype
	 * @param fileid
	 * @return
	 */
	public String getFileSource(Long identityid, Short identitytype, Short servicetype, String socialuserid, String fileid){
		String filesource = null;
		// 如果filesource
		if(identityid!=null&&identitytype!=null&&servicetype!=null
				&&fileid!=null&&fileid.trim().length()>0){
			Socialoathtoken vo = socialoathtokenDao.selectBySocialAccount(identitytype, servicetype, socialuserid);
			if(vo==null) {
				throw new LogicException(ExceptionConstant.Error_No_OauthToken);
			}
			Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
			boolean needRequestAccessToken = true;
			if(vo!=null && vo.getAccesstoken()!=null && !"".equals(vo.getAccesstoken())){
				Date access_expireDate = DateUtil.dateAddUnits(vo.getUpdatetimeaccess(),Calendar.SECOND,SocialoathtokenConstant.getAccesstokenLiveTime(servicetype));
				if(currentDate.compareTo(access_expireDate)<=0){
					needRequestAccessToken = false;
				}
			}
			// 根据refresh code得到access token,并且更新db
			// TODO 多线程的case没有考虑，如果有多个线程过来，则只需要更新一次
			if(needRequestAccessToken){
				String accessToken = getAccessTokenByRefreshToken(vo.getRefreshtoken(), servicetype);
				if(accessToken!=null){
					vo.setRefreshtoken(null);
					vo.setUpdatetimerefresh(null);
					vo.setAccesstoken(accessToken);
					vo.setUpdatetimeaccess(currentDate);
					socialoathtokenDao.updateByPK(vo);
				}
			}
			// 根据access token得到file source
			filesource = getFileSourceByAccessToken(fileid, vo.getAccesstoken(), servicetype);
		}
		return filesource;
	}
	
	// https://login.live.com/oauth20_token.srf
	// client_id=CLIENT_ID&client_secret=CLIENT_SECRET&redirect_uri=REDIRECT_URI&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	public String getAccessTokenByRefreshToken(String refreshToken, Short servicetype){
		if(refreshToken==null||refreshToken.trim().length()<1
			||!SocialoathtokenConstant.supportService(servicetype)){
			return null;
		}
		String accessToken = null;
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("client_id", SocialoathtokenConstant.getOauthAppID(servicetype));
		params.add("client_secret", SocialoathtokenConstant.getOauthAppSecret(servicetype));
		//params.add("redirect_uri", redirect_uri);
		params.add("grant_type", grant_type_2);
		params.add("refresh_token", refreshToken);

		try {
			WebResource r = c.resource(SocialoathtokenConstant.getTokenGetUrl(servicetype));
			// using Post not Get
			ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, params);
			JSONObject jsonObj = (JSONObject)JSONValue.parse(response.getEntity(String.class));
			//String response = r.queryParams(params).get(String.class);
			//JSONObject jsonObj = (JSONObject)JSONValue.parse(response);
			if(jsonObj != null && jsonObj.containsKey("access_token")) {
				accessToken = jsonObj.get("access_token").toString();
			}
		} catch (UniformInterfaceException e) {
			String detail = e.getResponse().getEntity(String.class).toString();
			log.error(detail, e);
			throw e;
		} catch (com.sun.jersey.api.client.ClientHandlerException e){
			log.error("error here, servicetype:"+servicetype, e);
			if((e.getCause() instanceof java.net.ConnectException)
					||(e.getCause() instanceof java.net.SocketTimeoutException)){
				String servicename = SocialoathtokenConstant.getServiceTypeName(servicetype);
				throw new LogicException(ExceptionConstant.Error_Cannot_ConnectToService).appendExtraInfo_FirstKey(servicename);
			}else {
			    throw e;
			}
		}
		return accessToken;
	}
	
	private String getFileSourceByAccessToken(String fileid, String accessToken, Short servicetype){
		if(fileid==null||fileid.trim().length()<1||
				accessToken==null||accessToken.trim().length()<1
				||!SocialoathtokenConstant.supportService(servicetype)){
			return null;
		}
		String filesource = null;

		try {
			WebResource r = c.resource(SocialoathtokenConstant.getFileApiCallUri(servicetype));
			String response = r.path(fileid).queryParam("access_token", accessToken).get(String.class);
			
			JSONObject jsonObj = (JSONObject)JSONValue.parse(response);
			filesource = extractFileSource(jsonObj, servicetype);
		} catch (UniformInterfaceException e) {
			String detail = e.getResponse().getEntity(String.class).toString();
			log.error(detail, e);
			throw e;
		} catch (com.sun.jersey.api.client.ClientHandlerException e){
			log.error("error here, servicetype:"+servicetype, e);
			if((e.getCause() instanceof java.net.ConnectException)
					||(e.getCause() instanceof java.net.SocketTimeoutException)){
				String servicename = SocialoathtokenConstant.getServiceTypeName(servicetype);
				throw new LogicException(ExceptionConstant.Error_Cannot_ConnectToService).appendExtraInfo_FirstKey(servicename);
			}else {
			    throw e;
			}
		}
		return filesource;
	}
	
	/**
	 * 过滤file source,在skydrive中的source后面会带有?download&psid=1或?psid=1
	 * 会使得某些文件不可读，因此去除这些多余的参数
	 * @param filesource
	 * @param servicetype
	 * @return
	 */
	private static String extractFileSource(JSONObject jsonObj, Short servicetype){
		if(jsonObj==null){
			return null;
		}
		String filesource = null;
		if(SocialoathtokenConstant.ServiceType_MSN.equals(servicetype)){
			if(jsonObj.containsKey("source")){
				filesource = jsonObj.get("source").toString();
			}
		} else if(SocialoathtokenConstant.ServiceType_Google.equals(servicetype)){
			if(jsonObj.containsKey("downloadUrl")){
				filesource = jsonObj.get("downloadUrl").toString();
			}
		} else if(SocialoathtokenConstant.ServiceType_Dropbox.equals(servicetype)){
			if(jsonObj.containsKey("url")){
				filesource = jsonObj.get("url").toString();
			}
		}
		
		filesource = filterFileSource(filesource, servicetype);
		return filesource;
	}
	
	public static String filterFileSource(String filesource, Short servicetype){
		if(filesource==null || filesource.length()<1){
			return null;
		}
		if(SocialoathtokenConstant.ServiceType_MSN.equals(servicetype)){
			if(filesource.endsWith("?download&psid=1")){
	            filesource = filesource.substring(0,filesource.indexOf("?download&psid=1"));
	        }else if(filesource.endsWith("?psid=1")){
                filesource = filesource.substring(0,filesource.indexOf("?psid=1"));
	        }
		} else if(SocialoathtokenConstant.ServiceType_Google.equals(servicetype)){
			if(filesource.endsWith("&e=download&gd=true")){
	            filesource = filesource.substring(0,filesource.indexOf("&e=download&gd=true"));
			}
		} else if(SocialoathtokenConstant.ServiceType_Dropbox.equals(servicetype)){
			
		}
		return filesource;
	}
	
	//	 make rest call to 
	// https://login.live.com/oauth20_token.srf?client_id=000000004C0E29BD&redirect_uri=http://www.mylearn.com/netTest/MyHtml.html&client_secret=ry8OnbTQrnN6RDq6QYrtW4rGURSWeWW8&code=c3bd8b49-8c70-6760-20e2-cbf746b7e6bc&grant_type=authorization_code
	// access_token and refeash token
	// return: 第一个是access token, 第二个是refresh token
	public String[] restGetAllToken(String athorizedCode, Short serviceType) {
		String access_token = null;
		String refresh_token = null;
		String[] rtnArr = null;
		if(athorizedCode==null||"".equals(athorizedCode)||serviceType==null){
			return rtnArr;
		}
		
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("client_id", SocialoathtokenConstant.getOauthAppID(serviceType));
		params.add("redirect_uri", SocialoathtokenConstant.getFileRedirectUrl(serviceType, false));
		params.add("client_secret", SocialoathtokenConstant.getOauthAppSecret(serviceType));
		params.add("grant_type", grant_type_1);
		params.add("code", athorizedCode);

		try {
			WebResource r = c.resource(SocialoathtokenConstant.getTokenGetUrl(serviceType));
			//String response = r.queryParams(params).get(String.class);
			ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, params);
			JSONObject jsonObj = (JSONObject)JSONValue.parse(response.getEntity(String.class));
			if(jsonObj!=null) {
				if(jsonObj.containsKey("access_token")) {
				    access_token = jsonObj.get("access_token").toString();
				}
				if(jsonObj.containsKey("refresh_token")) {
					refresh_token = jsonObj.get("refresh_token").toString();
				}
				
				if(access_token!=null){
			        rtnArr = new String[]{access_token, refresh_token};
				}else {
					log.error("oauth call failed:"+jsonObj.toJSONString());
				}
			}else {
				log.error("oauth call failed, no json object returned");
			}
			
		} catch (UniformInterfaceException e) {
			String detail = e.getResponse().getEntity(String.class).toString();
			log.error(detail, e);
			throw e;
		} catch (com.sun.jersey.api.client.ClientHandlerException e){
			log.error("error here, servicetype:"+serviceType, e);
			if((e.getCause() instanceof java.net.ConnectException)
					||(e.getCause() instanceof java.net.SocketTimeoutException)){
				String servicename = SocialoathtokenConstant.getServiceTypeName(serviceType);
				throw new LogicException(ExceptionConstant.Error_Cannot_ConnectToService).appendExtraInfo_FirstKey(servicename);
			}else {
			    throw e;
			}
		}
	    
		return rtnArr;
	}
	
	/**
	 * 根据authorize code 得到user相关信息
	 * @param accesstoken
	 * @param serviceType
	 * @return String array, [userid, displayname, email]
	 */
	public String[] restGetUserInfo(String accesstoken, Short serviceType) {
		String userid = null;
		String displayname = null;
		String email = null;
		String[] rtnArr = null;
		if(accesstoken==null || accesstoken.trim().length()<1){
			return rtnArr;
		}
		
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("access_token", accesstoken);
		
		try {
			WebResource r = c.resource(SocialoathtokenConstant.getCurrentUserCallUri(serviceType));
			// must be GET method, because Live only allow GET
			String response = r.queryParams(params).get(String.class);
			//ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, params);
			//JSONObject jsonObj = (JSONObject)JSONValue.parse(response.getEntity(String.class));
			JSONObject jsonObj = (JSONObject)JSONValue.parse(response);
			if(jsonObj!=null) {
				if(SocialoathtokenConstant.ServiceType_MSN.equals(serviceType)){
					userid = jsonObj.get("id").toString();
					if(jsonObj.get("name")!=null){
					   displayname = jsonObj.get("name").toString();
					}
					if(jsonObj.get("emails")!=null
						&& (jsonObj.get("emails") instanceof JSONObject))
					{
						Object mailJsonObj = jsonObj.get("emails");
						if(((JSONObject)mailJsonObj).get("account")!=null){
							email = ((JSONObject)mailJsonObj).get("account").toString();
						}
					}
				}else if(SocialoathtokenConstant.ServiceType_Dropbox.equals(serviceType)){
					userid = jsonObj.get("uid").toString();
					if(jsonObj.get("display_name")!=null){
					   displayname = jsonObj.get("display_name").toString();
					}
					if(jsonObj.get("email")!=null){
						email = jsonObj.get("email").toString();
					}
				}else if(SocialoathtokenConstant.ServiceType_Google.equals(serviceType)){
					userid = jsonObj.get("id").toString();
					if(jsonObj.get("name")!=null){
					   displayname = jsonObj.get("name").toString();
					}
					if(jsonObj.get("email")!=null){
						email = jsonObj.get("email").toString();
					}
				}else if(SocialoathtokenConstant.ServiceType_Facebook.equals(serviceType)){
					userid = jsonObj.get("id").toString();
					if(jsonObj.get("name")!=null){
					   displayname = jsonObj.get("name").toString();
					}
					if(jsonObj.get("email")!=null){
						email = jsonObj.get("email").toString();
					}
				}else if(SocialoathtokenConstant.ServiceType_QQ.equals(serviceType)){
					WebResource r_qq = c.resource(SocialoathtokenConstant.getSocialUserIdCallUri(serviceType));
					// must be GET method, because Live only allow GET
					String response_qq = r_qq.queryParams(params).get(String.class);
					response_qq = StringUtil.stripStrSimple(response_qq, "callback(", ");");
					JSONObject jsonObj_qq = (JSONObject)JSONValue.parse(response_qq);
					if(jsonObj_qq!=null&&jsonObj_qq.get("openid")!=null){
					   userid = jsonObj_qq.get("openid").toString();
					}
					if(jsonObj.get("name")!=null){
					   displayname = jsonObj.get("nickname").toString();
					}
					// cannot get email from QQ
				}
				if(userid!=null){
				    rtnArr = new String[]{userid, displayname, email};
				}else {
					log.error("oauth get user failed:"+jsonObj.toJSONString());
				}
			}else {
				log.error("oauth call failed, no json object returned");
			}
			
		} catch (UniformInterfaceException e) {
			String detail = e.getResponse().getEntity(String.class).toString();
			log.error(detail, e);
			throw e;
		} catch (com.sun.jersey.api.client.ClientHandlerException e){
			log.error("error here, servicetype:"+serviceType, e);
			if((e.getCause() instanceof java.net.ConnectException)
					||(e.getCause() instanceof java.net.SocketTimeoutException)){
				String servicename = SocialoathtokenConstant.getServiceTypeName(serviceType);
				throw new LogicException(ExceptionConstant.Error_Cannot_ConnectToService).appendExtraInfo_FirstKey(servicename);
			}else {
			    throw e;
			}
		}
	    
		return rtnArr;
	}
	
	/**
	 * 验证access token是否有效
	 * 目前是为了用social帐号登录使用的
	 * @param accesstoken
	 * @param serviceType
	 * @return
	 */
	public boolean isAccessTokenValid(String accesstoken, Short serviceType, String socialuserid){
		if(accesstoken==null || accesstoken.trim().length()<1
			|| serviceType==null || socialuserid==null || "".equals(socialuserid)){
			return false;
		}
		boolean ret = false;
		
		String[] userinfoArr = restGetUserInfo(accesstoken, serviceType);
		if(userinfoArr!=null && userinfoArr.length==3 
			&& socialuserid.equals(userinfoArr[0])){
			ret = true;
		}
		
		return ret;
	}
	
	/**
	 * 当用户使用social方式登录(例如google+)，验证成功后，返回系统，系统会为该social帐号自动创建user
	 * 当需要创建新的系统用户时，loginname为_Social_${social email}, see: userlogic.geneSocialUserLoginName
	 * @param socialVo
	 * @param userVo
	 * @return User, 为null表示该social email对应的User已经在系统中存在了，可以直接登录了
	 *               如果不为null表示user不存在，并且刚刚insert进入系统，需要在界面上提示用户设置下自己的系统帐号信息，例如: loginname
	 */
	public User saveSocialLoginUser(Socialoathtoken socialVo, User userVo) {
		AssertUtil.assertNotNull(socialVo, null);
		AssertUtil.assertNotNull(socialVo.getServicetype(), null);
		AssertUtil.assertNotNull(socialVo.getSocialuserid(), null);
		AssertUtil.assertNotNull(userVo, null);
		UserLogic userlogic = BOFactory_Platform.getUserLogic();
		String email = userVo.getEmail();
		if(email==null){
			userVo.setEmail(socialVo.getSocialuseraccount());
			email = userVo.getEmail();
		}
		
		boolean needAddUser = true;
		
		Socialoathtoken socialDbVo = socialoathtokenDao.selectBySocialAccount(
				SocialoathtokenConstant.IdentityType_UserID,
                socialVo.getServicetype(), socialVo.getSocialuserid());
		
		User dbVo = null;
		if(email!=null && email.trim().length()>0){
			dbVo = UserDaoImpl.getInstance().selectByEmail(email);
		    if(dbVo!=null) {
		    	needAddUser = false;
		    }
		}else if(socialDbVo!=null){
			dbVo = UserDaoImpl.getInstance().selectByPK(socialDbVo.getIdentityid());
			needAddUser = false;
		}
		
		if(needAddUser){
			if(userVo.getContactinfo()==null){
	        	Usercontactinfo contactinfo = new Usercontactinfo();
	        	userVo.setContactinfo(contactinfo);
	        }
			if(userVo.getCustinfoex()==null){
				Custinfoex custinfoex = new Custinfoex();
				userVo.setCustinfoex(custinfoex);
			}
			userVo.setStagestatus(CustomerConstant.StageStatus_notSetLoginName);
			// 用户通过登录social产生的系统user, 因此设置为active状态, 以后用户可以继续通过social帐号登录系统
			userVo.setStatus(CustomerConstant.CustomerStatus_active);
			// generate loginname
			String loginname = userlogic.geneSocialUserLoginName(email);
			userVo.setLoginname(loginname);
			try{
				userVo = userLogic.insertUser(userVo, CustomerConstant.NewCreateUserType_SocialLoginAdd);
			}catch (DuplicateException e){
				log.error("shouldn't have this exception, because email checking already done in previous step!",e);
				needAddUser = false;
			}
		}else {
		    userVo.setUserid(dbVo.getUserid());
		}
        
		// add social object
		if(socialDbVo==null){
			socialVo.setIdentityid(userVo.getUserid());
			socialVo.setIdentitytype(SocialoathtokenConstant.IdentityType_UserID);
			socialoathtokenDao.insert(socialVo);
		}
		
		if(needAddUser){
			return userVo;
		}else {
			return null;
		}
	}
	    
    public static SocialoathtokenLogic getInstance() {
    	SocialoathtokenLogic logic = (SocialoathtokenLogic)BeanFactory.getBeanFactory().getBean("socialoathtokenLogic");
        return logic;
    }

	public SocialoathtokenDao getSocialoathtokenDao() {
		return socialoathtokenDao;
	}

	public void setSocialoathtokenDao(SocialoathtokenDao socialoathtokenDao) {
		this.socialoathtokenDao = socialoathtokenDao;
	}
	
	public UserLogic getUserLogic() {
		return userLogic;
	}

	public void setUserLogic(UserLogic userLogic) {
		this.userLogic = userLogic;
	}

}
