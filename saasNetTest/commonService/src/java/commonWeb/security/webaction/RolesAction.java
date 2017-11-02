
package commonWeb.security.webaction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;

import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.util.AssertUtil;
import commonWeb.base.BaseAction;
import commonWeb.base.KeyInMemoryConstant;
import commonWeb.security.authentication.UserinfoSession;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dto.RolesQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Roles;
import commonWeb.security.webform.RolesForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class RolesAction extends BaseAction {
	
	static Logger log = Logger.getLogger(RolesAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		RolesForm theForm = (RolesForm) actionform;
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listRoles".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("listSysRolesSel".equals(myaction)) { // 列出系统级的role,暂时没有用到
			theForm.setSelectroletype(1);
			myforward = listRolesSel(mapping, actionform, request,response);
		} else if ("listShopRolesSel".equals(myaction)) {
			theForm.setSelectroletype(2);
			myforward = listRolesSel(mapping, actionform, request,response);
		} else if ("saveRole".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editRolePage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewRolePage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addRolePage".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteRole".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method 角色列表，仅仅列出操作者能看到的角色（默认的角色和自己所处的角色和自己创建的角色）
	 * TODO 列出的权限应该过滤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RolesForm theForm = (RolesForm) form;
		UserinfoSession sessinfo = getLoginInfo();

		Long localeid = sessinfo.getLocaleid();
		Long shopid = sessinfo.getShopid();
		RolesQuery queryVO = theForm.getQueryVO();
		queryVO.setLocaleid(localeid);
		queryVO.setSyscode(SysparamConstant.syscode);
		List list = null;

		RolesDao dao = BOFactory.getRolesDao();
//			 超级管理员可以查看全部的权限，其他的要用user做限制
		if(sessinfo.isRootLogin()){
			list = dao.selectByVO(queryVO);
		}else{ 
			// 先查询系统默认的角色，再查询商店自己创建的角色
			queryVO.setIsdefaultset(RolesConstant.Isdefaultset_default);
			list = dao.selectByVO(queryVO);
			if(list==null)
				list = new ArrayList();
			if(shopid!=null){ // 仅仅对有商店的系统起作用，例如netTest
				queryVO.setIsdefaultset(RolesConstant.Isdefaultset_notdefault);
				queryVO.setShopid(shopid);
				List list2 = dao.selectByVO(queryVO);
				if(list2!=null)
					list.addAll(list2);
			}
		}
		theForm.setRoleList(list);
		
		return mapping.findForward("list");
	}
	
	/** 
	 * 列出role供分配给用户，目前该函数只是使用在商店店里中的给用户分配权限，所以只查询商店级别的角色
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listRolesSel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RolesForm theForm = (RolesForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		int selectroletype = theForm.getSelectroletype();
		RolesQuery queryVO = theForm.getQueryVO();
		// 目前该函数只是使用在商店店里中的给用户分配权限，所以只查询商店级别的角色
		queryVO.setSyscode(syscode);
		queryVO.setLocaleid(localeid);
		
		String url = "listShopRoleSel";
		if(selectroletype==1){
			queryVO.setRolecodeStr(RolesConstant.getSysRoleForSelect());
			url = "listSysRoleSel";
		}else if(selectroletype==2){
			queryVO.setRolecodeStr(RolesConstant.getShopRoleForSelect());
			url = "listShopRoleSel";
		}

		RolesDao dao = BOFactory.getRolesDao();
		List list = dao.selectRolesForUserAdd(queryVO);
		theForm.setRoleList(list);
			
		return mapping.findForward(url);
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!isTokenValid(request)){
			saveToken(request);
			return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.resubmit");
		}else{
			resetToken(request);
		}
		
		RolesForm theForm = (RolesForm) form;
		UserinfoSession sessinfo = getLoginInfo();
		Long shopid = sessinfo.getShopid();
		Long localeid = sessinfo.getLocaleid();
		Roles vo = theForm.getVo();
		vo.setSyscode(SysparamConstant.syscode);
		vo.setLocaleid(localeid);
		vo.setStatus(CommonConstant.Status_valide);
		// 如果是超级管理员，则新增的角色是系统默认角色。否则不是系统默认角色
		if(sessinfo.isRootLogin()){
			vo.setIsdefaultset(RolesConstant.Isdefaultset_default);
			vo.setShopid(null);
		}else {
			vo.setIsdefaultset(RolesConstant.Isdefaultset_notdefault);
			vo.setShopid(shopid);
		}
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getId()==null||vo.getId()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;

		RolesDao dao = BOFactory.getRolesDao();
		dao.save(vo);

		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1", "BizKey");
		return this.list(mapping, theForm, request, response);
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveToken(request);
		RolesForm theForm = (RolesForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		RolesQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getId();
		
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		RolesDao dao = BOFactory.getRolesDao();
		Roles vo = dao.selectValueByPK(pk,localeid);
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		
		if(theForm.getEditType()==CommonConstant.editType_edit)
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		RolesForm theForm = (RolesForm) form;
		Long roleid = theForm.getVo().getId();
		int rows = 0;
		String result = String.valueOf(CommonConstant.success);
		String info = "";

		RolesDao dao = BOFactory.getRolesDao();
		UserRoleDao userroleDao = BOFactory.getUserRoleDao();
		List<Long> userList = userroleDao.selectUserUnderRole(roleid);
		if(userList!=null&&userList.size()>0){
			result = String.valueOf(CommonConstant.fail);
		}else {
		    rows = dao.deleteByPK(roleid);
		    info = String.valueOf(rows);
		}
		this.writeAjaxRtn(result, info,null, response);
		return null;
		
	}
	
}
