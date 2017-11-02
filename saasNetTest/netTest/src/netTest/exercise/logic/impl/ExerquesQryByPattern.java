package netTest.exercise.logic.impl;

import java.util.List;
import java.util.Map;

import netTest.common.logic.QuesTypeHandle;
import netTest.exercise.dao.ExerquesDao;
import netTest.exercise.dao.impl.ExerquesDaoImpl;
import netTest.exercise.vo.Exerques;
import netTest.wareques.dao.QuesanswerDao;
import netTest.wareques.dao.QuestionDao;
import netTest.wareques.dao.QuestionitemDao;
import netTest.wareques.dao.impl.QuesanswerDaoImpl;
import netTest.wareques.dao.impl.QuestionDaoImpl;
import netTest.wareques.dao.impl.QuestionitemDaoImpl;
import netTest.wareques.logic.impl.QuestionLogicImpl;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;

public class ExerquesQryByPattern extends QuesTypeHandle {

	private ExerquesDao exerquesDao;
	private QuestionDao quesDao;
	private QuestionitemDao itemDao;
	private QuesanswerDao answerDao;
	
	public ExerquesQryByPattern(){
		this.exerquesDao = ExerquesDaoImpl.getInstance();
		this.quesDao = QuestionDaoImpl.getInstance();
		this.itemDao = QuestionitemDaoImpl.getInstance();
		this.answerDao = QuesanswerDaoImpl.getInstance();
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
		Long exerid = (Long)paraObj1;
		list = exerquesDao.qryQuesByPatt(exerid, questype);
		
		if(list!=null&&list.size()>0){
			StringBuffer quesidBuffer = new StringBuffer();
			Exerques voTemp = null;
			List itemList = null;
			for(int i=0;i<list.size();i++){
				voTemp = (Exerques)list.get(i);
				quesidBuffer.append(voTemp.getQuesid().toString()).append(",");
				itemList = itemDao.selectByQues(voTemp.getQuesid(), voTemp.getShopid());
				voTemp.setItemList(itemList);
			}
			String quesidStr = quesidBuffer.substring(0, quesidBuffer.length()-1);
			Map<Long, Quesanswer> rtnMap = answerDao.selectByPkStr(quesidStr);
			Quesanswer answerTemp = null;
			for(int i=0; i<list.size(); i++){
				voTemp = (Exerques)list.get(i);
				answerTemp = rtnMap.get(voTemp.getQuesid());
				voTemp.setAnswerVO(answerTemp);
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
		Long exerid = (Long)paraObj1;
		list = exerquesDao.qryQuesByPatt(exerid, questype);
		Exerques voTemp = null;
		if(list!=null&&list.size()>0){
			StringBuffer quesidBuffer = new StringBuffer();
			for(int i=0;i<list.size();i++){
				voTemp = (Exerques)list.get(i);
				quesidBuffer.append(voTemp.getQuesid().toString()).append(",");
			}
			String quesidStr = quesidBuffer.substring(0, quesidBuffer.length()-1);
			Map<Long, Quesanswer> rtnMap = answerDao.selectByPkStr(quesidStr);
			Quesanswer answerTemp = null;
			for(int i=0; i<list.size(); i++){
				voTemp = (Exerques)list.get(i);
				answerTemp = rtnMap.get(voTemp.getQuesid());
				voTemp.setAnswerVO(answerTemp);
			}
		}
		
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
		Long exerid = (Long)paraObj1;
		list = exerquesDao.qryQuesByPatt(exerid, questype);
		if(list!=null&&list.size()>0){
			Exerques voTemp = null;
			List itemList = null;
			List subquesList = null;
			StringBuffer quesidBuffer = new StringBuffer();
			for(int i=0;i<list.size();i++){
				voTemp = (Exerques)list.get(i);
				quesidBuffer.append(voTemp.getQuesid().toString()).append(",");
				
				itemList = itemDao.selectByQues(voTemp.getQuesid(), voTemp.getShopid());
				// 根据完形填空的选项构造该完形填空的子题目
				subquesList = QuestionLogicImpl.shapeSubquesList(itemList,voTemp,voTemp.getQuestypeid(),voTemp.getQuesscore(),0);
				voTemp.setSubquesList(subquesList);
			}
			
			String quesidStr = quesidBuffer.substring(0, quesidBuffer.length()-1);
			Map<Long, Quesanswer> rtnMap = answerDao.selectByPkStr(quesidStr);
			Quesanswer answerTemp = null;
			for(int i=0; i<list.size(); i++){
				voTemp = (Exerques)list.get(i);
				answerTemp = rtnMap.get(voTemp.getQuesid());
				voTemp.setAnswerVO(answerTemp);
			}
		}
		return list;
	}

	@Override
	protected List doWenDa(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		List list = null;
		Long exerid = (Long)paraObj1;
		list = exerquesDao.qryQuesByPatt(exerid, questype);
		if(list.size()>0){
			StringBuffer buffer = new StringBuffer();
			Exerques vo = null;
			for(int i=0; i<list.size(); i++){
				vo = (Exerques)list.get(i);
				buffer.append(vo.getQuesid()).append(",");
			}
			Map<Long, Quesanswer> rtnMap = answerDao.selectByPkStr(buffer.toString());
			Quesanswer answerTemp = null;
			for(int i=0; i<list.size(); i++){
				vo = (Exerques)list.get(i);
				answerTemp = rtnMap.get(vo.getQuesid());
				vo.setAnswerVO(answerTemp);
			}
		}
		
		return list;
	}

	@Override
	protected List doYueDuLiJie(Object paraObj1,Object paraObj2,Integer questype) {
		if(paraObj1==null)
			return null;
		List list = null;
		Long exerid = (Long)paraObj1;
		list = exerquesDao.qryQuesByPatt(exerid, questype);
		if(list!=null&&list.size()>0){
			Exerques voTemp = null;
			Question subVO = null;
			List itemList = null;
			List subquesList = null;
			StringBuffer quesidBuffer = new StringBuffer();
			for(int i=0;i<list.size();i++){
				voTemp = (Exerques)list.get(i);
				quesidBuffer.append(voTemp.getQuesid()).append(",");
				subquesList = quesDao.selectByPid(voTemp.getQuesid());
				if(subquesList!=null&&subquesList.size()>0){
					for(int j=0;j<subquesList.size();j++){
						subVO = (Question)subquesList.get(j);
						itemList = itemDao.selectByQues(subVO.getQuesid(), subVO.getShopid());
						subVO.setItemList(itemList);
					}
				}
				voTemp.setSubquesList(subquesList);
			}
			// set quesanswer
			Map<Long, Quesanswer> rtnMap = answerDao.selectByPkStr(quesidBuffer.toString());
			Quesanswer answerTemp = null;
			for(int i=0; i<list.size(); i++){
				voTemp = (Exerques)list.get(i);
				answerTemp = rtnMap.get(voTemp.getQuesid());
				voTemp.setAnswerVO(answerTemp);
			}
		}
		return list;
	}
	
	protected List doDanXuanNoTrunk(Object paraObj1,Object paraObj2,Integer questype) {
		return null;
	}

	public QuesanswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(QuesanswerDao answerDao) {
		this.answerDao = answerDao;
	}

}
