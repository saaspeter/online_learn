package platform.dto;

import platform.vo.Baseaccount;

public class BaseaccountQuery extends Baseaccount{

   private String order_By_Clause;

   public BaseaccountQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
