package netTestWeb.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.EntityManager;
import netTest.bean.SysparamConstantNettest;
import netTest.util.UploadFileUtilNettest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import platform.exception.ExceptionConstant;
import platform.vo.Shop;
import platform.vo.User;
import commonTool.base.BaseEmptyEntity;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.StringUtil;
import commonTool.util.UploadFileUtil;
import commonWeb.base.BaseActionBase;
import commonWeb.security.authentication.UserinfoSession;

public abstract class AbstractUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 8338988004775087848L;

	private static Logger logger = Logger.getLogger(AbstractUploadServlet.class);
	// response返回的contentType
	private String responseContentType;
	// 允许上传文件的大小限制
	private Long maxFileSize;
		
	private Map<String, List<String>> allowedExtensions;// 允许的上传文件扩展名
	private Map<String, List<String>> deniedExtensions;// 阻止的上传文件扩展名
	// 在form中上传文件的input name
	private static final String Key_FileUpload_FieldName = "upload";
	
	protected String filenameType;
	protected String filenameSuffix;
	// 如何生成上传的文件名称，0为保留源文件名称，1为新生成文件名称，如果没有传值，这默认为0
	private static final String Key_FilenameType = "filenameType";
	// 当filenameType=1新生成文件名时，filenameSuffix为新生成文件后缀名
	private static final String Key_FilenameSuffix = "filenameSuffix";
	
	/**
	 * Servlet初始化方法
	 */
	public void init() throws ServletException {
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
		
		responseContentType = getInitParameter("responseContentType");
		if(responseContentType==null){
			responseContentType = "text/html";
		}
		String maxFileSizeStr = getInitParameter("maxFileSize");
		if(maxFileSizeStr!=null){
			maxFileSize = Long.parseLong(getInitParameter("maxFileSize"));
		}
		// 得到是否生成新文件，及新文件的后缀名
		filenameType = getInitParameter(Key_FilenameType);
		filenameSuffix = getInitParameter(Key_FilenameSuffix);
		
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
		response.setContentType(responseContentType+"; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		// 从请求参数中获取上传文件的类型：File/Image/Flash
		String typeStr = request.getParameter("Type");
		if (typeStr == null) {
			typeStr = "File";
		}
		// 文件路径的root路径
		String rootObjectType = request.getParameter("rootobjecttype");
		Long rootObjectId = null;
		String pathprefix = request.getParameter("pathprefix");
		
		UserinfoSession loginfo = BaseActionBase.getLoginInfo();
		if(rootObjectType==null||rootObjectType.trim().length()<1){
			rootObjectType = Shop.ObjectType;
		}
		if(Shop.ObjectType.equals(rootObjectType)){
			rootObjectId = loginfo.getShopid();
		}else if(User.ObjectType.equals(rootObjectType)){
			rootObjectId = loginfo.getUserid();
		}else {
			String rootobjectidstr = request.getParameter("rootobjectid");
			if(!StringUtil.isEmpty(rootobjectidstr)){
				rootObjectId = new Long(rootobjectidstr.trim());
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
		BaseEmptyEntity logicEntity = getRealLogicObject(sourceType, sourceId, 
										parentObjectType, parentObjectId, rootObjectType, rootObjectId);
		// 检查传入的参数是否是正确的
		securityCheck(rootObjectType, rootObjectId, logicEntity);
		
		String errormess = null;
        String fileUrl = "";
        String fileName = "";
        long filesize = 0;
        
//        String filenameType = request.getParameter(Key_FilenameType);
//        String filenameSuffix = request.getParameter(Key_FilenameSuffix);
        String newFileNameWithoutExt = geneNewFileName(filenameType, filenameSuffix, logicEntity);
		String fileDir = UploadFileUtil.getUploadFileDir(pathprefix, rootObjectType, rootObjectId,  
														 parentObjectType, parentObjectId, sourceType, sourceId);
		
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
			FileItem uploadFile = (FileItem) fields.get(Key_FileUpload_FieldName);
			if(uploadFile!=null){
				// pre handle upload file, e.g: delete existing file
				preUpload(fileDir, newFileNameWithoutExt, logicEntity);
				// 获取文件名并做处理
				fileName = UploadFileUtil.getFilenameFromFullPath(uploadFile.getName());
				
				// 获取文件扩展名
				String ext = UploadFileUtil.getExtention(fileName);
				filesize = uploadFile.getSize();
				// 检查文件上传大小
				if(maxFileSize!=null && filesize>maxFileSize){
					throw new LogicException(ExceptionConstant.Error_SingleFileSize_Overflow);
				}
				if (extIsAllowed(typeStr, ext)) {
					// newfilenameWithoutExt参数为null表示保留原上传文件名称
					fileUrl = UploadFileUtilNettest.saveFile(newFileNameWithoutExt, fileName, 
										uploadFile.getInputStream(),filesize, fileDir, 
										SysparamConstantNettest.ModuleName_SingleFileSize_Article);
					// 处理上传文件结束后的动作
					postUpload(fileUrl, filesize, logicEntity);
					// 得到返回的文件链接地址，用于引用上传的文件
					fileUrl = WebConstant.FileContext+fileUrl;
				} else {
					logger.error("invalid file suffix, don't allow this type: " + ext);
				}
			}else {
				logger.error("oops, got an error, ExceptionConstantBase.Error_NoFile_Uploaded ");
				errormess = ExceptionConstantBase.Error_NoFile_Uploaded;
			}
			
		} catch (Exception ex) {
			if(ex instanceof LogicException){
				errormess = ex.getMessage();
			}
			logger.error("oops, got an exception: ", ex);
		}
		
		String responseText = getResponseText(request, fileName, fileUrl, filesize, errormess);
		out.println(responseText);
		
		out.flush();
		out.close();
	}
	
	protected abstract String getResponseText(HttpServletRequest request, String oldFileName,
										String fileUrl, Long filesize, String errorMessage);

	/**
	 * 字符串像ArrayList转化的方法
	 */

	private List<String> stringToArrayList(String str) {
		if(str==null||"".equals(str)){
			return new ArrayList<String>();
		}
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
		List<String> allowList = allowedExtensions.get(fileType);
		List<String> denyList = deniedExtensions.get(fileType);
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

	private String geneNewFileName(String filenameType, String filenameSuffix, 
									BaseEmptyEntity object){
		String newFileName = null;
        if("1".equals(filenameType)){
        	if(filenameSuffix==null){
        		filenameSuffix = "";
        	}
        	
        	newFileName = UploadFileUtil.geneDefaultFileMainName(object.getName(), object.getId(), filenameSuffix);
        }
        return newFileName;
	}
	
	private BaseEmptyEntity getRealLogicObject(String sourceType, Long sourceId,
			   								   String parentObjectType, Long parentObjectId,
			   								   String rootObjectType, Long rootObjectId)
	{
		String objectType = "";
    	Long objectId = null;
    	if(!StringUtil.isEmpty(sourceType) && sourceId!=null){
    		objectType = sourceType;
    		objectId = sourceId;
    	}else if(!StringUtil.isEmpty(parentObjectType) && parentObjectId!=null){
    		objectType = parentObjectType;
    		objectId = parentObjectId;
    	}else if(!StringUtil.isEmpty(rootObjectType) && rootObjectId!=null){
    		objectType = rootObjectType;
    		objectId = rootObjectId;
    	}
    	return new BaseEmptyEntity(objectId, objectType);
	}
	
	// 简单点的数据检查，检查用户要操作的数据是否是自己所属商店的数据
	protected void securityCheck(String rootObjectType, Long rootObjectId, BaseEmptyEntity logicEntity){
		if(StringUtil.isEmpty(rootObjectType)||rootObjectId==null
			||logicEntity==null){
			throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
		}
		boolean securitycheck = false;
		Object targetObj = EntityManager.getInstance().getEntity(logicEntity.getName(), logicEntity.getId(), null);
		if(targetObj!=null){
		   	securitycheck = true;
		}else {
		   	securitycheck = false;
		}
		
		if(!securitycheck){
			throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
		}
		
	}
	
	/**
	 * 上传前要做的业务逻辑，删除已存在的文件, 各子类可以覆盖定义自己的方法
	 * @param fileDir
	 * @param newFileNameWithoutExt: new filename without file extension
	 * @param object
	 */
	protected void preUpload(String fileDir, String newFileNameWithoutExt, BaseEmptyEntity object){
		UploadFileUtilNettest.delFileByFileName(newFileNameWithoutExt, fileDir);
	}
	
	/**
	 * 处理上传结束后的业务逻辑，比如更新fileUrl
	 * @param fileUrl
	 * @param filesize
	 * @param filename
	 * @param object
	 */
	protected abstract void postUpload(String fileUrl, Long filesize, BaseEmptyEntity object);
	
}
