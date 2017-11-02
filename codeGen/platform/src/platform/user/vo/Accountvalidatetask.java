package platform.user.vo;

import java.util.Date;

public class Accountvalidatetask {

    private Long id;

    private Long userid;

    private String email;

    private String validatetype;

    private String validatecode;

    private Short status;

    private Date createdate;

    private Date aliveenddate;

    private Date activedate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidatetype() {
        return validatetype;
    }

    public void setValidatetype(String validatetype) {
        this.validatetype = validatetype;
    }

    public String getValidatecode() {
        return validatecode;
    }

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getAliveenddate() {
        return aliveenddate;
    }

    public void setAliveenddate(Date aliveenddate) {
        this.aliveenddate = aliveenddate;
    }

    public Date getActivedate() {
        return activedate;
    }

    public void setActivedate(Date activedate) {
        this.activedate = activedate;
    }
}