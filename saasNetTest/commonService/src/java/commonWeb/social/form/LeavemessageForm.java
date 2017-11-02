
package commonWeb.social.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import platform.social.dto.LeavemessageQuery;
import platform.social.vo.Leavemessage;


public class LeavemessageForm extends ActionForm {
	
	private static final long serialVersionUID = 3315628261525128636L;
	
	private Leavemessage vo;
	private LeavemessageQuery queryVO;
	
	private Long shopid;
	
	protected Long sessuserid;

	public static final int editType_edit = 1;
	public static final int editType_view = 2;
	
	private int editType;
	/**
	 * 新增类型，0是新增comment，1是回复comment
	 */
	private int addtype=0;

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		//super.reset(mapping, request);
		vo = new Leavemessage();
		queryVO = new LeavemessageQuery();
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
	
	public LeavemessageQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(LeavemessageQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Leavemessage getVo() {
		return vo;
	}

	public void setVo(Leavemessage vo) {
		this.vo = vo;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public int getAddtype() {
		return addtype;
	}

	public void setAddtype(int addtype) {
		this.addtype = addtype;
	}
	
	public Long getSessuserid() {
		return sessuserid;
	}

	public void setSessuserid(Long sessuserid) {
		this.sessuserid = sessuserid;
	}
		
}
