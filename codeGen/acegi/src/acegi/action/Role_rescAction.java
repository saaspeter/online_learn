
package acegiWeb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import acegi.dao.Role_rescDao;
import acegi.dto.Role_rescQuery;
import acegi.logicImpl.BOFactory;
import acegi.vo.Role_resc;
import acegiWeb.base.BaseAction;
import acegiWeb.base.WebConstant;
import acegiWeb.base.KeyInMemoryConstant;
import acegiWeb.form.Role_rescForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class Role_rescAction extends BaseAction {
	
	static Logger log = Logger.getLogger(Role_rescAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ResourcesForm theForm = (ResourcesForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listRole_resc".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveRole_resc".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editRole_resc".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewRole_resc".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addRole_resc".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteRole_resc".equals(myaction)) {
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Role_rescForm theForm = (Role_rescForm) form;
		Role_rescQuery queryVO = theForm.getQueryVO();
		try {
			Role_rescDao dao = BOFactory.getRole_rescDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:Role_rescAction Method:list", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
		
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Role_rescForm theForm = (Role_rescForm) form;
		Role_resc vo = theForm.getVo();
		int actionType = KeyInMemoryConstant.ActionTypeEditInt;
        if(vo!=null&&(vo.get{[#pkInMethod#]}()==null||vo.get{[#pkInMethod#]}()==0))
        	actionType = KeyInMemoryConstant.ActionTypeAddInt;
        try {
			Role_rescDao dao = BOFactory.getRole_rescDao();
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:Role_rescAction Method:save", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.save");
		}
		// set messag page parameters.
		super.setMessagePage(request,theForm, actionType, null, "BaseKey");
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Role_rescForm theForm = (Role_rescForm) form;
		Role_rescQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.get{[#pkInMethod#]}();
		try {
			if(pk==null){
			   log.info("----error:in Class:Role_rescAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			Role_rescDao dao = BOFactory.getRole_rescDao();
			Role_resc vo = dao.selectByPK(pk);
			if(vo==null){
				log.info("----error:in Class:Role_rescAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:Role_rescAction Method:editPage", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.editPage");
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
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Role_rescForm theForm = (Role_rescForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			Role_rescDao dao = BOFactory.getRole_rescDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:Role_rescAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request, theForm, KeyInMemoryConstant.ActionTypeDeleteInt, rows, "BaseKey");
		return mapping.findForward("toUrl");
	}
	
}
