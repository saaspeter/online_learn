
package netTestWeb.exam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exam.constant.TestdeptConstant;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dto.TestdeptQuery;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;
import netTestWeb.base.BaseAction;
import netTestWeb.exam.form.TestdeptForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.util.AssertUtil;

/** 
 * 
 */
public class TestdeptAction extends BaseAction {
	
	static Logger log = Logger.getLogger(TestdeptAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("monitorTest".equals(myaction)) {
			myforward = monitorTest(mapping, actionform, request,response);
		} 
//		else if ("seeTestdeptResult".equals(myaction)) {
//			theForm.setSearchTyep(3);
//			myforward = monitorTest(mapping, actionform, request,response);
//		} 
//		else if ("saveTestdept".equals(myaction)) {
//			myforward = save(mapping, actionform, request,response);
//		} else if ("deleteTestdept".equals(myaction)) {
//			myforward = delete(mapping, actionform, request,response);
//		} 
		else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	
	private ActionForward monitorTest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		TestdeptForm theForm = (TestdeptForm) form;
		TestdeptQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getTestid(), null);
		
		TestdeptDao dao = BOFactory.getTestdeptDao();
		TestinfoLogic testlogic = BOFactory.getTestinfoLogic();
		Testinfo testinfoVO = testlogic.qryTestInfo(queryVO.getTestid());
		AssertUtil.assertNotNull(testinfoVO, null);
		
		queryVO.setShopid(testinfoVO.getShopid());
		theForm.setTestinfoVO(testinfoVO);
		queryVO.setType(TestdeptConstant.Type_TestInfo); // 查询考试的人员统计信息
		Testdept vo = dao.selSatTestDept(queryVO.getTestid());
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		theForm.setUserorgid(testinfoVO.getCreateorgid());
		String toPage = "monitorTestPage";
		if(TestinfoConstant.Teststatus_openExam.equals(testinfoVO.getTeststatus())
			|| TestinfoConstant.Teststatus_allChecked.equals(testinfoVO.getTeststatus()))
		{ 
			// 如果考试状态为开放考试结果或阅卷完毕，转向考试结果页面
			toPage = "testdeptresult";
		}
		
		return mapping.findForward(toPage);
	}
	
		
	/** 
	 * Method save
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
//	public ActionForward save(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) 
//	{
//		String messCode = KeyInMemoryConstant.AddSuccess;
//		if(!isTokenValid(request)){
//			saveToken(request);
//			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
//		}else{
//			resetToken(request);
//		}
//		Long shopid = getLoginInfo().getShopid();
//		TestdeptForm theForm = (TestdeptForm) form;
//		Testdept vo = theForm.getVo();
//		Long createorgid = theForm.getCreateorgid();
//        TestdeptLogic logic = BOFactory.getTestdeptLogic();
//		int rows = logic.addTestdept(vo.getTestid(), shopid, createorgid,theForm.getDeptIdStr());
//		// set messag page parameters.
//		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
//		theForm.getQueryVO().setTestid(vo.getTestid());
//		return this.monitorTest(mapping, theForm, request, response);
//	}
	
	/** 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
//	public ActionForward delete(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		Long shopid = getLoginInfo().getShopid();
//		Long orgbaseid = getLoginInfo().getOrgbaseid();
//		//TODO 需要判断要删除的单位是不是登录人员单位的下级，如果不是则抛出没有权限异常
//		TestdeptForm theForm = (TestdeptForm) form;
//		Testdept vo = theForm.getVo();
//		String messCode = KeyInMemoryConstant.deleteSuccess;
//		TestdeptLogic logic = BOFactory.getTestdeptLogic();
//		int rows = logic.removeTestdept(vo.getTestid(), shopid, vo.getTestdeptid(), vo.getOrgbaseid());
//		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
//		theForm.getQueryVO().setTestid(vo.getTestid());
//		return this.monitorTest(mapping, theForm, request, response);
//	}
	
}
