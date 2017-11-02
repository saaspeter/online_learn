package netTest.product.vo;

import java.util.Date;
import netTest.common.vo.NettestBaseVO;


public class Userproduct extends NettestBaseVO{

    private Long userproductid;

    /** 用户使用产品的生成方式，1代表通过订单生成；
     * 2代表由子系统（教育系统）内部生成的数据，没有订单，
     * 因此其产品使用类型为免费使用，所有费用信息也不用填写 **/
    private Short genetype;

    private Short paytype;

    private Double cost;
    
    private Short produsetype;

    private Date startdate;

    private Date enddate;

    private Short ispay;
    
    private Integer uselimitnum;
    
    private Integer usenum;
    
    private Long userid;
    
    private String displayname;

    private String loginname;
    
    private Date lastaccesstime;
    
    // 非db 表字段
    protected Long categoryid;
    
    protected String categoryname;

    protected Short status;
    
    protected Short statusrank;
    
    public final static String ObjectType = "userproduct";
    

    
	public Long getUserproductid() {
        return userproductid;
    }

    public void setUserproductid(Long userproductid) {
        this.userproductid = userproductid;
    }

    public Short getPaytype() {
        return paytype;
    }

    public void setPaytype(Short paytype) {
        this.paytype = paytype;
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

    public Short getIspay() {
        return ispay;
    }

    public void setIspay(Short ispay) {
        this.ispay = ispay;
    }

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Short getProdusetype() {
		return produsetype;
	}

	public void setProdusetype(Short produsetype) {
		this.produsetype = produsetype;
	}

	public Integer getUselimitnum() {
		return uselimitnum;
	}

	public void setUselimitnum(Integer uselimitnum) {
		this.uselimitnum = uselimitnum;
	}

	public Integer getUsenum() {
		return usenum;
	}

	public void setUsenum(Integer usenum) {
		this.usenum = usenum;
	}

	public Short getGenetype() {
		return genetype;
	}

	public void setGenetype(Short genetype) {
		this.genetype = genetype;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getStatusrank() {
		return statusrank;
	}

	public void setStatusrank(Short statusrank) {
		this.statusrank = statusrank;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getLastaccesstime() {
		return lastaccesstime;
	}

	public void setLastaccesstime(Date lastaccesstime) {
		this.lastaccesstime = lastaccesstime;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}
	
	public Long getContainerID() {
		return productbaseid;
	}

	public String getContainerType() {
		return ProductMini.ObjectType;
	}

}