package platform.constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;

import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.LabelValueVO;
import commonTool.util.StringUtil;
import platform.util.ResourceBundleUtil;

public class CustomerConstant {

	/** 默认的系统自动生成的登录帐号的前缀分隔符 **/
	public static final String DefaultLoginName_Prefix = "#";
	
	/**  客户类型，普通学员用户 **/
	public static final Short UserType_user = 2;
	//------------------------客户状态-------------------------
	/** 客户状态，正常使用状态 **/
    public static final Short CustomerStatus_active = 1;
	
    /** 客户注册前的状态，未激活状态 **/
	public static final Short CustomerStatus_register = 0;
	/** 客户状态，无效状态 **/
	public static final Short CustomerStatus_inactive = 3;
	// 以下3个状态是为Tenant user准备的，暂时不会用到
//	/** 客户状态，正在审核 **/
//    public static final Short CustomerStatus_checking = 7;
//	/** 客户状态，审核未通过，可以再次修改信息，再次审核 **/
//    public static final Short CustomerStatus_checkdeny_modify = 10;
//	/** 客户状态，审核未通过，条件不符合，不能再次申请 **/
//    public static final Short CustomerStatus_checkdeny_cannouse = 13;
    /** 客户状态，欠费状态 **/
    public static final Short CustomerStatus_debt = 19;
    /** 客户状态，删除状态 **/
    public static final Short CustomerStatus_delete = 22;
    
    //  ----------------------- 客户阶段状态 -------------------------
    /** 客户阶段状态，已经设置全局帐号 **/
    public static final Short StageStatus_settedLoginName = 1;
    /** 客户阶段状态，未设置全局帐号，系统默认的全局帐号状态 **/
    public static final Short StageStatus_notSetLoginName = 3;
    
    /** 性别: 男 **/
    public static final Short Gender_male = 1;
    /** 性别: 女 **/
    public static final Short Gender_female = 2;
    /** 性别: 保密 **/
    public static final Short Gender_secret = -1;
    
    public static final String Gender_ConstCode = "Sysconst.Gender";
    
    
    public static final Short NewCreateUserType_SelfRegis = 1;
    public static final Short NewCreateUserType_OrgAdminAdd = 2;
    // 用社交帐号登录系统自动创建的帐号
    public static final Short NewCreateUserType_SocialLoginAdd = 3;
    
    /**
     * 用于显示用户的名字
     * @param displayname
     * @param loginame
     * @return
     */
    public static String combineDisplayname(String displayname, String loginame){
    	String rtnName = displayname;
		if(loginame!=null && !loginame.startsWith("_")){
			rtnName = rtnName+"("+loginame+")";
		}
		return rtnName;
    }
    
    /**
     * 得到用户的登录名
     * @return
     */
    public static String decombineDisplayname(String name){
    	return StringUtil.getEmbedString(name, "(", ")");
    }
    
	/**
	 * 根据客户状态数值和用户的Locale得到客户状态
	 * @param userType
	 * @param locale
	 * @return
	 */
	public static String getCustomerStatusName(Short customerStatus,Locale locale){
		if(customerStatus==null)
			return "";
		if(customerStatus.equals(CustomerConstant.CustomerStatus_register))
			return ResourceBundleUtil.getInstance().getValue("CustomerConstant.CustomerStatus.register", locale);
		else if(customerStatus.equals(CustomerConstant.CustomerStatus_inactive))
			return ResourceBundleUtil.getInstance().getValue("CustomerConstant.CustomerStatus.inactive", locale);
		else if(customerStatus.equals(CustomerConstant.CustomerStatus_active))
			return ResourceBundleUtil.getInstance().getValue("CustomerConstant.CustomerStatus.active", locale);
		else if(customerStatus.equals(CustomerConstant.CustomerStatus_debt))
			return ResourceBundleUtil.getInstance().getValue("CustomerConstant.CustomerStatus.debt", locale);
		else if(customerStatus.equals(CustomerConstant.CustomerStatus_delete))
			return ResourceBundleUtil.getInstance().getValue("CustomerConstant.CustomerStatus.delete", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户的locale得到用户状态标签的列表
	 * @param locale
	 * @return
	 */
	public static List getCustomerStatusLabels(){
		Iterator ite = I18nLogicImpl.localeMap.keySet().iterator();
		Locale locale = null;
		Long localeid = null;
		List listRtn = new ArrayList();
		while(ite.hasNext()){
			localeid = (Long)ite.next();
			locale = I18nLogicImpl.localeMap.get(localeid).getEqualLocale();
			
			String customerStatus_register_name = CustomerConstant.getCustomerStatusName(CustomerConstant.CustomerStatus_register, locale);
			String customerStatus_inactive_name = CustomerConstant.getCustomerStatusName(CustomerConstant.CustomerStatus_inactive, locale);
			String customerStatus_active_name = CustomerConstant.getCustomerStatusName(CustomerConstant.CustomerStatus_active, locale);
			String customerStatus_debt_name = CustomerConstant.getCustomerStatusName(CustomerConstant.CustomerStatus_debt, locale);
			String customerStatus_delete_name = CustomerConstant.getCustomerStatusName(CustomerConstant.CustomerStatus_delete, locale);
			
			// 带change参数的都是说明: 管理员可以选择修改用户的状态
			LabelValueVO labelregister = new LabelValueVO(customerStatus_register_name,CustomerConstant.CustomerStatus_register.toString(),localeid,null,null);
			LabelValueVO labelInactive = new LabelValueVO(customerStatus_inactive_name,CustomerConstant.CustomerStatus_inactive.toString(),localeid,"change",null);
			LabelValueVO labelActive = new LabelValueVO(customerStatus_active_name,CustomerConstant.CustomerStatus_active.toString(),localeid,"change",null);
			LabelValueVO labelDebt = new LabelValueVO(customerStatus_debt_name,CustomerConstant.CustomerStatus_debt.toString(),localeid,"change",null);
			LabelValueVO labeldelete = new LabelValueVO(customerStatus_delete_name,CustomerConstant.CustomerStatus_delete.toString(),localeid,null,null);
			
			listRtn.add(labelregister);
			listRtn.add(labelInactive);
			listRtn.add(labelActive);
			listRtn.add(labelDebt);
			listRtn.add(labeldelete);
		}
		return listRtn;
	}
	
	/**
	 * 根据用户类型数值和用户的Locale得到用户类型名称
	 * @param userType
	 * @param locale
	 * @return
	 */
	public static String getUserTypeName(Short userType,Locale locale){
		if(userType.equals(CustomerConstant.UserType_user))
			return ResourceBundleUtil.getInstance().getValue("CustomerConstant.UserType.user", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户的locale得到登录类型的列表
	 * @param locale
	 * @return
	 */
	public static List getUserTypeLabels(Locale locale){
		String userType_userName = ResourceBundleUtil.getInstance().getValue("CustomerConstant.UserType.user", locale);
		List listRtn = new ArrayList();
		LabelValueBean labelUser = new LabelValueBean(userType_userName,CustomerConstant.UserType_user.toString());
		listRtn.add(labelUser);
		return listRtn;
	}
	
	/**
	 * 根据商店代码和用户本商店的登录名，构造默认的全局登录名
	 * @param shopcode
	 * @param shoploginName
	 * @return
	 */
	public static String getDefaultLoginNameProfix(String shopcode,String shoploginName){
		if(shopcode==null||shopcode.trim().length()<1
				||shoploginName==null||shoploginName.trim().length()<1)
			return "";
		return CustomerConstant.DefaultLoginName_Prefix+shopcode+CustomerConstant.DefaultLoginName_Prefix+shoploginName;
	}
	
}
