package netTest.exam.logic.impl;

import java.util.Map;
import netTest.common.logic.QuesTypeHandle;
import netTest.exam.constant.UseranswerConstant;
import netTest.exam.vo.Useranswer;
import netTest.paper.vo.Paperques;
import netTest.util.QueshowUtil;
import netTest.wareques.constant.QuestionConstant;
import commonTool.util.StringUtil;
/**
 * 用于判断用户的答案是否正确，如果正确，则设置用户答案中的正确性和分数
 * @author peter
 */
public class JudgePaperUseranswerHandle extends QuesTypeHandle {

	private static JudgePaperUseranswerHandle instance;
	
	private JudgePaperUseranswerHandle(){}
    
    public static JudgePaperUseranswerHandle getInstance(){     
        if(instance==null){
        	synchronized(JudgePaperUseranswerHandle.class){
        		if(instance==null){
        			instance = new JudgePaperUseranswerHandle();
        		}
        	}
        }
        return instance;
    } 
	
	
	@Override
//	protected Float doCaiLiao(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return 0f;
//	}

    /**
     * 判断答题是否正确
     */
	protected Float doDanXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return 0f;
		Paperques quesVO = (Paperques)paraObj1;
		Useranswer answerVO = (Useranswer)paraObj2;
		String useranswer = answerVO.getAnswer();
		if(useranswer==null||"".equals(useranswer)){
			useranswer = answerVO.getAnswerByType(questype);
			answerVO.setAnswer(useranswer);
		}
		float score = 0f;
		if(useranswer!=null&&useranswer.equals(quesVO.getAnswer())){
			answerVO.setIsright(QuestionConstant.IsRight_right.toString());
			if(quesVO.getPaperquesscore()!=null){
			   score = quesVO.getPaperquesscore();
			}
			answerVO.setAnswerscore(score);
		}else{
			answerVO.setIsright(QuestionConstant.IsRight_wrong.toString());
			answerVO.setAnswerscore(0f);
		}
		answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
		return score;
	}

    /**
     * 判断答题是否正确
     */
	protected Float doDuoXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return 0f;
		boolean zeroFlag = true;
		Paperques quesVO = (Paperques)paraObj1;
		Useranswer answerVO = (Useranswer)paraObj2;
		float score = 0f;
		String quesanswer = quesVO.getAnswer();
		String useranswer = answerVO.getAnswer();
		if(useranswer==null||"".equals(useranswer)){
			useranswer = answerVO.getAnswerByType(questype);
			answerVO.setAnswer(useranswer);
		}
		if(quesanswer!=null&&useranswer!=null&&quesanswer.trim().length()>0&&useranswer.trim().length()>0){
			
			int quesanswerLeng = StringUtil.splitString(quesanswer, ",").length;
			String[] answerArr = StringUtil.splitString(useranswer, ",");
			if(answerArr!=null&&answerArr.length>0
					&&answerArr.length==quesanswerLeng){ // 要判断正确答案的选项个数和用户答案的选项个数是否相同
				int count = 0;
				for(int i=0;i<answerArr.length;i++){
					if(!StringUtil.includeStr(quesanswer, answerArr[i], null)){
						break;
					}
					count++;
				}
				if(count==answerArr.length){
					answerVO.setIsright(QuestionConstant.IsRight_right.toString());
					if(quesVO.getPaperquesscore()!=null){
					   score = quesVO.getPaperquesscore();
					}
					answerVO.setAnswerscore(score);
					zeroFlag = false;
				}
			}
		}
		if(zeroFlag){ // 设置用户答案分数为0
			answerVO.setIsright(QuestionConstant.IsRight_wrong.toString());
			answerVO.setAnswerscore(0f);
		}
		answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
		return score;
	}

    /**
     * 判断答题是否正确
     */
	protected Float doPanDuan(Object paraObj1,Object paraObj2,Integer questype) {
		return this.doDanXuan(paraObj1, paraObj2, questype);
	}

    /**
     * 判断答题是否正确
     */
	protected Float doPeiDui(Object paraObj1,Object paraObj2,Integer questype) {
		// TODO Auto-generated method stub
		return 0f;
	}

    /**
     * 判断答题是否正确
     */
	protected Float doTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return 0f;
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		Paperques quesVO = (Paperques)paraObj1;
		Useranswer answerVO = (Useranswer)paraObj2;
		if(QuestionConstant.Queschecktype_manual.equals(quesVO.getQueschecktype()))
			return 0f;
		String quesanswer = quesVO.getAnswer();
		String useranswer = answerVO.getAnswer();
		if(useranswer==null||"".equals(useranswer)){
			useranswer = answerVO.getAnswerByType(questype);
			answerVO.setAnswer(useranswer);
		}
		float quesscore = (quesVO.getPaperquesscore()==null)?0f:quesVO.getPaperquesscore();
		if(quesanswer!=null&&useranswer!=null){
			String[] quesanswerArr = QueshowUtil.getQuesAnswerTK(quesanswer);
			String[] useranswerArr = QueshowUtil.getQuesAnswerTK(useranswer);
			if(quesanswerArr!=null&&quesanswerArr.length>0&&useranswerArr!=null&&useranswerArr.length>0){
				for(int i=0;i<quesanswerArr.length;i++){
					if(i<useranswerArr.length){
						if(quesanswerArr[i]==null)
							quesanswerArr[i] = "";
						else
							quesanswerArr[i] = quesanswerArr[i].trim();
						if(useranswerArr[i]==null)
							useranswerArr[i] = "";
						else
							useranswerArr[i] = useranswerArr[i].trim();
						if(quesanswerArr[i].equals(useranswerArr[i])){
							buffer.append(QuestionConstant.IsRight_right.toString());
							count++;
						}else{
							buffer.append(QuestionConstant.IsRight_wrong.toString());
						}
					}else
						buffer.append(QuestionConstant.IsRight_wrong.toString());
				}
			}
		}
		answerVO.setIsright(buffer.toString());
		answerVO.setAnswerscore(count*quesscore);
		answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_PreJudge); // 预处理
		return answerVO.getAnswerscore();
	}

	@Override
	protected Float doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return 0f;
		Paperques quesVO = (Paperques)paraObj1;
		Useranswer answerVO = (Useranswer)paraObj2;
		Map subquesMap = quesVO.getSubquesMap();
		
		float totalScore = 0f;
		Float tempScore = null;
		if(answerVO.getSubanswList()!=null&&answerVO.getSubanswList().size()>0&&subquesMap!=null){
			Useranswer subanswerVO = null;
			for(int i=0;i<answerVO.getSubanswList().size();i++){
				subanswerVO = answerVO.getSubanswList().get(i);
				if(subanswerVO!=null&&subquesMap.get(subanswerVO.getQuesid())!=null){
					tempScore = this.doDanXuan(subquesMap.get(subanswerVO.getQuesid()), subanswerVO, subanswerVO.getQuestype());
				    if(tempScore!=null)
				    	totalScore += tempScore.floatValue();
				}
			}
		}
		answerVO.setAnswerscore(totalScore);
		answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
		return totalScore;
	}

	@Override
	protected Float doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return 0f;
		Paperques quesVO = (Paperques)paraObj1;
		Useranswer answerVO = (Useranswer)paraObj2;
		float score = 0f;
		if(QuestionConstant.Queschecktype_manual.equals(quesVO.getQueschecktype()))
			return 0f;
		// 以下代码对于问答题是不会执行的
		if(answerVO.getAnswer()!=null&&answerVO.getAnswer().equals(quesVO.getAnswer())){
			answerVO.setIsright(QuestionConstant.IsRight_right.toString());
			if(quesVO.getPaperquesscore()!=null){
				score = quesVO.getPaperquesscore();
			}
			answerVO.setAnswerscore(score);
		}else{
			answerVO.setIsright(QuestionConstant.IsRight_wrong.toString());
			answerVO.setAnswerscore(0f);
		}
		answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
		return answerVO.getAnswerscore();
	}

	@Override
	protected Float doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null||paraObj2==null)
			return 0f;
		Paperques quesVO = (Paperques)paraObj1;
		Useranswer answerVO = (Useranswer)paraObj2;
		Map subquesMap = quesVO.getSubquesMap();
		
		float totalScore = 0f;
		Float tempScore = null;
		if(answerVO.getSubanswList()!=null&&answerVO.getSubanswList().size()>0&&subquesMap!=null){
			Useranswer subanswerVO = null;
			for(int i=0;i<answerVO.getSubanswList().size();i++){
				subanswerVO = answerVO.getSubanswList().get(i);
				if(subanswerVO!=null&&subquesMap.get(subanswerVO.getQuesid())!=null){
					tempScore = this.doDuoXuan(subquesMap.get(subanswerVO.getQuesid()), subanswerVO, subanswerVO.getQuestype());
				    if(tempScore!=null)
				    	totalScore += tempScore.floatValue();
				}
			}
		}
		answerVO.setAnswerscore(totalScore);
		answerVO.setExaminestatus(UseranswerConstant.ExamineStatus_DidJudge);
		return totalScore;
	}
	
	protected Float doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return this.doDanXuan(paraObj1, paraObj2, questype);
	}
	
}
