package netTest.exam.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import netTest.bean.BeanFactory;
import netTest.exam.constant.UseranswerConstant;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.dto.UseranswerQuery;
import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Useranswer;
import org.apache.log4j.Logger;
import commonTool.base.BaseDao;


public class UseranswerDaoImpl extends BaseDao implements UseranswerDao {

	static Logger log = Logger.getLogger(UseranswerDaoImpl.class.getName());
	
    /**
     *
     */
    public UseranswerDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    public Useranswer selectByPK(Long pk){
    	if(pk==null)
    		return null;
		Useranswer record = (Useranswer) this.queryForObject("Useranswer.selectByPK", pk);
		return record;
    }
    
    /**
     * select records by queryVO
     */
    public List<Useranswer> selectByVO(UseranswerQuery queryVO){
		if(queryVO==null)
			return new ArrayList<Useranswer>();
		List<Useranswer> list = this.queryForList("Useranswer.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询用户答案数量
     * @param testid
     * @param paperid
     * @param shopid
     * @param searchType:0 查询全部阅卷类型题目，1代表查询客观题，2查询主观题，3查询主客观题，4查询客观题和主客观题
     *                   5 查询主观题和主客观题
     * @return
     */
    public long countAllTestAnswer(Long testid,Long paperid,Long shopid,Integer searchType,Short examinestatus){
    	if(testid==null||paperid==null||shopid==null)
    		return 0l;
    	UseranswerQuery queryVO = new UseranswerQuery();
    	queryVO.setTestid(testid);
    	queryVO.setPaperid(paperid);
    	queryVO.setShopid(shopid);
    	queryVO.setSearchCheckType(searchType);
    	queryVO.setExaminestatus(examinestatus);
    	Long nums = (Long)this.queryForObject("Useranswer.countAllTestAnswer", queryVO);
    	return nums==null?0:nums.longValue();
    }
    
    /**
     * 查询用户某次考试的某份试卷的所有答案
     * @param shopid
     * @param testid
     * @param userid
     * @param paperid
     * @param needRandom:是否按乱序的查询。如果是1则直接查询出答案，如果是0则按照题目中的quesOrder排序
     * @return
     */
    public List qryUseranswerList(Long shopid,Long testid,Long userid,Long paperid,int needRandom){
        if(shopid==null||testid==null||userid==null||paperid==null)
        	return null;
        List list = null;
        UseranswerQuery queryVO = new UseranswerQuery();
        queryVO.setTestid(testid);
        queryVO.setUserid(userid);
        queryVO.setPaperid(paperid);
        if(needRandom==0){
           queryVO.setOrder_By_Clause("quesOrder");
        }
        list = this.selectByVO(queryVO);
	    return list;
    }
    
    /**
     * 查询出用户对某次考试的试卷答案
     * 返回Map,主键是问题的类型,值是该类问题的用户答案类型Answerquestype.
     * 在该VO中包含了用户该种类型题目答案List集合。同时把子问题放入了父问题的subanswList里
     * @param shopid
     * @param testid
     * @param userid
     * @param paperid
     * @param needRandom:是否按乱序的查询。如果是1则直接查询出答案，如果是0则按照题目中的quesOrder排序
     * @return
     */
    public Map<Long,Answerquestype> qryAnswerMap(Long shopid,Long testid,Long userid,Long paperid,int needRandom){
    	List answerlist = this.qryUseranswerList(shopid, testid, userid, paperid, needRandom);
    	Map<Long,Answerquestype> retMap = new HashMap<Long,Answerquestype>();
    	Map<Long,Useranswer> quesMap = new HashMap<Long,Useranswer>();
    	List<Useranswer> listTemp = null;
    	List<Useranswer> subansList = new ArrayList<Useranswer>();
    	if(answerlist!=null&&answerlist.size()>0){
    		Useranswer answerTemp = null;
    		Answerquestype answerquestypeVO = null;
    		for(int i=0;i<answerlist.size();i++){ 
    			answerTemp = (Useranswer)answerlist.get(i);
    			if(answerTemp.getPid()==null){ // 如果是独立的题目，则把该题目存放到某一种题目类型的list中
    				quesMap.put(answerTemp.getQuesid(), answerTemp);
    				if(retMap.get(answerTemp.getQuestypeid())==null){
    					answerquestypeVO = new Answerquestype();
    					answerquestypeVO.setAnswerscore(answerTemp.getAnswerscore()==null?0f:answerTemp.getAnswerscore());
    					answerquestypeVO.setShopid(shopid);
    					answerquestypeVO.setPaperid(paperid);
    					answerquestypeVO.setUserid(userid);
    					answerquestypeVO.setQuestypeid(answerTemp.getQuestypeid());
    					answerquestypeVO.setQuestype(answerTemp.getQuestype());
    					listTemp = new ArrayList<Useranswer>();
    					listTemp.add(answerTemp);
    					answerquestypeVO.setAnswerList(listTemp);
    					retMap.put(answerTemp.getQuestypeid(), answerquestypeVO);
    				}else{
    					answerquestypeVO = retMap.get(answerTemp.getQuestypeid());
    					answerquestypeVO.getAnswerList().add(answerTemp);
    					if(answerTemp.getAnswerscore()!=null){
    					   answerquestypeVO.setAnswerscore(answerquestypeVO.getAnswerscore()+answerTemp.getAnswerscore());
    					}
    				}
    			}else{ // 先全部把子问题放到一个list集合中，然后统一和主问题配对
    				subansList.add(answerTemp);
    			}
    		}
    		if(subansList.size()>0){  // 如果存在子问题，即pid不为空
    			Useranswer answerTemp2 = null;
    			for(int j=0;j<subansList.size();j++){
    				answerTemp2 = subansList.get(j);
    				if(quesMap.get(answerTemp2.getPid())!=null){
    					answerTemp = (Useranswer)quesMap.get(answerTemp2.getPid());
    					if(answerTemp.getSubanswList()!=null){
    						answerTemp.getSubanswList().add(answerTemp2);
    					}else{
    						listTemp = new ArrayList<Useranswer>();
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
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Useranswer record){
    	if(record==null)
    		return null; 
		return (Long)super.insert("Useranswer.insert", record);
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Useranswer record){
    	if(record==null||record.getUseranswerid()==null)
    		return 0;
		int rows = super.update("Useranswer.updateByPK", record);
		if(record.getSubanswList()!=null && record.getSubanswList().size()>0){
			updBatchUseranswerSimple(record.getSubanswList());
		}
		return rows;
    }

    /**
     * update the record if exists pk,else insert the record
     * @param record
     * @return
     * @throws Exception
     */
    public Useranswer save(Useranswer record){
    	if(record==null)
    		return null;
		if(record.getUseranswerid()==null||record.getUseranswerid().intValue()==0){
			Long pkValue = this.insert(record);
			record.setUseranswerid(pkValue);
			return record;
		}else{
			this.updateByPK(record);
			return record;
		}
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk){
    	if(pk==null)
    		return 0;
		int rows = super.delete("Useranswer.deleteByPK", pk);
		return rows;
    }
    
    /**
     * delete user's answer by test
     */
    public int deleteByTest(Long testid, Long shopid){
    	if(testid==null || shopid==null)
    		return 0;
    	UseranswerQuery queryVO = new UseranswerQuery();
    	queryVO.setTestid(testid);
    	queryVO.setShopid(shopid);
		int rows = super.delete("Useranswer.deleteByTest", queryVO);
		return rows;
    }
    
    /**
     * job清理opentest中的useranswer的记录
     * @param date
     */
    public void cleanOpentestUseranswer(Date date){
    	// 删除 openTest结束后，开放考试结果15天后的useranswer记录
    	super.delete("Useranswer.cleanOpentestUseranswer1", date);
    	// 删除openTest, 查看结果是 '用户考完试就可以查看详细结果', 
    	// 并且testuser结束考试的时间超过15天用户的useranser记录被删除
    	super.delete("Useranswer.cleanOpentestUseranswer2", date);
    }

	/**
     * insertBatch records of List
     */
    public int insertBatch(List list){
    	if(list==null||list.size()<=0)
    		return 0;
    	int rows = 0;
       	rows = super.insertBatch("Useranswer.insert", list);
       	return rows;
    }
    
    /**
     * deleteBatch records by the String Array of PK
     */
    public int deleteBatchByPK(String[] pkArray){
    	if(pkArray==null||pkArray.length<=0)
    		return 0;
    	int rows = 0;
    	Long[] arrs = new Long[pkArray.length];
		for(int i=0;i<pkArray.length;i++){
			if(pkArray[i]!=null)
				arrs[i] = new Long(Long.parseLong(pkArray[i]));
		}
		rows = super.deleteBatch("Useranswer.deleteByPK", arrs);
		return rows; 
    }
    
    /**
     * 考试的时候更新用户的答案。仅仅更新答案和分数的信息
     * @param list
     * @return
     */
    public int updBatchUseranswer(List<Useranswer> list){
    	int rows = 0;
    	if(list!=null&&list.size()>0){
    		Useranswer vo = null;
    		List<Useranswer> updList = new ArrayList<Useranswer>();
    		for(int i=0;i<list.size();i++){
    			vo = list.get(i);
    			updList.add(vo);
    			if(vo.getSubanswList()!=null && vo.getSubanswList().size()>0)
    			{
    				updList.addAll(vo.getSubanswList());
    			}
    		}
    		rows = updBatchUseranswerSimple(updList);
    	}
    	return rows;
    }
    
    /**
     * 批量更新，只负责更新，不处理子记录
     * @param list
     * @return
     */
    private int updBatchUseranswerSimple(List<Useranswer> list){
    	int rows = 0;
    	if(list!=null&&list.size()>0){
    		Useranswer vo = null;
    		List<Useranswer> updList = new ArrayList<Useranswer>();
    		for(int i=0;i<list.size();i++){
    			vo = list.get(i);
    			if((vo.getQuesitemflag()!=null&&vo.getQuesitemflag().trim().length()>0)
    				||vo.getAnswerscore()!=null||(vo.getIsright()!=null&&vo.getIsright().trim().length()>0)
    				||vo.getExaminestatus()!=null||(vo.getAnswer()!=null&&vo.getAnswer().trim().length()>0))
    			{
    				updList.add(vo);
    			}
    		}
    		rows = super.updateBatch("Useranswer.updUseranswerPK", updList);
    	}
    	return rows;
    }
    
    /**
     * 根据主键更新考生答案状态
     */
    public int updStatusBatchByPK(List<Useranswer> list){
    	int rows = 0;
    	if(list!=null&&list.size()>0){
    		rows = super.updateBatch("Useranswer.updStatusByPK", list);
    	}
    	return rows;
    }
    
    /**
     * 查询考生答案阅卷题目数情况. 返回的Map中，key是问题的quentypeID,值是UseranswerQuery，其中有值代表各种评阅情况的题目数
     * @param shopid
     * @param testid
     * @param paperid
     * @param searchType:0 查询全部阅卷类型题目，1代表查询客观题，2查询主观题，3查询主客观题，4查询客观题和主客观题
     *                   5 查询主观题和主客观题
     * @return 
     */
    public Map<Long,UseranswerQuery> countQuestypeAnswer(Long testid,Long paperid,Long shopid,int searchType){
        if(shopid==null||testid==null||paperid==null)
        	return null;
        Map<Long,UseranswerQuery> map = new HashMap<Long,UseranswerQuery>();
        UseranswerQuery queryVO = new UseranswerQuery();
        queryVO.setTestid(testid);
        queryVO.setPaperid(paperid);
        queryVO.setShopid(shopid);
        queryVO.setSearchCheckType(searchType);
        
        List<UseranswerQuery> list = this.queryForList("Useranswer.countQuestypeAnswer", queryVO);
        if(list!=null&&list.size()>0){
        	UseranswerQuery voTemp = null;
        	UseranswerQuery voMap = null;
        	for(int i=0;i<list.size();i++){
        		voTemp = list.get(i);
        		if(map.get(voTemp.getQuesid())!=null){
        			voMap = map.get(voTemp.getQuesid());
        			if(UseranswerConstant.ExamineStatus_DidJudge.equals(voTemp.getExaminestatus())){
        				voMap.setQuantityChecked(voTemp.getQuantity()+voMap.getQuantityChecked());
        			}else if(UseranswerConstant.ExamineStatus_PreJudge.equals(voTemp.getExaminestatus())){
        				voMap.setQuantityPreCheck(voTemp.getQuantity()+voMap.getQuantityPreCheck());
        			}else if(UseranswerConstant.ExamineStatus_Judging.equals(voTemp.getExaminestatus())){
        				voMap.setQuantityChecking(voTemp.getQuantity()+voMap.getQuantityChecking());
        			}else if(UseranswerConstant.ExamineStatus_UnJudge.equals(voTemp.getExaminestatus())){
        				voMap.setQuantityUnCheck(voTemp.getQuantity()+voMap.getQuantityUnCheck());
        			}
        			voMap.setQuantity(voTemp.getQuantity()+voMap.getQuantity());
        		}else{
        			if(UseranswerConstant.ExamineStatus_DidJudge.equals(voTemp.getExaminestatus())){
        				voTemp.setQuantityChecked(voTemp.getQuantity());
        			}else if(UseranswerConstant.ExamineStatus_PreJudge.equals(voTemp.getExaminestatus())){
        				voTemp.setQuantityPreCheck(voTemp.getQuantity());
        			}else if(UseranswerConstant.ExamineStatus_Judging.equals(voTemp.getExaminestatus())){
        				voTemp.setQuantityChecking(voTemp.getQuantity());
        			}else if(UseranswerConstant.ExamineStatus_UnJudge.equals(voTemp.getExaminestatus())){
        				voTemp.setQuantityUnCheck(voTemp.getQuantity());
        			}
        			map.put(voTemp.getQuesid(), voTemp);
        		}
        	}
        }
	    return map;
    }
    
        
    /**
     * static spring getMethod
     */
     public static UseranswerDao getInstance(){
       	 UseranswerDao dao = (UseranswerDao)BeanFactory.getBeanFactory().getBean("useranswerDao");
         return dao;
     }
     
     public static void main(String[] args){
    	 UseranswerDao dao = UseranswerDaoImpl.getInstance();
     }
    
}
