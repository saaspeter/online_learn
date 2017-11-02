package netTest.paper.logic;

import java.util.List;

import netTest.paper.dto.PaperQuery;
import netTest.paper.vo.Paper;

import commonTool.base.Page;

public interface PaperLogic {

	/**
	 * 列出有权限看到的试卷，暂时只是能看到自己单位创建的试卷
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page listPaperPage(PaperQuery queryVO,int pageIndex,int pageSize,Integer total);
	
	/**
	 * 查询一份试卷，包括其中的题目
	 * @param paperid
	 * @param questyepUse: 需要查询的题型。
	 *        如果为空，则取试卷的第一种题型，如果为-1，则查询全部题型
	 * @return
	 */
	public Paper qryPaperquesPattern(Long paperid,Long questypeidUse);
	
	/**
	 * 查询一份试卷，包括其中的题目
	 * @param paperid
	 * @return
	 */
	public Paper qryPaperWhole(Long paperid);

	/**
	 * 生成试卷，先保存试卷(包括配置)，然后返回生成后的试卷
	 * @param vo
	 * @param createtype 是否自动生成试卷，0代表只创建试卷不自动选题，1代表要自动选题生成试卷
	 * @param questypeList
	 * @param contcateList
	 * @param diffList
	 * @return
	 */
	public abstract Long genePaper(Paper vo, int createtype, List questypeList,
								   List contcateList, List diffList);
		
	/**
	 * 删除试卷及试卷设置，包括试卷题目
	 * @param paperid
	 * @return
	 */
	public abstract int delWholePaper(Long paperid, Paper vo);
	
	/**
	 * 根据product删除所有的paper, 用于删除产品时的被调用
	 * @param productid
	 * @param shopid
	 */
	public void deletePaperByProd(Long productid, Long shopid);
		
}