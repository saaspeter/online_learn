
package netTestWeb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.base.Page;
import platform.user.dao.AccountvalidatetaskDao;
import platform.user.dto.AccountvalidatetaskQuery;
import platform.bean.BOFactory;
import platform.user.vo.Accountvalidatetask;
import platformWeb.base.BaseAction;
import platformWeb.base.WebConstant;
import platformWeb.base.KeyInMemoryConstant;
import commonTool.constant.CommonConstant;
import netTestWeb.form.AccountvalidatetaskForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class AccountvalidatetaskAction extends BaseAction {
	
	static Logger log = Logger.getLogger(AccountvalidatetaskAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listAccountvalidatetask".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveAccountvalidatetask".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editAccountvalidatetask".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewAccountvalidatetask".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addAccountvalidatetask".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteAccountvalidatetask".equals(myaction)) {
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
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		AccountvalidatetaskQuery queryVO = theForm.getQueryVO();
		
		AccountvalidatetaskDao dao = BOFactory.getAccountvalidatetaskDao();
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
		
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		Accountvalidatetask vo = theForm.getVo();
        if(vo!=null&&(vo.getId()==null||vo.getId()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
        AccountvalidatetaskDao dao = BOFactory.getAccountvalidatetaskDao();
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
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		AccountvalidatetaskQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getId();
		
		AccountvalidatetaskDao dao = BOFactory.getAccountvalidatetaskDao();
		Accountvalidatetask vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:AccountvalidatetaskAction;--method:editPage;");
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
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
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
		AccountvalidatetaskForm theForm = (AccountvalidatetaskForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			AccountvalidatetaskDao dao = BOFactory.getAccountvalidatetaskDao();
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
