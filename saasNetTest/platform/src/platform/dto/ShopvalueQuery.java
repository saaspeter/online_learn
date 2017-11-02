package platform.dto;

import platform.vo.Shopvalue;

public class ShopvalueQuery extends Shopvalue{

   private String order_By_Clause;

   public ShopvalueQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
