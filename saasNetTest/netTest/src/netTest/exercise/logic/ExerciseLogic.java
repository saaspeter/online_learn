package netTest.exercise.logic;

import java.util.Map;

import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerques;


public interface ExerciseLogic {

	/**
	 * 查询一份练习，包括其中的所有题目，题目集合在Exercise对象中
	 * 而questype对象中不包含题目
	 * @param paperid
	 * @return
	 */
	public Exercise qryAllExerQues(Long exerid);

	
	/**
	 * 查询一份试卷中某种题型的题目集合
	 * @param exerid
	 * @param questyepUse: 需要查询的题型。
	 *        如果为空，则取试卷的第一种题型，如果为-1，则查询全部题型
	 * @return
	 */
	public Exercise qryExerquesPattern(Long exerid, Integer questypeUse);
	
	/**
	 * 新增练习，增加练习及练习题型信息
	 * @param vo
	 * @param questypeArr: 练习中包含的题型的数组
	 */
	public Exercise addExercise(Exercise vo, String[] questypeArr);
	
	/**
	 * 删除整个练习，包括练习题目和练习的考生考试结果
	 * @param quizid
	 * @return
	 */
	public boolean delWholeExercise(Long quizid, Exercise vo, boolean sendnotification);
	
	/**
	 * 根据product删除所有的exercise, 用于删除产品时的被调用
	 * @param productid
	 * @param shopid
	 */
	public void deleteExerByProd(Long productid, Long shopid);
	
}
