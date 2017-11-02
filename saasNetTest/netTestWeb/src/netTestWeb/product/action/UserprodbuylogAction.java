
package netTestWeb.product.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.product.constant.UserProdBuyLogConstant;
import netTest.product.dao.UserprodbuylogDao;
import netTest.product.dto.UserprodbuylogQuery;
import netTest.product.vo.Userprodbuylog;
import netTestWeb.base.BaseAction;
import netTestWeb.product.form.UserprodbuylogForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;

import commonTool.base.Page;


public class UserprodbuylogAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserprodbuylogAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		if ("".equals(myaction)) { 
			myforward = mapping.findForward("failure");
		} else if ("listmyendprodlog".equals(myaction)) {
			myforward = listMyEndprodLog(mapping, actionform, request,response);
		} else if ("listuserendprodlog".equals(myaction)) {
			myforward = listUserEndprodLog(mapping, actionform, request,response);
		} else {
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
//	public ActionForward list(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		UserprodbuylogForm theForm = (UserprodbuylogForm) form;
//		UserprodbuylogQuery queryVO = theForm.getQueryVO();
//		try {
//			UserprodbuylogDao dao = BOFactory.getUserprodbuylogDao();
//			Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
//			this.setPage(request, page);
//		} catch (Exception e) {
//			log.error("----error in Class:UserprodbuylogAction Method:list", e);
//			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
//		}
//		
//		return mapping.findForward("list");
//	}
	
	/** 
	 * list出所有的用户到期被删除产品日志，管理员使用
	 */
	public ActionForward listUserEndprodLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserprodbuylogForm theForm = (UserprodbuylogForm) form;
		Long shopid = getLoginInfo().getShopid();
		UserprodbuylogQuery queryVO = theForm.getQueryVO();
		queryVO.setShopid(shopid);
		queryVO.setAction(UserProdBuyLogConstant.ActionType_Deleted);
		UserprodbuylogDao dao = BOFactory.getUserprodbuylogDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		
		if(page!=null && page.getList()!=null && page.getList().size()>0){
			Userprodbuylog vo = null;
			List<Long> useridList = new ArrayList<Long>();
			for(int i=0;i<page.getList().size();i++){
				vo = (Userprodbuylog)page.getList().get(i);
				useridList.add(vo.getUserid());
			}
			UsershopLogic usershoplogic = BOFactory_Platform.getUsershopLogic();
			Map<Long,String> nameMap = usershoplogic.qryUsernameByIdList(queryVO.getShopid(), useridList);
			for(int i=0;i<page.getList().size();i++){
				vo = (Userprodbuylog)page.getList().get(i);
				vo.setDisplayname(nameMap.get(vo.getUserid()));
			}
		}
		
		this.setPage(request, page);
		return mapping.findForward("listpage");
	}
	
	/** 
	 * list出用户自己的到期被删除产品日志，学员自己使用
	 */
	public ActionForward listMyEndprodLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserprodbuylogForm theForm = (UserprodbuylogForm) form;
		LoginInfoEdu loginfo = getLoginInfo();
		Long userid = loginfo.getUserid();
		Long shopid = loginfo.getShopid();
		UserprodbuylogQuery queryVO = theForm.getQueryVO();
		// 处理切换用户的商店
		switchShop(shopid, queryVO, false);
		queryVO.setUserid(userid);
		queryVO.setAction(UserProdBuyLogConstant.ActionType_Deleted);
		UserprodbuylogDao dao = BOFactory.getUserprodbuylogDao();
		Page page = dao.selectByVOPage(queryVO, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		return mapping.findForward("listpage");
	}
		
}
