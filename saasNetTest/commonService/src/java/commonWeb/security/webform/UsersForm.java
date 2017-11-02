
package commonWeb.security.webform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.UserQuery;
import platform.vo.User;

import commonWeb.base.BaseForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class UsersForm extends BaseForm {
	
	private User vo;
	private UserQuery queryVO;
	
	private String[] keys;

	/** 搜索类型�?1为只搜索本目录，2为包括下级数据的搜索�?3为包含所有级别的数据 **/
	private String complexSearchDivStatus;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new User();
		queryVO = new UserQuery();
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
	
	public UserQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UserQuery queryVO) {
		this.queryVO = queryVO;
	}

	public User getVo() {
		return vo;
	}

	public void setVo(User vo) {
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
		
}
