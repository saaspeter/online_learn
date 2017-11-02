package netTestWeb.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.SysparamConstantNettest;
import netTest.util.ResourceBundleUtil;
import netTest.util.UploadFileUtilNettest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import platform.vo.Shop;
import platform.vo.User;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.UploadFileUtil;
import commonWeb.base.BaseActionBase;
import commonWeb.security.authentication.UserinfoSession;

public class CopyOfCKEditorUploadServlet extends HttpServlet {

	/**
	 * 通过ckEditor上传文件，一般为图片文件
	 * 目前还无法删除文件，只能上传，而不能删除，大问题
	 */
	private static final long serialVersionUID = 1068741209485184532L;

	private static Logger logger = Logger.getLogger(CopyOfCKEditorUploadServlet.class);
	
	private static boolean enabled = false;// 是否开启CKEditor上传
	private static Map<String, List<String>> allowedExtensions;// 允许的上传文件扩展名
	private static Map<String, List<String>> deniedExtensions;// 阻止的上传文件扩展名
	private static SimpleDateFormat dirFormatter;// 目录命名格式:yyyyMM
	private static SimpleDateFormat fileFormatter;// 文件命名格式:yyyyMMddHHmmssSSS

	/**
	 * Servlet初始化方法
	 */
	public void init() throws ServletException {
		// 格式化目录和文件命名方式
		dirFormatter = new SimpleDateFormat("yyyyMM");
		fileFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 从web.xml中获取是否可以进行文件上传
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
		
		// 实例化允许的扩展名和阻止的扩展名
		allowedExtensions = new HashMap<String, List<String>>(3);
		deniedExtensions = new HashMap<String, List<String>>(3);
		// 从web.xml中读取配置信息
		allowedExtensions.put("File",
				stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File",
				stringToArrayList(getInitParameter("DeniedExtensionsFile")));

		allowedExtensions.put("Image",
				stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image",
				stringToArrayList(getInitParameter("DeniedExtensionsImage")));

		allowedExtensions.put("Flash",
				stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
		deniedExtensions.put("Flash",
				stringToArrayList(getInitParameter("DeniedExtensionsFlash")));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ((request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding("UTF-8");
		}
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		// 从请求参数中获取上传文件的类型：File/Image/Flash
		String typeStr = request.getParameter("Type");
		if (typeStr == null) {
			typeStr = "File";
		}
		// ckeditor文件路径的root路径
		String rootObjectType = request.getParameter("rootobjecttype");
		String pathprefix = request.getParameter("pathprefix");
		if(rootObjectType==null||rootObjectType.trim().length()<1){
			rootObjectType = Shop.ObjectType;
		}
		UserinfoSession loginfo = BaseActionBase.getLoginInfo();
		Long rootObjectId = null;
		if(Shop.ObjectType.equals(rootObjectType)){
			rootObjectId = loginfo.getShopid();
		}else if(User.ObjectType.equals(rootObjectType)){
			rootObjectId = loginfo.getUserid();
		}else {
			String rootobjectidStr = request.getParameter("rootobjectid");
			if(rootobjectidStr!=null && rootobjectidStr.trim().length()>0){
				rootObjectId = new Long(rootobjectidStr.trim());
			}
		}
		String parentObjectType = request.getParameter("parentObjectType");
		String parentObjectIdStr = request.getParameter("parentObjectId");
		Long parentObjectId = null;
		if(parentObjectIdStr!=null&&parentObjectIdStr.trim().length()>0){
			parentObjectId = new Long(parentObjectIdStr.trim());
		}
		
		String sourceType = request.getParameter("sourceType");
		String sourceIdStr = request.getParameter("sourceId");
		Long sourceId = null;
		if(sourceIdStr!=null&&sourceIdStr.trim().length()>0){
			sourceId = new Long(sourceIdStr.trim());
		}
		String errormess = null;
        String fileUrl = "";
		if (enabled) {
			String fileDir = UploadFileUtil.getUploadFileDir(pathprefix, rootObjectType, rootObjectId,  
														parentObjectType, parentObjectId, 
																	sourceType, sourceId);
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request);
				Map fields = new HashMap();
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField())
						fields.put(item.getFieldName(), item.getString());
					else
						fields.put(item.getFieldName(), item);
				}
				// CEKditor中file域的name值是upload
				FileItem uploadFile = (FileItem) fields.get("upload");
				if(uploadFile!=null){
					// 获取文件名并做处理
					String fileNameLong = uploadFile.getName();
					fileNameLong = fileNameLong.replace('\\', '/');
					String[] pathParts = fileNameLong.split("/");
					String fileName = pathParts[pathParts.length - 1];
					// 获取文件扩展名
					String ext = UploadFileUtil.getExtention(fileName);
					if (extIsAllowed(typeStr, ext)) {
						// newfilenameWithoutExt参数为null表示保留原上传文件名称
						fileUrl = UploadFileUtilNettest.saveFile(null, fileName, uploadFile.getInputStream(),uploadFile.getSize(), fileDir, SysparamConstantNettest.ModuleName_SingleFileSize_Article);
						// 得到返回给ckeditor的文件链接地址，用于引用上传的文件
						fileUrl = WebConstant.FileContext+fileUrl;
					} else {
						logger.error("无效的文件类型： " + ext);
					}
				}else {
					errormess = ExceptionConstantBase.Error_NoFile_Uploaded;
				}
				
			} catch (Exception ex) {
				if(ex instanceof LogicException){
					errormess = ex.getMessage();
				}
				logger.error("oops, got an exception: ", ex);
			}
		} else {
			logger.error("未开启CKEditor上传功能");
		}
		// CKEditorFuncNum是回调时显示的位置，这个参数必须有
		String callback = request.getParameter("CKEditorFuncNum");
		out.println("<script type=\"text/javascript\">");
		if(errormess==null || errormess.trim().length()<1){
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
					+ ",'" + fileUrl + "',''" + ")");
		}else {
			Locale locale = BaseActionBase.getLoginInfo().getLocale();
			errormess = ResourceBundleUtil.getInstance().getValue(errormess, locale, null, ResourceBundleUtil.bundleName_struts_BizKey);
			out.println("window.parent.alert('" + errormess + "')");
		}
		
		out.println("</script>");
		out.flush();
		out.close();
	}

	/**
	 * 字符串像ArrayList转化的方法
	 */

	private List<String> stringToArrayList(String str) {
		String[] strArr = str.split("\\|");
		List<String> rtnList = new ArrayList<String>();
		if (str.length() > 0) {
			for (int i = 0; i < strArr.length; ++i) {
				rtnList.add(strArr[i].toLowerCase());
			}
		}
		return rtnList;
	}

	/**
	 * 判断扩展名是否允许的方法
	 */
	private boolean extIsAllowed(String fileType, String ext) {
		ext = ext.toLowerCase();
		ArrayList allowList = (ArrayList) allowedExtensions.get(fileType);
		ArrayList denyList = (ArrayList) deniedExtensions.get(fileType);
		if (allowList.size() == 0) {
			if (denyList.contains(ext)) {
				return false;
			} else {
				return true;
			}
		}
		if (denyList.size() == 0) {
			if (allowList.contains(ext)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public String getResponseText(HttpServletRequest request, String fileUrl, 
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

}
