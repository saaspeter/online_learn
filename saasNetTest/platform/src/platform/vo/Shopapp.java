package platform.vo;

import java.util.Date;

public class Shopapp {

    private Long shopappid;
    /** 记录审批成功的商店id, 对于审批不通过的申请商店没有shopid **/
    private Long shopid;

    private String shopname;
    
    private String shopcode;

    private Long localeid;

    private Long userid;

    private Short opentype;

    private String usernumscale;
    
    private Short ownertype;
    
    private Integer bizarea;

    private Date appdate;

    private Short appstatus;
    
    private Date replydate;
    
    private String regioncode;
    
    private String contactname;

    private String address;

    private String email;

    private String telephone;
    
    private String postcode;

    private String notes;


    public Long getShopappid() {
        return shopappid;
    }

    public void setShopappid(Long shopappid) {
        this.shopappid = shopappid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Long getLocaleid() {
        return localeid;
    }

    public void setLocaleid(Long localeid) {
        this.localeid = localeid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getAppdate() {
        return appdate;
    }

    public void setAppdate(Date appdate) {
        this.appdate = appdate;
    }

    public Short getAppstatus() {
        return appstatus;
    }

    public void setAppstatus(Short appstatus) {
        this.appstatus = appstatus;
    }

    public Date getReplydate() {
        return replydate;
    }

    public void setReplydate(Date replydate) {
        this.replydate = replydate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}

	public Integer getBizarea() {
		return bizarea;
	}

	public void setBizarea(Integer bizarea) {
		this.bizarea = bizarea;
	}

	public Short getOpentype() {
		return opentype;
	}

	public void setOpentype(Short opentype) {
		this.opentype = opentype;
	}

	public String getUsernumscale() {
		return usernumscale;
	}

	public void setUsernumscale(String usernumscale) {
		this.usernumscale = usernumscale;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(Short ownertype) {
		this.ownertype = ownertype;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}
}