package netTest.paper.dto;

import netTest.paper.vo.Paperprop;
import commonTool.util.ObjUtil;

public class PaperpropQuery extends Paperprop{

   private String order_By_Clause;

   public PaperpropQuery() {
       super();
   }
   
   //public PaperpropQuery(Paperprop vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Paperprop vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
