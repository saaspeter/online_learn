package platform.vo;

import java.util.Date;
import java.util.Locale;

import platform.constant.CustomerConstant;
import commonTool.base.BaseVOBase;
import commonTool.util.StringUtil;


public class User extends BaseVOBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long userid;

    protected String displayname;
    
    /** 大部分情况下loginname应该和email相同 **/
    protected String loginname;
    
    protected String email;

    protected String pass;
    
    protected Short gender;
    
    /** 用户选择的localeid，如果用户的localeid不是系统支持的，则用系统默认的locale **/
    protected Long localeid;
    /** 用户自己设置对应的locale **/
    protected Locale locale;
    
    /** 所在时区, 格式是+08:00,0 **/
    protected String timezone;

    protected Double lcoinremain;

    protected Date registime;

    protected Short usertype;

    protected Short status;
    
    protected Short stagestatus;
    
    /** 用户的联系方式信息 **/
    protected Usercontactinfo contactinfo;
    /** 客户的扩展信息 **/
    protected Custinfoex custinfoex;
    
    public static final String ObjectType = "user";
    
    
    public Long getUserid() {
	    return userid;
    }

    public void setUserid(Long userid) {
	    this.userid = userid;
    }

    public String getPass() {
	    return pass;
    }

    public void setPass(String pass) {
	    this.pass = pass;
    }

    /** 此方法仅仅取表中的值，如果要查询登录人员的值请使用getUserLocaleID(request), 如果用户的localeid不是系统支持的，则用系统默认的locale **/
    public Long getLocaleid() {
	    return localeid;
    }

    public void setLocaleid(Long localeid) {
	    this.localeid = localeid;
    }

    public Double getLcoinremain() {
	    return lcoinremain;
    }

    public void setLcoinremain(Double lcoinremain) {
	    this.lcoinremain = lcoinremain;
    }

    public Date getRegistime() {
	    return registime;
    }

    public void setRegistime(Date registime) {
	    this.registime = registime;
    }

    public Short getUsertype() {
	    return usertype;
    }

    public void setUsertype(Short usertype) {
	    this.usertype = usertype;
    }

    public Short getStatus() {
	    return status;
    }

    public void setStatus(Short status) {
	    this.status = status;
    }

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	public Usercontactinfo getContactinfo() {
		return contactinfo;
	}

	public void setContactinfo(Usercontactinfo contactinfo) {
		this.contactinfo = contactinfo;
	}

	public Custinfoex getCustinfoex() {
		return custinfoex;
	}

	public void setCustinfoex(Custinfoex custinfoex) {
		this.custinfoex = custinfoex;
	}

	public Short getStagestatus() {
		return stagestatus;
	}

	public void setStagestatus(Short stagestatus) {
		this.stagestatus = stagestatus;
	}

	public String getTimezone() {
		return timezone;
	}
	
	/**
	 * 真正的timezone, 格式是 +08:00
	 * @return
	 */
	public String getTimezoneNum() {
		String[] arr = StringUtil.splitString(timezone, ",");
		if(arr!=null && arr.length>0){
			return arr[0];
		}else {
		    return timezone;
		}
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public String getObjectType(){
		return ObjectType;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
    
    public boolean isEmpty(){
    	if(this.displayname!=null && this.displayname.trim().length()>0){
			return false;
		}else if(this.localeid!=null){
			return false;
		}else if(this.timezone!=null && this.timezone.trim().length()>0){
			return false;
		}
    	// 
    	return true;
    }

	public Short getGender() {
		return gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}
	
	public String showDisplayLoginame(){
		return CustomerConstant.combineDisplayname(displayname, loginname);
	}

}