
package netTestWeb.category.form;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.ProductcategoryvalueQuery;
import platform.vo.Productcategoryvalue;


public class ProductcategoryvalueForm extends BaseForm {
	
	private Productcategoryvalue vo;
	private ProductcategoryvalueQuery queryVO;
	/** 该目录已经设置的国家语言字符串 **/
	private String selectedLocaleStr;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Productcategoryvalue();
		queryVO = new ProductcategoryvalueQuery();
		selectedLocaleStr = null;
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
	
	public ProductcategoryvalueQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ProductcategoryvalueQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Productcategoryvalue getVo() {
		return vo;
	}

	public void setVo(Productcategoryvalue vo) {
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

	public String getSelectedLocaleStr() {
		return selectedLocaleStr;
	}

	public void setSelectedLocaleStr(String selectedLocaleStr) {
		this.selectedLocaleStr = selectedLocaleStr;
	}
		
}
