package netTestWeb.platform.shop.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.order.constant.OrderConstant;
import netTest.order.dao.CustorderDao;
import netTest.order.dto.CustorderQuery;
import netTest.product.dao.ProductbaseDao;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.platform.shop.form.ShopForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.ShopConstant;
import platform.dao.ShopDao;
import platform.dao.ShopstyleconfigDao;
import platform.dao.ShopvalueDao;
import platform.dto.ShopQuery;
import platform.exception.ExceptionConstant;
import platform.logic.ShopLogic;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.shop.logic.ProdcateshopLogic;
import platform.shop.vo.Shopcontactinfo;
import platform.shop.vo.Shopext;
import platform.vo.Shop;
import platform.vo.Shopstatuslog;
import platform.vo.Shopstyleconfig;
import platform.vo.Shopvalue;
import platform.vo.User;
import platform.vo.Usershop;
import commonTool.base.BaseEmptyEntity;
import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonWeb.security.authentication.UserinfoSession;


public class ShopAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopAction.class.getName());
	static String SYSCODE = SysparamConstant.syscode;
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopForm theForm = (ShopForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("toshop".equals(myaction)) {
			myforward = toShop(mapping, actionform, request,response);
		} else if ("myOwnShops".equals(myaction)) {
			myforward = myOwnShops(mapping, actionform, request,response);
		} else if ("updatesaveshop".equals(myaction)) {
			theForm.setIsadminoper(false);
			myforward = updateSaveShop(mapping, actionform, request,response);
		} else if ("adminsaveshop".equals(myaction)) {
			theForm.setIsadminoper(true);
			myforward = updateSaveShop(mapping, actionform, request,response);
		} else if ("editshoppage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editShopPage(mapping, actionform, request,response);
		} else if ("viewshopeditpage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editShopPage(mapping, actionform, request,response);
		} else if ("viewshoppage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_viewOnly);
			myforward = editShopPage(mapping, actionform, request,response);
		} else if ("listshop".equals(myaction)) {
			myforward = listshop(mapping, actionform, request,response);
		} else if ("listShopIndex".equals(myaction)) {
			myforward = listShopIndex(mapping, actionform, request,response);
		} else if ("shopStatusChangePage".equals(myaction)) {
			myforward = shopStatusChangePage(mapping, actionform, request,response);
		} else if ("saveShopStatus".equals(myaction)) {
			myforward = saveShopStatus(mapping, actionform, request,response);
		} else if ("deleteShop".equals(myaction)) {
			myforward = deleteShop(mapping, actionform, request,response);
		} else if ("shopmagmessage".equals(myaction)) {
			myforward = shopmagMessage(mapping, actionform, request,response);
		} else if ("listapplyshopauthen".equals(myaction)) {
			myforward = listapplyshopauthen(mapping, actionform, request,response);
		} else if ("applyshopauthen".equals(myaction)) {
			myforward = applyshopauthen(mapping, actionform, request,response);
		} else if ("saveapplyshopauthen".equals(myaction)) {
			theForm.setIsadminoper(false);
			myforward = saveapplyshopauthen(mapping, actionform, request,response);
		} else if ("approveshopauthen".equals(myaction)) {
			theForm.setIsadminoper(true);
			myforward = saveapplyshopauthen(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	private ActionForward toShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Long shopid = theForm.getShopid();
		String shopcode = theForm.getShopcode();
		int loadauthority = theForm.getLoadauthority();
		UserinfoSession loginfo = getLoginInfo(true);
		Long sessshopid = loginfo.getShopid();
		if(shopid==null&&shopcode==null&&sessshopid==null){
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		}
		// 加载商店权限
		if(loadauthority==1 && !loginfo.isAnonymous()
			&& (sessshopid==null || !sessshopid.equals(shopid))) {
			BOFactory.getUserLoginSessionLogic().loadAuthorizeAndProductInShop((LoginInfoEdu)loginfo, shopid);
		}
		Shop shopvo = null;
		if(shopid!=null){
			Long localeid = loginfo.getLocaleid();
			shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, localeid);
		}else if(shopcode!=null){
			shopvo = BOFactory_Platform.getShopDao().selectByCode(shopcode);
		}
		AssertUtil.assertNotNull(shopvo, null);
		if(theForm.getShopid()==null){
			request.setAttribute("shopid", shopvo.getShopid());
		}
		theForm.setVo(shopvo);
		theForm.setLoginname(loginfo.getLoginname());
		return mapping.findForward("default");
	}
	
	/**
	 * 商店管理页面显示的信息提醒页面
	 */
	private ActionForward shopmagMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = theForm.getShopid();
		if(shopid==null){
			shopid = loginfo.getShopid();
			theForm.setShopid(shopid);
		}else {
		    shopid = switchShop(shopid, null, true);
		}
		// 检查商店是否设置了商店简介
		ShopvalueDao valueDao = platform.logicImpl.BOFactory_Platform.getShopvalueDao();
		Shopvalue shopvalueVO = valueDao.selectByPK(shopid, null);
		if(shopvalueVO==null || shopvalueVO.getShopdesc()==null 
				|| shopvalueVO.getShopdesc().length()<1){
			theForm.setHasshopdescript("no");
		}else {
			theForm.setHasshopdescript("yes");
		}
		// 检查订单order是否有未审批的
		CustorderQuery queryVO = new CustorderQuery();
		queryVO.setShopid(shopid);
		queryVO.setOrderstatus(OrderConstant.OrderStatus_submit);
		CustorderDao orderDao = BOFactory.getCustorderDao();
		Integer ordercount = orderDao.selectCount(queryVO);
		theForm.setOrdertodocount(ordercount);
		// 如果未审批的订单数目小于0
		if(ordercount<1){
			// 查询商店产品数量
			ProductbaseDao productdao = BOFactory.getProductbaseDao();
			int prodnum = productdao.selShopProdCount(shopid);
			if(prodnum<1){
				theForm.setHasshopproduct("no");
				// 查询商店有没有设置产品目录
				ProdcateshopLogic cateshoplogic = BOFactory_Platform.getProdcateshopLogic();
		        List catelist = cateshoplogic.getShopCateChildNodes(null, shopid, loginfo.getLocaleid(), SYSCODE);
			    if(catelist==null || catelist.size()<1){
			    	theForm.setHasshopcategory("no");
			    }else {
			    	theForm.setHasshopcategory("yes");
			    }
			}else {
				theForm.setHasshopproduct("yes");
			}
		}
		//TODO 查找商店或产品留言
		
		return mapping.findForward("shopmagmesspage");
	} 
	
//	/** 
//	 * 用户自己加入的商店列表
//	 */
//	private ActionForward myJoinShops(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		ShopForm theForm = (ShopForm) form;
//		LoginInfoEdu loginfo = getLoginInfo();
//		Long localeid = loginfo.getLocaleid();
//		Long userid = loginfo.getUserid();
//		Short usershopstatus = theForm.getUsershopstatus();
//
//        UsershopLogic logic = platform.logicImpl.BOFactory_Platform.getUsershopLogic();
//    	List<Usershop> list = logic.qryUserShop(userid, usershopstatus, localeid);
//		theForm.setUsershopList(list);
//		return mapping.findForward("userjoinshoplist");
//	}
	
	/** 
	 * 我创建的商店列表和我加入的商店列表
	 */
	private ActionForward myOwnShops(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long localeid = loginfo.getLocaleid();
		Long userid = loginfo.getUserid();
		ShopQuery queryVO = theForm.getQueryVO();
		queryVO.setUserid(userid);
		// 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
		// 查询我创建的商店
        ShopDao shopdao = platform.logicImpl.BOFactory_Platform.getShopDao();
    	List shoplist = shopdao.selectByVO(queryVO);
    	//
    	Set<Long> set = new HashSet();
    	if(shoplist!=null && shoplist.size()>0){
    		Shop shopvotemp = null;
    		Shopstyleconfig configVO = null;
    	    ShopstyleconfigDao configDao = platform.logicImpl.BOFactory_Platform.getShopstyleconfigDao();
    	    for(int i=0;i<shoplist.size();i++){
    	    	shopvotemp = (Shop)shoplist.get(i);
    	    	configVO = configDao.selectByPK(shopvotemp.getShopid());
    	    	if(configVO!=null){
    	    		shopvotemp.setBannerimg(configVO.getBannerimg());
    	    	}
    	    	set.add(shopvotemp.getShopid());
    	    }
    	}
		theForm.setShopList(shoplist);
		
		// 查询我加入的学校列表
		Short usershopstatus = theForm.getUsershopstatus();
		UsershopLogic logic = platform.logicImpl.BOFactory_Platform.getUsershopLogic();
    	List<Usershop> list = logic.qryUserShop(userid, usershopstatus, localeid);
    	//过滤自己创建的学校
    	if(list!=null && list.size()>0){
    		for(int j=list.size()-1; j>-1; j--){
    			if(set.contains(list.get(j).getShopid())){
    				list.remove(j);
    			}
    		}
    	}
		theForm.setUsershopList(list);
		
		return mapping.findForward("useropenshoplist");
	}
	
	/** 
	 * Method edit
	 */
	private ActionForward editShopPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		LoginInfoEdu userinfo = getLoginInfo();
		Long localeid = userinfo.getLocaleid();
		ShopQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getShopid();
		if(pk==null){
			pk = userinfo.getShopid();
		}
		AssertUtil.assertNotNull(pk, null);
		ShopDao dao = BOFactory_Platform.getShopDao();

        // 查询商店信息,如果默认locale没有设置，则用该商店设置的第一个locale作为查询locale
		Shop vo = dao.selectByPK(pk, localeid);
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		// get shop creator
		User creator = BOFactory_Platform.getUserDao().selectByPK(vo.getUserid());
		vo.setCreatorname(creator.showDisplayLoginame());
		theForm.setVo(vo);
		//
		Shopext extvo = BOFactory_Platform.getShopextDao().selectByPK(pk);
		theForm.setExtvo(extvo);
		
		int edittype = theForm.getEditType();
		String url = "viewPage";
		if(CommonConstant.editType_edit == edittype){
			url = "editPage";
		}else if(CommonConstant.editType_view == edittype){
			url = "viewEditPage";
		}
		return mapping.findForward(url);
	}
	
	private ActionForward updateSaveShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
        Shop vo = theForm.getVo();
        AssertUtil.assertNotNull(vo.getShopid(), null);
        // 不能修改creator
        vo.setUserid(null);
        // 只有后台super admin的界面才能操作这3个字段
        if(!theForm.isIsadminoper()){
        	vo.setIsauthen(null);
        	vo.setChargetype(null);
        	vo.setShopstatus(null);
        }
		ShopLogic logic = platform.logicImpl.BOFactory_Platform.getShopLogic();
		logic.updateShop(vo);
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method list:商店列表，为管理平台查询用，可以查询所有商店
	 * 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
	 */
	private ActionForward listshop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		ShopQuery queryVO = theForm.getQueryVO();
		String searchtext = theForm.getSearchtext();
		queryVO.setSearchtext(searchtext);
		Long localeid = getLoginInfo().getLocaleid();
		// 目前限制了查询商店必须指定一种Locale，默认为登录者的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
		// 选择商店
		ShopDao dao = BOFactory_Platform.getShopDao();
		Page page = dao.selectByVOPage(theForm.getCategoryid(), theForm.getRealRegionCode(), queryVO.getOwnertype(), queryVO.getOpentype(), 
				searchtext, queryVO.getLocaleid(), queryVO.getShopstatus(),
				getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);

		return mapping.findForward("list1");
	}
	
	/** 
	 * 首页上查询商店列表
	 */
	private ActionForward listShopIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		String searchtext = theForm.getSearchtext();
		Long categoryid = theForm.getCategoryid();
		Long localeid = getLoginInfo(true).getUseLocaleid(request);
		// 转换categoryid, 并记录session中
		BaseEmptyEntity basevo = switchProductCategory(categoryid, request, localeid, BOFactory_Platform.getProductcategoryLogic());
		categoryid = basevo.getId();
		ShopQuery queryVO = theForm.getQueryVO();
		// 目前限制了查询商店必须指定一种Locale，默认为登录者选择的Locale
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        	theForm.setQueryVO(queryVO);
        }
        
        ShopDao dao = BOFactory_Platform.getShopDao();
        Short opentype = null;
        //opentype = ShopConstant.openType_yes
        // categoryid, regioncode, searchText, localeid, shopstatus
        Page page = dao.selectByVOPage(categoryid, theForm.getRealRegionCode(), queryVO.getOwnertype(), opentype,
        					searchtext, queryVO.getLocaleid(), ShopConstant.ShopStatus_run,
        					getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);

		return mapping.findForward("shoplistIndex");
	}
	
	/** 
	 * Method statusChangePage
	 */
	private ActionForward shopStatusChangePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		ShopQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getShopid();
		AssertUtil.assertNotNull(pk, null);
		ShopDao dao = BOFactory_Platform.getShopDao();
        // 查询商店信息,如果默认locale没有设置，则用该商店设置的第一个locale作为查询locale
		Shop vo = dao.selectByPK(pk, localeid);
		if(vo==null){
            List list = dao.selectByVO(queryVO);
            if(list!=null&&list.size()>0)
			   vo = (Shop)list.get(0);
		}
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);
        return mapping.findForward("statusChangePage");
	}
	
	private ActionForward saveShopStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Shop vo = theForm.getVo();
		Shopstatuslog logVO = theForm.getLogVO();
		ShopLogic logic = BOFactory_Platform.getShopLogic();
		boolean result = logic.changeStatus(vo,logVO);
		String messCode = "platformWeb.UserAction.changeStatus.success";
		if(!result)
		   messCode = "platformWeb.UserAction.changeStatus.failure";
		
		super.setMessagePage(request,theForm, messCode, "1","platformKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 //TODO 删除很复杂 
	 */
	public ActionForward deleteShop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Long shopid = theForm.getShopid();
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try{
			ShopLogic logic = BOFactory_Platform.getShopLogic();
			logic.delShop(shopid);
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		// 得到错误信息
		info = getResources(request, "BizKey").getMessage(info);
		this.writeAjaxRtn(result, null, info, response);
		return null;
	}
	
	/** 
	 * 列出申请付费商店
	 */
	private ActionForward listapplyshopauthen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
        Shopext extvo = theForm.getExtvo();
        Short authenstatus = extvo.getAuthenstatus();
        if(authenstatus==null){
        	extvo.setAuthenstatus(ShopConstant.AuthenStatus_apply);
        }
		Page page = BOFactory_Platform.getShopextDao().selectByVOPage(extvo, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		if(page!=null && page.getList()!=null){
			ShopDao shopdao = BOFactory_Platform.getShopDao();
			Shop shopvotemp = null;
			Shopext temvo = null;
			for(int i=0; i<page.getList().size(); i++){
				temvo = (Shopext)page.getList().get(i);
				shopvotemp = shopdao.selectByPK(temvo.getShopid(), null);
				temvo.setShopcode(shopvotemp.getShopcode());
				temvo.setShopname(shopvotemp.getShopname());
			}
		}
		this.setPage(request, page);
		return mapping.findForward("listpage");
	}
	
	/** 
	 * 申请认证商店，包括申请付费商店
	 */
	private ActionForward applyshopauthen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShopForm theForm = (ShopForm) form;
		Long shopid = theForm.getShopid();
		if(shopid==null){
			shopid = getLoginInfo().getShopid();
		}
		Shop vo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		theForm.setVo(vo);
		Shopext extvo = BOFactory_Platform.getShopextDao().selectByPK(shopid);
		if(extvo!=null){
			theForm.setExtvo(extvo);
		}
		Shopcontactinfo contactvo = BOFactory_Platform.getShopcontactinfoDao().selectDefaultByShopid(shopid);
		theForm.setContactvo(contactvo);
		return mapping.findForward("editpage");
	}
	
	/**
	 * 保存商店认证申请
	 */
	private ActionForward saveapplyshopauthen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		ShopForm theForm = (ShopForm) form;
		Shopext extvo = theForm.getExtvo();
		boolean isapprove = theForm.isIsadminoper();
		String messCode = KeyInMemoryConstant.AddSuccess;
		try{
			AssertUtil.assertNotNull(extvo.getShopid(), null);
			BOFactory_Platform.getShopLogic().saveShopauthen(extvo, userid, isapprove);
		} catch (LogicException e) {
			messCode = e.getMessage();
		} catch (Exception e){
			messCode = ExceptionConstant.Error_System;
		}
		// 得到错误信息
//		info = getResources(request, "BizKey").getMessage(info);
		
		// set messag page parameters.
		request.setAttribute("pageAction", "closeDiv");
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
}
