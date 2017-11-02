package platform.user.vo;

import java.util.Date;

public class Useractivity {

    private Long activityid;

    private Long userid;

    private String actiontype;

    private String targetobject;

    private String result;

    private String note;

    private Date createtime;
    
    public static final String Actiontype_UpdateForgotPassword = "UpdateForgotPassword";
    
    public static final String Result_SUCCESS = "SUCCESS";
    
    public static final String Result_FAIL = "FAIL";
    

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

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public String getTargetobject() {
        return targetobject;
    }

    public void setTargetobject(String targetobject) {
        this.targetobject = targetobject;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}