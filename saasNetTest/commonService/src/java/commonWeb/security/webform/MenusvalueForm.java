
package commonWeb.security.webform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonWeb.base.BaseForm;
import commonWeb.security.dto.MenusvalueQuery;
import commonWeb.security.vo.Menusvalue;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class MenusvalueForm extends BaseForm {
	
	private Menusvalue vo;
	private MenusvalueQuery queryVO;
	
	private String[] keys;
	
	private int editType;
	/** 搜索类型�?为只搜索本目录，2为包括下级数据的搜索�?为包含所有级别的数据 **/
	private String complexSearchDivStatus;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Menusvalue();
		queryVO = new MenusvalueQuery();
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
	
	public MenusvalueQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(MenusvalueQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Menusvalue getVo() {
		return vo;
	}

	public void setVo(Menusvalue vo) {
		this.vo = vo;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}
		
}
