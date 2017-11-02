package netTest.exercise.dto;

import netTest.exercise.vo.Userexeranswer;

public class UserexeranswerQuery extends Userexeranswer{

   private String order_By_Clause;

   public UserexeranswerQuery() {
       super();
   }
   
   //public UserexeranswerQuery(Userexeranswer vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Userexeranswer vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
