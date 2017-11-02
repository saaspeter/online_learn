
package netTestWeb.exercise.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.exercise.dto.ExeruserQuery;
import netTest.exercise.vo.Exercise;
import netTest.exercise.vo.Exeruser;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ExeruserForm extends BaseForm {
	
	private Exeruser vo;
	private ExeruserQuery queryVO;
	private Exercise exerVO;
	
	private List<Exeruser> datalist;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Exeruser();
		queryVO = new ExeruserQuery();
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
	
	public ExeruserQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ExeruserQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Exeruser getVo() {
		return vo;
	}

	public void setVo(Exeruser vo) {
		this.vo = vo;
	}

	public Exercise getExerVO() {
		return exerVO;
	}

	public void setExerVO(Exercise exerVO) {
		this.exerVO = exerVO;
	}

	public List<Exeruser> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<Exeruser> datalist) {
		this.datalist = datalist;
	}

}
