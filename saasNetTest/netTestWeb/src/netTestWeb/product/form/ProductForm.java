
package netTestWeb.product.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.product.dto.ProductbaseQuery;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class ProductForm extends BaseForm {
	
	private Productbase vo;
	private ProductbaseQuery queryVO;
	
	/** 保存类型，是保存完毕后返回还是保存后继续在编辑页面 **/
	private int saveType;
		
	/** 商店名称 **/
	private String shopName;
	
	private Long categoryid;
	
	/** 目录名称 **/
	private String categoryName;
	
	private List productList;
	
	/**
	 * 能不能改变产品的changeType
	 */
	private boolean ablechangepaytype;
	
	private String privCode;
	
	private String cityCode;
	
	/** 产品评价平均得分 **/
	private String commentavggrade;
	/** 产品评价总数 **/
	private String commentusernumber;
	
//	private String payinfo;
	
	private Boolean ishopopen;
	
	// 是否允许在某个目录上创建课程
	private Boolean allowaddprodforcate;
	
	// 课程内容是否合格，是否可以发布
	private boolean canpublish;
	

	public Boolean getIshopopen() {
		if(ishopopen==null){
			ishopopen = true;
		}
		return ishopopen;
	}

	public void setIshopopen(Boolean ishopopen) {
		this.ishopopen = ishopopen;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List getProductList() {
		return productList;
	}

	public void setProductList(List productList) {
		this.productList = productList;
	}

	public int getSaveType() {
		return saveType;
	}

	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Productbase();
		queryVO = new ProductbaseQuery();
		ablechangepaytype = true;
		canpublish = true;
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

	public ProductbaseQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ProductbaseQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Productbase getVo() {
		return vo;
	}

	public void setVo(Productbase vo) {
		this.vo = vo;
	}

	public boolean isAblechangepaytype() {
		return ablechangepaytype;
	}

	public void setAblechangepaytype(boolean ablechangepaytype) {
		this.ablechangepaytype = ablechangepaytype;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getPrivCode() {
		return privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}
	
	public String getRealRegionCode() {
		if(cityCode!=null&&cityCode.trim().length()>0){
			return cityCode;
		}else if(privCode!=null&&privCode.trim().length()>0){
			return privCode;
		}else {
			return null;
		}
	}

	public String getCommentavggrade() {
		return commentavggrade;
	}

	public void setCommentavggrade(String commentavggrade) {
		this.commentavggrade = commentavggrade;
	}

	public String getCommentusernumber() {
		return commentusernumber;
	}

	public void setCommentusernumber(String commentusernumber) {
		this.commentusernumber = commentusernumber;
	}

	public Boolean getAllowaddprodforcate() {
		return allowaddprodforcate;
	}

	public void setAllowaddprodforcate(Boolean allowaddprodforcate) {
		this.allowaddprodforcate = allowaddprodforcate;
	}

	public boolean isCanpublish() {
		return canpublish;
	}

	public void setCanpublish(boolean canpublish) {
		this.canpublish = canpublish;
	}
	
}
