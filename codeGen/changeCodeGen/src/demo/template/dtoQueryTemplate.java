package {[#PackageDto#]};

import {[#PackageVO#]}.{[#className#]};
import commonTool.util.ObjUtil;

public class {[#className#]}Query extends {[#className#]}{

   private String order_By_Clause;

   public {[#className#]}Query() {
       super();
   }
   
   //public {[#className#]}Query({[#className#]} vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
   //public void copyProperty({[#className#]} vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}