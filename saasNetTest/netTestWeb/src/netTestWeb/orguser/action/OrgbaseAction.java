
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

import commonTool.util.AssertUtil;


public class OrgbaseAction extends BaseAction {
	
	static Logger log = Logger.getLogger(OrgbaseAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();
		OrgbaseForm theform = (OrgbaseForm)actionform;
		
		if ("magorgmain".equals(myaction)) {
			theform.setUrlType(1);
			theform.setMagtype("org");
			myforward = orgAndUserMain(mapping, actionform, request,response);
		} else if ("magorgusermain".equals(myaction)) {
			theform.setUrlType(1);
			theform.setMagtype("user");
			myforward = orgAndUserMain(mapping, actionform, request,response);
		} else if ("selectorgusermain".equals(myaction)) {
			theform.setUrlType(2);
			myforward = orgAndUserMain(mapping, actionform, request,response);
		} else if ("geneorgtreexml".equals(myaction)) {
			myforward = geneTreeXml(mapping, actionform, request,response);
		} else if ("selectorgtree".equals(myaction)) {
			theform.setUrlType(2);
			myforward = toTree(mapping, actionform, request,response);
		} else if ("orgtreemag".equals(myaction)) {
			theform.setUrlType(1);
			myforward = toTree(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}
	
	
	/** 
	 * 进入单位树的页面，初始化登录者所在单位
	 */
	private ActionForward orgAndUserMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
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
	 */
	private ActionForward geneTreeXml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginInfoEdu loginInfo = getLoginInfo();
		OrgbaseForm theForm = (OrgbaseForm) form;
		Orgbase vo = theForm.getVo();
		Long shopid = loginInfo.getShopid();
		String treeAction = null;
		OrgLogic logic = BOFactory.getOrgLogic();
        String url = "/org/geneorgtreexml.do?vo.pid=";
		String treeXml = logic.createOrgTreeXml(vo.getPid(),shopid,treeAction, url, null);
        request.setAttribute(KeyInMemoryConstant.treeXmlKey, treeXml);
		return mapping.findForward("treeXml");
	}
	
	/** 
	 * initiate tree page and show tree
	 * 在此要保证操作人只属于一个单位，如果是多个单位的系管，只能在session中存放一个单位信息。
	 * 用于专项显示数的页面
	 */
	private ActionForward toTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
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
		if(orgRange==1&&urlType==2){
			OrgLogic logic = BOFactory.getOrgLogic();
			vo = logic.qryTopDept(shopid);
		}else{
			OrgbaseDao dao = BOFactory.getOrgbaseDao();
			vo = dao.selectByPK(orgid);
		}
		AssertUtil.assertNotNull(vo, null);
		theForm.setVo(vo);
		return mapping.findForward(toUrl);
	}
	
}
