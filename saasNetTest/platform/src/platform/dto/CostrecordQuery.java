package platform.dto;

import platform.vo.Costrecord;

public class CostrecordQuery extends Costrecord{

   private String order_By_Clause;

   public CostrecordQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
