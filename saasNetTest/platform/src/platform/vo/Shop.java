package platform.vo;

import java.util.Date;
import java.util.List;

public class Shop extends ShopMini{

    private Long userid;
    /** 商店创建者名称 **/
    private String creatorname;

    private Long localeid;

    private Short opentype;

    private Date regisdate;
    
    private Integer usernum;
    
    private String usernumscale;
    
    private Integer bizarea;
    
    private Short chargetype;
    
    private Short ownertype;
    
    private Short shopstatus;

    private Long shopvalueid;
    
    private Short isdefaultset; 
        
    private String shopdesc;
    /** 不同语言国家设置商店名的集合 **/
    private List shopValueList;
    
    
    public String getShopdesc() {
		return shopdesc;
	}

	public void setShopdesc(String shopdesc) {
		this.shopdesc = shopdesc;
	}

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getLocaleid() {
        return localeid;
    }

    public void setLocaleid(Long localeid) {
        this.localeid = localeid;
    }

    public Date getRegisdate() {
        return regisdate;
    }

    public void setRegisdate(Date regisdate) {
        this.regisdate = regisdate;
    }

    public Integer getUsernum() {
        return usernum;
    }

    public void setUsernum(Integer usernum) {
        this.usernum = usernum;
    }

    public Short getShopstatus() {
        return shopstatus;
    }

    public void setShopstatus(Short shopstatus) {
        this.shopstatus = shopstatus;
    }

	public Long getShopvalueid() {
		return shopvalueid;
	}

	public void setShopvalueid(Long shopvalueid) {
		this.shopvalueid = shopvalueid;
	}

	public List getShopValueList() {
		return shopValueList;
	}

	public void setShopValueList(List shopValueList) {
		this.shopValueList = shopValueList;
	}

	public Short getIsdefaultset() {
		return isdefaultset;
	}

	public void setIsdefaultset(Short isdefaultset) {
		this.isdefaultset = isdefaultset;
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

	public Integer getBizarea() {
		return bizarea;
	}

	public void setBizarea(Integer bizarea) {
		this.bizarea = bizarea;
	}

	public String getCreatorname() {
		return creatorname;
	}

	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}

	public Short getChargetype() {
		return chargetype;
	}

	public void setChargetype(Short chargetype) {
		this.chargetype = chargetype;
	}
	
	public Short getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(Short ownertype) {
		this.ownertype = ownertype;
	}

}