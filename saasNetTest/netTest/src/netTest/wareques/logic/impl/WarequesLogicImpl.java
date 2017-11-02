package netTest.wareques.logic.impl;

import netTest.bean.BeanFactory;
import netTest.wareques.dao.QuestionDao;
import netTest.wareques.dto.QuestionQuery;
import netTest.wareques.logic.WarequesLogic;

import commonTool.base.Page;

public class WarequesLogicImpl extends QuestionLogicImpl implements WarequesLogic{

	private QuestionDao quesdao;
	
	/**
	 * 操作员查询题库题目，带分页
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page qryWarequesPage(QuestionQuery queryVO,int pageIndex,int pageSize,Integer total){
		Page page = Page.EMPTY_PAGE;
		if(queryVO==null||queryVO.getShopid()==null||queryVO.getProductbaseid()==null)
			return page;
		if(queryVO.getComptype()==null){
		   queryVO.setQrywholeques(true);
		}
		page =  quesdao.qryPage(queryVO, pageIndex, pageSize, total);
		return page;
	}
	
	/**
	 * 根据product删除题库题目，不删除product下的题目，这个由删除product时删除
	 * @param productid
	 * @param shopid
	 */
    public void delQuesByProd(Long productid, Long shopid){
    	// 删除问题，包括questionitem, quesanswer, question
    	quesdao.deleteByProd(productid); 
    }
	
	/**
	 * static spring getMethod
	 */
	 public static WarequesLogic getInstance(){
		 WarequesLogic logic = (WarequesLogic)BeanFactory.getBeanFactory().getBean("warequesLogic");
	     return logic;
	 }


	public QuestionDao getQuesdao() {
		return quesdao;
	}

	public void setQuesdao(QuestionDao quesdao) {
		this.quesdao = quesdao;
	}
	
}
