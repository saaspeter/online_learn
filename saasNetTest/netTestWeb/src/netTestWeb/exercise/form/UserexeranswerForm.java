
package netTestWeb.exercise.form;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.dto.UserexeranswerQuery;
import netTest.exercise.vo.Userexeranswer;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class UserexeranswerForm extends BaseForm {
	
	private Userexeranswer vo;
	private UserexeranswerQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Userexeranswer();
		queryVO = new UserexeranswerQuery();
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
	
	public UserexeranswerQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UserexeranswerQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Userexeranswer getVo() {
		return vo;
	}

	public void setVo(Userexeranswer vo) {
		this.vo = vo;
	}

		
}
