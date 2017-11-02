package netTest.paper.logic.impl;

import java.util.List;

import netTest.bean.BeanFactory;
import netTest.paper.dao.PaperDao;
import netTest.paper.dao.PaperpatternratioDao;
import netTest.paper.dao.PaperquesDao;
import netTest.paper.dao.PaperquestypeDao;
import netTest.paper.dto.PaperquesQuery;
import netTest.paper.logic.PaperquestypeLogic;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.constant.QuestionConstant;

import commonTool.exception.HasReferenceException;

public class PaperquestypeLogicImpl implements PaperquestypeLogic {
	
	private PaperquesDao quesDao;
	private PaperquestypeDao dao;
	private PaperpatternratioDao ratioDao;
	private PaperDao paperDao;

	/**
	 * 保存试卷题型配置表
	 * @param typeVO
	 * @return
	 */
	public Long save(Paperquestype typeVO){
		if(typeVO==null)
			return null;
		Long patternid = typeVO.getPatternid();
		if(patternid==null){
			Long maxquestypeid = dao.getMaxQuestypeid(typeVO.getShopid(), typeVO.getPaperid());
			maxquestypeid++;
			typeVO.setQuestypeid(maxquestypeid);
			typeVO.setPatternquesnum(0);
			typeVO.setPatternscore(0f);
			patternid = dao.insert(typeVO);
		}else{
			float patternScore = -9999;
			if(typeVO.getQuesscore_old()!=null&&!typeVO.getQuesscore_old().equals(typeVO.getQuesscore())){
				// 把试卷题目表中questypeid对应的题目的分值都改掉，同时修改题型总分和试卷总分
				quesDao.updateScoreByPatt(typeVO.getShopid(),typeVO.getPaperid(), typeVO.getQuestypeid(), typeVO.getQuesscore());
				int nums = quesDao.qryOptnumByPatt(typeVO.getShopid(),typeVO.getPaperid(), typeVO.getQuestypeid());
				patternScore = nums*typeVO.getQuesscore();
				float scoreDiff = nums*(typeVO.getQuesscore()-typeVO.getQuesscore_old());
				paperDao.updateScore(typeVO.getPaperid(), scoreDiff, 2, typeVO.getShopid());
			}
			if(patternScore>0){
			   typeVO.setPatternscore(patternScore);
			}
			dao.save(typeVO);
		}
		return patternid;
	}
	
	/**
	 * 删除试卷题型，如果该题型下还有题目的话抛异常
	 * @param patternid
	 * @return
	 */
	public int delete(Long patternid){
		Paperquestype vo = dao.selectByPK(patternid);
		if(vo!=null){
		   PaperquesQuery quesQuery = new PaperquesQuery();
		   quesQuery.setPaperid(vo.getPaperid());
		   quesQuery.setShopid(vo.getShopid());
		   quesQuery.setQuestypeid(vo.getQuestypeid());
		   int quesnum = quesDao.countByVO(quesQuery);
		   if(quesnum<1){
			   ratioDao.deleteByQuesPattern(vo.getPaperid(), vo.getQuestypeid());
			   dao.deleteByPK(patternid);
		   }else{
			   throw new HasReferenceException(patternid.toString());
		   }
		}
		return 1;
	}
	
	/**
	 * 判断试卷题型中包含哪种类型的题型
	 * @param paperid
	 * @return 1:客观题；2：主观题；3：客观主观题都有
	 */
	public int paperHasQuestype(Long paperid){
		int rtn = 0;
		if(paperid==null)
			return rtn;
		List list = dao.qryByPaperid(paperid);
		if(list!=null&&list.size()>0){
			boolean hasObj = false;
			boolean hasSub = false;
			Short checktype = null;
			Paperquestype vo = null;
			for(int i=0;i<list.size();i++){
				vo = (Paperquestype)list.get(i);
				checktype = QuestionConstant.getQueschecktype(vo.getQuestype());
				if(QuestionConstant.Queschecktype_auto.equals(checktype)){
					hasObj = true;
				}else if(QuestionConstant.Queschecktype_manual.equals(checktype)
						||QuestionConstant.Queschecktype_both.equals(checktype)){
					hasSub = true;
				}
				if(hasObj&&hasSub)
					rtn = 3;
				else if(hasObj)
					rtn = 1;
				else 
					rtn = 2;
			}
		}
		return rtn;
	}
	
	/**
	 * static spring getMethod
	 */
	 public static PaperquestypeLogic getInstance(){
		 PaperquestypeLogic logic = (PaperquestypeLogic)BeanFactory.getBeanFactory().getBean("paperquestypeLogic");
	     return logic;
	 }

	public PaperquestypeDao getDao() {
		return dao;
	}

	public void setDao(PaperquestypeDao dao) {
		this.dao = dao;
	}

	public PaperquesDao getQuesDao() {
		return quesDao;
	}

	public void setQuesDao(PaperquesDao quesDao) {
		this.quesDao = quesDao;
	}

	public PaperpatternratioDao getRatioDao() {
		return ratioDao;
	}

	public void setRatioDao(PaperpatternratioDao ratioDao) {
		this.ratioDao = ratioDao;
	}

	public PaperDao getPaperDao() {
		return paperDao;
	}

	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	
}
