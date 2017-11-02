
package netTestWeb.orguser.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import netTestWeb.base.BaseForm;
import org.apache.struts.action.ActionMapping;

import netTest.orguser.dto.OrgbaseQuery;
import netTest.orguser.vo.Orgbase;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class OrgbaseForm extends BaseForm {
	
	private Orgbase vo;
	private OrgbaseQuery queryVO;
	
	/** 右侧页面的链接地址，例如单位小组页面或人员列表页面 **/
	private String rightUrl;
	/** 定义树叶上的action触发的javascript函数 **/
	private String treeAction = "showRightFromTree";
	/** 链接地址类型,2为进入选择人员或选择单位的界面，其余值为单位管理或单位人员管理界面 **/
	private int urlType;
	/** 选择类型，0代表hidden,1代表单选(radio)，2代表多选(checkbox) **/
	private String selectType;
	/** 查找单位的范围，1代表查找该商店的整个单位树(查询该商店顶层的单位vo)，其余代表只查询本单位和其下级单位(查询传入orgid的vo) **/
	private int orgRange;
	/** 管理类型, org 或 user */
	private String magtype;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		vo = new Orgbase();
		queryVO = new OrgbaseQuery();
		rightUrl = null;
		urlType = 0;
		selectType = null;
		orgRange = 0;
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
	
	public String getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	
	public OrgbaseQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(OrgbaseQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Orgbase getVo() {
		return vo;
	}

	public void setVo(Orgbase vo) {
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

	public String getTreeAction() {
		return treeAction;
	}

	public void setTreeAction(String treeAction) {
		this.treeAction = treeAction;
	}

	public int getUrlType() {
		return urlType;
	}

	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public int getOrgRange() {
		return orgRange;
	}

	public void setOrgRange(int orgRange) {
		this.orgRange = orgRange;
	}

	public String getMagtype() {
		return magtype;
	}

	public void setMagtype(String magtype) {
		this.magtype = magtype;
	}
		
}
