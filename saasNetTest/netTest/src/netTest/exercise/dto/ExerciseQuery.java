package netTest.exercise.dto;

import netTest.exercise.vo.Exercise;

public class ExerciseQuery extends Exercise{

   private String order_By_Clause;

   public ExerciseQuery() {
       super();
   }
   
   //public PaperQuery(Paper vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Paper vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
