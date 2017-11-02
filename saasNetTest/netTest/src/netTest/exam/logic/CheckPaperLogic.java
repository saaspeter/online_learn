package netTest.exam.logic;

import java.util.List;
import java.util.Map;

import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Useranswer;
import netTest.paper.vo.Paper;

public interface CheckPaperLogic {


	/**
	 * 自动判卷，通过对比试题和用户的答案来评卷。返回时试卷的总分
	 * @param paperVO
	 * @param usranwMap
	 * @return
	 */
    public Float checkUserpaper(Paper paperVO,Map<Long,Answerquestype> usranwMap,boolean updateDB);
    
    /**
     * 自动评阅一场考试的试卷，并且更新考生的分数和考试的阅卷状态
     * @param testid
     * @return
     */
    public Short autoCheckAllTestpaper(Long testid);
    
    /**
     * 保存单个试题考生评阅分数结果
     * @param testid
     * @param questype
     * @param quesscore
     * @param useranswerList
     * @return
     */
    public int saveCheckResult(Integer questype,float quesscore,List<Useranswer> useranswerList);
    
    /**
     * 统计考试的情况。如果所有试题都已经评阅完毕，则自动统计考试成绩情况
     * @param testid
     * @param paperid
     * @param shopid
     * @return 1:还存在没有评阅的试题 2:所有试题已经评阅完毕，正在进行考试成绩统计 3:考试评阅并统计完毕
     */
    public int satisTestinfo(Long testid,Long paperid,Testinfo testVO);
    

}
