package platform.logicImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;
import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.bean.SysparamConstantPlatform;
import platform.constant.ProductcategoryConstant;
import platform.constant.ShopConstant;
import platform.constant.ShopappConstant;
import platform.constant.UsershopConstant;
import platform.dao.ShopDao;
import platform.dao.ShopappDao;
import platform.dao.ShopstatuslogDao;
import platform.dao.ShopstyleconfigDao;
import platform.dao.ShopvalueDao;
import platform.dao.UsershopDao;
import platform.daoImpl.UseraccountsettingDaoImpl;
import platform.dto.ShopQuery;
import platform.event.EventHandlerPlatform;
import platform.exception.ExceptionConstant;
import platform.logic.ShopLogic;
import platform.logic.UsershopLogic;
import platform.shop.dao.ShopcontactinfoDao;
import platform.shop.dao.ShopextDao;
import platform.shop.vo.Shopcontactinfo;
import platform.shop.vo.Shopext;
import platform.util.UploadFileUtilPlatform;
import platform.vo.Productcategory;
import platform.vo.Shop;
import platform.vo.ShopMini;
import platform.vo.Shopapp;
import platform.vo.Shopstatuslog;
import platform.vo.Shopstyleconfig;
import platform.vo.User;
import platform.vo.Useraccountsetting;
import platform.vo.Usershop;
import commonTool.biz.vo.I18n;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.constant.UsernotificationConstant;
import commonTool.event.Event;
import commonTool.exception.DuplicateException;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.UploadFileUtil;

public class ShopLogicImpl implements ShopLogic {

	static Logger log = Logger.getLogger(ShopLogicImpl.class.getName());
	
	private ShopDao dao;
	private ShopvalueDao valueDao;
	//private ShopfuncLogic funcLogic;
	private UsershopLogic usershopLogic;
//    private BaseaccountDao accountDao;
//    private BaseaccountLogic accountLogic;
	private ShopcontactinfoDao shopcontactinfoDao;
    private ShopstatuslogDao shopstatuslogDao;
    private UsershopDao usershopDao;
    private ShopappDao shopappDao;
    private ShopextDao shopextDao;
	private ShopstyleconfigDao shopstyleconfigDao;
    
    /**
     * 判断商店的名字或商店code是否重复
     * @param shopname
     * @param shopcode
     * @return 1:都不重复，2为商店code重复，3为商店名重复
     */
    public String checkDepulicateShop(String shopname, String shopcode){
    	String rtn = "1";
    	if(shopcode!=null){
    		shopcode = shopcode.trim();
    		if("".equals(shopcode)){
    			rtn = "2";
    		}else {
    		    boolean exist0 = dao.existcheckByCode(shopcode);
    		    if(exist0){
    			   rtn = "2";
    		    }
    		}
    	}
    	if(shopname!=null){
    		shopname = shopname.trim();
    		if("".equals(shopname)){
    			rtn = "3";
    		}else {
    		    boolean exist1 = valueDao.existcheckByname(shopname);
    		    if(exist1){
    			   rtn = "3";
    		    }
    		}
    	}
    	return rtn;
    }
	
	/**
	 * 申请商店，如果是新增商店则保存商店的功能，给商店添加一个商店帐户
	 * 此处以后可能要改，因为可能要根据子系统不同而分别在子系统中执行不同的操作，不一定所有的商店都要加单位，
	 * 商店要和系统联系起来
	 * @param vo：商店vo
	 * @param funcArr：功能code的数组
	 */
	public Shopapp saveApplyNewShop(Shopapp appvo,String prodcates) 
	       throws DuplicateException,Exception{
		if(appvo==null)
			return null;
        // 检查商店code, name是否有重名的
		// 在商店申请表中增加一条商店申请的记录
		boolean ret1 = dao.existcheckByCode(appvo.getShopcode());
		if(ret1){
			throw new DuplicateException(ExceptionConstant.Error_Shopcode_alreadyExist);
		}
		ret1 = valueDao.existcheckByname(appvo.getShopname());
		if(ret1){
			throw new DuplicateException(ExceptionConstant.Error_Shopname_alreadyExist);
		}
			
		appvo.setAppdate(DateUtil.getInstance().getNowtime_GLNZ());
		appvo.setAppstatus(ShopappConstant.AppStatus_needApprove);
		shopappDao.insert(appvo);
		return appvo;
	}
	
	/**
	 * 批量审批商店
	 * @param arrs
	 * @param appvo
	 * @return
	 * @throws Exception
	 */
	public int approveShopBatch(String[] shopappidArr, Shopapp appvo) throws Exception{
		if(shopappidArr==null||shopappidArr.length<1||appvo==null||appvo.getAppstatus()==null)
			return 0;
		int num = 0;
		Shop shop = null;
		try {
			for(int i=0;i<shopappidArr.length;i++){
				if(shopappidArr[i]!=null&&shopappidArr[i].trim().length()>0){
					appvo.setShopappid(new Long(shopappidArr[i]));
					if(shop!=null){
					   if(this.approveShop(appvo))
						   num++;
					}
				}
			}
		} catch (Exception e) {
			log.error("ShopLogicImpl的方法approveShopBatch(String[] arrs,Shopapp appvo)出错!", e);
			throw e;
		}
		return num;
	}
	
	/**
	 * 管理员审批商店，根据审批状态结果分别处理
	 * 正在审批状态:一般是租户用来修改申请信息时使用，改变商店信息和申请审批状态
	 * 审核通过时： 新建商店帐户；更新商店管理员改变商店的信息；更新商店状态为正常使用状态；在学习系统中添加一个顶级单位
	 * 审核商店不通过且要删除商店的操作：删除商店信息；删除用户商店信息
	 * 审核商店不通过且需要用户修改商店的信息：修改商店信息
	 * @param vo
	 * @param appvo
	 * @return
	 * @throws Exception
	 */
	public boolean approveShop(Shopapp inputAppvo) throws Exception{
		if(inputAppvo==null||inputAppvo.getShopappid()==null
				||inputAppvo.getAppstatus()==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		Short appstatus = inputAppvo.getAppstatus();
		// 目前在审批过程中，系统管理员能修改的仅仅是 审批状态和审批说明
		Shopapp appvo = shopappDao.selectByPK(inputAppvo.getShopappid());
		appvo.setAppstatus(appstatus);
		appvo.setNotes(inputAppvo.getNotes());
		if(appstatus.equals(ShopappConstant.AppStatus_pass)){
		   if(this.approvePassShop(appvo)){
		      appvo.setAppstatus(ShopappConstant.AppStatus_pass);
		   }else
			   return false;
		}else if(appstatus.equals(ShopappConstant.AppStatus_deny_del)){
		   if(this.approveDenyDelShop(appvo)){
		      appvo.setAppstatus(ShopappConstant.AppStatus_deny_del);
		   }else
			   return false;
		}else {
			throw new LogicException(ExceptionConstant.Error_Shopapp_appstatusNotValidate);
		}
		// 更新appvo
		appvo.setReplydate(DateUtil.getInstance().getNowtime_GLNZ());
		shopappDao.updateByPK(appvo);
		return true;
	}
	
	/**
	 * 审核通过时调用，处理审核通过时：
	 * 新建商店帐户；更新商店管理员改变商店的信息；更新商店状态为正常使用状态；
	 * 在学习系统中添加一个顶级单位
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private boolean approvePassShop(Shopapp appvo) throws Exception{
		if(appvo==null)
			return false;
        //TODO 在账户表中增加一个商店帐户
		try {
			// 插入商店记录
			Shop vo = new Shop();
			vo.setLocaleid(appvo.getLocaleid());
			vo.setShopname(appvo.getShopname());
			vo.setShopcode(appvo.getShopcode());
			vo.setUserid(appvo.getUserid());
			vo.setOpentype(appvo.getOpentype());
			vo.setRegisdate(appvo.getAppdate());
			vo.setUsernumscale(appvo.getUsernumscale());
			vo.setBizarea(appvo.getBizarea());
			vo.setOwnertype(appvo.getOwnertype());
			vo.setIsauthen(ShopConstant.IsAuthen_no);
			vo.setChargetype(ShopConstant.ChargeType_free); // 刚刚审批过的都是免费商店
			vo.setShopstatus(ShopConstant.ShopStatus_run);
			Long shopid = dao.insert(vo); // 同时插入了shopValue表
			vo.setShopid(shopid);
			appvo.setShopid(shopid);
			// 插入地址信息
			Shopcontactinfo contactvo = new Shopcontactinfo();
			contactvo.setAddress(appvo.getAddress());
			contactvo.setContactname(appvo.getContactname());
			contactvo.setEmail(appvo.getEmail());
			contactvo.setIsdefault(Shopcontactinfo.Isdefault_default);
			contactvo.setLocaleid(appvo.getLocaleid());
			contactvo.setPostcode(appvo.getPostcode());
			contactvo.setRegioncode(appvo.getRegioncode());
			contactvo.setShopid(shopid);
			contactvo.setTelephone(appvo.getTelephone());
			shopcontactinfoDao.insert(contactvo);
			// 插入商店主信息
		    Usershop usershop = new Usershop();
		    usershop.setShopid(shopid);
		    usershop.setUserid(vo.getUserid());
		    usershop.setJoindate(appvo.getAppdate());
		    usershop.setAreainproduct(UsershopConstant.AreaInProduct_all);
		    usershop.setUsershopstatus(UsershopConstant.UserShopStatus_active);
		    // 查询并设置用户的nickname和loginame
		    User creator = platform.logicImpl.BOFactory_Platform.getUserDao().selectByPK(appvo.getUserid());
		    usershop.setNickname(creator.getDisplayname());
		    usershop.setLoginname(creator.getLoginname());
		    UsershopDao usershopDao = platform.logicImpl.BOFactory_Platform.getUsershopDao();
		    usershopDao.insert(usershop);
		    // 插入商店创建者的Role，
		    
            //TODO 插入商店功能
	        //TODO 添加商店选择的课程目录
			
			// send shop created event
            // event处理: 添加一个顶级单位; 插入商店创建者的Role, 暂时用education的syscode
			Map paraMap = new HashMap();
			paraMap.put("userid", vo.getUserid());
			paraMap.put("shopid", shopid);
			paraMap.put("shopname", vo.getShopname());
			paraMap.put("syscode", CommonConstant.SysCode_Education);
			Event event = new Event(EventHandlerPlatform.EventType_Shop_ApprovePass, paraMap);
			EventHandlerPlatform.getInstance().publishEvent(event, EventHandlerPlatform.HandleType_Asyschronized_Message);
			
            // event处理: 新增商店名字和code的反向索引
			Map paraMapIndex = new HashMap();
			paraMapIndex.put("handleType", SearchIndexImpl.HandleType_AddIndex);
			paraMapIndex.put("indextextArr", new String[]{vo.getShopcode(),vo.getShopname()});
			paraMapIndex.put("entityid", shopid);
			paraMapIndex.put("entitytype", Shop.ObjectType);
			Event indexEvent = new Event(EventHandlerPlatform.EventType_SearchIndex_Handle, paraMapIndex);
			EventHandlerPlatform.getInstance().publishEvent(indexEvent, EventHandlerPlatform.HandleType_Asyschronized_Message);
			return true;
		} catch (Exception e) {
			log.error("ShopLogicImpl的方法approvePassShop(Shop vo)出错!", e);
			throw e;
		}
	}
	
	/**
	 * 审核商店不通过且要删除商店的操作：删除商店信息；删除用户商店信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private boolean approveDenyDelShop(Shopapp vo) {
		return true;
	}
	
	/**
	 * 更新用户商店基本信息，不涉及商店其他信息
	 * @param vo：商店vo
	 */
	public Shop updateShop(Shop vo) throws Exception{
		if(vo==null||vo.getShopid()==null)
			return null;
		// 修改商店基本信息
		Shop old_shopVO = dao.selectByPK(vo.getShopid(), vo.getLocaleid());
		dao.updateByPK(vo);
		// 如果shopcode或shopname改变了，需要发送改变search index的event
		if(old_shopVO!=null){
			Map paraMapIndex = new HashMap();
			paraMapIndex.put("handleType", SearchIndexImpl.HandleType_UpdateIndex);
			paraMapIndex.put("entityid", vo.getShopid());
			paraMapIndex.put("entitytype", Shop.ObjectType);
			Event indexEvent = new Event(EventHandlerPlatform.EventType_SearchIndex_Handle, paraMapIndex);
			// 发送shopcode change的search index data
			if(vo.getShopcode()!=null && !vo.getShopcode().equals(old_shopVO.getShopcode())){
				paraMapIndex.put("indexText", old_shopVO.getShopcode());
				paraMapIndex.put("newIndexText", vo.getShopcode());
				EventHandlerPlatform.getInstance().publishEvent(indexEvent, EventHandlerPlatform.HandleType_Asyschronized_Message);
			}
			if(vo.getShopname()!=null && !vo.getShopname().equals(old_shopVO.getShopname())){
				paraMapIndex.put("indexText", old_shopVO.getShopname());
				paraMapIndex.put("newIndexText", vo.getShopname());
				EventHandlerPlatform.getInstance().publishEvent(indexEvent, EventHandlerPlatform.HandleType_Asyschronized_Message);
			}
		}
		return vo;
	}
		
	/**
	 * 根据商店id，删除商店信息，包括商店的多种语言设置信息
	 * TODO　在删除之前有很多东西要做，暂时还不能用
	 * @param shopid
	 * @return
	 * @throws Exception
	 */
    public int delShop(Long shopid) throws Exception {
        int ret = 0;
    	try {
    		// TODO 需要判断该商店的很多信息，如果这些信息都满足了才能删除该商店
    		
    		// 删除商店中的用户的业务数据(可以调用学习系统的webservices)，个人答卷(UserAnswer)、考试人员信息(TestUser)、机构人员信息(OrgUser)
    		
    		// 删除学习考试相关业务数据，包括：题库信息，试卷信息，考试信息
		
    		// 删除商店对应的单位小组信息orgbase,deptinfo;
    		
    		// 删除商店建立的相关产品信息ProductBase和人员选择的产品信息等

    		// 删除商店人员usershop;
    		   usershopDao.deleteByShop(shopid);
    		// 删除商店的帐号计费信息
    		   //accountLogic.delAccountByObj(new Long(keys[i]));
    		// 删除商店
    		   shopstatuslogDao.deleteByShop(shopid);
			   ret = dao.deleteByPK(shopid);
			// 删除ReverseSearch中的商店数据
			   
		} catch (Exception e) {
			log.error("ShopLogicImpl的方法deleteShop(Long shopid)出错!", e);
			throw e;
		}
    	return ret;
    }
    
	/**
	 * 删除商店的某个语言设置,如果只剩一个语言设置了，则抛出异常
	 * @param shopid
	 * @param localeid
	 * @return 
	 * @throws Exception
	 */
//   public int delShopValue(Long shopid,Long localeid) throws NoLocaleValueException,Exception{
//	   int ret = 0;
//	   ShopvalueQuery queryValueVO = new ShopvalueQuery();
//	   queryValueVO.setShopid(shopid);
//	   queryValueVO.setLocaleid(localeid);
//	   try {
//		 List list = valueDao.selectByVO(queryValueVO);
//		 if(list!=null&&list.size()>1)
//		   ret = valueDao.deleteByVO(queryValueVO);
//		 else 
//		    throw new NoLocaleValueException();
//	   }catch (NoLocaleValueException e){
//		    throw e;
//	   }catch (Exception e) {
//			log.error("ShopLogicImpl的方法delShopValue(Long shopid,Long localeid)出错!", e);
//			throw e;
//	   } 
//	   return ret;
//   }
   
   /**
    * 根据用户userid和用户使用的Locale得到该用户使用的用于设置商店的Locale集合的Lable。显示的是某个用户用于设置商店的Locale集合
    * 用于在下拉菜单中显示用户使用过那些Locale设置商店
    * @param userid
    * @param locale
    * @return Locale的Label集合
    * @throws Exception
    */
   public List qryLabelUserSetLocales(Long userid,Locale locale) throws Exception{
	   List listRtn = new ArrayList();
	   try {
		   List list = dao.selectUserSetLocales(userid);
		   Locale localeTemp = null;
		   String label = null;
		   String value = null;
		   for(int i=0;i<list.size();i++){
				I18n i18nTemp = (I18n)list.get(i);
				if(i18nTemp!=null){
					localeTemp = new Locale(i18nTemp.getLanguage(),i18nTemp.getCountry());
					label = localeTemp.getDisplayLanguage(locale)+"("+localeTemp.getDisplayCountry(locale)+")";
					value = String.valueOf(i18nTemp.getLocaleid().longValue());
					LabelValueBean labelValueBean = new LabelValueBean(label,value);
					listRtn.add(labelValueBean);
				}
		   }
	   } catch (Exception e) {
			log.error("ShopLogicImpl的方法selectLableSetLocales(Long userid,Locale locale)出错!", e);
	        throw e;
	   }
	   return listRtn;
   }
   
   /**
    * 根据商店id得到该商店已经设置语言的列表，并且以用户指定的Locale显示。
    * 显示的是某个商店的Locale设置列表
    * @param shopid
    * @param locale：要显示的语言，一般为客户使用的语言
    * @return
    */
   public List qryLabelShopSetLocales(Long shopid,Locale locale) throws Exception{
	   List listRtn = new ArrayList();
	   ShopQuery queryVO = new ShopQuery();
	   queryVO.setShopid(shopid);
	   try {
		List list = dao.selectShopSetLocales(shopid);
		   if(list!=null){
			   Shop voTemp = null;
			   Locale localeTemp = null;
			   String label = null;
			   String value = null;
		       for(int i=0;i<list.size();i++){
		    	   localeTemp = (Locale)list.get(i);
				   label = localeTemp.getDisplayLanguage(locale)+"("+localeTemp.getDisplayCountry(locale)+")";
				   value = String.valueOf(voTemp.getLocaleid().longValue());
				   LabelValueBean labelValueBean = new LabelValueBean(label,value);
				   listRtn.add(labelValueBean);
		       } 
		   }
	   } catch (Exception e) {
		  log.error("ShopLogicImpl的方法selectLabelShopLocales(Long shopid,Locale locale)出错!", e);
          throw e;
	}
	   return listRtn;
   }
   
	/**
	 * 处理商店状态的改变，暂时做了在商店状态记录表里插入一条商店状态改变的记录。
	 * 把商店基础表里的用户状态改变。以后还要加入别的判断逻辑
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean changeStatus(Shop vo,Shopstatuslog logVO) throws Exception{
		if(vo==null||vo.getShopid()==null||vo.getShopstatus()==null)
			return true;
		Short status = vo.getShopstatus();
		try {
			Shop vo2 = dao.selectByPK(vo.getShopid(),null);
			Short status2 = vo2.getShopstatus();
			if(status2!=null&&(status.shortValue()==status2.shortValue()))
				return true;
			// 插入一条状态改变的记录
			if(logVO==null)
			   logVO = new Shopstatuslog();
			logVO.setAfstatus(status);
			logVO.setBfstatus(status2);
			logVO.setStatustime(DateUtil.getInstance().getNowtime_GLNZ());
			logVO.setShopid(vo.getShopid());
			logVO.setLogtype(Shopstatuslog.LogType_Status);
			shopstatuslogDao.insert(logVO);
			// 改变shop中的用户状态
			dao.updateStatus(vo);
		} catch (Exception e) {
      	    log.error("ShopLogicImpl方法changeStatus(Shop vo,Shopstatuslog logVO)时出错误!", e);
           throw e;
		}
		return true;
	}
    
    /**
     * select single shop mini object, need add cache
     */
	@Cacheable(value="platform.shopCache", key="'ShopLogic.getShopMini~'+#shopid+'~'+#localeid")
    public ShopMini getShopMini(Long shopid,Long localeid) {
    	Shop vo = dao.selectByPK(shopid, localeid);
    	if(vo==null){
    		return null;
    	}
    	ShopMini miniVO = new ShopMini();
    	miniVO.setShopid(vo.getShopid());
    	miniVO.setShopname(vo.getShopname());
    	miniVO.setShopcode(vo.getShopcode());
    	miniVO.setIsauthen(vo.getIsauthen());
    	// add cache key into key associate map
    	CacheSynchronizer.getInstance().buildAssoc("platform.shopCache", 
    					"ShopLogic.getShopMini~"+shopid+"~"+localeid, new String[]{Shop.ObjectType+":"+shopid});
    	// get shop style
    	Shopstyleconfig stylevo = shopstyleconfigDao.selectByPK(shopid);
    	if(stylevo!=null){
    		miniVO.setBannerimg(stylevo.getBannerimg());
    	}
    	CacheSynchronizer.getInstance().buildAssoc("platform.shopCache", 
				"ShopLogic.getShopMini~"+shopid+"~"+localeid, new String[]{Shopstyleconfig.ObjectType+":"+shopid});
    	return miniVO;
    }
    
    /**
     * 判断用户是否可以创建商店
     * @param userid
     * @return
     */
    public boolean canCreateShop(Long userid){
    	if(userid==null){
    		return false;
    	}
    	boolean creatable = true;
		// 检查用户是否允许创建多个商店
		Useraccountsetting settingVO = UseraccountsettingDaoImpl.getInstance().selectByPK(userid);
		if(settingVO!=null){
			if(Useraccountsetting.Shopcreateable_NotAllow.equals(settingVO.getShopcreateable())){
				creatable = false;
			}else if(Useraccountsetting.Shopcreateable_AllowOne.equals(settingVO.getShopcreateable())){
				// 检查用户是否已经创建了shop或申请过了商店
				int shopnum = dao.selectCountByOwner(userid);
				if(shopnum>0){
					creatable = false;
				}else {
					// 如果有正在申请的shop，就不允许再申请
					int appnum = shopappDao.selectCountByOwner(userid, ShopappConstant.AppStatus_needApprove);
					if(appnum>0){
						creatable = false;
					}
				}
			}
		}else {
			creatable = false;
		}
		return creatable;
    }
    
    /**
     * 保存更新shop authen信息
     * @param extvo
     * @param isApprove, false表示是申请的时候保存，true表示是系统管理员审批
     */
    public void saveShopauthen(Shopext extvo, Long userid, boolean isApprove){
    	if(extvo==null)
    		return;
    	Shopext oldextvo = shopextDao.selectByPK(extvo.getShopid());
    	if(!isApprove){
    		extvo.setApplyuserid(userid);
   			extvo.setAuthenstatus(ShopConstant.AuthenStatus_apply);
   			// 当shop admin自己修改商店认证信息时，shop中的认证状态并没有改变，如果shop已经认证通过了，则shop admin
   			// 修改认证信息是不会更改shop对象中的认证状态。但是在系统认证申请列表中能看到用户的修改，测试经过核实后可以选择认证通过或不通过
    	}else {
    		extvo.setApproveuserid(userid);
    	}
    	extvo.setAuthendate(DateUtil.getInstance().getNowtime_GLNZ());
    	
    	// save file
    	try {
			if(extvo.getAuthenimagefile()!=null && extvo.getAuthenimagefile().getFileSize()>0){
				String filename_main = "authenpic"+extvo.getShopid();
				FormFile file = extvo.getAuthenimagefile();
				String fileDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, extvo.getShopid(),  
																 null, null, ShopConstant.FolderName_Profile, null);
				UploadFileUtilPlatform.delFileByFileName(filename_main, fileDir);
				String fileUrl = UploadFileUtilPlatform.saveFile(filename_main, 
						file.getFileName(), file.getInputStream(), 
						file.getFileSize(), fileDir, SysparamConstantPlatform.ModuleName_SingleFileSize_ShopProfile);
				extvo.setAuthenimage(fileUrl);
			}
		} catch (IOException e) {
			log.error("ShopLogicImpl.saveShopauthen(Shopext extvo) error!", e);
			throw new LogicException(e);
		}
		Shop shopvo = dao.selectByPK(extvo.getShopid(), null);
		
		// set charge date of shop
		if(isApprove){
			if(ShopConstant.ChargeType_paid.equals(extvo.getApplychargetype())){
				Date chargeDate = DateUtil.dateAddDays(DateUtil.getInstance().getNowtime_GLNZ(), SysparamConstantPlatform.tryUseDayBeforeCharge_num);
				extvo.setChargedate(chargeDate);
			}else if(ShopConstant.ChargeType_free.equals(extvo.getApplychargetype())
					&& ShopConstant.ChargeType_paid.equals(oldextvo.getApplychargetype())){
				// TODO need change all paid shop product, change them from Paid to free
			}
			// save shop
	    	if(ShopConstant.AuthenStatus_verified.equals(extvo.getAuthenstatus())){
	    		shopvo.setIsauthen(ShopConstant.IsAuthen_pass);
	    	}else {
	    		shopvo.setIsauthen(ShopConstant.IsAuthen_no);
	    	}
	    	shopvo.setChargetype(extvo.getApplychargetype());
	    	dao.save(shopvo);
		}
		// save shopext to db
    	shopextDao.save(extvo);
    	
		// if approve or deny, and userid is not null, send notification
		if(isApprove && !ShopConstant.AuthenStatus_apply.equals(extvo.getAuthenstatus())){
			Long touserid = extvo.getApplyuserid();
			if(touserid==null && oldextvo!=null){
				touserid = oldextvo.getApplyuserid();
			}
			// 发送用户产品状态变更的 user notification
    		Map<String, String> paraMap = new HashMap<String, String>();
    		paraMap.put("touserid", touserid.toString());
    		paraMap.put("notifytype", UsernotificationConstant.NotifyType_UserProduct.toString());
    		paraMap.put("creatorid", extvo.getApproveuserid().toString());
    		String messKey = UsernotificationConstant.Messcode_Userproduct_ShopAuthen_Pass;
    		if(ShopConstant.AuthenStatus_deny.equals(extvo.getAuthenstatus())){
    			messKey = UsernotificationConstant.Messcode_Userproduct_ShopAuthen_Deny;
    		}
    		//paraMap.put("content", contentKey);
    		paraMap.put("messcode", messKey);
    		paraMap.put("openlinktype", UsernotificationConstant.OpenlinkType_NewDiv.toString());
    		paraMap.put("objectname", shopvo.getShopname());
    		Event event = new Event(EventHandlerPlatform.EventType_Usernotification_UserMessage, paraMap);
    		EventHandlerPlatform.getInstance().publishEvent(event, EventHandlerPlatform.HandleType_Asyschronized_Thread);
		}
    }
    
    // save shopstyle including its banner logo image
    public void saveShopstyle(Shopstyleconfig vo, FormFile logofile){
    	AssertUtil.assertNotNull(vo.getShopid(), null);
    	if(logofile!=null && logofile.getFileSize()>0){
    		Shopstyleconfig dbvo = shopstyleconfigDao.selectByPK(vo.getShopid());
    		// shop/1/shopstyleconfig/
    		String filepath = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, vo.getShopid(),
						  null, null, Shopstyleconfig.ObjectType, null);
    		String filemainName = "shopbannerlogo";
    		// first delete file if exists
    		if(dbvo!=null && dbvo.getBannerimg()!=null && dbvo.getBannerimg().trim().length()>0){
    			UploadFileUtilPlatform.delFileByFileName(filemainName, filepath);
    		}
    		// then save file
    		String fileurl = UploadFileUtilPlatform.saveFileFromStruts(filemainName,logofile,filepath,SysparamConstantPlatform.ModuleName_SingleFileSize_ShopProfile);
    		vo.setBannerimg(fileurl);
		}
    	shopstyleconfigDao.save(vo);
    	
    }
    
    /**
     * 本商店是否支持上传文件到local server, 目前只有系统商店才可以
     * @param shopid
     * @return
     */
    public boolean ableUploadFileToLocalServer(Long shopid){
    	if(shopid!=null){
    		Shop vo = dao.selectByPK(shopid, null);
        	if(ShopConstant.OwnerType_systemShop.equals(vo.getOwnertype())){
        		return true;
        	}else {
        		return false;
        	}
    	}else {
    	    return false;
    	}
    }
    
    /**
     * 本商店是否支持上传文件到file server, 目前只有付费商店和系统商店才可以
     * @param shopid
     * @return
     */
    public boolean ableUploadFileToFileServer(Long shopid){
    	if(shopid!=null){
    		Shop vo = dao.selectByPK(shopid, null);
        	if(ShopConstant.ChargeType_paid.equals(vo.getChargetype())
        		|| ShopConstant.OwnerType_systemShop.equals(vo.getChargetype())){
        		return true;
        	}else {
        		return false;
        	}
    	}else {
    		return false;
    	}
    }
    
	/**
	 * 给dwr调用，是否可以在producateCategory上创建课程
	 * @param cateid
	 * @return
	 */
	public boolean checkAddProdInCate(Long cateid){
		if(cateid==null){
			return false;
		}
		Productcategory vo = BOFactory_Platform.getProductcategoryDao().selectByPK(cateid);
		if(vo!=null) {
		    return ProductcategoryConstant.allowAddProductInCategory(vo.getCategorylevel());
		}else {
			return false;
		}
	}
    
    /**
     * static spring getMethod
     */
     public static ShopLogic getInstance() {
       	 ShopLogic logic = (ShopLogic)BeanFactory.getBeanFactory().getBean("shopLogic");
         return logic;
     }

 	public ShopvalueDao getValueDao() {
		return valueDao;
	}

	public void setDao(ShopDao dao) {
		this.dao = dao;
	}

	public void setValueDao(ShopvalueDao valueDao) {
		this.valueDao = valueDao;
	}

	public ShopDao getDao() {
		return dao;
	}

	public UsershopLogic getUsershopLogic() {
		return usershopLogic;
	}

	public void setUsershopLogic(UsershopLogic usershopLogic) {
		this.usershopLogic = usershopLogic;
	}

	public ShopstatuslogDao getShopstatuslogDao() {
		return shopstatuslogDao;
	}

	public void setShopstatuslogDao(ShopstatuslogDao shopstatuslogDao) {
		this.shopstatuslogDao = shopstatuslogDao;
	}

	public ShopappDao getShopappDao() {
		return shopappDao;
	}

	public void setShopappDao(ShopappDao shopappDao) {
		this.shopappDao = shopappDao;
	}

	public UsershopDao getUsershopDao() {
		return usershopDao;
	}

	public void setUsershopDao(UsershopDao usershopDao) {
		this.usershopDao = usershopDao;
	}

	public ShopcontactinfoDao getShopcontactinfoDao() {
		return shopcontactinfoDao;
	}

	public void setShopcontactinfoDao(ShopcontactinfoDao shopcontactinfoDao) {
		this.shopcontactinfoDao = shopcontactinfoDao;
	}

	public ShopextDao getShopextDao() {
		return shopextDao;
	}

	public void setShopextDao(ShopextDao shopextDao) {
		this.shopextDao = shopextDao;
	}

	public ShopstyleconfigDao getShopstyleconfigDao() {
		return shopstyleconfigDao;
	}

	public void setShopstyleconfigDao(ShopstyleconfigDao shopstyleconfigDao) {
		this.shopstyleconfigDao = shopstyleconfigDao;
	}
	
}
