package netTest.exam.dto;

import netTest.exam.constant.TestinfoConstant;
import netTest.exam.vo.Testinfo;

public class TestinfoQuery extends Testinfo{
	
	private Long orgbaseid;
	
	private String deptpath;
	
	/** 管理考试类型，1为和我相关的考试，2为下级单位创建的考试 **/
	private String qrytesttype = "1";
	
	private Long localeid;
	
	private Long categoryid;
	private String categoryname;
	
	/** 执行查询人员userid **/
	private Long userid;
	
	/** 查询考试状态的字符串，用于查询的sql **/
	private String teststatusquerystring;
	
	/** 设置查询考试状态的字符串，用于界面显示 **/
	private Short teststatuspage;

    private String order_By_Clause;
   
    // 查询时是否包含下级目录，1为包含，2为不包含下级目录。-1代表不设置
    private int isIncludeChild = -1;

    public TestinfoQuery() {
       super();
    }

    public String getOrder_By_Clause() {
       return order_By_Clause;
    }

    public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
    }

	public Long getOrgbaseid() {
		return orgbaseid;
	}
	
	public void setOrgbaseid(Long orgbaseid) {
		this.orgbaseid = orgbaseid;
	}
	
	public String getDeptpath() {
		return deptpath;
	}
	
	public void setDeptpath(String deptpath) {
		this.deptpath = deptpath;
	}
	
	public String getQrytesttype() {
		return qrytesttype;
	}
	
	public void setQrytesttype(String qrytesttype) {
		this.qrytesttype = qrytesttype;
	}

	public String getProdidStr() {
		return prodidStr;
	}

	public void setProdidStr(String prodidStr) {
		this.prodidStr = prodidStr;
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

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public int getIsIncludeChild() {
		return isIncludeChild;
	}

	public void setIsIncludeChild(int isIncludeChild) {
		this.isIncludeChild = isIncludeChild;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTeststatusquerystring() {
		return teststatusquerystring;
	}
	
//	public void setTeststatusquerystring(String teststatus) {
//		if(teststatus!=null && teststatus.trim().length()>0)
//		   this.teststatusquerystring = TestinfoConstant.getTeststatusquerystring(new Short(teststatus), false);
//	}
	
	public Short getTeststatuspage() {
		return teststatuspage;
	}

	public void setTeststatuspage(Short teststatuspage) {
		this.teststatuspage = teststatuspage;
		if(teststatuspage!=null){
		   this.teststatusquerystring = TestinfoConstant.getTeststatusquerystring(teststatuspage, false);
		}
	}

	public void setTeststatusquerystring2(Short teststatus, boolean includebeforestatus) {
		this.teststatusquerystring = TestinfoConstant.getTeststatusquerystring(teststatus, includebeforestatus);
	}
   
}
