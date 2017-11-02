package platform.dto;

import platform.vo.Usercontactinfo;

public class UsercontactinfoQuery extends Usercontactinfo{

   private String order_By_Clause;

   public UsercontactinfoQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
}
