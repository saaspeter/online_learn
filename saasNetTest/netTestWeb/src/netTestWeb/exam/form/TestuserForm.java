
package netTestWeb.exam.form;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.exam.dto.TestuserQuery;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import commonTool.util.DateUtil;

/** 
 */
public class TestuserForm extends BaseForm {
	
	private Testuser vo;
	private TestuserQuery queryVO;
	private Testinfo testVO;
	private String userIdstr;
	/** 列表页面类型：1为考试设置的时候看到的人会列表，2为考试结果中的人员列表 **/
	private String listjsptype;
	
	private Long testdeptid;
	/** 记录状态的字符串 **/
	private String statusStr;
	
	private Date testenddateset;
	/** 添加人员类型, 1为添加选择的userIdStr字符串,此时userIdstr不能为空,
	 *  2为添加一门课程中的所有学习人员 **/
	private String addusertype;
    /**
     * 是否可以开始考试，true or false
     */
	private Boolean canstarttest;
	/** test是否是open的 **/
	private Short testOpentype;
	
	private List datalist;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		vo = new Testuser();
		queryVO = new TestuserQuery();
		testVO = new Testinfo();
		userIdstr = null;
		testdeptid = null;
		statusStr = null;
		shopid = null;
		userid = null;
		canstarttest = false;
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
	
	public TestuserQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(TestuserQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Testuser getVo() {
		return vo;
	}

	public void setVo(Testuser vo) {
		this.vo = vo;
	}

	public String getUserIdstr() {
		return userIdstr;
	}

	public void setUserIdstr(String userIdstr) {
		this.userIdstr = userIdstr;
	}

	public String getListjsptype() {
		return listjsptype;
	}

	public void setListjsptype(String listjsptype) {
		this.listjsptype = listjsptype;
	}

	public Long getTestdeptid() {
		return testdeptid;
	}

	public void setTestdeptid(Long testdeptid) {
		this.testdeptid = testdeptid;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Testinfo getTestVO() {
		return testVO;
	}

	public void setTestVO(Testinfo testVO) {
		this.testVO = testVO;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getTestenddateset() {
		return testenddateset;
	}

	public void setTestenddateset(Date testenddateset) {
		this.testenddateset = testenddateset;
		if(testenddateset!=null){
		   // 将testenddate加1，用户查询，因为testenddate只显示年月日，所以查询时应该包含所选的这一天
		   queryVO.setTestenddate(DateUtil.dateAddDays(testenddateset, 1));
		}
	}

	public String getAddusertype() {
		return addusertype;
	}

	public void setAddusertype(String addusertype) {
		this.addusertype = addusertype;
	}

	public Boolean getCanstarttest() {
		return canstarttest;
	}

	public void setCanstarttest(Boolean canstarttest) {
		this.canstarttest = canstarttest;
	}

	public Short getTestOpentype() {
		return testOpentype;
	}

	public void setTestOpentype(Short testOpentype) {
		this.testOpentype = testOpentype;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}
		
}
