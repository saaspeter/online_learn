package netTest.learncont.logic.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.common.constant.RolesConstant;
import netTest.event.EventHandlerNetTest;
import netTest.learncont.constant.LearncontentConstant;
import netTest.learncont.dao.LearncontentDao;
import netTest.learncont.logic.LearncontentLogic;
import netTest.learncont.vo.Learncontent;
import netTest.product.vo.Learnactivity;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userproduct;
import netTest.util.QiniuFileUtil;
import netTest.util.UploadFileUtilNettest;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.springframework.security.GrantedAuthority;

import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.social.constant.SocialoathtokenConstant;
import platform.social.logic.impl.SocialoathtokenLogicImpl;
import platform.social.vo.Socialoathtoken;
import platform.vo.Shop;
import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.concurrent.AbstractTaskRunnable;
import commonTool.constant.SysparamConstant;
import commonTool.event.Event;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;
import commonTool.util.UploadFileUtil;

public class LearncontentLogicImpl implements LearncontentLogic{
	
	static Logger log = Logger.getLogger(LearncontentLogicImpl.class.getName());
//	/** 放置学习内容文件的目录 **/
//	public static final String LearncontentFileDir = "learncontentFile";
	
	private LearncontentDao dao;
	
	
	/**
	 * get learncontent, if file source expires, then regain its source
	 * @param contentid
	 * @return
	 */
	public Learncontent getLearncontent(Long contentid){
		Learncontent vo = dao.selectByPK(contentid);
		if(vo==null){
			return vo;
		}
		if( (LearncontentConstant.isOauthFileLink(vo.getLinktype())
			 || LearncontentConstant.linktype_QiniuLink.equals(vo.getLinktype()))
			&& vo.getLinkfileid()!=null)
		{
			Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
			boolean needUpdateFileSource = false;
			if(vo.getLinkfilesource()==null||"".equals(vo.getLinkfilesource())
				||vo.getLinkfiledate()==null){
				needUpdateFileSource = true;
			}
			// 比较文件最后更新时间是否超时
			Integer expireTimeInterval = SysparamConstant.Oauth_FileSource_ExpireSecond;
			if(LearncontentConstant.linktype_QiniuLink.equals(vo.getLinktype())){
				expireTimeInterval = QiniuFileUtil.getInstance().getExpireTimeDownloadToken();
			}
			if(!needUpdateFileSource){
				Date expireDate = DateUtil.dateAddUnits(vo.getLinkfiledate(), Calendar.SECOND, expireTimeInterval);
				if(currentDate.compareTo(expireDate)>0){
					needUpdateFileSource = true;
				}
			}
			// 更新文件source
			if(needUpdateFileSource){
				String filesource = null;
				if(LearncontentConstant.isOauthFileLink(vo.getLinktype())){
				    filesource = SocialoathtokenLogicImpl.getInstance().getFileSource(vo.getShopid(), SocialoathtokenConstant.IdentityType_ShopID,
													vo.getLinktype(), vo.getLinkuserid(), vo.getLinkfileid());
				}else if(LearncontentConstant.linktype_QiniuLink.equals(vo.getLinktype())){
					filesource = QiniuFileUtil.getInstance().getDownloadUrl(vo.getLinkfileid());
				}
				if(filesource!=null){
				   vo.setLinkfilesource(filesource);
				   dao.updatefilesource(filesource, currentDate, vo.getContentid());
				}
			}
			if(LearncontentConstant.isOauthFileLink(vo.getLinktype()) 
					&& vo.getLinkuserid()!=null){
				Socialoathtoken tokenvo = BOFactory_Platform.getSocialoathtokenDao().selectBySocialAccount(SocialoathtokenConstant.IdentityType_ShopID,
										vo.getLinktype(), vo.getLinkuserid());
				if(tokenvo!=null){
					vo.setLinkusername(tokenvo.getSocialuseraccount());
				}
			}
		}
		return vo;
	}
	
	/**
	 * 保存学习内容，同时可以保存文件。
	 * 如果是编辑学习内容，如果是可以带文件的类型，则可以先删除文件，然后再创建
	 * if uploadFile is not null, upload file path like: 
	 * Driver:/filePath/shop/1/product9/learncontent/uploadFileName(pk).***
	 */
	public Learncontent save(Learncontent vo, FormFile uploadFile) throws Exception {
		if(vo==null||vo.getContenttype()==null)
			return null;
		boolean isAdd = true;
		if(vo.getContentid()!=null){
			isAdd = false;
		}else {
			AssertUtil.assertNotNull(vo.getShopid(), ExceptionConstantBase.Error_Need_Paramter);
		}
		
		boolean hasparent = vo.getParentid()==null?false:true;
		Learncontent oldVO = null;
		Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		if(vo.getLinkfilesource()!=null){
			vo.setLinkfiledate(currentDate);
		}
		if(uploadFile!=null && uploadFile.getFileSize()>0){
			vo.setLinkfilename(uploadFile.getFileName());
		}
		// set child file property
		if(vo.getParentid()!=null){
			// current, all children file is downloadable
			vo.setDownloadable(LearncontentConstant.Downloadable_yes);
			// current, only allow zip/tar file for downloadable file
			vo.setContenttype(LearncontentConstant.ContentType_Zipped);
			oldVO = dao.selectPlainByPK(vo.getContentid());
			vo.setShopid(oldVO.getShopid());
			vo.setContentcateid(oldVO.getContentcateid());
			vo.setIstry(oldVO.getIstry());
			vo.setProductbaseid(oldVO.getProductbaseid());
			vo.setStatus(LearncontentConstant.Status_Valide);
		}
		// save to DB
		Learncontent retVO = dao.save(vo);
		if(!isAdd){
			if(oldVO==null){
			   oldVO = dao.selectPlainByPK(vo.getContentid());
			}
			if(retVO.getShopid()==null){
				retVO.setShopid(oldVO.getShopid());
			}
		}
		// 如果之前有附加文件，并且上传文件流不为空或类型已经不是本地上传了，就删除老文件
		if(!isAdd&&oldVO!=null&&(LearncontentConstant.Linktype_Local.equals(oldVO.getLinktype()))
				&&(!LearncontentConstant.Linktype_Local.equals(retVO.getLinktype())
						||(uploadFile!=null&&uploadFile.getFileSize()>0)))
		{
			// delete file
			UploadFileUtilNettest.delFile(null, oldVO.getLinkfilesource(), Shop.ObjectType, oldVO.getShopid(), oldVO.getFilesize());
		}
		if(LearncontentConstant.Linktype_Local.equals(retVO.getLinktype())
				&& uploadFile!=null && uploadFile.getFileSize()>0){
			//TODO first check whether shop is a system internal shop, current, only system shop can upload file to local server
			if(!BOFactory_Platform.getShopLogic().ableUploadFileToLocalServer(retVO.getShopid())){
				throw new LogicException(ExceptionConstant.Error_Invalid_Visit).appendExtraInfo("shopid", retVO.getShopid().toString());
			}
			String filePath = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, retVO.getShopid(),
                    							Productbase.ObjectType, retVO.getProductbaseid(), 
                    							Learncontent.ObjectType, null);
			// save file
			String filemainName = LearncontentConstant.generateFileName(uploadFile, vo.getContentid().toString(), hasparent);
			String fileurl = UploadFileUtilNettest.saveFileFromStruts(filemainName,uploadFile,filePath,
					                        SysparamConstantNettest.ModuleName_SingleFileSize_LearnContent, null, null);
			// update file url
			dao.updatefilesource(fileurl, currentDate, retVO.getContentid());
		}
		// sent notification, update product resource number
		if(isAdd && !hasparent){
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("productid", vo.getProductbaseid().toString());
			paraMap.put("number", "1");
			paraMap.put("contenttype", vo.getContenttype().toString());
			Event event = new Event(EventHandlerNetTest.EventType_LearnContent_AddDelete, paraMap);
			EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Message);
		}
		return retVO;
	}
	
	/**
	 * 删除学习内容，包括文件
	 * @param pk
	 * @return
	 */
	public int delete(Long pk){
		if(pk==null)
			return 0;
		Learncontent retVO = dao.selectPlainByPK(pk);
		if(retVO==null){
			return 0;
		}
		boolean hasparent = retVO.getParentid()==null?false:true;
  
		// may have local file in server, so need delete in local server
		if(LearncontentConstant.Linktype_Local.equals(retVO.getLinktype())
			|| LearncontentConstant.ContentType_HTML.equals(retVO.getContenttype())){
			String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, retVO.getShopid(),  
													 Productbase.ObjectType, retVO.getProductbaseid(), 
													    Learncontent.ObjectType, retVO.getContentid());
			UploadFileUtilNettest.delFile(null, fileDir, Shop.ObjectType, retVO.getShopid(), retVO.getFilesize());
		}
		// 如果是qiniu存储，则从qiniu删除
		if(LearncontentConstant.linktype_QiniuLink.equals(retVO.getLinktype())
			&& !StringUtil.isEmpty(retVO.getLinkfileid())){
			QiniuFileUtil.getInstance().removeSingleFile(retVO.getLinkfileid(), Shop.ObjectType, retVO.getShopid(), retVO.getFilesize());
		}
		
		int num = dao.deleteByPK(pk);
        // sent notification
		if(num>0 && !hasparent){
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("productid", retVO.getProductbaseid().toString());
			paraMap.put("number", "-1");
			paraMap.put("contenttype", retVO.getContenttype().toString());
			Event event = new Event(EventHandlerNetTest.EventType_LearnContent_AddDelete, paraMap);
			EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Message);
		}
		return 1;
	}
	
	/**
	 * 根据product删除学习内容，不删除包含的文件file,由删除product的函数统一删除
	 * @param productid
	 * @param shopid
	 * @return
	 */
	public int deleteByProd(Long productid, Long shopid){
		long storage = dao.selectAllStorageByProd(productid);
		UploadFileUtilNettest.satisFileStorage(Shop.ObjectType, shopid, -storage);
		//
		List<String> fileIDList = dao.selectAllLinkedFileID(productid, LearncontentConstant.linktype_QiniuLink);
		// 异步删除qiniu文件
		Map paraMap = new HashMap();
		paraMap.put("fileIDList", fileIDList);
		EventHandlerNetTest.getInstance().getThreadExecutor().executeTask(
				AbstractParallelExecutor.Module_Event, 
                new AbstractTaskRunnable(paraMap){
                    public void run(){
                    	Map paraMap2 = (Map)this.getParamObject();
                    	List<String> fileIDList = (List<String>)paraMap2.get("fileIDList");
                    	QiniuFileUtil.getInstance().removeBatchFiles(fileIDList, null, null, null);
                    }
				});
		
		int rows = dao.deleteByProd(productid);
		return rows;
	}
	
	/**
	 * 删除每天自动产生的learncontent数据，目前是进入新增页面而没有保存产生的垃圾数据
	 * 供CleanDataJob调用
	 * @return
	 */
	public void deleteDirtyAutoSaveData(){
		Date todate = DateUtil.getInstance().getNowtime_GLNZ();
		// 删除生成10小时后的垃圾数据
		todate = DateUtil.dateAddHours(todate, 10);
		
		List<Learncontent> dataList = null;
		boolean keeprun = true;
		while(keeprun){
			dataList = dao.selectLongTimeData(todate, LearncontentConstant.Status_AutoSave);
			if(dataList!=null && dataList.size()>0)
			{   
				for(Learncontent tempVO : dataList){
					if(tempVO!=null && !StringUtil.isEmpty(tempVO.getLinkfilesource())){
						if(LearncontentConstant.Linktype_Local.equals(tempVO.getLinktype())){
						    UploadFileUtilNettest.delFile(null, tempVO.getLinkfilesource(), Shop.ObjectType, tempVO.getShopid(), tempVO.getFilesize());
						}else if(LearncontentConstant.linktype_QiniuLink.equals(tempVO.getLinktype())){
							QiniuFileUtil.getInstance().removeSingleFile(tempVO.getLinkfileid(), Shop.ObjectType, tempVO.getShopid(), tempVO.getFilesize());
						}
					}
				}
			}else {
				keeprun = false;
			}
		}
		// 删除DB中的数据
		dao.deleteLongTimeData(todate, LearncontentConstant.Status_AutoSave);
	}
	
	/**
	 * 发送用户学习的记录，仅对learncontent对象起作用，对于exercise,在做练习的时候已经自动生成该activity记录了
	 * @param vo
	 * @param userid
	 * @param learnstatus
	 * @param actiontype
	 */
	public boolean sendLearnActivity(Long objectid, Long userid, Short learnstatus, Short actiontype){
		if(objectid==null || userid==null || learnstatus==null || actiontype==null){
			log.error("error, parameter is null, objectid:"+objectid+" ,userid:"+userid
					+" ,learnstatus:"+learnstatus+" ,actiontype:"+actiontype);
			return false;
		}
		Learncontent vo = dao.selectPlainByPK(objectid);
		Userproduct userprod = BOFactory.getUserproductDao().selectByLogicPk(userid, vo.getProductbaseid());
		if(userprod!=null) {
			Learnactivity activityvo = null;
			//如果用户是学习的action, 并且用户曾经学完了该学习资料，则不更新该资料记录
			if(Learnactivity.Learnstatus_Progressing.equals(learnstatus) 
					&& actiontype.equals(Learnactivity.Actiontype_Learn)){
				activityvo = BOFactory.getLearnactivityDao().selectByPK(userid, objectid, Learncontent.ObjectType, actiontype);
			    if(activityvo!=null 
			    		&& Learnactivity.Learnstatus_Finished.equals(activityvo.getLearnstatus())){
			    	return true;
			    }
			}
			Map paraMap = new HashMap();
			activityvo = new Learnactivity();
			activityvo.setUserid(userid);
			activityvo.setActiontype(actiontype);
			activityvo.setContentcateid(vo.getContentcateid());
			activityvo.setEndtime(null);
			activityvo.setObjectid(objectid);
			activityvo.setObjecttype(Learncontent.ObjectType);
			activityvo.setProductid(vo.getProductbaseid());
			activityvo.setShopid(vo.getShopid());
			activityvo.setLearnstatus(learnstatus);
			activityvo.setStarttime(DateUtil.getInstance().getNowtime_GLNZ());
			paraMap.put("vo", activityvo);
		    Event event = new Event(EventHandlerNetTest.EventType_Product_LearnActivity, paraMap);
		    EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
		    return true;
		}else {
			log.error("error, user's product is null, userid:"+userid+" , productid:"+vo.getProductbaseid());
		    return false;
		}
	}
	
	// load对learncontent的权限，如果是可以try的，就付给权限
	public GrantedAuthority[] loadObjectAuthority(Long userid, Long objectid, Object targetObject){
		GrantedAuthority[] rtnArr = null;
		if(targetObject==null && objectid!=null){
			targetObject = dao.selectPlainByPK(objectid);
		}
		if(targetObject!=null){
			if(targetObject instanceof Learncontent){
				if(LearncontentConstant.IsTry_Try.equals(((Learncontent)targetObject).getIstry())){
					rtnArr = RolesConstant.GrantedAuthority_LearnContentAccessor;
				}
			}else {
				throw new LogicException("targetObject is not Learncontent");
			}
		}
		return rtnArr;
	}
	
	public GrantedAuthority[] loadContainerAuthority(Long userid, Long objectid, Long sessShopid, 
			 GrantedAuthority[] sessionAuthArr){
		GrantedAuthority[] auth = null;
		if(objectid!=null){
		    Learncontent vo = dao.selectPlainByPK(objectid);
		    auth = BOFactory.getProductLogic().loadContainerAuthority(userid, vo.getProductbaseid(), sessShopid, sessionAuthArr);
		}
		return auth;
	}
	
	/**
	 * 构造出文件的名称,仅仅是文件的名称
	 * @return
	 */
//	private String makeFilePref(Long pk){
//		if(pk==null)
//			return null;
//		return pk.toString()+"_cont";
//	}
//	
	/**
	 * 构造出文件的存放路径
	 * @param pk
	 * @return
	 */
//	private String makeFilePath(Long productbaseid){
//		if(productbaseid==null)
//			return null;
//		return LearncontentFileDir+File.separatorChar+productbaseid;
//	}

	public LearncontentDao getDao() {
		return dao;
	}

	public void setDao(LearncontentDao dao) {
		this.dao = dao;
	}
	
    /**
     * static spring getMethod
     */
     public static LearncontentLogic getInstance(){
    	 LearncontentLogic logic = (LearncontentLogic)BeanFactory.getBeanFactory().getBean("learncontentLogic");
         return logic;
     }

}
