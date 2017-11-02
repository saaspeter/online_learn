
package netTestWeb.platform.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.platform.user.form.UsershopForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.UsershopConstant;
import platform.dao.UsershopDao;
import platform.dto.UsershopQuery;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Usershop;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.impl.RolesDaoImpl;


public class UsershopAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UsershopAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("selproductmaguser".equals(myaction)) {
			myforward = selProductMagUser(mapping, actionform, request,response);
		} else if ("updatenickname".equals(myaction)) {
			myforward = updateNickname(mapping, actionform, request,response);
		} 
		
		else { 
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
//	/** 
//	 * 列出用户自己商店中的学员
//	 */
//	public ActionForward listMyshopuser(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		UsershopForm theForm = (UsershopForm) form;
//		Long shopid = getLoginInfo().getShopid();
//		UsershopQuery queryVO = theForm.getQueryVO();
//		queryVO.setShopid(shopid);
//		UsershopLogic logic = platform.logicImpl.BOFactory_Platform.getUsershopLogic();
//		Page page = logic.qryShopuser(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
//		this.setPage(request, page);
//
//		return mapping.findForward("list");
//	}
	
	/**
	 * 列出用户商店中的用户以供在选择页面选择
	 */
	public ActionForward selProductMagUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long shopid = getLoginInfo().getShopid();
		UsershopForm theForm = (UsershopForm) form;
		String roletype = theForm.getRoletype();
		UsershopQuery queryVO = theForm.getQueryVO();
		queryVO.setShopid(shopid);
		// 如果是查询 学习考试管理功能的, 只显示具有特定角色的人员. 例如选择考试阅卷老师
        if(UsershopForm.Roletype_ExamMag.equals(roletype))
        {
   			String[] rolecodeArr = new String[]{RolesConstant.ROLE_ShopCreator, 
   			             RolesConstant.ROLE_ShopAdmin, RolesConstant.ROLE_ShopProductAdmin};
   			RolesDao roleDao = RolesDaoImpl.getInstance();
            String roleidStr = roleDao.selRoleidStrByCode(rolecodeArr, CommonConstant.SysCode_Education);
            queryVO.setRoleidStr(roleidStr);
		}
		queryVO.setUsershopstatus(UsershopConstant.UserShopStatus_active);
		UsershopDao dao = platform.logicImpl.BOFactory_Platform.getUsershopDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		return mapping.findForward("listforselect");
	}
		
	// 更新用户在商店中的昵称
	private ActionForward updateNickname(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UsershopForm theForm = (UsershopForm) form;
		Usershop vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getShopid(), null);
		AssertUtil.assertNotNull(vo.getUserid(), null);
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
		
}
