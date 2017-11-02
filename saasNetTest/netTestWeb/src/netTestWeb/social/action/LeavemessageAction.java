
package netTestWeb.social.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.product.vo.Productbase;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;
import platform.social.dto.LeavemessageQuery;
import platform.social.logic.LeavemessageLogic;
import platform.social.logic.impl.LeavemessageLogicImpl;
import platform.social.vo.Leavemessage;
import commonTool.base.Page;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonWeb.social.form.LeavemessageForm;


public class LeavemessageAction extends commonWeb.social.action.LeavemessageAction {
	
	static Logger log = Logger.getLogger(LeavemessageAction.class.getName());
	static String SYSCODE = SysparamConstant.syscode;
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		LeavemessageForm theForm = (LeavemessageForm) actionform;

		if ("listleavemess".equals(myaction)) {
			if(Productbase.ObjectType.equals(theForm.getQueryVO().getObjecttype())){
				myforward = listproductmess(mapping, actionform, request,response);
			}else {
			    myforward = super.list(mapping, actionform, request,response);
			}
		} else if ("addleavemess".equals(myaction)) {
			if(Productbase.ObjectType.equals(theForm.getVo().getObjecttype())){
				myforward = addPageProd(mapping, actionform, request,response);
			}else {
			    myforward = super.addPage(mapping, actionform, request,response);
			}
		} else if ("replyprodleavemess".equals(myaction)){
			theForm.getVo().setObjecttype(Productbase.ObjectType);
			theForm.setAddtype(1);
			myforward = super.save(mapping, actionform, request,response);
		} else if ("delprodleavemess".equals(myaction)) {
			theForm.getVo().setObjecttype(Productbase.ObjectType);
			myforward = super.delete(mapping, actionform, request,response);
		} else {
		    myforward = super.execute(mapping, actionform, request, response);
		}
		return myforward;
	}
	
	private ActionForward listproductmess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		LeavemessageForm theForm = (LeavemessageForm) form;
		LeavemessageQuery queryVO = theForm.getQueryVO();
		int pagesize = 30;
		AssertUtil.assertNotEmpty(queryVO.getObjecttype(), ExceptionConstant.Error_Need_Paramter);

		Long sessuserid = getLoginInfo(true).getUserid();
		theForm.setSessuserid(sessuserid);
		String url = "listprod";
		Page page = Page.EMPTY_PAGE;
		LeavemessageLogic logic = LeavemessageLogicImpl.getInstance();
		if(queryVO.getObjectid()!=null&&queryVO.getObjecttype()!=null){
			page = logic.qryMessagePage(queryVO.getObjectid(), queryVO.getObjecttype(), getCurrPageNumber(request), pagesize, getTotalNumber(request));
			this.setPage(request, page);
		}
		
		return mapping.findForward(url);
	}
	
	private ActionForward addPageProd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LeavemessageForm theForm = (LeavemessageForm) form;
		Leavemessage vo = theForm.getVo();
		AssertUtil.assertNotEmpty(vo.getObjecttype(), null);
		if(!vo.getObjecttype().equals(Productbase.ObjectType)){
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		String url = "addEditPage";
		return mapping.findForward(url);
	}
	
}
