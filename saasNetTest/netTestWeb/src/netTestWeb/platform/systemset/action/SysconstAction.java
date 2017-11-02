
package netTestWeb.platform.systemset.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.platform.systemset.form.SysconstForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.exception.ExceptionConstant;

import commonTool.base.Page;
import commonTool.biz.dao.SysconstDao;
import commonTool.biz.dto.SysconstQuery;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysconst;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.util.AssertUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstAction.class.getName());
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstForm theForm = (SysconstForm) form;
		SysconstQuery queryVO = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();
		Long localeid = getLoginInfo().getLocaleid();
		theForm.setLocale(locale);
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }
        if(queryVO.getSyscode()==null||queryVO.getSyscode().trim().length()<1){
        	queryVO.setSyscode(CommonConstant.SysCode_Platform);
        }

		SysconstDao dao = BOFactory.getSysconstDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);
		
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstForm theForm = (SysconstForm) form;
		Sysconst vo = theForm.getVo();
		SysconstDao dao = BOFactory.getSysconstDao();
		String messCode = KeyInMemoryConstant.modifySuccess;
		if(vo!=null&&(vo.getSysconstid()==null||vo.getSysconstid()==0)){
			messCode = KeyInMemoryConstant.AddSuccess;
			Sysconst constVO = dao.selectByConstCode(vo.getSysconstcode());
			// 如果是新增，先检查常量编码是否唯一
			if(constVO!=null){
			   	super.setMessagePage(request,theForm,"platform.sysConst.duplicateSysConstCode",null,"BizKey");
			   	return this.addPage(mapping, theForm, request, response);
			}
		}
		dao.save(vo);

		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return this.list(mapping, theForm, request, response);
	}
	
	/** 
	 * Method edit
	 */
	public ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstForm theForm = (SysconstForm) form;
		Long pk = theForm.getVo().getSysconstid();
		Long localeid = getLoginInfo().getLocaleid();
		Locale locale = getLoginInfo().getLocale();
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));
		
		AssertUtil.assertNotNull(pk, null);
		SysconstDao dao = BOFactory.getSysconstDao();
		Sysconst vo = dao.selectByPK(pk,localeid);
		AssertUtil.assertNotNull(vo, ExceptionConstant.Error_Record_Not_Exists);
		theForm.setVo(vo);

        return mapping.findForward("editPage");
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
		SysconstForm theForm = (SysconstForm) form;
		Locale locale = getLoginInfo().getLocale();
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));

		return mapping.findForward("addPage");
	}
	
	/** 
	 * Method delete
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstForm theForm = (SysconstForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;

		SysconstDao dao = BOFactory.getSysconstDao();
		rows = dao.deleteBatchByPK(keys);

		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return this.list(mapping, theForm, request, response);
	}
	
}
