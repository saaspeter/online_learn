package commonWeb.security.vo;

import java.io.Serializable;

public class Roles implements Serializable{

    private Long id;

    private String name;
    
    private String code;
    
    private Long shopid;
   
    private String syscode;
    
    private Long upId;
    
    private Short status;
    
    private Short isdefaultset;
    
    private Short rolelevel;

    private Long creator;
    
    private String descn;
    
    private String upIdName;
    
    private String upIdCode;
    
    private Long localeid;

    public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public Short getIsdefaultset() {
		return isdefaultset;
	}

	public void setIsdefaultset(Short isdefaultset) {
		this.isdefaultset = isdefaultset;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public Long getUpId() {
		return upId;
	}

	public void setUpId(Long upId) {
		this.upId = upId;
	}

	public String getUpIdName() {
		return upIdName;
	}

	public void setUpIdName(String upIdName) {
		this.upIdName = upIdName;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUpIdCode() {
		return upIdCode;
	}

	public void setUpIdCode(String upIdCode) {
		this.upIdCode = upIdCode;
	}

	public Short getRolelevel() {
		return rolelevel;
	}

	public void setRolelevel(Short rolelevel) {
		this.rolelevel = rolelevel;
	}
}