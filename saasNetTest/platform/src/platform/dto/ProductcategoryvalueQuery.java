package platform.dto;

import platform.vo.Productcategoryvalue;

public class ProductcategoryvalueQuery extends Productcategoryvalue{

   private String order_By_Clause;

   public ProductcategoryvalueQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
