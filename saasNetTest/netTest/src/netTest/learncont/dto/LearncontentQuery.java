package netTest.learncont.dto;

import netTest.learncont.vo.Learncontent;

public class LearncontentQuery extends Learncontent{

	private Short learntypequery;
	
	/** 查询doc级别，1代表顶级资料查询,默认为1 **/
	private int querydoclevel=1;
	
    private String order_By_Clause;

    public LearncontentQuery() {
       super();
    }
   
   //public LearncontentQuery(Learncontent vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

    public String getOrder_By_Clause() {
       return order_By_Clause;
    }

    public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
    }

	public int getQuerydoclevel() {
		return querydoclevel;
	}
	
	public void setQuerydoclevel(int querydoclevel) {
		this.querydoclevel = querydoclevel;
	}

	public Short getLearntypequery() {
		return learntypequery;
	}

	public void setLearntypequery(Short learntypequery) {
		this.learntypequery = learntypequery;
	}
   
   //public void copyProperty(Learncontent vo){
   //   ObjUtil.copyProperties(this, vo);
   //}
    
}
