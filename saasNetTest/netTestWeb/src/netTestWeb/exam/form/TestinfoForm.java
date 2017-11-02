
package netTestWeb.exam.form;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.exam.dto.TestinfoQuery;
import netTest.exam.vo.Testinfo;

/** 
 * 
 */
public class TestinfoForm extends BaseForm {
	
	private Testinfo vo;
	private TestinfoQuery queryVO;
	
	private Long userorgid;
	private Long shopid;
	
	private Long categoryid;
	private String categoryname;

	// 
	private Long newscategoryid;
	private String newscategoryname;
	
	// 需要处理的考试的数量
	private Integer testtodocount;
	// 商店是否已经添加了产品, 取值是yes或no
	private String hasshopproduct;
	// 商店共有多少产品数量
	private Integer productnum;
	// 商店中我直接管理的产品数量
	private Integer myprodnum;
	
	/** 考试列表 **/
	private List testinfoList;
	/** 列表类型，1为查询需要处理的考试(即：考试中和阅卷中的考试)，2为查询所有考试列表，3为考试结果列表 **/
	private int listtype;
	
	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Testinfo();
		queryVO = new TestinfoQuery();
		userorgid = null;
		shopid = null;
		listtype = 1;
		testtodocount = 0;
		productnum = 0;
		myprodnum = 0;
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
	
	public TestinfoQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(TestinfoQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Testinfo getVo() {
		return vo;
	}

	public void setVo(Testinfo vo) {
		this.vo = vo;
	}

	public Long getUserorgid() {
		return userorgid;
	}

	public void setUserorgid(Long userorgid) {
		this.userorgid = userorgid;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public Long getNewscategoryid() {
		return newscategoryid;
	}

	public void setNewscategoryid(Long newscategoryid) {
		this.newscategoryid = newscategoryid;
	}

	public String getNewscategoryname() {
		return newscategoryname;
	}

	public void setNewscategoryname(String newscategoryname) {
		this.newscategoryname = newscategoryname;
	}

	public Integer getTesttodocount() {
		return testtodocount;
	}

	public void setTesttodocount(Integer testtodocount) {
		this.testtodocount = testtodocount;
	}

	public String getHasshopproduct() {
		return hasshopproduct;
	}

	public void setHasshopproduct(String hasshopproduct) {
		this.hasshopproduct = hasshopproduct;
	}

	public List getTestinfoList() {
		return testinfoList;
	}

	public void setTestinfoList(List testinfoList) {
		this.testinfoList = testinfoList;
	}

	public int getListtype() {
		return listtype;
	}

	public void setListtype(int listtype) {
		this.listtype = listtype;
	}

	public Integer getProductnum() {
		return productnum;
	}

	public void setProductnum(Integer productnum) {
		this.productnum = productnum;
	}

	public Integer getMyprodnum() {
		return myprodnum;
	}

	public void setMyprodnum(Integer myprodnum) {
		this.myprodnum = myprodnum;
	}
	
}
