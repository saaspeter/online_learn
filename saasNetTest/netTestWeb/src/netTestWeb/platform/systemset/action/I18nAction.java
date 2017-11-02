
package netTestWeb.platform.systemset.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.systemset.form.I18nForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import commonTool.base.Page;
import commonTool.biz.dao.I18nDao;
import commonTool.biz.dto.I18nQuery;
import commonTool.biz.logic.I18nLogic;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.I18n;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class I18nAction extends BaseAction {
	
	static Logger log = Logger.getLogger(I18nAction.class.getName());
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		I18nForm theForm = (I18nForm) form;
		Locale locale = getLoginInfo().getLocale();
		I18nQuery voQuery = theForm.getQueryVO();

		I18nLogic logic = BOFactory.getI18nLogic();
		Page page = logic.qryPage(voQuery,locale,getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		I18nForm theForm = (I18nForm) form;
		I18n vo = theForm.getVo();
        
		I18nDao dao = BOFactory.getI18nDao();
		dao.save(vo);
		
		// set messag page parameters.
		String messCode = KeyInMemoryConstant.modifySuccess;
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		I18nForm theForm = (I18nForm) form;
		I18nQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getLocaleid();

		AssertUtil.assertNotNull(pk, null);
		I18nDao dao = BOFactory.getI18nDao();
		I18n vo = dao.selectByPK(pk);
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		I18nForm theForm = (I18nForm) form;
		I18n i18n = new I18n();
		i18n.setIsset(new Short(String.valueOf(CommonConstant.no)));
		i18n.setIsdefaultset(new Short(String.valueOf(CommonConstant.no)));
		theForm.setVo(i18n);
		
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		I18nForm theForm = (I18nForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		I18nDao dao = BOFactory.getI18nDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return this.list(mapping, theForm, request, response);
	}
	
	/** 
	 * Method import the locales from an excel file
     * 不能导入，因为导入会重新搞乱localeid
	 */
//	public ActionForward importExcel(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		I18nForm theForm = (I18nForm) form;
//		FormFile file = theForm.getXmlFile();
//		int rows = 0;
//		
//		if(file!=null&&file.getFileSize()>0){
//		   I18nLogic logic = BOFactory.getI18nLogic();
//		   rows = logic.importExcel(file.getInputStream());
//		}
//		
//		String messCode = KeyInMemoryConstant.importSuccess;
//		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
//		return mapping.findForward("toUrl");
//	}
	
}
