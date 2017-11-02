package netTest.exam.dto;

import netTest.exam.vo.Testdept;

public class TestdeptQuery extends Testdept{

   private String deptIdstr;
	
   private String order_By_Clause;

   public TestdeptQuery() {
       super();
   }
   
   //public TestdeptQuery(Testdept vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public String getDeptIdstr() {
		return deptIdstr;
	}
	
	public void setDeptIdstr(String deptIdstr) {
		this.deptIdstr = deptIdstr;
	}
   
   //public void copyProperty(Testdept vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
