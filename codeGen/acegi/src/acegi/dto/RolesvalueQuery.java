package acegi.dto;

import acegi.vo.Rolesvalue;

public class RolesvalueQuery extends Rolesvalue{

   private String order_By_Clause;

   public RolesvalueQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
