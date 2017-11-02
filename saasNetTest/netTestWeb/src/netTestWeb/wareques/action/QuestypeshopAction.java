
package netTestWeb.wareques.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.wareques.constant.QuestypeConstant;
import netTest.wareques.dao.QuestypeshopDao;
import netTest.wareques.dto.QuestypeshopQuery;
import netTest.wareques.vo.Questypeshop;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.wareques.form.QuestypeshopForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.constant.CommonConstant;
import commonTool.exception.HasReferenceException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.DateUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class QuestypeshopAction extends BaseAction {
	
	static Logger log = Logger.getLogger(QuestypeshopAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		QuestypeshopForm theForm = (QuestypeshopForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listQuestypeshop".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveQuestypeshop".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editQuestypeshop".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewQuestypeshop".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addQuestypeshop".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteQuestypeshop".equals(myaction)) {
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
		QuestypeshopForm theForm = (QuestypeshopForm) form;
		QuestypeshopQuery queryVO = theForm.getQueryVO();
		
		QuestypeshopDao dao = BOFactory.getQuestypeshopDao();
		List list = dao.selectByVO(queryVO);
		theForm.setList(list);
		
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		Long shopid = getLoginInfo().getShopid();
		QuestypeshopForm theForm = (QuestypeshopForm) form;
		Questypeshop vo = theForm.getVo();
        if(vo!=null&&(vo.getQuestypeid()==null||vo.getQuestypeid()==0)){
            vo.setShopid(shopid);
            vo.setCreatedate(DateUtil.getInstance().getNowtime_GLNZ());
            vo.setCreatetype(QuestypeConstant.CreateType_Customer);
        	messCode = KeyInMemoryConstant.AddSuccess;
        }
        
        QuestypeshopDao dao = BOFactory.getQuestypeshopDao();
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		QuestypeshopForm theForm = (QuestypeshopForm) form;
		QuestypeshopQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getQuestypeid();
		
		QuestypeshopDao dao = BOFactory.getQuestypeshopDao();
		Questypeshop vo = dao.selectByPK(pk);
		if(vo==null)
		   throw new NoSuchRecordException("--class:QuestypeshopAction;--method:editPage;");
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		saveToken(request);
		QuestypeshopForm theForm = (QuestypeshopForm) form;
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
		QuestypeshopForm theForm = (QuestypeshopForm) form;
		Long questypeid = theForm.getVo().getQuestypeid();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			//TODO 新建一个QuestypeLogic类，要检验自定义的questype是否被题目引用。
			QuestypeshopDao dao = BOFactory.getQuestypeshopDao();
			rows = dao.deleteByPK(questypeid);
			info = String.valueOf(rows);
		}catch (HasReferenceException e) {
			//TODO 在此得到异常数据，组装成ajax类型的数据返
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
	
}
