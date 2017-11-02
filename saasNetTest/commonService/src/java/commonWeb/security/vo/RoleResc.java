package commonWeb.security.vo;

import java.io.Serializable;

public class RoleResc implements Serializable{

    private Long rescId;

    private Long roleId;
    
    private String syscode;
    

	public Long getRescId() {
		return rescId;
	}

	public void setRescId(Long rescId) {
		this.rescId = rescId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}


}