package netTest.exercise.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BeanFactory;
import netTest.common.logic.QuesTypeHandle;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.constant.UseranswerConstant;
import netTest.exercise.dao.ExerciseDao;
import netTest.exercise.dao.ExeruserDao;
import netTest.exercise.dao.UserexeranswerDao;
import netTest.exercise.logic.ExeruserLogic;
import netTest.exercise.logic.JudgeExerUseranswerHandle;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerques;
import netTest.exercise.vo.Exeruser;
import netTest.exercise.vo.Userexeranswer;
import netTest.product.vo.Learnactivity;
import netTest.wareques.vo.Question;

import commonTool.event.Event;
import commonTool.util.DateUtil;

public class ExeruserLogicImpl implements ExeruserLogic{

	private ExeruserDao exeruserDao;
	private UserexeranswerDao answerDao;
	private ExerciseDao exerciseDao;
	
	/**
	 * 根据练习试题生成用户的用户答案列表
	 * @param userid
	 * @param quesList
	 * @return Map, Key: 题目的quesID
	 */
	public Map<Long, Userexeranswer> initUserAnswer(Long userid, List<Exerques> quesList, boolean needupdatedb)
	{
		Map<Long, Userexeranswer> retMap = new HashMap<Long, Userexeranswer>();
		if(userid==null||quesList==null||quesList.size()<1)
			return retMap;
		QuesTypeHandle handle = new ExerquesInitAnswerHandle();
		List<Userexeranswer> answerList = null;
		if(needupdatedb){
			answerList = new ArrayList<Userexeranswer>();
		}
        // 用户答案还不存，需要重新生成，是用户刚进入试卷答题的情况。
		Exerques quesVO = null;
		for(int i=0;i<quesList.size();i++){
			// 对于每种类型的题目集合，把其中的题目生成用户的答案useranswer
			// 对于题目选项也生成乱序，同时把testid，userid,shopid,paperid写入useranswer
			// 返回这个题型用户答案的list，调用接口处理
			quesVO = quesList.get(i);
			Userexeranswer answerVO = (Userexeranswer)handle.handleQuesType(quesVO, userid, quesVO.getQuestype());
			retMap.put(quesVO.getQuesid(), answerVO);
			if(needupdatedb){
				answerList.add(answerVO);
				if(answerVO.getSubanswList()!=null && answerVO.getSubanswList().size()>0){
					for(Userexeranswer subanswerVO : answerVO.getSubanswList()){
						answerList.add(subanswerVO);
					}
				}
			}
		}
		if(needupdatedb){
			answerDao.insertBatch(answerList);
		}

		return retMap;
	}
	
    /**
     * 查询出用户对某次练习答案
     * 返回Map,主键是练习题题目id(exerquesid),值是用户答案Userexeranswer.
     * 在该VO中包含了用户该种类型题目答案List集合。同时把子问题放入了父问题的subanswList里
     * @param shopid
     * @param userid
     * @param exerid
     * @return Map, Key: 题目的quesID
     */
    public Map<Long, Userexeranswer> qryUserExerAnswer(Long shopid, Long userid, 
    		         Long exerid, List<Exerques> quesList)
    {
    	List<Userexeranswer> answerlist = answerDao.qryUseranswerList(shopid, userid, exerid);
    	Map<Long, Userexeranswer> retMap = new HashMap<Long, Userexeranswer>();
    	List<Userexeranswer> listTemp = null;
    	List<Userexeranswer> subansList = new ArrayList<Userexeranswer>();
    	int answerNum = 0;
    	if(answerlist!=null&&answerlist.size()>0){
    		Userexeranswer answerTemp = null;
    		for(int i=0;i<answerlist.size();i++){ 
    			answerTemp = (Userexeranswer)answerlist.get(i);
    			if(answerTemp.getPid()==null){ // 如果是独立的题目，则把该题目存放到某一种题目类型的list中
    				retMap.put(answerTemp.getQuesid(), answerTemp);
    				answerNum++;
    			}else { // 先全部把子问题放到一个list集合中，然后统一和主问题配对
    				subansList.add(answerTemp);
    			}
    		}
    		if(subansList.size()>0){  // 如果存在子问题的答案，即pid不为空
    			Userexeranswer answerTemp2 = null;
    			for(int j=0;j<subansList.size();j++){
    				answerTemp2 = subansList.get(j);
    				if(retMap.get(answerTemp2.getPid())!=null){
    					answerTemp = (Userexeranswer)retMap.get(answerTemp2.getPid());
    					if(answerTemp.getSubanswList()!=null){
    						answerTemp.getSubanswList().add(answerTemp2);
    					}else{
    						listTemp = new ArrayList<Userexeranswer>();
    						listTemp.add(answerTemp2);
    						answerTemp.setSubanswList(listTemp);
    					}
    				}
    			}
    		}
    		
    	}
    	return retMap;
    }
    
    /**
     * 比较练习题和用户答案，如果练习题中有某些在用户的答案列表中不存在，则重新构建用户的答案
     * @param userid
     * @param quesList
     * @param useransMap
     * @return
     */
//    private void constructUseranserByQues(Long userid, List<Exerques> quesList, Map<Long, Userexeranswer> useransMap){
//    	QuesTypeHandle handle = new ExerquesInitAnswerHandle();
//    	for(Exerques quesVO : quesList){
//    		if(useransMap.get(quesVO.getQuesid())==null){
//    			Userexeranswer answerVO = (Userexeranswer)handle.handleQuesType(quesVO, userid, quesVO.getQuestype());
//    			useransMap.put(quesVO.getQuesid(), answerVO);
//    		}
//    	}
//    }
    
    /**
     * 检查用户的练习题答案中是否有没有评阅的试题，如果有则评阅试题并更新用户答案
     * @param quesList
     * @param usranwMap
     * @return 有多少之前没有评阅的试题
     */
    public int checkAllExerques(List<Exerques> quesList, Map<Long, Userexeranswer> usranwMap){
    	int nums = 0;
    	Long quesidTemp = null;
		Userexeranswer answerTemp = null;
		Userexeranswer subanswerTemp = null;
		List<Question> subquesList = null;
		List<Userexeranswer> updateList = new ArrayList<Userexeranswer>();
		QuesTypeHandle handle = new JudgeExerUseranswerHandle();
		// 循环练习中的所有题目
		for(Exerques quesTemp : quesList){
			quesidTemp = quesTemp.getQuesid();
			// 如果没有子题目
			answerTemp = usranwMap.get(quesidTemp);
			subquesList = quesTemp.getSubquesList();
			// 如果题目还没有被检查
			if(answerTemp!=null && 
					UseranswerConstant.ExamineStatus_UnJudge.equals(answerTemp.getExaminestatus())){
				handle.handleQuesType(quesTemp, answerTemp, quesTemp.getQuestype());
				updateList.add(answerTemp);
				if(subquesList!=null&&subquesList.size()>0){
					for(int i=0;i<subquesList.size();i++){
						subanswerTemp = answerTemp.getSubanswList().get(i);
						updateList.add(subanswerTemp);
					}
				}
			}
		}
		// 
		if(updateList.size()>0){
			nums = answerDao.batchUpdUseranswerPK(updateList);
		}
		return nums;
    }
    
    /**
     * 根据exercise和user查询Exeruser对象
     */
    public Exeruser selectByExer(Long exerid, Long userid){
    	Exeruser userVO = exeruserDao.selectByLogicPK(exerid, userid);
    	return userVO;
    }
    
    /**
     * 更新用户的use exercise状态，并且发送消息到learn activity
     * @param vo
     * @param userid
     * @param exerid
     */
    public void updateUserExer(Exeruser vo, Long userid, Long exerid){
    	if(vo!=null&&userid!=null&&exerid!=null){
    		exeruserDao.updateByLogicPK(vo, userid, exerid);
    		// 发送用户做练习的消息到learnactivity
    		if(vo.getStatus()!=null){
    			Learnactivity activityvo = new Learnactivity();
				activityvo.setUserid(userid);
				activityvo.setActiontype(Learnactivity.Actiontype_Exercise);
				activityvo.setObjectid(exerid);
				activityvo.setObjecttype(Exercise.ObjectType);
				if(vo.getProductbaseid()==null||vo.getShopid()==null||vo.getContentcateid()==null){
					Exercise exervo = exerciseDao.selectByPK(exerid);
					activityvo.setProductid(exervo.getProductbaseid());
	 				activityvo.setContentcateid(exervo.getContentcateid());
	 				activityvo.setShopid(exervo.getShopid());
				}else {
					activityvo.setProductid(vo.getProductbaseid());
	 				activityvo.setContentcateid(vo.getContentcateid());
	 				activityvo.setShopid(vo.getShopid());
				}
				// 如果是完成联系，就设置学习状态为完成
    			if(!TestuserConstant.Status_endExam.equals(vo.getStatus())){
    				activityvo.setLearnstatus(Learnactivity.Learnstatus_Progressing);
    				activityvo.setEndtime(null);
    				if(vo.getStarttime()!=null){
    					activityvo.setStarttime(vo.getStarttime());
    				}else {
    					activityvo.setStarttime(DateUtil.getInstance().getNowtime_GLNZ());
    				}
    			}else {
    				activityvo.setLearnstatus(Learnactivity.Learnstatus_Finished);
    				if(vo.getEndtime()!=null){
    					activityvo.setEndtime(vo.getEndtime());
    				}else {
    					activityvo.setEndtime(DateUtil.getInstance().getNowtime_GLNZ());
    				}
    			}
    			Map paraMap = new HashMap();
				paraMap.put("vo", activityvo);
			    Event event = new Event(EventHandlerNetTest.EventType_Product_LearnActivity, paraMap);
			    EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
    		}
    	}
    }
    
	
	/**
	 * static spring getMethod
	 */
	 public static ExeruserLogic getInstance(){
		 ExeruserLogic logic = (ExeruserLogic)BeanFactory.getBeanFactory().getBean("exeruserLogic");
	     return logic;
	 }

	public UserexeranswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(UserexeranswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public ExeruserDao getExeruserDao() {
		return exeruserDao;
	}

	public void setExeruserDao(ExeruserDao exeruserDao) {
		this.exeruserDao = exeruserDao;
	}

	public ExerciseDao getExerciseDao() {
		return exerciseDao;
	}

	public void setExerciseDao(ExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}
	
}
