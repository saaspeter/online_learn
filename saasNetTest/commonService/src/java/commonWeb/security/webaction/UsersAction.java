package commonWeb.security.webaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import platform.dao.UserDao;
import platform.dto.UserQuery;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.User;
import commonTool.base.Page;
import commonTool.util.AssertUtil;
import commonWeb.base.BaseAction;
import commonWeb.base.BaseForm;
import commonWeb.security.webform.UsersForm;

/**
 * 
 */
public class UsersAction extends BaseAction {

	static Logger log = Logger.getLogger(UsersAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		BaseForm theForm = (BaseForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listUsersForRoleAdd".equals(myaction)) {
			myforward = listUsersForRoleAdd(mapping, actionform, request,response);
		} else if ("viewUserPage".equals(myaction)) {
			myforward = viewPage(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}

//	/**
//	 * Method list
//	 * 
//	 */
//	public ActionForward list(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		UsersForm theForm = (UsersForm) form;
//		UserQuery queryVO = theForm.getQueryVO();
//		try {
//			UserDao dao = BOFactory_Platform.getUserDao();
//			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request),
//					getPageSize(), getTotalNumber(request));
//			this.setPage(request, page);
//		} catch (Exception e) {
//			log.error("----error in Class:UsersAction Method:list", e);
//			return this.forwardErrorPage(mapping, request, e,
//					"commonWeb.java.commonaction.errors.list");
//		}
//
//		return mapping.findForward("list");
//	}
	
	/** 
	 * Method listUsersForRoleAdd
	 */
	public ActionForward listUsersForRoleAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UsersForm theForm = (UsersForm) form;
		UserQuery queryVO = theForm.getQueryVO();
		UserDao dao = BOFactory_Platform.getUserDao();
		Page page = dao.selectUsersForRoleAddPage(queryVO, getCurrPageNumber(request), getPageSize(), getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("listUsersForRoleAdd");
	}

	/**
	 */
	public ActionForward viewPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		UsersForm theForm = (UsersForm) form;
		UserQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getUserid();
        AssertUtil.assertNotNull(pk, null);
		UserDao dao = BOFactory_Platform.getUserDao();
		User vo = dao.selectByPK(pk);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		return mapping.findForward("viewPage");
	}

}
