package netTest.paper.logic;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTest.wareques.vo.Question;

public interface PaperquesLogic {
	
	/**
	 * 根据试卷id和某种题型查找Paperques(从PaperQues表中查询)
	 * @param paperid
	 * @param questype
	 * @return
	 */
	public List qryPaperquesByPattern(Long paperid,Long questypeid,Integer questype);
	
	/**
	 * 删除试卷题目
	 * @param quesid
	 * @param questype
	 * @param shopid
	 * @return
	 */
	public boolean delPaperques(Long paperid,Long quesid,Long questypeid,Integer questype,Float paperquesscore,Integer quesoptnum,Long shopid);
	
	/**
	 * 更换试卷中的题目
	 * @param vo:必要属性：paperid,quesid,paperquesscore,questype,shopid,questypeid
	 * @param warequesid
	 * @return
	 */
	public Long changQues(Long paperid,Paperques vo,Long warequesid);
	
	/**
	 * 新增试卷题目,是通过选择题库题目产生的动作
	 * @param vo:必要属性：paperid,quesid,paperquesscore,questype,shopid
	 * @param warequesidStr：选择的要新增的题目id串，以逗号隔开
	 * @return
	 */
	public int addSelPaperques(Long paperid,Paperques vo,String warequesidStr);
    
	/**
	 * 从txt文件中导入试卷中的试题
	 * @param input
	 * @param wareid
	 * @param shopid
	 * @param productid
	 * @return 返回导入后的错误信息list, 最后一个元素是导入的成功数目
	 * @throws Exception
	 */
	public List<String> importPaperQuesFromTxt(Paper paperVO, InputStream input, Long wareid);
	
	/**
	 * 刷新试卷cache, 同时更新试卷分数
	 * @return
	 */
	public void flushPaperByQues(Collection<Paper> paperlist, Long quesid, 
			 	 				 Integer changeoptnum);
	
}
