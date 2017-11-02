package commonWeb.security.vo;

import java.io.Serializable;

public class UserRole implements Serializable{

    private Long roleid;

    private Long userid;
    /** 使用类型：1为可使用可分配；2为可使用；**/    
    private Short usetype;
    
    private Long shopid;
        
    private String syscode;


	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Short getUsetype() {
		return usetype;
	}

	public void setUsetype(Short usetype) {
		this.usetype = usetype;
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