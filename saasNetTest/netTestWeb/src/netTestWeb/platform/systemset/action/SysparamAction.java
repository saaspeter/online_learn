
package netTestWeb.platform.systemset.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.systemset.form.SysparamForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import commonTool.biz.dao.SysparamDao;
import commonTool.biz.dto.SysparamQuery;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysparam;
import commonTool.util.AssertUtil;

/** 
 * MyEclipse Struts
 */
public class SysparamAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysparamAction.class.getName());
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysparamForm theForm = (SysparamForm) form;
		SysparamQuery queryVO = theForm.getQueryVO();
		queryVO.setOrder_By_Clause("typecode");
		
		SysparamDao dao = BOFactory.getSysparamDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	/** 
	 * Method save：只支持修改，不支持新增，如果新增应该是通过db脚本
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysparamForm theForm = (SysparamForm) form;
		Sysparam vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;

		SysparamDao dao = BOFactory.getSysparamDao();
		dao.updateByPK(vo);
		//TODO flush param cache
		
		// set messag page parameters.	
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysparamForm theForm = (SysparamForm) form;
		SysparamQuery queryVO = theForm.getQueryVO();
		String pk = queryVO.getCode();
		
		AssertUtil.assertNotNull(pk, null);
		SysparamDao dao = BOFactory.getSysparamDao();
		Sysparam vo = dao.selectByPK(pk);
		theForm.setVo(vo);
		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("addEditPage");
		else
		   return mapping.findForward("viewPage");
	}
	
	/** 
	 * Method add: 不支持新增
	 */
//	public ActionForward addPage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		return mapping.findForward("addEditPage");
//	}
	
	/** 
	 * Method delete：不支持
	 */
//	public ActionForward delete(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		SysparamForm theForm = (SysparamForm) form;
//		String[] keys = theForm.getKeys();
//		int rows = 0;
//
//		SysparamDao dao = BOFactory.getSysparamDao();
//		rows = dao.deleteBatchByPK(keys);
//
//		String messCode = KeyInMemoryConstant.deleteSuccess;
//		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
//		return mapping.findForward("toUrl");
//	}
	
}
