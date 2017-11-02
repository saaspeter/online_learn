package netTest.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.apache.log4j.Logger;
import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.BatchStatRet;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.EntryPath;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;

import commonTool.constant.SysparamConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.Base64Util;
import commonTool.util.EncrypPBE;
import commonTool.util.StringUtil;

public class QiniuFileUtil {
	
	static Logger log = Logger.getLogger(QiniuFileUtil.class.getName());
	
	// one is long, the other is int, 
	private static long Qiniu_Uptoken_ExpireTime;
	private static int Qiniu_Downloadtoken_ExpireTime;
	
	private static String Qiniu_BucketName;
	private static String Qiniu_Domainame;
	private static String Qiniu_Upload_CallbackUrl;
	
	/** call back verify key time interval: 8 hours **/
	private static final long MaxIntervalTime = 28800000;
	
	private static QiniuFileUtil instance;
	
	private static Mac mac;
	
	public static QiniuFileUtil getInstance() {
		if(instance==null){
			instance = new QiniuFileUtil();
			instance.init();
		}
		return instance;
	}
	
	private QiniuFileUtil(){ }
	
	private void init(){
		Config.ACCESS_KEY = SysparamConstant.getProperty("qiniu.ACCESS_KEY");
		Config.SECRET_KEY = SysparamConstant.getProperty("qiniu.SECRET_KEY");
		mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		Qiniu_BucketName = SysparamConstant.getProperty("qiniu.bucketname");
		Qiniu_Domainame = SysparamConstant.getProperty("qiniu.domainame");
		Qiniu_Uptoken_ExpireTime = Long.parseLong(SysparamConstant.getProperty("qiniu.uploadToken.time"));
		Qiniu_Downloadtoken_ExpireTime = Integer.parseInt(SysparamConstant.getProperty("qiniu.downloadToken.time"));
		Qiniu_Upload_CallbackUrl = SysparamConstant.getProperty("qiniu.upload.callback");
	}
	
	public int getExpireTimeDownloadToken(){
		return Qiniu_Downloadtoken_ExpireTime;
	}
	
	public String geneUploadToken() {
		// 请确保该bucket已经存在
        PutPolicy putPolicy = new PutPolicy(Qiniu_BucketName);
        putPolicy.expires = Qiniu_Uptoken_ExpireTime;
        putPolicy.scope = Qiniu_BucketName;
        String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			log.error("get upload token error1", e);
			throw new LogicException(e);
		} catch (JSONException e) {
			log.error("get upload token error2", e);
			throw new LogicException(e);
		}
        return uptoken;
	}
	
	/**
	 *  返回一个数组，第一元素是uploadtoken, 第二个是 verifykey
	 * @param objecttype
	 * @param objectid
	 * @return
	 */
	public String[] geneUploadTokenWithCBUrl(String objecttype, Long objectid) {
		// 请确保该bucket已经存在
        PutPolicy putPolicy = new PutPolicy(Qiniu_BucketName);
        putPolicy.expires = Qiniu_Uptoken_ExpireTime;
        putPolicy.scope = Qiniu_BucketName;
        // qiniu的callback
        String verifykey = EncrypPBE.getInstance().encryptStr(objecttype+"_"+objectid+"_"+System.currentTimeMillis());
		String callbackurl = Qiniu_Upload_CallbackUrl+verifykey;
        putPolicy.callbackUrl = callbackurl;
        putPolicy.callbackBody = "hash=$(etag)&size=$(fsize)&name=$(fname)";
        String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			log.error("get upload token error1", e);
			throw new LogicException(e);
		} catch (JSONException e) {
			log.error("get upload token error2", e);
			throw new LogicException(e);
		}
        return new String[]{uptoken, verifykey};
	}
	
	public String getDownloadUrl(String fileIdKey) {
		String baseUrl;
        String downloadUrl;
		try {
			baseUrl = URLUtils.makeBaseUrl(Qiniu_Domainame, fileIdKey);
			GetPolicy getPolicy = new GetPolicy();
			getPolicy.expires = Qiniu_Downloadtoken_ExpireTime;
			downloadUrl = getPolicy.makeRequest(baseUrl, mac);
		} catch (EncoderException e) {
			log.error("download error1 for fileIdKey:"+fileIdKey+" | domain:"+Qiniu_Domainame, e);
			throw new LogicException(e).appendExtraInfo("fileIdKey", fileIdKey);
		} catch (AuthException e){
			log.error("download error2 for fileIdKey:"+fileIdKey+" | domain:"+Qiniu_Domainame, e);
			throw new LogicException(e).appendExtraInfo("fileIdKey", fileIdKey);
		}
		return downloadUrl;
	}
	
	// list object prefix files, for the auto cleaning file, 
	// list all objecttype file keys, then Logic method will find these keys in object table, if no, then can remove these files
	public List<String> listAllFile(String objecttype){
		RSFClient client = new RSFClient(mac);
        String marker = "";
        List<String> retList = new ArrayList<String>();
            
        ListPrefixRet ret = null;
        String prefix = constructUploadFilePrefixName(objecttype, null);
        while (true) {
            ret = client.listPrifix(Qiniu_BucketName, prefix, marker, 10);
            marker = ret.marker;
            if(ret.results!=null){
            	for(ListItem item:ret.results){
            		retList.add(item.key);
            	}
            }
            if (!ret.ok()) {
                // no more items or error occurs
                break;
            }
        }
        if (ret.exception.getClass() != RSFEofException.class) {
            // 
        	log.error("list file error for objecttype:"+objecttype, ret.exception); 
        	throw new LogicException(ret.exception).appendExtraInfo("objecttype", objecttype);
        }
        
        return retList;
	}
	
	public void removeSingleFile(String filekey, String containerType, Long containerID, 
			 					 Long storageSize){
		RSClient client = new RSClient(mac);
        client.delete(Qiniu_BucketName, filekey);
        // handle storage
        UploadFileUtilNettest.satisFileStorage(containerType, containerID, storageSize);
	}
	
	public void removeBatchFiles(List<String> keyList, String containerType, Long containerID, 
			 					 Long totalStorageSize){
		if(keyList==null || keyList.size()<1){
			return;
		}
		RSClient rs = new RSClient(mac);
        List<EntryPath> entries = new ArrayList<EntryPath>();
        for(String key : keyList){
        	EntryPath e1 = new EntryPath();
            e1.bucket = Qiniu_BucketName;
            e1.key = key;
            entries.add(e1);
        }
        BatchStatRet bsRet = rs.batchStat(entries);
        // handle storage
        UploadFileUtilNettest.satisFileStorage(containerType, containerID, totalStorageSize);
	}
	
	public static String constructUploadFilePrefixName(String objecttype, Long objectid){
		AssertUtil.assertNotEmpty(objecttype, null);
		if(objectid==null){
			return "_"+objecttype+":";
		}else {
		    return "_"+objecttype+":"+objectid+"_";
		}
	}
	
	public static String getOriginalFilename(String filename){
		AssertUtil.assertNotEmpty(filename, null);
		return filename.replaceFirst("_[A-Za-z0-9]+:(\\d)+_", "");
	}
	
	// 暂时不用callback
	private String geneCallbackUrl(String objecttype, Long objectid){
		String encodeParameter = objecttype+"_"+objectid+"_"+System.currentTimeMillis();
		String callbackurl = Qiniu_Upload_CallbackUrl+EncrypPBE.getInstance().encryptStr(encodeParameter);
		return callbackurl;
	}
	
	public String[][] verifyAnalysizeCallback(String verifykey){
		AssertUtil.assertNotEmpty(verifykey, "verifykey is empty in qiniu callback url");
		String decryString = EncrypPBE.getInstance().decryptStr(verifykey);
		String[] strArr = StringUtil.splitString(decryString, "_");
		String errorcode = "";
		if(strArr!=null && strArr.length==3){
			if(System.currentTimeMillis()<Long.parseLong(strArr[2])+MaxIntervalTime){
				return new String[][]{{strArr[0], strArr[1]}};
			}else {
				errorcode = "callback time internal expired";
			}
		}else {
			errorcode = "error format verifykey";
		}
		throw new LogicException(ExceptionConstantBase.Error_Invalid_Parameter).appendExtraInfo("reason", errorcode);
	}
	
	public Long getFileSize(String fileId){
		if(StringUtil.isEmpty(fileId)){
			return null;
		}
		try {
			RSClient client = new RSClient(mac);
			Entry entry = client.stat(Qiniu_BucketName, fileId);
			return entry.getFsize();
		} catch (Exception e) {
			log.error("fileId:"+fileId, e);
		}
		return null;
	}
	
}