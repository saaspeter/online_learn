package netTest.wareques.dto;

import netTest.wareques.vo.Questypeshop;
import commonTool.util.ObjUtil;

public class QuestypeshopQuery extends Questypeshop{

   private String order_By_Clause;

   public QuestypeshopQuery() {
       super();
   }
   
   //public QuestypeshopQuery(Questypeshop vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Questypeshop vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
