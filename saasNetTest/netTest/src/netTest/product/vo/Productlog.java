package netTest.product.vo;

import java.util.Date;

public class Productlog {

	protected Long productid;
	
    protected String productname;
    
    private Short logtype;
	
	protected Long shopid;
	
	private Short bfstatus;
	
	private Short afstatus;
	
	private Long creatorid;
	
	private Date logdate;
	
	private String note;
	
	public static final Short LogType_New = 1;
	public static final Short LogType_ChangeStatus = 2;
	public static final Short LogType_Delete = 4;
	
	
	private String order_By_Clause;

	
	public Long getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Long creatorid) {
		this.creatorid = creatorid;
	}

	public Date getLogdate() {
		return logdate;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}

	public Short getLogtype() {
		return logtype;
	}

	public void setLogtype(Short logtype) {
		this.logtype = logtype;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
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

	public Short getBfstatus() {
		return bfstatus;
	}

	public void setBfstatus(Short bfstatus) {
		this.bfstatus = bfstatus;
	}

	public Short getAfstatus() {
		return afstatus;
	}

	public void setAfstatus(Short afstatus) {
		this.afstatus = afstatus;
	}

	public String getOrder_By_Clause() {
		return order_By_Clause;
	}

	public void setOrder_By_Clause(String order_By_Clause) {
		this.order_By_Clause = order_By_Clause;
	}
	
}
