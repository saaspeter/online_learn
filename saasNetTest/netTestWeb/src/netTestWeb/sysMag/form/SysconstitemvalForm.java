
package netTestWeb.sysMag.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import netTestWeb.base.BaseForm;

import commonTool.biz.dto.SysconstitemvalQuery;
import commonTool.biz.vo.Sysconstitemval;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstitemvalForm extends BaseForm {
	
	private Sysconstitemval vo;
	private SysconstitemvalQuery queryVO;
	
	private List localesList;
	
	private String[] keys;
	private int editType;
	/** 搜索类型�?1为只搜索本目录，2为包括下级数据的搜索�?3为包含所有级别的数据 **/
	private String complexSearchDivStatus;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Sysconstitemval();
		queryVO = new SysconstitemvalQuery();
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
	
	public SysconstitemvalQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(SysconstitemvalQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Sysconstitemval getVo() {
		return vo;
	}

	public void setVo(Sysconstitemval vo) {
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
		
}
