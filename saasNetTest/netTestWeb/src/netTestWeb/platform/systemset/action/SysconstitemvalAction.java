
package netTestWeb.platform.systemset.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.systemset.form.SysconstitemvalForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstitemvalDao;
import commonTool.biz.dto.SysconstitemvalQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logic.SysconstitemvalLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysconstitemval;
import commonTool.util.AssertUtil;


public class SysconstitemvalAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstitemvalAction.class.getName());
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Locale locale = getLoginInfo().getLocale();
		SysconstitemvalQuery queryVO = theForm.getQueryVO();

		SysconstitemvalLogic logic = BOFactory.getSysconstitemvalLogic();
		Page page = logic.qryVoPage(queryVO,locale,getCurrPageNumber(request),getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Sysconstitemval vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(vo!=null&&(vo.getItemvalueid()==null||vo.getItemvalueid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        
		SysconstitemvalDao dao = BOFactory.getSysconstitemvalDao();
		dao.save(vo);

		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Locale locale = getLoginInfo().getLocale();
		SysconstitemvalQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getItemvalueid();
		
		AssertUtil.assertNotNull(pk, null);
		SysconstitemvalDao dao = BOFactory.getSysconstitemvalDao();
		Sysconstitemval vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);
        // 放入语言下拉列表
		I18nLogic logic = commonTool.biz.logicImpl.BOFactory.getI18nLogic();
		List list = logic.getLabelList(locale);
		theForm.setLocalesList(list);
		
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Long fk = theForm.getVo().getItemid();
		Locale locale = getLoginInfo().getLocale();
		
		SysconstitemvalLogic logic = BOFactory.getSysconstitemvalLogic();
		List list = logic.getLabelLocales(fk, locale);
		theForm.setLocalesList(list);
	
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		SysconstitemvalDao dao = BOFactory.getSysconstitemvalDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
