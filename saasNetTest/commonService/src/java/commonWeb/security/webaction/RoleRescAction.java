
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
import commonTool.constant.SysparamConstant;
import commonTool.util.AssertUtil;
import commonWeb.base.BaseAction;
import commonWeb.base.KeyInMemoryConstant;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dao.RoleRescDao;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dto.ResourcesQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Resources;
import commonWeb.security.vo.RoleResc;
import commonWeb.security.vo.Roles;
import commonWeb.security.webform.RoleRescForm;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class RoleRescAction extends BaseAction {
	
	static Logger log = Logger.getLogger(RoleRescAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listRoleResc".equals(myaction)) {
			myforward = listRoleResc(mapping, actionform, request,response);
		} else if ("saveRoleResc".equals(myaction)) {
			myforward = saveRoleResc(mapping, actionform, request,response);
		} else if ("deleteRoleResc".equals(myaction)) {
			myforward = deleteRoleResc(mapping, actionform, request,response);
		} else if ("showThisMenu".equals(myaction)) {
			myforward = showThisMenu(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	
	/** 
	 * Method listRoleUser：列出该角色下的资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward listRoleResc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RoleRescForm theForm = (RoleRescForm) form;
		Long localeid = getLoginInfo().getLocaleid();
		Roles rolesVO = theForm.getRolesVO();
		ResourcesQuery queryVO = theForm.getQueryVO();

		ResourcesDao rescDao = BOFactory.getResourcesDao();
		RolesDao roleDao = BOFactory.getRolesDao();
		if(queryVO.getPid()!=null&&rolesVO.getId()!=null){
			RoleRescDao rolerescDao = BOFactory.getRoleRescDao();
			int count = rolerescDao.countExistPriv(queryVO.getPid(), rolesVO.getId());
			if(count>0){  // 角色包括这个菜单
				theForm.setIncludeMenuCheck("1");
			}
		}
		
		rolesVO = roleDao.selectValueByPK(rolesVO.getId(),localeid);
		AssertUtil.assertNotNull(rolesVO, "commonWeb.pages.security.roleNotExist");
		theForm.setRolesVO(rolesVO);
		queryVO.setRoleId(rolesVO.getId());
		queryVO.setRescfold(Resources.RescFold_Priv);
		queryVO.setVisible(Resources.Visible_visible);
		List list = rescDao.selectByRoleId(queryVO);
		theForm.setRescList(list);

		return mapping.findForward("listRoleResc");
	}
		
	/** 
	 * Method saveRoleResc：保存一个角色下的所选资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveRoleResc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RoleRescForm theForm = (RoleRescForm) form;
		ResourcesQuery queryVO = theForm.getQueryVO();
		String rescIds = theForm.getRescIds();
		Long[] rescIdsArr = null;
		Roles rolesVO = theForm.getRolesVO();
		Long roleId = rolesVO.getId();
		int num = 0;

    	RoleRescDao dao = BOFactory.getRoleRescDao();
		if(rescIds!=null&&rescIds.trim().length()>0){
			StringTokenizer tokens = new StringTokenizer(rescIds,",");
			if(tokens!=null){
				rescIdsArr = new Long[tokens.countTokens()];
			   for(int i=0;tokens.hasMoreTokens();i++){
				   rescIdsArr[i] = new Long(tokens.nextToken());
			   }
			}
			num = dao.insertMoreBatch(rescIdsArr,roleId,SysparamConstant.syscode);
			// 检查这些资源的上级菜单是否被添加到该资源中了，如果没有则默认添加上所属菜单
			if(num>0&&queryVO.getPid()!=null){
				int count = dao.countExistPriv(queryVO.getPid(), roleId);
				if(count<1){
					RoleResc record = new RoleResc();
					record.setRoleId(roleId);
					record.setRescId(queryVO.getPid());
					dao.insertMoreBatch(new Long[]{queryVO.getPid()},roleId,SysparamConstant.syscode);
				}
			}
		}

		// set messag page parameters.
		String messCode = KeyInMemoryConstant.modifySuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(num), "BizKey");
		if(num>0)
		   return this.listRoleResc(mapping, form, request, response);
		else
		   return mapping.findForward("failure");
	}
	
	/** 
	 * Method deleteRoleResc:删除角色下的选定资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteRoleResc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RoleRescForm theForm = (RoleRescForm) form;
		String[] keys = theForm.getKeys();
		Roles rolesVO = theForm.getRolesVO();
		Long roleId = rolesVO.getId();
		int num = 0;

    	List list = new ArrayList();
    	RoleRescDao dao = BOFactory.getRoleRescDao();
		if(keys!=null&&keys.length>0){
			for(int i=0;i<keys.length;i++){
				RoleResc vo = new RoleResc();	
				vo.setRoleId(roleId);
				vo.setRescId(new Long(keys[i]));
				list.add(vo);
			}
			num = dao.deleteBatchByPK(list, SysparamConstant.syscode);
		}

		// set messag page parameters.
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(num), "BizKey");
		if(num>0)
			return this.listRoleResc(mapping, form, request, response);
		else
		   return mapping.findForward("failure");
	}
	
	
	/** 
	 * Method showThisMenu:处理界面上的是否显示该菜单选项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward showThisMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RoleRescForm theForm = (RoleRescForm) form;
		String includeMenuCheck = theForm.getIncludeMenuCheck();
		Long roleId = theForm.getRolesVO().getId();
		Long menuId = theForm.getQueryVO().getPid();

    	RoleRescDao dao = BOFactory.getRoleRescDao();
    	if("1".equals(includeMenuCheck)){
    		dao.insertMoreBatch(new Long[]{menuId},roleId,SysparamConstant.syscode);
    	}else if("0".equals(includeMenuCheck)){
    		dao.delMenuAndResc(menuId, roleId);
    	}
    	
		return this.listRoleResc(mapping, theForm, request, response);
	}
	
		
}
