
package netTestWeb.wareques.form;

import javax.servlet.http.HttpServletRequest;

import netTest.product.vo.Productbase;
import netTest.wareques.dto.WareQuery;
import netTest.wareques.vo.Ware;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class WareForm extends BaseForm {
	
	private Ware vo;
	private WareQuery queryVO;
	/** 转向的列表页面类型：1为管理列表页面；2为选择列表页面 **/
	private String listType;
	/** 1代表单选，2代表多选 **/
	private String selectType;
	/** 已经选择过的题库的id的集合 **/
	private String selectedIds;
	
	private Productbase productvo;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Ware();
		queryVO = new WareQuery();
		listType = "1";
		selectType = "2";
		selectedIds = null;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String msg = super.validateData(vo);
		ActionErrors errors = null;
		if(msg!=null && msg.trim().length()>0){
			errors = new ActionErrors();
			ActionMessage aError = new ActionMessage(msg);
            errors.add("warename", aError);
		}
		return errors;
	}
	
	public WareQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(WareQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Ware getVo() {
		return vo;
	}

	public void setVo(Ware vo) {
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

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public Productbase getProductvo() {
		return productvo;
	}

	public void setProductvo(Productbase productvo) {
		this.productvo = productvo;
	}
	
}
