package platform.vo;

import java.util.Date;

public class Usrcosrecdet {

	private Long usrcosrecdetid;

	private Long costrecordid;

	private Long baseaccountid;

	private Long orderid;

	private Long orderitemid;

	private Long userid;

	private Long shopid;

	private Date paytime;

	private Short paytype;

	private Double lcoincost;

	private Short ispay;

	private Short costtype;

	private String costdisc;

	public Long getUsrcosrecdetid() {
		return usrcosrecdetid;
	}

	public void setUsrcosrecdetid(Long usrcosrecdetid) {
		this.usrcosrecdetid = usrcosrecdetid;
	}

	public Long getCostrecordid() {
		return costrecordid;
	}

	public void setCostrecordid(Long costrecordid) {
		this.costrecordid = costrecordid;
	}

	public Long getBaseaccountid() {
		return baseaccountid;
	}

	public void setBaseaccountid(Long baseaccountid) {
		this.baseaccountid = baseaccountid;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Long getOrderitemid() {
		return orderitemid;
	}

	public void setOrderitemid(Long orderitemid) {
		this.orderitemid = orderitemid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public Short getPaytype() {
		return paytype;
	}

	public void setPaytype(Short paytype) {
		this.paytype = paytype;
	}

	public Double getLcoincost() {
		return lcoincost;
	}

	public void setLcoincost(Double lcoincost) {
		this.lcoincost = lcoincost;
	}

	public Short getIspay() {
		return ispay;
	}

	public void setIspay(Short ispay) {
		this.ispay = ispay;
	}

	public Short getCosttype() {
		return costtype;
	}

	public void setCosttype(Short costtype) {
		this.costtype = costtype;
	}

	public String getCostdisc() {
		return costdisc;
	}

	public void setCostdisc(String costdisc) {
		this.costdisc = costdisc;
	}
}