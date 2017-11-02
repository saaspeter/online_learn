
package commonToolWeb.biz.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.base.Page;
import commonTool.biz.dao.SystemlogDao;
import commonTool.biz.dto.SystemlogQuery;
import commonTool.bean.BOFactory;
import commonTool.biz.vo.Systemlog;
import commonToolWeb.base.BaseAction;
import commonToolWeb.base.WebConstant;
import commonToolWeb.base.KeyInMemoryConstant;
import commonTool.constant.CommonConstant;
import commonToolWeb.biz.form.SystemlogForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SystemlogAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SystemlogAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		SystemlogForm theForm = (SystemlogForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listSystemlog".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveSystemlog".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editSystemlog".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewSystemlog".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addSystemlog".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteSystemlog".equals(myaction)) {
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
		SystemlogForm theForm = (SystemlogForm) form;
		SystemlogQuery queryVO = theForm.getQueryVO();
		
		SystemlogDao dao = BOFactory.getSystemlogDao();
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
		
		SystemlogForm theForm = (SystemlogForm) form;
		Systemlog vo = theForm.getVo();
        if(vo!=null&&(vo.getLogid()==null||vo.getLogid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
        SystemlogDao dao = BOFactory.getSystemlogDao();
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
		SystemlogForm theForm = (SystemlogForm) form;
		SystemlogQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getLogid();
		
		SystemlogDao dao = BOFactory.getSystemlogDao();
		Systemlog vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:SystemlogAction;--method:editPage;");
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
		SystemlogForm theForm = (SystemlogForm) form;
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
		SystemlogForm theForm = (SystemlogForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			SystemlogDao dao = BOFactory.getSystemlogDao();
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
