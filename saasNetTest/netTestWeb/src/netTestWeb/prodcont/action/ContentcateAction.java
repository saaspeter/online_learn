
package netTestWeb.prodcont.action;

import commonWeb.security.authentication.UserinfoSession;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exception.ExceptionConstant;
import netTest.prodcont.dao.ContentcateDao;
import netTest.prodcont.dto.ContentcateQuery;
import netTest.prodcont.logic.ContentcateLogic;
import netTest.prodcont.vo.Contentcate;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.prodcont.form.ContentcateForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.constant.CommonConstant;
import commonTool.exception.HasChildException;
import commonTool.exception.HasReferenceException;
import commonTool.util.AssertUtil;
import commonTool.util.StringUtil;
import commonTool.util.TreeUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ContentcateAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ContentcateAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ContentcateForm theForm = (ContentcateForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listContentcatemag".equals(myaction)) {
			theForm.setListType("1");
			myforward = list(mapping, actionform, request,response);
		} else if ("listContentcatesel".equals(myaction)) {
			theForm.setListType("2");
			myforward = list(mapping, actionform, request,response);
		} else if ("genecontcatetree".equals(myaction)) {
			myforward = geneTreeXml(mapping, actionform, request,response);
		} else if ("saveContentcate".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editContentcate".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} 
//		else if ("viewContentcate".equals(myaction)) {
//			theForm.setEditType(WebConstant.editType_view);
//			myforward = editPage(mapping, actionform, request,response);
//		} 
		else if ("addContentcate".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteContentcate".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("toContcateTree".equals(myaction)) {
			theForm.setListType("1");
			myforward = toContcateTree(mapping, actionform, request,response);
		} else if ("selectContcateTree".equals(myaction)) {
			theForm.setListType("2");
			myforward = toContcateTree(mapping, actionform, request,response);
		} else if ("onlyviewprodcate".equals(myaction)) {
			myforward = onlyviewprodcate(mapping, actionform, request,response);
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
		ContentcateForm theForm = (ContentcateForm) form;
		ContentcateQuery queryVO = theForm.getQueryVO();
		if(queryVO.getPid()==null){
			queryVO.setPid(CommonConstant.TreeTopnodePid);
		}
        AssertUtil.assertNotNull(queryVO.getProductbaseid(), null);
		ContentcateDao dao = BOFactory.getContentcateDao();
		List list = dao.selectByVO(queryVO);
		theForm.setDatalist(list);
		
		return mapping.findForward("list");
	}
	
	// 用户免费查看课程目录
	private ActionForward onlyviewprodcate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContentcateForm theForm = (ContentcateForm) form;
		Long productid = theForm.getProductbaseid();
		AssertUtil.assertNotNull(productid, null);
		
		String retPage = "view";
		UserinfoSession logininfo = getLoginInfo(true);
		if(!logininfo.isAnonymous()){
			String prodMagStr = getLoginInfo().getProdIdMagStr();
			boolean include = StringUtil.includeStr(prodMagStr, productid.toString(), ",");
			if(include){
				theForm.setIsadmin(true);
			}
		}
		// 选择了课程才查询
		if(productid!=null){
			// 查询本课程的目录树
			ContentcateLogic catelogic = BOFactory.getContentcateLogic();
			List<Contentcate> catelist = catelogic.getProdCatetory(productid, true, true);
			// 查询仅属于课程本身，但不属于任何目录的学习内容和练习，并且虚拟出一个对象来
			Contentcate topcatevo = catelogic.getCateWithLearncont(catelist, CommonConstant.TreeTopnodePid, productid);
			
			theForm.setDatalist(catelist);
			theForm.setTopcatevo(topcatevo);
		}
		
		return mapping.findForward(retPage);
	}
	
	/** 
	 * Method geneTreeXml:generate the tree xml file,used to show the tree
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward geneTreeXml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContentcateForm theForm = (ContentcateForm) form;
		ContentcateQuery queryVO = theForm.getQueryVO();

		ContentcateDao dao = BOFactory.getContentcateDao();
		List list = dao.getChildNodes(queryVO.getProductbaseid(),queryVO.getPid());
		String treeXml = TreeUtil.getInstance().geneTreeXml(list);
        request.setAttribute(KeyInMemoryConstant.treeXmlKey, treeXml);
		
		return mapping.findForward("treeXml");
	}
	
	/** 
	 * Method save
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContentcateForm theForm = (ContentcateForm) form;
		Contentcate vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getContentcateid()==null||vo.getContentcateid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        if(vo.getShopid()==null){
        	vo.setShopid(getLoginInfo().getShopid());
        }
        ContentcateDao dao = BOFactory.getContentcateDao();
		dao.save(vo);
		// set messag page parameters.
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
		ContentcateForm theForm = (ContentcateForm) form;
		theForm.setEditType(WebConstant.editType_edit);
		ContentcateQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getContentcateid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		ContentcateDao dao = BOFactory.getContentcateDao();
		Contentcate vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);
		if(vo.getPid()!=null) {
			Contentcate parentVO = dao.selectByPK(vo.getPid());
			if(parentVO!=null){
			   theForm.setParentcateName(parentVO.getContentcatename());
			}
		}
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContentcateForm theForm = (ContentcateForm) form;
		Contentcate vo = theForm.getVo();
		if(vo.getPid()!=null){
			ContentcateDao dao = BOFactory.getContentcateDao();
			Contentcate parentVO = dao.selectByPK(vo.getPid());
			if(parentVO!=null){
			   theForm.getVo().setPath(parentVO.getPath());
			   theForm.setParentcateName(parentVO.getContentcatename());
			}
		}
		
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContentcateForm theForm = (ContentcateForm) form;
		Long pk = theForm.getVo().getContentcateid();
		Long productid = theForm.getProductbaseid();
		AssertUtil.assertNotNull(productid, null);
		AssertUtil.assertNotNull(pk, null);
		String messKey = "";
		String result = String.valueOf(CommonConstant.success);
		try {
			ContentcateLogic logic = BOFactory.getContentcateLogic();
			logic.deleteByPK(productid, pk);
			messKey = KeyInMemoryConstant.deleteSuccessCommon;
		} catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			log.error("oops, got an exception: ", e); 
			if(e instanceof HasChildException)
				messKey = "commonWeb.java.commonaction.errors.delete.hasChild";
			else if(e instanceof HasReferenceException)
				messKey = "commonWeb.java.commonaction.errors.RecordBeenReference";
			else
				messKey = ExceptionConstant.Error_System;
			
		}
		
		String info = getResources(request, "BizKey").getMessage(messKey);
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
	private ActionForward toContcateTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ContentcateForm theForm = (ContentcateForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		String listType = theForm.getListType();
		String toUrl = "contcateTree";
		if("2".equals(listType))
			toUrl = "selectTree";
		ContentcateQuery queryVO = theForm.getQueryVO();
		Productbase vo = BOFactory.getProductLogic().selectVO(queryVO.getProductbaseid(), localeid);
		theForm.setProductName(vo.getProductname());
		return mapping.findForward(toUrl);
	}
	
}
