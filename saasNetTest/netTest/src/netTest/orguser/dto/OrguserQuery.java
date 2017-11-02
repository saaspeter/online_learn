package netTest.orguser.dto;

import netTest.orguser.vo.Orguser;

public class OrguserQuery extends Orguser{

   /** 是否包括下级单位查询，1为不包括下级，2为包括下级 **/
   private Short isIncludeChild = 1;
   
   private String orgnameStr;
   
   private String productName;
   
   private Long neworgbaseid;
   
	
   private String order_By_Clause;

   public OrguserQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

   public Short getIsIncludeChild() {
	   return isIncludeChild;
   }

   public void setIsIncludeChild(Short isIncludeChild) {
	   this.isIncludeChild = isIncludeChild;
   }

	public String getOrgnameStr() {
		return orgnameStr;
	}
	
	public void setOrgnameStr(String orgnameStr) {
		this.orgnameStr = orgnameStr;
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getNeworgbaseid() {
		return neworgbaseid;
	}

	public void setNeworgbaseid(Long neworgbaseid) {
		this.neworgbaseid = neworgbaseid;
	}
}
