package netTest.product.constant;


public class UserproductConstant {
    
	/** 用户使用产品的生成方式，1代表通过订单生成； **/
	public static final Short GeneType_fromOrder = new Short("1");
	/** 用户使用产品的生成方式，2代表由子系统（教育系统）内部生成的数据 **/
	public static final Short GeneType_fromSubSystem = new Short("2");
	
    //todo 常量的读取文字信息未写
	/////////////////////////////是否已付费ispay//////////////////////////////
	/**  是否已付费，已经付费  **/
	public static final Short IsPay_yes = new Short("1");
	/**  是否已付费，没有付费  **/
	public static final Short IsPay_no = new Short("2");
	/**  是否已付费，免费使用，不用付费  **/
	//public static final Short IsPay_free = new Short("3");
	
	////////////////////////////产品使用状态status////////////////////////////
	/**  用户使用产品状态，使用状态 **/
	public static final Short Status_active = new Short("1");
	/**  用户使用产品状态，暂停使用状态 **/
	public static final Short Status_inactive = new Short("2");
	
	////////////////////////////产品的使用状态级别statusrank///////////////////
	/**  用户使用状态级别，正常使用状态  **/
	public static final Short StatusRank_Use_normal = new Short("1");
	/**  用户使用状态级别，免费使用  **/
	public static final Short StatusRank_Use_free = new Short("2");
	/**  用户使用状态级别，使用服务正常结束状态（对于有下载次数限制的文档，即是次数已满状态；对于固定长时间的培训商品则是使用期限已到）  **/
	public static final Short StatusRank_Suspend_serviceEnd = new Short("6");
	
    /////////////////////////产品使用类型prodUseType/////////////////////////
	/**  产品使用类型，使用课程学习  **/
	public static final Short ProdUseType_userNormal = new Short("2");
	/**  产品使用类型，管理课程  **/
	public static final Short ProdUseType_operatorMag = new Short("3");
	
	
	public static final Short Status_NotExistStatus = new Short("-1");
	
}
