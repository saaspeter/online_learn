package netTestWeb.index.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTest.exam.vo.Testinfo;
import netTest.product.vo.Productbase;
import netTestWeb.base.BaseAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import platform.vo.Shop;
import commonTool.constant.CommonConstant;


public class SearchAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SearchAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		
		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("tohome".equals(myaction)) {
			myforward = tohome(mapping, actionform, request,response);
		} else if ("commonsearch".equals(myaction)) {
			myforward = search(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	/**
	 * 主页home
	 */
	public ActionForward tohome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String localeid = request.getParameter("localeid");
		if(localeid!=null && localeid.trim().length()>0){
			switchUseLocale(new Long(localeid), request);
		}
		return mapping.findForward("defaultpage");
	}
	
	/** 
	 * 公用的搜索方法
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ActionForward forward = null;
		String searchType = request.getParameter("searchType");
		String searchInput = request.getParameter("searchInput");
		String categoryname = request.getParameter("categoryname");
		String categoryid = request.getParameter("categoryid");
		String commonStr = "&newscategoryid="+CommonConstant.SearchTabPK+"&categoryid="+categoryid+"&categoryname="+categoryname;
		if(searchType!=null && searchInput!=null 
				&& searchInput.trim().length()>0){
			if(searchType.equals(Productbase.ObjectType)){
				forward = new ActionForward("/shopping/searchProductList.do?"
						+"&productname="+searchInput
						+commonStr);
			}else if(searchType.equals(Testinfo.ObjectType)){
				forward = new ActionForward("/index/listIndexOpenTest.do?"
						+"&queryVO.testname="+searchInput
						+commonStr);
			}else if(searchType.equals(Shop.ObjectType)){
				forward = new ActionForward("/shop/listShopIndex.do?"
						+"&searchtext="+searchInput
						+commonStr);
			}
		}
		if(forward==null){
			forward = mapping.findForward("defaultpage");
		}
		return forward;
	}
	
}
