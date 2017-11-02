package netTest.paper.dto;

import netTest.paper.vo.Paperquestype;
import commonTool.util.ObjUtil;

public class PaperquestypeQuery extends Paperquestype{

   private String order_By_Clause;

   public PaperquestypeQuery() {
       super();
   }
   
   //public PaperquestypeQuery(Paperquestype vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Paperquestype vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
