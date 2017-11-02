
package netTestWeb.common.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.product.vo.Productbase;
import netTestWeb.base.BaseForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.vo.ShopMini;

import commonTool.constant.LabelValueVO;

/** 
 * @deprecated
 * Creation date: 08-08-2007
 */
public class ProdUseForm extends BaseForm {
	
	static Logger log = Logger.getLogger(ProdUseForm.class.getName());
	
	/** 可以访问商店的数组 **/
	private ShopMini[] shopArr; 
	
	/** 用户所能看到的产品集合，一旦加载，则不用重新加载，因此不可在reset方法中重置该变量
	 * 其中是Productbase的集合 **/
    private List prodList;
    
    /** 列表类型，1为单选类型，2为多选类型 **/
    private int listType;

    //private List prodOptList;
    // prodList是否被加载过了,如果为true说明被加载过了，get的时候如果为空也直接返回
    private boolean isLoaded;
    
    
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		listType = 1; // 默认是单选类型
		isLoaded = false;
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
	
//	public List getProdList() {
//		if((prodList==null||prodList.size()<1)&&!isLoaded){
//            try {
//				Object obj =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//				if(obj!=null){
//					LoginInfoEdu loginfo = (LoginInfoEdu)obj;
//				    LoginActionLogic.getInstance().setCanUseProd(loginfo, this, shopid);
//				}
//			} catch (Exception e) {
//				log.error(e);
//			}
//		}
//		return prodList;
//	}
//
//	public void setProdList(List prodList) {
//		this.prodList = prodList;
//		isLoaded = true;
//	}

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public List getProdOptList() {
		List rtnList = new ArrayList();
		if(prodList!=null&&prodList.size()>0){
			Productbase testVO = null;
			LabelValueVO labelVO = null;
			for(int i=0;i<prodList.size();i++){
				testVO = (Productbase)prodList.get(i);
				labelVO = new LabelValueVO();
				labelVO.setLabel(testVO.getProductname());
				labelVO.setValue(String.valueOf(testVO.getProductbaseid()));
				rtnList.add(labelVO);
			}
		}
		return rtnList;
	}

	public ShopMini[] getShopArr() {
		return shopArr;
	}

	public void setShopArr(ShopMini[] shopArr) {
		this.shopArr = shopArr;
	}

		
}
