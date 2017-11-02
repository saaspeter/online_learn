
package netTestWeb.product.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.UserproductDao;
import netTest.product.dto.UserproductQuery;
import netTest.product.logic.UserproductLogic;
import netTest.product.logic.impl.ProductLogicImpl;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userproduct;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.product.form.UserproductForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import platform.constant.UsershopConstant;
import platform.dao.UsershopDao;
import platform.dto.UsershopQuery;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.ShopMini;
import platform.vo.User;
import platform.vo.Usershop;
import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.impl.RolesDaoImpl;


public class UserproductAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserproductAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		UserproductForm theForm = (UserproductForm) actionform;

		if ("".equals(myaction)) { 
			myforward = mapping.findForward("failure");
		} else if ("myUserproduct".equals(myaction)) {
			myforward = myprodlist(mapping, actionform, request,response);
		} else if ("listshopprodforuser".equals(myaction)) {
			myforward = listShopProdForUser(mapping, actionform, request,response);
		} else if ("listuserprodmag".equals(myaction)) {
			theForm.setListprodusertype(1);
			myforward = listUserprodMag(mapping, actionform, request,response);
		} else if ("listoneproduser".equals(myaction)) {
			theForm.setListprodusertype(0);
			myforward = listUserprodMag(mapping, actionform, request,response);
		} else if ("seluserbyproduct".equals(myaction)) {
			myforward = selProductUser(mapping, actionform, request,response);
		} else if ("seloneprodmaguser".equals(myaction)) {
			myforward = selOneProdMagUser(mapping, actionform, request,response);
		} else if ("saveoneuserprod".equals(myaction)) {
			if(theForm.getVo().getUserproductid()==null){
				if(theForm.getVo().getUserid()!=null){
					theForm.setUseridStr(theForm.getVo().getUserid().toString());
				}else {
					throw new LogicException(ExceptionConstant.Error_Need_Paramter);
				}
				request.setAttribute("pageAction", "closeDiv");
			    myforward = saveAddUserProd(mapping, actionform, request,response);
			}else {
				myforward = saveUpdUserProd(mapping, actionform, request,response);
			}
		} else if ("adduserprod".equals(myaction)) {
			myforward = addProdPage(mapping, actionform, request,response);
		} else if ("edituserprod".equals(myaction)) {
			myforward = editProdPage(mapping, actionform, request,response);
		} else if ("deluserprod".equals(myaction)) {
			myforward = delUserProd(mapping, actionform, request,response);
		} else if ("viewUserproduct".equals(myaction)) {
			myforward = viewproductdetail(mapping, actionform, request,response);
		} else if ("addprodmoreuserpage".equals(myaction)) {
			myforward = addProdMoreUserPage(mapping, actionform, request,response);
		} else if ("saveprodmoreuser".equals(myaction)) {
			myforward = saveAddUserProd(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * 用户自己查看自己的产品信息，主要是用户自己的界面显示
     * @security 用户自己学习时使用
	 */
	public ActionForward myprodlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long userid = getLoginInfo().getUserid();
		Long shopid = theForm.getShopid();

		UserproductQuery queryVO = theForm.getQueryVO();
        // 处理切换用户的商店
		shopid = switchShop(shopid, null, false);
		
		Page page = BOFactory.getUserproductLogic().listMyProduct(userid, shopid, queryVO.getStatus(),
												getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("listpage");
	}
	
	/** 
	 * 管理员参看单个用户的产品信息，仅仅可查询管理员所在的商店中的用户的产品
	 */
	public ActionForward listShopProdForUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long userid = theForm.getUserid();
		Long shopid = getLoginInfo().getShopid();
		
		UserproductQuery queryVO = theForm.getQueryVO();
		queryVO.setUserid(userid);
		queryVO.setShopid(shopid);
				
		UserproductLogic logic = BOFactory.getUserproductLogic();
		Page page = logic.listUserProduct(null, userid, queryVO.getProdusetype(), shopid, null, null,
				                  getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		
//		 检查用户在该商店中是否还有别的学习产品，用于提示操作员是否要删除改用户的信息
        String refResult = logic.checkUserProdLink(userid, shopid);
        if("0".equals(refResult)){
        	theForm.setUserhasprod("0");
        }
		return mapping.findForward("listpage");
	}
	
	
	/** 
	 * 管理员查看产品用户信息, 只是在一个商店中查看
	 */
	public ActionForward listUserprodMag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long shopid = getLoginInfo().getShopid();
		
		UserproductQuery queryVO = theForm.getQueryVO();
		queryVO.setShopid(shopid);
		
		String retpage = "";
		int listprodusertype = theForm.getListprodusertype();
		if(listprodusertype==0){ // 查询其中一个产品的学员列表
			AssertUtil.assertNotNull(queryVO.getProductbaseid(), ExceptionConstant.Error_Need_Paramter);
			Productbase productvo = BOFactory.getProductbaseDao().selectByPK(queryVO.getProductbaseid());
			theForm.setProductvo(productvo);
			retpage = "listonepage";
		}else {  // 查询所有的课程学员列表
			retpage = "listallpage";
		}
		
		Page page = Page.EMPTY_PAGE;
		Long userid = null;
		boolean needsearch = true;
		if(queryVO.getLoginname()!=null && queryVO.getLoginname().trim().length()>0){
			User searchuserVO = BOFactory_Platform.getUserDao().selectByLogin(queryVO.getLoginname().trim());
		    if(searchuserVO!=null){
		    	userid = searchuserVO.getUserid();
		    }else {
		    	// 当用户输入了loginname, 又不存在该user的情况，不需要查询，直接返回empty
		    	needsearch = false;
		    }
		}
		
		if(needsearch){
			UserproductLogic logic = BOFactory.getUserproductLogic();
			page = logic.listUserProduct(queryVO.getProductbaseid(), userid, queryVO.getProdusetype(), queryVO.getShopid(), 
				                         null, null, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		}
		this.setPage(request, page);
		return mapping.findForward(retpage);
	}
	
	/** 
	 * 选择学习某个产品的人员
	 */
	public ActionForward selProductUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long shopid = getLoginInfo().getShopid();
		
		UserproductQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getProductbaseid(), ExceptionConstant.Error_Need_Paramter);
		
		Page page = Page.EMPTY_PAGE;
		boolean needsearch = true;
		Long userid = null;
		if(queryVO.getLoginname()!=null && queryVO.getLoginname().trim().length()>0){
			User searchuserVO = BOFactory_Platform.getUserDao().selectByLogin(queryVO.getLoginname().trim());
			if(searchuserVO!=null){
				userid = searchuserVO.getUserid();
			}else {
				// 当用户输入了loginname, 又不存在该user的情况，不需要查询，直接返回empty
				needsearch = false;
			}
		}
		if(needsearch){
		   UserproductLogic logic = BOFactory.getUserproductLogic();
		   page = logic.listUserProduct(queryVO.getProductbaseid(), userid, null, 
										  shopid, UserproductConstant.Status_active, null,
										  getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		}
		this.setPage(request, page);
		return mapping.findForward("listpage");
	}
	
	
	/** 
	 * 选择可以管理某个产品的人员，包括商店管理员，所有产品管理员，这个产品的管理员
	 * 目前用于选择管理考试的人员
	 */
	public ActionForward selOneProdMagUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long shopid = getLoginInfo().getShopid();
		UserproductQuery queryVO = theForm.getQueryVO();
		AssertUtil.assertNotNull(queryVO.getProductbaseid(), ExceptionConstant.Error_Need_Paramter);
		List returnList = null;
		
		Page page = Page.EMPTY_PAGE;
		boolean needsearch = true;
		Long userid = null;
		if(queryVO.getLoginname()!=null && queryVO.getLoginname().trim().length()>0){
			User searchuserVO = BOFactory_Platform.getUserDao().selectByLogin(queryVO.getLoginname().trim());
			if(searchuserVO!=null){
				userid = searchuserVO.getUserid();
			}else {
				// 当用户输入了loginname, 又不存在该user的情况，不需要查询，直接返回empty
				needsearch = false;
			}
		}
		// 查询这个产品的管理员
		if(needsearch){
			UserproductLogic logic = BOFactory.getUserproductLogic();
			page = logic.listUserProduct(queryVO.getProductbaseid(), userid, UserproductConstant.ProdUseType_operatorMag, 
					shopid, UserproductConstant.Status_active,null, 1, -1, getTotalNumber(request));
			if(page!=null) {
				returnList = page.getList();
			}
		}
		
		// 查询该商店中的 商店管理员，所有产品管理员
		String[] rolecodeArr = new String[]{RolesConstant.ROLE_ShopCreator, 
		             RolesConstant.ROLE_ShopAdmin, RolesConstant.ROLE_ShopProductAdmin};
		RolesDao roleDao = RolesDaoImpl.getInstance();
		String roleidStr = roleDao.selRoleidStrByCode(rolecodeArr, CommonConstant.SysCode_Education);
		UsershopQuery usershopQueryVO = new UsershopQuery();
		usershopQueryVO.setRoleidStr(roleidStr);
		usershopQueryVO.setShopid(shopid);
		usershopQueryVO.setUsershopstatus(UsershopConstant.UserShopStatus_active);
		UsershopDao usershopdao = platform.logicImpl.BOFactory_Platform.getUsershopDao();
		List magRoleUserList = usershopdao.selectByVO(usershopQueryVO);
		// 合并两个集合
		if(magRoleUserList!=null){
			if(returnList==null){
				returnList = new ArrayList();
			}
			Userproduct tempUserProductVO = null;
			Usershop tempUserShopVO = null;
			for(int i=0;i<magRoleUserList.size();i++){
				tempUserShopVO = (Usershop)magRoleUserList.get(i);
				tempUserProductVO = new Userproduct();
				tempUserProductVO.setUserid(tempUserShopVO.getUserid());
				tempUserProductVO.setProductbaseid(queryVO.getProductbaseid());
				tempUserProductVO.setDisplayname(CustomerConstant.combineDisplayname(tempUserShopVO.getNickname(),tempUserShopVO.getLoginname()));
				returnList.add(tempUserProductVO);
			}
		}
		theForm.setDatalist(returnList);
		return mapping.findForward("listpage");
	}
	
	/** 
	 * 保存用户新增的产品信息，为用户开通新产品
	 */
	public ActionForward saveAddUserProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Userproduct vo = theForm.getVo();
		String useridStr = theForm.getUseridStr();
		if(useridStr==null||useridStr.trim().length()<1||vo.getProdusetype()==null||vo.getProductbaseid()==null){
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		}
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long operatorid = loginfo.getUserid();
		
		String[] useridArr = StringUtil.splitString(useridStr, ",");
		String messCode = KeyInMemoryConstant.AddSuccess;

       	UserproductLogic logic = BOFactory.getUserproductLogic();
       	logic.addUserprodFromEdu(vo.getProductbaseid(), useridArr, shopid, vo.getProdusetype(), operatorid);
		// set messag page parameters.
       	
		this.setMessagePage(request, theForm, messCode, null, "BizKey");
		return mapping.findForward("toUrl");
	}
	
	public ActionForward saveUpdUserProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Userproduct vo = theForm.getVo();
		if(vo.getUserproductid()==null||vo.getStatus()==null){
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		}
		Long operatorid = getLoginInfo().getUserid();
		String messCode = KeyInMemoryConstant.modifySuccess;
		String statusdesc = theForm.getStatusdesc();

		UserproductLogic logic = BOFactory.getUserproductLogic();
		logic.updUsetypeStatusByPK(vo.getUserproductid(), vo.getProdusetype(), vo.getStatus(), operatorid, statusdesc);
		// set messag page parameters.
		this.setMessagePage(request, theForm, messCode, null, "BizKey");
		return mapping.findForward("toUrl");
	}
	
	
	/** 
	 * 为用户添加产品页面
	 */
	public ActionForward addProdPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		theForm.setLocaleid(localeid);
		theForm.getVo().setProdusetype(UserproductConstant.ProdUseType_userNormal);
		//TODO check whether user has operator role and set flage in ActionForm
		
		return mapping.findForward("addpage");
	}
	
	/** 
	 * 为产品添加多个用户页面
	 */
	public ActionForward addProdMoreUserPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		theForm.setLocaleid(localeid);
		theForm.getVo().setProdusetype(UserproductConstant.ProdUseType_userNormal);		
		return mapping.findForward("addpage");
	}
	
	/** 
	 * 编辑用户产品界面
	 */
	public ActionForward editProdPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		Userproduct vo = theForm.getVo();
		UserproductDao dao = BOFactory.getUserproductDao();
		vo = dao.selectByPK(vo.getUserproductid());
		AssertUtil.assertNotNull(vo, null);
		
		Productbase prodVO = ProductLogicImpl.getInstance().selectVO(vo.getProductbaseid(), localeid);
		vo.setCategoryid(prodVO.getCategoryid());
		vo.setCategoryname(prodVO.getCategoryname());
		vo.setProductname(prodVO.getProductname());
		theForm.setVo(vo);
		// get usershop and also check whether user has product manage Role
		boolean caneditproduct = false;
		Usershop usershopVO = BOFactory_Platform.getUsershopLogic().selectByLogicPK(vo.getShopid(), vo.getUserid());
		if(UsershopConstant.Usershoptype_Orguser.equals(usershopVO.getUsershoptype())){
			caneditproduct = true;
		}
		//TODO 查询用户是否有product manage Role
		theForm.setCaneditproduct(caneditproduct);
		
		return mapping.findForward("editpage");
	}
	
	/** 
	 * 删除用户产品，管理员暂时不能手动使用该方法，用户的产品失效时通过产品使用流程完成的。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward delUserProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserproductForm theForm = (UserproductForm) form;
		
		Long pk = theForm.getVo().getUserproductid();
		Long operatorid = getLoginInfo().getUserid();
		
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		// 删除
		UserproductLogic logic = BOFactory.getUserproductLogic();
        try {
			logic.checkAndDelUserproduct(pk, operatorid);
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
			info = getResources(request, "BizKey").getMessage(info);
			log.error("----error1 in Class:UserproductAction Method:delUserProd", e);
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = getResources(request, "BizKey").getMessage(ExceptionConstant.Error_System);
			log.error("----error2 in Class:UserproductAction Method:delUserProd", e);
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
    /**
     * 查看具体的用户产品信息
     */
	public ActionForward viewproductdetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu loginfo = getLoginInfo();
		Long sessionUserID = loginfo.getUserid();
		Long localeid = loginfo.getLocaleid();
		UserproductForm theForm = (UserproductForm) form;
		Long pk = theForm.getVo().getUserproductid();
		UserproductDao dao = BOFactory.getUserproductDao();
		Userproduct vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		
		Productbase prodVO = ProductLogicImpl.getInstance().selectVO(vo.getProductbaseid(), localeid);
		vo.setCategoryid(prodVO.getCategoryid());
		vo.setCategoryname(prodVO.getCategoryname());
		vo.setProductname(prodVO.getProductname());
		
		ShopMini shopmini = BOFactory_Platform.getShopLogic().getShopMini(prodVO.getShopid(), localeid);
		vo.setShopname(shopmini.getShopname());
		
		theForm.setVo(vo);
		if(sessionUserID.equals(vo.getUserid())){
			theForm.setViewidentity("self");
		}
		
		return mapping.findForward("detailpage");
	}
	
}
