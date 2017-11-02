package commonWeb.social.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;
import platform.social.dao.LeavemessageDao;
import platform.social.dao.impl.LeavemessageDaoImpl;
import platform.social.dto.LeavemessageQuery;
import platform.social.logic.LeavemessageLogic;
import platform.social.logic.impl.LeavemessageLogicImpl;
import platform.social.vo.Leavemessage;
import platform.vo.Shop;
import commonTool.base.Page;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonWeb.base.BaseAction;
import commonWeb.social.form.LeavemessageForm;


public class LeavemessageAction extends BaseAction {
	
	static Logger log = Logger.getLogger(LeavemessageAction.class.getName());
	static String SYSCODE = SysparamConstant.syscode;
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		LeavemessageForm theForm = (LeavemessageForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listleavemess".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveleavemess".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editleavemess".equals(myaction)) {
			theForm.setEditType(LeavemessageForm.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addleavemess".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("delshopleavemess".equals(myaction)) {
			theForm.getVo().setObjecttype(Shop.ObjectType);
			myforward = delete(mapping, actionform, request,response);
		} else if ("delsysleavemess".equals(myaction)) {
			theForm.getVo().setObjecttype(Leavemessage.SourceObjetType_System);
			myforward = delete(mapping, actionform, request,response);
		} else if ("getsingleleavemess".equals(myaction)) {
			myforward = getSingleComment(mapping, actionform, request,response);
		} else if ("replysysleavemess".equals(myaction)) {
			theForm.setAddtype(1); // 设置动作为reply
			theForm.getVo().setObjecttype(Leavemessage.SourceObjetType_System);
			myforward = save(mapping, actionform, request,response);
		} else if ("replyshopleavemess".equals(myaction)) {
			theForm.getVo().setObjecttype(Shop.ObjectType);
			theForm.setAddtype(1);
			myforward = save(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	protected ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		LeavemessageForm theForm = (LeavemessageForm) form;
		LeavemessageQuery queryVO = theForm.getQueryVO();
		int pagesize = 30;
		AssertUtil.assertNotEmpty(queryVO.getObjecttype(), ExceptionConstant.Error_Need_Paramter);

		Long sessuserid = getLoginInfo(true).getUserid();
		theForm.setSessuserid(sessuserid);
		String url = "";
		if(Leavemessage.SourceObjetType_System.equals(queryVO.getObjecttype())){
			queryVO.setObjectid(SYSCODE);
			url = "listsystem";
		}else if(Shop.ObjectType.equals(queryVO.getObjecttype())){
			AssertUtil.assertNotNull(theForm.getShopid(), ExceptionConstant.Error_Need_Paramter);
			if(theForm.getShopid()!=null){
			   queryVO.setObjectid(theForm.getShopid().toString());
			}
			url = "listshop";
		}
		Page page = Page.EMPTY_PAGE;
		LeavemessageLogic logic = LeavemessageLogicImpl.getInstance();
		if(queryVO.getObjectid()!=null&&queryVO.getObjecttype()!=null){
			page = logic.qryMessagePage(queryVO.getObjectid(), queryVO.getObjecttype(), getCurrPageNumber(request), pagesize, getTotalNumber(request));
			this.setPage(request, page);
		}
		
		return mapping.findForward(url);
	}
	
	protected ActionForward getSingleComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LeavemessageForm theForm = (LeavemessageForm) form;
		Leavemessage vo = theForm.getVo();
		Long pk = vo.getMessid();
        String result = "1";
        String message = "get comments successfully";
        try {
        	LeavemessageDao dao = LeavemessageDaoImpl.getInstance();
			vo = dao.selectByPK(pk);
			message = vo.getContent();
		} catch (Exception e) {
			result = "2";
			message = "save comments, error:"+e.getMessage();
		}
		
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
	/** 
	 * Method save
	 */
	protected ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		LeavemessageForm theForm = (LeavemessageForm) form;
		int addtype = theForm.getAddtype();
		Leavemessage vo = theForm.getVo();
		vo.setCreatorid(userid);
		vo.setUpdatedate(DateUtil.getInstance().getNowtime_GLNZ());
		vo.setSyscode(SYSCODE);
		String result = "1";
        String message = "save successfully";
        // 如果comment的parent有值,则添加的路径必须是reply,以便权限控制
        if(addtype!=1 && vo.getParent()!=null){
        	result = "2";
        	message = ExceptionConstant.Error_Invalid_Visit;
        }else {
        	try{
        		LeavemessageDao dao = LeavemessageDaoImpl.getInstance();
    			vo = dao.save(vo);
    			message = vo.getMessid().toString();
    		}catch (Exception e) {
    			result = "2";
    			message = "save failed, error message: "+e.getMessage();
    		}
        }
        
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
	
	/** 
	 * Method edit
	 */
	protected ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LeavemessageForm theForm = (LeavemessageForm) form;
		LeavemessageQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getMessid();
		
		LeavemessageDao dao = LeavemessageDaoImpl.getInstance();
		Leavemessage vo = dao.selectByPK(pk);
		theForm.setVo(vo);

		if(theForm.getEditType()==LeavemessageForm.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add
	 */
	protected ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		LeavemessageForm theForm = (LeavemessageForm) form;
		Leavemessage vo = theForm.getVo();
		AssertUtil.assertNotEmpty(vo.getObjecttype(), null);
		if(!vo.getObjecttype().equals(Shop.ObjectType)
				&&!vo.getObjecttype().equals(Leavemessage.SourceObjetType_System)){
			throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
		}
		String url = "addEditPage";
		return mapping.findForward(url);
	}
	
	/** 
	 * Method delete
	 */
	protected ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LeavemessageForm theForm = (LeavemessageForm) form;
		Long shopid = getLoginInfo().getShopid();
		Long messid = theForm.getVo().getMessid();
		String objecttype = theForm.getVo().getObjecttype();
		String result = "1";
		String message = "delete successfully";
		try {
			LeavemessageDao dao = LeavemessageDaoImpl.getInstance();
			LeavemessageLogic logic = LeavemessageLogicImpl.getInstance();
			Leavemessage vo = dao.selectByPK(messid);
			if(vo!=null){
				if(Shop.ObjectType.equals(objecttype)){
					if(vo.getObjectid().equals(shopid.toString())){
						logic.delMessageByPK(messid);
						result = "1";
					}else {
						result = "2";
						message = ExceptionConstant.Error_Unauthorized;
					}
				}else if(Leavemessage.SourceObjetType_System.equals(objecttype)){
					logic.delMessageByPK(messid);
				}
			}
		}catch (Exception e) {
			result = "2";
			message = "delete failed, error message: "+e.getMessage();
		}
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
}
