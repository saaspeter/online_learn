package commonWeb.social.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;
import platform.social.constant.CommentsConstant;
import platform.social.dao.CommentsDao;
import platform.social.dao.impl.CommentsDaoImpl;
import platform.social.dto.CommentsQuery;
import platform.social.logic.CommentsLogic;
import platform.social.logic.impl.CommentsLogicImpl;
import platform.social.vo.Comments;
import commonTool.base.Page;
import commonTool.exception.LogicException;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;
import commonWeb.base.BaseAction;
import commonWeb.social.form.CommentsForm;


public class CommentsAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CommentsAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		CommentsForm theForm = (CommentsForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listComments".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveComments".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("replycomments".equals(myaction)) {
			theForm.setAddtype(1); // 设置动作为reply
			myforward = save(mapping, actionform, request,response);
		} else if ("editComments".equals(myaction)) {
			theForm.setEditType(CommentsForm.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addComments".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteComments".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("getSingleComment".equals(myaction)) {
			myforward = getSingleComment(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 */
	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Long sessuserid = getLoginInfo().getUserid();
		CommentsForm theForm = (CommentsForm) form;
		CommentsQuery queryVO = theForm.getQueryVO();
		if(theForm.getSearchuserowner()==1){
			queryVO.setCreatorid(sessuserid);
		}
		theForm.setSessuserid(sessuserid);
		Page page = Page.EMPTY_PAGE;
		CommentsLogic logic = CommentsLogicImpl.getInstance();
		if(!StringUtil.isEmpty(queryVO.getObjectid())&&!StringUtil.isEmpty(queryVO.getObjecttype())){
			page = logic.qryComments(queryVO.getObjectid(), queryVO.getObjecttype(), 
					                 queryVO.getSubclassid(), queryVO.getCreatorid(), 
					                 getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		}
		this.setPage(request, page);
		return mapping.findForward("list");
	}
	
	private ActionForward getSingleComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CommentsForm theForm = (CommentsForm) form;
		Comments vo = theForm.getVo();
		Long pk = vo.getCommentid();
        String result = "1";
        String message = "get comments successfully";
        try {
			CommentsDao dao = CommentsDaoImpl.getInstance();
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
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		CommentsForm theForm = (CommentsForm) form;
		int addtype = theForm.getAddtype();
		Comments vo = theForm.getVo();
		vo.setCreatorid(userid);
		vo.setUpdatedate(DateUtil.getInstance().getNowtime_GLNZ());
		if(vo.getObjectid()!=null&&vo.getObjectid().trim().length()>0&&vo.getObjecttype()!=null
				&&vo.getObjecttype().trim().length()>0){
			vo.setSourcetype(CommentsConstant.SourceType_Object);
			// 检查comments是否是被支持的objectType
			if(!CommentsConstant.isSupportObjectType(vo.getObjecttype())){
				throw new LogicException(ExceptionConstant.Error_Invalid_Parameter);
			}
		}else if(vo.getSourceurl()!=null){
			vo.setSourcetype(CommentsConstant.SourceType_URL);
		}
		String result = "1";
        String message = "save successfully";
        // 如果comment的parent有值,则添加的路径必须是reply,以便权限控制
        if(addtype!=1 && vo.getParent()!=null){
        	result = "2";
        	message = ExceptionConstant.Error_Invalid_Visit;
        }else {
        	try{
    	        CommentsDao dao = CommentsDaoImpl.getInstance();
    			vo = dao.save(vo);
    			message = vo.getCommentid().toString();
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
	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		CommentsForm theForm = (CommentsForm) form;
		Long pk = theForm.getVo().getCommentid();
		
		CommentsDao dao = CommentsDaoImpl.getInstance();
		Comments vo = dao.selectByPK(pk);
		theForm.setVo(vo);

		String url = "addEditPage";
//		if("product".equals(queryVO.getObjecttype())){
//			url = "editPage_product";
//		}
	    return mapping.findForward(url);
	}
	
	/** 
	 * Method add
	 */
	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		CommentsForm theForm = (CommentsForm) form;
		String url = "addEditPage";
//		if("product".equals(queryVO.getObjecttype())){
//			url = "addPage_product";
//		}
		return mapping.findForward(url);
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CommentsForm theForm = (CommentsForm) form;
		Long commentid = theForm.getVo().getCommentid();
		String result = "1";
		String message = "delete successfully";
		try {
			CommentsLogic logic = CommentsLogicImpl.getInstance();
			logic.delCommentsByPK(commentid);
		}catch (Exception e) {
			result = "2";
			message = "delete failed, error message: "+e.getMessage();
		}
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
}
