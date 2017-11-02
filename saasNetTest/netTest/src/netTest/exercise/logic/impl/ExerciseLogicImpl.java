package netTest.exercise.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.common.logic.QuesTypeHandle;
import netTest.event.EventHandlerNetTest;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dao.ExerquesDao;
import netTest.exercise.dao.ExerquestypeDao;
import netTest.exercise.dao.ExeruserDao;
import netTest.exercise.dao.UserexeranswerDao;
import netTest.exercise.dto.ExerciseQuery;
import netTest.exercise.logic.ExerciseLogic;
import netTest.exercise.logic.ExerquesLogic;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exerquestype;
import netTest.paper.logic.QuesAnswerGetHandle;
import netTest.product.logic.ProductLogic;
import netTest.product.logic.impl.ProductLogicImpl;
import netTest.product.vo.Productbase;
import netTest.wareques.dao.QuesanswerDao;
import netTest.wareques.vo.Quesanswer;
import netTest.wareques.vo.Question;

import commonTool.event.Event;
import commonTool.util.DateUtil;


public class ExerciseLogicImpl implements ExerciseLogic{

	
	public static final Integer WholeQyesType = -99999999; 
	
	private ExerciseDao exerDao;

	private ExerquestypeDao exerquestypeDao;
	
	private ExerquesDao exerquesDao;
	
	private ExeruserDao exeruserDao;
	
	private UserexeranswerDao useranwerDao;
	
	private QuesanswerDao quesanswerDao;
	
	private ExerquesLogic quesLogic;
	
	/**
	 * 查询一份练习，包括其中的所有题目，题目集合在Exercise对象中
	 * 同时会把问题答案初始化进题目的answer中
	 * 而questype对象中不包含题目
	 * @param paperid
	 * @return
	 */
	public Exercise qryAllExerQues(Long exerid){
		if(exerid==null)
			return null;
		Exercise vo = exerDao.selectByPK(exerid);
		if(vo!=null){
			List questypeList = exerquestypeDao.qryByExerid(exerid);
			if(questypeList!=null&&questypeList.size()>0){
			    vo.setQuestypeList(questypeList);
			    Exerquestype typeVO = null;
			    List<Exerques> quesList = new ArrayList<Exerques>();
			    List<Exerques> listTemp;
				for(int i=0;i<questypeList.size();i++){
					typeVO = (Exerquestype)questypeList.get(i);
					listTemp = quesLogic.qryExerquesByPattern(exerid,typeVO.getQuestype());
					quesList.addAll(listTemp);
				}
				vo.setQuesList(quesList);
			}
			// 初始化题目答案，初始化到题目的answer中
			Map<Long,Exerques> quesMap = doQuesanswerMap(vo);
			vo.setQuesanswerMap(quesMap);
			
			ProductLogic prodlogic = ProductLogicImpl.getInstance();
			Productbase prodVO = prodlogic.selectVO(vo.getProductbaseid(), null);
			vo.setProductname(prodVO.getProductname());
		}
		return vo;
	}
	
	/**
	 * 查询一份试卷中某种题型的题目集合
	 * @param exerid
	 * @param questyepUse: 需要查询的题型。
	 *        如果为空，则取试卷的第一种题型，如果为-1，则查询全部题型
	 * @return
	 */
	public Exercise qryExerquesPattern(Long exerid, Integer questypeUse){
		if(exerid==null)
			return null;
		Exercise vo = exerDao.selectByPK(exerid);
		if(vo!=null){
			List<Exerquestype> questypeList = (List<Exerquestype>)exerquestypeDao.qryByExerid(exerid);
			if(questypeList!=null&&questypeList.size()>0){
			    vo.setQuestypeList(questypeList);
			    Exerquestype typeVO = null;
			    List<Exerques> listTemp = null;

				// 从PaperQues表查询试卷题目
				if(questypeUse==null){
					typeVO = (Exerquestype)questypeList.get(0);
					questypeUse = typeVO.getQuestype();
				}
				if(questypeUse.intValue()==WholeQyesType)
					questypeUse = null;
				for(int i=0;i<questypeList.size();i++){
					typeVO = (Exerquestype)questypeList.get(i);
					if(questypeUse!=null){
						if(questypeUse.equals(typeVO.getQuestype())){
							listTemp = quesLogic.qryExerquesByPattern(exerid,typeVO.getQuestype());	
							typeVO.setQuesList(listTemp);
							break;
						}
					}
                    // 当不指定题目类型时，则查询所有类型题目
					listTemp = quesLogic.qryExerquesByPattern(exerid,typeVO.getQuestype());
					typeVO.setQuesList(listTemp);
				}

				vo.setQuestypeUse(questypeUse);
			}
			ProductLogic prodlogic = ProductLogicImpl.getInstance();
			Productbase prodVO = prodlogic.selectVO(vo.getProductbaseid(), null);
			vo.setProductname(prodVO.getProductname());
		}
		return vo;
	}
	
	
	/**
	 * 获得试卷题目的答案，并且存入map中; 方便阅卷。为单个考生的阅卷准备
	 * Map是题目id作为key，题目Paperques作为value(此时的题目中的answer即是问题的答案)
	 * 子题目会被放置在一个Map中放入父题目中。子题目不会被单独放入到Map中，是在父题目中的
	 * @param paperid
	 * @return
	 */
	private Map<Long,Exerques> doQuesanswerMap(Exercise exerVO){
		if(exerVO==null)
			return null;
		QuesTypeHandle handle = new QuesAnswerGetHandle();
		Map<Long, Exerques> retMap = new HashMap<Long, Exerques>();
		Exerques quesTemp = null;
		Question subquesTemp = null;
		String answer = "";
		Object answerObj = null;
		List<Exerques> quesList = exerVO.getQuesList();
		if(quesList!=null && quesList.size()>0){
            // 把题目答案的提示信息answerVO装入，如果题目的answerVO is null
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<quesList.size();i++){
				quesTemp = quesList.get(i);
				if(quesTemp.getAnswerVO()==null){
					buffer.append(quesTemp.getQuesid()).append(",");
				}
			}
			Map<Long, Quesanswer> answerMap = quesanswerDao.selectByPkStr(buffer.toString());
			Quesanswer answerVO = null;
			for(int i=0;i<quesList.size();i++){
				quesTemp = quesList.get(i);
				answerVO = answerMap.get(quesTemp.getQuesid());
				if(answerVO!=null){
				   quesTemp.setAnswerVO(answerVO);
				}
   			    // 调用相关的处理类设置每题的正确答案
				answerObj = handle.handleQuesType(quesTemp,null,quesTemp.getQuestype());
				answer = (answerObj==null)?"":((String)answerObj);
				quesTemp.setAnswer(answer);
				retMap.put(quesTemp.getQuesid(), quesTemp);
				if(quesTemp.getSubquesList()!=null&&quesTemp.getSubquesList().size()>0){
				    Map<Long, Question> subquesMap = new HashMap<Long, Question>();
					for(int k=0;k<quesTemp.getSubquesList().size();k++){
						subquesTemp = quesTemp.getSubquesList().get(k);
						answerObj = handle.handleQuesType(subquesTemp,null,subquesTemp.getQuestype());
						answer = (answerObj==null)?"":((String)answerObj);
						subquesTemp.setAnswer(answer);
						subquesMap.put(subquesTemp.getQuesid(), subquesTemp);
					}
					quesTemp.setSubquesMap(subquesMap);
				}
			}
            
		}
		return retMap;
	}
	
	/**
	 * 新增练习，增加练习及练习题型信息
	 * @param vo
	 * @param questypeList
	 */
	public Exercise addExercise(Exercise vo, String[] questypeArr){
		vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
		Long pk = exerDao.insert(vo);
		vo.setExerid(pk);
		Long shopid = vo.getShopid();
		List<Exerquestype> questypeList = null;

		// 插入题目题型
		if(questypeArr!=null){
			questypeList = new ArrayList<Exerquestype>();
			for(int i=0; i<questypeArr.length; i++){
				Exerquestype typeVO = new Exerquestype();
				typeVO.setExerid(pk);
				typeVO.setShopid(shopid);
				typeVO.setQuestype(new Integer(questypeArr[i]));
				questypeList.add(typeVO);
			}
		}
		exerquestypeDao.insertBatch(questypeList);
		// send notification for new add exercise
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("productid", vo.getProductbaseid().toString());
		paraMap.put("number", "1");
		Event event = new Event(EventHandlerNetTest.EventType_Exercise_AddDelete, paraMap);
		EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Message);
		return vo;
	}

	/**
	 * 删除整个练习，包括练习题目和练习的考生考试结果
	 * @param quizid
	 * @return
	 */
	public boolean delWholeExercise(Long exerid, Exercise vo, boolean sendnotification){
		if(exerid==null)
			return false;
		if(vo==null) {
		   vo = exerDao.selectByPK(exerid);
		}
		if(vo==null){
			return false;
		}
		// 删除练习的分配信息和练习的人员答题信息
		exeruserDao.deleteByExer(exerid);
		useranwerDao.deleteByExer(exerid);
		exerquesDao.deleteByExer(exerid);
		exerquestypeDao.deleteByExer(exerid);
		exerDao.deleteByPK(exerid);
		if(sendnotification){
			// send notification for deleted exercise
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("productid", vo.getProductbaseid().toString());
			paraMap.put("number", "-1");
			Event event = new Event(EventHandlerNetTest.EventType_Exercise_AddDelete, paraMap);
			EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Message);
		}
		return true;
	}
	
	/**
	 * 根据product删除所有的exercise, 用于删除产品时的被调用
	 * @param productid
	 * @param shopid
	 */
	public void deleteExerByProd(Long productid, Long shopid){
		ExerciseQuery queryVO = new ExerciseQuery();
		queryVO.setProductbaseid(productid);
		queryVO.setShopid(shopid);
		List exerlist = exerDao.selectByVO(queryVO);
		if(exerlist!=null && exerlist.size()>0){
			Exercise votemp = null;
			for(int i=0;i<exerlist.size();i++){
				votemp = (Exercise)exerlist.get(i);
				if(votemp!=null) {
					delWholeExercise(votemp.getExerid(), votemp, false);
				}
			}
		}
	}
	

	public ExerciseDao getExerDao() {
		return exerDao;
	}
	public void setExerDao(ExerciseDao exerDao) {
		this.exerDao = exerDao;
	}
	public ExerquesDao getExerquesDao() {
		return exerquesDao;
	}
	public void setExerquesDao(ExerquesDao exerquesDao) {
		this.exerquesDao = exerquesDao;
	}
	public ExerquestypeDao getExerquestypeDao() {
		return exerquestypeDao;
	}
	public void setExerquestypeDao(ExerquestypeDao exerquestypeDao) {
		this.exerquestypeDao = exerquestypeDao;
	}
	public void setExeruserDao(ExeruserDao exeruserDao) {
		this.exeruserDao = exeruserDao;
	}
	public ExeruserDao getExeruserDao() {
		return exeruserDao;
	}
	public void setQuizuserDao(ExeruserDao exeruserDao) {
		this.exeruserDao = exeruserDao;
	}

    public static ExerciseLogic getInstance(){
    	ExerciseLogic logic = (ExerciseLogic)BeanFactory.getBeanFactory().getBean("exerciseLogic");
        return logic;
    }

	public ExerquesLogic getQuesLogic() {
		return quesLogic;
	}

	public void setQuesLogic(ExerquesLogic quesLogic) {
		this.quesLogic = quesLogic;
	}

	public QuesanswerDao getQuesanswerDao() {
		return quesanswerDao;
	}

	public void setQuesanswerDao(QuesanswerDao quesanswerDao) {
		this.quesanswerDao = quesanswerDao;
	}

	public UserexeranswerDao getUseranwerDao() {
		return useranwerDao;
	}

	public void setUseranwerDao(UserexeranswerDao useranwerDao) {
		this.useranwerDao = useranwerDao;
	}
   
	
}
