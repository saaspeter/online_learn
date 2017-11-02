package platform.constant;



public class UsershopConstant {

//////////////////////////////////////////////////////////////////////////////////
	/**
	 * 该人员在商店中类型，1代表商店本身人员,这些人员都在单位org中; 
	 * 2代表人员不是商店自身人员,是购买了商店课程的人员,不在在org中
	 */
	public static final Short Usershoptype_Orguser = 1;
	
	public static final Short Usershoptype_Productuser = 2;
	
//////////////////////////////////////////////////////////////////////////////////	
	/**  用户加入的方式，1代表是申请加入的，2代表是商店管理员加入的  **/
	public static final Short JoinWay_apply = new Short("1");
	public static final Short JoinWay_shopadd = new Short("2");
	
///////////////////////////////////////////////////////////////////////////////////
	/**
	 * 标明该人员和产品的关系，
	 * 1：代表该人员应用于所有的产品上，当有新产品建立时，该小组会自动应用到该产品上，
	 * 2：代表该人员仅应用于固定的某些产品上， 
	 * 即用户产品表中会自动添加一条记录。
	 */
	public static final Short AreaInProduct_all = new Short("1");
	public static final Short AreaInProduct_some = new Short("2");
	
///////////////////////////////////////////////////////////////////////////////////
	/**
	 * 标明该人员和系统的关系，
	 * 1：代表应用于该商店拥有的所有系统上，即商店的所有系统都仅有这一条记录。
	 * 2：代表该人员仅应用于本条记录上的系统，
	 */
//	public static final Short AreaInSys_all = new Short("1");
//	public static final Short AreaInSys_single = new Short("2");
	
////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 用户在商店中的状态，1:申请状态；4:正常使用状态；7:即将欠费状态；10:欠费状态；
	 * 13:在商店中不可用状态；
	 */
//	public static final Short UserShopStatus_apply = new Short("1");
	public static final Short UserShopStatus_active = new Short("4");
//	public static final Short UserShopStatus_dueTo_debt = new Short("7");
//	public static final Short UserShopStatus_debt = new Short("10");
	public static final Short UserShopStatus_inactive = new Short("13");
		
}
