package commonWeb.security.authorized;

import org.springframework.security.GrantedAuthority;

public class AuthorizeCheckUtil {

	/**
	 * if the authorities contain the role
	 * @param authorities
	 * @param role
	 * @return
	 */
	public static boolean containRole(GrantedAuthority[] authorities, String role){
		if(authorities!=null && authorities.length>0 && role!=null && role.trim().length()>0){
			for(GrantedAuthority authority : authorities){
				if(authority!=null && authority.getAuthority().endsWith(role)){
					return true;
				}
			}
		}
		return false;
	}
	
}
