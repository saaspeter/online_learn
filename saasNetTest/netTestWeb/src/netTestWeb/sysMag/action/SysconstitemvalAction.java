
package netTestWeb.sysMag.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.sysMag.form.SysconstitemvalForm;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstitemvalDao;
import commonTool.biz.dto.SysconstitemvalQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logic.SysconstitemvalLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysconstitemval;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstitemvalAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstitemvalAction.class.getName());
	
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Locale locale = getLoginInfo().getLocale();
		SysconstitemvalQuery queryVO = theForm.getQueryVO();
		try {
			SysconstitemvalLogic logic = BOFactory.getSysconstitemvalLogic();
			Page page = logic.qryVoPage(queryVO,locale,getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemvalAction Method:list", e);
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Sysconstitemval vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getItemvalueid()==null||vo.getItemvalueid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        try {
			SysconstitemvalDao dao = BOFactory.getSysconstitemvalDao();
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemvalAction Method:save", e);
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		SysconstitemvalQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getItemvalueid();
		try {
			if(pk==null){
			   log.info("----error:in Class:SysconstitemvalAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			SysconstitemvalDao dao = BOFactory.getSysconstitemvalDao();
			Sysconstitemval vo = dao.selectByPK(pk);
			if(vo==null){
				log.info("----error:in Class:SysconstitemvalAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
	        //  放入语言下拉列表
			I18nLogic logic = commonTool.biz.logicImpl.BOFactory.getI18nLogic();
			List list = logic.getLabelList(locale);
			theForm.setLocalesList(list);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemvalAction Method:editPage", e);
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		Long fk = theForm.getVo().getItemid();
		Locale locale = getLoginInfo().getLocale();
		try {
			SysconstitemvalLogic logic = BOFactory.getSysconstitemvalLogic();
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
		SysconstitemvalForm theForm = (SysconstitemvalForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			SysconstitemvalDao dao = BOFactory.getSysconstitemvalDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemvalAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request,theForm, KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
