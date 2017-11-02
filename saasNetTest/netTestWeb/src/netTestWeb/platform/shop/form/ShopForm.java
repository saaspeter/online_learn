
package netTestWeb.platform.shop.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.ShopQuery;
import platform.shop.vo.Shopcontactinfo;
import platform.shop.vo.Shopext;
import platform.vo.Shop;
import platform.vo.Shopstatuslog;
import platform.vo.Usershop;


public class ShopForm extends BaseForm {
	
	private ShopQuery queryVO;
	private Shop vo;
	private Shopext extvo;
	
	private Shopcontactinfo contactvo;
	
	private Short usershopstatus;
	private Shopstatuslog logVO;
	
	private List shopList;
	private List<Usershop> usershopList;
	
	private String viewtype;
	
	/** 保存类型，是保存完毕后返回还是保存后继续在编辑页面，用于需要多种语言的地方 **/
	private int saveType;
	
	/** 商店选取的商店目录id字符串 **/
	private String prodcates;
	/** 记录原状态名 **/
	private String orginStatusName;
	
	private Long categoryid;
	private String categoryname;
	
	private Long newscategoryid;
	private String newscategoryname;
	
	private String searchtext;
	
	private String loginname;
	// 是否加载在商店中的权限，1为加载，0为不加载
	private int loadauthority;
	
	// 是否已经添加了商店简介, 取值是yes或no
	private String hasshopdescript;
	// 是否有需要等待审批的订单
	private Integer ordertodocount;
	// 是否已经添加了商店目录, 取值是yes或no
	private String hasshopcategory;
	// 商店是否已经添加了产品, 取值是yes或no
	private String hasshopproduct;
	// 选择商店所在地的code
	private String privCode;
	private String cityCode;
	
	/** 是否是后台系统管理员操作,为了在action中区分动作使用 **/
	private boolean isadminoper;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		queryVO = new ShopQuery();
		vo = new Shop();
		loadauthority = 0;
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

	public ShopQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shop getVo() {
		return vo;
	}

	public void setVo(Shop vo) {
		this.vo = vo;
	}

	public List getShopList() {
		return shopList;
	}

	public void setShopList(List shopList) {
		this.shopList = shopList;
	}

	public List<Usershop> getUsershopList() {
		return usershopList;
	}

	public void setUsershopList(List<Usershop> usershopList) {
		this.usershopList = usershopList;
	}

	public Short getUsershopstatus() {
		return usershopstatus;
	}

	public void setUsershopstatus(Short usershopstatus) {
		this.usershopstatus = usershopstatus;
	}

	public String getViewtype() {
		return viewtype;
	}

	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}

	public Shopstatuslog getLogVO() {
		if(logVO==null){
			logVO = new Shopstatuslog();
		}
		return logVO;
	}

	public void setLogVO(Shopstatuslog logVO) {
		this.logVO = logVO;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Long getNewscategoryid() {
		return newscategoryid;
	}

	public void setNewscategoryid(Long newscategoryid) {
		this.newscategoryid = newscategoryid;
	}

	public String getNewscategoryname() {
		return newscategoryname;
	}

	public void setNewscategoryname(String newscategoryname) {
		this.newscategoryname = newscategoryname;
	}

	public String getOrginStatusName() {
		return orginStatusName;
	}

	public void setOrginStatusName(String orginStatusName) {
		this.orginStatusName = orginStatusName;
	}

	public String getProdcates() {
		return prodcates;
	}

	public void setProdcates(String prodcates) {
		this.prodcates = prodcates;
	}

	public int getSaveType() {
		return saveType;
	}

	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

	public String getSearchtext() {
		return searchtext;
	}

	public void setSearchtext(String searchtext) {
		this.searchtext = searchtext;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public int getLoadauthority() {
		return loadauthority;
	}

	public void setLoadauthority(int loadauthority) {
		this.loadauthority = loadauthority;
	}

	public String getHasshopdescript() {
		return hasshopdescript;
	}

	public void setHasshopdescript(String hasshopdescript) {
		this.hasshopdescript = hasshopdescript;
	}

	public String getHasshopcategory() {
		return hasshopcategory;
	}

	public void setHasshopcategory(String hasshopcategory) {
		this.hasshopcategory = hasshopcategory;
	}

	public Integer getOrdertodocount() {
		return ordertodocount;
	}

	public void setOrdertodocount(Integer ordertodocount) {
		this.ordertodocount = ordertodocount;
	}

	public String getHasshopproduct() {
		return hasshopproduct;
	}

	public void setHasshopproduct(String hasshopproduct) {
		this.hasshopproduct = hasshopproduct;
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

	public boolean isIsadminoper() {
		return isadminoper;
	}

	public void setIsadminoper(boolean isadminoper) {
		this.isadminoper = isadminoper;
	}

	public Shopext getExtvo() {
		if(extvo==null){
		   extvo = new Shopext();
		}
		return extvo;
	}

	public void setExtvo(Shopext extvo) {
		this.extvo = extvo;
	}

	public Shopcontactinfo getContactvo() {
		return contactvo;
	}

	public void setContactvo(Shopcontactinfo contactvo) {
		this.contactvo = contactvo;
	}
	
}
