
package netTestWeb.order.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.order.dto.CustorderQuery;
import netTest.order.vo.Custorder;
import netTest.order.vo.Orderproduct;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.vo.Usershop;
import netTestWeb.base.BaseForm;

public class CustorderForm extends BaseForm {
	
	private Custorder vo;
	private CustorderQuery queryVO;
	private List orderprodList;
	private Usershop usershopVO;
	
	private Long orgid;
	private String orgname;
	private String nickname;
	
	private String[] keys;
	private int editType;
	// 订单备注
	private String notes;
	
	/** 1为平台系统管理员查看订单列表，2为个人查询我的订单列表，3为商店管理员查询本单位的所有订单列表 **/
	private int listType;
	
	private String complexSearchDivStatus;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Custorder();
		queryVO = new CustorderQuery();
		orderprodList = new ArrayList();
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
	
	public CustorderQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(CustorderQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Custorder getVo() {
		return vo;
	}

	public void setVo(Custorder vo) {
		this.vo = vo;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
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

	public List getOrderprodList() {
		return orderprodList;
	}

	public void setOrderprodList(List orderprodList) {
		this.orderprodList = orderprodList;
	}
	
    public Object getOrderprod(int key) {
        if(orderprodList.size()<=key){
            int iCount = key-orderprodList.size()+1;
            for(int i=0;i<iCount;i++)
            {
            	Orderproduct vo=new Orderproduct();
            	orderprodList.add(vo);
            }        	
        	return orderprodList.get(key);
        }
    	return orderprodList.get(key);
    }

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Usershop getUsershopVO() {
		return usershopVO;
	}

	public void setUsershopVO(Usershop usershopVO) {
		this.usershopVO = usershopVO;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrgname() {
		if(orgname==null)
			orgname = "";
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
		
}
