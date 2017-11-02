package commonWeb.social.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import platform.social.vo.Usecomment;


public class UsecommentForm extends ActionForm {
	
	protected Usecomment vo;
	
	protected Long sessuserid;
	
	public static final int editType_edit = 1;
	public static final int editType_view = 2;
	
	protected int editType;
	
	/** 产品评价平均得分 **/
	private String commentavggrade;
	/** 产品评价总数 **/
	private String commentusernumber;
	

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
		vo = new Usecomment();
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
	

	public Usecomment getVo() {
		return vo;
	}

	public void setVo(Usecomment vo) {
		this.vo = vo;
	}

	public Long getSessuserid() {
		return sessuserid;
	}

	public void setSessuserid(Long sessuserid) {
		this.sessuserid = sessuserid;
	}

	public String getCommentavggrade() {
		return commentavggrade;
	}

	public void setCommentavggrade(String commentavggrade) {
		this.commentavggrade = commentavggrade;
	}

	public String getCommentusernumber() {
		return commentusernumber;
	}

	public void setCommentusernumber(String commentusernumber) {
		this.commentusernumber = commentusernumber;
	}
		
}
