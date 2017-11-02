package netTest.product.vo;

import java.util.Date;

import netTest.bean.BOFactory;

public class Openactivitymember {

	private Long activityid;
	
	private Long userid;
	
    private String displayname;

    private String phone;

    private String othercontactinfo;

    private Date registertime;

    private Short joinstatus;

    private String note;
        
    private Date joinedtime;
    
    private Long creatorid;
    
    private Openactivity activityvo;
    
    /** 1 注册状态  **/
    public final static Short JoinStatus_Register = 1;
    /** 2 已经参加  **/
    public final static Short JoinStatus_Joined = 2;
    


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

    public Date getJoinedtime() {
		return joinedtime;
	}

	public void setJoinedtime(Date joinedtime) {
		this.joinedtime = joinedtime;
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

	// for security check, load activity creatorid dynamically
	public Long getCreatorid() {
		if(creatorid==null){
			Openactivity vo = BOFactory.getOpenactivityDao().selectByPK(activityid);
			if(vo!=null){
				creatorid = vo.getCreatorid();
			}
		}
		return creatorid;
	}

	public void setCreatorid(Long creatorid) {
		this.creatorid = creatorid;
	}

	public Openactivity getActivityvo() {
		return activityvo;
	}

	public void setActivityvo(Openactivity activityvo) {
		this.activityvo = activityvo;
	}
    
}