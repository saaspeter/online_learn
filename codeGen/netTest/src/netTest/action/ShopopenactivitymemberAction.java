
package netTestWeb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.base.Page;
import netTest.product.dao.ShopopenactivitymemberDao;
import netTest.product.dto.ShopopenactivitymemberQuery;
import netTest.bean.BOFactory;
import netTest.product.vo.Shopopenactivitymember;
import netTestWeb.base.BaseAction;
import netTestWeb.base.WebConstant;
import netTestWeb.base.KeyInMemoryConstant;
import commonTool.constant.CommonConstant;
import netTestWeb.form.ShopopenactivitymemberForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ShopopenactivitymemberAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShopopenactivitymemberAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShopopenactivitymemberForm theForm = (ShopopenactivitymemberForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listShopopenactivitymember".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveShopopenactivitymember".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editShopopenactivitymember".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewShopopenactivitymember".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addShopopenactivitymember".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteShopopenactivitymember".equals(myaction)) {
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
		ShopopenactivitymemberForm theForm = (ShopopenactivitymemberForm) form;
		ShopopenactivitymemberQuery queryVO = theForm.getQueryVO();
		
		ShopopenactivitymemberDao dao = BOFactory.getShopopenactivitymemberDao();
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
		
		ShopopenactivitymemberForm theForm = (ShopopenactivitymemberForm) form;
		Shopopenactivitymember vo = theForm.getVo();
        if(vo!=null&&(vo.getActivityid()==null||vo.getActivityid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
        ShopopenactivitymemberDao dao = BOFactory.getShopopenactivitymemberDao();
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
		ShopopenactivitymemberForm theForm = (ShopopenactivitymemberForm) form;
		ShopopenactivitymemberQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getActivityid();
		
		ShopopenactivitymemberDao dao = BOFactory.getShopopenactivitymemberDao();
		Shopopenactivitymember vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:ShopopenactivitymemberAction;--method:editPage;");
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
		ShopopenactivitymemberForm theForm = (ShopopenactivitymemberForm) form;
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
		ShopopenactivitymemberForm theForm = (ShopopenactivitymemberForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			ShopopenactivitymemberDao dao = BOFactory.getShopopenactivitymemberDao();
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
