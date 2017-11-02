package netTestWeb.product.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.product.dto.OpenactivitymemberQuery;
import netTest.product.vo.Openactivitymember;

/** 
 */
public class OpenactivitymemberForm extends BaseForm {
	
	private Openactivitymember vo;
	private OpenactivitymemberQuery queryVO;
	/** 是否有管理open activity列表人员的权限 **/
	private Boolean canadmin;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Openactivitymember();
		queryVO = new OpenactivitymemberQuery();
		canadmin = false;
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
	
	public OpenactivitymemberQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(OpenactivitymemberQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Openactivitymember getVo() {
		return vo;
	}

	public void setVo(Openactivitymember vo) {
		this.vo = vo;
	}

	public Boolean getCanadmin() {
		return canadmin;
	}

	public void setCanadmin(Boolean canadmin) {
		this.canadmin = canadmin;
	}

}
