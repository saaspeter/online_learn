package netTest.product.dto;

import netTest.product.vo.Openactivitymember;
import commonTool.util.ObjUtil;

public class OpenactivitymemberQuery extends Openactivitymember{

	private String useridstr;
	
   private String order_By_Clause;

   public OpenactivitymemberQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public String getUseridstr() {
		return useridstr;
	}
	
	public void setUseridstr(String useridstr) {
		this.useridstr = useridstr;
	}

   
}
