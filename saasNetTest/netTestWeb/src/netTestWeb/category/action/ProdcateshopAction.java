
package netTestWeb.category.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.common.session.LoginInfoEdu;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.category.form.ProdcateshopForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.logicImpl.BOFactory_Platform;
import platform.shop.logic.ProdcateshopLogic;
import platform.shop.vo.Prodcateshop;

import commonTool.constant.SysparamConstant;
import commonTool.util.TreeUtil;

public class ProdcateshopAction extends BaseAction {
	
	static Logger log = Logger.getLogger(ProdcateshopAction.class.getName());
	static String SYSCODE = SysparamConstant.syscode;
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("shopprodcatetreepage".equals(myaction)) {
			myforward = shopProdcateTreePage(mapping, actionform, request,response);
		} else if ("shoprodcateTree".equals(myaction)) {
			myforward = geneTreeXml(mapping, actionform, request,response);
		} else if ("saveProdcateshop".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("deleteProdcateshop".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/**
	 * 显示商店产品目录树页面，非xml页面
	 */
	public ActionForward shopProdcateTreePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu userinfo = getLoginInfo();
		Long localeid = userinfo.getUseLocaleid(request);
		ProdcateshopForm theForm = (ProdcateshopForm) form;
		Long shopid = theForm.getShopid();
		if(shopid==null){
			shopid = userinfo.getShopid();
			theForm.setShopid(shopid);
		}
        String rtnpage = "treePage";
        if(theForm.getUrltype()==1){
        	rtnpage = "selectPage";
        }
		// 临时只为netTest服务
		ProdcateshopLogic logic = BOFactory_Platform.getProdcateshopLogic();
        List list = logic.getShopCateChildNodes(null, shopid, localeid, SYSCODE);
        if(list==null){
        	theForm.setChildsize(0);
        }else {
        	theForm.setChildsize(list.size());
        }
		return mapping.findForward(rtnpage);
	}
	
	/** 
	 * Method geneTreeXml:generate the tree xml file,used to show the tree
	 */
	public ActionForward geneTreeXml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		Long localeid = getLoginInfo(true).getUseLocaleid(request);
		ProdcateshopForm theForm = (ProdcateshopForm) form;
		Prodcateshop vo = theForm.getVo();
		Long shopid = vo.getShopid();
		if(shopid==null){
			shopid = getLoginInfo(true).getShopid();
		}
		// 临时只为netTest服务
		try {
            ProdcateshopLogic logic = BOFactory_Platform.getProdcateshopLogic();
            List list = logic.getShopCateChildNodes(vo.getPid(), shopid, localeid, SYSCODE);
			String treeXml = TreeUtil.getInstance().geneTreeXml(list);
			request.setAttribute(KeyInMemoryConstant.treeXmlKey, treeXml);
		} catch (Exception e) {
			log.error("----error in Class:ProdcateshopAction Method:geneTreeXml", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.geneTreeXml");
		}
		
		return mapping.findForward("treeXml");
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
		String messCode = KeyInMemoryConstant.AddSuccess;
		
		Long shopid = getLoginInfo().getShopid();
		ProdcateshopForm theForm = (ProdcateshopForm) form;
		String categoryidStr = theForm.getCategoryidStr();
		// 暂时设置考试系统netTest
		ProdcateshopLogic logic = BOFactory_Platform.getProdcateshopLogic();
		logic.addCategory(categoryidStr, shopid, SYSCODE);
		super.setMessagePage(request,theForm, messCode, "","BizKey");
		return mapping.findForward("treePage");
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
		Long shopid = getLoginInfo().getShopid();
		ProdcateshopForm theForm = (ProdcateshopForm) form;
		Long categoryid = theForm.getVo().getCategoryid();

		String messCode = KeyInMemoryConstant.deleteSuccess;
		// 暂时设置考试系统netTest
		ProdcateshopLogic logic = BOFactory_Platform.getProdcateshopLogic();
		int delRtn = logic.delCate(categoryid, shopid, SYSCODE);
		if(delRtn==-2){
			messCode = "ProdcateshopLogic.delete.hasSubCate";
		}else if(delRtn==-3){
			messCode = "ProdcateshopLogic.delete.hasProduct";
		}
		
		super.setMessagePage(request,theForm, messCode, String.valueOf(delRtn),"BizKey");
		return mapping.findForward("treePage");
	}
	
}
