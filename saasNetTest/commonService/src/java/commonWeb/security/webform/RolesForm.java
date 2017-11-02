
package commonWeb.security.webform;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonWeb.base.BaseForm;
import commonWeb.security.dto.RolesQuery;
import commonWeb.security.vo.Roles;


public class RolesForm extends BaseForm {
	
	private Roles vo;
	private RolesQuery queryVO;
	
	private List roleList;
	
	private String syscode;
	
	/**
	 * 查询role的类型，1代表仅查询系统级role，2代表仅查询商店内的Role
	 */
	private int selectroletype = 2;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Roles();
		queryVO = new RolesQuery();
		selectroletype = 2;
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
	
	public RolesQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(RolesQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Roles getVo() {
		return vo;
	}

	public void setVo(Roles vo) {
		this.vo = vo;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public int getSelectroletype() {
		return selectroletype;
	}

	public void setSelectroletype(int selectroletype) {
		this.selectroletype = selectroletype;
	}
		
}
