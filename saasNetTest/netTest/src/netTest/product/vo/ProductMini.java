package netTest.product.vo;

import netTest.common.vo.NettestBaseVO;

public class ProductMini extends NettestBaseVO{

    protected Long categoryid;
    protected String categoryname;
//    protected Integer producttype;
    
    public static final String ObjectType = "product";


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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
 
//    public Integer getProducttype() {
//        return producttype;
//    }
//
//    public void setProducttype(Integer producttype) {
//        this.producttype = producttype;
//    }

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
    
}