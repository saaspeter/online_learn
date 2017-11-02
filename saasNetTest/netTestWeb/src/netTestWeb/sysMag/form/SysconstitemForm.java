
package netTestWeb.sysMag.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;
import netTestWeb.base.WebConstant;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.dto.SysconstitemQuery;
import commonTool.biz.vo.Sysconstitem;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstitemForm extends BaseForm {
	
	private Sysconstitem vo;
	private SysconstitemQuery queryVO;
	/** 用于显示所属常量的名称 **/
	private String constname;
	/** 下拉语言列表 **/
	private List localesList;
	private String[] keys;
	private int editType = WebConstant.editType_view;
	/** 搜索类型�?1为只搜索本目录，2为包括下级数据的搜索�?3为包含所有级别的数据 **/
	private String complexSearchDivStatus;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysconstitem();
		queryVO = new SysconstitemQuery();
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
	
	public SysconstitemQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysconstitemQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysconstitem getVo() {
		return vo;
	}

	public void setVo(Sysconstitem vo) {
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

	public List getLocalesList() {
		return localesList;
	}

	public void setLocalesList(List localesList) {
		this.localesList = localesList;
	}

	public String getConstname() {
		return constname;
	}

	public void setConstname(String constname) {
		this.constname = constname;
	}
		
}
