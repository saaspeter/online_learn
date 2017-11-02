package netTest.product.vo;

import java.util.Date;

public class Shopopenactivitymember {

	private Long activityid;
	
	private Long userid;
	
    private String displayname;

    private String phone;

    private String othercontactinfo;

    private Date registertime;

    private Short joinstatus;

    private String note;


    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOthercontactinfo() {
        return othercontactinfo;
    }

    public void setOthercontactinfo(String othercontactinfo) {
        this.othercontactinfo = othercontactinfo;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public Short getJoinstatus() {
        return joinstatus;
    }

    public void setJoinstatus(Short joinstatus) {
        this.joinstatus = joinstatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public Long getActivityid() {
        return activityid;
    }

    public void setActivityid(Long activityid) {
        this.activityid = activityid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}