
package netTestWeb.order.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import netTestWeb.base.BaseForm;

public class ShoppingCartForm extends BaseForm {
	
	private Custorder custorder;
    private Orderproduct orderprodVO;
	private List<Orderproduct> prodList;
    /** 选购的订单产品id **/
    private Long orderprodid;

	/** 国家语言设置列表 **/
	private List localeList;
	/** 商店支付信息 **/
	private String payinfo;
	
	/////////共用的
	private String[] keys;
	private int editType;
	/**  代表高级搜索的打开或关闭情况，1为打开，2为隐蔽高级搜索  **/
	private String complexSearchDivStatus;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		custorder = new Custorder();
//		prodList = new ArrayList();
		orderprodVO = new Orderproduct();
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

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public List getLocaleList() {
		return localeList;
	}

	public void setLocaleList(List localeList) {
		this.localeList = localeList;
	}

	public Custorder getCustorder() {
		return custorder;
	}

	public void setCustorder(Custorder custorder) {
		this.custorder = custorder;
	}
	
//    public Object getProduct(int key) {
//        if(prodList.size()<=key){
//            int iCount = key-prodList.size()+1;
//            for(int i=0;i<iCount;i++)
//            {
//            	Orderproduct vo=new Orderproduct();
//            	prodList.add(vo);
//            }        	
//        	return prodList.get(key);
//        }
//    	return prodList.get(key);
//    }
    
	/**
	 * 获得界面选中的集合
	 * @return
	 */
//	public List getSelectedKeys() {
//		// 去除其中没有productbaseid的记录，即去除没有选中的记录
//		List rtnList = new ArrayList();
//		Orderproduct tempVO = null;
//		if(prodList!=null&&prodList.size()>0&&keys!=null&&keys.length>0){
//			for(int i=0;i<prodList.size();i++){
//				tempVO = (Orderproduct)prodList.get(i);
//				if(tempVO!=null&&tempVO.getProductbaseid()!=null){
//				    for(int j=0;j<this.keys.length;j++){
//				    	if(keys[j].equals(tempVO.getProductbaseid().toString())){
//				    		rtnList.add(tempVO);
//				    		break;
//				    	}
//				    }
//				}
//			}
//		}else
//			rtnList = prodList;
//		return rtnList;
//	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public List<Orderproduct> getProdList() {
		return prodList;
	}

	public void setProdList(List<Orderproduct> prodList) {
		this.prodList = prodList;
	}

	public Orderproduct getOrderprodVO() {
		return orderprodVO;
	}

	public void setOrderprodVO(Orderproduct orderprodVO) {
		this.orderprodVO = orderprodVO;
	}

	public Long getOrderprodid() {
		return orderprodid;
	}

	public void setOrderprodid(Long orderprodid) {
		this.orderprodid = orderprodid;
	}

	public String getPayinfo() {
		return payinfo;
	}

	public void setPayinfo(String payinfo) {
		this.payinfo = payinfo;
	}
	
}
