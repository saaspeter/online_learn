package acegi.dto;

import acegi.vo.Role_resc;

public class Role_rescQuery extends Role_resc{

   private String order_By_Clause;

   public Role_rescQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
