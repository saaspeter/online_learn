
package netTestWeb.exam.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import netTestWeb.base.BaseForm;
import netTest.exam.dto.TestdeptQuery;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class TestdeptForm extends BaseForm {
	
	private Testinfo testinfoVO;
	private Testdept vo;
	private TestdeptQuery queryVO;
	private List deptList;
	
	/** 用于记录新增的考试单位的ids和names **/
	private String deptIdStr;
	private String deptNameStr;
	/** 操作员的单位 **/
	private Long userorgid;
	/** 考试创建者的单位 **/
	private Long createorgid;
	
	/** 查询类型。0为考试设置。3为开放考试结果 **/
	private int searchTyep;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		testinfoVO = new Testinfo();
		vo = new Testdept();
		queryVO = new TestdeptQuery();
		deptIdStr = null;
		deptNameStr = null;
		userorgid = null;
		createorgid = null;
		searchTyep = 0;
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
	
	public TestdeptQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(TestdeptQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Testdept getVo() {
		return vo;
	}

	public void setVo(Testdept vo) {
		this.vo = vo;
	}

	public Testinfo getTestinfoVO() {
		return testinfoVO;
	}

	public void setTestinfoVO(Testinfo testinfoVO) {
		this.testinfoVO = testinfoVO;
	}

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}

	public String getDeptIdStr() {
		return deptIdStr;
	}

	public void setDeptIdStr(String deptIdStr) {
		this.deptIdStr = deptIdStr;
	}

	public String getDeptNameStr() {
		return deptNameStr;
	}

	public void setDeptNameStr(String deptNameStr) {
		this.deptNameStr = deptNameStr;
	}

	public Long getUserorgid() {
		return userorgid;
	}

	public void setUserorgid(Long userorgid) {
		this.userorgid = userorgid;
	}

	public Long getCreateorgid() {
		return createorgid;
	}

	public void setCreateorgid(Long createorgid) {
		this.createorgid = createorgid;
	}

	public int getSearchTyep() {
		return searchTyep;
	}

	public void setSearchTyep(int searchTyep) {
		this.searchTyep = searchTyep;
	}

		
}
