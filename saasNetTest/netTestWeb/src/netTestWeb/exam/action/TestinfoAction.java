
package netTestWeb.exam.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dto.TestinfoQuery;
import netTest.exam.logic.TestinfoLogic;
import netTest.exam.vo.Testinfo;
import netTest.paper.constant.PaperConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.ProductbaseDao;
import netTestWeb.base.ActionLinkUtil;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.exam.form.TestinfoForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.StringUtil;


public class TestinfoAction extends BaseAction {
	
	static Logger log = Logger.getLogger(TestinfoAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		TestinfoForm theForm = (TestinfoForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listTestinfo".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("listTodoTestinfo".equals(myaction)) {
			myforward = listTodoTestinfo(mapping, actionform, request,response);
		} 
//		else if ("listIndexOpenTest".equals(myaction)) {
//			myforward = listOpenTest(mapping, actionform, request,response);
//		} 
		else if ("savenewtestinfo".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("saveTestinfo".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editTestinfo".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewTestinfo".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addTestinfo".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("changeStatus".equals(myaction)) {
			myforward = changeStatus(mapping, actionform, request,response);
		} else if ("deleteTestinfo".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("showprodmagmess".equals(myaction)) {
			myforward = showprodmagmess(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/**
	 * 课程管理 页面显示的 信息提醒页面
	 */
	private ActionForward showprodmagmess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestinfoForm theForm = (TestinfoForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long userid = loginfo.getUserid();

		// 查询产品数量
		ProductbaseDao productdao = BOFactory.getProductbaseDao();
		Integer prodnum = productdao.selShopProdCount(shopid);
		if(prodnum==null || prodnum<1){
			theForm.setHasshopproduct("no");
		}else {
			theForm.setHasshopproduct("yes");
			theForm.setProductnum(prodnum);
			String[] prodIdArr = BOFactory.getUserproductLogic().getUserprodStr(userid, null);
			if(prodIdArr!=null && prodIdArr.length>1){
				if(!StringUtil.isEmpty(prodIdArr[1])){
					theForm.setMyprodnum(StringUtil.splitString(prodIdArr[1], ",").length);
				}
			}
			//查询需要审批的考试，参考list方法中的逻辑
			Long orgbaseid = loginfo.getOrgbaseid();
			TestinfoQuery testqueryVO = theForm.getQueryVO();
			testqueryVO.setShopid(shopid);
			// 处理切换用户产品
			switchProduct(null,testqueryVO,UserproductConstant.ProdUseType_operatorMag);
	
			testqueryVO.setOrgbaseid(orgbaseid);
			testqueryVO.setUserid(userid);
			// 只查询考试阅卷完成前的状态
			testqueryVO.setTeststatusquerystring2(TestinfoConstant.Teststatus_allChecked, true);
			
			int testcount = 0;
			TestinfoDao testDao = BOFactory.getTestinfoDao();
			List testlist = testDao.selectByVO(testqueryVO);
			if(testlist!=null) {
				testcount = testlist.size();
				theForm.setTestinfoList(testlist);
			}
			theForm.setTesttodocount(testcount);
		}
		
		return mapping.findForward("magproductmess");
	} 
	
	/** 
	 * 管理员管理商店时查看考试列表
     * @security 管理员管理商店时使用
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TestinfoForm theForm = (TestinfoForm) form;
		Long shopid = theForm.getShopid();
		Long userid = getLoginInfo().getUserid();
		Long orgbaseid = getLoginInfo().getOrgbaseid();
		Long productid = theForm.getProductbaseid();
		TestinfoQuery queryVO = theForm.getQueryVO();
		
        // 处理切换用户的商店
		shopid = switchShop(shopid,queryVO,true);
		// 处理切换用户产品
		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_operatorMag);
		
		String toPage = "list";
		
		theForm.setUserorgid(orgbaseid);
		theForm.setShopid(shopid);
		queryVO.setOrgbaseid(orgbaseid);
		queryVO.setUserid(userid);		
		queryVO.setOrder_By_Clause(" teststartdate desc");
		TestinfoLogic logic = BOFactory.getTestinfoLogic();
		Page page = logic.listTestForOper(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward(toPage);
	}
	
	/** 
	 * 管理员管理商店时查看需要处理的考试列表，只查询 考试未结束的考试 或 正在阅卷的考试
     * @security 管理员管理商店时使用
	 */
	private ActionForward listTodoTestinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TestinfoForm theForm = (TestinfoForm) form;
		Long shopid = theForm.getShopid();
		Long userid = getLoginInfo().getUserid();
		Long productid = theForm.getProductbaseid();
		TestinfoQuery queryVO = theForm.getQueryVO();
		
        // 处理切换用户的商店
		if(productid==null && shopid!=null){
		   switchShop(shopid,queryVO,true);
		}
		// 处理切换用户产品
		switchProduct(productid,queryVO,UserproductConstant.ProdUseType_operatorMag);
				
		theForm.setShopid(shopid);
		// 如果所查询的考试状态为空，则查询仍需要处理的考试，即还没有开放考试结果的考试
		if(queryVO.getTeststatus()==null&&(queryVO.getTeststatusquerystring()==null
			||queryVO.getTeststatusquerystring().trim().length()<1)){
			queryVO.setTeststatusquerystring2(TestinfoConstant.Teststatus_openExam, true);
		}
		// 查询当前用户有权限管理的考试，即testchecker表中有权限的test
		queryVO.setUserid(userid);		
		queryVO.setOrder_By_Clause(" teststartdate desc");
		TestinfoDao testDao = BOFactory.getTestinfoDao();
		List datalist = testDao.selectByVO(queryVO);
		theForm.setTestinfoList(datalist);
		
		return mapping.findForward("listtodo");
	}
	
	/** 
	 * 在主页上列出开放的考试
	 */
//	private ActionForward listOpenTest(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) 
//	{
//		TestinfoForm theForm = (TestinfoForm) form;
//		Long localeid = theForm.getLocaleid();
//		if(localeid==null){
//		   localeid = getLoginInfo(true).getLocaleid();
//		}
//		Long categoryid = theForm.getCategoryid();
//		// 转换categoryid, 并记录session中
//		BaseEmptyEntity basevo = switchProductCategory(categoryid, request, localeid, BOFactory_Platform.getProductcategoryLogic());
//		categoryid = basevo.getId();
//		TestinfoQuery queryVO = theForm.getQueryVO();	
//		queryVO.setLocaleid(localeid);
//		queryVO.setCategoryid(categoryid);
//		queryVO.setOrder_By_Clause("a.testStartDate");
//		queryVO.setIsIncludeChild(CommonConstant.SearchType_Below); // 查询本机目录，包括下级目录中的公开考试
//        		
//		TestinfoLogic logic = BOFactory.getTestinfoLogic();
//		Page page = null;
//        // 查询
//		if(queryVO.getCategoryid()==null){
//			page = Page.EMPTY_PAGE;
//		}else {
//		    page = logic.listOpenTest(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
//		}
//		this.setPage(request, page);
//		
//		return mapping.findForward("list");
//	}
	
	/** 
	 * Method save
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		TestinfoForm theForm = (TestinfoForm) form;
		Testinfo vo = theForm.getVo();
        if(vo!=null&&(vo.getTestid()==null||vo.getTestid()==0)){
        	messCode = KeyInMemoryConstant.AddSuccess;
        	LoginInfoEdu loginfo = getLoginInfo();
            Long createorgid = loginfo.getOrgbaseid();
            String createorgname = loginfo.getOrgname();
            Long userid = loginfo.getUserid();
            String loginname = loginfo.getLoginname();
            String displayname = loginfo.getNickname();
            Long shopid = loginfo.getShopid();
            
            vo.setCreateorgid(createorgid);
            vo.setCreateorgname(createorgname);
            vo.setCreateuserid(userid);
            vo.setCreateloginname(loginname);
            vo.setCreateusername(displayname);
            vo.setShopid(shopid);
            vo.setIsdynamicpaper(PaperConstant.GeneType_fixed);
            vo.setTeststatus(TestinfoConstant.Teststatus_unStart);
        }
        TestinfoLogic logic = BOFactory.getTestinfoLogic();
        vo = logic.save(vo);
		// set messag page parameters.
        String url = "monitorTest.do?queryVO.testid="+vo.getTestid();
		super.setMessUrlPage(request, url, messCode, "1", "BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		TestinfoForm theForm = (TestinfoForm) form;
		TestinfoQuery queryVO = theForm.getQueryVO();
		Long userorgid = getLoginInfo().getOrgbaseid();
		Long shopid = getLoginInfo().getShopid();
		Long pk = queryVO.getTestid();
		theForm.setUserorgid(userorgid);
		theForm.setShopid(shopid);
		
		TestinfoDao dao = BOFactory.getTestinfoDao();
		Testinfo vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:TestinfoAction;--method:editPage;");
		theForm.setVo(vo);

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		TestinfoForm theForm = (TestinfoForm) form;
		LoginInfoEdu sess = getLoginInfo();
		Long shopid = sess.getShopid();
		Long orgbaseid = sess.getOrgbaseid();
		theForm.setShopid(shopid);
		theForm.setUserorgid(orgbaseid);
		Testinfo vo = theForm.getVo();
		vo.setProductbaseid(sess.getProductid());
		vo.setProductname(sess.getProductname());
		vo.setOpentype(TestinfoConstant.OpenType_ProductUser);
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addEditPage");
	}
	
	private ActionForward changeStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		TestinfoForm theForm = (TestinfoForm) form;
		Long testid = theForm.getVo().getTestid();
		LoginInfoEdu loginfo = getLoginInfo();
		Long orgbaseid = loginfo.getOrgbaseid();
		Long shopid = loginfo.getShopid();
		String messCode = KeyInMemoryConstant.modifySuccess;
		TestinfoLogic logic = BOFactory.getTestinfoLogic();
		Short nextteststatus = logic.changeStatus(testid, orgbaseid, shopid);
		String url = ActionLinkUtil.getPageLinkMap().get("TestdeptAction.monitorTest")+testid;  
		this.setMessUrlPage(request, url, messCode, null, WebConstant.Resource_BaseKey_Boundle);
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		Long shopid = getLoginInfo().getShopid();
		Long orgbaseid = getLoginInfo().getOrgbaseid();
		TestinfoForm theForm = (TestinfoForm) form;
		Testinfo vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.deleteSuccess;
		TestinfoLogic logic = BOFactory.getTestinfoLogic();
		int rows = logic.deleteTest(vo.getTestid(), null, shopid, orgbaseid);
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		theForm.getQueryVO().setOrgbaseid(orgbaseid);
		return this.list(mapping, theForm, request, response);
	}
		
}
