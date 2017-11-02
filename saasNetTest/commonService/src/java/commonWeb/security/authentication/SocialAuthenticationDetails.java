package commonWeb.security.authentication;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.ui.WebAuthenticationDetails;

/**
* change from org.springframework.security.ui.WebAuthenticationDetails
* for login with Google/Facebook/QQ case
*/

public class SocialAuthenticationDetails extends WebAuthenticationDetails {

	public static final String AccessToken_Parameter = "accesstoken";
	public static final String AccountEmail_Parameter = "accountemail";
	public static final String Servicetype_Parameter = "servicetype";
	public static final String SocialUserid_Parameter = "socialuserid";
	
    private String accesstoken;
    private String accountemail;
	private Short servicetype;
	private String socialuserid;
	
	public SocialAuthenticationDetails(HttpServletRequest request) {
		super(request);
	}
	
	public void doPopulateAdditionalInformation(HttpServletRequest request) {
		this.accesstoken = request.getParameter(AccessToken_Parameter);
	    this.accountemail = request.getParameter(AccountEmail_Parameter);
	    String servicetypeStr = request.getParameter(Servicetype_Parameter);
	    if(servicetypeStr!=null && servicetypeStr.trim().length()>0){
	    	servicetype = new Short(servicetypeStr.trim());
	    }
	    this.socialuserid = request.getParameter(SocialUserid_Parameter);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof SocialAuthenticationDetails) {
			SocialAuthenticationDetails rhs = (SocialAuthenticationDetails)obj;
			boolean ret = super.equals(obj);
			if(!ret){
				return ret;
			}
			if ((accesstoken != null && !accesstoken.equals(rhs.getAccesstoken()))
           	     || (rhs.getAccesstoken()!=null && !rhs.getAccesstoken().equals(accesstoken)) ) {
				return false;
            }
	          
	        if ((accountemail != null && !accountemail.equals(rhs.getAccountemail()))
	             || (rhs.getAccountemail()!=null && !rhs.getAccountemail().equals(accountemail)) ) {
	        	return false;
	        }

	        if ((servicetype != null && !servicetype.equals(rhs.getServicetype()))
	             || (rhs.getServicetype()!=null && !rhs.getServicetype().equals(servicetype)) ) {
	        	return false;
	        }
	        
	        if ((socialuserid != null && !socialuserid.equals(rhs.getSocialuserid()))
		             || (rhs.getSocialuserid()!=null && !rhs.getSocialuserid().equals(socialuserid)) ) {
	        	return false;
	        }
		}else {
		    return false;
		}
		
		return true;
	}
	
	public int hashCode() {
       int code = super.hashCode();

       if (this.accesstoken != null) {
           code = code * (this.accesstoken.hashCode() % 7);
       }
       
       if (this.accountemail != null) {
           code = code * (this.accountemail.hashCode() % 7);
       }
       
       if (this.servicetype != null) {
           code = code * (this.servicetype.hashCode() % 7);
       }
       
       if (this.socialuserid != null) {
           code = code * (this.socialuserid.hashCode() % 7);
       }

       return code;
    }
	
	public String getAccesstoken() {
		return accesstoken;
	}

	public String getAccountemail() {
		return accountemail;
	}

	public Short getServicetype() {
		return servicetype;
	}

	public String getSocialuserid() {
		return socialuserid;
	}

}
