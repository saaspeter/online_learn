package platform.constant;


public class ShopConstant {

    //////////////////////////////////////////////////////////////////////
	/**  商店所属类型：系统商店  **/
	public static final Short OwnerType_systemShop = 1;
	/**  商店所属类型：个人开设的商店  **/
	public static final Short OwnerType_personalShop = 2;
	/**  商店所属类型：行政单位开设的商店  **/
	public static final Short OwnerType_trainingShop = 3;
	/**  商店所属类型：行政单位开设的商店  **/
	public static final Short OwnerType_governmentShop = 4;
	/**  商店所属类型：企业开设的商店  **/
	public static final Short OwnerType_enterpriseShop = 5;
	/**  商店所属类型：其他  **/
	public static final Short OwnerType_others = -1;
	
	//////////////////////////////////////////////////////////////////
	/** 认证类型，0未认证 **/
	public static final Short IsAuthen_no = 0;
	/** 认证类型，1 认证通过 **/
	public static final Short IsAuthen_pass = 1;
	
	
	/** 开放类型，1开放 **/
	public static final Short openType_yes = 1;
	/** 认证类型，2不开放 **/
	public static final Short openType_no = 2;
	
	/** 商店收费类型，1免费 means: don't charge to the shop, it also only allow to open free course **/
	public static final Short ChargeType_free = 1;
	/** 商店收费类型，1 收费 will chage from the shop, it allow to open charged course **/
	public static final Short ChargeType_paid = 2;
	
	
	/** 商店认证状态，1 申请中 **/
	public static final Short AuthenStatus_apply = 1;
	/** 商店认证状态，2 verified  **/
	public static final Short AuthenStatus_verified = 2;
	/** 商店认证状态，3: not pass verification, should delete next time **/
	public static final Short AuthenStatus_deny = 3;
	
    //////////////////////////////////////////////////////////////////
	/**  商店状态，未激活状态，表明租户刚刚申请开店，还未激活  **/
	//public static final Short AppStatus_inactive = 1;//暂时没有用到，可能需要邮件激活
	
	/** 商店状态，配置状态，表明商店还在配置状态，不能正常使用 **/
	public static final Short ShopStatus_inConfig = -2;
	/** 商店状态，运营状态，表明商店在正常地运营 **/
	public static final Short ShopStatus_run = 7;
	/** 商店状态，暂停状态，表明商店在暂停状态 **/
	public static final Short ShopStatus_suspend = 10;
	/** 商店状态，欠费状态，表明商店在欠费状态 **/
	public static final Short ShopStatus_debt = 13;
	/** 商店状态，封存状态，表明商店被封存状态 **/
	public static final Short ShopStatus_seal = 16;

	/** 存放shop本身的图片，例如认证用的图片 **/
	public static final String FolderName_Profile = "profile";
	
	/** shop的默认图标 **/
	public static final String ShopLogo_Default = "/styles/imgs/logo_platform.jpg";
	
//	/**
//	 * 根据商店开放数值和用户的Locale得到商店开放类型名称
//	 * @param userType
//	 * @param locale
//	 * @return
//	 */
//	public static String getOpenTypeName(Short openType,Locale locale){
//		if(openType.equals(ShopConstant.IsOpen))
//			return ResourceBundleUtil.getValue("ShopConstant.OpenType.IsOpen", locale);
//		else if(openType.equals(ShopConstant.IsNotOpen))
//			return ResourceBundleUtil.getValue("ShopConstant.OpenType.IsNotOpen", locale);
//		else 
//			return "";
//	}
//	
//	/**
//	 * 根据商店试用类型数值和用户的Locale得到商店试用类型名称
//	 * @param userType
//	 * @param locale
//	 * @return
//	 */
//	public static String getTryTypeName(Short tryType,Locale locale){
//		if(tryType.equals(ShopConstant.IsTry))
//			return ResourceBundleUtil.getValue("ShopConstant.TryType.IsTry", locale);
//		else if(tryType.equals(ShopConstant.IsNotTry))
//			return ResourceBundleUtil.getValue("ShopConstant.TryType.IsNotTry", locale);
//		else 
//			return "";
//	}
//	
//	/**
//	 * 根据商店状态数值和用户的Locale得到商店状态名称
//	 * @param userType
//	 * @param locale
//	 * @return
//	 */
//	public static String getShopStatusName(Short shopStatus,Locale locale){
//		//if(shopStatus.equals(ShopConstant.ShopStatus_apply))
//		//	return ResourceBundleUtil.getValue("ShopConstant.ShopStatus.apply", locale);
//		if(shopStatus.equals(ShopConstant.ShopStatus_approve))
//			return ResourceBundleUtil.getValue("ShopConstant.ShopStatus.approve", locale);
//		else if(shopStatus.equals(ShopConstant.ShopStatus_run))
//			return ResourceBundleUtil.getValue("ShopConstant.ShopStatus.run", locale);
//		else if(shopStatus.equals(ShopConstant.ShopStatus_suspend))
//			return ResourceBundleUtil.getValue("ShopConstant.ShopStatus.suspend", locale);
//		else if(shopStatus.equals(ShopConstant.ShopStatus_debt))
//			return ResourceBundleUtil.getValue("ShopConstant.ShopStatus.debt", locale);
//		else if(shopStatus.equals(ShopConstant.ShopStatus_seal))
//			return ResourceBundleUtil.getValue("ShopConstant.ShopStatus.seal", locale);
//		else 
//			return "";
//	}
//	
//	/**
//	 * 根据用户的locale得到商店开放类型的列表
//	 * @param locale
//	 * @return
//	 */
//	public static List getOpenTypeLabels(Locale locale){
//		String openType_isOpenName = ResourceBundleUtil.getValue("ShopConstant.OpenType.IsOpen", locale);
//		String openType_isNotOpenName = ResourceBundleUtil.getValue("ShopConstant.OpenType.IsNotOpen", locale);
//		List listRtn = new ArrayList();
//		LabelValueBean labelIsOpen = new LabelValueBean(openType_isOpenName,ShopConstant.IsOpen.toString());
//		LabelValueBean labelIsNotOpen = new LabelValueBean(openType_isNotOpenName,ShopConstant.IsNotOpen.toString());
//		listRtn.add(labelIsOpen);
//		listRtn.add(labelIsNotOpen);
//		return listRtn;
//	}
//	
//	/**
//	 * 根据用户的locale得到商店试用类型的列表
//	 * @param locale
//	 * @return
//	 */
//	public static List getTryTypeLabels(Locale locale){
//		String tryType_isTry = ResourceBundleUtil.getValue("ShopConstant.TryType.IsTry", locale);
//		String tryType_isNotTry = ResourceBundleUtil.getValue("ShopConstant.TryType.IsNotTry", locale);
//		List listRtn = new ArrayList();
//		LabelValueBean labelIsTry = new LabelValueBean(tryType_isTry,ShopConstant.IsTry.toString());
//		LabelValueBean labelIsNotTry = new LabelValueBean(tryType_isNotTry,ShopConstant.IsNotTry.toString());
//		listRtn.add(labelIsTry);
//		listRtn.add(labelIsNotTry);
//		return listRtn;
//	}
//	
//	/**
//	 * 根据用户的locale得到商店开设类型的列表
//	 * 系统开设；普通客户开设
//	 * @param locale
//	 * @return
//	 */
//	public static List getShopTypeLabels(Locale locale){
//		String shopType_system = ResourceBundleUtil.getValue("ShopConstant.ShopType.system", locale);
//		String shopType_personal = ResourceBundleUtil.getValue("ShopConstant.ShopType.personal", locale);
//		String ShopType_enterprise = ResourceBundleUtil.getValue("ShopConstant.ShopType.enterprise", locale);
//		List listRtn = new ArrayList(); 
//		LabelValueBean labelSystem = new LabelValueBean(shopType_system,ShopConstant.ShopType_system.toString());
//		LabelValueBean labelPerson = new LabelValueBean(shopType_personal,ShopConstant.ShopType_personal.toString());
//		LabelValueBean labelEnterprise = new LabelValueBean(ShopType_enterprise,ShopConstant.ShopType_enterprise.toString());
//		listRtn.add(labelPerson);
//		listRtn.add(labelEnterprise);
//		listRtn.add(labelSystem);
//		return listRtn;
//	}
//	
//	/**
//	 * 根据用户的locale得到商店开放类型的列表
//	 * @param locale
//	 * @return
//	 */
//	public static List getShopStatusLabels(Locale locale){
//		//String apply_name = ShopConstant.getShopStatusName(ShopConstant.ShopStatus_apply, locale);
//		String approve_name = ShopConstant.getShopStatusName(ShopConstant.ShopStatus_approve, locale);
//		String run_name = ShopConstant.getShopStatusName(ShopConstant.ShopStatus_run, locale);
//		String suspend_name = ShopConstant.getShopStatusName(ShopConstant.ShopStatus_suspend, locale);
//		String debt_name = ShopConstant.getShopStatusName(ShopConstant.ShopStatus_debt, locale);
//		String seal_name = ShopConstant.getShopStatusName(ShopConstant.ShopStatus_seal, locale);
//		
//		List listRtn = new ArrayList();
//		//LabelValueBean labelApply = new LabelValueBean(apply_name,ShopConstant.ShopStatus_apply.toString());
//		LabelValueBean labelapprove = new LabelValueBean(approve_name,ShopConstant.ShopStatus_approve.toString());
//		LabelValueBean labelrun = new LabelValueBean(run_name,ShopConstant.ShopStatus_run.toString());
//		LabelValueBean labelsuspend = new LabelValueBean(suspend_name,ShopConstant.ShopStatus_suspend.toString());
//		LabelValueBean labeldebt = new LabelValueBean(debt_name,ShopConstant.ShopStatus_debt.toString());
//		LabelValueBean labelseal = new LabelValueBean(seal_name,ShopConstant.ShopStatus_seal.toString());
//		
//		//listRtn.add(labelApply);
//		listRtn.add(labelapprove);
//		listRtn.add(labelrun);
//		listRtn.add(labelsuspend);
//		listRtn.add(labeldebt);
//		listRtn.add(labelseal);
//		return listRtn;
//	}
	
}
