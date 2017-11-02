package netTest.paper.logic;

import commonTool.util.StringUtil;

import netTest.common.logic.QuesTypeHandle;
import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;
/**
 * 用于从试卷题目中抽取题目的答案，用于和用户的答案进行判断以比较是否正确
 * @author peter
 */
public class QuesAnswerGetHandle extends QuesTypeHandle {

	
	public QuesAnswerGetHandle(){}
	
//	@Override
//	protected Object doCaiLiao(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return "";
//	}

    /**
     * 
     */
	protected String doDanXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return "";
		Question quesVO = (Question)paraObj1;
		String answer = null;
		if(quesVO.getItemList()!=null&&quesVO.getItemList().size()>0){
			Questionitem itemVO = null;
			for(int i=0;i<quesVO.getItemList().size();i++){
				itemVO = (Questionitem)quesVO.getItemList().get(i);
				if(itemVO!=null&&QuestionConstant.IsRight_right.equals(itemVO.getIsright())){
					answer = itemVO.getQuesitemid().toString();
					break;
				}
			}
		}
		return answer;
	}

    /**
     * 多选题乱序，包括题目的选项
     */
	protected String doDuoXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return "";
		Question quesVO = (Question)paraObj1;
		String answer = "";
		if(quesVO.getItemList()!=null&&quesVO.getItemList().size()>0){
			Questionitem itemVO = null;
			for(int i=0;i<quesVO.getItemList().size();i++){
				itemVO = (Questionitem)quesVO.getItemList().get(i);
				if(itemVO!=null&&QuestionConstant.IsRight_right.equals(itemVO.getIsright())){
					answer = answer+","+itemVO.getQuesitemid().toString();
				}
			}
			if(answer!=null){
				answer = StringUtil.trimComma(answer);
			}
		}
		return answer;
	}

    /**
     * 
     */
	protected String doPanDuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return "";
		Question quesVO = (Question)paraObj1;
		String answer = "";
		answer = quesVO.getAnswersim();
		return answer;
	}

    /**
     * 
     */
	protected String doPeiDui(Object paraObj1,Object paraObj2,Integer questype) {
		// TODO Auto-generated method stub
		return "";
	}

    /**
     * 返回的填空题答案如：〖kong1〗〖kong2〗〖kong3〗
     */
	protected String doTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return "";
		Question quesVO = (Question)paraObj1;
		String answer = QueshowUtil.wrapUserAnswerTK(QueshowUtil.getQuesAnswerTK(quesVO.getQuestion()));
		return answer;
	}

	@Override
	protected String doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		return "";
	}

	@Override
	protected String doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return "";
		Question quesVO = (Question)paraObj1;
		String answer = "";
		if(quesVO.getAnswerVO()!=null){
			answer = quesVO.getAnswerVO().getAnswertext();
		}
		return answer;
	}

	@Override
	protected String doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		return "";
	}

	protected String doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return this.doDanXuan(paraObj1, paraObj2, questype);
	}
	
}
