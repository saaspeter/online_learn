package netTest.order.dto;

import netTest.order.vo.Orderproduct;

public class OrderproductQuery extends Orderproduct{

   private String order_By_Clause;

   public OrderproductQuery() {
       super();
   }
   
   //public OrderproductQuery(Orderproduct vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Orderproduct vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
