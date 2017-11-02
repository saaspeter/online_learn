package commonWeb.security.filter;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.providers.anonymous.AnonymousProcessingFilter;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonWeb.base.KeyInMemoryConstant;
import commonWeb.security.authentication.UserinfoSession;

public class SaasAnonymousProcessingFilter extends AnonymousProcessingFilter {
	
//	private ShopDao shopDao = null;
	
	/**
	 * 设置用户商店等信息
	 */
	protected Authentication createAuthentication(HttpServletRequest request) {
		Authentication auth = super.createAuthentication(request);
		//HttpServletRequest httpRequest = (HttpServletRequest) request;
//		String shopidStr = httpRequest.getParameter("shopid");
//		Long shopid = null;
//		if(shopidStr!=null&&shopidStr.trim().length()>0)
//			shopid = new Long(shopidStr.trim());
		
		GrantedAuthority[] authties = null;
		if(auth!=null)
			authties = auth.getAuthorities();
		UserinfoSession userinfo = new UserinfoSession(null, null, true, true, true, true,
				authties);
		userinfo.setAnonymous(true);
//        // 查找商店并检查商店的状态(TODO)。并且加载商店编码
//		try {
//			if(shopid!=null){
//				shopDao = this.getShopDao();
//				Shop shop = shopDao.selectByPK(shopid, null);
//				if(shop!=null)
//					userinfo.setShopcode(shop.getShopcode());
//				else{
//					//TODO 此处应该是抛出异常
//				}
//			}
//		} catch (Exception e) {
//			throw new DaoException("error.common.dataaccesserror",e);
//		}
		HttpSession session = request.getSession(true);
		Locale locale = (Locale)session.getAttribute(KeyInMemoryConstant.SessionKey_LocaleUserSelect);
		Long localeid = (Long)session.getAttribute(KeyInMemoryConstant.SessionKey_LocaleidUserSelect);
		String jsSuffix = (String)session.getAttribute(KeyInMemoryConstant.SessionKey_UserJsSuffix);
		if(locale==null || localeid==null){
			locale = request.getLocale();
			// 根据request中的locale查询localeid, 如果不是被支持的locale则使用系统默认的localeid
			localeid = I18nLogicImpl.getLocaleid(locale);
			// 在此重新查询一次Locale，因为如果用户request使用的Locale不在系统有的Locale,则使用系统默认的Locale
			locale = I18nLogicImpl.getLocale(localeid); 
			// 设置struts中的国家语言
			session.setAttribute(Globals.LOCALE_KEY, locale);
			jsSuffix = "_"+locale.getLanguage()+"_"+locale.getCountry();
			
			// 在session中设置用户自己选择的locale
			userinfo.setUseLocale(locale, request);
			userinfo.setUseLocaleid(localeid, request);
			userinfo.setUseJsSuffix(jsSuffix, request);
		}
		
		// 设置用户自己选择的locale
		userinfo.setLocaleid(localeid);
		userinfo.setLocale(locale);
		userinfo.setJsSuffix(jsSuffix);
		
		auth = new AnonymousAuthenticationToken(this.getKey(), userinfo, authties);
		return auth;
	}
    	
}
