package commonWeb.social.action;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.BOFactory_Platform;
import platform.social.dao.UsecommentDao;
import platform.social.vo.Usecomment;
import platform.vo.User;

import commonTool.base.Page;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonWeb.base.BaseAction;
import commonWeb.social.form.UsecommentForm;


public class UsecommentAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UsecommentAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listusecomment".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveusecomment".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("deleteusecomment".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
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
		Long userid = getLoginInfo(true).getUserid();
		UsecommentForm theForm = (UsecommentForm) form;
		Usecomment vo = theForm.getVo();
		Long objectid = vo.getObjectid();
		String objecttype = vo.getObjecttype();
		AssertUtil.assertNotNull(objectid, null);
		AssertUtil.assertNotEmpty(objecttype, null);

		Page page = Page.EMPTY_PAGE;
		UsecommentDao dao = BOFactory_Platform.getUsecommentDao();
		page = dao.selectComments(objectid, objecttype, getCurrPageNumber(request),
						    	  getPageSize(), getTotalNumber(request));
		// 查询用户自己的评价
		if(userid!=null && page!=null && page.getTotalNumber()>0){
		   vo = dao.selectByUser(userid, vo.getObjectid(), vo.getObjecttype());
		   if(vo!=null){ 
			   theForm.setVo(vo);
		   }
		}
		// 查询平均评价
		Map<String, String> map = dao.selectAvgComment(objectid, objecttype);
		if(map!=null){
			theForm.setCommentavggrade(map.get(UsecommentDao.AvgGradeValue));
			theForm.setCommentusernumber(map.get(UsecommentDao.CommentNumber));
		}
		// 处理用户显示名称
		if(page!=null){
			List<Usecomment> list = (List<Usecomment>)page.getList();
			if(list!=null && list.size()>0){
				Set<Long> useridSet = new HashSet<Long>();
				for(Usecomment commentvo : list){
					useridSet.add(commentvo.getUserid());
				}
				//
				Map<Long, User> userMap = BOFactory_Platform.getUserLogic().qryBatchUser(useridSet);
				if(userMap!=null && !userMap.isEmpty()){
					for(Usecomment commentvo : list){
						if(commentvo.getUserid()!=null && userMap.get(commentvo.getUserid())!=null){
							commentvo.setCreatordisplayname(userMap.get(commentvo.getUserid()).getDisplayname());
						}
					}
				}
			}
		}
		
		
		this.setPage(request, page);
		return mapping.findForward("list");
	}
	
	// each app should validate whether can edit it
	private ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		UsecommentForm theForm = (UsecommentForm) form;
		Usecomment vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getObjectid(), null);
		AssertUtil.assertNotEmpty(vo.getObjecttype(), null);
		
		vo = BOFactory_Platform.getUsecommentDao().selectByUser(userid, vo.getObjectid(), vo.getObjecttype());
		if(vo!=null){
		   theForm.setVo(vo);
		}
		return mapping.findForward("editpage");
	}
	
	/** 
	 * Method save
	 */
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long userid = getLoginInfo().getUserid();
		UsecommentForm theForm = (UsecommentForm) form;
		Usecomment vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getObjectid(), null);
		AssertUtil.assertNotEmpty(vo.getObjecttype(), null);
		AssertUtil.assertNotNull(vo.getGrade(), null);
		
		vo.setUserid(userid);
		vo.setUpdatedate(DateUtil.getInstance().getNowtime_GLNZ());
		
		String result = "1";
        String message = "save successfully";
       	try{
      		vo = BOFactory_Platform.getUsecommentDao().save(vo);
   			message = vo.getCommentid().toString();
   		}catch (Exception e) {
   			result = "2";
   			message = "save failed, error message: "+e.getMessage();
   		}
        
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
	/** 
	 * Method delete
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UsecommentForm theForm = (UsecommentForm) form;
		Long commentid = theForm.getVo().getCommentid();
		String result = "1";
		String message = "delete successfully";
		try {
			BOFactory_Platform.getUsecommentDao().deleteByPK(commentid);
		}catch (Exception e) {
			result = "2";
			message = "delete failed, error message: "+e.getMessage();
		}
		this.writeAjaxRtn(result, message, null, response);
		return null;
	}
	
}
