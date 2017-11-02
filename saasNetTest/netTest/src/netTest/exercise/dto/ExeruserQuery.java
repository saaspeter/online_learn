package netTest.exercise.dto;

import netTest.exercise.vo.Exeruser;

public class ExeruserQuery extends Exeruser{

	private String prodidStr;
	
    private String order_By_Clause;
   

    public ExeruserQuery() {
       super();
    }

    public String getOrder_By_Clause() {
       return order_By_Clause;
    }

    public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
    }

	public String getProdidStr() {
		return prodidStr;
	}
	
	public void setProdidStr(String prodidStr) {
		this.prodidStr = prodidStr;
	}
  
}
