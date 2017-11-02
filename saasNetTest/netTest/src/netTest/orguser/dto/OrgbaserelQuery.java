package netTest.orguser.dto;

import netTest.orguser.vo.Orgbaserel;

public class OrgbaserelQuery extends Orgbaserel{

   private String order_By_Clause;

   public OrgbaserelQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
