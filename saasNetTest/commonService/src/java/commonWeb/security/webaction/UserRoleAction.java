
package commonWeb.security.webaction;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.security.GrantedAuthority;

import platform.constant.ShopConstant;
import platform.dao.UserDao;
import platform.dto.UserQuery;
import platform.exception.ExceptionConstant;
import platform.vo.User;

import commonTool.base.Page;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonWeb.base.BaseAction;
import commonWeb.base.KeyInMemoryConstant;
import commonWeb.security.authentication.UserinfoSession;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.constant.UserRolesConstant;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dto.UserRoleQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Roles;
import commonWeb.security.vo.UserRole;
import commonWeb.security.webform.UserRoleForm;


public class UserRoleAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserRoleAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		UserRoleForm theForm = (UserRoleForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listUserRole".equals(myaction)) {
			// 该方法只给系统管理人员使用，注意这点
			theForm.setSecuritylevel("1");
			myforward = listUserRole(mapping, actionform, request,response);
		} else if ("listShopUserRole".equals(myaction)) {
			theForm.setSecuritylevel("0");
			myforward = listUserRole(mapping, actionform, request,response);
		} else if ("listRoleUser".equals(myaction)) {
			myforward = listRoleUser(mapping, actionform, request,response);
		} else if ("saveUserRole".equals(myaction)) {
			theForm.setSecuritylevel("1");
			myforward = saveUserRole(mapping, actionform, request,response);
		} else if ("saveShopUserRole".equals(myaction)) {
			theForm.setSecuritylevel("0");
			myforward = saveUserRole(mapping, actionform, request,response);
		} else if ("saveRoleUser".equals(myaction)) {
			myforward = saveRoleUser(mapping, actionform, request,response);
		} else if ("deleteRoleUser".equals(myaction)) {
			myforward = deleteRoleUser(mapping, actionform, request,response);
		} else if ("deleteUserRole".equals(myaction)) {
			theForm.setSecuritylevel("1");
			myforward = deleteUserRole(mapping, actionform, request,response);
		} else if ("deleteShopUserRole".equals(myaction)) {
			theForm.setSecuritylevel("0");
			myforward = deleteUserRole(mapping, actionform, request,response);
		} else if ("listRescsForRoleAdd".equals(myaction)) {
			myforward = listRescsForRoleAdd(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method listUserRole：列出该用户下的角色
	 */
	public ActionForward listUserRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		String securitylevel = theForm.getSecuritylevel();
		AssertUtil.assertNotNull(theForm.getQueryVO().getUserid(), null);
		
		UserinfoSession loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		Long localeid = loginfo.getLocaleid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		UserRoleQuery queryVO = theForm.getQueryVO();
		if("1".equals(securitylevel)){ // 系统管理员查询
			if(queryVO.getShopid()==null){
				shopid = UserRolesConstant.NonShopID;
			}else {
				shopid = queryVO.getShopid();
			}
		}
		
		UserDao userDao = platform.logicImpl.BOFactory_Platform.getUserDao();
		UserRoleDao userroleDao = BOFactory.getUserRoleDao();
		
		User userVO = userDao.selectByPK(queryVO.getUserid());
		AssertUtil.assertNotNull(userVO, "commonWeb.pages.userNotExist");
		theForm.setUsersVO(userVO);
		List<Long> roleidlist = userroleDao.selectUserRole(queryVO.getUserid(), shopid, syscode);
		List<Roles> list = new ArrayList<Roles>();	
		RolesDao roledao = BOFactory.getRolesDao();
		if(roleidlist!=null){
    		Roles rolevo = null;
    		for(Long roleid : roleidlist){
    			rolevo = roledao.selectValueByPK(roleid, localeid);
    			if(rolevo!=null){
    				list.add(rolevo);
    			}
    		}
		}
		theForm.setRoleList(list);

		return mapping.findForward("listUserRole");
	}
	
	/** 
	 * Method listRoleUser：列出该角色下的用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listRoleUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		Long shopid = getLoginInfo().getShopid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		Roles rolesVO = theForm.getRolesVO();
		UserRoleQuery queryVO = theForm.getQueryVO();
		
		UserQuery userqueryVO = new UserQuery();
		userqueryVO.setLocaleid(localeid);
		userqueryVO.setShopid(shopid);
		userqueryVO.setLoginname(queryVO.getLoginname());
		userqueryVO.setSyscode(syscode);
		UserDao userDao = platform.logicImpl.BOFactory_Platform.getUserDao();
		RolesDao roleDao = BOFactory.getRolesDao();
		
		rolesVO = roleDao.selectValueByPK(rolesVO.getId(),localeid);
		AssertUtil.assertNotNull(rolesVO, "commonWeb.pages.security.roleNotExist");
		theForm.setRolesVO(rolesVO);
		userqueryVO.setRoleId(rolesVO.getId());
		Page page = userDao.selectByRoleIdPage(userqueryVO, getCurrPageNumber(request), getPageSize(), getTotalNumber(request));
		this.setPage(request, page);

		return mapping.findForward("listRoleUser");
	}
	
	/** 
	 * Method saveUserRole:保存一个用户下所选角色
	 */
	public ActionForward saveUserRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		String securitylevel = theForm.getSecuritylevel();
		
		String roleIds = theForm.getRoleIds();
		Long[] roleIdsArr = null;
		Long userId = theForm.getQueryVO().getUserid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		int num = 0;
		
		UserRole vo = theForm.getVo();
		UserinfoSession userinfo = getLoginInfo();
		Long shopid = userinfo.getShopid();
		GrantedAuthority[] authories = userinfo.getAuthorities();
		if("0".equals(securitylevel)){ // 普通shop admin必须登录商店
			AssertUtil.assertNotNull(shopid, null);
		}else if("1".equals(securitylevel)){
			if(theForm.getQueryVO().getShopid()==null){
				shopid = UserRolesConstant.NonShopID;
			}else {
				shopid = theForm.getQueryVO().getShopid();
			}
		}
		
		vo.setShopid(shopid);
		vo.setSyscode(syscode);
		vo.setUsetype(UserRolesConstant.UseType_useAssign);

    	UserRoleDao dao = BOFactory.getUserRoleDao();
    	RolesDao roleDao = BOFactory.getRolesDao();
    	List<Long> hasRoleIdList = dao.selectUserRole(userId, shopid, syscode);
    	if(hasRoleIdList==null){
    		hasRoleIdList = new ArrayList<Long>();
    	}
		if(roleIds!=null&&roleIds.trim().length()>0){
			StringTokenizer tokens = new StringTokenizer(roleIds,",");
			if(tokens!=null){
			   Roles roleVO = null;
			   boolean hasPrivilege = false;
			   roleIdsArr = new Long[tokens.countTokens()];
			   for(int i=0;tokens.hasMoreTokens();i++){
				   roleIdsArr[i] = new Long(tokens.nextToken());
				   // 查看用户当前已有的role中是否包含要新添加的role
				   if(!hasRoleIdList.contains(roleIdsArr[i])){
					   roleVO = roleDao.selectByPK(roleIdsArr[i]);
					   // 检查当前用户是否有权限操作用户角色
					   hasPrivilege = RolesConstant.hasPrivilegeToAddRole(roleVO.getCode(), authories);
					   if(!hasPrivilege){
						   throw new LogicException(ExceptionConstant.Error_Unauthorized);
					   }
					   hasRoleIdList.add(roleIdsArr[i]);
				   }else {
					   roleIdsArr[i] = null;
				   }
			   }
			}
			num = dao.insertMoreBatch(new Long[]{userId}, roleIdsArr, vo);
		}
		
		// set messag page parameters.
		String messCode = KeyInMemoryConstant.AddSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(num), "BizKey");
		return this.listUserRole(mapping, form, request, response);
	}
	
	/** 
	 * Method saveRoleUser：保存一个角色下的所选用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveRoleUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		String userIds = theForm.getUserIds();
		Long[] userIdsArr = null;
		Roles rolesVO = theForm.getRolesVO();
		Long roleId = rolesVO.getId();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		Long shopid = getLoginInfo().getShopid();
		int num = 0;
		UserRole vo = theForm.getVo();
		if(shopid==null){
			shopid = UserRolesConstant.NonShopID;
		}
		vo.setShopid(shopid);
		vo.setSyscode(syscode);
		vo.setUsetype(UserRolesConstant.UseType_useAssign);

    	UserRoleDao dao = BOFactory.getUserRoleDao();
		if(userIds!=null&&userIds.trim().length()>0){
			StringTokenizer tokens = new StringTokenizer(userIds,",");
			if(tokens!=null){
				userIdsArr = new Long[tokens.countTokens()];
			   for(int i=0;tokens.hasMoreTokens();i++){
				   userIdsArr[i] = new Long(tokens.nextToken());
			   }
			}
			num = dao.insertMoreBatch(userIdsArr,new Long[]{roleId},vo);
		}

		// set messag page parameters.
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(num), "BizKey");
		if(num>0)
		   return this.listRoleUser(mapping, form, request, response);
		else
		   return mapping.findForward("failure");
	}
	
	/** 
	 * Method deleteRoleUser:删除角色下的选定用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteRoleUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		String[] keys = theForm.getKeys();
		Roles rolesVO = theForm.getRolesVO();
		Long roleId = rolesVO.getId();
		Long shopid = getLoginInfo().getShopid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		
		int num = 0;
    	List list = new ArrayList();
    	UserRoleDao dao = BOFactory.getUserRoleDao();
		if(keys!=null&&keys.length>0){
			if(shopid==null){
				shopid = UserRolesConstant.NonShopID;
			}
			for(int i=0;i<keys.length;i++){
			    UserRole vo = new UserRole();	
				vo.setRoleid(roleId);
				vo.setShopid(shopid);
				vo.setUserid(new Long(keys[i]));
				vo.setSyscode(syscode);
				list.add(vo);
			}
			num = dao.deleteBatchByPK(list);
		}

		// set messag page parameters.
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(num), "BizKey");
		if(num>0)
			return this.listRoleUser(mapping, form, request, response);
		else
		   return mapping.findForward("failure");
	}
	
	/** 
	 * Method deleteUserRole：删除某用户下的选定角色集合
	 */
	public ActionForward deleteUserRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		String[] keys = theForm.getKeys();
		String securitylevel = theForm.getSecuritylevel();
		UserRoleQuery queryVO = theForm.getQueryVO();
		Long userId = queryVO.getUserid();
		UserinfoSession loginfo = getLoginInfo();
		Long shopid = loginfo.getShopid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		int num = 0;
		GrantedAuthority[] authories = loginfo.getAuthorities();
    	List list = new ArrayList();
    	UserRoleDao dao = BOFactory.getUserRoleDao();
    	RolesDao roleDao = BOFactory.getRolesDao();
		if(keys!=null&&keys.length>0){
			if("0".equals(securitylevel)){ // 普通shop admin必须登录商店
				AssertUtil.assertNotNull(shopid, null);
			}else if("1".equals(securitylevel)){
				if(queryVO.getShopid()==null){
					shopid = UserRolesConstant.NonShopID;
				}else {
					shopid = queryVO.getShopid();
				}
			}
			
			Long roleId = null;
			boolean hasPrivilege = false;
			Roles roleVO = null;
			for(int i=0;i<keys.length;i++){
				roleId = new Long(keys[i]);
				roleVO = roleDao.selectByPK(roleId);
				hasPrivilege = RolesConstant.hasPrivilegeToAddRole(roleVO.getCode(), authories);
				if(!hasPrivilege){
				   throw new LogicException(ExceptionConstant.Error_Unauthorized);
				}
			    UserRole vo = new UserRole();	
				vo.setRoleid(roleId);
				vo.setUserid(userId);
				vo.setShopid(shopid);
				vo.setSyscode(syscode);
				list.add(vo);
			}
			num = dao.deleteBatchByPK(list);
		}

		// set messag page parameters.
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(num), "BizKey");
		if(num>0)
			return this.listUserRole(mapping, form, request, response);
		else
		   return mapping.findForward("failure");
	}
	
	/** 
	 * Method 为角色新增权限时的弹出权限列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listRescsForRoleAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserRoleForm theForm = (UserRoleForm) form;
		UserinfoSession sessioninfo = getLoginInfo();
		Long userid = getLoginInfo().getUserid();
		String syscode = theForm.getSyscode();
		if(syscode==null || syscode.trim().length()<1){
			syscode = SysparamConstant.syscode;
		}
		UserRoleQuery queryVO = theForm.getQueryVO();
		Long localeid = getLoginInfo().getLocaleid();
		queryVO.setLocaleid(localeid);
		queryVO.setSyscode(syscode);
		queryVO.setUserid(userid);
		
//		 查询的是privilege
		ResourcesDao dao = BOFactory.getResourcesDao();
		List list = null;
		// 如果是super user,则查询该菜单下的所有权限
		if(sessioninfo.isRootLogin()){
		    list = dao.selectRescsForRoleAdd(queryVO);
		}else{ // 否则，查询该用户在该菜单下所具有的所有权限
			list = dao.selectRescsForRoleAddRestrict(queryVO);
		}
		theForm.setRescList(list);
		
		return mapping.findForward("listRescsForRoleAdd");
	}
	
}
