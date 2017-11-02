package netTest.wareques.dto;

import netTest.wareques.vo.Quesdifficult;

public class QuesdifficultQuery extends Quesdifficult{

   private String order_By_Clause;

   public QuesdifficultQuery() {
       super();
   }
   
   //public QuesdifficultQuery(Quesdifficult vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Quesdifficult vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
