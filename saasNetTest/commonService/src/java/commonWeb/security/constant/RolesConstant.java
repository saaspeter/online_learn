package commonWeb.security.constant;

import org.springframework.security.GrantedAuthority;

import platform.exception.ExceptionConstant;

import commonTool.exception.LogicException;

public class RolesConstant {

	/** 是否是默认的系统角色，0是默认角色，1为用户自己创建的角色 **/
	public static final Short Isdefaultset_default = 0;
	
	public static final Short Isdefaultset_notdefault = 1;
	
	/** 角色级别，1为系统级，用户登录系统的时候就会有的 **/
	public static final Short RoleLevel_System = 1;
	/** 角色级别，2为商店级，用户进入商店后才会有的 **/
	public static final Short RoleLevel_ShopLevel = 2;
	
	
	public static final String ROLE_SysAdmin = "ROLE_SysAdmin";
	public static final String ROLE_BizDataAdmin = "ROLE_BizDataAdmin";
	
	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	
	public static final String ROLE_RegisterMember = "ROLE_RegisterMember";
	/** 商店创建者 **/
	public static final String ROLE_ShopCreator = "ROLE_ShopCreator";
	/** 商店管理员 **/
	public static final String ROLE_ShopAdmin = "ROLE_ShopAdmin";
	/** 商店用户管理员 **/
	public static final String ROLE_ShopUserAdmin = "ROLE_ShopUserAdmin";
	/** 商店产品管理员，当用户可以管理一个产品时就会有这个role,当没有可以管理的产品后会删除该用户的这个role **/
	public static final String ROLE_ShopProductAdmin = "ROLE_ShopProductAdmin";
	/** 商店成员 **/
	public static final String ROLE_ShopMember = "ROLE_ShopMember";
	
	/**
	 * 得到商店中可以手动分配给用户的角色
	 * @return
	 */
	public static String getShopRoleForSelect(){
		return "'"+ROLE_ShopAdmin+"','"+ROLE_ShopUserAdmin+"'";
	}
	
	/**
	 * 得到系统后台中可以手动分配给用户的角色
	 * @return
	 */
	public static String getSysRoleForSelect(){
		return "'"+ROLE_SysAdmin+"','"+ROLE_BizDataAdmin+"'";
	}
	
	/**
	 * 检查用户是否具有操作这些角色的权限
	 * @param rolecode
	 * @param authorities
	 * @return
	 */
	public static boolean hasPrivilegeToAddRole(String rolecode, GrantedAuthority[] authorities){
		if(rolecode==null || ROLE_ANONYMOUS.equals(rolecode) || 
			ROLE_RegisterMember.equals(rolecode)){
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		if(authorities==null || authorities.length<1){
			return false;
		}
		boolean result = false;
		if(ROLE_ShopCreator.equals(rolecode) || ROLE_ShopAdmin.equals(rolecode)
			|| ROLE_ShopUserAdmin.equals(rolecode) || ROLE_ShopProductAdmin.equals(rolecode)
			|| ROLE_ShopMember.equals(rolecode)){
			for(GrantedAuthority authority : authorities){
				if(authority!=null && (ROLE_ShopCreator.equals(authority.getAuthority())
				   ||ROLE_ShopAdmin.equals(authority.getAuthority()))){
					result = true;
					break;
				}
			}
		}
		if(ROLE_SysAdmin.equals(rolecode) || ROLE_BizDataAdmin.equals(rolecode)){
			for(GrantedAuthority authority : authorities){
				if(authority!=null && (ROLE_SysAdmin.equals(authority.getAuthority()))){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public static boolean hasRole(String rolecode, GrantedAuthority[] authorities){
		if(rolecode==null || authorities==null || authorities.length<1){
			return false;
		}
		boolean result = false;
		for(GrantedAuthority authority : authorities){
			if(authority!=null && (rolecode.equals(authority.getAuthority()))){
				result = true;
				break;
			}
		}
		return result;
	}
	
}
