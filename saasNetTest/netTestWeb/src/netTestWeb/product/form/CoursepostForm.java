package netTestWeb.product.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import netTest.product.vo.Coursepost;
import netTestWeb.base.BaseForm;


public class CoursepostForm extends BaseForm {
	
	private Coursepost vo;
	
	/** 1管理界面上的商店公告列表。2商店主页上的商店公告 **/
	private int urltype;
	
	private Long contentcateid;
	private String contentcatename;
	
	private Date lastlearndate;
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Coursepost();
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

	public Coursepost getVo() {
		return vo;
	}

	public void setVo(Coursepost vo) {
		this.vo = vo;
	}

	public int getUrltype() {
		return urltype;
	}

	public void setUrltype(int urltype) {
		this.urltype = urltype;
	}

	public Long getContentcateid() {
		return contentcateid;
	}

	public void setContentcateid(Long contentcateid) {
		this.contentcateid = contentcateid;
	}

	public String getContentcatename() {
		return contentcatename;
	}

	public void setContentcatename(String contentcatename) {
		this.contentcatename = contentcatename;
	}

	public Date getLastlearndate() {
		return lastlearndate;
	}

	public void setLastlearndate(Date lastlearndate) {
		this.lastlearndate = lastlearndate;
	}
	
}
