package netTestWeb.platform.systemset.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.platform.systemset.form.SysconstitemForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import platform.exception.ExceptionConstant;
import commonTool.base.Page;
import commonTool.biz.dao.SysconstDao;
import commonTool.biz.dao.SysconstitemDao;
import commonTool.biz.daoImpl.SysconstDaoImpl;
import commonTool.biz.dto.SysconstitemQuery;
import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.vo.Sysconst;
import commonTool.biz.vo.Sysconstitem;
import commonTool.util.AssertUtil;
import commonTool.util.ObjUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysconstitemAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysconstitemAction.class.getName());
	
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
		Long localeid = getLoginInfo().getLocaleid();
		SysconstitemForm theForm = (SysconstitemForm) form;
		SysconstitemQuery queryVO = theForm.getQueryVO();
		String code = queryVO.getSysconstcode();
        if(queryVO.getLocaleid()==null||queryVO.getLocaleid().longValue()<=0)
        {
        	queryVO.setLocaleid(localeid);
        }

		// 查询所属常量的名字
		SysconstDao constDao = SysconstDaoImpl.getInstance();
		Sysconst sysconst = constDao.selectByConstCode(code, queryVO.getLocaleid());
		if(sysconst!=null){
		   theForm.setConstname(sysconst.getName());
		}
		//
		queryVO.setOrder_By_Clause("a.orderNO");
		SysconstitemDao dao = BOFactory.getSysconstitemDao();
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
		SysconstitemForm theForm = (SysconstitemForm) form;
		Sysconstitem vo = theForm.getVo();
		String messCode = KeyInMemoryConstant.modifySuccess;
		SysconstitemDao dao = BOFactory.getSysconstitemDao();
		boolean exist = false;
		if(vo!=null&&(vo.getItemid()==null||vo.getItemid()==0)){
			messCode = KeyInMemoryConstant.AddSuccess;
		    // 对于新增，查看value是否重复
			exist = dao.existValue(vo.getItemid(),vo.getSysconstcode(), vo.getValue());
			if(exist){
				super.setMessagePage(request,theForm,"platform.sysConstitem.duplicateItemValue",null,"platform.pagemess");
 		   	    return this.addPage(mapping, theForm, request, response);
			}
		}else {
			// 对于编辑，当value的值有所变化时，需要验证新输入的value值是否有重复
			Sysconstitem oldVO = dao.selectByPK(vo.getItemid());
			if(!oldVO.getValue().equals(vo.getValue())){
				exist = dao.existValue(vo.getItemid(),vo.getSysconstcode(), vo.getValue());
				if(exist){
					super.setMessagePage(request,theForm,"platform.sysConstitem.duplicateItemValue",null,"platform.pagemess");
					SysconstitemQuery queryVO = theForm.getQueryVO();
				   	ObjUtil.copyProperties(queryVO, vo);
				   	return this.editPage(mapping, theForm, request, response);
				}
			}
		}

		dao.save(vo);
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
		SysconstitemForm theForm = (SysconstitemForm) form;
		SysconstitemQuery queryVO = theForm.getQueryVO();
		String code = queryVO.getSysconstcode();
		Long localeid = getLoginInfo().getLocaleid();
		Long pk = queryVO.getItemid();
		if(queryVO.getLocaleid()==null)
			queryVO.setLocaleid(localeid);

		AssertUtil.assertNotNull(pk, null);
		SysconstitemDao dao = BOFactory.getSysconstitemDao();
		Sysconstitem vo = dao.selectByPK(pk,localeid);
		AssertUtil.assertNotNull(pk, ExceptionConstant.Error_Record_Not_Exists);
        // 查询所属常量的名字
		SysconstDao constDao = SysconstDaoImpl.getInstance();
		Sysconst sysconst = constDao.selectByConstCode(code, queryVO.getLocaleid());
		if(sysconst!=null)
		   theForm.setConstname(sysconst.getName());
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
		SysconstitemForm theForm = (SysconstitemForm) form;
		String code = theForm.getVo().getSysconstcode();
		Long localeid = getLoginInfo().getLocaleid();

        // 查询所属常量的名字
		SysconstDao constDao = SysconstDaoImpl.getInstance();
		Sysconst sysconst = constDao.selectByConstCode(code, localeid);
		if(sysconst!=null)
		   theForm.setConstname(sysconst.getName());

		return mapping.findForward("addPage");
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
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysconstitemForm theForm = (SysconstitemForm) form;
		String[] keys = theForm.getKeys();
		int rows = 0;
		
		SysconstitemDao dao = BOFactory.getSysconstitemDao();
		rows = dao.deleteBatchByPK(keys);
		
		String messCode = KeyInMemoryConstant.deleteSuccess;
		super.setMessagePage(request,theForm, messCode, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
	
}
