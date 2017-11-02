package platform.social.dto;

import platform.social.vo.Leavemessage;

public class LeavemessageQuery extends Leavemessage{
	
	private String pkStr;

   private String order_By_Clause;

   public LeavemessageQuery() {
       super();
   }
   
   //public CommentsQuery(Comments vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

public String getPkStr() {
	return pkStr;
}

public void setPkStr(String pkStr) {
	this.pkStr = pkStr;
}
   
   //public void copyProperty(Comments vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
