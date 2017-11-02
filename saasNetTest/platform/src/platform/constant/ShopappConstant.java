package platform.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;

import platform.util.ResourceBundleUtil;

public class ShopappConstant {

	/**  审核状态，申请状态，表明租户正在申请开店，等待批准  **/
	public static final Short AppStatus_needApprove = 4;
    /**  审核状态，审核不通过，并且需要删除该商店  **/
	public static final Short AppStatus_deny_del = 7;
	/**  审核状态，审核通过  **/
	public static final Short AppStatus_pass = 10;
	
	
	/**
	 * 根据审核状态数值和用户的Locale得到审核状态名称
	 * @param appStatus
	 * @param locale
	 * @return
	 */
	public static String getAppStatusName(Short appStatus,Locale locale){
		if(appStatus.equals(ShopappConstant.AppStatus_needApprove))
			return ResourceBundleUtil.getInstance().getValue("ShopAppConstant.AppStatus.approve", locale);
		else if(appStatus.equals(ShopappConstant.AppStatus_deny_del))
			return ResourceBundleUtil.getInstance().getValue("ShopAppConstant.AppStatus.deny.del", locale);
		else if(appStatus.equals(ShopappConstant.AppStatus_pass))
			return ResourceBundleUtil.getInstance().getValue("ShopAppConstant.AppStatus.pass", locale);
		else 
			return "";
	}
		
	/**
	 * 根据用户的locale得到审批审核类型的列表
	 * @param locale
	 * @return
	 */
	public static List getAppStatusLabels(Locale locale){
		String apply_name = ShopappConstant.getAppStatusName(ShopappConstant.AppStatus_needApprove, locale);
		String deny_del_name = ShopappConstant.getAppStatusName(ShopappConstant.AppStatus_deny_del, locale);
		String pass_name = ShopappConstant.getAppStatusName(ShopappConstant.AppStatus_pass, locale);
		
		List listRtn = new ArrayList();
		LabelValueBean label_apply = new LabelValueBean(apply_name,ShopappConstant.AppStatus_needApprove.toString());
		LabelValueBean label_deny_del = new LabelValueBean(deny_del_name,ShopappConstant.AppStatus_deny_del.toString());
		LabelValueBean label_pass = new LabelValueBean(pass_name,ShopappConstant.AppStatus_pass.toString());
		
		listRtn.add(label_apply);
		listRtn.add(label_deny_del);
		listRtn.add(label_pass);
		return listRtn;
	}
	
}
