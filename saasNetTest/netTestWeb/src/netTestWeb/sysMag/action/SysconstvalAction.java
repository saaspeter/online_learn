
package netTestWeb.sysMag.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import netTestWeb.sysMag.form.SysconstvalForm;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstvalDao;
import commonTool.biz.dto.SysconstvalQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logic.SysconstvalLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.logicImpl.SysconstvalLogicImpl;
import commonTool.biz.vo.Sysconstval;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstvalAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstvalAction.class.getName());
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SysconstvalForm theForm = (SysconstvalForm) form;
		SysconstvalQuery voQuery = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();
		try {
			SysconstvalLogic logic = BOFactory.getSysconstvalLogic();
			Page page = logic.qryVoPage(voQuery,locale,getCurrPageNumber(request),getPageSize(request), getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:SysconstvalAction Method:list", e);
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
			HttpServletRequest request, HttpServletResponse response) {
		SysconstvalForm theForm = (SysconstvalForm) form;
		Sysconstval vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getConstvalueid()==null||vo.getConstvalueid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        try {
			SysconstvalDao dao = BOFactory.getSysconstvalDao();
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:SysconstvalAction Method:save", e);
			return this.forwardErrorPage(mapping, request, e, "{commonWeb.java.commonaction.errors.save");
		}
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Locale locale = getLoginInfo().getLocale();
		SysconstvalForm theForm = (SysconstvalForm) form;
		SysconstvalQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getConstvalueid();
		try {
			if(pk==null){
			   log.info("----error:in Class:SysconstvalAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			SysconstvalDao dao = BOFactory.getSysconstvalDao();
			Sysconstval vo = dao.selectByPK(pk);
			if(vo==null){
				log.info("----error:in Class:SysconstvalAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
	        //  放入语言下拉列表
			I18nLogic logic = commonTool.biz.logicImpl.BOFactory.getI18nLogic();
			List list = logic.getLabelList(locale);
			theForm.setLocalesList(list);
		} catch (Exception e) {
			log.error("----error in Class:SysconstvalAction Method:editPage", e);
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
			HttpServletRequest request, HttpServletResponse response) {
		SysconstvalForm theForm = (SysconstvalForm) form;
		Long fk = theForm.getVo().getSysconstid();
		Locale locale = getLoginInfo().getLocale();
		try {
			SysconstvalLogic logic = SysconstvalLogicImpl.getInstance();
			List list = logic.getLabelLocales(fk, locale);
			theForm.setLocalesList(list);
		} catch (Exception e) {
			log.error("----error in Class:SysconstvalAction Method:addPage", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.addPage");
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
			HttpServletRequest request, HttpServletResponse response) {
		SysconstvalForm theForm = (SysconstvalForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			SysconstvalDao dao = BOFactory.getSysconstvalDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:SysconstvalAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request,theForm, KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
