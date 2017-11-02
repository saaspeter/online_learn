
package netTestWeb.sysMag.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import netTestWeb.sysMag.form.SysconstitemForm;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstDao;
import commonTool.biz.dao.SysconstitemDao;
import commonTool.biz.daoImpl.SysconstDaoImpl;
import commonTool.biz.dto.SysconstitemQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysconst;
import commonTool.biz.vo.Sysconstitem;
import commonTool.util.ObjUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstitemAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstitemAction.class.getName());
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		Long localeid = getLoginInfo().getLocaleid();
		SysconstitemForm theForm = (SysconstitemForm) form;
		SysconstitemQuery queryVO = theForm.getQueryVO();
		String code = queryVO.getSysconstcode();
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
		try {
			// 查询所属常量的名字
			SysconstDao constDao = SysconstDaoImpl.getInstance();
			Sysconst sysconst = constDao.selectByConstCode(code, queryVO.getLocaleid());
			if(sysconst!=null)
			   theForm.setConstname(sysconst.getName());
			//
			SysconstitemDao dao = BOFactory.getSysconstitemDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemAction Method:list", e);
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
		SysconstitemForm theForm = (SysconstitemForm) form;
		Sysconstitem vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
		int actionType = KeyInMemoryConstant.ActionTypeEditInt;
        if(vo!=null&&(vo.getItemid()==null||vo.getItemid()==0)){
        	actionType = KeyInMemoryConstant.ActionTypeAddInt;
        	messCode = KeyInMemoryConstant.AddSuccess;
        }
        try {
			SysconstitemDao dao = BOFactory.getSysconstitemDao();
			// 如果是新增，先检查子选项值是否唯一
			if(dao.existValue(vo.getItemid(),vo.getSysconstcode(), vo.getValue())){
			   	super.setMessagePage(request,theForm,"netTest.sysConst.duplicateSysConstCode",null,"BizKey");
			   	if(actionType==KeyInMemoryConstant.ActionTypeAddInt)
			   	   return this.addPage(mapping, theForm, request, response);
			   	else{
			   	   SysconstitemQuery queryVO = theForm.getQueryVO();
			   	   ObjUtil.copyProperties(queryVO, vo);
			   	   return this.editPage(mapping, theForm, request, response);
			   	}
			}
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemAction Method:save", e);
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
			HttpServletRequest request, HttpServletResponse response) {
		SysconstitemForm theForm = (SysconstitemForm) form;
		SysconstitemQuery queryVO = theForm.getQueryVO();
		String code = queryVO.getSysconstcode();
		Long localeid = getLoginInfo().getLocaleid();
		Long pk = queryVO.getItemid();
		if(queryVO.getLocaleid()==null)
			queryVO.setLocaleid(localeid);
		try {
			if(pk==null){
			   log.info("----error:in Class:SysconstitemAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			SysconstitemDao dao = BOFactory.getSysconstitemDao();
			Sysconstitem vo = dao.selectByPK(pk,localeid);
			if(vo==null){
				log.info("----error:in Class:SysconstitemAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
            // 查询所属常量的名字
			SysconstDao constDao = SysconstDaoImpl.getInstance();
			Sysconst sysconst = constDao.selectByConstCode(code, queryVO.getLocaleid());
			if(sysconst!=null)
			   theForm.setConstname(sysconst.getName());
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemAction Method:editPage", e);
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
		SysconstitemForm theForm = (SysconstitemForm) form;
		String code = theForm.getVo().getSysconstcode();
		Locale locale = getLoginInfo().getLocale();
		Long localeid = getLoginInfo().getLocaleid();
		// 放入语言下拉列表
		try {
			I18nLogic logic = commonTool.biz.logicImpl.BOFactory.getI18nLogic();
			List list = logic.getLabelList(locale);
			theForm.setLocalesList(list);
            // 查询所属常量的名字
			SysconstDao constDao = SysconstDaoImpl.getInstance();
			Sysconst sysconst = constDao.selectByConstCode(code, localeid);
			if(sysconst!=null)
			   theForm.setConstname(sysconst.getName());
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
		SysconstitemForm theForm = (SysconstitemForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			SysconstitemDao dao = BOFactory.getSysconstitemDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:SysconstitemAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request,theForm, KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
