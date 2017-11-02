package netTest.exam.dto;

import netTest.exam.vo.Useranswer;

public class UseranswerQuery extends Useranswer{

	private static final long serialVersionUID = 1L;
	
	/** 查询考生答案的类型，0 查询全部阅卷类型题目，1代表查询客观题，2查询主观题，3查询主客观题，4查询客观题和主客观题
     *                   5 查询主观题和主客观题(填空题) **/
	private Integer searchCheckType;

   private String order_By_Clause;
   
   private Long totalnum;
   private Long startnum;
   private Long endnum;
   private Integer pagesize;
   
   
   /** 记录某种类型用户答案数量 **/
   private Long quantity;
   /** 记录某种类型用户答案数量 **/
   private Long quantityChecked;
   /** 记录某种类型用户答案数量 **/
   private Long quantityUnCheck;
   /** 记录某种类型用户答案数量 **/
   private Long quantityPreCheck;
   /** 正在评阅的试题数目 **/
   private Long quantityChecking;
   

   public UseranswerQuery() {
       super(); 
   }
   
   //public UseranswerQuery(Useranswer vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public Integer getSearchCheckType() {
		return searchCheckType;
	}
	
	public void setSearchCheckType(Integer searchCheckType) {
		this.searchCheckType = searchCheckType;
		if(searchCheckType!=null){
			setQueschecktype(null);
		}
	}

	public Long getQuantity() {
		if(quantity==null)
			quantity = 0l;
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getQuantityChecked() {
		if(quantityChecked==null)
			quantityChecked = 0l;
		return quantityChecked;
	}

	public void setQuantityChecked(Long quantityChecked) {
		this.quantityChecked = quantityChecked;
	}

	public Long getQuantityPreCheck() {
		if(quantityPreCheck==null)
			quantityPreCheck = 0l;
		return quantityPreCheck;
	}

	public void setQuantityPreCheck(Long quantityPreCheck) {
		this.quantityPreCheck = quantityPreCheck;
	}

	public Long getQuantityUnCheck() {
		if(quantityUnCheck==null)
			quantityUnCheck = 0l;
		return quantityUnCheck;
	}

	public void setQuantityUnCheck(Long quantityUnCheck) {
		this.quantityUnCheck = quantityUnCheck;
	}

	public Long getQuantityChecking() {
		if(quantityChecking==null)
			quantityChecking = 0l;
		return quantityChecking;
	}

	public void setQuantityChecking(Long quantityChecking) {
		this.quantityChecking = quantityChecking;
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
   
   //public void copyProperty(Useranswer vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
   
}
