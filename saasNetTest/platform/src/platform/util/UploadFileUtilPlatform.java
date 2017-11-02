package platform.util;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import platform.bean.SysparamConstantPlatform;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.UploadFileUtil;

public class UploadFileUtilPlatform {
	
	static Logger log = Logger.getLogger(UploadFileUtilPlatform.class.getName());
			
	/** 文件存储跟路径，如/shop/51/warequesFile/... **/
	public static final String File_Root_Shop = "shop";
	public static final String File_Root_User = "user";
	
	
	/**
	 * 检查单个文件尺寸大小限制
	 * filesize 单位都是byte
	 * 如果检查失败，则抛出exception
	 */ 
	private static void checkSingleFileSize(String moduleName, long filesize)
	{
		boolean result = true;
		Float filesizeLimit = SysparamConstantPlatform.getSingleFileSizeLimit(moduleName);
		if(filesizeLimit!=null){
			if(filesize>filesizeLimit*1024){
				result = false;
			}
		}
		if(!result)
		   throw new LogicException(ExceptionConstantBase.Error_SingleFileSize_Overflow).appendExtraInfo("limit", filesizeLimit.toString());
	}
	
    /**
     * 功能：创建新的图片文件 
     * @param newfilenameWithoutExt：文件的新名称，没有后缀名
     * @param uploadFileName：上传文件的文件名
     * @param input: 文件流
     * @param filesize: 上传文件的尺寸大小. 单位是byte
     * @param dir：新创建的文件应保存的文件夹，如果该文件夹不存在就创建一个新的文件夹，如果存在则直接保存在该文件夹中。
     * 如果为null或为空表示需要创建一个为name的文件夹，把新创建的文件保存在文件夹中
     * @return 返回的是：该题目文件存放的文件夹名+该题目图片名
     */
    public static String saveFile(String newfilenameWithoutExt, String uploadFileName,
    		                      InputStream input, long filesize, String dir, String uploadModuleName){
    	if(input==null)
            return "";
    	// check single file size, 如果不通过则抛出异常
        checkSingleFileSize(uploadModuleName, filesize);
        
        String filepath = UploadFileUtil.saveFile(newfilenameWithoutExt, uploadFileName, input, filesize, dir);
        return filepath;
    }
    
    public static String saveFileFromStruts(String filename,FormFile oldFile,String dir,String uploadModuleName){
        if(oldFile==null||oldFile.getFileSize()==0)
            return "";
        // check single file size, 如果不通过则抛出异常
        checkSingleFileSize(uploadModuleName, oldFile.getFileSize());
        String returnFileStr = UploadFileUtil.saveFileFromStruts(filename, oldFile, dir);
		return returnFileStr;
    }
		
    /**
     * 功能：删除文件，无论给出的是文件夹或文件，都将删除，
     * 如果是文件夹，则该文件夹中的所有文件都将被删除。
     * @param name:删除图片文件名，带后缀名。也可以包含路径。如果为空，则直接删除dir
     * @param dir:文件夹名称，一般为该题目的文件夹名称,可以留空
     */
    public static void delFile(String name, String dir){
    	UploadFileUtil.delFile(name, dir);
    	// TODO 处理storage的变化
    }
        
    /**
     * 删除dir目录下的文件前缀为prefixFilename的文件，不管文件的后缀名
     * @param prefixFilename
     * @param dir
     */
    public static void delFileByFileName(String prefixFilename, String dir){
    	UploadFileUtil.delFileByFileName(prefixFilename, dir);
    	//TODO 处理storage的变化
    }

}
