
package netTestWeb.sysMag.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.sysMag.form.SysconstForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstDao;
import commonTool.biz.dto.SysconstQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysconst;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstAction.class.getName());
	
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
		SysconstForm theForm = (SysconstForm) form;
		SysconstQuery queryVO = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();
		Long localeid = getLoginInfo().getLocaleid();
		theForm.setLocale(locale);
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
        queryVO.setSyscode(CommonConstant.SysCode_Education);
		try {
			SysconstDao dao = BOFactory.getSysconstDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:SysconstAction Method:list", e);
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
		SysconstForm theForm = (SysconstForm) form;
		Sysconst vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
		int actionType = KeyInMemoryConstant.ActionTypeEditInt;
        if(vo!=null&&(vo.getSysconstid()==null||vo.getSysconstid()==0)){
        	actionType = KeyInMemoryConstant.ActionTypeAddInt;
        	messCode = KeyInMemoryConstant.AddSuccess;
        }
        try {
			SysconstDao dao = BOFactory.getSysconstDao();
			// 如果是新增，先检查常量编码是否唯一
			if((actionType==KeyInMemoryConstant.ActionTypeAddInt)
					&&(dao.selectByConstCode(vo.getSysconstcode())!=null)){
			   	super.setMessagePage(request,theForm,"netTest.sysConst.duplicateSysConstCode",null,"BizKey");
			   	return this.addPage(mapping, theForm, request, response);
			}
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:SysconstAction Method:save", e);
			return this.forwardErrorPage(mapping, request, e, "{commonWeb.java.commonaction.errors.save");
		}
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return this.list(mapping, theForm, request, response);
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
			HttpServletRequest request, HttpServletResponse response) {
		SysconstForm theForm = (SysconstForm) form;
		Long pk = theForm.getVo().getSysconstid();
		Long localeid = getLoginInfo().getLocaleid();
		Locale locale = getLoginInfo().getLocale();
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));
		try {
			if(pk==null){
			   log.info("----error:in Class:SysconstAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			SysconstDao dao = BOFactory.getSysconstDao();
			Sysconst vo = dao.selectByPK(pk,localeid);
			if(vo==null){
				log.info("----error:in Class:SysconstAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:SysconstAction Method:editPage", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.editPage");
		}
        return mapping.findForward("editPage");
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
		SysconstForm theForm = (SysconstForm) form;
		Locale locale = getLoginInfo().getLocale();
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));
		// 设置国家语言
		// 放入语言下拉列表
		try {
			I18nLogic logic = commonTool.biz.logicImpl.BOFactory.getI18nLogic();
			List list = logic.getLabelList(locale);
			theForm.setLocalesList(list);
		} catch (Exception e) {
			log.error("----error in Class:SysconstAction Method:addPage", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.addPage");
		}
		return mapping.findForward("addPage");
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
		SysconstForm theForm = (SysconstForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			SysconstDao dao = BOFactory.getSysconstDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:SysconstAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request,theForm, KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return this.list(mapping, theForm, request, response);
	}
	
}
