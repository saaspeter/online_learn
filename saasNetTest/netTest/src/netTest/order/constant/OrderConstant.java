package netTest.order.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import netTest.util.ResourceBundleUtil;

import org.apache.struts.util.LabelValueBean;

public class OrderConstant {
	////////////////////// 订单物品类型 /////////////////
	/**  订单类型，信息产品，电子交易即可  **/
	public static final Short OrderType_infoProd = 1;
	/**  订单类型，实体商品，需要配送  **/
	public static final Short OrderType_realProd = 2;
	
	////////////////////// 客户订单状态,主要是决定哪些订单人需要处理，哪些是历史订单。 /////////////////
	/**  客户订单状态: 提交状态，未作处理  **/
	public static final Short OrderStatus_submit = 1;
	/**  客户订单状态: 订单审批通过  **/
	public static final Short OrderStatus_approve = 7;
	/**  客户订单状态: 订单审批否决  **/
	public static final Short OrderStatus_deny = 10;
	
	/**  客户订单状态: 订单处理完毕  **/
	//public static final Short OrderStatus_finish = 13;
	/**  客户订单状态: 订单处理失败，一般是异常情况  **/
	public static final Short OrderStatus_fail = -2;

	
    ////////////////////// 订单审核状态 /////////////////
	/**  审核状态，表明用户申请购买的产品正等待批准  **/
//	public static final Short AppStatus_checking = 4;
	/**  审核状态，购买该产品的申请审核否决  **/
//	public static final Short AppStatus_deny = 7;
//	/**  审核状态，购买该产品的申请审核通过  **/
//	public static final Short AppStatus_pass = 10;
	
	////////////////////// 订单结算状态 /////////////////
	/**  结算状态，未结算状态  **/
	public static final Short PayStatus_notPay = 1;
	/**  结算状态，仅支付了部分订单条目  **/
	public static final Short PayStatus_partPaid = 4;
	/**  结算状态，已付定金状态  **/
	public static final Short PayStatus_prePaid = 7;
	/**  结算状态，试用产品的试用支付(花费为0，只对订单条目有用) **/
	public static final Short PayStatus_tryPaid = 10;
	/**  结算状态，结算完毕  **/
	public static final Short PayStatus_fullPaid = 13;
	
	////////////////////////// notification的信息code ///////////////////////////
	
	/**
	 * 根据审核状态数值和用户的Locale得到审核状态名称
	 * @param appStatus
	 * @param locale
	 * @return
	 */
	public static String getAppStatusName(Short appStatus,Locale locale){
		if(appStatus.equals(OrderConstant.OrderStatus_deny))
			return ResourceBundleUtil.getInstance().getValue("OrderConstant.AppStatus.deny", locale);
		else if(appStatus.equals(OrderConstant.OrderStatus_approve))
			return ResourceBundleUtil.getInstance().getValue("OrderConstant.AppStatus.pass", locale);
		else 
			return "";
	}
		
	/**
	 * 根据用户的locale得到审批审核类型的列表，用于查询审批结果
	 * @param locale
	 * @return
	 */
	public static List getAppStatusLabels(Locale locale){
		String deny_name = OrderConstant.getAppStatusName(OrderConstant.OrderStatus_deny, locale);
		String pass_name = OrderConstant.getAppStatusName(OrderConstant.OrderStatus_approve, locale);
		
		List listRtn = new ArrayList();
		LabelValueBean label_deny = new LabelValueBean(deny_name,OrderConstant.OrderStatus_deny.toString());
		LabelValueBean label_pass = new LabelValueBean(pass_name,OrderConstant.OrderStatus_approve.toString());
		
		listRtn.add(label_pass);
		listRtn.add(label_deny);
		
		return listRtn;
	}
	
	
	/**
	 * 根据结算状态数值和用户的Locale得到结算状态名称
	 * @param appStatus
	 * @param locale
	 * @return
	 */
//	public static String getPayStatusName(Short payStatus,Locale locale){
//		if(payStatus.equals(OrderConstant.PayStatus_notPay))
//			return ResourceBundleUtil.getValue("OrderConstant.PayStatus.notPay", locale);
//		else if(payStatus.equals(OrderConstant.PayStatus_partPaid))
//			return ResourceBundleUtil.getValue("OrderConstant.PayStatus.partPaid", locale);
//		else if(payStatus.equals(OrderConstant.PayStatus_prePaid))
//			return ResourceBundleUtil.getValue("OrderConstant.PayStatus.prePaid", locale);
//		else if(payStatus.equals(OrderConstant.PayStatus_tryPaid))
//			return ResourceBundleUtil.getValue("OrderConstant.PayStatus.tryPaid", locale);
//		else if(payStatus.equals(OrderConstant.PayStatus_fullPaid))
//			return ResourceBundleUtil.getValue("OrderConstant.PayStatus.fullPaid", locale);
//		else 
//			return "";
//	}
		
	/**
	 * 根据用户的locale得到结算类型的列表
	 * @param locale
	 * @return
	 */
//	public static List getPayStatusLabels(Locale locale){
//		String notPay_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_notPay, locale);
//		String partPaid_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_partPaid, locale);
//		String prePaid_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_prePaid, locale);
//		String tryPaid_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_tryPaid, locale);
//		String fullPaid_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_fullPaid, locale);
//		
//		List listRtn = new ArrayList();
//		LabelValueBean label_notPay = new LabelValueBean(notPay_name,OrderConstant.PayStatus_notPay.toString());
//		LabelValueBean label_partPaid = new LabelValueBean(partPaid_name,OrderConstant.PayStatus_partPaid.toString());
//		LabelValueBean label_prePaid = new LabelValueBean(prePaid_name,OrderConstant.PayStatus_prePaid.toString());
//		LabelValueBean label_tryPaid = new LabelValueBean(tryPaid_name,OrderConstant.PayStatus_tryPaid.toString());
//		LabelValueBean label_fullPaid = new LabelValueBean(fullPaid_name,OrderConstant.PayStatus_fullPaid.toString());
//		
//		listRtn.add(label_notPay);
//		listRtn.add(label_partPaid);
//		listRtn.add(label_prePaid);
//		listRtn.add(label_tryPaid);
//		listRtn.add(label_fullPaid);
//		return listRtn;
//	}
	
	/**
	 * 根据用户的locale得到可以试用类产品的结算类型列表
	 * @param locale
	 * @return
	 */
//	public static List getTryPayStatusLabels(Locale locale){
//		String tryPaid_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_tryPaid, locale);
//		String fullPaid_name = OrderConstant.getPayStatusName(OrderConstant.PayStatus_fullPaid, locale);
//		
//		List listRtn = new ArrayList();
//		LabelValueBean label_tryPaid = new LabelValueBean(tryPaid_name,OrderConstant.PayStatus_tryPaid.toString());
//		LabelValueBean label_fullPaid = new LabelValueBean(fullPaid_name,OrderConstant.PayStatus_fullPaid.toString());
//		
//		listRtn.add(label_tryPaid);
//		listRtn.add(label_fullPaid);
//		return listRtn;
//	}
	
}
