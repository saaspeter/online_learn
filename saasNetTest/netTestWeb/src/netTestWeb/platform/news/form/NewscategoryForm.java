
package netTestWeb.platform.news.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.vo.Newscategory;


public class NewscategoryForm extends BaseForm {
	
	private Newscategory vo;

	private List<Newscategory> newscateList;
	
	private int deletetype;

	private Long defaultlocaleid;
	
	// 是否需要刷新父页面  1为要刷新
	private String needflushparentpage;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Newscategory();
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

	public Newscategory getVo() {
		return vo;
	}

	public void setVo(Newscategory vo) {
		this.vo = vo;
	}

	public List<Newscategory> getNewscateList() {
		return newscateList;
	}

	public void setNewscateList(List<Newscategory> newscateList) {
		this.newscateList = newscateList;
	}

	public int getDeletetype() {
		return deletetype;
	}

	public void setDeletetype(int deletetype) {
		this.deletetype = deletetype;
	}

	public String getNeedflushparentpage() {
		return needflushparentpage;
	}

	public void setNeedflushparentpage(String needflushparentpage) {
		this.needflushparentpage = needflushparentpage;
	}

	public Long getDefaultlocaleid() {
		return defaultlocaleid;
	}

	public void setDefaultlocaleid(Long defaultlocaleid) {
		this.defaultlocaleid = defaultlocaleid;
	}

		
}
