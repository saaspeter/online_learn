
package netTestWeb.product.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.product.dto.UserproductQuery;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userproduct;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class UserproductForm extends BaseForm {
	
	private Userproduct vo;
	private UserproductQuery queryVO;
	/** 0代表列出用户自己的课程，1代表商店管理员列出本商店的用户课程，2代表超级管理员查看用户课程 **/
	private int listType;
	
	private Long userid;
	
	private String shopname;
	private String categoryname;
	/** 用户在本商店中是否还有产品引用，当删除完用户产品后用于提示是否可以删除用户。1还有产品，0没有产品了. 
	 * 默认为1 **/
	private String userhasprod;
	
	/** 状态改变描述 */
	private String statusdesc;
	
    /** 查看者身份，目前只有"self"，代表用户自己看自己的产品 **/
	private String viewidentity;
	/** 用户是否可以被授予管理该产品的能力，如果没有则在界面隐藏选择管理方式的界面 **/
	private boolean caneditproduct;
	
	/** 列出课程人员类型。0为只能查看其中一种产品的学习人员，1为商店管理员查看所有课程的userproduct **/
	private int listprodusertype;
	
	private List datalist;
	
	/** 选择的人员id string **/
	private String useridStr;
	
	private Productbase productvo;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Userproduct();
		queryVO = new UserproductQuery();
		listType = 0;
		userhasprod = "1";
		caneditproduct = false;
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
	
	public UserproductQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(UserproductQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Userproduct getVo() {
		return vo;
	}

	public void setVo(Userproduct vo) {
		this.vo = vo;
	}

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUserhasprod() {
		return userhasprod;
	}

	public void setUserhasprod(String userhasprod) {
		this.userhasprod = userhasprod;
	}

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}

	public String getViewidentity() {
		return viewidentity;
	}

	public void setViewidentity(String viewidentity) {
		this.viewidentity = viewidentity;
	}

	public boolean isCaneditproduct() {
		return caneditproduct;
	}

	public void setCaneditproduct(boolean caneditproduct) {
		this.caneditproduct = caneditproduct;
	}

	public int getListprodusertype() {
		return listprodusertype;
	}

	public void setListprodusertype(int listprodusertype) {
		this.listprodusertype = listprodusertype;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}

	public String getUseridStr() {
		return useridStr;
	}

	public void setUseridStr(String useridStr) {
		this.useridStr = useridStr;
	}

	public Productbase getProductvo() {
		return productvo;
	}

	public void setProductvo(Productbase productvo) {
		this.productvo = productvo;
	}

}
