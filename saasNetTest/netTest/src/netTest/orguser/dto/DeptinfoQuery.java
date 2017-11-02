package netTest.orguser.dto;

import netTest.orguser.vo.Deptinfo;

public class DeptinfoQuery extends Deptinfo{

   
	
   private String order_By_Clause;

   public DeptinfoQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

}
