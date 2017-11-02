package netTest.common.constant;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;


public class RolesConstant {
	
	/** 对产品有权限获取的角色,动态生成的角色 **/
	public static final String ROLE_Product_Accessor = "ROLE_ProductAccessor";
	/** 对产品有权限管理的角色,动态生成的角色. 参见： commonWeb.security.constant。RolesConstant.ROLE_ShopProductAdmin **/
	public static final String ROLE_ProductAdmin_Accessor = "ROLE_ProductAdminAccessor";
	
	/** 对Testinfo有权限获取的角色,动态生成的角色 **/
	public static final String ROLE_TestInfo_Accessor = "ROLE_TestInfoAccessor";
	/** 对Testinfo有权限管理的角色,动态生成的角色 **/
	public static final String ROLE_TestInfoAdmin_Accessor = "ROLE_TestInfoAdminAccessor";
	
	/** 对学习资料的access权限,动态生成的角色，例如免费的learncontent, 免费的exercise **/
	public static final String ROLE_LearnContent_Accessor = "ROLE_LearnContentAccessor";
	
		
	public static final GrantedAuthority[] GrantedAuthority_TestInfoAdmin = new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_TestInfoAdmin_Accessor), new GrantedAuthorityImpl(RolesConstant.ROLE_TestInfo_Accessor)};
	public static final GrantedAuthority[] GrantedAuthority_TestInfoAccessor = new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_TestInfo_Accessor)};
	
	public static final GrantedAuthority[] GrantedAuthority_ProductAdmin = new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_ProductAdmin_Accessor), new GrantedAuthorityImpl(RolesConstant.ROLE_Product_Accessor)}; 
	public static final GrantedAuthority[] GrantedAuthority_ProductAccessor = new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_Product_Accessor)};
	
	public static final GrantedAuthority[] GrantedAuthority_LearnContentAccessor = new GrantedAuthorityImpl[]{new GrantedAuthorityImpl(RolesConstant.ROLE_LearnContent_Accessor)};
}
