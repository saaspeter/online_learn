package netTestWeb.category.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.category.form.ProductcategoryForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.ProductcategoryDao;
import platform.dto.ProductcategoryQuery;
import platform.logic.ProductcategoryLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Productcategory;

import commonTool.base.Page;
import commonTool.biz.logic.I18nLogic;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.HasChildException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;
import commonTool.util.TreeUtil;

/** 
 *  
 * XDoclet definition:
 * @struts.action path="/productCategoryAction" name="productCategoryForm" input="/product/category_newEdit.jsp" scope="request" validate="true"
 */
public class ProductcategoryAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ProductcategoryAction.class.getName());
	static String SYSCODE = SysparamConstant.syscode;
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ProductcategoryForm theForm = (ProductcategoryForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listproductcategory".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("toprodcategorytreepage".equals(myaction)) {
			theForm.setUrlType(1);
			myforward = totreepage(mapping, actionform, request,response);
		} else if ("selprodcategorytreepage".equals(myaction)) {
			theForm.setUrlType(2);
			myforward = totreepage(mapping, actionform, request,response);
		} else if ("prodcategorytree".equals(myaction)) {
			myforward = geneTreeXml(mapping, actionform, request,response);
		} else if ("saveproductcategory".equals(myaction)){
			myforward = save(mapping, actionform, request,response);
		} else if ("addproductcategory".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("editproductcategory".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewproductcategory".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("delproductcategory".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long localeid = getLoginInfo(true).getUseLocaleid(request);
		ProductcategoryForm theForm = (ProductcategoryForm) form;
		ProductcategoryQuery queryVO = theForm.getQueryVO();
		int needPid = theForm.getNeedPid();

		if(needPid!=2){//如果不需要清空pid
	       if(queryVO!=null&&queryVO.getPid()==null)
	    	   queryVO.setPid(CommonConstant.TreeTopnodePid);
		}else
			queryVO.setPid(null);// 需要清空pid
		queryVO.setLocaleid(localeid);
	
		ProductcategoryDao dao = BOFactory_Platform.getProductcategoryDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);

		return mapping.findForward("list");
	}
	
	/** 
	 * Method 显示上级目录列表
	 */
//	private ActionForward upperList(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		Long localeid = UserinfoSession.getUseLocaleid(request);
//		ProductcategoryForm theForm = (ProductcategoryForm) form;
//		ProductcategoryQuery queryVO = theForm.getQueryVO();
//		
//	    if(queryVO!=null&&queryVO.getPid()==null)
//	    	queryVO.setPid(CommonConstant.TreeTopnodePid);
//	    queryVO.setLocaleid(localeid);
//	
//		ProductcategoryLogic logic = BOFactory_Platform.getProductcategoryLogic();
//		Page page = logic.qryUpperCate(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
//		this.setPage(request, page);
//	
//		return mapping.findForward("selectList_single");
//	}
	
	/**
	 * 系统产品目录页面
	 */
	private ActionForward totreepage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductcategoryForm theForm = (ProductcategoryForm) form;
		Long localeid = theForm.getLocaleid();
		if(localeid==null){
			localeid = getLoginInfo(true).getUseLocaleid(request);
			theForm.setLocaleid(localeid);
		}
		int urlType = theForm.getUrlType();
		String syscode = theForm.getSyscode();
		if(syscode==null||syscode.trim().length()<1)
			syscode = SYSCODE;
		Productcategory vo = theForm.getVo();
		Long pid = vo.getPid();
	
		ProductcategoryLogic logic = BOFactory_Platform.getProductcategoryLogic();
		if(pid==null){
			vo = logic.getSystemTopCategory(syscode, localeid);
		}else {
			vo = BOFactory_Platform.getProductcategoryDao().selectByLogicPK(pid, localeid);
		}
		
		theForm.setVo(vo);
	    
		String url = "catetreepage";
		if(urlType==2){
			url = "seltreepage";
		}else {
			int edittype = theForm.getEditType();
			if(CommonConstant.editType_view==edittype){
				url = "catetreepage_mag";
			}
		}
		return mapping.findForward(url);
	}
	
	/** 
	 * Method geneTreeXml:generate the tree xml file,used to show the tree
	 */
	private ActionForward geneTreeXml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductcategoryForm theForm = (ProductcategoryForm) form;
		Long localeid = theForm.getLocaleid();
		if(localeid==null){
			localeid = getLoginInfo(true).getUseLocaleid(request);
		}
		ProductcategoryQuery queryVO = theForm.getQueryVO();
		String toUrl = "treeXml";
		
		int inadminuse = theForm.getInadminuse();
		boolean isforadminuse = false;
		if(inadminuse==1){
			isforadminuse = true;
		}

		//String url = "/platform/productcategory/productcategory.do?method=geneTreeXml&amp;viewQueryVO.pid=";
		List list = BOFactory_Platform.getProductcategoryDao().getChildNodes(queryVO.getPid(),theForm.getSyscode(),localeid,isforadminuse);
		String treeXml = TreeUtil.getInstance().geneTreeXml(list);
		treeXml = StringUtil.filterSpecCharForXML(treeXml);
		//String treeXml = logic.createTreeXml(viewQueryVO.getPid(), "showRightFromTree", url, null,localeid);
        request.setAttribute(KeyInMemoryConstant.treeXmlKey, treeXml);		
		
		return mapping.findForward(toUrl);
	}
	
	/** 
	 * Method save
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductcategoryForm theForm = (ProductcategoryForm) form;
        Productcategory vo = theForm.getVo();

		ProductcategoryLogic logic = BOFactory_Platform.getProductcategoryLogic();
		logic.save(vo);

		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProductcategoryForm theForm = (ProductcategoryForm) form;
		ProductcategoryQuery queryVO = theForm.getQueryVO();
		Long categoryid = queryVO.getCategoryid();
		Long localeid = queryVO.getLocaleid();
		if(localeid==null||localeid.longValue()==0)
		   localeid = getLoginInfo().getLocaleid();
			
		AssertUtil.assertNotNull(categoryid, null);

    	theForm.setLocaleid(localeid);
    	Productcategory vo = BOFactory_Platform.getProductcategoryDao().selectByLogicPK(categoryid, localeid);
    	theForm.setVo(vo);

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("editPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add,从列表页面点击新增按钮，在此转向新增页面，处理某些值的初始化
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Locale locale = getLoginInfo().getLocale();
		Long localeid = getLoginInfo().getLocaleid();
		ProductcategoryForm theForm = (ProductcategoryForm) form;

		Productcategory vo = theForm.getVo();
	    // 初始化queryVO中的值parentName和categoryLevel     
	    ProductcategoryActionLogic.initAddPage(vo,locale,localeid);
	    // 设置默认的localeid
	    vo.setLocaleid(localeid);
	    // 放入语言下拉列表
	    I18nLogic logic = commonTool.biz.logicImpl.BOFactory.getI18nLogic();
	    List list = logic.getLabelList(locale);
	    theForm.setLocalesList(list);

		return mapping.findForward("addPage");
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
		ProductcategoryForm theForm = (ProductcategoryForm) form;
		Long pk = theForm.getVo().getCategoryid();
		int rows = 0;
		String messCode = KeyInMemoryConstant.deleteSuccess;
		try {
			rows = BOFactory_Platform.getProductcategoryDao().deleteByPK(pk);
		} catch (Exception e) {
			if(e instanceof HasChildException)
				messCode = "commonWeb.java.commonaction.errors.delete.hasChild";
			else
				throw e;
		}
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}