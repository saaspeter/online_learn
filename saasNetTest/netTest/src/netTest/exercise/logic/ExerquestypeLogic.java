package netTest.exercise.logic;

import netTest.exercise.vo.Exerquestype;


public interface ExerquestypeLogic {

	/**
	 * 保存试卷题型配置表
	 * @param typeVO
	 * @return
	 */
	public Long save(Exerquestype typeVO);
	
	/**
	 * 删除试卷题型，如果该题型下还有题目的话抛异常
	 * @param patternid
	 * @return
	 */
	public int delete(Long patternid);
	
	/**
	 * 判断试卷题型中包含哪种类型的题型
	 * @param paperid
	 * @return 1:客观题；2：主观题；3：客观主观题都有
	 */
	public int exerHasQuestype(Long exerid);

}