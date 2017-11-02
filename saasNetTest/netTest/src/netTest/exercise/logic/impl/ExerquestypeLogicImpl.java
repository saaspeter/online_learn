package netTest.exercise.logic.impl;

import java.util.List;

import netTest.bean.BeanFactory;
import netTest.exercise.dao.ExerquesDao;
import netTest.exercise.dao.ExerquestypeDao;
import netTest.exercise.dto.ExerquesQuery;
import netTest.exercise.logic.ExerquestypeLogic;
import netTest.exercise.vo.Exerquestype;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.constant.QuestionConstant;

import commonTool.exception.HasReferenceException;

public class ExerquestypeLogicImpl implements ExerquestypeLogic {
	
	private ExerquesDao quesDao;
	private ExerquestypeDao dao;

	/**
	 * 保存试卷题型配置表
	 * @param typeVO
	 * @return
	 */
	public Long save(Exerquestype typeVO){
		if(typeVO==null)
			return null;
		Long patternid = typeVO.getPatternid();
		if(patternid==null){
			typeVO.setPatternquesnum(0);
			patternid = dao.insert(typeVO);
		}else{
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
		Exerquestype vo = dao.selectByPK(patternid);
		if(vo!=null){
			ExerquesQuery quesQuery = new ExerquesQuery();
		   quesQuery.setExerid(vo.getExerid());
		   quesQuery.setShopid(vo.getShopid());
		   quesQuery.setQuestype(vo.getQuestype());
		   int quesnum = quesDao.countByVO(quesQuery);
		   if(quesnum<1){
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
	public int exerHasQuestype(Long exerid){
		int rtn = 0;
		if(exerid==null)
			return rtn;
		List list = dao.qryByExerid(exerid);
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
	 public static ExerquestypeLogic getInstance(){
		 ExerquestypeLogic logic = (ExerquestypeLogic)BeanFactory.getBeanFactory().getBean("exerquestypeLogic");
	     return logic;
	 }

	public ExerquestypeDao getDao() {
		return dao;
	}

	public void setDao(ExerquestypeDao dao) {
		this.dao = dao;
	}

	public ExerquesDao getQuesDao() {
		return quesDao;
	}

	public void setQuesDao(ExerquesDao quesDao) {
		this.quesDao = quesDao;
	}

	
}
