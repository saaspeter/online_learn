package netTest.wareques.dto;

import netTest.wareques.vo.Ware;

public class WareQuery extends Ware {

	/** 题库字符串 **/
	private String wareidStr;
	
	
	private String order_By_Clause;

	public WareQuery() {
		super();
	}

	public String getOrder_By_Clause() {
		return order_By_Clause;
	}

	public void setOrder_By_Clause(String order_By_Clause) {
		this.order_By_Clause = order_By_Clause;
	}

	public String getWareidStr() {
		return wareidStr;
	}

	public void setWareidStr(String wareidStr) {
		this.wareidStr = wareidStr;
	}

}
