package platform.dto;

import platform.vo.User;

public class UserQuery extends User{

	private Long shopid;
	
    /** 为了查询角色下有多少用户而设定的角色id **/
    private Long roleId;
    
    private String syscode;
	
    private String order_By_Clause;

    public UserQuery() {
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

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	
}
