package netTest.wareques.dto;

import netTest.wareques.vo.Question;
import commonTool.util.ObjUtil;

public class QuestionQuery extends Question{

   private String wareidStr;
	
   private String warenameStr;
	
   private String difficultname;
	
   private Long orgbaseid;
   
   private Long paperid;
	
   private String order_By_Clause;

   public QuestionQuery() {
       super();
   }
   
   //public QuestionQuery(Question vo) {
   //    super();
   //    ObjUtil.copyProperties(this, vo);
   //}

   public String getOrder_By_Clause() {
       return order_By_Clause;
   }

   public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
   }

	public String getDifficultname() {
		return difficultname;
	}
	
	public void setDifficultname(String difficultname) {
		this.difficultname = difficultname;
	}
	
	public Long getOrgbaseid() {
		return orgbaseid;
	}
	
	public void setOrgbaseid(Long orgbaseid) {
		this.orgbaseid = orgbaseid;
	}
	
	public String getWareidStr() {
		return wareidStr;
	}
	
	public void setWareidStr(String wareidStr) {
		if(wareidStr!=null&&wareidStr.trim().endsWith(",")){
			wareidStr = wareidStr.trim();
			wareidStr = wareidStr.substring(0,wareidStr.length()-1);
		}
		this.wareidStr = wareidStr;
	}
	
	public String getWarenameStr() {
		return warenameStr;
	}
	
	public void setWarenameStr(String warenameStr) {
		this.warenameStr = warenameStr;
	}

	public Long getPaperid() {
		return paperid;
	}

	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
   
}
