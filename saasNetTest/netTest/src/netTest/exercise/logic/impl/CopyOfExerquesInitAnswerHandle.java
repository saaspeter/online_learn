package netTest.exercise.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import netTest.common.logic.QuesTypeHandle;
import netTest.exam.constant.UseranswerConstant;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exerquestype;
import netTest.exercise.vo.Userexeranswer;
import netTest.wareques.constant.QuestionConstant;

import commonTool.util.CollectionUtil;
/**
 * 负责初始化用户对于练习的答案UserExerAnswer。用于在用户刚进入练习时处理。
 * @author peter
 */
public class CopyOfExerquesInitAnswerHandle extends QuesTypeHandle {

	
	public CopyOfExerquesInitAnswerHandle(){}
	
	@Override
//	protected List<Userexeranswer> doCaiLiao(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return null;
//	}

    /**
     * 构造单选题用户答案。
     */
	protected List<Userexeranswer> doDanXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Map paraMap = (Map)paraObj1;
		List<Userexeranswer> retList = new ArrayList<Userexeranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null){
		   Exerquestype typeVO = (Exerquestype)paraMap.get("typeVO");
		   Long userid = (Long)paraMap.get("userid");
		   Long exerid = typeVO.getExerid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getQuesList();
		   
		   if(queslist!=null&&queslist.size()>0){
			  Userexeranswer answerVO = null;
			  Exerques quesVO = null;
			  int[] randArr = null;
	    	  randArr = CollectionUtil.getSequenceNum(queslist.size());
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Userexeranswer();
		    	  quesVO = (Exerques)queslist.get(randArr[i]);
		    	  answerVO.setExerid(exerid);
		    	  answerVO.setExerquesid(quesVO.getExerquesid());
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setUserid(userid);
		    	  answerVO.setShopid(shopid);
		    	  answerVO.setQueschecktype(quesVO.getQueschecktype());
		    	  answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
		    	  retList.add(answerVO);
		      }
		   }
		}
		return retList;
	}

    /**
     * 多选题乱序，包括题目的选项
     */
	protected List<Userexeranswer> doDuoXuan(Object paraObj1,Object paraObj2,Integer questype) {
		List<Userexeranswer> list = this.doDanXuan(paraObj1,paraObj2, questype);
		return list;
	}

    /**
     * 判断题乱序，包括题目的选项
     */
	protected List<Userexeranswer> doPanDuan(Object paraObj1,Object paraObj2,Integer questype) {
		List<Userexeranswer> list = this.doDanXuan(paraObj1,paraObj2, questype);
		return list;
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
	protected List<Userexeranswer> doTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		List<Userexeranswer> list = doPanDuan(paraObj1,paraObj2,questype);
		return list;
	}

	@Override
	protected List<Userexeranswer> doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Map paraMap = (Map)paraObj1;
		List<Userexeranswer> retList = new ArrayList<Userexeranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null)
		{
		   Exerquestype typeVO = (Exerquestype)paraMap.get("typeVO");
		   Long userid = (Long)paraMap.get("userid");
		   Long exerid = typeVO.getExerid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getQuesList();
		   if(queslist!=null&&queslist.size()>0){
			  Userexeranswer answerVO = null;
			  Exerques quesVO = null;
			  int[] randArr = CollectionUtil.getSequenceNum(queslist.size());
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Userexeranswer();
		    	  quesVO = (Exerques)queslist.get(randArr[i]);
		    	  answerVO.setExerid(exerid);
		    	  answerVO.setExerquesid(quesVO.getExerquesid());
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setUserid(userid);
		    	  answerVO.setShopid(shopid);
		    	  answerVO.setQueschecktype(quesVO.getQueschecktype());
		    	  // 设置子题目
		    	  if(quesVO.getSubquesList()!=null&&quesVO.getSubquesList().size()>0){
		    		  List<Userexeranswer> subansList = new ArrayList<Userexeranswer>();
		    		  for(int j=0;j<quesVO.getSubquesList().size();j++){
		    			  Userexeranswer subanswerVO = new Userexeranswer();
		    			  Exerques subquesVO = (Exerques)quesVO.getSubquesList().get(j);
		    			  subanswerVO.setExerid(exerid);
		    			  subanswerVO.setExerquesid(subquesVO.getExerquesid());
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
		    	  retList.add(answerVO);
		      }
		   }

		}
		return retList;
	}

	@Override
	protected List<Userexeranswer> doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		List<Userexeranswer> list = this.doPanDuan(paraObj1,paraObj2, questype);
		return list;
	}

	@Override
	protected List<Userexeranswer> doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Map paraMap = (Map)paraObj1;
		List<Userexeranswer> retList = new ArrayList<Userexeranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null)
		{
		   Exerquestype typeVO = (Exerquestype)paraMap.get("typeVO");
		   Long userid = (Long)paraMap.get("userid");
		   Long exerid = typeVO.getExerid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getQuesList();
		   if(queslist!=null&&queslist.size()>0){
			   Userexeranswer answerVO = null;
			  Exerques quesVO = null;
			  int[] randArr = null;
			  int[] randArr2 = null;
		      randArr = CollectionUtil.getSequenceNum(queslist.size());
		      
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Userexeranswer();
		    	  quesVO = (Exerques)queslist.get(randArr[i]);
		    	  answerVO.setExerid(exerid);
		    	  answerVO.setExerquesid(quesVO.getExerquesid());
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setUserid(userid);
		    	  answerVO.setShopid(shopid);
		    	  answerVO.setQueschecktype(quesVO.getQueschecktype());
		    	  // 设置子题目
		    	  if(quesVO.getSubquesList()!=null&&quesVO.getSubquesList().size()>0){
		    		  List<Userexeranswer> subansList = new ArrayList<Userexeranswer>();
				      randArr2 = CollectionUtil.getSequenceNum(quesVO.getSubquesList().size());
				      
		    		  for(int j=0;j<randArr2.length;j++){
		    			  Userexeranswer subanswerVO = new Userexeranswer();
		    			  Exerques subquesVO = (Exerques)quesVO.getSubquesList().get(randArr2[j]);
		    			  answerVO.setExerid(exerid);
		    			  answerVO.setExerquesid(subquesVO.getExerquesid());
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
		    	  retList.add(answerVO);
		      }
		   }
		}
		return retList;
	}
	
	protected List<Userexeranswer> doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return null;
	}

}
