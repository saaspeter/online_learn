
package netTestWeb.orguser.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.orguser.dao.OrgbaseDao;
import netTest.orguser.dao.OrguserDao;
import netTest.orguser.dto.OrguserQuery;
import netTest.orguser.logic.OrguserLogic;
import netTest.orguser.vo.Orgbase;
import netTest.orguser.vo.Orguser;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.orguser.form.OrguserForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.ShopConstant;
import platform.dao.CustinfoexDao;
import platform.dao.UsercontactinfoDao;
import platform.logic.UserLogic;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Custinfoex;
import platform.vo.Shop;
import platform.vo.Usercontactinfo;
import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;


public class OrguserAction extends BaseAction {
	
	static Logger log = Logger.getLogger(OrguserAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		OrguserForm theform = (OrguserForm)actionform;
		
		if ("listorguser".equals(myaction)) {
			theform.setUrlType(1);
			myforward = list(mapping, actionform, request,response);
		} else if ("selectorguser".equals(myaction)) {
			theform.setUrlType(2);
			myforward = list(mapping, actionform, request,response);
		} else if ("savedeptuser".equals(myaction)) {
			myforward = savedeptuser(mapping, actionform, request,response);
		} else if ("editdeptuserPage".equals(myaction)) {
			myforward = editdeptuserPage(mapping, actionform, request,response);
		} else if ("viewdeptuser".equals(myaction)) {
			myforward = viewdeptuser(mapping, actionform, request,response);
		} else if ("adddeptuserPage".equals(myaction)) {
			myforward = adddeptuserPage(mapping, actionform, request,response);
		} else if ("delUserDept".equals(myaction)) {
			myforward = delUserDept(mapping, actionform, request,response);
		} else if ("transferorguser".equals(myaction)) {
			myforward = transferUser(mapping, actionform, request,response);
		} else if ("putuserintodept".equals(myaction)) {
			myforward = putuserintodept(mapping, actionform, request,response);
		} else if ("toimportorguser".equals(myaction)) {
			myforward = toimportorguser(mapping, actionform, request,response);
		} else if ("importorguser".equals(myaction)) {
			myforward = importorguser(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	
	/** 
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		OrguserForm theForm = (OrguserForm) form;
		OrguserQuery queryVO = theForm.getQueryVO();
		queryVO.setShopid(shopid);
		String url = "deptuserlist";
		AssertUtil.assertNotNull(queryVO.getOrgbaseid(), ExceptionConstant.Error_Need_Paramter);
		OrguserLogic logic = BOFactory.getOrguserLogic();
		Page page = logic.qryOrguserPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		// 查找本单位信息
		OrgbaseDao orgDao = BOFactory.getOrgbaseDao();
		Orgbase orgVO = orgDao.selectByPK(queryVO.getOrgbaseid());
		theForm.setOrgVO(orgVO);
		if(theForm.getUrlType()==2){
			url = "mulSelUsr";
		}
		// check whether shop is free or paid
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		theForm.setShopchargetype(shopvo.getChargetype());
		
		return mapping.findForward(url);
	}
	
	/**
	 * 根据userid查看user在商店中信息
	 */
	private ActionForward viewdeptuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrguserForm theForm = (OrguserForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		OrguserQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getUserid(), ExceptionConstant.Error_Need_Paramter);
		OrguserLogic logic = BOFactory.getOrguserLogic();
		Orguser vo = logic.qryOrguserByUsershop(queryVO.getUserid(), shopid);
		theForm.setVo(vo);
		if(vo.getOrgbaseid()==null){
			theForm.setSessionorgid(loginfo.getOrgbaseid());
			theForm.setSessionorgname(loginfo.getOrgname());
		}
        return mapping.findForward("viewdeptuserPage");
	}
	
	/** 
	 * Method savedeptuser
	 */
	private ActionForward savedeptuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrguserForm theForm = (OrguserForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Orguser vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getOrguserid()==null||vo.getOrguserid()==0)){
        	messCode = KeyInMemoryConstant.AddSuccess;
        }
        vo.setShopid(shopid);
        
        if(theForm.getCustinfoex()!=null){
        	vo.setCustinfoex(theForm.getCustinfoex());
        }
        if(theForm.getContactinfo()!=null){
        	vo.setContactinfo(theForm.getContactinfo());
        }
        
        if(vo.getOrguserid()==null) {
        	UserLogic userLogic = BOFactory_Platform.getUserLogic();
            String loginname = userLogic.geneShopUserLoginName(vo.getNickname(), loginfo.getShopcode(), CommonConstant.SysCode_Education);
            // check whether loginname is valid
            boolean checkresult = userLogic.isExistsLoginname(loginname);
            if(checkresult){
            	throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
            }
            vo.setLoginname(loginname);
            // check whether email is valid
            checkresult = userLogic.isExistsEmail(vo.getEmail());
            if(checkresult){
            	throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
            }
        }
        // orgAdmin cannot set user's password, through email to initial or change pass
        vo.setPass(null);
        
       	OrguserLogic logic = BOFactory.getOrguserLogic();
		logic.saveUserDept(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}

	
	/** 
	 * Method editdeptuserPage
	 */
	private ActionForward editdeptuserPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrguserForm theForm = (OrguserForm) form;
		OrguserQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getOrguserid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		OrguserDao dao = BOFactory.getOrguserDao();
		Orguser vo = dao.qryOrguserByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		
		UsercontactinfoDao contactDao = BOFactory_Platform.getUsercontactinfoDao();
		CustinfoexDao custinfoexDao = BOFactory_Platform.getCustinfoexDao();
		Usercontactinfo contactinfo = contactDao.selectByPK(vo.getUserid());
		Custinfoex custinfoex = custinfoexDao.selectByPK(vo.getUserid());
		theForm.setContactinfo(contactinfo);
		theForm.setCustinfoex(custinfoex);
		
		theForm.setVo(vo);
		return mapping.findForward("editdeptuserPage");
	}
	
	/** 
	 * Method adddeptuserPage
	 */
	private ActionForward adddeptuserPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrguserForm theForm = (OrguserForm) form;
		String shopcode = getLoginInfo().getShopcode();
		theForm.setShopcode(shopcode);
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("adddeptuserPage");
	}
	
	/** 
	 * 目前一个用户只能在一个单位中，所以删除时把该用户从shop中直接删除(利用Event)
	 */
	private ActionForward delUserDept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrguserForm theForm = (OrguserForm) form;
		Long shopid = getLoginInfo().getShopid();
		Long userid = theForm.getUserid();
		
		int rows = 0;
		OrguserLogic logic = BOFactory.getOrguserLogic();
		// 目前一个用户只能在一个单位中，所以删除时把该用户从shop中直接删除
		rows = logic.delUserDept(userid, shopid);
		super.setMessagePage(request, theForm, KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
	/**
	 * 把用户加入到商店中，在人员产品页面可以将暂时还不是单位用户的人员加入单位中
	 */
	private ActionForward putuserintodept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrguserForm theForm = (OrguserForm) form;
		Long shopid = getLoginInfo().getShopid();
		Orguser vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        vo.setShopid(shopid);
       	OrguserLogic logic = BOFactory.getOrguserLogic();
		logic.putUserIntoOrg(shopid, vo.getUserid(), vo.getOrgbaseid());
		// set message page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	private ActionForward toimportorguser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		return mapping.findForward("importpage");
	}
	
	//导入orguser from file
	private ActionForward importorguser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		OrguserForm theForm = (OrguserForm) form;
		Orguser vo = theForm.getVo();
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		if(!ShopConstant.ChargeType_paid.equals(shopvo.getChargetype())){
			throw new LogicException(ExceptionConstantBase.Error_Unauthorized);
		}
		OrguserLogic logic = BOFactory.getOrguserLogic();
		List<String> messageList = logic.importOrguserFromExcel(theForm.getUserFile().getInputStream(), 
				shopid, shopvo.getShopcode(), vo, loginfo.getLocale());
		
		if(messageList!=null){
			String successImportNumberStr = messageList.get(messageList.size()-1);
		    int successImportNumber = 0;
		    if(successImportNumberStr!=null){
		    	successImportNumber = Integer.parseInt(successImportNumberStr);
		    }
		    if(messageList.size()>1){
		    	theForm.setImportFailNumber(messageList.size()-1);
		        theForm.setMessageList(messageList.subList(0, messageList.size()-1));
		    }else {
		    	// 没有失败的问题记录
		    	theForm.setImportFailNumber(0);
		    }
			theForm.setImportSuccessNumber(successImportNumber);
		}
		return mapping.findForward("importResultPage");
	}
	
	/** 
	 * Method transferUser
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward transferUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		OrguserForm theForm = (OrguserForm) form;
		Long userid = theForm.getUserid();
		Long newDeptid = theForm.getNeworgid();
		AssertUtil.assertNotNull(newDeptid, ExceptionConstant.Error_Need_Paramter);
		Orguser orguservo = BOFactory.getOrguserDao().selectOrgForUser(userid, shopid);
		if(orguservo==null){
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
    	int rows = 0;
		OrguserLogic logic = BOFactory.getOrguserLogic();
		rows = logic.transferDept(userid, orguservo.getOrgbaseid(), newDeptid);
		super.setMessagePage(request, theForm, KeyInMemoryConstant.modifySuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method insertMutiUserProd:为多个学员新增多个产品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
//	public ActionForward insertMutiUserProd(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) 
//	{
//		OrguserForm theForm = (OrguserForm) form;
//		String productStr = theForm.getProductStr();
//		Short userprodstat = theForm.getUserprodstat();
//		Long shopid = getLoginInfo().getShopid();
////		TODO 添加用户产品选择的localeid,默认为操作员的localeid，以后再行改动
//		Long localeid = getLoginInfo().getUseLocaleid(); 
//		Long operatorid = getLoginInfo().getUserid();
//		String[] useridArr = theForm.getKeys();
//		String messCode = KeyInMemoryConstant.AddSuccess;
//        try {
//        	UserproductLogic logic = BOFactory.getUserproductLogic();
//        	logic.addUserprodFromEdu(productStr, useridArr, shopid, userprodstat, operatorid);
//		} catch (Exception e) {
//			log.error("----error in Class:OrguserAction Method:insertMutiUserProd", e);
//			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.save");
//		}
//		// set messag page parameters.
//		super.setMessagePage(request,theForm, messCode, null,"BizKey");
//		return this.list(mapping, theForm, request, response);
//	}
		
}
