package netTest.product.dto;

import netTest.product.vo.Shopopenactivitymember;
import commonTool.util.ObjUtil;

public class ShopopenactivitymemberQuery extends Shopopenactivitymember{

   private String order_By_Clause;

   public ShopopenactivitymemberQuery() {
       super();
   }
   
   //public ShopopenactivitymemberQuery(Shopopenactivitymember vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Shopopenactivitymember vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
