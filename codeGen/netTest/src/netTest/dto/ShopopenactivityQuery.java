package netTest.product.dto;

import netTest.product.vo.Shopopenactivity;
import commonTool.util.ObjUtil;

public class ShopopenactivityQuery extends Shopopenactivity{

   private String order_By_Clause;

   public ShopopenactivityQuery() {
       super();
   }
   
   //public ShopopenactivityQuery(Shopopenactivity vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Shopopenactivity vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
