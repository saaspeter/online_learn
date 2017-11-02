
package netTestWeb.category.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.SysproductcateQuery;
import platform.vo.Sysproductcate;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysproductcateForm extends BaseForm {
	
	private Sysproductcate vo;
	private SysproductcateQuery queryVO;

	private List showList;
	
	private Locale locale;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysproductcate();
		queryVO = new SysproductcateQuery();
		showList = new ArrayList();
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
	
	public SysproductcateQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysproductcateQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysproductcate getVo() {
		return vo;
	}

	public void setVo(Sysproductcate vo) {
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

	public List getShowList() {
		return showList;
	}

	public void setShowList(List showList) {
		this.showList = showList;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
		
}
