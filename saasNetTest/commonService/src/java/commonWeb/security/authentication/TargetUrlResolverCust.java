package commonWeb.security.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.Authentication;
import org.springframework.security.ui.TargetUrlResolverImpl;
import org.springframework.security.ui.savedrequest.SavedRequest;

public class TargetUrlResolverCust extends TargetUrlResolverImpl{
	
	private String parenturlkey = "parentUrl";
	
	private String backurlkey = "backUrl";

	private String targeturlkey = "targetUrl";
	

	public String determineTargetUrl(SavedRequest savedRequest, HttpServletRequest currentRequest,
            Authentication auth) {

		String targetUrl = super.determineTargetUrl(savedRequest, currentRequest, auth);
		if(targetUrl==null||"".equals(targetUrl)){
			return targetUrl;
		}
		String parenturl = getComputParenturl(savedRequest);
		if(parenturl!=null){
			String returl = (parenturl.indexOf("?")==-1)?(parenturl+"?"):(parenturl+"&");
			returl += targeturlkey+"="+getComputTargetUrl(savedRequest);
			return returl;

		}else {
			return targetUrl;
		}
        
	}
	
	private String getComputTargetUrl(SavedRequest savedRequest){
		String[] urlArr = savedRequest.getParameterValues(targeturlkey);
		String returl = null;
		if(urlArr!=null && urlArr.length>0){
			returl = urlArr[0];
		}else {
			returl = "/"+buildRequestUrl(savedRequest.getRequestURI(), savedRequest.getContextPath(), savedRequest.getPathInfo(), savedRequest.getQueryString());
			try {
				returl = URLEncoder.encode(returl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("pos2, UTF-8 not supported. Shouldn't be possible, in TargetUrlResolverCust");
			}
		}
	    return returl;
	}
	
	private String getComputParenturl(SavedRequest savedRequest){
		if(savedRequest==null){
			return null;
		}
		String returl = null;
		String[] urlArr = savedRequest.getParameterValues(parenturlkey);
		if(urlArr!=null && urlArr.length>0){
			returl = urlArr[0];
		}else {
			urlArr = savedRequest.getParameterValues(backurlkey);
			if(urlArr!=null && urlArr.length>0){
				returl = urlArr[0];
			}
		}
		if(returl!=null){
			try {
				returl = URLDecoder.decode(returl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("UTF-8 not supported. Shouldn't be possible, in TargetUrlResolverCust");
			}
			returl = buildFullRequestUrl(savedRequest.getScheme(), savedRequest.getServerName(), savedRequest.getServerPort(), 
					null, returl, null, null);
		}
		return returl;
	}
	
	private static String buildFullRequestUrl(String scheme, String serverName, int serverPort, String contextPath,
	            String requestURI, String pathInfo, String queryString) {

        boolean includePort = true;

        if ("http".equals(scheme.toLowerCase()) && (serverPort == 80)) {
            includePort = false;
        }

        if ("https".equals(scheme.toLowerCase()) && (serverPort == 443)) {
            includePort = false;
        }
        contextPath = (contextPath==null)?"":contextPath;
        return scheme + "://" + serverName + ((includePort) ? (":" + serverPort) : "") + contextPath
        + buildRequestUrl(requestURI, contextPath, pathInfo, queryString);
    }
	
	private static String buildRequestUrl(String requestURI, String contextPath, String pathInfo,
	        String queryString) {

        String uri = requestURI;
        if(contextPath!=null){
        	uri = requestURI.substring(contextPath.length());
        }

        return uri + ((pathInfo == null) ? "" : pathInfo) + ((queryString == null) ? "" : ("?" + queryString));
    }
	
	
	public void setParenturlkey(String parenturlkey) {
		this.parenturlkey = parenturlkey;
	}
	
	public void setBackurlkey(String backurlkey) {
		this.backurlkey = backurlkey;
	}

	public void setTargeturlkey(String targeturlkey) {
		this.targeturlkey = targeturlkey;
	}
	
}
