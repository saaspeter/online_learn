package netTest.exam.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;
import netTest.exam.constant.TestinfoConstant;

public class Testinfo extends NettestBaseVO{

    private Long testid;

    private String testname;

    private String papername;

    private Long paperid;

    private Short isdynamicpaper;

    private Integer paperversion;
    
    private Integer papertime;

    private Date createtime;

    private Date teststartdate;

    private Date testenddate;

    private Short teststatus;

    private Long createorgid;

    private String createorgname;

    private Date updatetime;
    /** 1: 仅对指定单位中指定的人员开放,  2: 对指定单位中的所有成员开放, 3: 对整个互联网开放 **/
    private Short opentype;
    
    private Float testavescore;
    
    private Float testquaper;
    
    private Short testcanstop;
    
    private Integer canstoptime;
    
    private Long createuserid;
    
    private String createloginname;
    
    private String createusername;
    
    private String testnotes;
    
    /** 现在考试状态的下一个考试状态 **/
    private Short nextteststatus;
    
    /** 查看考试结果类型 **/
    private Short viewresulttype;
    
	
	public static final String ObjectType = "testinfo";

    
    public Long getTestid() {
        return testid;
    }

    public void setTestid(Long testid) {
        this.testid = testid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }

    public Short getIsdynamicpaper() {
        return isdynamicpaper;
    }

    public void setIsdynamicpaper(Short isdynamicpaper) {
        this.isdynamicpaper = isdynamicpaper;
    }

    public Integer getPaperversion() {
        return paperversion;
    }

    public void setPaperversion(Integer paperversion) {
        this.paperversion = paperversion;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getTeststartdate() {
        return teststartdate;
    }

    public void setTeststartdate(Date teststartdate) {
        this.teststartdate = teststartdate;
    }

    public Date getTestenddate() {
        return testenddate;
    }

    public void setTestenddate(Date testenddate) {
        this.testenddate = testenddate;
    }

    public Long getProductbaseid() {
        return productbaseid;
    }

    public void setProductbaseid(Long productbaseid) {
        this.productbaseid = productbaseid;
    }

    public Short getTeststatus() {
        return teststatus;
    }

    public void setTeststatus(Short teststatus) {
        this.teststatus = teststatus;
    }

    public Long getCreateorgid() {
        return createorgid;
    }

    public void setCreateorgid(Long createorgid) {
        this.createorgid = createorgid;
    }

    public String getCreateorgname() {
        return createorgname;
    }

    public void setCreateorgname(String createorgname) {
        this.createorgname = createorgname;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

	public Integer getCanstoptime() {
		return canstoptime;
	}

	public void setCanstoptime(Integer canstoptime) {
		this.canstoptime = canstoptime;
	}

	public String getCreateloginname() {
		return createloginname;
	}

	public void setCreateloginname(String createloginname) {
		this.createloginname = createloginname;
	}

	public Long getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(Long createuserid) {
		this.createuserid = createuserid;
	}

	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public Float getTestavescore() {
		return testavescore;
	}

	public void setTestavescore(Float testavescore) {
		this.testavescore = testavescore;
	}

	public Short getTestcanstop() {
		return testcanstop;
	}

	public void setTestcanstop(Short testcanstop) {
		this.testcanstop = testcanstop;
	}

	public String getTestnotes() {
		return testnotes;
	}

	public void setTestnotes(String testnotes) {
		this.testnotes = testnotes;
	}

	public Float getTestquaper() {
		return testquaper;
	}

	public void setTestquaper(Float testquaper) {
		this.testquaper = testquaper;
	}

	public Integer getPapertime() {
		return papertime;
	}

	public void setPapertime(Integer papertime) {
		this.papertime = papertime;
	}

	public Short getNextteststatus() {
		return nextteststatus;
	}

	public void setNextteststatus(Short nextteststatus) {
		this.nextteststatus = nextteststatus;
	}
	
	public boolean isChangedProp(){
		boolean ret = false;
		if(this.testavescore!=null||this.testquaper!=null||this.testcanstop!=null
			||this.canstoptime!=null||this.createuserid!=null||this.createloginname!=null
			||this.createusername!=null||this.papername!=null||this.testnotes!=null)
			ret = true;
		return ret;
			
	}
	
	public boolean isChangedTestinfo(){
		boolean ret = false;
		if(this.testname!=null||this.paperid!=null||this.isdynamicpaper!=null
			||this.paperversion!=null||this.papertime!=null||this.createtime!=null
			||this.teststartdate!=null||this.testenddate!=null||this.productbaseid!=null
			||this.teststatus!=null||this.createorgid==null||this.createorgname==null
			||this.productname!=null||this.updatetime!=null)
			
			ret = true;
		return ret;
	}

	public Short getOpentype() {
		return opentype;
	}

	public void setOpentype(Short opentype) {
		this.opentype = opentype;
	}
	
	
	/** 从teststatus转换为teststatusshow状态。变量为teststatusshowfromteststatus
	 * 用于在界面上显示目前考试的状态 
	 * 1: 考试未开始  3: 考试进行中  5:考试结束  10:考试阅卷中  13:试卷评阅完毕  15:考试结果开放 
	 * **/
	public Short getTeststatusshowfromteststatus() {
		Short teststatusshowTemp = teststatus;
		if(TestinfoConstant.Teststatus_beginCheck.equals(teststatus)||
//		   TestinfoConstant.Teststatus_autoChecking.equals(teststatus)||
		   TestinfoConstant.Teststatus_manualChecking.equals(teststatus)){
			teststatusshowTemp = 10;
		}else{
			teststatusshowTemp = teststatus;
		}
		return teststatusshowTemp;
	}

	public Short getViewresulttype() {
		return viewresulttype;
	}

	public void setViewresulttype(Short viewresulttype) {
		this.viewresulttype = viewresulttype;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	protected String getObjectType() {
		return "Testinfo";
	}
	
	@Override
	public Long getContainerID() {
		return testid;
	}

	@Override
	public String getContainerType() {
		return Testinfo.ObjectType;
	}
    
}