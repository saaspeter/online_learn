package platform.vo;

public class Useraccountsetting {

	private Long userid;
	
	private Short shopcreateable;
	
	/** 已经创建的shop数目，仅仅为了显示用，不对应db字段 **/
	private Integer createdShopNum;
	/** 正在申请待审核的shop数目，仅仅为了显示用，不对应db字段 **/
	private Integer inapplyShopNum;
	
	/** 不允许创建商店 **/
	public final static Short Shopcreateable_NotAllow = 0;
	/** 允许创建一个商店 **/
	public final static Short Shopcreateable_AllowOne = 1;
	/** 可以创建多个商店 **/
	public final static Short Shopcreateable_AllowMultiple = 2;

	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Short getShopcreateable() {
		return shopcreateable;
	}

	public void setShopcreateable(Short shopcreateable) {
		this.shopcreateable = shopcreateable;
	}

	public Integer getCreatedShopNum() {
		return createdShopNum;
	}

	public void setCreatedShopNum(Integer createdShopNum) {
		this.createdShopNum = createdShopNum;
	}

	public Integer getInapplyShopNum() {
		return inapplyShopNum;
	}

	public void setInapplyShopNum(Integer inapplyShopNum) {
		this.inapplyShopNum = inapplyShopNum;
	}
	
}
