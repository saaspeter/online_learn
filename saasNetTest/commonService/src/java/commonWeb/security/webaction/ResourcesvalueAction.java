
package commonWeb.security.webaction;

import java.util.List;

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
import commonWeb.security.dao.ResourcesvalueDao;
import commonWeb.security.dto.ResourcesvalueQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Resourcesvalue;
import commonWeb.security.webform.ResourcesvalueForm;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ResourcesvalueAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ResourcesvalueAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ResourcesvalueForm theForm = (ResourcesvalueForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listResourcesvalue".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveResourcesvalue".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editResourcesvalue".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewResourcesvalue".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		ResourcesvalueQuery queryVO = theForm.getQueryVO();

		ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		Resourcesvalue vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getRescvalueid()==null||vo.getRescvalueid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
		ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
		dao.save(vo);
		
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1", "BizKey");
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		ResourcesvalueQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getRescvalueid();
		
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
		Resourcesvalue vo = dao.selectByPK(pk);
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		Resourcesvalue vo = theForm.getVo();
		ResourcesvalueQuery queryVO = theForm.getQueryVO();
		queryVO.setId(vo.getId());
		String removeStr = null;
		if(queryVO.getId()!=null){
			ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
			List list = dao.selectByVO(queryVO);
			if(list!=null&&list.size()>0){
		      removeStr = "";
		      Resourcesvalue voTemp = null;
			  for(int i=0;i<list.size();i++){
				  voTemp = (Resourcesvalue)list.get(i);
				  if(voTemp!=null)
				     removeStr += voTemp.getLocaleid()+",";
			  }
			}
		}
		theForm.setRemoveStr(removeStr);
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
		ResourcesvalueForm theForm = (ResourcesvalueForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		ResourcesvalueDao dao = BOFactory.getResourcesvalueDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
