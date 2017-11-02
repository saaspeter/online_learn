package platform.dto;

import platform.vo.Shopapp;

public class ShopappQuery extends Shopapp{

   private String order_By_Clause;

   public ShopappQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
