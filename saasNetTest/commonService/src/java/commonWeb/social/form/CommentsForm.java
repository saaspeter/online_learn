
package commonWeb.social.form;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import platform.social.dto.CommentsQuery;
import platform.social.vo.Comments;


public class CommentsForm extends ActionForm {
	
	private static final long serialVersionUID = 3315628261525128636L;
	
	protected Comments vo;
	protected CommentsQuery queryVO;
	
	protected Long shopid;
	protected String shopname;
	/** 是否要查询自己创建的comment, 0为不查询，1为查询用户自己的comment **/
	protected int searchuserowner;
	
	protected String usernamestr;
	protected String useridstr;
	
	protected Long sessuserid;
	
	public static final int editType_edit = 1;
	public static final int editType_view = 2;
	
	protected int editType;
	/**
	 * 新增类型，0是新增comment，1是回复comment
	 */
	protected int addtype=0;
	
	protected List commentList;
	
	protected Long productid;
	
	protected String productname;
	

	public List getCommentList() {
		return commentList;
	}

	public void setCommentList(List commentList) {
		this.commentList = commentList;
	}

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
		vo = new Comments();
		queryVO = new CommentsQuery();
		searchuserowner = 0;
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
	
	public CommentsQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(CommentsQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Comments getVo() {
		return vo;
	}

	public void setVo(Comments vo) {
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

	public int getSearchuserowner() {
		return searchuserowner;
	}

	public void setSearchuserowner(int searchuserowner) {
		this.searchuserowner = searchuserowner;
	}

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getUsernamestr() {
		return usernamestr;
	}

	public void setUsernamestr(String usernamestr) {
		this.usernamestr = usernamestr;
	}

	public String getUseridstr() {
		return useridstr;
	}

	public void setUseridstr(String useridstr) {
		this.useridstr = useridstr;
	}

	public Long getSessuserid() {
		return sessuserid;
	}

	public void setSessuserid(Long sessuserid) {
		this.sessuserid = sessuserid;
	}
		
}
