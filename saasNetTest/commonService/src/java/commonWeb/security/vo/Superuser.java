package commonWeb.security.vo;

import java.util.Date;

public class Superuser {

    private String loginname;

    private String password;

    private Date createtime;

    private Date duetime;

    private String ipstr;

    private String syscodestr;

    private Short state;


    public String getLoginname() {
        return loginname;
    }


    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreatetime() {
        return createtime;
    }


    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    public Date getDuetime() {
        return duetime;
    }


    public void setDuetime(Date duetime) {
        this.duetime = duetime;
    }


    public String getIpstr() {
        return ipstr;
    }


    public void setIpstr(String ipstr) {
        this.ipstr = ipstr;
    }


    public String getSyscodestr() {
        return syscodestr;
    }


    public void setSyscodestr(String syscodestr) {
        this.syscodestr = syscodestr;
    }


    public Short getState() {
        return state;
    }


    public void setState(Short state) {
        this.state = state;
    }
    
}