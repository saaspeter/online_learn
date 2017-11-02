
package netTestWeb.exercise.form;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.dto.ExerquesQuery;
import netTest.exercise.vo.Exerques;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ExerquesForm extends BaseForm {
	
	private Exerques vo;
	private ExerquesQuery queryVO;
	
	/** 新增或变更题目时选择的新的题目 **/
	private String quesidStr;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Exerques();
		queryVO = new ExerquesQuery();
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
	
	public ExerquesQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ExerquesQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Exerques getVo() {
		return vo;
	}

	public void setVo(Exerques vo) {
		this.vo = vo;
	}

	public String getQuesidStr() {
		return quesidStr;
	}

	public void setQuesidStr(String quesidStr) {
		this.quesidStr = quesidStr;
	}

		
}
