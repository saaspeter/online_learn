package platform.shop.vo;

import commonTool.base.TreeVOInf;

import platform.vo.Productcategory;

public class Prodcateshop extends Productcategory implements TreeVOInf{

    private Long shopid;

    private String syscode;
    

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String toTreeString() {
        return this.getCategoryname();
	}
	
	public Long getPk(){
		return this.getCategoryid();
	}
    
}