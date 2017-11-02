package platform.vo;

import java.util.Date;

public class Shopfunc {

    private Long shopfuncid;
    private Long shopid;

    private String functioncode;
    
    private String functionname;

    private Short paytype;

    private String syscode;

    private Double cost;

    private Short ispay;

    private Date startdate;

    private Date enddate;

    private Short status;

    public Long getShopfuncid() {
   	   return shopfuncid;
    }


    public void setShopfuncid(Long shopfuncid) {
	   this.shopfuncid = shopfuncid;
    }

    public Long getShopid() {
	   return shopid;
    }

    public void setShopid(Long shopid) {
	   this.shopid = shopid;
    }

    public String getFunctioncode() {
	   return functioncode;
    }

    public void setFunctioncode(String functioncode) {
	   this.functioncode = functioncode;
    }

    public Short getPaytype() {
	   return paytype;
    }

    public void setPaytype(Short paytype) {
	   this.paytype = paytype;
    }

    public Double getCost() {
	   return cost;
    }

    public void setCost(Double cost) {
	   this.cost = cost;
    }

    public Short getIspay() {
  	   return ispay;
    }

    public void setIspay(Short ispay) {
	   this.ispay = ispay;
    }

    public Date getStartdate() {
	   return startdate;
    }

    public void setStartdate(Date startdate) {
	   this.startdate = startdate;
    }

    public Date getEnddate() {
	   return enddate;
    }

    public void setEnddate(Date enddate) {
	   this.enddate = enddate;
    }

    public Short getStatus() {
	   return status;
    }

    public void setStatus(Short status) {
	   this.status = status;
    }

	public String getFunctionname() {
		return functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
}