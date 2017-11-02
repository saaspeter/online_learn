
package netTestWeb.exercise.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.exercise.dao.UserexeranswerDao;
import netTest.exercise.dto.UserexeranswerQuery;
import netTest.exercise.vo.Userexeranswer;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.exercise.form.UserexeranswerForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.exception.NoSuchRecordException;


public class UserexeranswerAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserexeranswerAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		UserexeranswerForm theForm = (UserexeranswerForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("saveUserexeranswer".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editUserexeranswer".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewUserexeranswer".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addUserexeranswer".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteUserexeranswer".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}

	
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
		
		UserexeranswerForm theForm = (UserexeranswerForm) form;
		Userexeranswer vo = theForm.getVo();
        if(vo!=null&&(vo.getUseranswerid()==null||vo.getUseranswerid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
        UserexeranswerDao dao = BOFactory.getUserexeranswerDao();
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
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		UserexeranswerForm theForm = (UserexeranswerForm) form;
		UserexeranswerQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getUseranswerid();
		
		UserexeranswerDao dao = BOFactory.getUserexeranswerDao();
		Userexeranswer vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:UserexeranswerAction;--method:editPage;");
		theForm.setVo(vo);

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
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		UserexeranswerForm theForm = (UserexeranswerForm) form;
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
		UserexeranswerForm theForm = (UserexeranswerForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			UserexeranswerDao dao = BOFactory.getUserexeranswerDao();
			rows = dao.deleteBatchByPK(keys);
			info = String.valueOf(rows);
		}catch (HasReferenceException e) {
			//TODO 在此得到异常数据，组装成ajax类型的数据返�?
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
