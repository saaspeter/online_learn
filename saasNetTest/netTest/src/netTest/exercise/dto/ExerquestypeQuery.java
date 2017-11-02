package netTest.exercise.dto;

import netTest.exercise.vo.Exerquestype;

public class ExerquestypeQuery extends Exerquestype{

   private String order_By_Clause;

   public ExerquestypeQuery() {
       super();
   }
   
   //public ExerquestypeQuery(Exerquestype vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Exerquestype vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
