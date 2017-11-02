
package netTestWeb.platform.shop.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTestWeb.base.BaseAction;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.shop.form.ShoplicenseForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import platform.logicImpl.BOFactory_Platform;
import platform.shop.dao.ShoplicenseDao;
import platform.shop.vo.Shoplicense;
import commonTool.exception.NoSuchRecordException;
import commonWeb.base.KeyInMemoryConstant;

/**
 * 这个类暂时没有用到，没有配置
 * @author xuefan
 *
 */
public class ShoplicenseAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ShoplicenseAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ShoplicenseForm theForm = (ShoplicenseForm) actionform;

		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listShoplicense".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveShoplicense".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editShoplicense".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewShoplicense".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addShoplicense".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ShoplicenseForm theForm = (ShoplicenseForm) form;
		
		ShoplicenseDao dao = BOFactory_Platform.getShoplicenseDao();
		List list = dao.selectByShop(theForm.getShopid(), null);
		theForm.setDatalist(list);
		
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.saveSuccess;
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		ShoplicenseForm theForm = (ShoplicenseForm) form;
		Shoplicense vo = theForm.getVo();       
        ShoplicenseDao dao = BOFactory_Platform.getShoplicenseDao();
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
		saveToken(request);
		ShoplicenseForm theForm = (ShoplicenseForm) form;
		Shoplicense vo = theForm.getVo();
		
		ShoplicenseDao dao = BOFactory_Platform.getShoplicenseDao();
		vo = dao.selectByPK(vo.getShopid(), vo.getSyscode(), vo.getResourcecode());
		if(vo==null)
		   throw new NoSuchRecordException("--class:ShoplicenseAction;--method:editPage;");
		theForm.setVo(vo);

		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	

	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		ShoplicenseForm theForm = (ShoplicenseForm) form;
		theForm.setEditType(WebConstant.editType_add);
		return mapping.findForward("addEditPage");
	}
	
}
