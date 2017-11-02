package netTestWeb.exercise.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exception.ExceptionConstant;
import netTest.exercise.logic.ExerquesLogic;
import netTest.exercise.vo.Exerques;
import netTestWeb.base.BaseAction;
import netTestWeb.exercise.form.ExerquesForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;


public class ExerquesAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ExerquesAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ExerquesForm theForm = (ExerquesForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} 
//		else if ("saveExerques".equals(myaction)) {
//			myforward = save(mapping, actionform, request,response);
//		}
		else if ("deleteExerques".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("addExerQues".equals(myaction)) {
			myforward = addExerQues(mapping, actionform, request,response);
		} else if ("changeQues".equals(myaction)) {
			myforward = changeQues(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
//	/** 
//	 * Method list
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return ActionForward
//	 */
//	public ActionForward list(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception 
//	{
//		ExerquesForm theForm = (ExerquesForm) form;
//		ExerquesQuery queryVO = theForm.getQueryVO();
//		
//		ExerquesDao dao = BOFactory.getExerquesDao();
//		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
//		this.setPage(request, page);
//		
//		return mapping.findForward("list");
//	}
	
    /**
     * 新增试卷题目
     */
	public ActionForward addExerQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		ExerquesForm theForm = (ExerquesForm) form;
		Exerques vo = theForm.getVo();
		vo.setShopid(shopid);
		String quesidStr = theForm.getQuesidStr();
		String info = "";
		String result = String.valueOf(CommonConstant.success);
		
		try {
			ExerquesLogic logic = BOFactory.getExerquesLogic();
			int rows = logic.addSelExerques(vo, quesidStr);
			info = String.valueOf(rows);
		} catch (Exception e) {
			info = ExceptionConstant.Error_System;
			log.error(e);
		}
		
		this.writeAjaxRtn(result, info, null, response);
		return null;
//		theForm.classSetBackUrlEncode("editExercise.do?vo.exerid="+vo.getExerid()+"&questypeUse="+vo.getQuestype());
//		this.setMessagePage(request, theForm, null, null, null);
//		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method 更换试卷题目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward changeQues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		ExerquesForm theForm = (ExerquesForm) form;
		Exerques vo = theForm.getVo();
		String info = "1";
		String result = String.valueOf(CommonConstant.success);
		vo.setShopid(shopid);
		String quesidStr = theForm.getQuesidStr();
		Long warequesid = new Long(quesidStr.trim());
		try {
			ExerquesLogic logic = BOFactory.getExerquesLogic();
			logic.changQues(vo, warequesid);
		} catch (Exception e) {
			info = ExceptionConstant.Error_System;
			log.error(e);
		}
		
		this.writeAjaxRtn(result, info, null, response);
		return null;
		
		//theForm.classSetBackUrlEncode("editExercise.do?vo.exerid="+vo.getExerid()+"&questypeUse="+vo.getQuestype());
		//this.setMessagePage(request, theForm, null, null, null);
		//return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method save
	 */
//	public ActionForward save(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		String messCode = KeyInMemoryConstant.modifySuccess;
//		if(!isTokenValid(request)){
//			saveToken(request);
//			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
//		}else{
//			resetToken(request);
//		}
//		
//		ExerquesForm theForm = (ExerquesForm) form;
//		Exerques vo = theForm.getVo();
//        if(vo!=null&&(vo.getExerid()==null||vo.getExerid()==0))
//        	messCode = KeyInMemoryConstant.AddSuccess;
//        
//        ExerquesDao dao = BOFactory.getExerquesDao();
//		dao.save(vo);
//		// set messag page parameters.
//		super.setMessagePage(request,theForm, messCode, "1","BizKey");
//		return mapping.findForward("toUrl");
//	}
	
//	/** 
//	 * Method edit
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return ActionForward
//	 */
//	public ActionForward editPage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		saveToken(request);
//		ExerquesForm theForm = (ExerquesForm) form;
//		ExerquesQuery queryVO = theForm.getQueryVO();
//		Long pk = queryVO.getExerid();
//		
//		ExerquesDao dao = BOFactory.getExerquesDao();
//		Exerques vo = dao.selectByPK(pk);
//		if(vo==null)
//		   throw new NoSuchRecordException("--class:ExerquesAction;--method:editPage;");
//		theForm.setVo(vo);
//
//		if(theForm.getEditType()==WebConstant.editType_edit)
//		   return mapping.findForward("addEditPage");
//		else
//		   return mapping.findForward("viewPage");
//	}
	
//	/** 
//	 * Method add
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return ActionForward
//	 */
//	public ActionForward addPage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		saveToken(request);
//		ExerquesForm theForm = (ExerquesForm) form;
//		theForm.setEditType(WebConstant.editType_add);
//		return mapping.findForward("addEditPage");
//	}
	
	/** 
	 * Method delete
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ExerquesForm theForm = (ExerquesForm) form;
		Exerques vo = theForm.getVo();
		String result = String.valueOf(CommonConstant.success);
		String info = "1";
		try {
			ExerquesLogic logic = BOFactory.getExerquesLogic();
			logic.delExerques(vo.getExerid(), vo.getExerquesid(), vo.getQuestype());
		}catch (Exception e) {
			log.error(e);
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof HasReferenceException){
				info = e.getMessage();
			}else {
			    info = ExceptionConstant.Error_System;
			}
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
