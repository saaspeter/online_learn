package commonTool.biz.dto;

import commonTool.biz.vo.Systemlog;
import commonTool.util.ObjUtil;

public class SystemlogQuery extends Systemlog{

   private String order_By_Clause;

   public SystemlogQuery() {
       super();
   }
   
   //public SystemlogQuery(Systemlog vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty(Systemlog vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
