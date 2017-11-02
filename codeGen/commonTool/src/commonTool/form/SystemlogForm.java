
package commonToolWeb.biz.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import commonToolWeb.base.BaseForm;
import commonTool.biz.dto.SystemlogQuery;
import commonTool.biz.vo.Systemlog;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SystemlogForm extends BaseForm {
	
	private Systemlog vo;
	private SystemlogQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Systemlog();
		queryVO = new SystemlogQuery();
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
	
	public SystemlogQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SystemlogQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Systemlog getVo() {
		return vo;
	}

	public void setVo(Systemlog vo) {
		this.vo = vo;
	}

		
}
