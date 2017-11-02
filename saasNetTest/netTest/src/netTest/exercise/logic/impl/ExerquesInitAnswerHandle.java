package netTest.exercise.logic.impl;

import java.util.ArrayList;
import java.util.List;

import netTest.common.logic.QuesTypeHandle;
import netTest.exam.constant.UseranswerConstant;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Userexeranswer;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Question;
/**
 * 负责初始化用户对于练习的答案UserExerAnswer。用于在用户刚进入练习时处理。
 * @author peter
 */
public class ExerquesInitAnswerHandle extends QuesTypeHandle {

	
	public ExerquesInitAnswerHandle(){}
	
	@Override
//	protected List<Userexeranswer> doCaiLiao(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return null;
//	}

    /**
     * 构造单选题用户答案。
     */
	protected Userexeranswer doDanXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Exerques quesVO = (Exerques)paraObj1;
		Long userid = (Long)paraObj2;
		Userexeranswer answerVO = new Userexeranswer();
	    Long exerid = quesVO.getExerid();
	    Long shopid = quesVO.getShopid();		   

	    answerVO.setExerid(exerid);
	    answerVO.setExerquesid(quesVO.getExerquesid());
	    answerVO.setQuesid(quesVO.getQuesid());
	    answerVO.setQuestype(questype);
	    answerVO.setUserid(userid);
	    answerVO.setShopid(shopid);
	    answerVO.setQueschecktype(quesVO.getQueschecktype());
	    answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);  	  

		return answerVO;
	}

    /**
     * 多选题乱序，包括题目的选项
     */
	protected Userexeranswer doDuoXuan(Object paraObj1,Object paraObj2,Integer questype) {
		Userexeranswer vo = this.doDanXuan(paraObj1,paraObj2, questype);
		return vo;
	}

    /**
     * 判断题乱序，包括题目的选项
     */
	protected Userexeranswer doPanDuan(Object paraObj1,Object paraObj2,Integer questype) {
		Userexeranswer vo = this.doDanXuan(paraObj1,paraObj2, questype);
		return vo;
	}

    /**
     * 配对题乱序，包括题目的选项
     */
	protected List<Userexeranswer> doPeiDui(Object paraObj1,Object paraObj2,Integer questype) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 填空题乱序，包括题目的选项。
     */
	protected Userexeranswer doTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		Userexeranswer vo = doPanDuan(paraObj1,paraObj2,questype);
		return vo;
	}

	@Override
	protected Userexeranswer doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return null;
		Exerques quesVO = (Exerques)paraObj1;
		Long userid = (Long)paraObj2;
		Userexeranswer answerVO = new Userexeranswer();
		
	    Long exerid = quesVO.getExerid();
	    Long shopid = quesVO.getShopid();
	    answerVO.setExerid(exerid);
	    answerVO.setExerquesid(quesVO.getExerquesid());
	    answerVO.setQuesid(quesVO.getQuesid());
	    answerVO.setQuestype(questype);
	    answerVO.setUserid(userid);
	    answerVO.setShopid(shopid);
	    answerVO.setQueschecktype(quesVO.getQueschecktype());
	    answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
	    // 设置子题目
	    if(quesVO.getSubquesList()!=null&&quesVO.getSubquesList().size()>0){
		   List<Userexeranswer> subansList = new ArrayList<Userexeranswer>();
		   for(int j=0;j<quesVO.getSubquesList().size();j++){
			  Userexeranswer subanswerVO = new Userexeranswer();
			  Question subquesVO = quesVO.getSubquesList().get(j);
			  subanswerVO.setExerid(exerid);
			  subanswerVO.setExerquesid(quesVO.getExerquesid());
			  subanswerVO.setQuesid(subquesVO.getQuesid());
			  subanswerVO.setQuestype(QuestionConstant.QuesType_WanXingTianKong_Subques);
			  subanswerVO.setUserid(userid);
			  subanswerVO.setShopid(shopid);
			  subanswerVO.setPid(answerVO.getQuesid()); 
			  subanswerVO.setQueschecktype(QuestionConstant.Queschecktype_auto);
			  subanswerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge); 	  
	    	  subansList.add(subanswerVO);
		   }
		   answerVO.setSubanswList(subansList);
	    }

		return answerVO;
	}

	@Override
	protected Userexeranswer doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		Userexeranswer vo = this.doPanDuan(paraObj1,paraObj2, questype);
		return vo;
	}

	@Override
	protected Userexeranswer doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return null;
		Exerques quesVO = (Exerques)paraObj1;
		Long userid = (Long)paraObj2;
		Userexeranswer answerVO = new Userexeranswer();
		
	    Long exerid = quesVO.getExerid();
	    Long shopid = quesVO.getShopid();
	    answerVO.setExerid(exerid);
	    answerVO.setExerquesid(quesVO.getExerquesid());
	    answerVO.setQuesid(quesVO.getQuesid());
	    answerVO.setQuestype(questype);
	    answerVO.setUserid(userid);
	    answerVO.setShopid(shopid);
	    answerVO.setQueschecktype(quesVO.getQueschecktype());
	    answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
	    // 设置子题目
	    List<Question> subquesList = quesVO.getSubquesList();
	    if(subquesList!=null&&subquesList.size()>0){
		   List<Userexeranswer> subansList = new ArrayList<Userexeranswer>();  
		   for(int j=0;j<quesVO.getSubquesList().size();j++){
			  Userexeranswer subanswerVO = new Userexeranswer();
			  Question subquesVO = subquesList.get(j);
			  subanswerVO.setExerid(exerid);
			  subanswerVO.setExerquesid(quesVO.getExerquesid());
			  subanswerVO.setQuesid(subquesVO.getQuesid());
			  subanswerVO.setQuestype(QuestionConstant.QuesType_YueDuLiJie_Subques);
			  subanswerVO.setUserid(userid);
			  subanswerVO.setShopid(shopid);
			  subanswerVO.setPid(answerVO.getQuesid());
			  subanswerVO.setQueschecktype(QuestionConstant.Queschecktype_auto);
			  subanswerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
	    	  subansList.add(subanswerVO);
		   }
		   answerVO.setSubanswList(subansList);
	    }
		return answerVO;
	}
	
	protected List<Userexeranswer> doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return null;
	}

}
