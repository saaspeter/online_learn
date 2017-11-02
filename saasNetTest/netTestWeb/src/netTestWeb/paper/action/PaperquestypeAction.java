
package netTestWeb.paper.action;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTest.bean.BOFactory;
import netTest.paper.dao.PaperquestypeDao;
import netTest.paper.dto.PaperquestypeQuery;
import netTest.paper.logic.PaperquestypeLogic;
import netTest.paper.vo.Paperquestype;
import netTest.wareques.constant.QuestionConstant;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.paper.form.PaperquestypeForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.constant.CommonConstant;
import commonTool.constant.LabelValueVO;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.HasReferenceException;
import commonTool.exception.NoSuchRecordException;


public class PaperquestypeAction extends BaseAction {
	
	static Logger log = Logger.getLogger(PaperquestypeAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("addpaperquespatt".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("editpaperquespatt".equals(myaction)) {
			myforward = editPage(mapping, actionform, request,response);
		} else if ("savepaperquespatt".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("delpaperquespatt".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
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
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		Long shopid = getLoginInfo().getShopid();
		PaperquestypeForm theForm = (PaperquestypeForm) form;
		Paperquestype vo = theForm.getVo();
        vo.setShopid(shopid);
        PaperquestypeLogic logic = BOFactory.getPaperquestypeLogic();
        logic.save(vo);
		// set messag page parameters.
		request.setAttribute("pageAction", "closeDiv");
		request.setAttribute("url", "editPaper.do?queryVO.paperid="+vo.getPaperid()
				             +"&questypeidUse="+vo.getQuestypeid());
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
		Long shopid = getLoginInfo().getShopid();
		PaperquestypeForm theForm = (PaperquestypeForm) form;
		theForm.setEditType(WebConstant.editType_edit);
		PaperquestypeQuery queryVO = theForm.getQueryVO();
		Long patternid = queryVO.getPatternid();
		// TODO 在此需要检查该份试卷有没有对应的考试正在进行，如果有则不允许修改试卷。这个可以单独写一个action，界面上用ajax调用检查

		PaperquestypeDao dao = BOFactory.getPaperquestypeDao();
		Paperquestype vo = dao.selectByPK(patternid);	
		if(vo==null)
		   throw new NoSuchRecordException("--class:PaperAction;--method:editPage;");
		theForm.setVo(vo);
        return mapping.findForward("addEditPage");
	}
	
	/** 
	 * Method edit
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
		Locale locale = getLoginInfo().getUseLocale(request);
		PaperquestypeForm theForm = (PaperquestypeForm) form;
		theForm.setEditType(WebConstant.editType_add);       
		// TODO 在此需要检查该份试卷有没有对应的考试正在进行，如果有则不允许修改试卷。这个可以单独写一个action，界面上用ajax调用检查
		Paperquestype vo = theForm.getVo();
		// 查询试卷中已经有的题型，并且找出第一个试卷中还不存在的题型，设置为默认选中的题型
		PaperquestypeDao dao = BOFactory.getPaperquestypeDao();
		List questypeList = dao.qryByPaperid(vo.getPaperid());
		
		Map<Integer, String> paperquestypeMap = new HashMap<Integer, String>();
		if(questypeList!=null && questypeList.size()>0){
			Paperquestype voTemp = null;
			for(int i=0;i<questypeList.size();i++){
				voTemp = (Paperquestype)questypeList.get(i);
				paperquestypeMap.put(voTemp.getQuestype(), voTemp.getQuestypename());
			}
		}
		Integer questypeDefault = null;
		List allquestypeList = QuestionConstant.getQuesTypeList();
		LabelValueVO labelVO = null;
		for(int j=0;j<allquestypeList.size();j++){
			labelVO = (LabelValueVO)allquestypeList.get(j);
			questypeDefault = new Integer(labelVO.getValue());
			if(paperquestypeMap.get(questypeDefault)==null){
				break;
			}
		}
		vo.setQuestype(questypeDefault);
		vo.setQuestypename(QuestionConstant.getQuestypename(questypeDefault, locale));
		theForm.setVo(vo);
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
		PaperquestypeForm theForm = (PaperquestypeForm) form;
		Long patternid = theForm.getVo().getPatternid();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";
		try {
			PaperquestypeLogic logic = BOFactory.getPaperquestypeLogic();
			rows = logic.delete(patternid);
			info = String.valueOf(rows);
		}catch (HasReferenceException e) {
			result = String.valueOf(CommonConstant.fail);
			info = getResources(request, "BizKey").getMessage("exception.ExerquestypeAction.exerquestype.cannotdelete.hasRef");
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			info = getResources(request, "BizKey").getMessage(ExceptionConstantBase.Error_System);
		}
		this.writeAjaxRtn(result, info, null, response);
		return null;
	}
		
	
}
