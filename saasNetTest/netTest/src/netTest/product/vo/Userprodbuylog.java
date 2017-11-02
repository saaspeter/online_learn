package netTest.product.vo;

import java.util.Date;

import netTest.common.vo.NettestBaseVO;

public class Userprodbuylog extends NettestBaseVO{

    private Long orderid;

    private Long userproductid;

    private Long userid;

    private Date happendate;
    /** 1表示新增产品，2代表用户产品被删除 **/
    private Short action;

    private String displayname;

    private String loginname;
    
    public final static String ObjectType = "userprodbuylog";
    

    public Long getUserid() {
    	return userid;
    }

    public void setUserid(Long userid) {
    	this.userid = userid;
    }

	public Long getUserproductid() {
		return userproductid;
	}

	public void setUserproductid(Long userproductid) {
		this.userproductid = userproductid;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Short getAction() {
		return action;
	}

	public void setAction(Short action) {
		this.action = action;
	}

	public Date getHappendate() {
		return happendate;
	}

	public void setHappendate(Date happendate) {
		this.happendate = happendate;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
	
}