package commonWeb.security.dto;

import commonWeb.security.vo.Menus;

public class MenusQuery extends Menus{

   private Long roleId;
   
   private Long userId;
      
   private Long shopid; // 查询用户角色对应的菜单时使用
   
	/** 查询类型。1为本菜单的权限，2为菜单下所有权限 **/
	private Integer searchListType;
	
   private String order_By_Clause;
   
   //private static final String anonyRoleName = RolesConstant.ROLE_ANONYMOUS;

   public MenusQuery() {
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

//	public static String getAnonyRoleName() {
//		return anonyRoleName;
//	}

	public Integer getSearchListType() {
		return searchListType;
	}

	public void setSearchListType(Integer searchListType) {
		this.searchListType = searchListType;
	}
}
