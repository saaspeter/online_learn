package netTestWeb.exercise.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTest.bean.BOFactory;
import netTest.exercise.dao.ExerquestypeDao;
import netTest.exercise.dto.ExerquestypeQuery;
import netTest.exercise.logic.ExerquestypeLogic;
import netTest.exercise.vo.Exerquestype;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.exercise.form.ExerquestypeForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.constant.CommonConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.HasReferenceException;
import commonTool.exception.NoSuchRecordException;


public class ExerquestypeAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ExerquestypeAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ExerquestypeForm theForm = (ExerquestypeForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("saveExerquestype".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editExerquestype".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addExerquestype".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteExerquestype".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
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
//		ExerquestypeForm theForm = (ExerquestypeForm) form;
//		ExerquestypeQuery queryVO = theForm.getQueryVO();
//		
//		ExerquestypeDao dao = BOFactory.getExerquestypeDao();
//		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
//		this.setPage(request, page);
//		
//		return mapping.findForward("list");
//	}
	
	/** 
	 * Method save
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
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
		
		ExerquestypeForm theForm = (ExerquestypeForm) form;
		Exerquestype vo = theForm.getVo();
        if(vo!=null&&(vo.getPatternid()==null||vo.getPatternid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
        ExerquestypeLogic logic = BOFactory.getExerquestypeLogic();
        logic.save(vo);
        // set messag page parameters.
		request.setAttribute("pageAction", "closeDiv");
		request.setAttribute("url", "editExercise.do?vo.exerid="+vo.getExerid()
				             +"&questypeUse="+vo.getQuestype());
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ExerquestypeForm theForm = (ExerquestypeForm) form;
		ExerquestypeQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getPatternid();
		
		ExerquestypeDao dao = BOFactory.getExerquestypeDao();
		Exerquestype vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:ExerquestypeAction;--method:editPage;");
		theForm.setVo(vo);

 	    return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method add
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ExerquestypeForm theForm = (ExerquestypeForm) form;
		Long exerid = theForm.getVo().getExerid();
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addEditPage");
	}
	
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
		ExerquestypeForm theForm = (ExerquestypeForm) form;
		Long patterid = theForm.getVo().getPatternid();
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			ExerquestypeLogic logic = BOFactory.getExerquestypeLogic();
			logic.delete(patterid);
		}catch (HasReferenceException e) {
			result = String.valueOf(CommonConstant.fail);
			info = getResources(request, "BizKey").getMessage("exception.ExerquestypeAction.exerquestype.cannotdelete.hasRef");
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = getResources(request, "BizKey").getMessage(ExceptionConstantBase.Error_System);
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
