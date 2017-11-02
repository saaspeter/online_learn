
package commonWeb.security.webaction;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.AssertUtil;
import commonTool.util.TreeUtil;
import commonWeb.base.BaseAction;
import commonWeb.base.KeyInMemoryConstant;
import commonWeb.security.dao.ResourcesDao;
import commonWeb.security.dto.ResourcesQuery;
import commonWeb.security.logic.BOFactory;
import commonWeb.security.vo.Resources;
import commonWeb.security.webform.ResourcesForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class ResourcesAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ResourcesAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		ResourcesForm theForm = (ResourcesForm) actionform;

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listResources".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("saveResource".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("editResourcePage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewResourcePage".equals(myaction)) {
			theForm.setEditType(CommonConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("addResourcePage".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("deleteResource".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else if ("toRescfoldTreePage".equals(myaction)) {
			myforward = toRescfoldTreePage(mapping, actionform, request,response);
		} else if ("toRescfoldTreeXml".equals(myaction)) {
			myforward = geneTreeXml(mapping, actionform, request,response);
		} 
		else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ResourcesForm theForm = (ResourcesForm) form;
		ResourcesQuery queryVO = theForm.getQueryVO();
		Long localeid = getLoginInfo().getLocaleid();
		queryVO.setLocaleid(localeid);
		queryVO.setSyscode(SysparamConstant.syscode);
		// 查询的是privilege
		//queryVO.setRescfold(Resources.RescFold_Priv);

		if(queryVO.getSearchListType()==null){
			queryVO.setSearchListType(1);
		}else if(queryVO.getSearchListType().intValue()==2
				&&queryVO.getPid()!=null){
			queryVO.setPath(String.valueOf(queryVO.getPid().intValue()));
		}
		try {
			ResourcesDao dao = BOFactory.getResourcesDao();
			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(), getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:ResourcesAction Method:list", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
	    return mapping.findForward("list");
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
			HttpServletRequest request, HttpServletResponse response) 
	{
		Long localeid = getLoginInfo().getLocaleid();
		ResourcesForm theForm = (ResourcesForm) form;
		Resources vo = theForm.getVo();
		vo.setLocaleid(localeid);
		String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getId()==null||vo.getId()==0))
        	messCode = KeyInMemoryConstant.AddSuccess;
        // 如果是菜单类型，则插入-1
        if(Resources.ResType_MENU.equals(vo.getResType())){
        	vo.setResString("-1");
        }
        if(vo.getSyscode()==null || vo.getSyscode().trim().length()<1){
        	vo.setSyscode(SysparamConstant.syscode);
        }
        ResourcesDao dao = BOFactory.getResourcesDao();
		dao.save(vo);

		// set messag page parameters.
		ResourcesQuery queryVO = theForm.getQueryVO();
		queryVO.setSyscode(vo.getSyscode());
		queryVO.setPid(vo.getPid());
		queryVO.setSearchListType(1); // 仅仅查询本菜单下的权限
		theForm.setQueryVO(queryVO);
		super.setMessagePage(request,theForm, messCode, "1", "BizKey");
		return this.list(mapping, form, request, response);
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
		ResourcesForm theForm = (ResourcesForm) form;
		Resources vo = theForm.getVo();
		Long pk = vo.getId();
		
		Long localeid = getLoginInfo().getLocaleid();
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Need_Paramter);
		ResourcesDao dao = BOFactory.getResourcesDao();
		vo = dao.selectByPK(pk,localeid);
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
			HttpServletRequest request, HttpServletResponse response) {
		Long localeid = getLoginInfo().getLocaleid();
		ResourcesForm theForm = (ResourcesForm) form;
		theForm.setEditType(CommonConstant.editType_add);
		Resources vo = theForm.getVo();
		vo.setRescfold(Resources.RescFold_Priv);
		if(vo.getPid()!=null && !vo.getPid().equals(CommonConstant.NullPK_Parameter)){
			ResourcesDao dao = BOFactory.getResourcesDao();
			Resources voTemp = dao.selectByPK(vo.getPid(),localeid);
			if(voTemp==null){
				throw new NoSuchRecordException();
			}
			vo.setPidName(voTemp.getName());
		}
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
			HttpServletRequest request, HttpServletResponse response) {
		ResourcesForm theForm = (ResourcesForm) form;
		String[] keys = theForm.getKeys();
		Long localeid = null;
		String arg = null;
		String messCode = KeyInMemoryConstant.deleteSuccess;
		try {
			ResourcesDao dao = BOFactory.getResourcesDao();
			Map map = dao.deleteBatchByPK(keys,localeid);
			if(map!=null&&map.get("result")!=null){
				arg = (String)map.get("info");
				if(!"1".equals((String)map.get("result")))
				   messCode = ExceptionConstant.Error_Record_BeenReference;
			}else{
				messCode = ExceptionConstant.Error_System;
			}
		} catch (Exception e) {
			log.error("----error in Class:ResourcesAction Method:delete", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.delete");
		}
		String[] args = null;
		if(arg!=null&&arg.trim().length()>0)
			args = new String[]{arg};
		super.setMessage(request, messCode, args, "BizKey");
	
		return this.list(mapping, form, request, response);
	}
		
	
	public ActionForward toRescfoldTreePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ResourcesForm theForm = (ResourcesForm) form;
//		Long localeid = getLoginInfo().getLocaleid();

		return mapping.findForward("rescTree");
	}
	
	/** 
	 * 生成权限资源目录树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward geneTreeXml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ResourcesForm theForm = (ResourcesForm) form;
		ResourcesQuery queryVO = theForm.getQueryVO();
		Long pid = queryVO.getPid();
		Long localeid = getLoginInfo().getLocaleid();
		try {
			ResourcesDao dao = BOFactory.getResourcesDao();
			
			List list = dao.qryRescfoldTree(SysparamConstant.syscode, pid, localeid);
			String treeXml = TreeUtil.getInstance().geneTreeXml(list);
            request.setAttribute(KeyInMemoryConstant.treeXmlKey, treeXml);
		} catch (Exception e) {
			log.error("----error in Class:ResourcesAction Method:geneTreeXml", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.geneTreeXml");
		}
		return mapping.findForward("treeXml");
	}
	
}
