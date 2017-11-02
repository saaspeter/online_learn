
package commonWeb.security.webform;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import sun.security.util.Resources;
import commonWeb.base.BaseForm;
import commonWeb.security.dto.ResourcesQuery;
import commonWeb.security.vo.RoleResc;
import commonWeb.security.vo.Roles;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class RoleRescForm extends BaseForm {
	
	private Resources rescVO;
	
	private Roles rolesVO;
	private RoleResc vo;
	private ResourcesQuery queryVO;

	/** 资源字符串 **/
	private String rescIds;
	
	/** 对应界面上的：是否包含该菜单. 0不包含，1包含 **/
	private String includeMenuCheck;
	
	private List rescList;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		rescVO = new Resources();
		rolesVO = new Roles();
		vo = new RoleResc();
		queryVO = new ResourcesQuery();
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
	
	public ResourcesQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ResourcesQuery queryVO) {
		this.queryVO = queryVO;
	}

	public RoleResc getVo() {
		return vo;
	}

	public void setVo(RoleResc vo) {
		this.vo = vo;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public Resources getRescVO() {
		return rescVO;
	}

	public void setRescVO(Resources rescVO) {
		this.rescVO = rescVO;
	}

	public Roles getRolesVO() {
		return rolesVO;
	}

	public void setRolesVO(Roles rolesVO) {
		this.rolesVO = rolesVO;
	}

	public String getRescIds() {
		return rescIds;
	}

	public void setRescIds(String rescIds) {
		this.rescIds = rescIds;
	}

	public String getIncludeMenuCheck() {
		return includeMenuCheck;
	}

	public void setIncludeMenuCheck(String includeMenuCheck) {
		this.includeMenuCheck = includeMenuCheck;
	}

	public List getRescList() {
		return rescList;
	}

	public void setRescList(List rescList) {
		this.rescList = rescList;
	}
		
}
