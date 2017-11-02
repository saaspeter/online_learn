package netTest.exam.logic;

import java.util.Map;

import netTest.exam.vo.Answerquestype;
import netTest.paper.vo.Paper;

public interface ExampaperLogic {

	
    /**
     * 得到试卷题目，并且得到试卷问题的答案，放在Paper中。
     * 此方法会缓存TODO
     * @param paperid
     * @param answertype: 答案类型，1是为评阅单个考生的试卷而设置的（用户自测阅卷）。2是批量评阅考生答卷设置的(自动批量阅卷)。
     *                            3是为手动阅卷准备的试卷及答案(去除所有客观题，以节省内存)
     * @return
     */
	public Paper getTestPaperAnswer(Long paperid,int answertype);
	
	/**
	 * 查询用户的答案列表，并做分页处理。
	 * 从UserAnswer表中查询数据
	 * @param testid
	 * @param userid
	 * @param paperid
	 * @param shopid
	 * @param paperVO
	 * @param needRandom 试卷中的试题是否需要乱序。0不乱序（即对新生成的答案要按照题目顺序组织，如果读取表中的值，按照quesorder排列）
	 * 					 1 乱序（即对新生成的答案要随机题目顺序，对于读取用户答案表中的值，则直接读取即可）
	 * @return
	 */
	public Map<Long,Answerquestype> qryTestUseranswer(Long testid,Long userid,Long paperid,Paper paperVO,int needRandom);
	
	/**
	 * 根据试卷试题生成用户的用户答案列表，并乱序处理。
	 * 从UserAnswer表中查询数据,如果没有查询到则需要生成该份试卷的答案列表
	 * @param testid
	 * @param userid
	 * @param paperid
	 * @param shopid
	 * @param paperVO
	 * @param needRandom 试卷中的试题是否需要乱序。0不乱序（即对新生成的答案要按照题目顺序组织，如果读取表中的值，按照quesorder排列）
	 * 					 1 乱序（即对新生成的答案要随机题目顺序，对于读取用户答案表中的值，则直接读取即可）
	 * @return
	 */
	public Map<Long,Answerquestype> initTestUseranswer(Long testid,Long userid,Long paperid,Long shopid,Paper paperVO,int needRandom);
	

}