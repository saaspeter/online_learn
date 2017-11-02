
package netTestWeb.category.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.vo.Hotcategory;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class HotcategoryForm extends BaseForm {
	
	private Hotcategory vo;
	
	private List hotcateList;
	// 1:列出hot category用于管理, 2:列出用于显示
	private int listtype;
	
	/** 新增或删除热门目录的范围。1为仅限本localeid, 2为对系统所有的localeid都起作用 **/
	private int scope;
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Hotcategory();
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

	public Hotcategory getVo() {
		return vo;
	}

	public void setVo(Hotcategory vo) {
		this.vo = vo;
	}

	public List getHotcateList() {
		return hotcateList;
	}

	public void setHotcateList(List hotcateList) {
		this.hotcateList = hotcateList;
	}

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}
		
}
