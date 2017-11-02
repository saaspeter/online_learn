package netTest.product.dto;

import netTest.product.vo.Productbase;

public class ProductbaseQuery extends Productbase {

	/** 记录产品id的字符串 * */
	private String prodStr;

	// 查询时是否包含下级目录，1为不包含，仅查询当前目录，2为包含下级目录。默认是1
	private int isIncludeChild = 1;

	private Long localeid;

	private String regioncode;

	private String order_By_Clause;

	public ProductbaseQuery() {
		super();
	}

	public String getOrder_By_Clause() {
		return order_By_Clause;
	}

	public void setOrder_By_Clause(String order_By_Clause) {
		this.order_By_Clause = order_By_Clause;
	}

	public String getProdStr() {
		return prodStr;
	}

	public void setProdStr(String prodStr) {
		this.prodStr = prodStr;
	}

	public int getIsIncludeChild() {
		return isIncludeChild;
	}

	public void setIsIncludeChild(int isIncludeChild) {
		this.isIncludeChild = isIncludeChild;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

}
