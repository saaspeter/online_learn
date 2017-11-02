package netTestWeb.product.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import netTestWeb.base.BaseForm;
import netTest.product.dto.OpenactivityQuery;
import netTest.product.vo.Openactivity;
import netTest.product.vo.Openactivitymember;

/**
 */
public class OpenactivityForm extends BaseForm {
	
	private Openactivity vo;
	private OpenactivityQuery queryVO;
    private Openactivitymember membervo;
	
	// 0: 在首页上列出来看  1:在商店主页上列出  2:商店管理员看的list页面，有管理和删除按钮 3:课程管理界面上显示list，限定本课程
    // 4: 课程学习页面上的链接，用户点击课程后进入课程学习界面
    // 5: 在购买课程页面上的tab页显示公开活动
	private int showtype;
	
	private Long shopid;
	
	private String privCode;
	private String cityCode;
	
	private Long categoryid;
	private String categoryName;
	
	private boolean ableAdd;
	
	private List<Openactivity> datalist;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Openactivity();
		queryVO = new OpenactivityQuery();
		showtype = 0;
		privCode="";
		cityCode="";
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
	
	public OpenactivityQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(OpenactivityQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Openactivity getVo() {
		return vo;
	}

	public void setVo(Openactivity vo) {
		this.vo = vo;
	}

	public int getShowtype() {
		return showtype;
	}

	public void setShowtype(int showtype) {
		this.showtype = showtype;
	}

	public List<Openactivity> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<Openactivity> datalist) {
		this.datalist = datalist;
	}

	public String getPrivCode() {
		return privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean isAbleAdd() {
		return ableAdd;
	}

	public void setAbleAdd(boolean ableAdd) {
		this.ableAdd = ableAdd;
	}

	public Openactivitymember getMembervo() {
		return membervo;
	}

	public void setMembervo(Openactivitymember membervo) {
		this.membervo = membervo;
	}
	
}
