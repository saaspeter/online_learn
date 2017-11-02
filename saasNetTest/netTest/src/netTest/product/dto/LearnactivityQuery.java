package netTest.product.dto;

import java.util.List;

import netTest.product.vo.Learnactivity;

public class LearnactivityQuery extends Learnactivity{
	
	private List<Long> objectidList;

    public List<Long> getObjectidList() {
		return objectidList;
	}

	public void setObjectidList(List<Long> objectidList) {
		this.objectidList = objectidList;
	}

	private String order_By_Clause;

   public LearnactivityQuery() {
       super();
   }
   
   //public LearnactivityQuery(Learnactivity vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }
   
}
