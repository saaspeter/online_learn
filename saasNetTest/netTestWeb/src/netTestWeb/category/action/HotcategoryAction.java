
package netTestWeb.category.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.category.form.HotcategoryForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logic.HotcategoryLogic;
import platform.logic.ProductcategoryLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Hotcategory;
import platform.vo.Productcategory;

import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.exception.LogicException;


public class HotcategoryAction extends BaseAction {
	
	static Logger log = Logger.getLogger(HotcategoryAction.class.getName());
	// hotcategory的管理在各个子系统web中，因此syscode由各个子系统自己加载
	static String SYSCODE = SysparamConstant.syscode;
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listHotcategory".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("magHotcategory".equals(myaction)) {
			myforward = listMag(mapping, actionform, request,response);
		} else if ("addHotcategory".equals(myaction)) {
			myforward = addPage(mapping, actionform, request,response);
		} else if ("saveHotcategory".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("delHotcategory".equals(myaction)) {
			myforward = deleteHotcategory(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HotcategoryForm theForm = (HotcategoryForm) form;
		Long localeid = getLoginInfo(true).getUseLocaleid(request);
		String rtnStr = "list";
		
		HotcategoryLogic logic = BOFactory_Platform.getHotcategoryLogic();
		List<Hotcategory> list = logic.qryHotcategory(localeid, SYSCODE, false);
		theForm.setHotcateList(list);
		return mapping.findForward(rtnStr);
	}
	

	public ActionForward listMag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HotcategoryForm theForm = (HotcategoryForm) form;
		Long localeid = theForm.getVo().getLocaleid();
		if(localeid==null){
			localeid = getLoginInfo(true).getLocaleid();
			theForm.getVo().setLocaleid(localeid);
		}
		String rtnStr = "listMag";
		
		HotcategoryLogic logic = BOFactory_Platform.getHotcategoryLogic();
		List<Hotcategory> list = logic.qryHotcategory(localeid, SYSCODE, true);
		theForm.setHotcateList(list);
		return mapping.findForward(rtnStr);
	}
	
	public ActionForward addPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HotcategoryForm theForm = (HotcategoryForm) form;
		Hotcategory vo = theForm.getVo();
		Long localeid = vo.getLocaleid();
		Long pid = vo.getPid();
		if(localeid==null){
			localeid = getLoginInfo().getLocaleid();
			vo.setLocaleid(localeid);
		}		
		ProductcategoryLogic logic = BOFactory_Platform.getProductcategoryLogic();
		if(pid==null){
			Productcategory cateVO = logic.getSystemTopCategory(SYSCODE, localeid);
			vo.setCategoryname(cateVO.getCategoryname());
			vo.setPid(cateVO.getCategoryid());
		}
		theForm.setVo(vo);
		return mapping.findForward("addpage");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.AddSuccess;
		HotcategoryForm theForm = (HotcategoryForm) form;
		Hotcategory vo = theForm.getVo();
		int scope = theForm.getScope();
		if(scope==2){
		   vo.setLocaleid(null);  // 对系统所有的locale都起作用
		}
        if(vo.getPid()==null){
        	vo.setPid(CommonConstant.TreeTopnodePid);
        }
        vo.setSyscode(SYSCODE);
        HotcategoryLogic logic = BOFactory_Platform.getHotcategoryLogic();
		try {
			logic.addHotCategory(vo);
		} catch (LogicException e) {
			messCode = e.getMessage();
		}
		// set messag page parameters.
		request.setAttribute("pageAction", "closeDiv");
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
	
	
	public ActionForward deleteHotcategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String messCode = KeyInMemoryConstant.deleteSuccess;
		HotcategoryForm theForm = (HotcategoryForm) form;
		Hotcategory vo = theForm.getVo();
		int scope = theForm.getScope();
		if(scope==2){
		   vo.setLocaleid(null);  // 对系统所有的locale都起作用
		}
        
        HotcategoryLogic logic = BOFactory_Platform.getHotcategoryLogic();
		try {
			logic.delHotCategory(vo.getCategoryid(), vo.getLocaleid(), SYSCODE);
		} catch (LogicException e) {
			messCode = e.getMessage();
		}
		// set messag page parameters.
		request.setAttribute("pageAction", "closeDiv");
		super.setMessagePage(request,theForm, messCode, "1","BizKey");
		return mapping.findForward("toUrl");
	}
		
}
