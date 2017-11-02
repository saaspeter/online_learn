package acegi.dto;

import acegi.vo.User_role;

public class User_roleQuery extends User_role{

   private String order_By_Clause;

   public User_roleQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
