package netTest.exam.dto;

import netTest.exam.vo.Testchecker;

public class TestcheckerQuery extends Testchecker{

   private Long orgbaseid;
   
   private String userIdStr;
   /** 考试状态字符串，可以一次查询多种状态 **/
   private String teststatusStr;
	
   private String order_By_Clause;

   public TestcheckerQuery() {
       super();
   }
   
   //public TestcheckerQuery(Testchecker vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

public Long getOrgbaseid() {
	return orgbaseid;
}

public void setOrgbaseid(Long orgbaseid) {
	this.orgbaseid = orgbaseid;
}

public String getUserIdStr() {
	return userIdStr;
}

public void setUserIdStr(String userIdStr) {
	this.userIdStr = userIdStr;
}

public String getTeststatusStr() {
	return teststatusStr;
}

public void setTeststatusStr(String teststatusStr) {
	this.teststatusStr = teststatusStr;
}
   
   //public void copyProperty(Testchecker vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
   
}
