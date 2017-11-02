package platform.constant;
// 记录客户帐号信息的常量类
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.struts.util.LabelValueBean;
import platform.util.ResourceBundleUtil;

public class AccountConstant {

	/** 帐户状态，正在审核 **/
    public static final Short Status_check = 1;
	/** 帐户状态，正常使用状态 **/
    public static final Short Status_active = 4;
    /** 帐户状态，暂停状态 **/
    public static final Short Status_suspend = 7;
    /** 帐户状态，欠费状态 **/
    public static final Short Status_debt = 10;
    /** 帐户状态，不可使用状态 **/
    public static final Short Status_sealed = 13;
    
    // 定义帐户类型
	/**  帐户类型，普通学员用户 **/
	public static final Short AccountType_user = 2;
	/**  帐户类型，商店帐户 **/
	public static final Short AccountType_shop = 3;
    
	/**
	 * 根据帐户状态数值和用户的Locale得到帐户状态
	 * @param userType
	 * @param locale
	 * @return
	 */
	public static String getStatusName(Short status,Locale locale){
		if(status.equals(AccountConstant.Status_check))
			return ResourceBundleUtil.getInstance().getValue("AccountConstant.Status.check", locale);
		else if(status.equals(AccountConstant.Status_active))
			return ResourceBundleUtil.getInstance().getValue("AccountConstant.Status.active", locale);
		else if(status.equals(AccountConstant.Status_suspend))
			return ResourceBundleUtil.getInstance().getValue("AccountConstant.Status.suspend", locale);
		else if(status.equals(AccountConstant.Status_debt))
			return ResourceBundleUtil.getInstance().getValue("AccountConstant.Status.debt", locale);
		else if(status.equals(AccountConstant.Status_sealed))
			return ResourceBundleUtil.getInstance().getValue("AccountConstant.Status.sealed", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户的locale得到帐户状态标签的列表
	 * @param locale
	 * @return
	 */
	public static List getCustomerStatusLabels(Locale locale){
		String status_check_name = AccountConstant.getStatusName(AccountConstant.Status_check, locale);
		String status_active_name = AccountConstant.getStatusName(AccountConstant.Status_active, locale);
		String status_suspend_name = AccountConstant.getStatusName(AccountConstant.Status_suspend, locale);
		String status_debt_name = AccountConstant.getStatusName(AccountConstant.Status_debt, locale);
		String status_sealed_name = AccountConstant.getStatusName(AccountConstant.Status_sealed, locale);
		
		List listRtn = new ArrayList();
		LabelValueBean labelcheck = new LabelValueBean(status_check_name,AccountConstant.Status_check.toString());
		LabelValueBean labelActive = new LabelValueBean(status_active_name,AccountConstant.Status_active.toString());
		LabelValueBean labelSuspend = new LabelValueBean(status_suspend_name,AccountConstant.Status_suspend.toString());
		LabelValueBean labelDebt = new LabelValueBean(status_debt_name,AccountConstant.Status_debt.toString());
		LabelValueBean labelSealed = new LabelValueBean(status_sealed_name,AccountConstant.Status_sealed.toString());
		
		listRtn.add(labelcheck);
		listRtn.add(labelActive);
		listRtn.add(labelSuspend);
		listRtn.add(labelDebt);
		listRtn.add(labelSealed);
		return listRtn;
	}

}
