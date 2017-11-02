
package netTestWeb.category.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.category.form.SysproductcateForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.SysproductcateDao;
import platform.dto.SysproductcateQuery;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Sysproductcate;

/** 
 */
public class SysproductcateAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysproductcateAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listSysproductcate".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveSysproductcate".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("deleteSysproductcate".equals(myaction)) {
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
		SysproductcateForm theForm = (SysproductcateForm) form;
		SysproductcateQuery queryVO = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();
		theForm.setLocale(locale);
		
		SysproductcateDao dao = BOFactory_Platform.getSysproductcateDao();
		List list = dao.selectByVO(queryVO);
		theForm.setShowList(list);
		// 设置已经选择过的系统的过滤字符串
		if(list!=null){
		   Sysproductcate voTemp = null;
		   String filterStrs = "";
		   for(int i=0;i<list.size();i++){
			   voTemp = (Sysproductcate)list.get(i);
			   if(voTemp!=null&&voTemp.getSyscode()!=null&&voTemp.getSyscode().trim().length()>0)
				   filterStrs += voTemp.getSyscode()+",";
		   }
		   theForm.getVo().setSyscodesStr(filterStrs);
		}
		
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysproductcateForm theForm = (SysproductcateForm) form;
		Sysproductcate vo = theForm.getVo();
		long num = 0l;

		SysproductcateDao dao = BOFactory_Platform.getSysproductcateDao();
		dao.insert(vo.getCategoryid(), vo.getSyscodesStr());

		// set messag page parameters.
		String messCode = KeyInMemoryConstant.AddSuccess;
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return this.list(mapping, theForm, request, response);
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
		SysproductcateForm theForm = (SysproductcateForm) form;
		Sysproductcate vo = theForm.getQueryVO();
		String[] keys = theForm.getKeys();
		int rows = 0;

		SysproductcateDao dao = BOFactory_Platform.getSysproductcateDao();
		rows = dao.deleteBatchByPK(vo.getCategoryid(), keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		theForm.getQueryVO().setCategoryid(vo.getCategoryid());
		return this.list(mapping, theForm, request, response);
	}
	
}
