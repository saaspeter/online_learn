package netTest.wareques.logic;

import java.util.List;

import netTest.wareques.dto.WareQuery;
import netTest.wareques.vo.Ware;

import commonTool.base.Page;
import commonTool.exception.HasReferenceException;
import commonTool.exception.LogicException;

public interface WareLogic {

	
	/**
     * 为操作员查询题库准备的函数
     */
    public List qryWare(WareQuery queryVO);
    
    /**
     * 为操作员查询题库准备的分页函数，设定了开放类型为对所有人开放的题库
     * @param queryVO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Page qryWarePage(WareQuery queryVO,int pageIndex,int pageSize,Integer total);
    	
	/**
	 * 插入题库
	 * @param vo
	 * @return
	 */
	public Long insertWare(Ware vo);
	
	/**
	 * 更新题库
	 * @param vo
	 * @return
	 */
	public boolean updateWareByPk(Ware vo);
	
//	/**
//	 * 更新题目题目数，在原有题目数量上增加或减少
//	 * @param wareid
//	 * @param number
//	 */
//	public void updateQuesNumber(Long wareid, Long number);
	
	/**
	 * 插入或更新题库
	 * @param vo
	 * @return
	 */
	public Long saveWare(Ware vo);
		
	/**
	 * 删除表Ware,如果题库中还有题目，则抛出异常
	 * @param keys
	 * @return
	 */
	public int delWare(Long wareid) throws LogicException;
	
	/**
	 * 根据产品删除题库，包括题库中的题目
	 * @param productid
	 * @param shopid
	 * @return
	 * @throws LogicException
	 */
	public int delWareByProd(Long productid, Long shopid) throws LogicException;
		
}
