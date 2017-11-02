package netTest.exam.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;

public class Testuser extends NettestBaseVO {

    private Long testuserid;

    private Long testid;
    
    private String testname;

    private Long userid;
    
    private Long orgbaseid;
    
    /** 不对应db字段 **/
    private String displayname;
    /** 不对应db字段 **/
    private String loginname;
    
    private Long paperid;

    private Date starttime;

    private Integer lefttime;

    private Date suspendtesttime;

    private Date resumetesttime;

    private Date endtime;

    private Float objectscore;

    private Float totalscore;

    private Short isqualify;

    private Short status;
    
    private Integer ordernodept;
    
    private Integer ordernoall;
    
    //////////////////////////
    
    private Date teststartdate;

    private Date testenddate;

    private Short teststatus;
    
    private String createorgname;
    
    private Integer paperversion;
    
    public final static String ObjectType = "testuser";
    

    public Long getTestuserid() {
        return testuserid;
    }


    public void setTestuserid(Long testuserid) {
        this.testuserid = testuserid;
    }


    public Long getShopid() {
        return shopid;
    }


    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }


    public Long getTestid() {
        return testid;
    }


    public void setTestid(Long testid) {
        this.testid = testid;
    }


    public Long getUserid() {
        return userid;
    }


    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getPaperid() {
        return paperid;
    }


    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }


    public Date getStarttime() {
        return starttime;
    }


    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }


    public Integer getLefttime() {
        return lefttime;
    }


    public void setLefttime(Integer lefttime) {
        this.lefttime = lefttime;
    }


    public Date getSuspendtesttime() {
        return suspendtesttime;
    }


    public void setSuspendtesttime(Date suspendtesttime) {
        this.suspendtesttime = suspendtesttime;
    }


    public Date getResumetesttime() {
        return resumetesttime;
    }


    public void setResumetesttime(Date resumetesttime) {
        this.resumetesttime = resumetesttime;
    }


    public Date getEndtime() {
        return endtime;
    }


    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }


    public Float getObjectscore() {
        return objectscore;
    }


    public void setObjectscore(Float objectscore) {
        this.objectscore = objectscore;
    }


    public Float getTotalscore() {
        return totalscore;
    }


    public void setTotalscore(Float totalscore) {
        this.totalscore = totalscore;
    }


    public Short getIsqualify() {
        return isqualify;
    }


    public void setIsqualify(Short isqualify) {
        this.isqualify = isqualify;
    }


    public Short getStatus() {
        return status;
    }


    public void setStatus(Short status) {
        this.status = status;
    }

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public Date getTestenddate() {
		return testenddate;
	}

	public void setTestenddate(Date testenddate) {
		this.testenddate = testenddate;
	}

	public Date getTeststartdate() {
		return teststartdate;
	}

	public void setTeststartdate(Date teststartdate) {
		this.teststartdate = teststartdate;
	}

	public Short getTeststatus() {
		return teststatus;
	}

	public void setTeststatus(Short teststatus) {
		this.teststatus = teststatus;
	}

	public String getCreateorgname() {
		return createorgname;
	}

	public void setCreateorgname(String createorgname) {
		this.createorgname = createorgname;
	}

	public Integer getOrdernoall() {
		return ordernoall;
	}

	public void setOrdernoall(Integer ordernoall) {
		this.ordernoall = ordernoall;
	}

	public Integer getOrdernodept() {
		return ordernodept;
	}

	public void setOrdernodept(Integer ordernodept) {
		this.ordernodept = ordernodept;
	}


	public Integer getPaperversion() {
		return paperversion;
	}


	public void setPaperversion(Integer paperversion) {
		this.paperversion = paperversion;
	}

	public String getLoginname() {
		return loginname;
	}


	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}


	@Override
	protected String getObjectType() {
		return ObjectType;
	}


	public Long getOrgbaseid() {
		return orgbaseid;
	}


	public void setOrgbaseid(Long orgbaseid) {
		this.orgbaseid = orgbaseid;
	}


	public String getDisplayname() {
		return displayname;
	}


	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
}