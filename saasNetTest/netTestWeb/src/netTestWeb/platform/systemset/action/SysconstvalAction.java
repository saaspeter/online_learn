
package netTestWeb.platform.systemset.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.systemset.form.SysconstvalForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstvalDao;
import commonTool.biz.dto.SysconstvalQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logic.SysconstvalLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.logicImpl.SysconstvalLogicImpl;
import commonTool.biz.vo.Sysconstval;
import commonTool.util.AssertUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstvalAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstvalAction.class.getName());
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstvalForm theForm = (SysconstvalForm) form;
		SysconstvalQuery voQuery = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();
		
		SysconstvalLogic logic = BOFactory.getSysconstvalLogic();
		Page page = logic.qryVoPage(voQuery,locale,getCurrPageNumber(request),getPageSize(request), getTotalNumber(request));
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
		SysconstvalForm theForm = (SysconstvalForm) form;
		Sysconstval vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getConstvalueid()==null||vo.getConstvalueid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;

		SysconstvalDao dao = BOFactory.getSysconstvalDao();
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
		Locale locale = getLoginInfo().getLocale();
		SysconstvalForm theForm = (SysconstvalForm) form;
		SysconstvalQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getConstvalueid();
		
        AssertUtil.assertNotNull(pk, null);
		SysconstvalDao dao = BOFactory.getSysconstvalDao();
		Sysconstval vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);
        //  放入语言下拉列表
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysconstvalForm theForm = (SysconstvalForm) form;
		Long fk = theForm.getVo().getSysconstid();
		Locale locale = getLoginInfo().getLocale();

		SysconstvalLogic logic = SysconstvalLogicImpl.getInstance();
		List list = logic.getLabelLocales(fk, locale);
		theForm.setLocalesList(list);
		return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstvalForm theForm = (SysconstvalForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		SysconstvalDao dao = BOFactory.getSysconstvalDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
