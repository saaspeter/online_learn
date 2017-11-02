package netTest.product.dto;

import java.util.Date;

import netTest.product.vo.Openactivity;

public class OpenactivityQuery extends Openactivity{
	
	// 开始时间的范围，3: 3天内 , 7: 一周内, 14: 两周内, -1: 全部
	private Integer starttimerange;
	
	// 查询用开始时间范围的开始
	private Date starttime1;
	// 查询用开始时间范围的结束
	private Date starttime2;
	
	private Date endtime1;
	
	private Date endtime2;
	
	private Integer rownumfrom;
	
	private Integer rownum;
	

    private String order_By_Clause;

    public OpenactivityQuery() {
       super();
    }

    public String getOrder_By_Clause() {
       return order_By_Clause;
    }

    public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
    }

	public int getRownumfrom() {
		return rownumfrom;
	}
	
	public void setRownumfrom(Integer rownumfrom) {
		this.rownumfrom = rownumfrom;
	}
	
	public int getRownum() {
		return rownum;
	}
	
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

	public Integer getStarttimerange() {
		return starttimerange;
	}

	public void setStarttimerange(Integer starttimerange) {
		this.starttimerange = starttimerange;
	}

	public Date getStarttime1() {
		return starttime1;
	}

	public void setStarttime1(Date starttime1) {
		this.starttime1 = starttime1;
	}

	public Date getStarttime2() {
		return starttime2;
	}

	public void setStarttime2(Date starttime2) {
		this.starttime2 = starttime2;
	}

	public Date getEndtime1() {
		return endtime1;
	}

	public void setEndtime1(Date endtime1) {
		this.endtime1 = endtime1;
	}

	public Date getEndtime2() {
		return endtime2;
	}

	public void setEndtime2(Date endtime2) {
		this.endtime2 = endtime2;
	}
    
}
