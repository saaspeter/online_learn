package platform.shop.dto;

import platform.shop.vo.Shoppost;

public class ShoppostQuery extends Shoppost{

   private String order_By_Clause;

   public ShoppostQuery() {
       super();
   }
   
   //public ShoppostQuery(Shoppost vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Shoppost vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
