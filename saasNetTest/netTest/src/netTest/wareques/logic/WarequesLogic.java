package netTest.wareques.logic;

import netTest.wareques.dto.QuestionQuery;

import commonTool.base.Page;

public interface WarequesLogic extends QuestionLogic{

	/**
	 * 操作员查询题库题目，带分页
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page qryWarequesPage(QuestionQuery queryVO,int pageIndex,int pageSize,Integer total);
	
	/**
	 * 根据product删除题库题目
	 * @param productid
	 * @param shopid
	 */
    public void delQuesByProd(Long productid, Long shopid);
	
}
