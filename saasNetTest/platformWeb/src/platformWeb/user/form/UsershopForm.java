
package platformWeb.user.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.UsershopQuery;
import platform.vo.Usershop;
import platformWeb.base.BaseForm;


public class UsershopForm extends BaseForm {
	
	public static final String Roletype_All = "all";
	public static final String Roletype_ExamMag = "ExamMag";
	
	private Usershop vo;
	private UsershopQuery queryVO;
	
	private String roletype;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Usershop();
		queryVO = new UsershopQuery();
		roletype = Roletype_ExamMag;
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
	
	public UsershopQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UsershopQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Usershop getVo() {
		return vo;
	}

	public void setVo(Usershop vo) {
		this.vo = vo;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
		
}
