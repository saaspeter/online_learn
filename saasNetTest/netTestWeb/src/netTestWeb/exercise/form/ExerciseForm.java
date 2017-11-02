
package netTestWeb.exercise.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.dto.ExerciseQuery;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exerquestype;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ExerciseForm extends BaseForm {
	
	private Exercise vo;
	private ExerciseQuery queryVO;
	
	/** 问题类型集合，内是Paperquestype **/
	private List<Exerquestype> questypeList;
	
    /** 显示问题类型的集合，里是LabelValueVO **/
    private List quetyporiList;
    
	/** 1:管理产品时查看练习，2:学员学习时查看的练习 **/
	private int listtype;
	
	/** 正在使用的题目类型（展现编辑的题型） **/
    private Integer questypeUse;
    
//    private Long questypeidUse;
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Exercise();
		queryVO = new ExerciseQuery();
		questypeList = null;
		quetyporiList = null;
//		questypeidUse = null;
		questypeUse = null;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public ExerciseQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ExerciseQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Exercise getVo() {
		return vo;
	}

	public void setVo(Exercise vo) {
		this.vo = vo;
	}

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}

	public List getQuetyporiList() {
		return quetyporiList;
	}

	public void setQuetyporiList(List quetyporiList) {
		this.quetyporiList = quetyporiList;
	}
	
	public List<Exerquestype> getQuestypeList_clean() {
		List<Exerquestype> listTemp = new ArrayList<Exerquestype>();
        if(questypeList!=null&&questypeList.size()>0){
        	Exerquestype voTemp = null;
        	for(int i=0;i<questypeList.size();i++)
        	{
        		voTemp = (Exerquestype)questypeList.get(i);
        		if(voTemp.getQuestype()!=null){
        			listTemp.add(voTemp);
        		}
        	}
        }
        return listTemp;
	}

	public List<Exerquestype> getQuestypeList() {
		return questypeList;
	}

	public void setQuestypeList(List<Exerquestype> questypeList) {
		this.questypeList = questypeList;
	}

	public Integer getQuestypeUse() {
		return questypeUse;
	}

	public void setQuestypeUse(Integer questypeUse) {
		this.questypeUse = questypeUse;
	}
		
}
