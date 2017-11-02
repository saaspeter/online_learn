package commonWeb.security.dto;

import commonWeb.security.vo.RoleResc;


public class RoleRescQuery extends RoleResc{

   private String order_By_Clause;

   public RoleRescQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
