package netTest.exercise.logic;

import java.util.List;

import netTest.exercise.vo.Exerques;
import netTest.wareques.logic.QuestionLogic;

public interface ExerquesLogic extends QuestionLogic{
    

	/**
	 * 根据试卷id和某种题型查找Paperques(从PaperQues表中查询)
	 * @param paperid
	 * @param questype
	 * @return
	 */
	public List qryExerquesByPattern(Long exerid, Integer questype);
	
	/**
	 * 删除练习题目,同时更新练习的版本号
	 * @param quesid
	 * @param questype
	 * @param shopid
	 * @return
	 */
	public boolean delExerques(Long exerid,Long exerquesid,Integer questype);
	
	/**
	 * 更换试卷中的题目
	 * @param vo:必要属性：exerid,exerquesid,quesscore,questype,shopid
	 * @param warequesid
	 * @return
	 */
	public Long changQues(Exerques vo,Long quesid);
	
	/**
	 * 新增试卷题目,是通过选择题库题目产生的动作
	 * @param vo:必要属性：paperid,quesid,paperquesscore,questype,shopid
	 * @param warequesidStr：选择的要新增的题目id串，以逗号隔开
	 * @return
	 */
	public int addSelExerques(Exerques vo,String quesidStr);

	
}
