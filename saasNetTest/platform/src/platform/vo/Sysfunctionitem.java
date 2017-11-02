package platform.vo;

import java.util.Date;

public class Sysfunctionitem {


    private String functioncode;

    private String functionname;

    private String syscode;
    
    private String systemname;

    private Short paytype;
    
    private String paytypename;

    private Double cost;
    
    private Short ismust;

    private Date startdate;

    private Short status;

    private String funcdesc;

 
    public String getFunctioncode() {
        return functioncode;
    }


    public void setFunctioncode(String functioncode) {
        this.functioncode = functioncode;
    }


    public String getFunctionname() {
        return functionname;
    }


    public void setFunctionname(String functionname) {
        this.functionname = functionname;
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


    public Date getStartdate() {
        return startdate;
    }


    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Short getStatus() {
        return status;
    }


    public void setStatus(Short status) {
        this.status = status;
    }


    public String getFuncdesc() {
        return funcdesc;
    }


    public void setFuncdesc(String funcdesc) {
        this.funcdesc = funcdesc;
    }


	public String getSystemname() {
		return systemname;
	}


	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}


	public String getPaytypename() {
		return paytypename;
	}


	public void setPaytypename(String paytypename) {
		this.paytypename = paytypename;
	}


	public Short getIsmust() {
		return ismust;
	}


	public void setIsmust(Short ismust) {
		this.ismust = ismust;
	}


	public String getSyscode() {
		return syscode;
	}


	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
}