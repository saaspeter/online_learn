
package acegiWeb.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import acegiWeb.base.BaseForm;
import acegi.dto.CustorderQuery;
import acegi.vo.Custorder;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class CustorderForm extends BaseForm {
	
	private Custorder vo;
	private CustorderQuery queryVO;
	
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
		vo = new Custorder();
		queryVO = new CustorderQuery();
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
	
	public CustorderQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(CustorderQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Custorder getVo() {
		return vo;
	}

	public void setVo(Custorder vo) {
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
