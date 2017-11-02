
package acegiWeb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import acegi.dao.ResourcesvalueDao;
import acegi.dto.ResourcesvalueQuery;
import acegi.logicImpl.BOFactory;
import acegi.vo.Resourcesvalue;
import acegiWeb.base.BaseAction;
import acegiWeb.base.WebConstant;
import acegiWeb.base.KeyInMemoryConstant;
import acegiWeb.form.ResourcesvalueForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ResourcesvalueAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ResourcesvalueAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ResourcesForm theForm = (ResourcesForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listResourcesvalue".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveResourcesvalue".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editResourcesvalue".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewResourcesvalue".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addResourcesvalue".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteResourcesvalue".equals(myaction)) {
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		ResourcesvalueQuery queryVO = theForm.getQueryVO();
		try {
			ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:ResourcesvalueAction Method:list", e);
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		Resourcesvalue vo = theForm.getVo();
		int actionType = KeyInMemoryConstant.ActionTypeEditInt;
        if(vo!=null&&(vo.getRescvalueid()==null||vo.getRescvalueid()==0))
        	actionType = KeyInMemoryConstant.ActionTypeAddInt;
        try {
			ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:ResourcesvalueAction Method:save", e);
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		ResourcesvalueQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getRescvalueid();
		try {
			if(pk==null){
			   log.info("----error:in Class:ResourcesvalueAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
			Resourcesvalue vo = dao.selectByPK(pk);
			if(vo==null){
				log.info("----error:in Class:ResourcesvalueAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:ResourcesvalueAction Method:editPage", e);
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:ResourcesvalueAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request, theForm, KeyInMemoryConstant.ActionTypeDeleteInt, rows, "BaseKey");
		return mapping.findForward("toUrl");
	}
	
}
