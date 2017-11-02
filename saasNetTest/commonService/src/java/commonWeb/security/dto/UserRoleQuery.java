package commonWeb.security.dto;

import java.util.List;

import commonWeb.security.vo.UserRole;


public class UserRoleQuery extends UserRole{

	/** 对应的上级权限 **/
	private Long pid;
	
	private Long localeid;
	
	private String syscode;
	
   private String order_By_Clause;
   
   private String rescname;
   
   private Long rescId;
   
   private String loginname;
   
   private String roleidstr;
   
   

   public UserRoleQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public Long getPid() {
		return pid;
	}
	
	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getRescname() {
		return rescname;
	}

	public void setRescname(String rescname) {
		this.rescname = rescname;
	}

	public Long getRescId() {
		return rescId;
	}

	public void setRescId(Long rescId) {
		this.rescId = rescId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getRoleidstr() {
		return roleidstr;
	}

	public void setRoleidstr(String roleidstr) {
		this.roleidstr = roleidstr;
	}

	
}
