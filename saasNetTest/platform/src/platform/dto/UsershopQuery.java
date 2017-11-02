package platform.dto;

import platform.vo.Usershop;

public class UsershopQuery extends Usershop{

	private Long orgbaseid;
	
	private Long productbaseid;
	
	private String useridStr;

	
	/** 用户的角色id字符串 **/
	public String roleidStr;
	
    private String order_By_Clause;

    public UsershopQuery() {
        super();
    }

    public String getOrder_By_Clause() {
        return order_By_Clause;
    }

    public void setOrder_By_Clause(String order_By_Clause) {
        this.order_By_Clause = order_By_Clause;
    }

	public String getRoleidStr() {
		return roleidStr;
	}

	public void setRoleidStr(String roleidStr) {
		this.roleidStr = roleidStr;
	}

	public Long getOrgbaseid() {
		return orgbaseid;
	}

	public void setOrgbaseid(Long orgbaseid) {
		this.orgbaseid = orgbaseid;
	}

	public Long getProductbaseid() {
		return productbaseid;
	}

	public void setProductbaseid(Long productbaseid) {
		this.productbaseid = productbaseid;
	}

	public String getUseridStr() {
		return useridStr;
	}

	public void setUseridStr(String useridStr) {
		this.useridStr = useridStr;
	}
	
}
