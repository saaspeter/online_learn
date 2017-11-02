package netTestWeb.exam.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exam.dao.TestcheckerDao;
import netTest.exam.dto.TestcheckerQuery;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.vo.Testchecker;
import netTest.exam.vo.Testinfo;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.exam.form.TestcheckerForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;

import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.util.AssertUtil;

/** 
 * 
 */
public class TestcheckerAction extends BaseAction {
	
	static Logger log = Logger.getLogger(TestcheckerAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listTestchecker".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveTestchecker".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("deleteTestchecker".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TestcheckerForm theForm = (TestcheckerForm) form;
		TestcheckerQuery queryVO = theForm.getQueryVO();
		// 查询Testinfo的信息
		TestinfoLogic testinfologic = BOFactory.getTestinfoLogic();
		Testinfo testinfoVO = testinfologic.qryTestInfo(queryVO.getTestid());
		AssertUtil.assertNotNull(testinfoVO, null);
		theForm.setTestinfoVO(testinfoVO);		
		queryVO.setShopid(testinfoVO.getShopid());
		TestcheckerDao dao = BOFactory.getTestcheckerDao();
		List datalist = dao.selectByVO(queryVO);
		// 处理用户的displayname
		if(datalist!=null && datalist.size()>0){
		   List<Long> useridList = new ArrayList<Long>();
		   Testchecker voTemp = null;
		   for(int i=0;i<datalist.size();i++){
			   voTemp = (Testchecker)datalist.get(i);
			   useridList.add(voTemp.getUserid());
		   }
		   UsershopLogic usershoplogic = BOFactory_Platform.getUsershopLogic();
		   Map<Long,String> nameMap = usershoplogic.qryUsernameByIdList(testinfoVO.getShopid(), useridList);
		   for(int i=0;i<datalist.size();i++){
			   voTemp = (Testchecker)datalist.get(i);
			   voTemp.setDisplayname(nameMap.get(voTemp.getUserid()));
		   }
		}
		
		theForm.setDatalist(datalist);
				
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String messCode = KeyInMemoryConstant.AddSuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		Long shopid = getLoginInfo().getShopid();
		TestcheckerForm theForm = (TestcheckerForm) form;
		TestcheckerQuery queryVO = theForm.getQueryVO();
		String userIdStr = theForm.getUserIdStr();
       
        TestcheckerDao dao = BOFactory.getTestcheckerDao();
		dao.addTestcheckers(queryVO.getTestid(), shopid, userIdStr);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return this.list(mapping, theForm, request, response);
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestcheckerForm theForm = (TestcheckerForm) form;
		Long shopid = getLoginInfo().getShopid();
		String[] keys = theForm.getKeys();
		
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			TestcheckerDao dao = BOFactory.getTestcheckerDao();
			rows = dao.deleteCheckers(shopid, theForm.getVo().getTestid(), keys);
			info = String.valueOf(rows);
		}catch (HasReferenceException e) {
			//在此得到异常数据，组装成ajax类型的数据返回
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
