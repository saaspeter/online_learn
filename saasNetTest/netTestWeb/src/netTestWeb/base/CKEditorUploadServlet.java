package netTestWeb.base;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTest.util.ResourceBundleUtil;
import commonTool.base.BaseEmptyEntity;
import commonWeb.base.BaseActionBase;

public class CKEditorUploadServlet extends AbstractUploadServlet {

	private static final long serialVersionUID = 3168848749103062533L;

	public String getResponseText(HttpServletRequest request, String oldFileName, String fileUrl, 
								  Long filelength, String errorMessage){
		StringBuffer responseTxtBuffer = new StringBuffer();
		// CKEditorFuncNum是回调时显示的位置，这个参数必须有
		String callback = request.getParameter("CKEditorFuncNum");
		responseTxtBuffer.append("<script type=\"text/javascript\">");
		if(errorMessage==null || errorMessage.trim().length()<1){
			responseTxtBuffer.append("window.parent.CKEDITOR.tools.callFunction(" + callback
					+ ",'" + fileUrl + "',''" + ")");
		}else {
			Locale locale = BaseActionBase.getLoginInfo().getLocale();
			errorMessage = ResourceBundleUtil.getInstance().getValue(errorMessage, locale, null, ResourceBundleUtil.bundleName_struts_BizKey);
			responseTxtBuffer.append("window.parent.alert('" + errorMessage + "')");
		}
		
		responseTxtBuffer.append("</script>");
		return responseTxtBuffer.toString();
	}
	
	@Override
	protected void postUpload(String fileUrl, Long filesize, BaseEmptyEntity object){
		return;
	}

}
