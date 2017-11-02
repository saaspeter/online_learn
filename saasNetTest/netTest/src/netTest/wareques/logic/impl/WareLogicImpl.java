package netTest.wareques.logic.impl;

import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.event.EventHandlerNetTest;
import netTest.exception.ExceptionConstant;
import netTest.wareques.dao.WareDao;
import netTest.wareques.dto.WareQuery;
import netTest.wareques.logic.WareLogic;
import netTest.wareques.logic.WarequesLogic;
import netTest.wareques.vo.Ware;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.util.DateUtil;

public class WareLogicImpl implements WareLogic, EventHandle{

	private WareDao dao;
	
	private WarequesLogic warequesLogic;
		
	/**
     * 为操作员查询题库准备的函数
     */
    public List qryWare(WareQuery queryVO){
    	List list = null;
    	if(queryVO!=null){
    		list = dao.selectByVO(queryVO);
    	}
    	return list;
    }
    
    /**
     * 为操作员查询题库准备的分页函数，设定了开放类型为对所有人开放的题库
     * @param queryVO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Page qryWarePage(WareQuery queryVO,int pageIndex,int pageSize,Integer total){
    	Page page = null;
    	if(queryVO==null||queryVO.getShopid()==null)
    		page = Page.EMPTY_PAGE;
    	else{
    		page = dao.selectByVOPage(queryVO, pageIndex, pageSize, total);
    	}
    	return page;
    }
    
	
	/**
	 * 插入题库
	 * @param vo
	 * @return
	 */
	public Long insertWare(Ware vo){
		if(vo==null||vo.getShopid()==null||vo.getProductbaseid()==null)
			throw new NeedParamsException("In WareLogicImpl:insertWare:need shopid and productbaseid and waretype");
		vo.setWarecreatetime(DateUtil.getInstance().getNowtime_GLNZ());
	    vo.setWarequesnum(new Long("0"));
		Long pk = dao.insert(vo);
		return pk;
	}
	
	/**
	 * 更新题库
	 * @param vo
	 * @return
	 */
	public boolean updateWareByPk(Ware vo){
		if(vo==null||vo.getWareid()==null)
			throw new NeedParamsException("In WareLogicImpl:updateWareByPk");
		boolean ret = true;
		// 检查单位orgbaseid是否有权限操作该题库
		int rows = dao.updateByPK(vo);
		if(rows<1)
			ret = false;
		return ret;
	}
	
	/**
	 * 插入或更新题库
	 * @param vo
	 * @return
	 */
	public Long saveWare(Ware vo){
		if(vo==null||vo.getShopid()==null||vo.getProductbaseid()==null)
			throw new NeedParamsException("In WareLogicImpl:saveWare");
		Long pk = null;
		if(vo.getWareid()==null){
			vo.setStatus(CommonConstant.Status_valide);
			pk = this.insertWare(vo);
		}else{
			this.updateWareByPk(vo);
			pk = vo.getWareid();
		}
		return pk;
	}
	
	/**
	 * 删除表Ware,如果题库中还有题目，则抛出异常
	 * @param keys
	 * @return
	 */
	public int delWare(Long wareid) throws LogicException{
		if(wareid==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		int rtn = 0;

		// 检查单位orgbaseid是否有权限操作该题库
		Ware wareVO = dao.selectByPK(wareid);
		if(wareVO.getWarequesnum()>0)
			throw new LogicException(ExceptionConstant.Error_Record_BeenReference);
		rtn = dao.delWareByPK(wareid);

		return rtn;
	}
	
	/**
	 * 根据产品删除题库，包括题库中的题目
	 * @param productid
	 * @param shopid
	 * @return
	 * @throws LogicException
	 */
	public int delWareByProd(Long productid, Long shopid) throws LogicException{
		if(productid==null || shopid==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		int rtn = 0;

		// 删除题库题目，包括题目文件
		warequesLogic.delQuesByProd(productid, shopid);
		// 删除题库
		rtn = dao.delWareByProd(productid);
		return rtn;
	}
	
	/**
	 * 更新题目题目数，在原有题目数量上增加或减少
	 * @param wareid
	 * @param number
	 */
	private void updateQuesNumber(Long wareid, Long number){
		dao.updateQuesNumByPK(wareid, number);
	}
	
	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String quesidStr = (String)paraMap.get("quesidStr");
		Long wareid = new Long(paraMap.get("wareid").toString());
		Long number = new Long(paraMap.get("quesnumber").toString());
		String eventType = event.getEventType();
		if(EventHandlerNetTest.EventType_Question_ImportQues.equals(eventType)){
			updateQuesNumber(wareid, number);
		}
		
	}
	
	
	/**
	 * static spring getMethod
	 */
	 public static WareLogic getInstance(){
		 WareLogic logic = (WareLogic)BeanFactory.getBeanFactory().getBean("wareLogic");
	     return logic;
	 }

	public WareDao getDao() {
		return dao;
	}

	public void setDao(WareDao dao) {
		this.dao = dao;
	}

	public WarequesLogic getWarequesLogic() {
		return warequesLogic;
	}

	public void setWarequesLogic(WarequesLogic warequesLogic) {
		this.warequesLogic = warequesLogic;
	}
	
}
