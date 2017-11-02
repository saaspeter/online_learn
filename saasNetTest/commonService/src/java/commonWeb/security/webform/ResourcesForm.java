
package commonWeb.security.webform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonWeb.base.BaseForm;
import commonWeb.security.dto.ResourcesQuery;
import commonWeb.security.vo.Resources;



/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ResourcesForm extends BaseForm {
	
	private Resources vo;
	private ResourcesQuery queryVO;
	
	//private String[] keys;
	
	/** 搜索类型�?1为只搜索本目录，2为包括下级数据的搜索�?3为包含所有级别的数据 **/
	//private String complexSearchDivStatus;
	
	/** 查询权限类型，0为权限列表，1为显示角色对应的权限列表 **/
	private String rescListType;


	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Resources();
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

	public Resources getVo() {
		return vo;
	}

	public void setVo(Resources vo) {
		this.vo = vo;
	}

	public String getRescListType() {
		return rescListType;
	}

	public void setRescListType(String rescListType) {
		this.rescListType = rescListType;
	}

		
}
