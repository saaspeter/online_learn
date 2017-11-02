
package netTestWeb.platform.shop.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.ShopfuncQuery;
import platform.vo.Shopfunc;

/** 
 * MyEclipse Struts
 */
public class ShopfuncForm extends BaseForm {
	
	private Shopfunc vo;
	private ShopfuncQuery queryVO;
	private String functioncode;
	

	private List datalist;
	
	/** 平台系统功能列表 **/
	private List funcPlatList;
	/** 教育系统功能列表 **/
	private List funcEduList;
	/** 选中的功能列表的集合 **/
	private String[] funcArr;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopfunc();
		queryVO = new ShopfuncQuery();
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
	
	public ShopfuncQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopfuncQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopfunc getVo() {
		return vo;
	}

	public void setVo(Shopfunc vo) {
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

	public String[] getFuncArr() {
		return funcArr;
	}

	public void setFuncArr(String[] funcArr) {
		this.funcArr = funcArr;
	}

	public List getFuncEduList() {
		return funcEduList;
	}

	public void setFuncEduList(List funcEduList) {
		this.funcEduList = funcEduList;
	}

	public List getFuncPlatList() {
		return funcPlatList;
	}

	public void setFuncPlatList(List funcPlatList) {
		this.funcPlatList = funcPlatList;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}
	
	public String getFunctioncode() {
		return functioncode;
	}

	public void setFunctioncode(String functioncode) {
		this.functioncode = functioncode;
	}

}
