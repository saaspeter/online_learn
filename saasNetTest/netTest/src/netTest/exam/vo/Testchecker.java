package netTest.exam.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;

public class Testchecker extends NettestBaseVO{
    
	private Long testid;
    private Long userid;
    private Integer checkquesnum;
    private String displayname;
    /** 是否是考试创建者，1：考试创建者，2：不是 **/
    private Short isexamcreator;

    ///////////////////////////////////////////////////////////////
    // 以下是testinfo的信息
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
    private Short testtype;
    private Long testpid;
    
    public final static String ObjectType = "testchecker";
    
    ///////////////////////////////////////////////////////////////
    
    
    public Integer getCheckquesnum() {
        return checkquesnum;
    }

    public void setCheckquesnum(Integer checkquesnum) {
        this.checkquesnum = checkquesnum;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Short getIsdynamicpaper() {
		return isdynamicpaper;
	}

	public void setIsdynamicpaper(Short isdynamicpaper) {
		this.isdynamicpaper = isdynamicpaper;
	}

	public Long getPaperid() {
		return paperid;
	}

	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}

	public String getPapername() {
		return papername;
	}

	public void setPapername(String papername) {
		this.papername = papername;
	}

	public Integer getPapertime() {
		return papertime;
	}

	public void setPapertime(Integer papertime) {
		this.papertime = papertime;
	}

	public Integer getPaperversion() {
		return paperversion;
	}

	public void setPaperversion(Integer paperversion) {
		this.paperversion = paperversion;
	}

	public Date getTestenddate() {
		return testenddate;
	}

	public void setTestenddate(Date testenddate) {
		this.testenddate = testenddate;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
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

	public Short getTesttype() {
		return testtype;
	}

	public void setTesttype(Short testtype) {
		this.testtype = testtype;
	}

	public Long getTestpid() {
		return testpid;
	}

	public void setTestpid(Long testpid) {
		this.testpid = testpid;
	}

	public Short getIsexamcreator() {
		return isexamcreator;
	}

	public void setIsexamcreator(Short isexamcreator) {
		this.isexamcreator = isexamcreator;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
    
}