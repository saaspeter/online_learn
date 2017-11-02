package commonWeb.base;

import javax.servlet.http.HttpServletRequest;

public class RequestresponseUtil {

	/**
	 * get client ip, considering server is using nginx proxy
	 * @param request
	 * @return
	 */
	public static String getClintIP(HttpServletRequest request){
		String ip = request.getHeader("X-Real-IP");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("X-Forwarded-For");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }   
	    return ip;  
	}
	
}
