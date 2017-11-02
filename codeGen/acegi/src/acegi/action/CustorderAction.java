
package acegiWeb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import acegi.dao.CustorderDao;
import acegi.dto.CustorderQuery;
import acegi.logicImpl.BOFactory;
import acegi.vo.Custorder;
import acegiWeb.base.BaseAction;
import acegiWeb.base.WebConstant;
import acegiWeb.base.KeyInMemoryConstant;
import acegiWeb.form.CustorderForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class CustorderAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CustorderAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ResourcesForm theForm = (ResourcesForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listCustorder".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveCustorder".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editCustorder".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewCustorder".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addCustorder".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteCustorder".equals(myaction)) {
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
		CustorderForm theForm = (CustorderForm) form;
		CustorderQuery queryVO = theForm.getQueryVO();
		try {
			CustorderDao dao = BOFactory.getCustorderDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize());
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:CustorderAction Method:list", e);
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
		CustorderForm theForm = (CustorderForm) form;
		Custorder vo = theForm.getVo();
		int actionType = KeyInMemoryConstant.ActionTypeEditInt;
        if(vo!=null&&(vo.getOrderid()==null||vo.getOrderid()==0))
        	actionType = KeyInMemoryConstant.ActionTypeAddInt;
        try {
			CustorderDao dao = BOFactory.getCustorderDao();
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:CustorderAction Method:save", e);
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
		CustorderForm theForm = (CustorderForm) form;
		CustorderQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getOrderid();
		try {
			if(pk==null){
			   log.info("----error:in Class:CustorderAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			CustorderDao dao = BOFactory.getCustorderDao();
			Custorder vo = dao.selectByPK(pk);
			if(vo==null){
				log.info("----error:in Class:CustorderAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:CustorderAction Method:editPage", e);
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
		CustorderForm theForm = (CustorderForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			CustorderDao dao = BOFactory.getCustorderDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:CustorderAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request, theForm, KeyInMemoryConstant.ActionTypeDeleteInt, rows, "BaseKey");
		return mapping.findForward("toUrl");
	}
	
}
