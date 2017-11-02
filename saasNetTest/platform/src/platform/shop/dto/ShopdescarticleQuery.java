package platform.shop.dto;

import platform.shop.vo.Shopdescarticle;

public class ShopdescarticleQuery extends Shopdescarticle{

   private String order_By_Clause;

   public ShopdescarticleQuery() {
       super();
   }
   
   //public ShopdescarticleQuery(Shopdescarticle vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Shopdescarticle vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
