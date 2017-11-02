package netTest.product.dto;

import netTest.product.vo.Userproduct;

public class UserproductQuery extends Userproduct{

   /** 对应基础产品表中的产品状态 **/
   private Short prodStatus;
	
   private String syscode;
   
//   private String prodIDStr;

   private String order_By_Clause;

   public UserproductQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public String getSyscode() {
		return syscode;
	}
	
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public Short getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(Short prodStatus) {
		this.prodStatus = prodStatus;
	}

}
