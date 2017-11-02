
package netTestWeb.sysMag.form;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;
import netTestWeb.base.WebConstant;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.dto.SysconstQuery;
import commonTool.biz.vo.Sysconst;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstForm extends BaseForm {
	
	private Sysconst vo;
	private SysconstQuery queryVO;
	
	private String[] keys;
	
	private List sysList;
	private Locale locale;
	private List localesList;
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
		vo = new Sysconst();
		queryVO = new SysconstQuery();
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
	
	public SysconstQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysconstQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysconst getVo() {
		return vo;
	}

	public void setVo(Sysconst vo) {
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List getSysList() {
		return sysList;
	}

	public void setSysList(List sysList) {
		this.sysList = sysList;
	}

	public List getLocalesList() {
		return localesList;
	}

	public void setLocalesList(List localesList) {
		this.localesList = localesList;
	}
		
}
