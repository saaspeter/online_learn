package netTest.wareques.logic;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import netTest.wareques.vo.Question;
import netTest.wareques.vo.Questionitem;

public interface QuestionLogic {


	
	/**
	 * 新增题目，总的入口，会根据不同的题目类型做相应的处理
	 * @param vo
	 * @return
	 */
	public Long addQues(Question vo);
	
	/**
	 * 更新题目，所有类型的题目的入口
	 * @param vo
	 * @param delItemStr：对于选择题时有用，其余类型的题目可以留空
	 * @return
	 */
	public Question updateQues(Question vo,String delItemStr);
		
	/**
	 * 查询单个题目
	 * @param quesid
	 * @param shopid
	 * @return
	 */
	public Question qryQuesByPk(Long quesid,Long shopid);
	
	/**
	 * 删除一个题库题目，包括题目的文件
	 * @param quesid
	 * @param quesKind 问题的种类：题库题目还是试卷题目
	 * @return
	 */
	public boolean delSingleQues(Long quesid,Integer questype,Long shopid,Long pid);
	
	/**
	 * 删除多个题目，包括删除复合题目的子题目
	 * 也可以删除多个子题目，同时处理这些子题目的父题目的一些操作
	 * @param quesArr:二维数组，[0]代表主键quesid，[1]代表题目类型
	 * @param pid: 子题目的父题目，如果是独立的题目，则传入空即可
	 * @return
	 */
	public int delQuesBatch(String[][] quesArr,Long pid,Long shopid,Long productid);
		
	/**
	 * 删除完形填空题的子题目
	 * @param quesArr:二维数组，[0]代表主键quesid，[1]代表题目类型
	 * @return
	 */
	public int delSubQues_WXTK(Long quesid,Integer disorder);
	
    /**
     * 更换两个题目的题目选项的disorder
     * @param vo
     * @param type:1代表向上移动(disorder变小)，2代表向下移动
     * @return
     */
    public boolean switDisorderQues(Questionitem vo,int type);
	
    /**
     * 根据子题目选项id和显示序号查询比指定disorder略大的显示序号
     * 如果disorder为空，则查询所属的quesid下的子选项中最大的显示号disorder
     * @param quesid
     * @param disorder
     * @return
     */
    public Integer qryLargerDisorder(Long quesid,Integer disorder);
    
	/**
	 * 导入excel试题
	 * @param input
	 * @param wareid
	 * @param shopid
	 * @param productid
	 * @return
	 * @throws Exception
	 */
	public List<String> importQuesFromExcel(InputStream input,Long wareid, Long shopid, 
			Long productid, Long creatorID, Locale locale);
    
	/**
	 * 从txt文件中导入试题
	 * @param input
	 * @param wareid
	 * @param shopid
	 * @param productid
	 * @return 返回导入后的错误信息list, 最后一个元素是导入的成功数目
	 * @throws Exception
	 */
	public List<String> importQuesFromTxt(InputStream input,Long wareid, Long shopid, 
			Long productid, Long creatorID);
	
	/**
	 * 更新题库题目
	 * @param wareid
	 * @param number
	 */
	public void updateWareQuesNumber(Long wareid, Long productid, int number);
	
}
