
package commonWeb.security.webaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;
import commonWeb.base.BaseAction;
import commonWeb.base.KeyInMemoryConstant;
import commonWeb.security.dao.MenusvalueDao;
import commonWeb.security.dto.MenusvalueQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Menusvalue;
import commonWeb.security.webform.MenusvalueForm;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class MenusvalueAction extends BaseAction {
	
	static Logger log = Logger.getLogger(MenusvalueAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		MenusvalueForm theForm = (MenusvalueForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listMenusvalue".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveMenusvalue".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editMenusvalue".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewMenusvalue".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addMenusvalue".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteMenusvalue".equals(myaction)) {
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
		MenusvalueForm theForm = (MenusvalueForm) form;
		MenusvalueQuery queryVO = theForm.getQueryVO();
		
		MenusvalueDao dao = BOFactory.getMenusvalueDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(), getTotalNumber(request));
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
		MenusvalueForm theForm = (MenusvalueForm) form;
		Menusvalue vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(vo!=null&&(vo.getMenuvalueid()==null))
        	messCode = KeyInMemoryConstant.AddSuccess;
 
		MenusvalueDao dao = BOFactory.getMenusvalueDao();
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
		MenusvalueForm theForm = (MenusvalueForm) form;
		MenusvalueQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getMenuvalueid();
		
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		MenusvalueDao dao = BOFactory.getMenusvalueDao();
		Menusvalue vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);

		if(theForm.getEditType()==CommonConstant.editType_edit)
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
		MenusvalueForm theForm = (MenusvalueForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		MenusvalueDao dao = BOFactory.getMenusvalueDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}