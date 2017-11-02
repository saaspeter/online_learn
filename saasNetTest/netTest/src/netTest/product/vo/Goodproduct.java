package netTest.product.vo;

import java.util.Date;

public class Goodproduct {

    private Long productbaseid;

    private Long categoryid;

    private Long shopid;

    private Integer goodvalue;

    private Short fromsource;

    /** 1: product locale, 2: all locales shared **/
    private Short scope;

    private Date lastdate;
    
    /////////////// 以下字段是为查询使用的，不对应具体db字段
    //  查询时是否包含下级目录，1为不包含，仅查询当前目录，2为包含下级目录。默认是1
	private int isIncludeChild = 1;

	private Long localeid;


    public Long getProductbaseid() {
        return productbaseid;
    }


    public void setProductbaseid(Long productbaseid) {
        this.productbaseid = productbaseid;
    }


    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Integer getGoodvalue() {
        return goodvalue;
    }

    public void setGoodvalue(Integer goodvalue) {
        this.goodvalue = goodvalue;
    }

    public Short getFromsource() {
        return fromsource;
    }

    public void setFromsource(Short fromsource) {
        this.fromsource = fromsource;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
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


	public Short getScope() {
		return scope;
	}


	public void setScope(Short scope) {
		this.scope = scope;
	}


}