
package netTestWeb.platform.systemset.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.platform.systemset.form.SubsysmanageForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import commonTool.constant.CommonConstant;
import commonWeb.base.BaseAction;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SubsysmanageAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SubsysmanageAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) 
	{

		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listSubsys".equals(myaction)) {
			myforward = listSubsys(mapping, actionform, request,response);
		} 
		return myforward;
	}
	
	/** 
	 * Method listSubsys:展现所有子系统，过滤掉通用系统的选择
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listSubsys(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		SubsysmanageForm theForm = (SubsysmanageForm) form;
        Locale locale = getLoginInfo().getLocale();
        String filterStrs = theForm.getFilterStrs();
        
		try {
			List list = CommonConstant.qrySystemFilterLabel(locale,filterStrs);
			theForm.setSubsysList(list);
		} catch (Exception e) {
			log.error("----error in Class:SubsysmanageAction Method:list", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
	    return mapping.findForward("listSubsys");
	}
		
}
