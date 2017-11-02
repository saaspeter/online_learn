package netTest.learncont.constant;

import org.apache.struts.upload.FormFile;

import commonTool.util.UploadFileUtil;

import platform.social.constant.SocialoathtokenConstant;

public class LearncontentConstant {

	/** 是否是试用的资料. 1 正常使用资料，非试用 **/
	public static final Short IsTry_Normal = 1;
	/** 是否是试用的资料. 1 试用课程资料 **/
	public static final Short IsTry_Try = 2;
	
	/** 学习的类型， 0: zipped file type, 1：html文档资料，2：pdf文档; 
	 * 3：word文档，4: powerpoint；5:excel文档，6：视频资料，7 音频资料,8：flash课件(swf) **/
	public static final Integer ContentType_Zipped = 0;
    public static final Integer ContentType_HTML = 1;
    public static final Integer ContentType_PDF = 2;
    public static final Integer ContentType_WORD = 3;
    public static final Integer ContentType_PPT = 4;
    public static final Integer ContentType_EXCEL = 5;
    public static final Integer ContentType_VIDEO = 6;
    public static final Integer ContentType_AUDIO = 7;
    public static final Integer ContentType_FLASH = 8;
    
    /** 上传文件方式, 1是本地上传, 2是外部文件直接链接,不需要转换, 3是在线的video/audio地址,如youtube/youku
     *  4为qiniu.com的存储方式
     *  其余情况uploadfileway为null **/
    public static final Short Linktype_Local = 1; 
    public static final Short Linktype_DirectLINK = 2;
    public static final Short Linktype_OnlineLINK = 3;
    // 七牛存储的文件类型
    public static final Short linktype_QiniuLink = 4;
    public static final Short Linktype_DropboxLINK = SocialoathtokenConstant.ServiceType_Dropbox;
    public static final Short Linktype_GoogleDriveLINK = SocialoathtokenConstant.ServiceType_Google;
    public static final Short Linktype_SkydirveLINK = SocialoathtokenConstant.ServiceType_MSN;
    
    public static final Short Status_Valide = 1;
    public static final Short Status_InValide = 2;
    /** 自动保存状态，不能被正常搜索出来 **/
    public static final Short Status_AutoSave = 3;
    
    /** 1: can download, 2: cannot download **/
    public static final Short Downloadable_yes = 1;
    public static final Short Downloadable_no = 2;
    
    /** 学习方式，1 可以在线学习，2 附件，可供下载，即contenttypeContentType_Zipped **/
    public static final Short LearnType_Online = 1;
    public static final Short LearnType_Attachment = 2;
	
    
    public static boolean isOauthFileLink(Short linktype){
    	if(Linktype_SkydirveLINK.equals(linktype)
    			||Linktype_DropboxLINK.equals(linktype)
    			||Linktype_GoogleDriveLINK.equals(linktype)){
    		return true;
    	}else {
    		return false;
    	}
    }
        
	public static String getPageObjectType(Integer contenttype){
		String str = "";
		if(LearncontentConstant.ContentType_HTML.equals(contenttype))
			str = "text/html";
		else if(LearncontentConstant.ContentType_PDF.equals(contenttype))
			str = "application/pdf";
		else if(LearncontentConstant.ContentType_WORD.equals(contenttype))
			str = "application/msword";
		else if(LearncontentConstant.ContentType_PPT.equals(contenttype))
			str = "application/vnd.ms-powerpoint";
		else if(LearncontentConstant.ContentType_EXCEL.equals(contenttype))
			str = "application/vnd.ms-excel";
		else if(LearncontentConstant.ContentType_VIDEO.equals(contenttype))
			str = "flv";
		else if(LearncontentConstant.ContentType_AUDIO.equals(contenttype))
			str = "flv";
		else if(LearncontentConstant.ContentType_FLASH.equals(contenttype))
			str = "application/x-shockwave-flash";
		else if(LearncontentConstant.ContentType_Zipped.equals(contenttype))
			str = "application/x-download";
		return str;
	}
    
	/**
	 * 生成learncontent的文件名
	 * @param uploadfile
	 * @param objectid
	 * @param hasparent
	 * @return
	 */
	public static String generateFileName(FormFile uploadfile, String objectid, boolean hasparent){
		String filename = null;
		String uploadFileName = uploadfile.getFileName();
		if(hasparent){
			filename = UploadFileUtil.getMainFileName(uploadFileName)+"("+"attach_"+objectid+")";
		}else {
			filename = UploadFileUtil.getMainFileName(uploadFileName)+"("+objectid+")";
		}
		return filename;
	}
	
}
