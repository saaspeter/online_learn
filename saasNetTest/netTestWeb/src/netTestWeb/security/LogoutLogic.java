package netTestWeb.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.ui.logout.LogoutHandler;

import commonTool.cache.CacheSynchronizer;
import commonWeb.security.authentication.UserinfoSession;

public class LogoutLogic implements LogoutHandler {

	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		UserinfoSession sessinfo = null;
		Long userid = null;
		if(authentication!=null && (authentication.getPrincipal() instanceof UserinfoSession)){
        	sessinfo = (UserinfoSession)authentication.getPrincipal();
        	userid = sessinfo.getUserid();
        }
		// flush session level cache
		if(userid!=null){
		   CacheSynchronizer.getInstance().flushCache("netTest.userSessionCache", "UserSessionCache.UserJoinedShops~"+userid);
		}

	}

}
