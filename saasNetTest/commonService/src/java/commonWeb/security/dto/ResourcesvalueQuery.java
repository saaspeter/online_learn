package commonWeb.security.dto;

import commonWeb.security.vo.Resourcesvalue;


public class ResourcesvalueQuery extends Resourcesvalue{

   private String order_By_Clause;

   public ResourcesvalueQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
