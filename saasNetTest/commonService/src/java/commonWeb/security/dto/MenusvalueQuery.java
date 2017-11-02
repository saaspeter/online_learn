package commonWeb.security.dto;

import commonWeb.security.vo.Menusvalue;


public class MenusvalueQuery extends Menusvalue{

   private String order_By_Clause;

   public MenusvalueQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
