
package platformWeb.shop.form;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.ShopQuery;
import platform.shop.vo.Shopcontactinfo;
import platform.vo.Shop;
import platform.vo.Shopapp;
import platform.vo.Shopstatuslog;
import platformWeb.base.BaseForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ShopForm extends BaseForm {
	
	private ShopQuery queryVO;
	private Shop vo;
	private Shopstatuslog logVO;
	private Shopapp appvo;
	private Shopcontactinfo contactinfo;
	
	private Locale locale;
	
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
	
	// 1 按商店名查询, 2 按商店编码查询
	private int searchshoptype;
	private String searchtext;
	
	private List shopList;
	
	
	public List getShopList() {
		return shopList;
	}

	public void setShopList(List shopList) {
		this.shopList = shopList;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		queryVO = new ShopQuery();
		vo = new Shop();
		logVO = new Shopstatuslog();
		appvo = new Shopapp();
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

	public int getSaveType() {
		return saveType;
	}

	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Shop getVo() {
		return vo;
	}

	public void setVo(Shop vo) {
		this.vo = vo;
	}

	public String getProdcates() {
		return prodcates;
	}

	public void setProdcates(String prodcates) {
		this.prodcates = prodcates;
	}

	public String getOrginStatusName() {
		return orginStatusName;
	}

	public void setOrginStatusName(String orginStatusName) {
		this.orginStatusName = orginStatusName;
	}

	public Shopstatuslog getLogVO() {
		return logVO;
	}

	public void setLogVO(Shopstatuslog logVO) {
		this.logVO = logVO;
	}

	public Shopapp getAppvo() {
		return appvo;
	}

	public void setAppvo(Shopapp appvo) {
		this.appvo = appvo;
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

	public int getSearchshoptype() {
		return searchshoptype;
	}

	public void setSearchshoptype(int searchshoptype) {
		this.searchshoptype = searchshoptype;
	}

	public String getSearchtext() {
		return searchtext;
	}

	public void setSearchtext(String searchtext) {
		this.searchtext = searchtext;
	}

	public Shopcontactinfo getContactinfo() {
		if(contactinfo==null){
			contactinfo = new Shopcontactinfo();
		}
		return contactinfo;
	}

	public void setContactinfo(Shopcontactinfo contactinfo) {
		this.contactinfo = contactinfo;
	}
		
}
