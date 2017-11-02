package commonWeb.security.constant;

public class UserRolesConstant {

	/** 使用类型：1为可使用可分配；2为可使用；3可分配 **/
	public static final Short UseType_useAssign = 1;
	
	public static final Short UseType_use = 2;
	
	public static final Short UseType_assign = 3;
	
	/** 容器类型，可以是商店1，单位2 **/
	public static final Short ContainerType_shop = 1;
	public static final Short ContainerType_dept = 2;
	
	/** 1:只管理本级。2:可以管理下级 **/
	public static final Short ManageScope_self = 1;
	public static final Short ManageScope_selfBelow = 2;
	
	/** 系统级的角色的用户记录，此role是用户进入系统就具有的,因此shopID为-1 **/
	public static final Long NonShopID = new Long("-1");
	
}
