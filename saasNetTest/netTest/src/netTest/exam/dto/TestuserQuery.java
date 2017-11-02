package netTest.exam.dto;

import commonTool.util.StringUtil;

import netTest.exam.vo.Testuser;

public class TestuserQuery extends Testuser{

   private String userIdstr;
	
   private String order_By_Clause;
   
   /** 状态字符串，用于查询用户信息 **/
   private String statusStr;
   
   private String orderColumn;
   private String orderColumn_real;
   /** 1为升序，2为降序 **/
   private Integer orderDirect;
   
   /**
    * 查询方式：1为简单查询(testuser,OrgBase)，2为复合查询(testuser,TestInfo)
    */
   private int queryType=1;
   /** 大概的考试状态，用户用来查询考试的, 见TestuserConstant.RoughTestStatus, 默认没有这个查询条件 **/
   private Integer roughteststatus;
   
   
   private Long totalnum;
   private Long startnum;
   private Long endnum;
   private Integer pagesize;
   

   public TestuserQuery() {
       super();
   }
   
   //public TestuserQuery(Testuser vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
	   String orderby = null;
	   if(getOrderDirect()!=null){
		   if(getOrderDirect()==2)
			   orderby = "desc";
		   else
			   orderby = "asc";
	   }else
		   orderby = "asc";
	   
	   if(orderColumn_real!=null&&orderColumn_real.trim().length()>0)
	      order_By_Clause = " "+orderColumn_real+" "+orderby+" ";
       return order_By_Clause;
   }

	public String getUserIdstr() {
		userIdstr = StringUtil.filterSpecCharForSql(userIdstr,0);
		return userIdstr;
	}
	
	public void setUserIdstr(String userIdstr) {
		this.userIdstr = userIdstr;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		if(orderColumn!=null&&orderColumn.trim().length()>0){
			if("isqualify".equals(orderColumn)){
				this.orderColumn_real = "a.isQualify";
			}else if("totalscore".equals(orderColumn)){
				this.orderColumn_real = "a.totalScore";
			}else if("objectscore".equals(orderColumn)){
				this.orderColumn_real = "a.objectScore";
			}else if("teststartdate".equals(orderColumn)){
				this.orderColumn_real = "b.testStartDate";
			}else if("ordernoall".equals(orderColumn)){
				this.orderColumn_real = "a.orderNoAll";
			}else {
				this.orderColumn_real = null;
			}
			this.orderColumn = orderColumn;
		}
	}

	public Integer getOrderDirect() {
		if(orderDirect==null)
			orderDirect = 1;
		return orderDirect;
	}

	public void setOrderDirect(Integer orderDirect) {
		this.orderDirect = orderDirect;
	}

	public Integer getRoughteststatus() {
		return roughteststatus;
	}

	public void setRoughteststatus(Integer roughteststatus) {
		this.roughteststatus = roughteststatus;
	}

	public Long getEndnum() {
		return endnum;
	}

	public void setEndnum(Long endnum) {
		this.endnum = endnum;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Long getStartnum() {
		return startnum;
	}

	public void setStartnum(Long startnum) {
		this.startnum = startnum;
	}

	public Long getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Long totalnum) {
		this.totalnum = totalnum;
	}
   
   //public void copyProperty(Testuser vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
