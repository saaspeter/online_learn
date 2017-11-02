package netTest.orguser.dto;

import netTest.orguser.vo.Orgbase;

public class OrgbaseQuery extends Orgbase{

	private String orgpathStr;
	
   private String order_By_Clause;

   public OrgbaseQuery() {
       super();
   }

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public String getOrgpathStr() {
		return orgpathStr;
	}
	
	public void setOrgpathStr(String orgpathStr) {
		this.orgpathStr = orgpathStr;
	}
	
}
