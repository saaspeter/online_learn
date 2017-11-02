package platform.dto;

import platform.vo.Shopfunc;

public class ShopfuncQuery extends Shopfunc{

   private String order_By_Clause;

   public ShopfuncQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
