package netTestWeb.social.action;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.exception.ExceptionConstant;
import netTestWeb.base.BaseAction;
import netTestWeb.base.WebConstant;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import platform.social.constant.SocialoathtokenConstant;
import platform.social.dao.SocialoathtokenDao;
import platform.social.dao.impl.SocialoathtokenDaoImpl;
import platform.social.logic.SocialoathtokenLogic;
import platform.social.logic.impl.SocialoathtokenLogicImpl;
import platform.social.vo.Socialoathtoken;
import platform.vo.User;
import commonTool.biz.logic.ConstantInf;
import commonTool.biz.logicImpl.ConstantLogicImpl;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;
import commonWeb.security.authentication.SocialAuthenticationDetails;
 
public class OauthFlowAction extends BaseAction {
	
	static Logger log = Logger.getLogger(OauthFlowAction.class.getName());


	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

        if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("goselectfile".equals(myaction)) {
			myforward = goselectfile(mapping, actionform, request,response);
		} else if ("saveloginwith".equals(myaction)) {
			myforward = saveloginwith(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;  
        
	}
	

	private ActionForward goselectfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String servicetypeCode = request.getParameter("servicetype");
		AssertUtil.assertNotEmpty(servicetypeCode, null);
		Short servicetype = new Short(servicetypeCode.trim());
		String authorizeCode = request.getParameter("code");
		String accessToken = request.getParameter("access_token");
		String socialuserid = request.getParameter("socialuserid");
		String displayname = request.getParameter("displayname");
		
		Long shopid = getLoginInfo().getShopid();
		
		Socialoathtoken tokenVO = saveToken(shopid, SocialoathtokenConstant.IdentityType_ShopID, 
                servicetype, socialuserid, displayname, null, authorizeCode, accessToken);
		
		request.setAttribute("oauthAppID", SocialoathtokenConstant.getOauthAppID(servicetype));
		if(tokenVO!=null) {
		    request.setAttribute("socialuserid", tokenVO.getSocialuserid());
		    request.setAttribute("key_accesstoken", tokenVO.getAccesstoken());
		}
		
		String url = "";
		if(SocialoathtokenConstant.ServiceType_MSN.equals(servicetype)){
			url = "selectfilepage_skydrive";
		}else if(SocialoathtokenConstant.ServiceType_Dropbox.equals(servicetype)){
			url = "selectfilepage_dropbox";
		}else if(SocialoathtokenConstant.ServiceType_Google.equals(servicetype)){
			url = "selectfilepage_google";
		}
		
		return mapping.findForward(url);
	}
	
	// 如果shop没有token记录则插入，否则更新，只有在access token或refresh token快过期的时候才更新
    private Socialoathtoken saveToken(Long identity, Short identitytype, Short serviceType,
    		                  		  String socialuserid, String displayname, String socialuseraccount,
    		                  		  String authorizeCode, String accessToken)
    {
    	SocialoathtokenDao dao = SocialoathtokenDaoImpl.getInstance();
    	Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
    	SocialoathtokenLogic logic = SocialoathtokenLogicImpl.getInstance();
    	String[] tokenArr = null;
    	String[] userinfoArr = null;
    	if((socialuserid==null || displayname==null) 
    			&& authorizeCode!=null && !"".equals(authorizeCode)){
    		if(accessToken==null){
    	        tokenArr = logic.restGetAllToken(authorizeCode, serviceType);
    	        if(tokenArr!=null) {
    		       accessToken = tokenArr[0];
    	        }
    		}
    	    userinfoArr = logic.restGetUserInfo(accessToken, serviceType);
    	    if(userinfoArr!=null){
    	       socialuserid = userinfoArr[0];
    	       displayname = userinfoArr[1];
    	       socialuseraccount = userinfoArr[2];
    	    }
    	}
    	if(socialuserid==null || socialuserid.trim().length()<1){
    		return null;
    	}
    	
    	Socialoathtoken vo = dao.selectBySocialAccount(identitytype, serviceType, socialuserid);
    	
    	if(vo==null){
    		if(tokenArr==null && authorizeCode!=null && authorizeCode.length()>0){
    			tokenArr = logic.restGetAllToken(authorizeCode, serviceType);
    		}
    		if(tokenArr!=null){
	    		vo = new Socialoathtoken();
	    		vo.setIdentityid(identity);
	    		vo.setIdentitytype(identitytype);
	    		vo.setServicetype(serviceType);
	    		vo.setSocialuserid(socialuserid);
	    		vo.setSocialuseraccount(socialuseraccount);
	    		vo.setDisplayname(displayname);
	    		vo.setAccesstoken(tokenArr[0]);
	    		vo.setRefreshtoken(tokenArr[1]);
	    		vo.setUpdatetimeaccess(currentDate);
	    		vo.setUpdatetimerefresh(currentDate);
	    		vo.setCreatedate(currentDate);
	    		dao.insert(vo);
    		}
		}else {
			Date refresh_expireDate = DateUtil.dateAddDays(vo.getUpdatetimerefresh(), SysparamConstant.Oauth_RefreshToken_ExpireDay);
			Date access_expireDate = DateUtil.dateAddUnits(vo.getUpdatetimeaccess(), Calendar.SECOND, SysparamConstant.Oauth_AccessToken_ExpireSecond);
			boolean isupdate = false;
			// 如果refresh token到期了,则更新
			if(SocialoathtokenConstant.supportRefreahToken(serviceType)){
				if(currentDate.compareTo(refresh_expireDate)>0 || StringUtil.isEmpty(vo.getRefreshtoken())){
					if(tokenArr==null){
					   tokenArr = logic.restGetAllToken(authorizeCode, serviceType);
					}
					accessToken = tokenArr[0];
					vo.setAccesstoken(accessToken);
		    		vo.setRefreshtoken(tokenArr[1]);
					vo.setUpdatetimerefresh(currentDate);
					vo.setUpdatetimeaccess(currentDate);
					vo.setDisplayname(displayname);
					isupdate = true;
				}
			}
			// 如果access token到期了,则仅更新access token
			if(currentDate.compareTo(access_expireDate)>0 || StringUtil.isEmpty(vo.getAccesstoken())){
				if(accessToken==null){
					String accesstoken = logic.getAccessTokenByRefreshToken(vo.getRefreshtoken(), serviceType);
					vo.setAccesstoken(accesstoken);
				}else {
					vo.setAccesstoken(accessToken);
				}
				vo.setUpdatetimeaccess(currentDate);
				vo.setDisplayname(displayname);
				isupdate = true;
			}
			if(isupdate){
			    dao.updateByPK(vo);
			}
		}
    	return vo;
    }
    
    /**
     * 用户使用social login成功后跳到该链接，在本系统中为该social帐号创建一个系统帐号，并且把social中的信息通过过来
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private ActionForward saveloginwith(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	String accesstoken = request.getParameter("accesstoken");
    	String accountemail = request.getParameter("accountemail");
    	String servicetypeStr = request.getParameter("servicetype");
    	String displayname = request.getParameter("displayname");
    	String socialuserid = request.getParameter("socialuserid");
    	String genderStr = request.getParameter("gender");
    	String localeStr = request.getParameter("locale");
    	String timezoneStr = request.getParameter("timezone");
    	if(timezoneStr!=null){
    		timezoneStr = timezoneStr.trim();
    	}
    	    	
    	AssertUtil.assertNotEmpty(servicetypeStr, null);
    	AssertUtil.assertNotEmpty(accesstoken, null);
    	AssertUtil.assertNotEmpty(socialuserid, null);
    	
    	Short servicetype = new Short(servicetypeStr.trim());
    	
    	SocialoathtokenLogic logic = SocialoathtokenLogicImpl.getInstance();
    	// validate access token
    	boolean isvalidate = logic.isAccessTokenValid(accesstoken, servicetype, socialuserid);
    	if(!isvalidate){
    		throw new LogicException(ExceptionConstant.Error_Invalid_Visit).appendExtraInfo_FirstKey("access token is invalidate or expired!");
    	}
    	// assemble user profile from social login request, then save them
    	Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
    	Socialoathtoken socialVo = new Socialoathtoken();
    	socialVo.setAccesstoken(accesstoken);
    	socialVo.setCreatedate(currentDate);
    	socialVo.setDisplayname(displayname);
    	socialVo.setIdentitytype(SocialoathtokenConstant.IdentityType_UserID);
    	socialVo.setServicetype(servicetype);
    	socialVo.setSocialuseraccount(accountemail);
    	socialVo.setSocialuserid(socialuserid);
    	socialVo.setUpdatetimeaccess(currentDate);
    	
    	Locale inputLocale = I18nLogicImpl.getRawLocale(localeStr);
    	Long localeid = I18nLogicImpl.getLocaleid(inputLocale);
    	
    	User userVo = new User();
    	userVo.setDisplayname(displayname);
    	userVo.setEmail(accountemail);
    	if(genderStr!=null&&genderStr.trim().length()>0){
    		ConstantInf inf = ConstantLogicImpl.getInstance();
    		String genderShortStr = inf.getValue(CustomerConstant.Gender_ConstCode, genderStr.trim());
    		if(genderShortStr!=null&&genderShortStr.trim().length()>0){
    			userVo.setGender(Short.parseShort(genderShortStr));
    		}
    	}
    	userVo.setLocaleid(localeid);
    	userVo.setRegistime(currentDate);
    	userVo.setTimezone(timezoneStr);
    	
    	userVo = logic.saveSocialLoginUser(socialVo, userVo);
    	// if returned userVo is null, means user email already in system, 
    	// so directly forward to login url with email and access_token, /spring_security_login
//    	ActionForward forward = null;
//    	//if(userVo==null){
//    		forward = new ActionForward("/spring_security_login"
//					+"?"+SocialAuthenticationDetails.AccessToken_Parameter+"="+accesstoken
//					+"&"+SocialAuthenticationDetails.AccountEmail_Parameter+"="+accountemail
//					+"&"+SocialAuthenticationDetails.Servicetype_Parameter+"="+servicetypeStr);
    		//TODO
    	//}else {
    	// if returned userVo is not null, then need jump to a page which need user to confirm system account information,
    	// especially the loginname need to be updated
    	//TODO
    	//	forward = mapping.findForward(url);
    	//}
    	if(accountemail==null){
    		accountemail = "";
    	}
		String url = WebConstant.WebContext+"/spring_security_login"
				+"?"+SocialAuthenticationDetails.AccessToken_Parameter+"="+accesstoken
				+"&"+SocialAuthenticationDetails.AccountEmail_Parameter+"="+accountemail
				+"&"+SocialAuthenticationDetails.Servicetype_Parameter+"="+servicetypeStr
				+"&"+SocialAuthenticationDetails.SocialUserid_Parameter+"="+socialuserid;
		super.setMessUrlPage(request, url, null, null, "BizKey");
		return mapping.findForward("toUrl");
	}
	
}
