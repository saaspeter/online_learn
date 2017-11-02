package netTest.exam.logic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import platform.vo.Infonews;
import commonTool.cache.CacheSynchronizer;
import commonTool.util.ObjUtil;
import netTest.bean.BeanFactory;
import netTest.common.logic.QuesTypeHandle;
import netTest.exam.dao.UseranswerDao;
import netTest.exam.logic.ExampaperLogic;
import netTest.exam.vo.Answerquestype;
import netTest.exam.vo.Useranswer;
import netTest.paper.logic.PaperLogic;
import netTest.paper.logic.QuesAnswerGetHandle;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperques;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.vo.Question;

/**
 * 负责展现答卷和收集试卷答案；考生进入考场
 * @author fan
 *
 */
public class ExampaperLogicImpl implements ExampaperLogic {

	private UseranswerDao answerDao;
	private PaperLogic paperLogic;
		
    /**
     * 得到试卷题目，并且得到试卷问题的答案，放在Paper中。
     * @param paperid
     * @param answertype: 答案类型，0 代表只返回试卷题目本身，不返回题目答案情况
     *           1是为评阅单个考生的试卷而设置的(用户自测阅卷, 自题目答案放在父题目中，以便于个人评卷)
     *                            2是批量评阅考生答卷设置的(自动批量阅卷)。
     *                            3是为手动阅卷准备的试卷及答案(去除所有客观题，以节省内存)
     * @return
     */
	@Cacheable(value="netTest.paperCache", key="'ExampaperLogicImpl.TestPaperAnswer~paper:'+#paperid+'~'+#answertype", unless="#result==null")
	public Paper getTestPaperAnswer(Long paperid,int answertype){
		if(paperid==null||(answertype!=0&&answertype!=1&&answertype!=2&&answertype!=3))
			return null;
		Paper originalVO = paperLogic.qryPaperWhole(paperid);
		Paper vo = (Paper)ObjUtil.deepClone(originalVO);
		Map<Long,Paperques> quesMap = null;
		if(answertype==1){
			quesMap = doQuesanswerMap1(vo);
		} else if(answertype==2){
			quesMap = doQuesanswerMap2(vo);
		} else if(answertype==3){
			List<Paperquestype> list = vo.getQuestypeList();
			if(list!=null&&list.size()>0){
				Paperquestype typevo = null;
				for(int i=list.size()-1;i>-1;i--){
					typevo = list.get(i);
					if(QuestionConstant.Queschecktype_auto.
							equals(QuestionConstant.getQueschecktype(typevo.getQuestype()))){
						typevo.setPaperquesList(null);
					}
				}
			}
			quesMap = doQuesanswerMap1(vo);
		}
		if(quesMap==null)
			quesMap = new HashMap<Long,Paperques>();
    	vo.setQuesanswerMap(quesMap);
    	
    	// add cache key into key assoic map
    	CacheSynchronizer.getInstance().buildAssoc("netTest.paperCache", 
    				"ExampaperLogicImpl.TestPaperAnswer~"+Paper.ObjectType+":"+paperid+"~"+answertype,
    				new String[]{Paper.ObjectType+":"+paperid});
		return vo;
	}
	
	/**
	 * 获得试卷题目的答案，并且存入map中; 方便阅卷。为单个考生的阅卷准备
	 * Map是题目id作为key，题目Paperques作为value(此时的题目中的answer即是问题的答案)
	 * 子题目会被放置在一个Map中放入父题目中。子题目不会被单独放入到Map中，是在父题目中的
	 * @param paperid
	 * @return
	 */
	private Map<Long,Paperques> doQuesanswerMap1(Paper vo){
		if(vo==null)
			return null;
		QuesTypeHandle handle = new QuesAnswerGetHandle();
		Paperquestype typeVO = null;
		Map<Long, Paperques> retMap = new HashMap<Long, Paperques>();
		if(vo!=null&&vo.getQuestypeList()!=null){
			for(int i=0;i<vo.getQuestypeList().size();i++){
				// 对于每种类型的题目集合，处理用户试卷的答案
				typeVO = (Paperquestype)(vo.getQuestypeList().get(i));
				if(typeVO!=null&&typeVO.getPaperquesList()!=null){
					Paperques quesTemp = null;
					Paperques subquesTemp = null;
					String answer = "";
					Object answerObj = null;
					for(int j=0;j<typeVO.getPaperquesList().size();j++){
						quesTemp = typeVO.getPaperquesList().get(j);
						// 调用相关的处理类设置每题的正确答案
						answerObj = handle.handleQuesType(quesTemp,null,quesTemp.getQuestype());
						answer = (answerObj==null)?"":((String)answerObj);
						quesTemp.setAnswer(answer);
						retMap.put(quesTemp.getQuesid(), quesTemp);
						if(quesTemp.getSubquesList()!=null&&quesTemp.getSubquesList().size()>0){
							Map<Long, Question> subquesMap = new HashMap<Long, Question>();
							for(int k=0;k<quesTemp.getSubquesList().size();k++){
								subquesTemp = (Paperques)quesTemp.getSubquesList().get(k);
								answerObj = handle.handleQuesType(subquesTemp,null,subquesTemp.getQuestype());
								answer = (answerObj==null)?"":((String)answerObj);
								subquesTemp.setAnswer(answer);
								subquesMap.put(subquesTemp.getQuesid(), subquesTemp);
							}
							quesTemp.setSubquesMap(subquesMap);
						}
					}
				}
			}
		}
		vo.setQuesanswerMap(retMap);
		return retMap;
	}
	
	/**
	 * 获得试卷题目的答案，并且存入map中; 方便阅卷。为一场考试的大量自动阅卷准备。
	 * Map是题目id作为key，题目Paperques作为value(此时的题目中的answer即是问题的答案)
	 * 子题目会被单独放入到Map中，不放在父题目中。
	 * @param paperid
	 * @return
	 */
	private Map<Long,Paperques> doQuesanswerMap2(Paper vo){
		if(vo==null)
			return null;
		QuesTypeHandle handle = new QuesAnswerGetHandle();
		Paperquestype typeVO = null;
		Map<Long, Paperques> retMap = new HashMap<Long, Paperques>();
		if(vo!=null&&vo.getQuestypeList()!=null){
			for(int i=0;i<vo.getQuestypeList().size();i++){
				// 对于每种类型的题目集合，处理用户试卷的答案
				typeVO = (Paperquestype)(vo.getQuestypeList().get(i));
				if(typeVO!=null&&typeVO.getPaperquesList()!=null){
					Paperques quesTemp = null;
					Paperques subquesTemp = null;
					String answer = "";
					Object answerObj = null;
					for(int j=0;j<typeVO.getPaperquesList().size();j++){
						quesTemp = typeVO.getPaperquesList().get(j);
						// 调用相关的处理类设置每题的正确答案
						answerObj = handle.handleQuesType(quesTemp,null,quesTemp.getQuestype());
						answer = (answerObj==null)?"":((String)answerObj);
						quesTemp.setAnswer(answer);
						retMap.put(quesTemp.getQuesid(), quesTemp);
						// 如果有子题目则把子题目也放到retMap中，主题目中不再保留这个引用。
						if(quesTemp.getSubquesList()!=null&&quesTemp.getSubquesList().size()>0){
							for(int k=0;k<quesTemp.getSubquesList().size();k++){
								subquesTemp = (Paperques)quesTemp.getSubquesList().get(k);
								answerObj = handle.handleQuesType(subquesTemp,null,subquesTemp.getQuestype());
								answer = (answerObj==null)?"":((String)answerObj);
								subquesTemp.setAnswer(answer);
								retMap.put(subquesTemp.getQuesid(), subquesTemp);
							}
						}
					}
				}
			}
		}
		vo.setQuesanswerMap(retMap);
		return retMap;
	}
	
	/**
	 * 查询用户的答案列表，并做分页处理。用于在试卷上显示考生的试题。
	 * TODO 需要缓存10分钟，主要是考虑考试结束后查看考生答案时的需要。
	 * 从UserAnswer表中查询数据
	 * @param testid
	 * @param userid
	 * @param paperid
	 * @param shopid
	 * @param paperVO
	 * @param needRandom 试卷中的试题是否需要乱序。0不乱序（即对新生成的答案要按照题目顺序组织，如果读取表中的值，按照quesorder排列）
	 * 					 1 乱序（即对新生成的答案要随机题目顺序，对于读取用户答案表中的值，则直接读取即可）
	 * @return
	 */
	public Map<Long,Answerquestype> qryTestUseranswer(Long testid,Long userid,Long paperid,Paper paperVO,int needRandom){
		if(paperid==null||userid==null||testid==null)
			return null;
		Map<Long,Answerquestype> retMap = new HashMap<Long,Answerquestype>();
		Answerquestype retObj = null;
		Map paramMap = new HashMap();
		QuesTypeHandle handle = new PaperquesInitAnswerHandle();
		if(paperVO==null)
			paperVO = paperLogic.qryPaperWhole(paperid);
		//retMap.put("paperid", paperid); // 把试卷id也放入其中，用于以后从session中取得后校验
		if(paperVO==null||paperVO.getQuestypeList()==null||paperVO.getQuestypeList().size()<1){
			return retMap;
		}
		Long shopid = paperVO.getShopid();
		// 从UserAnswer表中查询数据,如果没有查询到则需要生成该份试卷的答案列表
		// 并且需要把用户的答案列表根据题目类型分类
		Map<Long,Answerquestype> answerMap = answerDao.qryAnswerMap(shopid, testid, userid, paperid, needRandom);
		if(answerMap!=null&&!answerMap.isEmpty()){ // 用户的答卷答案已经在数据表中存在，直接把这些答案放到返回的Map中
			Paperquestype typeVO = null;
			for(int i=0;i<paperVO.getQuestypeList().size();i++){
				// 对于每种类型的题目集合，处理用户试卷的答案
				typeVO = (Paperquestype)(paperVO.getQuestypeList().get(i));
				if(answerMap.get(typeVO.getQuestypeid())!=null){
					retObj = answerMap.get(typeVO.getQuestypeid());
				}else{ // 如果用户答案表中不存在该类型题目，则重新生成这些题目的空白答案
					retObj = invokePaperquesInitAnswer(handle,paramMap,typeVO,testid,userid,needRandom);
				}
				if(retObj!=null){
					// 设置用户答案的分页信息
					retObj.initPageInfo(typeVO.getPagesize());
					retMap.put(typeVO.getQuestypeid(), retObj);
				}
			}
		}
		return retMap;
	}
	
	/**
	 * 根据试卷试题生成用户的用户答案列表，并乱序处理。
	 * 从UserAnswer表中查询数据,如果没有查询到则需要生成该份试卷的答案列表
	 * @param testid
	 * @param userid
	 * @param paperid
	 * @param shopid
	 * @param paperVO
	 * @param needRandom 试卷中的试题是否需要乱序。0不乱序（即对新生成的答案要按照题目顺序组织，如果读取表中的值，按照quesorder排列）
	 * 					 1 乱序（即对新生成的答案要随机题目顺序，对于读取用户答案表中的值，则直接读取即可）
	 * @return
	 */
	public Map<Long,Answerquestype> initTestUseranswer(Long testid,Long userid,Long paperid,
			Long shopid,Paper paperVO,int needRandom){
		if(paperid==null||userid==null)
			return null;
		Map<Long,Answerquestype> retMap = new HashMap<Long,Answerquestype>();
		Answerquestype retObj = null;
		Map paramMap = new HashMap();
		QuesTypeHandle handle = new PaperquesInitAnswerHandle();
		if(paperVO==null)
			paperVO = paperLogic.qryPaperWhole(paperid);
		if(paperVO==null||paperVO.getQuestypeList()==null||paperVO.getQuestypeList().size()<1){
			return retMap;
		}
        // 用户答案还不存，需要重新生成，是用户刚进入试卷答题的情况。
		Paperquestype typeVO = null;
		for(int i=0;i<paperVO.getQuestypeList().size();i++){
			// 对于每种类型的题目集合，把其中的题目生成用户的答案useranswer
			// 对于题目选项也生成乱序，同时把testid，userid,shopid,paperid写入useranswer
			// 返回这个题型用户答案的list，调用接口处理
			typeVO = (Paperquestype)(paperVO.getQuestypeList().get(i));
			retObj = invokePaperquesInitAnswer(handle,paramMap,typeVO,testid,userid,needRandom);
			if(retObj!=null&&typeVO!=null){
				// 设置用户答案的分页信息
				retObj.initPageInfo(typeVO.getPagesize());
				retMap.put(typeVO.getQuestypeid(), retObj);
			}
		}

		return retMap;
	}
	
	
	// 根据试卷题目生成用户题目答案列表
	private Answerquestype invokePaperquesInitAnswer(QuesTypeHandle handle,Map paramMap,
			     Paperquestype typeVO,Long testid,Long userid,int needRandom){
		Answerquestype answertypeVO = null;
		if(typeVO==null)
			return answertypeVO;
		if(paramMap==null)
			paramMap = new HashMap();
		if(handle==null)
			handle = new PaperquesInitAnswerHandle();
		List<Useranswer> randList = null;
		paramMap.put("typeVO", typeVO);
		paramMap.put("testid", testid);
		paramMap.put("userid", userid);
		paramMap.put("needRandom", new Integer(needRandom));
		randList = (List<Useranswer>)handle.handleQuesType(paramMap,typeVO.getQuestypeid(), typeVO.getQuestype());
		if(randList!=null){
			answertypeVO = new Answerquestype();
			answertypeVO.setPaperid(typeVO.getPaperid());
			answertypeVO.setShopid(typeVO.getShopid());
			answertypeVO.setAnswerList(randList);
			answertypeVO.setQuestype(typeVO.getQuestype());
			answertypeVO.setQuestypeid(typeVO.getQuestypeid());
		}
		return answertypeVO;
	}

    
	
	/**
	 * static spring getMethod
	 */
	 public static ExampaperLogic getInstance(){
		 ExampaperLogic logic = (ExampaperLogic)BeanFactory.getBeanFactory().getBean("exampaperLogic");
	     return logic;
	 }

	public PaperLogic getPaperLogic() {
		return paperLogic;
	}

	public void setPaperLogic(PaperLogic paperLogic) {
		this.paperLogic = paperLogic;
	}

	public UseranswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(UseranswerDao answerDao) {
		this.answerDao = answerDao;
	}
	
}
