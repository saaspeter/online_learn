
package platformWeb.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springside.components.acegi.constant.RolesConstant;
import org.springside.components.acegi.dao.RolesDao;
import org.springside.components.acegi.daoImpl.RolesDaoImpl;

import platform.constant.UsershopConstant;
import platform.dao.UsershopDao;
import platform.dto.UsershopQuery;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Usershop;
import platformWeb.base.BaseAction;
import platformWeb.base.KeyInMemoryConstant;
import platformWeb.user.form.UsershopForm;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;


public class UsershopAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UsershopAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listmyshopuser".equals(myaction)) {
			myforward = listMyshopuser(mapping, actionform, request,response);
		} else if ("selshopuserpage".equals(myaction)) {
			myforward = selShopuserPage(mapping, actionform, request,response);
		} else if ("delshopuser".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("updatenickname".equals(myaction)) {
			myforward = updateNickname(mapping, actionform, request,response);
		} else { 
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * 列出用户自己商店中的学员
	 */
	public ActionForward listMyshopuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UsershopForm theForm = (UsershopForm) form;
		UsershopQuery queryVO = theForm.getQueryVO();
		Page page = Page.EMPTY_PAGE;
		if(queryVO.getShopid()!=null){
			//TODO 检查用户是否有权限访问该shop
			UsershopLogic logic = BOFactory_Platform.getUsershopLogic();
			page = logic.qryShopuser(queryVO, getCurrPageNumber(request), getPageSize());
		}
		this.setPage(request, page);

		return mapping.findForward("list");
	}
	
	/**
	 * 列出用户商店中的用户以供在选择页面选择
	 */
	public ActionForward selShopuserPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UsershopForm theForm = (UsershopForm) form;
		String roletype = theForm.getRoletype();
		UsershopQuery queryVO = theForm.getQueryVO();
		Page page = Page.EMPTY_PAGE;
		// 如果是查询 学习考试管理功能的
        if(UsershopForm.Roletype_ExamMag.equals(roletype))
        {
   			String[] rolecodeArr = new String[]{RolesConstant.Roles_Nettest_LearnAdmin, 
   			             RolesConstant.Roles_Nettest_ProductAdmin};
   			RolesDao roleDao = RolesDaoImpl.getInstance();
            String roleidStr = roleDao.selRoleidStrByCode(rolecodeArr, CommonConstant.SysCode_Education);
            queryVO.setRoleidStr(roleidStr);
		}
		queryVO.setUsershopstatus(UsershopConstant.UserShopStatus_using);
		if(queryVO.getShopid()!=null){
			//TODO 检查用户是否有权限访问该shop
			UsershopLogic logic = BOFactory_Platform.getUsershopLogic();
			page = logic.qryShopuser(queryVO, getCurrPageNumber(request), getPageSize());
		}
		this.setPage(request, page);
		return mapping.findForward("listforselect");
	}
	
	// 更新用户在商店中的昵称
	private ActionForward updateNickname(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{

		UsershopForm theForm = (UsershopForm) form;
		Usershop vo = theForm.getVo();
		UsershopLogic logic = BOFactory_Platform.getUsershopLogic();
		int ret = logic.updateNickname(vo.getShopid(), vo.getUserid(), vo.getNickname(), CommonConstant.SysCode_Education);

		String messCode = "CommonSystem.commonAction.operation.failed";
		if(ret==-2){
			messCode = "platformWeb.action.UsershopAction.errors.nicknameExists";
		}else if(ret==1){
			messCode = "CommonSystem.commonAction.operation.succeed";
		}
		String message = getResources(request).getMessage(messCode);
		this.writeAjaxRtn(String.valueOf(ret), message, null, response);
		return null;
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
		UsershopForm theForm = (UsershopForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		UsershopDao dao = BOFactory_Platform.getUsershopDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
