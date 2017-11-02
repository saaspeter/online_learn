package netTestWeb.orguser.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.orguser.constant.OrgbaseConstant;
import netTest.orguser.dao.DeptinfoDao;
import netTest.orguser.dao.OrgbaseDao;
import netTest.orguser.dto.DeptinfoQuery;
import netTest.orguser.logic.OrgLogic;
import netTest.orguser.vo.Deptinfo;
import netTest.orguser.vo.Orgbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.orguser.form.DeptinfoForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

/** 
 * 
 */
public class DeptinfoAction extends BaseAction {
	
	static Logger log = Logger.getLogger(DeptinfoAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		DeptinfoForm theForm = (DeptinfoForm) actionform;
		
		if ("listdeptinfo".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("adddeptinfo".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("editdeptinfo".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_edit);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("viewdeptinfo".equals(myaction)) {
			theForm.setEditType(WebConstant.editType_view);
			myforward = editPage(mapping, actionform, request,response);
		} else if ("savedeptinfo".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("deldeptinfo".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	

	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu logininfo = getLoginInfo();
		Long shopid = logininfo.getShopid();
		//Long shopid = new Long(1);
		DeptinfoForm theForm = (DeptinfoForm) form;
		// 设置locale
		theForm.setLocale(logininfo.getLocale());
		DeptinfoQuery queryVO = theForm.getQueryVO();
		Long orgbaseid = queryVO.getPid();
		queryVO.setShopid(shopid);

		DeptinfoDao dao = BOFactory.getDeptinfoDao();
		OrgbaseDao orgDao = BOFactory.getOrgbaseDao();
		// 查找本单位信息
		Orgbase orgbase = orgDao.selectByPK(orgbaseid);
		if(orgbase!=null){
		  theForm.setOrgbase(orgbase);
		}
		// 查找本单位的下级单位列表
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
		this.setPage(request, page);

		return mapping.findForward("list");
	}
	

	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu loginInfo = getLoginInfo();
		Long shopid = loginInfo.getShopid();
		//Long shopid = new Long(1);
		DeptinfoForm theForm = (DeptinfoForm) form;
		Deptinfo vo = theForm.getVo();
		if(vo!=null)
			vo.setShopid(shopid);
        // 设置动作的类型，默认为修改操作
        String messCode = KeyInMemoryConstant.modifySuccess;
        if(vo!=null&&(vo.getOrgbaseid()==null||vo.getOrgbaseid()==0)){
        	messCode = KeyInMemoryConstant.AddSuccess;
        	vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
        }
        DeptinfoDao dao = BOFactory.getDeptinfoDao();
		dao.save(vo);
		// set messag page parameters.
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	

	private ActionForward editPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DeptinfoForm theForm = (DeptinfoForm) form;
		DeptinfoQuery queryVO = theForm.getQueryVO();
		Long pk = queryVO.getOrgbaseid();
		AssertUtil.assertNotNull(pk, null);
		OrgLogic logic = BOFactory.getOrgLogic();
		Deptinfo vo = logic.getDeptinfoByPK(pk);
		theForm.setVo(vo);
		if(theForm.getEditType()==WebConstant.editType_edit)
		   return mapping.findForward("editPage");
		else
		   return mapping.findForward("viewPage");
	}
	

	private ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		DeptinfoForm theForm = (DeptinfoForm) form;
		DeptinfoQuery queryVO = theForm.getQueryVO();
		Deptinfo vo = theForm.getVo();
		Long pid = queryVO.getPid();
		if(pid==null||pid.longValue()<=0){
			pid = OrgbaseConstant.OrgPidTop;
		    queryVO.setPid(pid);	
		}
	    // 设置单位名称
		DeptinfoDao dao = BOFactory.getDeptinfoDao();
		Deptinfo deptinfo = dao.selectByPK(pid);
		if(deptinfo!=null){
		   queryVO.setPidName(deptinfo.getOrgname());
		   // 设置单位级别
		   vo.setPath(deptinfo.getPath());
		   vo.setOrglevel(deptinfo.getOrglevel());
		}
		return mapping.findForward("addPage");
	}
	

	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DeptinfoForm theForm = (DeptinfoForm) form;
		//String[] keys = theForm.getKeys();
		Long orgid = theForm.getVo().getOrgbaseid();
		AssertUtil.assertNotNull(orgid, null);
		int rows = -1;
		DeptinfoDao dao = BOFactory.getDeptinfoDao();
		//rows = dao.deleteBatchByPK(keys);
		rows = dao.deleteByPK(orgid);
		super.setMessagePage(request,theForm,KeyInMemoryConstant.deleteSuccess, String.valueOf(rows),"BizKey");
		return mapping.findForward("toUrl");
	}
			
}
