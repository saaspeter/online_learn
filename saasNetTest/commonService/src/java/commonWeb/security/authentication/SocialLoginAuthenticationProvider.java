package commonWeb.security.authentication;

import org.springframework.dao.DataAccessException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import platform.social.constant.SocialoathtokenConstant;
import platform.social.logic.SocialoathtokenLogic;
import platform.social.logic.impl.SocialoathtokenLogicImpl;
import platform.vo.User;

import commonTool.constant.CommonConstant;

public class SocialLoginAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	//~ Instance fields ================================================================================================

	private SocialoathtokenLogic socialoathtokenLogic;
	
    private UserDetailsService userDetailsService;

    //~ Methods ========================================================================================================
    
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		String access_token = null;
		String socialuserid = null;
		Short servicetype = null;
    	
		if(authentication.getDetails()!=null && (authentication.getDetails() instanceof SocialAuthenticationDetails)){
			SocialAuthenticationDetails detailsVO = (SocialAuthenticationDetails)authentication.getDetails();
			access_token = detailsVO.getAccesstoken();
			socialuserid = detailsVO.getSocialuserid();
			servicetype = detailsVO.getServicetype();
		}
		
        if (access_token==null||"".equals(access_token)
        	|| socialuserid==null||socialuserid.trim().length()<1
        	    || servicetype==null) {
        	throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"),
                    "no access_token or no email or no servicetype");
        }
        socialuserid = socialuserid.trim();
        boolean checkresult = getSocialoathtokenLogic().isAccessTokenValid(access_token, servicetype, socialuserid);
        if (!checkresult) {
        	throw new BadCredentialsException("Bad access_token, expired or invalidate token",
                    "access_token:"+access_token+" | socialuserid:"+socialuserid+" | servicetype:"+servicetype);
        }
        // if social logic check successfully, set login type
        if(userDetails instanceof UserinfoSession){
        	((UserinfoSession)userDetails).setLogintype(getLoginTypeFromServiceType(servicetype));
        	((UserinfoSession)userDetails).setAccesstoken(access_token);
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;
        
        String account = null;
        if(authentication.getDetails()!=null && (authentication.getDetails() instanceof SocialAuthenticationDetails)){
			SocialAuthenticationDetails detailsVO = (SocialAuthenticationDetails)authentication.getDetails();
			account = detailsVO.getAccountemail();
			if(account==null||"".equals(account)){
				User uservo = getSocialoathtokenLogic().selectUserBySocialAccount( 
						detailsVO.getServicetype(), detailsVO.getSocialuserid());
				if(uservo!=null){
					account = uservo.getLoginname();
				}
			}
		}
        
        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(account);
        }catch (DataAccessException repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    private Short getLoginTypeFromServiceType(Short servicetype){
		if(servicetype!=null){
			if(SocialoathtokenConstant.ServiceType_Google.equals(servicetype)){
				return CommonConstant.LoginType_SocialLogin_Google;
			}else if(SocialoathtokenConstant.ServiceType_Facebook.equals(servicetype)){
				return CommonConstant.LoginType_SocialLogin_Facebook;
			}else if(SocialoathtokenConstant.ServiceType_QQ.equals(servicetype)){
				return CommonConstant.LoginType_SocialLogin_QQ;
			}
		}
		return null;
	}
    
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

	public SocialoathtokenLogic getSocialoathtokenLogic() {
		if(socialoathtokenLogic==null){
			socialoathtokenLogic = SocialoathtokenLogicImpl.getInstance();
		}
		return socialoathtokenLogic;
	}

	public void setSocialoathtokenLogic(SocialoathtokenLogic socialoathtokenLogic) {
		this.socialoathtokenLogic = socialoathtokenLogic;
	}
	
}
