package netTestWeb.product.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exception.ExceptionConstant;
import netTest.product.constant.GoodproductConstant;
import netTest.product.constant.ProductConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.GoodproductDao;
import netTest.product.dao.ProductbaseDao;
import netTest.product.dto.ProductbaseQuery;
import netTest.product.logic.ProductLogic;
import netTest.product.vo.Goodproduct;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.product.form.ProductForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.ProductcategoryConstant;
import platform.constant.ShopConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.logicImpl.UsershopLogicImpl;
import platform.social.dao.UsecommentDao;
import platform.vo.Productcategory;
import platform.vo.Shop;
import platform.vo.Usershop;
import commonTool.base.BaseEmptyEntity;
import commonTool.base.Page;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.constant.PayTypeConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

public class ProductAction extends BaseAction {

	static Logger log = Logger.getLogger(ProductAction.class.getName());
	static String SYSCODE = SysparamConstant.syscode;

	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ProductForm theForm = (ProductForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("index".equals(myaction)) {
			myforward = toIndex(mapping, actionform, request, response);
		} else if ("searchProductList".equals(myaction)) {
			myforward = searchProductList(mapping, actionform, request,
					response);
		} else if ("viewProductListMag".equals(myaction)) {
			myforward = listProductListMag(mapping, actionform, request,
					response);
		} else if ("listshopproductmag".equals(myaction)) {
			myforward = listshopproductmag(mapping, actionform, request,
					response);
		} else if ("listshopopenproduct".equals(myaction)) {
			myforward = listshopopenproduct(mapping, actionform, request,
					response);
		} else if ("saveproduct".equals(myaction)) {
			myforward = save(mapping, actionform, request, response);
		} else if ("editproduct".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request, response);
		} else if ("viewproduct".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request, response);
		} else if ("viewproductdesc".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_viewOnly);
			myforward = viewProductDesc(mapping, actionform, request, response);
		} else if ("viewproddescedit".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = viewProductDesc(mapping, actionform, request, response);
		} else if ("editproductdesc".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = viewProductDesc(mapping, actionform, request, response);
		} else if ("saveproductdesc".equals(myaction)) {
			myforward = saveProductDesc(mapping, actionform, request, response);
		} else if ("viewCourseToBuy".equals(myaction)) {
			myforward = viewCourseToBuy(mapping, actionform, request, response);
		} else if ("addproduct".equals(myaction)) {
			myforward = addPage(mapping, actionform, request, response);
		} else if ("delproduct".equals(myaction)) {
			myforward = delete(mapping, actionform, request, response);
		} else if ("addGoodProduct".equals(myaction)) {
			myforward = addGoodProduct(mapping, actionform, request, response);
		} else if ("deleteGoodProduct".equals(myaction)) {
			myforward = deleteGoodProduct(mapping, actionform, request,
					response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}

	/**
	 * Method list
	 */
	// private ActionForward list(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) throws
	// Exception
	// {
	// ProductForm theForm = (ProductForm) form;
	//
	// ProductbaseQuery queryVO = theForm.getQueryVO();
	// // 如果商店categoryid为空, 则设置categoryid为本
	// if(queryVO.getCategoryid()==null ||
	// CommonConstant.TreeTopnodePid.equals(queryVO.getCategoryid())){
	// ProductcategoryLogic cateLogic =
	// BOFactory_Platform.getProductcategoryLogic();
	// Productcategory cateVO = cateLogic.getSystemTopCategory(SYSCODE, null);
	// queryVO.setCategoryid(cateVO.getCategoryid());
	// }
	//
	// ProductbaseDao dao = BOFactory.getProductbaseDao();
	// Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request),
	// getPageSize(request) , getTotalNumber(request));
	// this.setPage(request, page);
	//
	// return mapping.findForward("list");
	// }

	private ActionForward toIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm theForm = (ProductForm) form;
		Long cateid = theForm.getCategoryid();
		Long localeid = theForm.getLocaleid();
		if (localeid != null && !I18nLogicImpl.isSupport(localeid)) {
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		if (localeid == null) {
			localeid = getLoginInfo(true).getUseLocaleid(request);
			theForm.setLocaleid(localeid);
		}
		// 在session中设置categoryid
		BaseEmptyEntity basevo = switchProductCategory(cateid, request,
				localeid, BOFactory_Platform.getProductcategoryLogic());
		cateid = basevo.getId();

		List<Productbase> productlist = null;
		ProductLogic prodLogic = BOFactory.getProductLogic();
		if (cateid != null) {
			productlist = prodLogic.selectRecommendProd(cateid, localeid);
			if (productlist == null || productlist.size() < 1) {
				productlist = prodLogic.selectMostLearned(cateid);
			}
		} else {
			productlist = prodLogic.selectMostLearned(null);
		}
		theForm.setProductList(productlist);
		return mapping.findForward("index");
	}

	/**
	 * 查看产品列表，用于显示所有商店的所有产品的查询，用于后台管理
	 */
	private ActionForward listProductListMag(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		ProductbaseQuery queryVO = theForm.getQueryVO();
		Long cateid = theForm.getCategoryid();
		// 在session中设置categoryid
		BaseEmptyEntity basevo = switchProductCategory(cateid, request,
				localeid, BOFactory_Platform.getProductcategoryLogic());
		cateid = basevo.getId();
		queryVO.setCategoryid(cateid);
		if (queryVO.getLocaleid() == null) {
			queryVO.setLocaleid(localeid);
		}
		if (queryVO.getIsIncludeChild() == -1) {
			// 后台管理界面，默认只查询本目录
			queryVO.setIsIncludeChild(CommonConstant.SearchType_Self);
		}

		if (queryVO.getShopcode() != null
				&& queryVO.getShopcode().trim().length() > 0) {
			Shop shopvo = BOFactory_Platform.getShopDao().selectByCode(
					queryVO.getShopcode().trim());
			queryVO.setShopid(shopvo.getShopid());
		}

		// 查询
		ProductLogic logic = BOFactory.getProductLogic();
		Page page = logic.qryCateProdForMag(queryVO, true,
				getCurrPageNumber(request), getPageSize(request),
				getTotalNumber(request));
		this.setPage(request, page);
		return mapping.findForward("list");
	}

	/**
	 * Method listshopproductmag:商店管理员列出产品管理
	 */
	private ActionForward listshopproductmag(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Long shopid = getLoginInfo().getShopid();
		ProductbaseQuery queryVO = theForm.getQueryVO();
		// 处理切换用户的商店
		shopid = switchShop(shopid, null, true);
		queryVO.setShopid(shopid);
		// 可以查询本目录下的所有子目录的课程
		queryVO.setIsIncludeChild(2);

		ProductLogic logic = BOFactory.getProductLogic();
		Page page = logic.qryCateProdForMag(queryVO, false,
				getCurrPageNumber(request), getPageSize(request),
				getTotalNumber(request));
		this.setPage(request, page);

		// 设置是否允许在选定的category上新增课程
		boolean allowaddprodforcate = false;
		if (queryVO.getCategoryid() != null) {
			Productcategory catevo = BOFactory_Platform.getProductcategoryDao()
					.selectByPK(queryVO.getCategoryid());
			if (catevo != null) {
				allowaddprodforcate = ProductcategoryConstant
						.allowAddProductInCategory(catevo.getCategorylevel());
			}
		}
		theForm.setAllowaddprodforcate(allowaddprodforcate);

		return mapping.findForward("list");
	}

	/**
	 * 商店主界面上的培训产品列表，公开商店
	 */
	private ActionForward listshopopenproduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		ProductbaseQuery queryVO = theForm.getQueryVO();
		// 只查询公开产品
		queryVO.setIsopen(ProductConstant.Isopen_yes);
		// 设置shopid
		if (queryVO.getShopid() == null) {
			queryVO.setShopid(getLoginInfo(true).getShopid());
		}
		AssertUtil.assertNotNull(queryVO.getShopid(), null);
		queryVO.setStatus(ProductConstant.Status_valid);

		// if shop is not open check access to shop
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(
				queryVO.getShopid(), null);
		if (ShopConstant.openType_no.equals(shopvo.getOpentype())) {
			UsershopLogicImpl.getInstance().checkAccessShop(
					getLoginInfo(true).getUserid(), queryVO.getShopid(),
					CommonConstant.SysCode_Education, null);
		}

		ProductbaseDao dao = BOFactory.getProductbaseDao();
		List productList = dao.selectByShop(queryVO.getShopid(),
				queryVO.getCategoryid(), queryVO.getPromotable(),
				queryVO.getPaytype(), queryVO.getIsopen(), queryVO.getStatus());
		theForm.setProductList(productList);

		return mapping.findForward("listshopproduct");
	}

	/**
	 * Method searchProductList: public搜索产品的页面
	 */
	private ActionForward searchProductList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Long cateid = theForm.getCategoryid();
		Long localeid = theForm.getLocaleid();
		if (localeid != null && !I18nLogicImpl.isSupport(localeid)) {
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		if (localeid == null) {
			localeid = getLoginInfo(true).getUseLocaleid(request);
			theForm.setLocaleid(localeid);
		}
		// 在session中设置categoryid
		BaseEmptyEntity basevo = switchProductCategory(cateid, request,
				localeid, BOFactory_Platform.getProductcategoryLogic());
		cateid = basevo.getId();

		ProductbaseQuery queryVO = new ProductbaseQuery();
		queryVO.setCategoryid(cateid);
		// 因为是public搜索，所以包含下级目录查询
		queryVO.setIsIncludeChild(CommonConstant.SearchType_Below);
		queryVO.setLocaleid(localeid);
		queryVO.setRegioncode(theForm.getRealRegionCode());
		queryVO.setProductname(theForm.getProductname());
		// 只能查询有效的和公开的产品
		queryVO.setStatus(ProductConstant.Status_valid);
		queryVO.setIsopen(ProductConstant.Isopen_yes);
		boolean needRecommand = true;
		if (queryVO.getProductname() != null
				&& queryVO.getProductname().trim().length() > 0) {
			needRecommand = false;
		}
		Page page = null;
		ProductLogic logic = BOFactory.getProductLogic();
		page = logic.qryCateProductsPage(queryVO, needRecommand,
				getCurrPageNumber(request), getPageSize(request),
				getTotalNumber(request));

		this.setPage(request, page);
		return mapping.findForward("list");
	}

	/**
	 * Method save
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginInfoEdu loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		ProductForm theForm = (ProductForm) form;
		Productbase vo = theForm.getVo();
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		int actionType = KeyInMemoryConstant.ActionTypeEditInt;
		// new added product
		if (vo != null && vo.getProductbaseid() == null) {
			actionType = KeyInMemoryConstant.ActionTypeAddInt;
			// 新增课程，在未添加课程资料之前，状态只能是准备中
			vo.setStatus(ProductConstant.Status_preparing);
			vo.setShopid(shopid);
			if (ShopConstant.ChargeType_free.equals(shopvo.getChargetype())) {
				vo.setIsopen(ProductConstant.Isopen_yes);
				vo.setPaytype(PayTypeConstant.PayType_free);
			}
			// check whether allow add product in this category level
			AssertUtil.assertNotNull(vo.getCategoryid(), null);
			Productcategory catevo = BOFactory_Platform.getProductcategoryDao().selectByPK(vo.getCategoryid());
			boolean allowaddprodforcate = ProductcategoryConstant.allowAddProductInCategory(catevo.getCategorylevel());
			if (!allowaddprodforcate) {
				throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
			}
		}

		String messCode = KeyInMemoryConstant.AddSuccess;
		if(vo.getProductbaseid()!=null){
			messCode = KeyInMemoryConstant.saveSuccess;
		}
		String url = "";
		
		ProductLogic logic = BOFactory.getProductLogic();
		try {
			vo = logic.save(vo, loginfo.getUserid());
			url = "viewproduct.do?productbaseid="+vo.getProductbaseid()+"&shopid="+shopid;
		} catch (LogicException e) {
			log.info("productid:"+vo.getProductbaseid(), e);
			messCode = e.getMessage();
			url = theForm.getBackUrl();
		}
		// 如果是新增，添加新增的productid到session中, 便于立即使用
		if (actionType == KeyInMemoryConstant.ActionTypeAddInt) {
			loginfo.addProdId(vo.getProductbaseid(),
					UserproductConstant.ProdUseType_operatorMag);
		}
		super.setMessUrlPage(request, url, messCode, "1", "BizKey");
		return mapping.findForward("toUrl");
	}

	/**
	 * Method edit
	 */
	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm theForm = (ProductForm) form;
		ProductbaseQuery queryVO = theForm.getQueryVO();
		Long pk = theForm.getProductbaseid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		// switch product, 因为这是进入产品的第一个url连接，所以要switch product
		switchProduct(pk, queryVO, UserproductConstant.ProdUseType_operatorMag);

		Long localeid = theForm.getLocaleid();
		if (localeid == null || localeid.longValue() == 0) {
			localeid = getLoginInfo().getLocaleid();
		}

		ProductLogic prodLogic = BOFactory.getProductLogic();
		// 这里需要localeid是为了查询category的名称
		Productbase basevo = prodLogic.selectVO(pk, localeid);
		theForm.setVo(basevo);
		// 设置shop charge type
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(
				basevo.getShopid(), null);
		if (ShopConstant.ChargeType_free.equals(shopvo.getChargetype())) {
			theForm.getVo().setPaytype(PayTypeConstant.PayType_free);
			theForm.setAblechangepaytype(false);
		}
		String returnurl = "viewPage";
		if (theForm.getEditType() == WebConstant.editType_edit) {
			returnurl = "editPage";
			// 检查shop是否是open的，如果是非open，则product必须是非公开的
			if (ShopConstant.openType_no.equals(shopvo.getOpentype())) {
				theForm.setIshopopen(false);
				basevo.setIsopen(ProductConstant.Isopen_no);
			}
			// 如果状态不是valid, 查询课程的学习资料是否符合标准，并用于显示
			boolean canpublish = prodLogic.checkCourseForPublish(pk);
			theForm.setCanpublish(canpublish);
		}
		// 设置商店名称
		// theForm.setShopName(getLoginInfo(request, response).getShopname());
		return mapping.findForward(returnurl);
	}
	
	// 发布课程
//	private ActionForward publicCourse(ActionMapping mapping, ActionForm form, 
//			HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		ProductForm theForm = (ProductForm) form;
//		Long productid = theForm.getProductbaseid();
//
//		String result = String.valueOf(CommonConstant.success);
//		String info = "delete_success";
//		try {
//			GoodproductDao dao = BOFactory.getGoodproductDao();
//			dao.deleteByPK(productbaseid, categoryid);
//		} catch (Exception e) {
//			result = String.valueOf(CommonConstant.fail);
//			info = e.getMessage();
//			log.error("oops, got an exception: ", e);
//		}
//		this.writeAjaxRtn(result, info, null, response);
//		return null;
//	}

	/**
	 * view product description
	 */
	private ActionForward viewProductDesc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Productbase vo = theForm.getVo();
		Long pk = vo.getProductbaseid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);

		ProductbaseDao prodDao = BOFactory.getProductbaseDao();
		Productbase basevo = prodDao.selectByPK(pk);
		AssertUtil.assertNotNull(basevo, null);
		// 如果课程是非公开课程，并且session user不是该产品所在的shop中，则抛出unauthorized错误
		if (ProductConstant.Isopen_no.equals(basevo.getIsopen())) {
			Long sessUserID = getLoginInfo().getUserid();
			Usershop usershopvo = BOFactory_Platform.getUsershopDao()
					.selectByLogicPK(basevo.getShopid(), sessUserID);
			if (usershopvo == null) {
				throw new LogicException(
						ExceptionConstant.Error_Product_NotOpen);
			}
		}
		Productbase descvo = prodDao.selectProdDesc(pk);
		if (descvo == null) {
			descvo = vo;
		}
		theForm.setVo(descvo);

		String url = "";
		int viewType = theForm.getEditType();
		if (WebConstant.editType_viewOnly == viewType) {
			url = "viewproddesc";
		} else if (WebConstant.editType_view == viewType) {
			url = "viewproddescedit";
		} else if (WebConstant.editType_edit == viewType) {
			url = "editproddesc";
		}

		return mapping.findForward(url);
	}

	/**
	 * save product description
	 */
	private ActionForward saveProductDesc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Productbase vo = theForm.getVo();
		Long pk = vo.getProductbaseid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);

		ProductbaseDao prodDao = BOFactory.getProductbaseDao();
		prodDao.saveProdExt(vo);

		return mapping.findForward("viewproddescedit");
	}

	/**
	 * view product satis information
	 */
	// private ActionForward viewProductSatis(ActionMapping mapping, ActionForm
	// form,
	// HttpServletRequest request, HttpServletResponse response) throws
	// Exception{
	// ProductForm theForm = (ProductForm) form;
	// Productbase vo = theForm.getVo();
	// Long pk = vo.getProductbaseid();
	// AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
	//
	// ProductbaseDao prodDao = BOFactory.getProductbaseDao();
	// Productbase basevo = prodDao.selectByPK(pk);
	// AssertUtil.assertNotNull(basevo, null);
	// // 如果课程是非公开课程，并且session user不是该产品所在的shop中，则抛出unauthorized错误
	// if(ProductConstant.Isopen_no.equals(basevo.getIsopen())){
	// Long sessUserID = getLoginInfo().getUserid();
	// Usershop usershopvo =
	// BOFactory_Platform.getUsershopDao().selectByLogicPK(basevo.getShopid(),
	// sessUserID);
	// if(usershopvo==null){
	// throw new LogicException(ExceptionConstant.Error_Product_NotOpen);
	// }
	// }
	// Productbase satisvo = prodDao.selectProdSatis(pk);
	// if(satisvo==null){
	// satisvo = vo;
	// }
	// theForm.setVo(satisvo);
	//
	// return mapping.findForward("viewprodsatis");
	// }

	/**
	 * 查看课程的详细信息，以期购买
	 */
	private ActionForward viewCourseToBuy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		ProductbaseQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getProductbaseid();
		Long localeid = theForm.getLocaleid();
		if (localeid == null || localeid.longValue() == 0)
			localeid = getLoginInfo(true).getLocaleid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		ProductLogic prodLogic = BOFactory.getProductLogic();
		Productbase basevo = prodLogic.selectVO(pk, localeid);
		AssertUtil.assertNotNull(basevo,
				ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(basevo);
		// check whether shop is open
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(
				basevo.getShopid(), null);
		if (ShopConstant.openType_no.equals(shopvo.getOpentype())) {
			UsershopLogicImpl.getInstance().checkAccessShop(
					getLoginInfo().getUserid(), basevo.getShopid(),
					CommonConstant.SysCode_Education, null);
		}
		// 如果课程是非公开课程，并且session user不是该产品所在的shop中，则抛出unauthorized错误
		if (ProductConstant.Isopen_no.equals(basevo.getIsopen())) {
			Long sessUserID = getLoginInfo().getUserid();
			Usershop usershopvo = BOFactory_Platform.getUsershopDao()
					.selectByLogicPK(basevo.getShopid(), sessUserID);
			if (usershopvo == null) {
				throw new LogicException(
						ExceptionConstant.Error_Product_NotOpen);
			}
		}
		// 如果product是付费产品，则显示付费信息, 现在在shopCartList页面显示
		// if(!PayTypeConstant.PayType_free.equals(basevo.getPaytype())){
		// Shopext shopext =
		// BOFactory_Platform.getShopextDao().selectByPK(basevo.getShopid());
		// if(shopext!=null){
		// theForm.setPayinfo(shopext.getPayinfo());
		// }
		// }
		// 设置课程评价
		Map<String, String> commentMap = BOFactory_Platform.getUsecommentDao()
				.selectAvgComment(pk, Productbase.ObjectType);
		if (commentMap != null) {
			theForm.setCommentavggrade(commentMap
					.get(UsecommentDao.AvgGradeValue));
			theForm.setCommentusernumber(commentMap
					.get(UsecommentDao.CommentNumber));
		}
		// 设置目录名称
		Long categoryid = theForm.getVo().getCategoryid();
		Productcategory cateVO = BOFactory_Platform.getProductcategoryDao()
				.selectByLogicPK(categoryid, localeid);
		if (cateVO != null) {
			theForm.setCategoryName(cateVO.getCategoryname());
		}
		return mapping.findForward("viewbuyprodPage");
	}

	/**
	 * Method add
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm theForm = (ProductForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long localeid = loginfo.getLocaleid();
		Long shopid = loginfo.getShopid();

		theForm.getVo().setPromotable(ProductConstant.Promotable_no);
		// 产品状态：准备中
		theForm.getVo().setStatus(ProductConstant.Status_preparing);
		// 检查商店是否有权限再创建product
		int avaiablenum = BOFactory.getProductLogic().checkShopProductLimit(shopid);
		if (avaiablenum < 1) {
			throw new LogicException(
					ExceptionConstant.Error_ResourceLimit_MaxShopProduct);
		}
		// set shop property
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		if (ShopConstant.ChargeType_free.equals(shopvo.getChargetype())) {
			theForm.getVo().setPaytype(PayTypeConstant.PayType_free);
			theForm.setAblechangepaytype(false);
		}
        theForm.getVo().setShopid(shopid);
		// 设置目录名称
		Long categoryid = theForm.getVo().getCategoryid();
		Productcategory cateVO = BOFactory_Platform.getProductcategoryDao()
				.selectByLogicPK(categoryid, localeid);
		if (cateVO != null) {
			theForm.setCategoryName(cateVO.getCategoryname());
		}
		// 设置商店名称
		// theForm.setShopName(getLoginInfo(request, response).getShopname());

		return mapping.findForward("addPage");
	}

	/**
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductForm theForm = (ProductForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long productid = theForm.getProductbaseid();
		ProductLogic logic = BOFactory.getProductLogic();
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			logic.deleteProduct(productid, loginfo.getUserid(),
					loginfo.getOrgbaseid());
			info = KeyInMemoryConstant.deleteSuccessCommon;
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
			log.error("oops, got an exception: ", e);
		} catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
			log.error("oops, got an exception: ", e);
		}
		// 得到错误信息
		info = getResources(request, "BizKey").getMessage(info);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}

	// 新增推荐商品
	private ActionForward addGoodProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Productbase vo = theForm.getVo();
		// TODO 要添加保护，如果产品在指定的目录已经是推荐产品了，则返回错误。如果产品不是开放产品或产品是失效状态，则返回错误
		Goodproduct goodprodVO = new Goodproduct();
		goodprodVO.setCategoryid(vo.getCategoryid());
		goodprodVO.setFromsource(GoodproductConstant.FromSource_AdminAdd);
		goodprodVO.setLastdate(DateUtil.getInstance().getNowtime_GLNZ());
		goodprodVO.setProductbaseid(vo.getProductbaseid());
		goodprodVO.setShopid(vo.getShopid());
		if (vo.getGoodproductscope() == null) {
			goodprodVO.setScope(GoodproductConstant.Scope_Local);
		} else {
			goodprodVO.setScope(vo.getGoodproductscope());
		}

		String result = String.valueOf(CommonConstant.success);
		String info = "operation_success";
		try {
			GoodproductDao dao = BOFactory.getGoodproductDao();
			dao.insert(goodprodVO);
		} catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
			log.error("oops, got an exception: ", e);
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}

	// 删除推荐商品
	private ActionForward deleteGoodProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductForm theForm = (ProductForm) form;
		Productbase vo = theForm.getVo();

		Long categoryid = vo.getCategoryid();
		Long productbaseid = vo.getProductbaseid();

		String result = String.valueOf(CommonConstant.success);
		String info = "delete_success";
		try {
			GoodproductDao dao = BOFactory.getGoodproductDao();
			dao.deleteByPK(productbaseid, categoryid);
		} catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
			log.error("oops, got an exception: ", e);
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}

}
