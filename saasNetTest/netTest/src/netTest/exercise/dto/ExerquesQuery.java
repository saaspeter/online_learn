package netTest.exercise.dto;

import netTest.exercise.vo.Exerques;

public class ExerquesQuery extends Exerques{

   private String order_By_Clause;

   public ExerquesQuery() {
       super();
   }
   
   //public ExerquesQuery(Exerques vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Exerques vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
