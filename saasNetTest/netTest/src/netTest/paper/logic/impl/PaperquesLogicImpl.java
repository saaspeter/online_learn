package netTest.paper.logic.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.exception.ExceptionConstant;
import netTest.paper.constant.PaperConstant;
import netTest.paper.dao.PaperDao;
import netTest.paper.dao.PaperpropDao;
import netTest.paper.dao.PaperquesDao;
import netTest.paper.dao.PaperquestypeDao;
import netTest.paper.logic.PaperquesLogic;
import netTest.paper.logic.PaperquesQryByPattern;
import netTest.paper.vo.Paper;
import netTest.paper.vo.Paperprop;
import netTest.paper.vo.Paperques;
import netTest.util.ImportQuesFromTxtHelper;
import netTest.wareques.constant.QuestionConstant;
import netTest.wareques.constant.QuestionImportConstant;
import netTest.wareques.dao.QuestionDao;
import netTest.wareques.vo.Question;
import commonTool.cache.CacheSynchronizer;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;

public class PaperquesLogicImpl implements PaperquesLogic {

	private PaperDao paperDao;
	private PaperquesDao paperquesDao;
	private PaperquestypeDao typeDao;
	private QuestionDao questionDao;
	
	/**
	 * 根据试卷id和某种题型查找Paperques(从PaperQues表中查询)
	 * @param paperid
	 * @param questype
	 * @return
	 */
	public List qryPaperquesByPattern(Long paperid,Long questypeid,Integer questype){
		List quesList = null;
		PaperquesQryByPattern handle = new PaperquesQryByPattern();
		quesList = (List)handle.handleQuesType(paperid,questypeid, questype);
	    return quesList;
	}
	
	/**
	 * 删除试卷题目,同时更新题型分数和试卷分数
	 * 同时更新试卷修改时间和版本号
	 * @param quesid
	 * @param questype
	 * @param shopid
	 * @return
	 */
	public boolean delPaperques(Long paperid,Long paperquesid,Long questypeid,Integer questype,
							    Float paperquesscore,Integer quesoptnum,Long shopid){
		paperquesDao.deleteByPK(paperquesid);
		if(paperquesscore!=null&&paperquesscore.floatValue()!=0f){
			if(quesoptnum==null)
				quesoptnum = 1;
			paperquesscore = -(paperquesscore*quesoptnum);
			typeDao.updatePatternscore(paperid, questypeid, paperquesscore, 2, shopid);
			paperDao.updateScore(paperid, paperquesscore, 2, shopid);
		}else{
			paperDao.updateModInfo(paperid, shopid, null);
		}
		
		// flush query cache
		CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", Paper.ObjectType, paperid.toString());
		return true;
	}
		
	/**
	 * 更换试卷中的题目
	 * 同时更新试卷修改时间和版本号
	 * @param vo:必要属性：paperid,quesid,paperquesscore,questype,shopid,questypeid
	 * @param warequesid
	 * @return
	 */
	public Long changQues(Long paperid,Paperques vo,Long warequesid){
		if(vo==null||vo.getQuesid()==null||vo.getPaperquesscore()==null||vo.getPaperid()==null||vo.getQuestypeid()==null
		   ||vo.getQuestype()==null||vo.getShopid()==null||warequesid==null)
		   throw new NeedParamsException();
		// 删除原有题目
		this.delPaperques(vo.getPaperid(), vo.getPaperquesid(), vo.getQuestypeid(), vo.getQuestype(), vo.getPaperquesscore(),vo.getQuesoptnum(), vo.getShopid());
		// 新增题目，在此可以调用新增题目的方法
		vo.setQuesid(null);
		Long quesid = addSingleSelPaperQues(vo.getPaperid(),vo.getQuestypeid(),vo.getQuestype(),vo.getPaperquesscore(), warequesid,vo.getShopid(),true);
		
		// flush query cache
		CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", Paper.ObjectType, paperid.toString());
		return quesid;
	}
	
	/**
	 * 新增试卷题目,是通过选择题库题目产生的动作
	 * 同时更新试卷修改时间和版本号
	 * @param vo:必要属性：paperid,quesid,paperquesscore,questype,shopid,questypeid
	 * @param warequesidStr：选择的要新增的题目id串，以逗号隔开
	 * @return
	 */
	public int addSelPaperques(Long paperid, Paperques vo, String warequesidStr){
		if(vo==null||vo.getPaperquesscore()==null||vo.getPaperid()==null
		   ||vo.getQuestype()==null||vo.getShopid()==null||warequesidStr==null
		   ||warequesidStr.trim().length()<1)
		   return 0;
		int retsult = 0;
		String quesidArr[] = StringUtil.splitString(warequesidStr, ",");
		if(quesidArr!=null&&quesidArr.length>0){
			for(int i=0;i<quesidArr.length;i++){
                // 新增试卷题目，同时更新题型和试卷的分数（因为需要知道每题的子题目数，所以必须单个更新试卷题型分数）
				this.addSingleSelPaperQues(vo.getPaperid(),vo.getQuestypeid(),vo.getQuestype(),
						vo.getPaperquesscore(),new Long(quesidArr[i]),vo.getShopid(),true);
				retsult++;
			}
		}
		
		// flush query cache
		CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", Paper.ObjectType, paperid.toString());
		return retsult;
	}
	
	/**
	 * 新增一个题库中的题目到试卷中
	 * 同时更新试卷修改时间和版本号
	 * @param vo
	 * @param warequesid:题库中的题目id
	 * @return
	 */
	private Long addSingleSelPaperQues(Long paperid,Long questypeid,Integer questype,
			       Float paperquesscore,Long quesid,Long shopid,boolean updateScore){
		if(paperid==null||questype==null||quesid==null||shopid==null)
			return null;
		if(updateScore&&paperquesscore==null)
			return null;
		Question quesVO = questionDao.selectByPK(quesid);
		AssertUtil.assertNotNull(quesVO, null);
		Paperques vo = new Paperques();
		vo.setPaperid(paperid);
		vo.setShopid(shopid);
		vo.setQuestype(questype);
		vo.setPaperquesscore(paperquesscore);
		vo.setQuesid(quesid);
		vo.setQuestypeid(questypeid);
		
		Long paperquesid = paperquesDao.insert(vo);
		// 得到题目中的子题目数
		int quesoptnum = 1;
		if(quesVO.getQuesoptnum()!=null){
			quesoptnum = quesVO.getQuesoptnum().intValue();
		}
		
		// 更新题型和试卷的分数
		if(updateScore && vo.getPaperquesscore()!=null 
				&& vo.getPaperquesscore().floatValue()!=0f)
		{
			typeDao.updatePatternscore(vo.getPaperid(), questypeid, vo.getPaperquesscore()*quesoptnum, 2, vo.getShopid());
			paperDao.updateScore(vo.getPaperid(), vo.getPaperquesscore()*quesoptnum, 2, vo.getShopid());
		}
		return paperquesid;
	}
	
	/**
	 * 更新试卷题目分数，并且更新试卷版本和最后修改时间。当从试卷上更新了试卷题目的时候，更新完题库题目后，或调用本方法更新试卷题目相关信息
	 * @param quesid: 这里的quesid应该都是独立题目id或复合型题目的父题目id
	 * @param quesoptnum_changed：改变的空格个数
	 * @return
	 */
//	public void updatePaperByQuesChange(Set<Long> quesidset, Long paperid, Long shopid,
//			 							Integer quesoptnum_changed)
//	{
//		if(quesidset==null || quesidset.size()<1)
//			return;
//		
//		List<Paperques> quesList = paperquesDao.qryPaperquesByQues(quesidset, paperid, shopid);
//		if(quesList!=null && quesList.size()>0){
//			for(Paperques quesVO : quesList){
//				if(quesoptnum_changed!=null && quesoptnum_changed!=0){
//					float score = quesoptnum_changed*quesVO.getPaperquesscore();
//					typeDao.updatePatternscore(quesVO.getPaperid(), quesVO.getQuestypeid(), score, 2, shopid);
//					paperDao.updateScore(quesVO.getPaperid(), score, 2, shopid);
//				}else{
//					paperDao.updateModInfo(quesVO.getPaperid(), shopid, null);
//				}
//				// flush query cache
//				CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", Paper.ObjectType, quesVO.getPaperid().toString());
//			}
//		}
//	}
	

//	
//	/**
//	 * 新增复合题目的子题目，保存
//	 * 同时更新试卷修改时间和版本号
//	 * @param vo:Paperques 要保存的子题目
//	 * @param questype_parent: 父题目的题目类型
//	 * @return
//	 */
//	public Long addSubques(Long paperid,Paperques vo,Integer questype_parent){
//		if(vo==null||questype_parent==null||vo.getQuestypeid()==null)
//			throw new NeedParamsException();
//		Long pk = super.addQues(vo);
//		if(vo.getPaperquesscore()!=null){ // 更新分数
//		   typeDao.updatePatternscore(vo.getPaperid(), vo.getQuestypeid(), vo.getPaperquesscore(), 2, vo.getShopid());
//		   paperDao.updateScore(vo.getPaperid(), vo.getPaperquesscore(), 2, vo.getShopid());
//		}else{
//		   paperDao.updateModInfo(vo.getPaperid(), vo.getShopid(), null);
//		}
//		
//		// flush query cache
//		CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", Paper.ObjectType, paperid.toString());
//		return pk;
//	}
//	
//	/**
//	 * 删除试卷中子题目(复合题目的)，同时处理题目分数问题。完形填空的子题目不在这里处理
//	 * 同时更新试卷修改时间和版本号
//	 * @param quesArr:二维数组，[0]代表主键quesid，[1]代表题目类型
//	 * @param questype_parent:复合问题的父问题的类型
//	 * @param questype_sub:子问题的问题类型
//	 * @param pid: 父题目id
//	 * @return
//	 */
//	public boolean delSubques(Long paperid,Long quesid,Long questypeid,Integer questype_sub,
//			Float paperquesscore,Long shopid,Long pid){
//		if(paperid==null||quesid==null||shopid==null||questypeid==null||pid==null||questype_sub==null)
//			return false;
//		boolean result = super.delSingleQues(quesid,questype_sub,QuestionConstant.Question_paper, shopid,pid);
//		if(result){
//			paperDao.updateModInfo(paperid, shopid, null);
//			if(paperquesscore!=null){
//				// 更新分数
//				typeDao.updatePatternscore(paperid, questypeid, -paperquesscore, 2, shopid);
//				paperDao.updateScore(paperid, -paperquesscore, 2, shopid);
//			}else{
//				paperDao.updateModInfo(paperid, shopid, null);
//			}
//		}
//		
//		// flush query cache
//		CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", Paper.ObjectType, paperid.toString());
//		return result;
//	}
	    
	/**
	 * 从txt文件中导入试卷中的试题
	 * @param input
	 * @param wareid
	 * @param shopid
	 * @param productid
	 * @return 返回导入后的错误信息list, 最后一个元素是导入的成功数目
	 * @throws Exception
	 */
	public List<String> importPaperQuesFromTxt(Paper paperVO, InputStream input, Long wareid) 
	{
    	if(input==null || wareid==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);		
        int successNumber = 0;
        Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
        // 插入paper
        paperVO.setCreatetime(currentDate);
        paperVO.setStatus(PaperConstant.PaperStatus_valide);
        paperVO.setPhase(PaperConstant.PaperPhase_finished);
        paperVO.setPapertotalscore(0f);
        paperVO.setModitime(currentDate);
        paperVO.setVersion(1);
        paperVO.setGenetype(PaperConstant.GeneType_fixed);
		Long paperid = paperDao.insert(paperVO);
		paperVO.setPaperid(paperid);
		// 插入paper property
		Paperprop propVO = new Paperprop();
		propVO.setPaperid(paperid);
		propVO.setShopid(paperVO.getShopid());
		propVO.setOrgname(paperVO.getOrgname());
		propVO.setProductbaseid(paperVO.getProductbaseid());
		propVO.setProductname(paperVO.getProductname());
		propVO.setWarenamestr(paperVO.getWarenamestr());
		propVO.setPaperdesc(paperVO.getPaperdesc());
		PaperpropDao propDao = BOFactory.getPaperpropDao();
		propDao.insert(propVO);
        // 准备参数Map, 用于设题每道题目属性
		Map map = new HashMap();
        map.put("wareid",wareid);
        map.put("shopid",paperVO.getShopid());
        map.put("paperid",paperid);
        map.put("productbaseid",paperVO.getProductbaseid());
        map.put("quescreatetime", currentDate);
        map.put("filetype", QuestionConstant.FileType_None);
        map.put("creatorid", paperVO.getCreatorid());
	    // 解析excel文件从中提取出题目	
        ImportQuesFromTxtHelper helper = ImportQuesFromTxtHelper.getInstance();
        List[] importRtnArr = helper.readFile(input, QuestionImportConstant.QuestionType_PaperQues, map);
        List queslist = importRtnArr[0];
        if(queslist!=null && queslist.size()>0){
        	// paperquesType的整理写入
        	List patternList = importRtnArr[2];
        	typeDao.insertBatch(patternList);
        	// 将这些问题插入题库，并更新题库题目数, insert后question对象会有quesid
        	questionDao.insertBatch(queslist);
            BOFactory.getQuestionLogic().updateWareQuesNumber(wareid, paperVO.getProductbaseid(), queslist.size());
            //循环quesList构造paperquesList, 批量插入
            List<Paperques> paperquesList = new ArrayList<Paperques>();
            Question quesvoTemp = null;
            for(int i=0; i<queslist.size(); i++){
            	quesvoTemp = (Question)queslist.get(i);
            	Paperques paperquesVO = new Paperques();
            	paperquesVO.copyQuestion(quesvoTemp);
            	paperquesList.add(paperquesVO);
            }
            // TODO 是否需要更新paperques中的paperQuesScore，需要测试时注意
            successNumber= paperquesDao.insertBatch(paperquesList);
        }
        // 导入完成，更新试卷的总分数
        paperDao.autoUpdateScore(paperid, paperVO.getShopid());
        List<String> messageList = (List<String>)importRtnArr[1];
        messageList.add(String.valueOf(successNumber));
        return messageList;
    }
	
	/**
	 * 刷新试卷cache, 同时更新试卷分数
	 * @return
	 */
	public void flushPaperByQues(Collection<Paper> paperlist, Long quesid, 
			 	 				 Integer changeoptnum){
		if(paperlist==null||paperlist.isEmpty())
			return;
		Paperques paperquesvo = null;
		// flush query cache
		for(Paper votemp : paperlist){ 
			paperquesvo = paperquesDao.qryPaperquesByQues(quesid, votemp.getPaperid());
			if(changeoptnum!=null && changeoptnum!=0 && paperquesvo!=null)
			{
				typeDao.updatePatternscore(votemp.getPaperid(), paperquesvo.getQuestypeid(), 
								paperquesvo.getPaperquesscore()*changeoptnum, 2, paperquesvo.getShopid());
				paperDao.updateScore(paperquesvo.getPaperid(), paperquesvo.getPaperquesscore()*changeoptnum, 
									 2, paperquesvo.getShopid());
			}
			// flush cache
			CacheSynchronizer.getInstance().pubFlush("netTest.paperCache", 
								Paper.ObjectType, votemp.getPaperid().toString());
		}
	}
	
	
	/**
	 * static spring getMethod
	 */
	 public static PaperquesLogic getPaperquesInstance(){
		 PaperquesLogic logic = (PaperquesLogic)BeanFactory.getBeanFactory().getBean("paperquesLogic");
		 
	     return logic;
	 }

	public PaperDao getPaperDao() {
		return paperDao;
	}

	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	public PaperquestypeDao getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(PaperquestypeDao typeDao) {
		this.typeDao = typeDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public PaperquesDao getPaperquesDao() {
		return paperquesDao;
	}

	public void setPaperquesDao(PaperquesDao paperquesDao) {
		this.paperquesDao = paperquesDao;
	}

//	@Override
//	public void onEvent(Event event) {
//		Map paraMap = event.getParaMap();
//		Long quesid = new Long(paraMap.get("quesid").toString());
//		Long paperid = new Long(paraMap.get("paperid").toString());
//		Long shopid = new Long(paraMap.get("shopid").toString());
//		Integer quesoptnum_changed = new Integer(paraMap.get("quesoptnum_changed").toString());
//		String eventType = event.getEventType();
//		if(EventHandlerNetTest.EventType_Question_Change_UpdatePaper.equals(eventType)){
//			updatePaperByQuesChange(quesid, paperid, shopid, quesoptnum_changed);
//		}
//		
//	}

}
