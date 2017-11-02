
package netTestWeb.orguser.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.orguser.dao.OrgbaseDao;
import netTest.orguser.dto.OrgbaseQuery;
import netTest.orguser.logic.OrgLogic;
import netTest.orguser.vo.Orgbase;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.orguser.form.OrgbaseForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import commonTool.base.Page;


public class CopyOfOrgbaseAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CopyOfOrgbaseAction.class.getName());
	
	/** 
	 * Method list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		OrgbaseForm theForm = (OrgbaseForm) form;
		OrgbaseQuery voQuery = theForm.getQueryVO();
		try {
			OrgbaseDao dao = BOFactory.getOrgbaseDao();
			Page page = dao.selectByVOPage(voQuery, getCurrPageNumber(request), getPageSize(request), getTotalNumber(request));
			this.setPage(request, page);
		} catch (Exception e) {
			log.error("----error in Class:OrgbaseAction Method:list", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.list");
		}
		return mapping.findForward("list");
	}
	
	
	/** 
	 * 进入单位树的页面，初始化登录者所在单位
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward orgAndUserMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LoginInfoEdu loginInfo = getLoginInfo();
		Long orgid = loginInfo.getOrgbaseid();
		//Long orgid = new Long(2);
		OrgbaseForm theForm = (OrgbaseForm) form;
		OrgbaseQuery queryVO = theForm.getQueryVO();
		String tourl = "orgAndUserMain";
		if(theForm.getUrlType()==2)
			tourl = "selectUserMain";
		//TODO 如果queryVO.pid不为空，则需要验证传入的pid是不是用户所在的单位orgid的下级单位或者是orgid本身，
		// 如果不是orgid的下级则认为没有权限访问传入的这个单位的数据
		if(queryVO.getPid()==null)
            queryVO.setPid(orgid);
		theForm.setQueryVO(queryVO);
		
		return mapping.findForward(tourl);
	}
	
	
	/** 
	 * Method geneTreeXml:generate the tree xml file,used to show the tree
	 * 用于生成树机构
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward geneTreeXml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LoginInfoEdu loginInfo = getLoginInfo();
		OrgbaseForm theForm = (OrgbaseForm) form;
		Orgbase vo = theForm.getVo();
		Long shopid = loginInfo.getShopid();
		String treeAction = null;
		try {
			OrgLogic logic = BOFactory.getOrgLogic();
            String url = "/org/orgbase.do?method=geneTreeXml"
                          +"&amp;vo.pid=";
			String treeXml = logic.createOrgTreeXml(vo.getPid(),shopid,treeAction, url, null);
            request.setAttribute(KeyInMemoryConstant.treeXmlKey, treeXml);
		} catch (Exception e) {
			log.error("----error in Class:OrgbaseAction Method:geneTreeXml", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.geneTreeXml");
		}
		return mapping.findForward("treeXml");
	}
	
	/** 
	 * Method toTree:initiate tree page and show tree
	 * 在此要保证操作人只属于一个单位，如果是多个单位的系管，只能在session中存放一个单位信息。
	 * 用于专项显示数的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LoginInfoEdu loginInfo = getLoginInfo();
		Long orgid = loginInfo.getOrgbaseid();
		Long shopid = loginInfo.getShopid();
		OrgbaseForm theForm = (OrgbaseForm) form;
		OrgbaseQuery queryVO = theForm.getQueryVO();
		if(queryVO.getPid()!=null){
			//TODO 如果queryVO.pid不为空，则需要验证传入的pid是不是用户所在的单位orgid的下级单位或者是orgid本身，
			// 如果不是orgid的下级则认为没有权限访问传入的这个单位的数据
			orgid = queryVO.getPid();
		}
		Orgbase vo = null;
		int urlType = theForm.getUrlType();
		int orgRange = theForm.getOrgRange();
		String toUrl = "toTree";
		if(urlType==2)
			toUrl = "selectTree";
		try {
			if(orgRange==1&&urlType==2){
				OrgLogic logic = BOFactory.getOrgLogic();
				vo = logic.qryTopDept(shopid);
			}else{
				OrgbaseDao dao = BOFactory.getOrgbaseDao();
				vo = dao.selectByPK(orgid);
			}
			theForm.setVo(vo);
		} catch (Exception e) {
			log.error("----error in Class:OrgbaseAction Method:toTree", e);
			return this.forwardErrorPage(mapping, request, e, "commonWeb.java.commonaction.errors.geneTreeXml");
		}
	    if(vo==null)
	    	return this.forwardErrorPage(mapping, request, null, "commonWeb.java.commonaction.errors.editPage.noSuchRecord");
		return mapping.findForward(toUrl);
	}
	
}
