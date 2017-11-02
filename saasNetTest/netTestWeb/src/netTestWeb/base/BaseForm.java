package netTestWeb.base;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import netTest.util.ResourceBundleUtil;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import commonTool.biz.logicImpl.I18nLogicImpl;
import commonWeb.base.BaseActionBase;
import commonWeb.base.BaseFormBase;

public class BaseForm extends BaseFormBase {
	
	static Logger log = Logger.getLogger(BaseForm.class.getName());
	
	public static String Default_All_SessionProductName = "NetTest.Default.All.SessionProductName";
	public static String Default_All_SessionShopName = "NetTest.Default.All.SessionShopName";
	
//    /** 选择的产品id **/
//    protected String prodidStr;
//    /** 选择的产品名 **/
//    protected String prodnameStr;
	
	protected Long productbaseid;
	
	protected String productname;
	
	// sessionProductid
	// sessionProductname
    
    protected Long shopid;
    
    protected String shopcode;
    
    protected String shopname;
    
    protected Long userid;
//    
//    // 是否切换商店，1为不切换，2为切换
//    protected int isChangeshop;
//    // 是否切换产品，1为不切换，2为切换
//    protected int isChangeprod;
    

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
       super.reset(mapping, request);
	}

//	public String getProdidStr() {
//		return prodidStr;
//	}

//	public void setProdidStr(String prodidStr) {
//		// TODO 需要过滤，保证用户传过来的prod都是用户可以看到的
//		this.prodidStr = prodidStr;
//	}

//	public String getProdnameStr() {
//		return prodnameStr;
//	}
//
//	public void setProdnameStr(String prodnameStr) {
//		this.prodnameStr = prodnameStr;
//	}

	public Long getShopid() {
		return shopid;
	}
	
	public Long getSessionShopid() {
		return BaseAction.getLoginInfo().getShopid();
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getShopname() {
		return shopname;
	}

	public String getSessionShopname() {
		String sessionShopname = BaseActionBase.getLoginInfo(true).getShopname();
		if(sessionShopname==null||sessionShopname.trim().length()<1){
		   Locale locale = I18nLogicImpl.localeMap.get(BaseAction.getLoginInfo().getLocaleid()).getEqualLocale();
		   sessionShopname = ResourceBundleUtil.getInstance().getValue(Default_All_SessionShopName, locale);
		}
		return sessionShopname;
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

	public String getSessionShopcode() {
		String shopcodeSession = BaseActionBase.getLoginInfo(true).getShopcode();
		return shopcodeSession;
	}
	
	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}

	public Long getProductbaseid() {
		return productbaseid;
	}
	
	public Long getSessionProductid() {
		return BaseAction.getLoginInfo().getProductid();
	}

	public void setProductbaseid(Long productbaseid) {
		this.productbaseid = productbaseid;
	}

	public String getProductname() {
		return productname;
	}
	
	public String getSessionProductname() {
		String sessionProductname = BaseAction.getLoginInfo().getProductname();
		if(sessionProductname==null||sessionProductname.trim().length()<1){
			Locale locale = I18nLogicImpl.localeMap.get(BaseAction.getLoginInfo().getLocaleid()).getEqualLocale();
			sessionProductname = ResourceBundleUtil.getInstance().getValue(Default_All_SessionProductName, locale);
		}
		return sessionProductname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
	
}
