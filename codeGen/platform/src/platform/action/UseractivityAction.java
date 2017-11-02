
package netTestWeb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.base.Page;
import platform.user.dao.UseractivityDao;
import platform.user.dto.UseractivityQuery;
import platform.bean.BOFactory;
import platform.user.vo.Useractivity;
import platformWeb.base.BaseAction;
import platformWeb.base.WebConstant;
import platformWeb.base.KeyInMemoryConstant;
import commonTool.constant.CommonConstant;
import netTestWeb.form.UseractivityForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class UseractivityAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UseractivityAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		UseractivityForm theForm = (UseractivityForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listUseractivity".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveUseractivity".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editUseractivity".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewUseractivity".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addUseractivity".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteUseractivity".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UseractivityForm theForm = (UseractivityForm) form;
		UseractivityQuery queryVO = theForm.getQueryVO();
		
		UseractivityDao dao = BOFactory.getUseractivityDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
		this.setPage(request, page);
		
		return mapping.findForward("list");
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
		
		UseractivityForm theForm = (UseractivityForm) form;
		Useractivity vo = theForm.getVo();
        if(vo!=null&&(vo.getActivityid()==null||vo.getActivityid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
        UseractivityDao dao = BOFactory.getUseractivityDao();
		dao.save(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BaseKey");
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
		UseractivityForm theForm = (UseractivityForm) form;
		UseractivityQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getActivityid();
		
		UseractivityDao dao = BOFactory.getUseractivityDao();
		Useractivity vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:UseractivityAction;--method:editPage;");
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
		UseractivityForm theForm = (UseractivityForm) form;
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
		UseractivityForm theForm = (UseractivityForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			UseractivityDao dao = BOFactory.getUseractivityDao();
			rows = dao.deleteBatchByPK(keys);
			info = String.valueOf(rows);
		}catch (HasReferenceException e) {
			//TODO 在此得到异常数据，组装成ajax类型的数据返回
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}
		this.writeAjaxRtn(result, info, response);
		return null;
	}
	
}
