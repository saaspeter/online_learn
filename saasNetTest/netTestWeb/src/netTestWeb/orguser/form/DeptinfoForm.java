
package netTestWeb.orguser.form;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTest.orguser.dto.DeptinfoQuery;
import netTest.orguser.vo.Deptinfo;
import netTest.orguser.vo.Orgbase;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class DeptinfoForm extends BaseForm {
	
	private Orgbase orgbase;
	private Deptinfo vo;
	private DeptinfoQuery queryVO;
	
	private String[] keys;
	
	private int editType;
	/** 搜索类型�?1为只搜索本目录，2为包括下级数据的搜索�?3为包含所有级别的数据 **/
	private String complexSearchDivStatus;

	private Locale locale;
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Deptinfo();
		queryVO = new DeptinfoQuery();
		
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
	
	public DeptinfoQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(DeptinfoQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Deptinfo getVo() {
		return vo;
	}

	public void setVo(Deptinfo vo) {
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

	public Orgbase getOrgbase() {
		return orgbase;
	}

	public void setOrgbase(Orgbase orgbase) {
		this.orgbase = orgbase;
	}
		
}
