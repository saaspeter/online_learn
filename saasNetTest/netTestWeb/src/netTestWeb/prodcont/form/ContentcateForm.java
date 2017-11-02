
package netTestWeb.prodcont.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.prodcont.dto.ContentcateQuery;
import netTest.prodcont.vo.Contentcate;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ContentcateForm extends BaseForm {
	
	private Contentcate vo;
	private ContentcateQuery queryVO;
	
	private String productName;
	
	private String parentcateName;
	
	/** 转向的列表页面类型：1为管理列表页面；2为选择列表页面 **/
	private String listType;
	
	/** 1代表单选，2代表多选 **/
	private String selectType;
	
	private List datalist;
	
	private Contentcate topcatevo;
	
	private boolean isadmin;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Contentcate();
		queryVO = new ContentcateQuery();
		productName = null;
		listType = null;
		selectType = "2";
		isadmin = false;
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
	
	public ContentcateQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ContentcateQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Contentcate getVo() {
		return vo;
	}

	public void setVo(Contentcate vo) {
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}

	public String getParentcateName() {
		return parentcateName;
	}

	public void setParentcateName(String parentcateName) {
		this.parentcateName = parentcateName;
	}

	public Contentcate getTopcatevo() {
		return topcatevo;
	}

	public void setTopcatevo(Contentcate topcatevo) {
		this.topcatevo = topcatevo;
	}

	public boolean isIsadmin() {
		return isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}
	
}
