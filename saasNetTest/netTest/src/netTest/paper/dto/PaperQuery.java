package netTest.paper.dto;

import netTest.paper.vo.Paper;

public class PaperQuery extends Paper{

	
   private String order_By_Clause;

   public PaperQuery() {
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
