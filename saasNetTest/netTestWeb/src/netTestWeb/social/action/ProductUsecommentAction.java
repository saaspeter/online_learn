package netTestWeb.social.action;


import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.exception.ExceptionConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.dto.UserprodstatuslogQuery;
import netTest.product.vo.Userproduct;
import netTest.util.ResourceBundleUtil;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.BOFactory_Platform;
import platform.social.vo.Usecomment;
import commonWeb.security.authentication.UserinfoSession;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonWeb.base.BaseAction;
import commonWeb.social.form.UsecommentForm;

public class ProductUsecommentAction extends BaseAction {
	
	private static final String ParamKey_CourseCanComment_Interval = "CourseCanComment.Interval";
	
	static Logger log = Logger.getLogger(ProductUsecommentAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("editusecomment".equals(myaction)) {
			myforward = edit(mapping, actionform, request,response);
		} else if ("saveprodusecomment".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}

	private ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserinfoSession loginfo = getLoginInfo();
		Long userid = loginfo.getUserid();
		Locale locale = loginfo.getLocale();
		
		UsecommentForm theForm = (UsecommentForm) form;
		Usecomment vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getObjectid(), null);
		AssertUtil.assertNotEmpty(vo.getObjecttype(), null);
		
		// 必须是该产品的使用用户，并且使用超过1天.或者曾经学习过这门可能。才可以评论产品
		boolean cancomment = cancomment(userid, vo.getObjectid());
		if(cancomment){
			vo = BOFactory_Platform.getUsecommentDao().selectByUser(userid, vo.getObjectid(), vo.getObjecttype());
			if(vo!=null){
			   theForm.setVo(vo);
			}
		}else {
			request.setAttribute("CheckResult", "0");
			String errormess = ResourceBundleUtil.getInstance().getValue(ExceptionConstant.Error_ProdUseComment_CannotComment, locale);
			request.setAttribute("ErrorMessage", errormess);
		}
		
		return mapping.findForward("editpage");
	}
	
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserinfoSession loginfo = getLoginInfo();
		Long userid = loginfo.getUserid();
		Locale locale = loginfo.getLocale();
		
		UsecommentForm theForm = (UsecommentForm) form;
		Usecomment vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getObjectid(), null);
		AssertUtil.assertNotEmpty(vo.getObjecttype(), null);
		AssertUtil.assertNotNull(vo.getGrade(), null);
		
		Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
		
		vo.setUserid(userid);
		vo.setUpdatedate(curDate);
		
		String result = "1";
        String message = "save successfully";
       	try{
       	    // 必须是该产品的使用用户，并且使用超过1天.或者曾经学习过这门可能。才可以评论产品
    		boolean cancomment = cancomment(userid, vo.getObjectid());
    		if(!cancomment){
    			throw new LogicException(ExceptionConstant.Error_ProdUseComment_CannotComment);
    		}
       		
      		vo = BOFactory_Platform.getUsecommentDao().save(vo);
   			message = vo.getCommentid().toString();
   		}catch (Exception e) {
   			result = "2";
   			log.error("--error when save product use comment", e);
   			
   			String errorcode = ExceptionConstant.Error_System;
   			if(e instanceof LogicException){
   				errorcode = e.getMessage();
   			}
   			message = ResourceBundleUtil.getInstance().getValue(errorcode, locale);
   		}
        
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
	private boolean cancomment(Long userid, Long productid){
		boolean cancomment = false;
		Date curDate = DateUtil.getInstance().getNowtime_GLNZ();
		int commentInterval = 1;
		String commentIntervalStr = SysparamConstantNettest.getProperty(ParamKey_CourseCanComment_Interval);
		if(commentIntervalStr!=null && !"".equals(commentIntervalStr)){
			commentInterval = Integer.parseInt(commentIntervalStr);
		}
		Userproduct userprodvo = BOFactory.getUserproductDao().selectByLogicPk(userid, productid);
		if(userprodvo!=null && curDate.compareTo(DateUtil.dateAddDays(userprodvo.getStartdate(),commentInterval))>0){
			cancomment = true;
		}else {
			UserprodstatuslogQuery logqueryvo = new UserprodstatuslogQuery();
			logqueryvo.setUserid(userid);
			logqueryvo.setProductbaseid(productid);
			logqueryvo.setAfstatus(UserproductConstant.Status_active);
			List loglist = BOFactory.getUserprodstatuslogDao().selectByVO(logqueryvo);
		    if(loglist!=null && loglist.size()>0){
		    	cancomment = true;
		    }
		}
		return cancomment;
	}
	
	
}
