
package netTestWeb.category.form;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.shop.vo.Prodcateshop;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ProdcateshopForm extends BaseForm {
	
	private Prodcateshop vo;
	/** category string, use commer as interval **/
	private String categoryidStr;
	/** 用于在产品目录页面判断并显示信息 **/
	private Integer childsize;
	
	/** 0代表进入管理页面，1代表选择课程界面 **/
	private int urltype;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Prodcateshop();
		urltype = 0;
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

	public Prodcateshop getVo() {
		return vo;
	}

	public void setVo(Prodcateshop vo) {
		this.vo = vo;
	}

	public String getCategoryidStr() {
		return categoryidStr;
	}

	public void setCategoryidStr(String categoryidStr) {
		this.categoryidStr = categoryidStr;
	}

	public Integer getChildsize() {
		if(childsize==null){
			childsize = 1;
		}
		return childsize;
	}

	public void setChildsize(Integer childsize) {
		this.childsize = childsize;
	}

	public int getUrltype() {
		return urltype;
	}

	public void setUrltype(int urltype) {
		this.urltype = urltype;
	}
	
}
