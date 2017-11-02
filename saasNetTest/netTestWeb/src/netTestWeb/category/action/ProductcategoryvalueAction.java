
package netTestWeb.category.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.category.form.ProductcategoryvalueForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.dao.ProductcategoryvalueDao;
import platform.dto.ProductcategoryvalueQuery;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Productcategoryvalue;

import commonTool.base.Page;
import commonTool.util.AssertUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ProductcategoryvalueAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ProductcategoryvalueAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ProductcategoryvalueForm theForm = (ProductcategoryvalueForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listProductcategoryvalue".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveProductcategoryvalue".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editProductcategoryvalue".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addProductcategoryvalue".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteProductcategoryvalue".equals(myaction)) {
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
		ProductcategoryvalueForm theForm = (ProductcategoryvalueForm) form;
		ProductcategoryvalueQuery queryVO = theForm.getQueryVO();

		ProductcategoryvalueDao dao = BOFactory_Platform.getProductcategoryvalueDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
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
		ProductcategoryvalueForm theForm = (ProductcategoryvalueForm) form;
		Productcategoryvalue vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getCategoryvalueid()==null||vo.getCategoryvalueid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;

		ProductcategoryvalueDao dao = BOFactory_Platform.getProductcategoryvalueDao();
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
		ProductcategoryvalueForm theForm = (ProductcategoryvalueForm) form;
		theForm.setEditType(WebConstant.editType_edit);
		ProductcategoryvalueQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getCategoryvalueid();

        AssertUtil.assertNotNull(pk, null);
		ProductcategoryvalueDao dao = BOFactory_Platform.getProductcategoryvalueDao();
		Productcategoryvalue vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
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
		ProductcategoryvalueForm theForm = (ProductcategoryvalueForm) form;
		theForm.setEditType(WebConstant.editType_add);
		Productcategoryvalue vo = theForm.getVo();
		if(vo.getCategoryid()!=null){
			ProductcategoryvalueDao dao = BOFactory_Platform.getProductcategoryvalueDao();
			String removeStr = dao.qrySelectedLocaleStr(vo.getCategoryid());
			theForm.setSelectedLocaleStr(removeStr);
		}
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
		ProductcategoryvalueForm theForm = (ProductcategoryvalueForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		ProductcategoryvalueDao dao = BOFactory_Platform.getProductcategoryvalueDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		
		return this.list(mapping, theForm, request, response);
	}
	
}
