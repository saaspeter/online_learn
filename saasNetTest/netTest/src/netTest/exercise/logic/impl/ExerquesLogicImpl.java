package netTest.exercise.logic.impl;

import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.event.EventHandlerNetTest;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dao.ExerquesDao;
import netTest.exercise.logic.ExerquesLogic;
import netTest.exercise.vo.Exerques;
import netTest.wareques.logic.impl.QuestionLogicImpl;

import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.exception.NeedParamsException;
import commonTool.util.StringUtil;

public class ExerquesLogicImpl extends QuestionLogicImpl implements ExerquesLogic, EventHandle{

	private ExerciseDao exerDao;
	private ExerquesDao quesDao;
	//private ExerquestypeDao typeDao;
	
	
	/**
	 * 根据试卷id和某种题型查找Paperques(从PaperQues表中查询)
	 * @param paperid
	 * @param questype
	 * @return
	 */
	public List qryExerquesByPattern(Long exerid, Integer questype){
		List quesList = null;
		ExerquesQryByPattern handle = new ExerquesQryByPattern();
		quesList = (List)handle.handleQuesType(exerid,null, questype);
	    return quesList;
	}
	
	
	/**
	 * 删除练习题目,同时更新练习的版本号
	 * @param quesid
	 * @param questype
	 * @param shopid
	 * @return
	 */
	public boolean delExerques(Long exerid,Long exerquesid,Integer questype){
		quesDao.deleteByPK(exerquesid);
		boolean ret = true;
		//exerDao.updateModInfo(exerid, null);
		return ret;
	}

	/**
	 * 更换试卷中的题目
	 * 同时更新试卷修改时间和版本号
	 * @param vo:原有的题目，必要属性：paperid,quesid,paperquesscore,questype,shopid,questypeid
	 * @param warequesid
	 * @return
	 */
	public Long changQues(Exerques vo,Long quesid){
		if(vo==null||vo.getExerquesid()==null||vo.getExerid()==null
		   ||vo.getQuestype()==null||vo.getShopid()==null||quesid==null)
		   throw new NeedParamsException();
		// 删除原有题目，并且更新练习版本
		this.delExerques(vo.getExerid(), vo.getExerquesid(),vo.getQuestype());
		// 新增题目，在此可以调用新增题目的方法
		vo.setExerquesid(null);
		addSingleSelQues(vo.getExerid(), vo.getQuestype(), quesid, vo.getShopid());
		
		return quesid;
	}

	/**
	 * 新增试卷题目,是通过选择题库题目产生的动作
	 * 同时更新试卷修改时间和版本号
	 * @param vo:必要属性：paperid,quesid,paperquesscore,questype,shopid,questypeid
	 * @param warequesidStr：选择的要新增的题目id串，以逗号隔开
	 * @return
	 */
	public int addSelExerques(Exerques vo,String quesidStr){
		if(vo==null||vo.getExerid()==null||vo.getQuestype()==null
			||vo.getShopid()==null||quesidStr==null||quesidStr.trim().length()<1)
		   return 0;
		int retsult = 0;
		String quesidArr[] = StringUtil.splitString(quesidStr, ",");
		if(quesidArr!=null&&quesidArr.length>0){
			for(int i=0;i<quesidArr.length;i++){
                // 新增练习题目，
				this.addSingleSelQues(vo.getExerid(),vo.getQuestype(),new Long(quesidArr[i]),vo.getShopid());
				retsult++;
			}
			//exerDao.updateModInfo(vo.getExerid(), null);
		}
		return retsult;
	}
	
	/**
	 * 新增一个题库中的题目到试卷中
	 * 同时更新试卷修改时间和版本号
	 * @param vo
	 * @param warequesid:题库中的题目id
	 * @return
	 */
	private Long addSingleSelQues(Long exerid,Integer questype,Long quesid,Long shopid){
		if(exerid==null||questype==null||quesid==null||shopid==null)
			return null;
		Exerques vo = new Exerques();
		vo.setExerid(exerid);
		vo.setQuesid(quesid);
		vo.setQuestype(questype);
		vo.setShopid(shopid);
		Long exerquesid = quesDao.insert(vo);

		return exerquesid;
	}
	
	/**
	 * 当题库中的试题发生变化时，更新练习中的引用题目，并且更新用户练习中的引用
	 * @param quesidStr
	 * @param wareid
	 */
	private void onWarequesChange(String quesidStr, Long wareid, String eventType){
	   if((quesidStr==null||quesidStr.trim().length()<1)&&wareid==null)	
		   return ;
	   if(EventHandlerNetTest.EventType_WareQuestion_DelQuesBatch.equals(eventType)){
		   // 如果是删除题目，则删除练习中的题目引用和用户练习答案中的引用
	      quesidStr = StringUtil.trimComma(quesidStr);
	      quesDao.delExerByWareQues(quesidStr, wareid);
	   }
	   return;
	}
	
	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String quesidStr = (String)paraMap.get("quesidStr");
		Long wareid = (Long)paraMap.get("wareid");
		String eventType = event.getEventType();
		onWarequesChange(quesidStr, wareid, eventType);
	}
	
	/**
	 * static spring getMethod
	 */
	 public static ExerquesLogic getExerquesInstance(){
		 ExerquesLogic logic = (ExerquesLogic)BeanFactory.getBeanFactory().getBean("exerquesLogic");
		 
	     return logic;
	 }


	public ExerciseDao getExerDao() {
		return exerDao;
	}


	public void setExerDao(ExerciseDao exerDao) {
		this.exerDao = exerDao;
	}


	public ExerquesDao getQuesDao() {
		return quesDao;
	}


	public void setQuesDao(ExerquesDao quesDao) {
		this.quesDao = quesDao;
	}


}
