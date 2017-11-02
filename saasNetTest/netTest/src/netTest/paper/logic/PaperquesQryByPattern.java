package netTest.paper.logic;

import java.util.List;

import netTest.common.logic.QuesTypeHandle;
import netTest.paper.dao.PaperquesDao;
import netTest.paper.dao.impl.PaperquesDaoImpl;
import netTest.paper.vo.Paperques;
import netTest.wareques.dao.QuesanswerDao;
import netTest.wareques.dao.QuestionDao;
import netTest.wareques.dao.QuestionitemDao;
import netTest.wareques.dao.impl.QuesanswerDaoImpl;
import netTest.wareques.dao.impl.QuestionDaoImpl;
import netTest.wareques.dao.impl.QuestionitemDaoImpl;
import netTest.wareques.logic.impl.QuestionLogicImpl;
import netTest.wareques.vo.Question;

public class PaperquesQryByPattern extends QuesTypeHandle {

	private PaperquesDao dao;
	private QuestionDao questionDao;
	private QuestionitemDao itemDao;
	private QuesanswerDao answerDao;
	
	public PaperquesQryByPattern(){
		this.dao = PaperquesDaoImpl.getInstance();
		this.itemDao = QuestionitemDaoImpl.getInstance();
		this.answerDao = QuesanswerDaoImpl.getInstance();
		this.questionDao = QuestionDaoImpl.getInstance();
	}
	
//	@Override
//	protected List doCaiLiao(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	protected List doDanXuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		List list = null;
		Long paperid = (Long)paraObj1;
		Long questypeid = (Long)paraObj2;
		list = dao.qryByPaperPatt(paperid, questypeid);
		if(list!=null && list.size()>0){
			Paperques voTemp = null;
			List itemList = null;
			for(int i=0;i<list.size();i++){
				voTemp = (Paperques)list.get(i);
				itemList = itemDao.selectByQues(voTemp.getQuesid(), voTemp.getShopid());
				voTemp.setItemList(itemList);
			}
		}
		return list;
	}

	@Override
	protected List doDuoXuan(Object paraObj1,Object paraObj2,Integer questype) {
		List list = this.doDanXuan(paraObj1,paraObj2, questype);
		return list;
	}

	@Override
	protected List doPanDuan(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		List list = null;
		Long paperid = (Long)paraObj1;
		Long questypeid = (Long)paraObj2;
		list = dao.qryByPaperPatt(paperid, questypeid);
		return list;
	}

//	@Override
//	protected List doPeiDui(Object paraObj1,Object paraObj2,Integer questype) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	protected List doTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		List list = this.doPanDuan(paraObj1,paraObj2, questype);
		return list;
	}

	@Override
	protected List doWanXingTianKong(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		List list = null;
		Long paperid = (Long)paraObj1;
		Long questypeid = (Long)paraObj2;
		list = dao.qryByPaperPatt(paperid, questypeid);
		if(list!=null&&list.size()>0){
			Paperques voTemp = null;
			List itemList = null;
			List subquesList = null;
			for(int i=0;i<list.size();i++){
				voTemp = (Paperques)list.get(i);
				itemList = itemDao.selectByQues(voTemp.getQuesid(), voTemp.getShopid());
				// 根据完形填空的选项构造该完形填空的子题目
				subquesList = QuestionLogicImpl.shapeSubquesList(itemList,voTemp,voTemp.getQuestypeid(),voTemp.getPaperquesscore(), 1);
				voTemp.setSubquesList(subquesList);
			}
		}
		return list;
	}

	@Override
	protected List doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		List list = this.doPanDuan(paraObj1,paraObj2, questype);
		return list;
	}

	@Override
	protected List doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		List list = null;
		Long paperid = (Long)paraObj1;
		Long questypeid = (Long)paraObj2;
		list = dao.qryByPaperPatt(paperid, questypeid);
		if(list!=null&&list.size()>0){
			Paperques paperquesVO = null;
			Question subVO = null;
			List itemList = null;
			List<Question> subquesList = null;
			for(int i=0;i<list.size();i++){
				paperquesVO = (Paperques)list.get(i);
				subquesList = dao.selectByPid(paperquesVO.getQuesid(), paperquesVO.getPaperquesscore());
				if(subquesList!=null && subquesList.size()>0){
					for(int j=0;j<subquesList.size();j++){
						subVO = subquesList.get(j);
						itemList = itemDao.selectByQues(subVO.getQuesid(), subVO.getShopid());
						subVO.setItemList(itemList);
						// set rowsitems
						if(subVO.getRowitems()==null){
							subVO.setRowitems(paperquesVO.getRowitems());
						}
					}
				}
				paperquesVO.setSubquesList(subquesList);
			}
		}
		return list;
	}
	
	protected List doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return null;
	}

}
