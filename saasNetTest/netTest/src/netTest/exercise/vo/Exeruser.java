package netTest.exercise.vo;

import java.util.Date;
import netTest.common.vo.NettestBaseVO;

public class Exeruser extends NettestBaseVO{

    private Long exeruserid;

    private Long exerid;

    private Long userid;
    
    private String displayname;

    private Long orgbaseid;
    
    private String orgname;

    private Date starttime;

    private Date endtime;

    private Float objectscore;

    private Float totalscore;

    private Short status;

    private Integer orderno;

    private Integer examtimes;
    
    private Integer exerversion;
    
    //////// 来自Exercise的属性
    private String exername;
    
    private Long contentcateid;
    
    private String contentcatename;
    
    public final static String ObjectType = "exeruser";

    
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Integer getExamtimes() {
		return examtimes;
	}

	public void setExamtimes(Integer examtimes) {
		this.examtimes = examtimes;
	}

	public Long getExerid() {
		return exerid;
	}

	public void setExerid(Long exerid) {
		this.exerid = exerid;
	}

	public Long getExeruserid() {
		return exeruserid;
	}

	public void setExeruserid(Long exeruserid) {
		this.exeruserid = exeruserid;
	}

	public Float getObjectscore() {
		return objectscore;
	}

	public void setObjectscore(Float objectscore) {
		this.objectscore = objectscore;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public Long getOrgbaseid() {
		return orgbaseid;
	}

	public void setOrgbaseid(Long orgbaseid) {
		this.orgbaseid = orgbaseid;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Float getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(Float totalscore) {
		this.totalscore = totalscore;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getExerversion() {
		return exerversion;
	}

	public void setExerversion(Integer exerversion) {
		this.exerversion = exerversion;
	}

	public Long getContentcateid() {
		return contentcateid;
	}

	public void setContentcateid(Long contentcateid) {
		this.contentcateid = contentcateid;
	}

	public String getContentcatename() {
		return contentcatename;
	}

	public void setContentcatename(String contentcatename) {
		this.contentcatename = contentcatename;
	}

	public String getExername() {
		return exername;
	}

	public void setExername(String exername) {
		this.exername = exername;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
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