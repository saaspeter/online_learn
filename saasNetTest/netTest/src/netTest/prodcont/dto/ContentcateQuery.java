package netTest.prodcont.dto;

import netTest.prodcont.vo.Contentcate;

public class ContentcateQuery extends Contentcate{

   private String order_By_Clause;

   public ContentcateQuery() {
       super();
   }
   
   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

}
