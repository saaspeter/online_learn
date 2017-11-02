package platform.shop.vo;

import java.io.Serializable;
import java.util.Date;
import org.apache.struts.upload.FormFile;
import platform.logic.Container;
import platform.vo.ShopMini;

public class Shopext implements Serializable, Container{

    private Long shopid;

    private Short authenidtype;

    private String authenidno;

    private String authenimage;
    
    private Short applychargetype;
    
    private Long applyuserid;
    
    private Long approveuserid;

	private Short authenstatus;

    private String authendesc;
    
    private Date authendate;
    
    private Date chargedate;
    // 支付信息，shop admin自己填写, 只有付费商店才需要填写这个，只有付费产品才需要显示支付信息
    private String payinfo;
    
	private FormFile authenimagefile;
    
    private String shopname;
    private String shopcode;
    
    

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

	public Long getContainerID() {
		return shopid;
	}

	public String getContainerType() {
		return ShopMini.ObjectType;
	}

	public boolean isSameShop(Long shopid) {
		return this.shopid.equals(shopid);
	}

	public String getAuthendesc() {
		return authendesc;
	}

	public void setAuthendesc(String authendesc) {
		this.authendesc = authendesc;
	}

	public String getAuthenidno() {
		return authenidno;
	}

	public void setAuthenidno(String authenidno) {
		this.authenidno = authenidno;
	}

	public Short getAuthenidtype() {
		return authenidtype;
	}

	public void setAuthenidtype(Short authenidtype) {
		this.authenidtype = authenidtype;
	}

	public String getAuthenimage() {
		return authenimage;
	}

	public void setAuthenimage(String authenimage) {
		this.authenimage = authenimage;
	}

	public Short getAuthenstatus() {
		return authenstatus;
	}

	public void setAuthenstatus(Short authenstatus) {
		this.authenstatus = authenstatus;
	}

	public Short getApplychargetype() {
		return applychargetype;
	}

	public void setApplychargetype(Short applychargetype) {
		this.applychargetype = applychargetype;
	}

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public Date getAuthendate() {
		return authendate;
	}

	public void setAuthendate(Date authendate) {
		this.authendate = authendate;
	}

	public FormFile getAuthenimagefile() {
		return authenimagefile;
	}

	public void setAuthenimagefile(FormFile authenimagefile) {
		this.authenimagefile = authenimagefile;
	}
	
    public Long getApplyuserid() {
		return applyuserid;
	}

	public void setApplyuserid(Long applyuserid) {
		this.applyuserid = applyuserid;
	}
	
	public Long getApproveuserid() {
		return approveuserid;
	}

	public void setApproveuserid(Long approveuserid) {
		this.approveuserid = approveuserid;
	}

	public Date getChargedate() {
		return chargedate;
	}

	public void setChargedate(Date chargedate) {
		this.chargedate = chargedate;
	}
	
    public String getPayinfo() {
		return payinfo;
	}

	public void setPayinfo(String payinfo) {
		this.payinfo = payinfo;
	}
}