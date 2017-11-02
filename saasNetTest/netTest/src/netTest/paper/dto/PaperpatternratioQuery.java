package netTest.paper.dto;

import netTest.paper.vo.Paperpatternratio;

public class PaperpatternratioQuery extends Paperpatternratio{

   private String order_By_Clause;

   public PaperpatternratioQuery() {
       super();
   }
   
   //public PaperpatternratioQuery(Paperpatternratio vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Paperpatternratio vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
