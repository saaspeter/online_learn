package netTestWeb.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commonWeb.security.authentication.UserinfoSession;
import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.product.dao.OpenactivityDao;
import netTest.product.dto.OpenactivityQuery;
import netTest.product.logic.OpenactivityLogic;
import netTest.product.vo.Openactivity;
import netTest.product.vo.Openactivitymember;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.product.form.OpenactivityForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.ShopConstant;
import platform.logic.ShopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Productcategory;
import platform.vo.Shop;
import platform.vo.ShopMini;
import commonTool.base.BaseEmptyEntity;
import commonTool.base.Page;
import commonTool.biz.logic.CountryregionLogic;
import commonTool.biz.logicImpl.CountryregionLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;

/** 
 * 
 */
public class OpenactivityAction extends BaseAction {
	
	static Logger log = Logger.getLogger(OpenactivityAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		OpenactivityForm theForm = (OpenactivityForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listOpenactivity".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveOpenactivity".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editOpenactivity".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewOpenactivity".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addOpenactivity".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteOpenactivity".equals(myaction)) {
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
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		OpenactivityForm theForm = (OpenactivityForm) form;
		OpenactivityQuery queryVO = theForm.getQueryVO();
		
		Long shopid = theForm.getShopid();
		Long localeid = getLoginInfo(true).getUseLocaleid(request);
		// 转换categoryid, 并记录session中
		BaseEmptyEntity basevo = switchProductCategory(theForm.getCategoryid(), request, localeid, BOFactory_Platform.getProductcategoryLogic());
		if(basevo!=null){
			queryVO.setCategoryid(basevo.getId());
		}
		// 设置默认查询开始时间
		if(queryVO.getStarttimerange()==null){
			queryVO.setStarttimerange(7);
		}
		// 目前限制了查询商店必须指定一种Locale，默认为登录者选择的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
        String regioncode = theForm.getRealRegionCode();
        queryVO.setRegioncode(regioncode);
        theForm.setQueryVO(queryVO);
		
		String url = "list";
		boolean checkstarttime = true;
		if(theForm.getShowtype()==1){
			url = "listinshop";
			queryVO.setStatus(Openactivity.Status_Scheduled);
		}else if(theForm.getShowtype()==2||theForm.getShowtype()==3){
			checkstarttime = false;
			shopid = getLoginInfo().getShopid();
			// 检查商店是否是认证过的商店，只有认证过的才可以发布openactivity
    		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, localeid);
    		if(!ShopConstant.IsAuthen_pass.equals(shopvo.getIsauthen())){
    			theForm.setAbleAdd(false);
    		}else {
    			theForm.setAbleAdd(true);
    		}
    		//set product name
    		if(queryVO.getProductid()!=null && StringUtil.isEmpty(queryVO.getProductname())){
    			Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(queryVO.getProductid());
    			if(prodvo!=null){
    				queryVO.setProductname(prodvo.getProductname());
    			}
    		}
    		if(theForm.getShowtype()==2){
    			url = "listadmin";
    		}else { // showtype = 3
    			url = "listforprodadmin";
    		}
		}else if(theForm.getShowtype()==4){
			url = "listforprodlearn";
		}else if(theForm.getShowtype()==5){
			url = "listforprodbuyframe";
		}else {
			queryVO.setStatus(Openactivity.Status_Scheduled);
		}
		OpenactivityLogic logic = BOFactory.getOpenactivityLogic();
		Page page = logic.query(queryVO.getCategoryid(), queryVO.getName(), queryVO.getLocaleid(), 
				queryVO.getRegioncode(), queryVO.getStatus(), shopid, queryVO.getProductid(), 
				queryVO.getStarttimerange(), checkstarttime, getCurrPageNumber(request), Page.PAGESIZE, getTotalNumber(request));
		if(theForm.getShowtype()==0 && page!=null && page.getList()!=null && page.getList().size()>0){
			Openactivity votemp = null;
			ShopLogic shoplogic = BOFactory_Platform.getShopLogic();
			ShopMini shopminivo = null;
			for(int i=0;i<page.getList().size();i++){
				votemp = (Openactivity)page.getList().get(i);
				shopminivo = shoplogic.getShopMini(votemp.getShopid(), localeid);
				if(shopminivo!=null){
					votemp.setShopname(shopminivo.getShopname());
				}
			}
		}
		this.setPage(request, page);
		return mapping.findForward(url);
	}

	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long localeid = loginfo.getLocaleid();
		OpenactivityForm theForm = (OpenactivityForm) form;
		Openactivity vo = theForm.getVo();
		if(theForm.getRealRegionCode()!=null){
			vo.setRegioncode(theForm.getRealRegionCode());
		}
        if(vo.getActivityid()==null){
        	// 检查商店是否是认证过的商店，只有认证过的才可以发布openactivity
    		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, localeid);
    		if(!ShopConstant.IsAuthen_pass.equals(shopvo.getIsauthen())){
    			throw new LogicException(platform.exception.ExceptionConstant.Error_Shop_NotAuthentiated);
    		}
        	vo.setShopid(shopid);
        	vo.setLocaleid(localeid);
        	vo.setCreatorid(loginfo.getUserid());
        	messCode = KeyInMemoryConstant.AddSuccess;
        }
        if(vo.getProductid()!=null){
        	Productbase prodVO = BOFactory.getProductbaseDao().selectByPK(vo.getProductid());
    		vo.setCategoryid(prodVO.getCategoryid());	
        }
        
        OpenactivityDao dao = BOFactory.getOpenactivityDao();
		dao.save(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OpenactivityForm theForm = (OpenactivityForm) form;
		if(theForm.getEditType()==WebConstant.editType_edit){
		   saveToken(request);
		}
		UserinfoSession loginfo = getLoginInfo(true);
		Long localeid = loginfo.getLocaleid();
		Long userid = loginfo.getUserid();
		Long pk = theForm.getVo().getActivityid();
		
		OpenactivityDao dao = BOFactory.getOpenactivityDao();
		Openactivity vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
				
		// 设置地区编码
		CountryregionLogic regionLogin = CountryregionLogicImpl.getInstance();
		String[] regionArr = regionLogin.getPrivCityCode(vo.getRegioncode(), vo.getLocaleid().toString());
		theForm.setPrivCode(regionArr[0]);
		theForm.setCityCode(regionArr[1]);
		// 设置目录名称
		Productcategory cateVO = BOFactory_Platform.getProductcategoryDao()
									.selectByLogicPK(vo.getCategoryid(), localeid);
		if (cateVO != null) {
			theForm.setCategoryName(cateVO.getCategoryname());
		}
		// 设置课程名称
		Productbase prodVO = BOFactory.getProductbaseDao().selectByPK(vo.getProductid());
		if(prodVO!=null){
			vo.setProductname(prodVO.getProductname());
		}

		theForm.setVo(vo);
		// 
		String url = "";
		if(theForm.getEditType()==WebConstant.editType_edit){
			url = "addEditPage";
		}else {
			url = "viewPage";
			ShopMini shopvo = BOFactory_Platform.getShopLogic().getShopMini(vo.getShopid(), localeid);
			vo.setShopname(shopvo.getShopname());
			// check current user's relation with this activity
			if(userid!=null){
				Openactivitymember membervo = BOFactory.getOpenactivitymemberDao().selectByPK(vo.getActivityid(), userid);
			    theForm.setMembervo(membervo);
			}
		}
		return mapping.findForward(url);
	}
	
	
	/** 
	 * Method add
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		OpenactivityForm theForm = (OpenactivityForm) form;
		Openactivity activityVO = theForm.getVo();
		theForm.setEditType(WebConstant.editType_add);
		LoginInfoEdu loginfo = getLoginInfo();
		Long localeid = loginfo.getLocaleid();
		// 设置地区编码
		activityVO.setLocaleid(localeid);
		activityVO.setShopid(loginfo.getShopid());
		// 设置目录名称
		Long categoryid = activityVO.getCategoryid();
		Productcategory cateVO = BOFactory_Platform.getProductcategoryDao()
									.selectByLogicPK(categoryid, localeid);
		if (cateVO != null) {
			theForm.setCategoryName(cateVO.getCategoryname());
		}
		if(theForm.getVo().getProductid()!=null){
			Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(activityVO.getProductid());
			if(prodvo!=null){
				activityVO.setProductname(prodvo.getProductname());
			}
		}
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OpenactivityForm theForm = (OpenactivityForm) form;
		Long pk = theForm.getVo().getActivityid();
		String result = String.valueOf(CommonConstant.success);
		String messKey = "";
		try {
			OpenactivityDao dao = BOFactory.getOpenactivityDao();
			dao.deleteByPK(pk);
			messKey = KeyInMemoryConstant.deleteSuccessCommon;
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof LogicException){
				messKey = e.getMessage();
			}else {
				messKey = ExceptionConstant.Error_System;
			}
		}
		String message = getResources(request).getMessage(messKey);
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
}
