package netTest.paper.dto;

import netTest.paper.vo.Paperques;

public class PaperquesQuery extends Paperques{

   private String order_By_Clause;

   public PaperquesQuery() {
       super();
   }
   
   //public PaperquesQuery(Paperques vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Paperques vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
