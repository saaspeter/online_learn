
package netTestWeb.wareques.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.base.Page;
import netTest.wareques.dao.QuesdifficultDao;
import netTest.wareques.dto.QuesdifficultQuery;
import netTest.bean.BOFactory;
import netTest.wareques.vo.Quesdifficult;
import netTestWeb.base.BaseAction;
import netTestWeb.base.WebConstant;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.wareques.form.QuesdifficultForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class QuesdifficultAction extends BaseAction {
	
	static Logger log = Logger.getLogger(QuesdifficultAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		QuesdifficultForm theForm = (QuesdifficultForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listQuesdifficult".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveQuesdifficult".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editQuesdifficult".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewQuesdifficult".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addQuesdifficult".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteQuesdifficult".equals(myaction)) {
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Long shopid = getLoginInfo().getShopid();
		QuesdifficultForm theForm = (QuesdifficultForm) form;
		QuesdifficultQuery queryVO = theForm.getQueryVO();
		String listType = theForm.getListType();
		queryVO.setShopid(shopid);
		String url = "list";
		try {
			QuesdifficultDao dao = BOFactory.getQuesdifficultDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:QuesdifficultAction Method:list", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
		if(listType!=null&&"2".equals(listType))
			url = "select";
		return mapping.findForward(url);
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		QuesdifficultForm theForm = (QuesdifficultForm) form;
		Quesdifficult vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getDifficultid()==null||vo.getDifficultid()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        try {
			QuesdifficultDao dao = BOFactory.getQuesdifficultDao();
			dao.save(vo);
		} catch (Exception e) {
			log.error("----error in Class:QuesdifficultAction Method:save", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.save");
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
		QuesdifficultForm theForm = (QuesdifficultForm) form;
		QuesdifficultQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getDifficultid();
		try {
			if(pk==null){
			   log.info("----error:in Class:QuesdifficultAction Method:editPage::no pk");
			   return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noPK");
			}
			QuesdifficultDao dao = BOFactory.getQuesdifficultDao();
			Quesdifficult vo = dao.selectByPK(pk);
			if(vo==null){
				log.info("----error:in Class:QuesdifficultAction Method:editPage::pk:"+pk+":no such record!");
				return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
			}
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:QuesdifficultAction Method:editPage", e);
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Long shopid = getLoginInfo().getShopid();
		QuesdifficultForm theForm = (QuesdifficultForm)form;
		theForm.getVo().setShopid(shopid);
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		QuesdifficultForm theForm = (QuesdifficultForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		try {
			QuesdifficultDao dao = BOFactory.getQuesdifficultDao();
			rows = dao.deleteBatchByPK(keys);
		} catch (Exception e) {
			log.error("----error in Class:QuesdifficultAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		super.setMessagePage(request, theForm, KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
