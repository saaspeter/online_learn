
package netTestWeb.user.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTest.exam.constant.TestuserConstant;
import netTest.exam.dao.TestuserDao;
import netTest.exam.dto.TestuserQuery;
import netTest.product.dao.UserproductDao;
import netTest.product.vo.Userproduct;
import netTest.util.ResourceBundleUtil;
import netTestWeb.base.BaseAction;
import netTestWeb.user.form.UserServiceForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.social.constant.SocialoathtokenConstant;

import commonTool.base.Page;
import commonTool.biz.dao.UsernotificationDao;
import commonTool.biz.logicImpl.UsernotificationLogicImpl;
import commonTool.biz.vo.Usernotification;
import commonTool.constant.UsernotificationConstant;
import commonTool.util.StringUtil;


public class UserServiceAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserServiceAction.class.getName());
		
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("tologin".equals(myaction)) {
			myforward = tologin(mapping, actionform, request,response);
		} else if ("myhomepage".equals(myaction)) {
			myforward = myHomePage(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;  
	}
	
	private ActionForward tologin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String forwardurl = "login";
		String urltype = request.getParameter("urltype");
		if("simple".equals(urltype)){
			forwardurl = "loginsimple";
		}
		String messcode = request.getParameter("messcode");
		UserServiceForm theForm = (UserServiceForm)form;
		Object messObj = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String message = "";
		Locale locale = request.getLocale();
		if(messObj!=null){
			message = ((java.lang.Exception)messObj).getMessage();
			request.getSession().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		}else if(messcode!=null && messcode.trim().length()>0){
			if("-1".equals(messcode)){
				messcode = "login.error.loginExpire";
			}else if("-2".equals(messcode)){
				messcode = "login.error.oneAccountInManyClient";
			}
			message = getResources(request, "BizKey").getMessage(locale, messcode);
		}
		// get oauth social client_id
		String clientid_google = SocialoathtokenConstant.getOauthAppID(SocialoathtokenConstant.ServiceType_Google);
		theForm.setClientid_google(clientid_google);
		
		theForm.setMessage(message);
		return mapping.findForward(forwardurl);
	}
	
	/**
	 * 用户的主页内容, 如最近学习的课程, 要参加的考试等
	 */
	private ActionForward myHomePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoginInfoEdu loginfo = getLoginInfo();
		Long userid = loginfo.getUserid();
		Locale locale = loginfo.getLocale(request);
		UserServiceForm theForm = (UserServiceForm)form;
		// 查询用户总的学习课程数量情况
		int[] prodNumArr = BOFactory.getUserproductLogic().getUserprodNumber(userid, null);
		theForm.setMylearnprodnum(prodNumArr[0]);
		theForm.setMyadminprodnum(prodNumArr[1]);
		
		// get latest learning product
		UserproductDao userproddao = BOFactory.getUserproductDao();
		List<Userproduct> userproductlist = userproddao.selMostAccessProds(userid);
		theForm.setProductList(userproductlist);
		if(userproductlist==null || userproductlist.size()<1){
			Page userproductPage = BOFactory.getUserproductLogic().listMyProduct(userid, null, null, 
					                    getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		    if(userproductPage==null || userproductPage.getTotalNumber()==0){
		    	theForm.setHasproduct(false);
		    }
		}
		// get upcoming test
		TestuserQuery testuserqueryVO = new TestuserQuery();
		testuserqueryVO.setUserid(userid);
		// 复合查询，查询结果中有testinfo的信息
		testuserqueryVO.setQueryType(2);
		testuserqueryVO.setOrderColumn("teststartdate");
		testuserqueryVO.setOrderDirect(2); // 降序排列
		testuserqueryVO.setRoughteststatus(TestuserConstant.RoughTestStatus_upcoming);
		TestuserDao testuserdao = BOFactory.getTestuserDao();
		List testList = testuserdao.selectByVO(testuserqueryVO);
		theForm.setTestList(testList);
		// get notification
		UsernotificationDao notifydao = commonTool.biz.logicImpl.BOFactory.getUsernotificationDao();
		List<Usernotification> notifyList = (List<Usernotification>)notifydao.selectByUser(userid, 0, UsernotificationConstant.IsRead_NotRead);
		notifyList = UsernotificationLogicImpl.getInstance().shownotificationList(notifyList, 0, locale, ResourceBundleUtil.bundleName_struts_BizKey);
		theForm.setNotifyList(notifyList);
		return mapping.findForward("homepage");
	}
	
}
