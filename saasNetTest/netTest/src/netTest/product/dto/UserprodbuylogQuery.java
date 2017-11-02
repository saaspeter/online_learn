package netTest.product.dto;

import netTest.product.vo.Userprodbuylog;

public class UserprodbuylogQuery extends Userprodbuylog{

   private String order_By_Clause;

   public UserprodbuylogQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
