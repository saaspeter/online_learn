
package netTestWeb.exam.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import netTest.exam.dto.TestcheckerQuery;
import netTest.exam.vo.Testchecker;
import netTest.exam.vo.Testinfo;
import netTestWeb.base.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class TestcheckerForm extends BaseForm {
	
	private Testchecker vo;
	private TestcheckerQuery queryVO;
	
	private Testinfo testinfoVO;
	
	private String userIdStr;
	private String userNameStr;
	
	private List datalist;

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		vo = new Testchecker();
		queryVO = new TestcheckerQuery();
		userIdStr = null;
		userNameStr = null;
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
	
	public TestcheckerQuery getQueryVO() {
		return queryVO;
	}

	public void setQueryVO(TestcheckerQuery queryVO) {
		this.queryVO = queryVO;
	}

	public Testchecker getVo() {
		return vo;
	}

	public void setVo(Testchecker vo) {
		this.vo = vo;
	}

	public String getUserIdStr() {
		return userIdStr;
	}

	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}

	public String getUserNameStr() {
		return userNameStr;
	}

	public void setUserNameStr(String userNameStr) {
		this.userNameStr = userNameStr;
	}

	public List getDatalist() {
		return datalist;
	}

	public void setDatalist(List datalist) {
		this.datalist = datalist;
	}

	public Testinfo getTestinfoVO() {
		return testinfoVO;
	}

	public void setTestinfoVO(Testinfo testinfoVO) {
		this.testinfoVO = testinfoVO;
	}

		
}
