package netTest.util;

import java.io.InputStream;

import netTest.bean.BOFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.product.vo.Productbase;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import platform.logicImpl.BOFactory_Platform;
import platform.shop.constant.ShoplicenseConstant;
import platform.shop.vo.Shoplicense;
import platform.vo.Shop;
import platform.vo.User;
import commonTool.base.BaseEmptyEntity;
import commonTool.constant.CommonConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.UploadFileUtil;

public class UploadFileUtilNettest {
	
	static Logger log = Logger.getLogger(UploadFileUtilNettest.class.getName());
	
//	static{
//		if(SysparamConstant.uploadFilePath.endsWith(Character.toString(File.separatorChar)))
//			UploadFileUtilNettest.uploadFilePath = SysparamConstant.uploadFilePath;
//		else
//			UploadFileUtilNettest.uploadFilePath = SysparamConstant.uploadFilePath+File.separatorChar;
//		// 如果总的上传文件目录不存在，则创建
//        File pathDir = new File(UploadFileUtilNettest.uploadFilePath);
//        if(!pathDir.exists()){
//            pathDir.mkdir();
//        }
//	}
	
	//private static FileUtil FileUtil=null;
    /**  保存图片等文件存放的文件夹全路径,如f:\\upload\\，后面一定有文件分割符，
     *   放在配置文件sysParam.properties中配置  **/	
//	private static String uploadFilePath;
	
	/** 文件存储跟路径，如/shop/51/warequesFile/... **/
	public static final String File_Root_Shop = "shop";
	public static final String File_Root_User = "user";
	
	
//	/** 放置题库题目图片文件的目录 **/
//	public static final String WarequesFileDir = "warequesFile";
//	/** 放置试卷题目图片文件的目录 **/
//	public static final String PaperquesFileDir = "paperquesFile";
	    
	
    /**
     * 功能：创建新的图片文件 
     * @param name：图片的文件名，即问题的主键
     * @param oldFile：传递过来的用户提交的问题图片文件
     * @param dir：新创建的文件应保存的文件夹，如果该文件夹不存在就创建一个新的文件夹，如果存在则直接保存在该文件夹中。
     * 如果为null或为空表示需要创建一个为name的文件夹，把新创建的文件保存在文件夹中
     * @return 返回的是：该题目文件存放的文件夹名+该题目图片名
     */
    public static String saveFileFromStruts(String filename,FormFile oldFile,String dir,
    			    String uploadModuleName, String containerType, Long containerID){
        if(oldFile==null||oldFile.getFileSize()==0)
            return "";
        // check single file size, 如果不通过则抛出异常
        checkSingleFileSize(uploadModuleName, oldFile.getFileSize());
        String returnFileStr = UploadFileUtil.saveFileFromStruts(filename, oldFile, dir);
        satisFileStorage(containerType, containerID, (long)oldFile.getFileSize());
		return returnFileStr;
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
		
	//TODO 上传文件的时候检查商店或个人的storage大小是否足够
	public boolean checkTotalFileSize(String containerType, Long containerID, 
			                     Long filesize){
		boolean result = true;
		if(Shop.ObjectType.equals(containerType)){
			Shoplicense licenseVO = (Shoplicense)BOFactory_Platform.getShoplicenseDao().selectByPK(containerID, CommonConstant.SysCode_Education, 
									 ShoplicenseConstant.ResourceCode_Storage);
			if(licenseVO!=null){
				if(filesize>(licenseVO.getAllocatenum()-licenseVO.getUsenum())){
					result = false;
				}
			}
		}
		return result;
	}
	
    /**
     * 功能：删除文件，无论给出的是文件夹或文件，都将删除，
     * 如果是文件夹，则该文件夹中的所有文件都将被删除。
     * @param name:删除图片文件名，带后缀名。也可以包含路径。如果为空，则直接删除dir
     * @param dir:文件夹名称，一般为该题目的文件夹名称,可以留空
     */
    public static void delFile(String name, String dir, String containerType, Long containerid, Long filesize){
    	UploadFileUtil.delFile(name, dir);
    	// 处理storage的变化
    	satisFileStorage(containerType, containerid, filesize);
    }
    
    /**
     * 功能：把一个文件拷贝并重新命名. 
     * @param fileName_old:原文件的全名，带后缀名
     * @param filenameNew:新文件名，不带后缀名
     * @param dir:该文件所在的文件夹(在总的存放文件的名下的文件夹)，一般为题目的题目文件夹
     * @return 返回的是：该题目文件存放的文件夹名+该题目图片名
     */
    public static String copyFile(String fileName_old,String filenameNew,String dir){
    	String filepath = UploadFileUtil.copyFile(fileName_old, filenameNew, dir);
    	//TODO 处理storage的变化
    	return filepath;
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
	
	/**
	 * 检查单个文件尺寸大小限制
	 * filesize 单位都是byte
	 * 如果检查失败，则抛出exception
	 */ 
	private static void checkSingleFileSize(String moduleName, long filesize)
	{
		boolean result = true;
		Float filesizeLimit = SysparamConstantNettest.getSingleFileSizeLimit(moduleName);
		if(filesizeLimit!=null){
			if(filesize>filesizeLimit*1024){
				result = false;
			}
		}
		if(!result)
		   throw new LogicException(ExceptionConstantBase.Error_SingleFileSize_Overflow).appendExtraInfo("limit", filesizeLimit.toString());
	}
	
	//TODO 增加或减少文件的尺寸大小，暂时不做
	public static void satisFileStorage(String containerType, Long containerID, 
								 Long storageSize){
		if(Shop.ObjectType.equals(containerType) && containerID!=null){
			if(storageSize!=null){
				BOFactory_Platform.getShoplicenseDao().updateLicenseUsageSum(containerID, 
						CommonConstant.WebContext_Education, ShoplicenseConstant.ResourceCode_Storage, storageSize);
			}
			// if it is remove file, then need remove the file storage, 
			// if the url is a file, remove storage directly, if it is directory, then 
			
		}
		
	}
	
	// 处理上传文件结束后应该处理的逻辑
	public static void postUploadLogoImg(String fileUrl, Long filesize, BaseEmptyEntity object){
		if(Productbase.ObjectType.equals(object.getName())){
			// change product logo image
			Productbase vo = new Productbase();
			vo.setProductbaseid(object.getId());
			vo.setLogoimage(fileUrl);
			BOFactory.getProductbaseDao().updateByPK(vo);
		}else if(Shop.ObjectType.equals(object.getName())){
			// should change Shopstyleconfig.bannerimg, but current don't do it
			
		}else if(User.ObjectType.equals(object.getName())){
			// change user's logo image, current don't have this function
			
		}
	}

}
