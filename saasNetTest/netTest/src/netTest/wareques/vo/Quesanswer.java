package netTest.wareques.vo;

import netTest.common.vo.NettestBaseVO;
import netTest.wareques.constant.QuestionConstant;

public class Quesanswer extends NettestBaseVO implements QuesBaseVOInf{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long quesid;

    protected Long shopid;
    
    protected String answertext;

    protected String solvedesc;

    protected String order_By_Clause;

    public Long getQuesid() {
        return quesid;
    }


    public void setQuesid(Long quesid) {
        this.quesid = quesid;
    }


    public Long getShopid() {
        return shopid;
    }


    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

	public String getAnswertext() {
		return answertext;
	}

	public void setAnswertext(String answertext) {
		this.answertext = answertext;
	}

	public String getSolvedesc() {
		return solvedesc;
	}

	public void setSolvedesc(String solvedesc) {
		this.solvedesc = solvedesc;
	}
	
	public boolean canInsert(){
	   if(quesid!=null&&shopid!=null&&
			   (answertext!=null||solvedesc!=null))
		   return true;
	   else
		   return false;
	    
	}
	
	public boolean canUpdate(){
		if(quesid!=null &&
				   (answertext!=null||solvedesc!=null))
			   return true;
		   else
			   return false;
	}
	
	/** 得到问题的种类：题库题目 **/
	public int getQuesKind(){
		return QuestionConstant.Question_ware;
	}


	public String getOrder_By_Clause() {
		return order_By_Clause;
	}


	public void setOrder_By_Clause(String order_By_Clause) {
		this.order_By_Clause = order_By_Clause;
	}


	@Override
	protected String getObjectType() {
		return "quesanswer";
	}
	
}