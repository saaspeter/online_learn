package netTest.product.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;

public class Userprodstatuslog extends NettestBaseVO{

    private Long logid;
	private Short afstatus;
    private Short afstatusrank;
    private Short bfstatus;
    private Short bfstatusrank;
    private Long userproductid;
    private Long userid;
    private Date happendate;
    private Long opertorid;
    private String statusdisc;
    /** 操作者人员名称 **/
    private String opertorDisplayname;
    
    public final static String ObjectType = "userprodstatuslog";

    
    public Long getUserid() {
	   return userid;
    }

    public void setUserid(Long userid) {
	   this.userid = userid;
    }
    
    public Date getHappendate() {
	   return happendate;
    }
    
    public void setHappendate(Date happendate) {
	   this.happendate = happendate;
    }

    
    public String getStatusdisc() {
	   return statusdisc;
    }

    
    public void setStatusdisc(String statusdisc) {
	   this.statusdisc = statusdisc;
    }

	public Short getAfstatus() {
		return afstatus;
	}

	public void setAfstatus(Short afstatus) {
		this.afstatus = afstatus;
	}

	public Short getAfstatusrank() {
		return afstatusrank;
	}

	public void setAfstatusrank(Short afstatusrank) {
		this.afstatusrank = afstatusrank;
	}

	public Short getBfstatus() {
		return bfstatus;
	}

	public void setBfstatus(Short bfstatus) {
		this.bfstatus = bfstatus;
	}

	public Short getBfstatusrank() {
		return bfstatusrank;
	}

	public void setBfstatusrank(Short bfstatusrank) {
		this.bfstatusrank = bfstatusrank;
	}

	public Long getUserproductid() {
		return userproductid;
	}

	public void setUserproductid(Long userproductid) {
		this.userproductid = userproductid;
	}

	public Long getOpertorid() {
		return opertorid;
	}

	public void setOpertorid(Long opertorid) {
		this.opertorid = opertorid;
	}

	public Long getLogid() {
		return logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

	public String getOpertorDisplayname() {
		return opertorDisplayname;
	}

	public void setOpertorDisplayname(String opertorDisplayname) {
		this.opertorDisplayname = opertorDisplayname;
	}
	
}