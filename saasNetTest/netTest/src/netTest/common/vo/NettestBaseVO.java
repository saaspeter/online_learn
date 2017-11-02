package netTest.common.vo;

import platform.logic.Container;
import platform.vo.Shop;
import commonTool.base.BaseVOBase;
import commonTool.util.StringUtil;

public abstract class NettestBaseVO extends BaseVOBase implements Container{
	
	protected Long shopid;
	
	protected String shopname;

	protected Long productbaseid;
	
	protected String productname;
	
	protected String prodidStr;

	
	public String getProdidStr() {
		if(prodidStr!=null){
			return StringUtil.trimComma(prodidStr);
		}else
		    return prodidStr;
	}

	public void setProdidStr(String prodidStr) {
		this.prodidStr = prodidStr;
	}

	public Long getProductbaseid() {
		return productbaseid;
	}

	public void setProductbaseid(Long productbaseid) {
		this.productbaseid = productbaseid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	
	public Long getContainerID() {
		return shopid;
	}

	public String getContainerType() {
		return Shop.ObjectType;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}
	
}
