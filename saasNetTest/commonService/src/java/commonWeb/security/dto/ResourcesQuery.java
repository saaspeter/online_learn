package commonWeb.security.dto;

import commonWeb.security.vo.Resources;

public class ResourcesQuery extends Resources{

   private Long roleId;
   
   private Long userId;
      
   private Long shopid; // 查询用户角色对应的菜单时使用
   	
   private String order_By_Clause;
   /** 角色字符串，以英文逗号隔开 **/
   private String roleStr;
   
   //private static final String anonyRoleName = RolesConstant.ROLE_ANONYMOUS;

   public ResourcesQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getRoleStr() {
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

//	public static String getAnonyRoleName() {
//		return anonyRoleName;
//	}

}
