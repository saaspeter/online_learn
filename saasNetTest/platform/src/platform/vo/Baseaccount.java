package platform.vo;

import java.util.Date;

public class Baseaccount {

    private Long baseaccountid;
    private Long objectid;
    private String accountcode;
    private Short accounttype;
    private Date createtime;
    private Double accountnum;
    private Double prepaynum;
    private Short status;
    private String statusdisc;

    public String getStatusdisc() {
		return statusdisc;
	}

	public void setStatusdisc(String statusdisc) {
		this.statusdisc = statusdisc;
	}

	public Long getBaseaccountid() {
	return baseaccountid;
    }

    public void setBaseaccountid(Long baseaccountid) {
	this.baseaccountid = baseaccountid;
    }

    public String getAccountcode() {
	return accountcode;
    }

    public void setAccountcode(String accountcode) {
	this.accountcode = accountcode;
    }

    public Date getCreatetime() {
	return createtime;
    }

    public void setCreatetime(Date createtime) {
	this.createtime = createtime;
    }

    public Short getStatus() {
	return status;
    }

    public void setStatus(Short status) {
	this.status = status;
    }

	public Short getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(Short accounttype) {
		this.accounttype = accounttype;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public Double getAccountnum() {
		return accountnum;
	}

	public void setAccountnum(Double accountnum) {
		this.accountnum = accountnum;
	}

	public Double getPrepaynum() {
		return prepaynum;
	}

	public void setPrepaynum(Double prepaynum) {
		this.prepaynum = prepaynum;
	}
}