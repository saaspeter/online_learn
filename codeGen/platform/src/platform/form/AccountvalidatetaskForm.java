
package netTestWeb.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import platformWeb.base.BaseForm;
import platform.user.dto.AccountvalidatetaskQuery;
import platform.user.vo.Accountvalidatetask;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class AccountvalidatetaskForm extends BaseForm {
	
	private Accountvalidatetask vo;
	private AccountvalidatetaskQuery queryVO;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Accountvalidatetask();
		queryVO = new AccountvalidatetaskQuery();
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
	
	public AccountvalidatetaskQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(AccountvalidatetaskQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Accountvalidatetask getVo() {
		return vo;
	}

	public void setVo(Accountvalidatetask vo) {
		this.vo = vo;
	}

		
}
