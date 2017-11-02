package platform.user.dto;

import platform.user.vo.Useractivity;
import commonTool.util.ObjUtil;

public class UseractivityQuery extends Useractivity{

   private String order_By_Clause;

   public UseractivityQuery() {
       super();
   }
   
   //public UseractivityQuery(Useractivity vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Useractivity vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
