package platform.vo;

import java.util.Date;

public class Shopstatuslog {

    private Long logid;

    private Long shopid;
    
    private Short logtype;

    private Short bfstatus;

    private Short afstatus;

    private Date statustime;

    private String statusdisc;

    /** log shop status change **/
    public final static Short LogType_Status = 1;
    /** log shop chargeType change **/
    public final static Short LogType_ChargeType = 2;
    

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Short getBfstatus() {
        return bfstatus;
    }

    public void setBfstatus(Short bfstatus) {
        this.bfstatus = bfstatus;
    }

    public Short getAfstatus() {
        return afstatus;
    }

    public void setAfstatus(Short afstatus) {
        this.afstatus = afstatus;
    }

    public Date getStatustime() {
        return statustime;
    }

    public void setStatustime(Date statustime) {
        this.statustime = statustime;
    }

    public String getStatusdisc() {
        return statusdisc;
    }

    public void setStatusdisc(String statusdisc) {
        this.statusdisc = statusdisc;
    }

	public Long getLogid() {
		return logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public Short getLogtype() {
		return logtype;
	}

	public void setLogtype(Short logtype) {
		this.logtype = logtype;
	}
    
}