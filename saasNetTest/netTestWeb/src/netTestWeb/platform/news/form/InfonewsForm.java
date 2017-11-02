
package netTestWeb.platform.news.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import platform.dto.InfonewsQuery;
import platform.social.vo.SocialNews;
import platform.vo.Infonews;
import platform.vo.Newscategory;


public class InfonewsForm extends BaseForm {
	
	private Infonews vo;
	private InfonewsQuery queryVO;
	// 目录id
	private Long categoryid;
	
	private String categoryname;
	
	// 新闻分类id
	private Long newscategoryid;
	private String newscategoryname;
	
	// 模块编号，对于同一种模块，编号一样，但是单个的id不同
	private Integer moduleno;
	// 标签横向列表
	private List<Newscategory> tablist;
	
	/** 1为列出查看，2为列出管理 **/
	private int listtype;
	
	/** 下一次查询从哪条记录开始 **/
	private Integer indexcursor;
	
	private List<SocialNews> socialnewslist;


	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		vo = new Infonews();
		queryVO = new InfonewsQuery();
		listtype = 1;
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

	public InfonewsQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(InfonewsQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Infonews getVo() {
		return vo;
	}

	public void setVo(Infonews vo) {
		this.vo = vo;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public Long getNewscategoryid() {
		return newscategoryid;
	}

	public void setNewscategoryid(Long newscategoryid) {
		this.newscategoryid = newscategoryid;
	}

	public List<Newscategory> getTablist() {
		return tablist;
	}

	public void setTablist(List<Newscategory> tablist) {
		this.tablist = tablist;
	}
	
	public boolean isShowtab(){
		if(tablist!=null && tablist.size()>0){
			if(tablist.size()==1){
				return false;
			}else {
				return true;
			}
		}else {
			return false;
		}
	}

	public Integer getModuleno() {
		return moduleno;
	}

	public void setModuleno(Integer moduleno) {
		this.moduleno = moduleno;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getNewscategoryname() {
		return newscategoryname;
	}

	public void setNewscategoryname(String newscategoryname) {
		this.newscategoryname = newscategoryname;
	}

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}

	public Integer getIndexcursor() {
		return indexcursor;
	}

	public void setIndexcursor(Integer indexcursor) {
		this.indexcursor = indexcursor;
	}

	public List<SocialNews> getSocialnewslist() {
		return socialnewslist;
	}

	public void setSocialnewslist(List<SocialNews> socialnewslist) {
		this.socialnewslist = socialnewslist;
	}

}
