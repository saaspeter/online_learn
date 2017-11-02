
package netTestWeb.platform.shop.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.shop.dto.ShopcontactinfoQuery;
import platform.shop.vo.Shopcontactinfo;
import platform.vo.Shop;


public class ShopcontactinfoForm extends BaseForm {
	
	private Shopcontactinfo vo;
	private ShopcontactinfoQuery queryVO;
	
	private Shop shopvo;
	
	private Long localeid;
	
	private List contactList;
	
//	private List<LabelValueVO> localeList;
	
	private String regioncodeStr;
	
	private String privCode;
	
	private String cityCode;
 
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Shopcontactinfo();
		queryVO = new ShopcontactinfoQuery();
		privCode = "";
		cityCode = "";
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

	public ShopcontactinfoQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(ShopcontactinfoQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Shopcontactinfo getVo() {
		return vo;
	}

	public void setVo(Shopcontactinfo vo) {
		this.vo = vo;
	}

	public Shop getShopvo() {
		return shopvo;
	}

	public void setShopvo(Shop shopvo) {
		this.shopvo = shopvo;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public List getContactList() {
		return contactList;
	}

	public void setContactList(List contactList) {
		this.contactList = contactList;
	}


	public String getRegioncodeStr() {
		return regioncodeStr;
	}

	public void setRegioncodeStr(String regioncodeStr) {
		this.regioncodeStr = regioncodeStr;
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
		
}
