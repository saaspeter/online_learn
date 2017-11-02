package platform.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;

import platform.util.ResourceBundleUtil;

public class ShopfuncConstant {
    /** 该功能是否已经支付，已经支付 **/
	public static final Short IsPay_yes = 1;
	/** 该功能是否已经支付，没有支付 **/
	public static final Short IsPay_no = 2;
	
    /** 该商店功能的状态，有效 **/
	public static final Short Status_valide = 1;
	/** 该商店功能的状态，无效 **/
	public static final Short Status_invalide = 2;
	
	/**
	 * 根据是否支付和用户的Locale得到是否支付名称
	 * @param isPay
	 * @param locale
	 * @return
	 */
	public static String getIsPayName(Short isPay,Locale locale){
		if(isPay.equals(ShopfuncConstant.IsPay_yes))
			return ResourceBundleUtil.getInstance().getValue("ShopfuncConstant.IsPay.yes", locale);
		else if(isPay.equals(ShopfuncConstant.IsPay_no))
			return ResourceBundleUtil.getInstance().getValue("ShopfuncConstant.IsPay.no", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户的locale得到是否支付的列表
	 * @param locale
	 * @return
	 */
	public static List getIsPayLabels(Locale locale){
		String IsPay_yesName = ShopfuncConstant.getIsPayName(IsPay_yes, locale);
		String IsPay_noName = ShopfuncConstant.getIsPayName(IsPay_no, locale);
		List listRtn = new ArrayList();
		LabelValueBean labelIsPay = new LabelValueBean(IsPay_yesName,ShopfuncConstant.IsPay_yes.toString());
		LabelValueBean labelIsNotPay = new LabelValueBean(IsPay_noName,ShopfuncConstant.IsPay_no.toString());
		listRtn.add(labelIsPay);
		listRtn.add(labelIsNotPay);
		return listRtn;
	}
	
	/**
	 * 根据商店功能的状态和用户的Locale得到商店功能名称
	 * @param status
	 * @param locale
	 * @return
	 */
	public static String getStatusName(Short status,Locale locale){
		if(status.equals(ShopfuncConstant.Status_valide))
			return ResourceBundleUtil.getInstance().getValue("ShopfuncConstant.Status.valide", locale);
		else if(status.equals(ShopfuncConstant.Status_invalide))
			return ResourceBundleUtil.getInstance().getValue("ShopfuncConstant.Status.invalide", locale);
		else 
			return "";
	}
	
	/**
	 * 根据用户的locale得到功能状态的列表
	 * @param locale
	 * @return
	 */
	public static List getStatusLabels(Locale locale){
		String status_valideName = ShopfuncConstant.getStatusName(Status_valide, locale);
		String status_invalideName = ShopfuncConstant.getStatusName(Status_invalide, locale);
		List listRtn = new ArrayList();
		LabelValueBean labelvalide = new LabelValueBean(status_valideName,ShopfuncConstant.Status_valide.toString());
		LabelValueBean labelinvalide = new LabelValueBean(status_invalideName,ShopfuncConstant.Status_invalide.toString());
		listRtn.add(labelvalide);
		listRtn.add(labelinvalide);
		return listRtn;
	}
	
}
