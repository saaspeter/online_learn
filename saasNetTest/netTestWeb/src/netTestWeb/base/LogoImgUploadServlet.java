package netTestWeb.base;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTest.util.ResourceBundleUtil;
import netTest.util.UploadFileUtilNettest;
import commonTool.base.BaseEmptyEntity;
import commonWeb.base.BaseActionBase;

public class LogoImgUploadServlet extends AbstractUploadServlet {

	private static final long serialVersionUID = 1210801636634998814L;
	
	public String getResponseText(HttpServletRequest request, String oldFileName, String fileUrl, 
								  Long filesize, String errorMessage){
		StringBuffer responseTxtBuffer = new StringBuffer();
		String status = "1";
		if(errorMessage!=null && errorMessage.trim().length()>0){
			Locale locale = BaseActionBase.getLoginInfo().getLocale();
			errorMessage = ResourceBundleUtil.getInstance().getValue(errorMessage, locale, null, ResourceBundleUtil.bundleName_struts_BizKey);
			status = "2";
		}
		
		responseTxtBuffer.append("{\"status\":"+status+",")
					     .append("\"uploadfilename\":\"" + oldFileName +"\",")
					     .append("\"fileurl\":\"" + fileUrl + "\",")
					     .append("\"filesize\":\"" + filesize + "\",")
					     .append("\"message\":\"" + errorMessage + "\"")
					     .append("}");
		return responseTxtBuffer.toString();
	}
	
	protected void postUpload(String fileUrl, Long filesize, BaseEmptyEntity object){
		UploadFileUtilNettest.postUploadLogoImg(fileUrl, filesize, object);
	}

}
