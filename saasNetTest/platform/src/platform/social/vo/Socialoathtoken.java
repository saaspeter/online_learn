package platform.social.vo;

import java.util.Date;

public class Socialoathtoken {

    private Long identityid;
    
    private Short identitytype;

    private Short servicetype;
    
    private String socialuserid;
    
    private String socialuseraccount;
    
    private String displayname;

    private String refreshtoken;

    private String accesstoken;

    private Date updatetimerefresh;

    private Date updatetimeaccess;
    
    private Date createdate;
    

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public Date getUpdatetimeaccess() {
		return updatetimeaccess;
	}

	public void setUpdatetimeaccess(Date updatetimeaccess) {
		this.updatetimeaccess = updatetimeaccess;
	}

	public Date getUpdatetimerefresh() {
		return updatetimerefresh;
	}

	public void setUpdatetimerefresh(Date updatetimerefresh) {
		this.updatetimerefresh = updatetimerefresh;
	}

	public Long getIdentityid() {
		return identityid;
	}

	public void setIdentityid(Long identityid) {
		this.identityid = identityid;
	}

	public Short getIdentitytype() {
		return identitytype;
	}

	public void setIdentitytype(Short identitytype) {
		this.identitytype = identitytype;
	}

	public String getSocialuserid() {
		return socialuserid;
	}

	public void setSocialuserid(String socialuserid) {
		this.socialuserid = socialuserid;
	}

	public void setServicetype(Short servicetype) {
		this.servicetype = servicetype;
	}

	public Short getServicetype() {
		return servicetype;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
	public String getSocialuseraccount() {
		return socialuseraccount;
	}

	public void setSocialuseraccount(String socialuseraccount) {
		this.socialuseraccount = socialuseraccount;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
    
}