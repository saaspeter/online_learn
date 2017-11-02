package netTest.exam.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import netTest.common.logic.QuesTypeHandle;
import netTest.exam.constant.UseranswerConstant;
import netTest.exam.vo.Useranswer;
import netTest.paper.vo.Paperques;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Question;
import commonTool.util.CollectionUtil;
/**
 * 负责初始化用户对于一份试卷的答案Useranswer。用于在用户刚进入考试时处理。
 * @author peter
 */
public class PaperquesInitAnswerHandle extends QuesTypeHandle {

	
	public PaperquesInitAnswerHandle(){}
	
	@Override
//	protected List<Useranswer> doCaiLiao(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return null;
//	}

    /**
     * 构造单选题用户答案。如果需要乱序，则乱序，包括题目的选项
     */
	protected List<Useranswer> doDanXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Long questypeid = (Long)paraObj2;
		Map paraMap = (Map)paraObj1;
		boolean needRandom = getNeedRandom(paraMap);
		List<Useranswer> retList = new ArrayList<Useranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null){
		   Paperquestype typeVO = (Paperquestype)paraMap.get("typeVO");
		   Long testid = (paraMap.get("testid")==null)?null:((Long)paraMap.get("testid"));
		   Long userid = (Long)paraMap.get("userid");
		   Long paperid = typeVO.getPaperid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getPaperquesList();
		   
		   if(queslist!=null&&queslist.size()>0){
			  Useranswer answerVO = null;
			  StringBuffer itemorderstr = new StringBuffer();
			  Paperques quesVO = null;
			  int[] itemArr = null;
		      int[] randArr = null;
		      if(needRandom){
		    	  randArr = CollectionUtil.getRandomNum(queslist.size(), queslist.size());
		      }else{
		    	  randArr = CollectionUtil.getSequenceNum(queslist.size());
		      }
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Useranswer();
		    	  quesVO = (Paperques)queslist.get(randArr[i]);
		    	  answerVO.setPaperid(paperid);
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuesorder(randArr[i]);
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setQuestypeid(questypeid);
		    	  answerVO.setTestid(testid);
		    	  answerVO.setUserid(userid);
		    	  answerVO.setShopid(shopid);
		    	  answerVO.setQueschecktype(quesVO.getQueschecktype());
		    	  answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
		    	  // 乱序选择题的答案选项
		    	  itemorderstr = new StringBuffer();
		    	  if(quesVO.getItemList()!=null&&quesVO.getItemList().size()>0){
		    		  if(needRandom){
		    			  itemArr = CollectionUtil.getRandomNum(quesVO.getItemList().size(), quesVO.getItemList().size());
				      }else{
				    	  itemArr = CollectionUtil.getSequenceNum(quesVO.getItemList().size());
				      }
		    		  if(itemArr!=null&&itemArr.length>0){
		    			  for(int j=0;j<itemArr.length;j++){
		    				  itemorderstr.append(String.valueOf(itemArr[j])).append(",");
		    			  }
		    			  answerVO.setItemorderstr(itemorderstr.toString());
		    		  }
		    	  }
		    	  retList.add(answerVO);
		      }
		   }
		}
		return retList;
	}

    /**
     * 多选题乱序，包括题目的选项
     */
	protected List<Useranswer> doDuoXuan(Object paraObj1,Object paraObj2,Integer questype) {
		List<Useranswer> list = this.doDanXuan(paraObj1,paraObj2, questype);
		return list;
	}

    /**
     * 判断题乱序，包括题目的选项
     */
	protected List<Useranswer> doPanDuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Long questypeid = (Long)paraObj2;
		Map paraMap = (Map)paraObj1;
		boolean needRandom = getNeedRandom(paraMap);
		List<Useranswer> retList = new ArrayList<Useranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null){
		   Paperquestype typeVO = (Paperquestype)paraMap.get("typeVO");
		   Long testid = (paraMap.get("testid")==null)?null:((Long)paraMap.get("testid"));
		   Long userid = (Long)paraMap.get("userid");
		   Long paperid = typeVO.getPaperid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getPaperquesList();
		   if(queslist!=null&&queslist.size()>0){
			  Useranswer answerVO = null;
			  Paperques quesVO = null;
			  int[] randArr = null;
		      if(needRandom){
		    	  randArr = CollectionUtil.getRandomNum(queslist.size(), queslist.size());
		      }else{
		    	  randArr = CollectionUtil.getSequenceNum(queslist.size());
		      }
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Useranswer();
		    	  quesVO = (Paperques)queslist.get(randArr[i]);
		    	  answerVO.setPaperid(paperid);
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuesorder(randArr[i]);
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setQuestypeid(questypeid);
		    	  answerVO.setTestid(testid);
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
     * 配对题乱序，包括题目的选项
     */
	protected List<Useranswer> doPeiDui(Object paraObj1,Object paraObj2,Integer questype) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * 填空题乱序，包括题目的选项。
     */
	protected List<Useranswer> doTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		List<Useranswer> list = doPanDuan(paraObj1,paraObj2,questype);
		return list;
	}

	@Override
	protected List<Useranswer> doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Long questypeid = (Long)paraObj2;
		Map paraMap = (Map)paraObj1;
		boolean needRandom = getNeedRandom(paraMap);
		List<Useranswer> retList = new ArrayList<Useranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null)
		{
		   Paperquestype typeVO = (Paperquestype)paraMap.get("typeVO");
		   Long testid = (paraMap.get("testid")==null)?null:((Long)paraMap.get("testid"));
		   Long userid = (Long)paraMap.get("userid");
		   Long paperid = typeVO.getPaperid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getPaperquesList();
		   if(queslist!=null&&queslist.size()>0){
			  Useranswer answerVO = null;
			  StringBuffer itemorderstr=null;
			  Paperques quesVO = null;
			  int[] itemArr = null;
			  int[] randArr = null;
		      if(needRandom){
		    	  randArr = CollectionUtil.getRandomNum(queslist.size(), queslist.size());
		      }else{
		    	  randArr = CollectionUtil.getSequenceNum(queslist.size());
		      }
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Useranswer();
		    	  quesVO = (Paperques)queslist.get(randArr[i]);
		    	  answerVO.setPaperid(paperid);
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuesorder(randArr[i]);
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setQuestypeid(questypeid);
		    	  answerVO.setTestid(testid);
		    	  answerVO.setUserid(userid);
		    	  answerVO.setShopid(shopid);
		    	  answerVO.setQueschecktype(quesVO.getQueschecktype());
		    	  // 设置子题目
		    	  if(quesVO.getSubquesList()!=null&&quesVO.getSubquesList().size()>0){
		    		  List<Useranswer> subansList = new ArrayList<Useranswer>();
		    		  for(int j=0;j<quesVO.getSubquesList().size();j++){
		    			  Useranswer subanswerVO = new Useranswer();
		    			  Paperques subquesVO = (Paperques)quesVO.getSubquesList().get(j);
		    			  subanswerVO.setPaperid(paperid);
		    			  subanswerVO.setQuesid(subquesVO.getQuesid());
		    			  subanswerVO.setQuesorder(j);
		    			  subanswerVO.setQuestypeid(questypeid);
		    			  subanswerVO.setQuestype(QuestionConstant.QuesType_WanXingTianKong_Subques);
		    			  subanswerVO.setTestid(testid);
		    			  subanswerVO.setUserid(userid);
		    			  subanswerVO.setShopid(shopid);
		    			  subanswerVO.setPid(answerVO.getQuesid()); 
		    			  subanswerVO.setQueschecktype(QuestionConstant.Queschecktype_auto);
		    			  subanswerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
		    			  // 乱序选择题的答案选项
		    			  itemorderstr = new StringBuffer();
				    	  if(subquesVO.getItemList()!=null&&subquesVO.getItemList().size()>0){
				    		  if(needRandom){
				    			  itemArr = CollectionUtil.getRandomNum(subquesVO.getItemList().size(), subquesVO.getItemList().size());
						      }else{
						    	  itemArr = CollectionUtil.getSequenceNum(subquesVO.getItemList().size());
						      }
				    		  if(itemArr!=null&&itemArr.length>0){
				    			  for(int k=0;k<itemArr.length;k++){
				    				  itemorderstr.append(String.valueOf(itemArr[k])).append(",");
				    			  }
				    			  subanswerVO.setItemorderstr(itemorderstr.toString());
				    		  }
				    	  }
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
	protected List<Useranswer> doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		List<Useranswer> list = this.doPanDuan(paraObj1,paraObj2, questype);
		return list;
	}

	@Override
	protected List<Useranswer> doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		Long questypeid = (Long)paraObj2;
		Map paraMap = (Map)paraObj1;
		boolean needRandom = getNeedRandom(paraMap);
		List<Useranswer> retList = new ArrayList<Useranswer>();
		if(paraMap!=null&&paraMap.get("typeVO")!=null&&paraMap.get("userid")!=null)
		{
		   Paperquestype typeVO = (Paperquestype)paraMap.get("typeVO");
		   Long testid = (paraMap.get("testid")==null)?null:((Long)paraMap.get("testid"));
		   Long userid = (Long)paraMap.get("userid");
		   Long paperid = typeVO.getPaperid();
		   Long shopid = typeVO.getShopid();
		   List queslist = typeVO.getPaperquesList();
		   if(queslist!=null&&queslist.size()>0){
			  Useranswer answerVO = null;
			  StringBuffer itemorderstr = null;
			  Paperques quesVO = null;
			  int[] itemArr = null;
			  int[] randArr = null;
			  int[] randArr2 = null;
		      if(needRandom){
		    	  randArr = CollectionUtil.getRandomNum(queslist.size(), queslist.size());
		      }else{
		    	  randArr = CollectionUtil.getSequenceNum(queslist.size());
		      }
		      for(int i=0;i<randArr.length;i++){
		    	  answerVO = new Useranswer();
		    	  quesVO = (Paperques)queslist.get(randArr[i]);
		    	  answerVO.setPaperid(paperid);
		    	  answerVO.setQuesid(quesVO.getQuesid());
		    	  answerVO.setQuesorder(randArr[i]);
		    	  answerVO.setQuestype(questype);
		    	  answerVO.setQuestypeid(questypeid);
		    	  answerVO.setTestid(testid);
		    	  answerVO.setUserid(userid);
		    	  answerVO.setShopid(shopid);
		    	  answerVO.setQueschecktype(quesVO.getQueschecktype());
		    	  // 设置子题目
		    	  if(quesVO.getSubquesList()!=null&&quesVO.getSubquesList().size()>0){
		    		  List<Useranswer> subansList = new ArrayList<Useranswer>();
				      if(needRandom){
				    	  randArr2 = CollectionUtil.getRandomNum(quesVO.getSubquesList().size(), quesVO.getSubquesList().size());
				      }else{
				    	  randArr2 = CollectionUtil.getSequenceNum(quesVO.getSubquesList().size());
				      }
		    		  for(int j=0;j<randArr2.length;j++){
		    			  Useranswer subanswerVO = new Useranswer();
		    			  Question subquesVO = quesVO.getSubquesList().get(randArr2[j]);
		    			  subanswerVO.setPaperid(paperid);
		    			  subanswerVO.setQuesid(subquesVO.getQuesid());
		    			  subanswerVO.setQuesorder(randArr2[j]);
		    			  subanswerVO.setQuestypeid(questypeid);
		    			  subanswerVO.setQuestype(QuestionConstant.QuesType_YueDuLiJie_Subques);
		    			  subanswerVO.setTestid(testid);
		    			  subanswerVO.setUserid(userid);
		    			  subanswerVO.setShopid(shopid);
		    			  subanswerVO.setPid(answerVO.getQuesid());
		    			  subanswerVO.setQueschecktype(QuestionConstant.Queschecktype_auto);
		    			  subanswerVO.setExaminestatus(UseranswerConstant.ExamineStatus_UnJudge);
		    			  // 乱序选择题的答案选项
		    			  itemorderstr = new StringBuffer();
				    	  if(subquesVO.getItemList()!=null&&subquesVO.getItemList().size()>0){
				    		  if(needRandom){
				    			  itemArr = CollectionUtil.getRandomNum(subquesVO.getItemList().size(), subquesVO.getItemList().size());
						      }else{
						    	  itemArr = CollectionUtil.getSequenceNum(subquesVO.getItemList().size());
						      }
				    		  if(itemArr!=null&&itemArr.length>0){
				    			  for(int k=0;k<itemArr.length;k++){
				    				  itemorderstr.append(String.valueOf(itemArr[k])).append(",");
				    			  }
				    			  subanswerVO.setItemorderstr(itemorderstr.toString());
				    		  }
				    	  }
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
	
	protected List<Useranswer> doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return null;
	}
	
	private boolean getNeedRandom(Map paraMap){
		if(paraMap==null||paraMap.get("needRandom")==null)
			return true;
		int needRandom = ((Integer)paraMap.get("needRandom")).intValue();
		if(needRandom==0)
			return false;
		else
			return true;
	}

}
