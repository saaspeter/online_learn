package netTestWeb.product.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exception.ExceptionConstant;
import netTest.prodcont.vo.Contentcate;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.CoursepostDao;
import netTest.product.vo.Coursepost;
import netTest.product.vo.Learnactivity;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.product.form.CoursepostForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;


public class CoursepostAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CoursepostAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		CoursepostForm theForm = (CoursepostForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listcoursepost".equals(myaction)) {
			theForm.setUrltype(1);
			myforward = list(mapping, actionform, request,response);
		} else if ("listcoursepostmag".equals(myaction)) {
			theForm.setUrltype(2);
			myforward = list(mapping, actionform, request,response);
		} else if ("savecoursepost".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editcoursepost".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} 
//		else if ("viewcoursepost".equals(myaction)) {
//			theForm.setEditType(WebConstant.editType_view);
//			myforward = editPage(mapping, actionform, request,response);
//		} 
		else if ("addcoursepost".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("delcoursepost".equals(myaction)) {
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
		CoursepostForm theForm = (CoursepostForm) form;
		int urltype = theForm.getUrltype();
		Coursepost vo = theForm.getVo();
		
		Long userid = getLoginInfo().getUserid();
		boolean loadauthority = false;
		Short produsetype = UserproductConstant.ProdUseType_userNormal;
		String url = "list";
		if(urltype==2){
			loadauthority = true;
			url = "listmag";
			produsetype = UserproductConstant.ProdUseType_operatorMag;
		}
				
		// 处理切换用户产品
		switchProduct(vo.getProductbaseid(), vo, produsetype);
		
		Page page = Page.EMPTY_PAGE;
		if(vo.getProductbaseid()!=null) {
		   CoursepostDao dao = BOFactory.getCoursepostDao();
		   page = dao.selectByVOPage(vo.getProductbaseid(), vo.getCaption(), 
				getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		   // 如果是学员进入，则加载学员第一次学习的章节
		   if(urltype==1) {
		      Learnactivity activityVO2 = BOFactory.getLearnactivityDao().selectLearnLastest(userid, vo.getProductbaseid());
			  if(activityVO2!=null && activityVO2.getContentcateid()!=null){
		    	 theForm.setContentcateid(activityVO2.getContentcateid());
		    	 Contentcate contvo = BOFactory.getContentcateDao().selectByPK(activityVO2.getContentcateid());
			     if(contvo!=null){
			    	 theForm.setContentcatename(contvo.getContentcatename());
			    	 theForm.setLastlearndate(activityVO2.getStarttime());
			     }
			  }
		   }
		}
		this.setPage(request, page);
		
		return mapping.findForward(url);
	}
	

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		CoursepostForm theForm = (CoursepostForm) form;
		Coursepost vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getProductbaseid(), null);
		AssertUtil.assertNotEmpty(vo.getContent(), null);
		
		Long userid = getLoginInfo().getUserid();
        if(vo.getId()==null||vo.getId()==0){
        	messCode = KeyInMemoryConstant.AddSuccess;
            vo.setGeneratetype(Coursepost.GenerateType_PageCreate);
            Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
            vo.setCreatetime(curDate);
            vo.setCreator(userid);
            Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(vo.getProductbaseid());
            vo.setShopid(prodvo.getShopid());
        }
        CoursepostDao dao = BOFactory.getCoursepostDao();
		dao.save(vo);
		// set message page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		theForm.setUrltype(2);
		return list(mapping, form, request, response);
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		CoursepostForm theForm = (CoursepostForm) form;
		Coursepost vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getId(), null);
		
		CoursepostDao dao = BOFactory.getCoursepostDao();
		vo = dao.selectByPK(vo.getId());
		AssertUtil.assertNotNull(vo, null);
		Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(vo.getProductbaseid());
	    if(prodvo!=null){
	    	vo.setProductname(prodvo.getProductname());
	    }
		
		theForm.setVo(vo);

	    return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method add
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		saveToken(request);
		CoursepostForm theForm = (CoursepostForm) form;
		Coursepost vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getProductbaseid(), null);
		if(vo.getProductname()==null||vo.getProductname().trim().length()<1){
			Productbase prodvo = BOFactory.getProductbaseDao().selectByPK(vo.getProductbaseid());
		    if(prodvo!=null){
		    	vo.setProductname(prodvo.getProductname());
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
		CoursepostForm theForm = (CoursepostForm) form;
		Long pk = theForm.getVo().getId();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "1";
		try{
			CoursepostDao dao = BOFactory.getCoursepostDao();
		    rows = dao.deleteByPK(pk);
		    info = String.valueOf(rows);
		}catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstant.Error_System;
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
