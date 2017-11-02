
package commonWeb.security.webform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonWeb.base.BaseForm;
import commonWeb.security.dto.ResourcesvalueQuery;
import commonWeb.security.vo.Resourcesvalue;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ResourcesvalueForm extends BaseForm {
	
	private Resourcesvalue vo;
	private ResourcesvalueQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Resourcesvalue();
		queryVO = new ResourcesvalueQuery();
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		//if(queryVO!=null){
		//   if(request.getParameter("queryVO.categorylevel")==null
		//      ||(!((String)request.getParameter("queryVO.categorylevel")).matches("^\\d+$")))
		//    	queryVO.setCategorylevel(null);
		//}
		return null;
	}
	
	public ResourcesvalueQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ResourcesvalueQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Resourcesvalue getVo() {
		return vo;
	}

	public void setVo(Resourcesvalue vo) {
		this.vo = vo;
	}
		
}
