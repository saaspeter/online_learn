
package netTestWeb.user.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class UserServiceForm extends BaseForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5096022728943898416L;
	
	/**
	 * 最近学习的产品列表
	 */
	private List productList;
	/**
	 * 需要参加的考试列表
	 */
	private List testList;
	/**
	 * 需要做的练习列表
	 */	
	private List exerList;
	
	/**
	 * 用户的通知列表
	 */
	private List notifyList;

	private String message;
	
	/** 用户是否有选修课程，如果没有选修过任何课程，则界面上会显示选课按钮 **/
	private boolean hasproduct;
	
	private String clientid_google;
	
	/** 我学习的课程数量 **/
	private int mylearnprodnum;
	/** 我教授课程的数量 **/
	private int myadminprodnum;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        hasproduct = true;
        productList = null;
        testList = null;
        exerList = null;
        notifyList = null;
        message = null;
        mylearnprodnum = 0;
        myadminprodnum = 0;
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

	public List getExerList() {
		return exerList;
	}

	public void setExerList(List exerList) {
		this.exerList = exerList;
	}

	public List getProductList() {
		return productList;
	}

	public void setProductList(List productList) {
		this.productList = productList;
	}

	public List getTestList() {
		return testList;
	}

	public void setTestList(List testList) {
		this.testList = testList;
	}

	public List getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List notifyList) {
		this.notifyList = notifyList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHasproduct() {
		return hasproduct;
	}

	public void setHasproduct(boolean hasproduct) {
		this.hasproduct = hasproduct;
	}

	public String getClientid_google() {
		return clientid_google;
	}

	public void setClientid_google(String clientid_google) {
		this.clientid_google = clientid_google;
	}

	public int getMylearnprodnum() {
		return mylearnprodnum;
	}

	public void setMylearnprodnum(int mylearnprodnum) {
		this.mylearnprodnum = mylearnprodnum;
	}

	public int getMyadminprodnum() {
		return myadminprodnum;
	}

	public void setMyadminprodnum(int myadminprodnum) {
		this.myadminprodnum = myadminprodnum;
	}
	
}
