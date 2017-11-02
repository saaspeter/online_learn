package netTestWeb.exam.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dto.TestuserQuery;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.logic.TestuserLogic;
import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;
import netTest.exception.ExceptionConstant;
import netTest.paper.constant.PaperConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.vo.Userproduct;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.exam.form.TestuserForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.UserDao;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.User;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;


public class TestuserAction extends BaseAction {
	
	static Logger log = Logger.getLogger(TestuserAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		TestuserForm theForm = (TestuserForm) actionform;
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listtestuser".equals(myaction)) {
			myforward = listTestuser(mapping, actionform, request,response);
		} else if ("listTestinfouser".equals(myaction)) {
			myforward = listTestinfo(mapping, actionform, request,response);
		} else if ("saveTestuser".equals(myaction)) {
			myforward = saveTestUser(mapping, actionform, request,response);
		} else if ("deleteTestuser".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} 
//		else if ("joinopentest".equals(myaction)) {
//			myforward = joinOpenTest(mapping, actionform, request,response);
//		} 
		else if ("enterTestHall".equals(myaction)) {
			myforward = enterTestHall(mapping, actionform, request,response);
		} 
//		else if ("enteropentesthall".equals(myaction)) {
//			theForm.setTestOpentype(TestinfoConstant.OpenType_AllUser_Internet);
//			myforward = enterTestHall(mapping, actionform, request,response);
//		} 
		else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method listTestuser: 为某次考试查询考试人员的
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listTestuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestuserForm theForm = (TestuserForm) form;
		String url = "";

		TestuserQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getTestid(), null);
		Page page = Page.EMPTY_PAGE;
		boolean needsearch = true;
	    Testinfo testVO = BOFactory.getTestinfoLogic().getTestinfo(queryVO.getTestid());
	    theForm.setTestVO(testVO);
	    queryVO.setQueryType(1);
	    
	    if(TestinfoConstant.Teststatus_unStart.equals(testVO.getTeststatus()))
	  	{
	       if(queryVO.getStatus()==null){
	    	   queryVO.setStatus(TestuserConstant.Status_unStart);
	       }
	  	   url = "listuser";
	  	} else if(TestinfoConstant.Teststatus_started.equals(testVO.getTeststatus()))
		{
	  	   if(queryVO.getStatus()==null){
		   	   queryVO.setStatus(TestuserConstant.Status_examing);
		   }
	  	   url = "listuser";
		}else {
	  	   url = "listresultuser";
	  	}
	    
	    TestuserDao dao = BOFactory.getTestuserDao();
	    //查询人员id，只支持精确的完全匹配查询loginname
	    if(queryVO.getLoginname()!=null && queryVO.getLoginname().trim().length()>0){
		   User uservo = BOFactory_Platform.getUserDao().selectByLogin(queryVO.getLoginname().trim());
	       if(uservo==null){
	    	   needsearch = false;
	       }else {
	    	   queryVO.setUserid(uservo.getUserid());
	       }
	    }
	    if(needsearch){
		   page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), 1000, getTotalNumber(request));
		   // 处理用户的displayname
		   if(page!=null && page.getList()!=null && page.getList().size()>0){
			   List<Long> useridList = new ArrayList<Long>();
			   Testuser testuserTemp = null;
			   for(int i=0;i<page.getList().size();i++){
				   testuserTemp = (Testuser)page.getList().get(i);
				   useridList.add(testuserTemp.getUserid());
			   }
			   UsershopLogic usershoplogic = BOFactory_Platform.getUsershopLogic();
			   Map<Long,String> nameMap = usershoplogic.qryUsernameByIdList(testVO.getShopid(), useridList);
			   for(int i=0;i<page.getList().size();i++){
				   testuserTemp = (Testuser)page.getList().get(i);
				   testuserTemp.setDisplayname(nameMap.get(testuserTemp.getUserid()));
			   }
		   }
	    }
		
		this.setPage(request, page);
		return mapping.findForward(url);
	}
	
	/** 
	 * Method listTestinfo:考生查询自己有哪些考试信息的方法
     * @security 只有学员学习时调用
	 */
	public ActionForward listTestinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestuserForm theForm = (TestuserForm) form;
		TestuserQuery queryVO = theForm.getQueryVO();
		LoginInfoEdu loginInfo = getLoginInfo();
		Long userid = loginInfo.getUserid();
		Long shopid = theForm.getShopid();
		Long productid = theForm.getProductbaseid();
		//Long localeid = loginInfo.getLocaleid();
		
        // 处理切换用户的商店
		shopid = switchShop(shopid,queryVO,false);
		// 处理切换用户产品
		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_userNormal);
				
		queryVO.setUserid(userid);
		// 复合查询，查询结果中有testinfo的信息
		queryVO.setQueryType(2);
		// 设置默认查询考试状态
		if(queryVO.getRoughteststatus()==null){
			queryVO.setRoughteststatus(TestuserConstant.RoughTestStatus_upcoming);
		}
		
		queryVO.setOrderColumn("teststartdate");
		queryVO.setOrderDirect(2); // 降序排列
				
		TestuserDao dao = BOFactory.getTestuserDao();
 	    List testuserList = dao.selectByVO(queryVO);
		// 增加shop信息
//		if(testuserList!=null&&testuserList.size()>0){
//			ShopDao shopdao = ShopDaoImpl.getInstance();
//			Testuser tempUserVO = null;
//			Shop tempShopVO = null;
//			for(int i=0; i<testuserList.size(); i++){
//				tempUserVO = (Testuser)testuserList.get(i);
//				tempShopVO = shopdao.selectByPK(tempUserVO.getShopid(), localeid);
//				if(tempShopVO!=null){
//					tempUserVO.setShopname(tempShopVO.getShopname());
//				}
//			}
//		}
		
		theForm.setDatalist(testuserList);
		return mapping.findForward("listTestinfouser");
	}
		
	/** 
	 * 保存为考试选择的考生
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveTestUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.AddSuccess;
//		if(!isTokenValid(request)){
//			saveToken(request);
//			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
//		}else{
//			resetToken(request);
//		}
		
		TestuserForm theForm = (TestuserForm) form;
		Long shopid = getLoginInfo().getShopid();
		TestuserQuery queryVO = theForm.getQueryVO();
		String addusertype = theForm.getAddusertype();
		Long testid = queryVO.getTestid();
		Long productid = queryVO.getProductbaseid();
		String userIdstr = theForm.getUserIdstr();
		Long testdeptid = theForm.getTestdeptid();
		
		AssertUtil.assertNotNull(testid, null);
		TestinfoDao testDao = BOFactory.getTestinfoDao();
        Testinfo testVO = testDao.selectByPK(testid);
        AssertUtil.assertNotNull(testVO, "netTestWeb.exam.action.testinfoNotExist");
        TestuserLogic logic = BOFactory.getTestuserLogic();
        int nums = 0;
        // 添加考试人员，但这里不指定用户使用的试卷，这个动作由进入考试大厅的动作触发
		if("2".equals(addusertype)){
			// 添加一门课程所有的学习人员
			AssertUtil.assertNotNull(productid, null);
			nums = logic.addAllProductUserIntoTest(shopid, testid);
		}else {
			// 添加选中的人员进入考试
			AssertUtil.assertNotEmpty(userIdstr, null);
			nums = logic.addTestuser(shopid, queryVO.getTestid(), theForm.getUserIdstr());
		}
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, String.valueOf(nums),"BizKey");
		return this.listTestuser(mapping, theForm, request, response);
	}
	
	/** 
	 * 保存所选单位中的所有考生学员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	/**
	public ActionForward saveAllUserInDept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.AddSuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		TestuserForm theForm = (TestuserForm) form;
		Long shopid = getLoginInfo().getShopid();
		TestuserQuery queryVO = theForm.getQueryVO();
		Long testid = queryVO.getTestid();
		String userIdstr = theForm.getUserIdstr();
		Long testdeptid = theForm.getTestdeptid();
		if(testid==null||userIdstr==null||userIdstr.trim().length()<1)
			throw new NeedParamsException("-- in saveTestuser action --");
        TestinfoDao testDao = BOFactory.getTestinfoDao();
        Testinfo testVO = testDao.selectByPK(testid);
        if(testVO==null)
        	return this.forwardErrorPage(mapping, request, null, "netTestWeb.exam.action.testinfoNotExist");
        TestuserLogic logic = BOFactory.getTestuserLogic();
        // 这里不插入用户使用的试卷，这个动作由进入考试大厅的动作触发
        int nums = logic.addTestuser(shopid, queryVO.getTestid(), theForm.getUserIdstr(),testdeptid);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, String.valueOf(nums),"BizKey");
		// 标志人数数目变化了，需要更新主考试界面人数
		request.setAttribute("initjsp", "1");
		return this.listTestuser(mapping, theForm, request, response);
	}
	*/
	
	/** 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestuserForm theForm = (TestuserForm) form;
		TestuserQuery queryVO = theForm.getQueryVO();
		if(queryVO.getTestid()==null)
			throw new NeedParamsException("-- in deleteTestuser action --");
		Long shopid = getLoginInfo().getShopid();
		String[] keys = theForm.getKeys();
		Long testdeptid = theForm.getTestdeptid();
		String info = "";
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		try {
			TestuserLogic logic = BOFactory.getTestuserLogic();
			rows = logic.removeTestuser(queryVO.getTestid(), shopid, keys,testdeptid);
			info = String.valueOf(rows);
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof HasReferenceException){
				info = e.getMessage();
			}else {
				info = ExceptionConstant.Error_System;
			}
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	/** 
	 * Method enterTestHall:考生进入考试大厅，显示考试的具体信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward enterTestHall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestuserForm theForm = (TestuserForm) form;
		Long testid = theForm.getVo().getTestid();
		Long userid = getLoginInfo().getUserid();
		TestuserLogic userlogic = BOFactory.getTestuserLogic();
		TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
		Testinfo testVO = testLogic.getTestinfo(testid);
		AssertUtil.assertNotNull(testVO, "netTestWeb.exam.action.testinfoNotExist");
		
		theForm.setTestVO(testVO);
		TestuserDao testuserDao = BOFactory.getTestuserDao();
		Testuser testuserVO = testuserDao.selectByLogicPK(testid, userid);
		if(testuserVO!=null){
			UserDao userDao = BOFactory_Platform.getUserDao();
			User userVO = userDao.selectByPK(userid);
			testuserVO.setDisplayname(userVO.getDisplayname()+"("+userVO.getLoginname()+")");
			theForm.setVo(testuserVO);
		}else {
			// 如果考试是面向所有课程考试的，这初始化testuser对象
			if(TestinfoConstant.OpenType_ProductUser.equals(testVO.getOpentype())){
				Userproduct userprodVO = BOFactory.getUserproductDao().selectByLogicPk(userid, testVO.getProductbaseid());
				if(userprodVO!=null && UserproductConstant.Status_active.equals(userprodVO.getStatus())){
					testuserVO = userlogic.autoJoinTest(testVO, userid);
					theForm.setVo(testuserVO);
				}else {
				    throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
				}
			}else {
				AssertUtil.assertNotNull(testVO, "netTestWeb.exam.enterTestHall.notsuchTestuser");
			}
		}
		// 如果考试已经开始则初始化考生信息
		if(testuserVO!=null&&TestinfoConstant.Teststatus_started.equals(testVO.getTeststatus())
			&&TestuserConstant.Status_unStart.equals(testuserVO.getStatus())
			&&testuserVO.getPaperid()==null)
		{
			testuserVO.setPaperid(testVO.getPaperid());
			// 初始化用户的答案，为用户生成试题，插入用户答案表
			int needRandom = PaperConstant.Ques_Random; // 暂时不需要乱序，以后如果需要，需要在记录选项在paper或exercise中
			boolean ret = userlogic.initUseranswer(testid, testVO.getPaperid(), userid, testVO.getShopid(), needRandom);
			if(ret){
				testuserDao.updateByPK(testuserVO);
			}
			// TODO 如果是动态试卷则需要另作处理
		}
		// 设置是否可以开始考试标志
		Date nowdate = DateUtil.getInstance().getNowtime_GLNZ();
		if(TestinfoConstant.Teststatus_started.equals(testVO.getTeststatus())
			&& testuserVO!=null 
			&& (TestuserConstant.Status_unStart.equals(testuserVO.getStatus()))
//	             ||TestuserConstant.Status_suspend.equals(testuserVO.getStatus()))
	        && (nowdate.compareTo(testVO.getTestenddate())<0))
		{
	          theForm.setCanstarttest(true);
	    }
		return mapping.findForward("testHall");
	}
	
	/** 
	 * 用户加入open test, 并且初始化用户考试答案
	 */
//	public ActionForward joinOpenTest(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		TestuserForm theForm = (TestuserForm) form;
//		Long testid = theForm.getVo().getTestid();
//		Long userid = getLoginInfo().getUserid();
//		String result = String.valueOf(CommonConstant.success);
//		String messCode = "CommonSystem.commonAction.operation.succeed";
//		TestuserLogic userlogic = BOFactory.getTestuserLogic();
//		TestinfoLogic testLogic = BOFactory.getTestinfoLogic();
//		Testinfo testVO = testLogic.getTestinfo(testid);
//		AssertUtil.assertNotNull(testVO, "netTestWeb.exam.action.testinfoNotExist");
//		String message;
//		try {
//			if(TestinfoConstant.OpenType_AllUser_Internet.equals(testVO.getOpentype())
//				&& TestinfoConstant.Teststatus_started.equals(testVO.getTeststatus())){
//				TestuserDao testuserDao = BOFactory.getTestuserDao();
//				Testuser userVO = testuserDao.selectByLogicPK(testid, userid);
//				if(userVO==null){
//				   int needRandom = PaperConstant.Ques_Random; 
//				   userVO = userlogic.joinTestAndInitUseranswer(testVO, userid, needRandom);
//				   result = String.valueOf(CommonConstant.success);
//				   messCode = String.valueOf(userVO.getTestuserid());
//				}else {
//					result = String.valueOf(CommonConstant.fail);
//					messCode = "netTestWeb.exam.error.usercannotJoinTest.alreadyExist";
//				}
//			}else {
//				result = String.valueOf(CommonConstant.fail);
//				messCode = "netTestWeb.exam.error.usercannotJoinTest.testIsNotOpenOrNotStarted";
//			}
//		} catch (RuntimeException e) {
//			result = String.valueOf(CommonConstant.fail);
//			messCode = "CommonSystem.commonAction.operation.failed";
//			log.error("oops, got an exception: ", e);
//		}
//		message = messCode;
//		if(String.valueOf(CommonConstant.fail).equals(result)){
//		   message = getResources(request).getMessage(messCode);
//		}
//		this.writeAjaxRtn(result, message, null, response);
//		return null;
//	}
	
}
