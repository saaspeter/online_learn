
package netTestWeb.platform.news.action;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.common.session.LoginInfoEdu;
import netTest.product.vo.ProductMini;
import netTest.util.ResourceBundleUtil;
import netTestWeb.base.BaseAction;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.news.form.UsernotificationForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import platform.daoImpl.UserDaoImpl;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Shop;
import platform.vo.User;

import commonTool.biz.dao.UsernotificationDao;
import commonTool.biz.logic.UsernotificationLogic;
//import commonTool.biz.logicImpl.BOFactory;
import commonTool.biz.logicImpl.UsernotificationLogicImpl;
import commonTool.biz.vo.Usernotification;
import commonTool.constant.CommonConstant;
import commonTool.constant.SysparamConstant;
import commonTool.constant.UsernotificationConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonTool.util.StringUtil;
import commonWeb.security.constant.RolesConstant;

public class UsernotificationAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UsernotificationAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("".equals(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("listnotification".equals(myaction)) {
			myforward = list(mapping, actionform, request,response);
		} else if ("addnotification".equals(myaction)) {
			myforward = add(mapping, actionform, request,response);
		} else if ("savenotification".equals(myaction)) {
			myforward = save(mapping, actionform, request,response);
		} else if ("readnotification".equals(myaction)) {
			myforward = read(mapping, actionform, request,response);
		} else if ("deletenotification".equals(myaction)) {
			myforward = delete(mapping, actionform, request,response);
		} else {
			myforward = mapping.findForward("failure");
		}
		return myforward;  
	}
	

	private ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long userid = getLoginInfo().getUserid();
		Locale locale = getLoginInfo().getLocale(request);
		UsernotificationForm theForm = (UsernotificationForm)form;
		Short isread = theForm.getVo().getIsread();
		UsernotificationDao notifydao = commonTool.biz.logicImpl.BOFactory.getUsernotificationDao();
		List<Usernotification> notifyList = (List<Usernotification>)notifydao.selectByUser(userid, theForm.getShowusertype(), isread);
		notifyList = UsernotificationLogicImpl.getInstance().shownotificationList(notifyList, theForm.getShowusertype(), locale, ResourceBundleUtil.bundleName_struts_BizKey);
		theForm.setVolist(notifyList);
		
		return mapping.findForward("list");
	}
	
	private ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UsernotificationForm theForm = (UsernotificationForm)form;
		List<Long> touseridList = theForm.getTouseridArr();
		String touserloginame = theForm.getTouserloginame();
		String objectadmintype = theForm.getObjectadmintype();
		if((touseridList==null||touseridList.size()<1)
				&&objectadmintype!=null&&objectadmintype.trim().length()>0
				&&theForm.getObjectid()!=null)
		{
			objectadmintype = objectadmintype.trim();
			if(Shop.ObjectType.equals(objectadmintype)){
				String roleidStr = commonWeb.security.logic.BOFactory.getRolesDao().selRoleidStrByCode(new String[]{RolesConstant.ROLE_ShopCreator, RolesConstant.ROLE_ShopAdmin}, SysparamConstant.syscode);
				touseridList = commonWeb.security.logic.BOFactory.getUserRoleDao().selectUserByShopRole(theForm.getObjectid(), roleidStr, SysparamConstant.syscode);
			}else if(ProductMini.ObjectType.equals(objectadmintype)){
				touseridList = netTest.bean.BOFactory.getUserproductDao().selectProductAdminUserId(theForm.getObjectid());
			}
		}
		if(touseridList!=null&&touseridList.size()>0&&(touserloginame==null||touserloginame.trim().length()<1)){
			Map<Long, String> userMap = BOFactory_Platform.getUsershopLogic().qryUsernameByIdList(null, touseridList);
			StringBuffer buffer = new StringBuffer();
			for(Map.Entry<Long, String> enry : userMap.entrySet()){
				buffer.append(enry.getValue()).append(";");
			}
			
			touserloginame = buffer.toString();
			theForm.setTouserloginame(touserloginame);
		}
		return mapping.findForward("addpage");
	}
	
	private ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		LoginInfoEdu loginfo = getLoginInfo();	
		UsernotificationForm theForm = (UsernotificationForm)form;
		Usernotification vo = theForm.getVo();
		String touserloginame = theForm.getTouserloginame().trim();
		String touseridStr = theForm.getTouseridStr();
		Map<Long, String> paramap = null;
		String result = String.valueOf(CommonConstant.success);
		String tip = "";
		User uservo = null;
		try {
			vo.setCreatetime(DateUtil.getInstance().getNowtime_GLNZ());
			vo.setIsread(UsernotificationConstant.IsRead_NotRead);
			vo.setCreatorname(CustomerConstant.combineDisplayname(loginfo.getDisplayname(), loginfo.getLoginname()));
			vo.setCreatorid(loginfo.getUserid());
			vo.setOpenlinktype(UsernotificationConstant.OpenlinkType_NewDiv);
			if(vo.getNotifytype()==null){
				vo.setNotifytype(UsernotificationConstant.NotifyType_UserLetter);
			}
			
			if(touseridStr!=null && touseridStr.trim().length()>0){
				paramap = BOFactory_Platform.getUsershopLogic().qryUsernameByIdList(null, theForm.getTouseridArr());
			}else if(touserloginame!=null && touserloginame.trim().length()>0){
				String[] loginameArr = StringUtil.splitString(touserloginame, ";");
				if(loginameArr!=null){
					paramap = new HashMap<Long, String>();
					String loginametemp = null;
					for(int i=0; i<loginameArr.length; i++){
						loginametemp = CustomerConstant.decombineDisplayname(loginameArr[i]);
						if(loginametemp!=null && loginametemp.length()>0){
							uservo = UserDaoImpl.getInstance().selectByLogin(loginametemp);
							AssertUtil.assertNotNull(uservo, ExceptionConstantBase.Error_User_NotExists);
							paramap.put(new Long(uservo.getUserid()), loginametemp);
						}
					}
				}
			}
			
			UsernotificationLogic logic = commonTool.biz.logicImpl.BOFactory.getUsernotificationLogic();
			int num = logic.batchSend(vo, paramap);
			if(num<1){
				throw new LogicException(ExceptionConstantBase.Error_User_NotExists);
			}
			tip = "UsernotificationAction.save.successfulTip";
		} catch (LogicException e) {
			result = String.valueOf(CommonConstant.fail);
			tip = e.getMessage();
			log.error("error in Usernotification.save1", e);
		} catch (Exception e){
			result = String.valueOf(CommonConstant.fail);
			tip = ExceptionConstant.Error_System;
			log.error("error in Usernotification.save2", e);
		}
		// 得到错误信息
		tip = getResources(request, "BizKey").getMessage(tip);
		this.writeAjaxRtn(result, null, tip, response);
		return null;
	}
	
	/** 
	 * 更新该消息为已读消息
	 */
	private ActionForward read(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UsernotificationForm theForm = (UsernotificationForm) form;
		Usernotification vo = theForm.getVo();
		AssertUtil.assertNotNull(vo.getNotifyid(), ExceptionConstant.Error_Need_Paramter);
		String forwardurl = null;
		String tourl = "toUrl";

		LoginInfoEdu loginfo = getLoginInfo();
		Long sessUserID = loginfo.getUserid();
		Locale locale = loginfo.getLocale(request);
		UsernotificationDao dao = commonTool.biz.logicImpl.BOFactory.getUsernotificationDao();
		vo = dao.selectByPK(vo.getNotifyid());
		AssertUtil.assertNotNull(vo, null);
		UsernotificationLogicImpl.getInstance().shownotification(vo, locale, ResourceBundleUtil.bundleName_struts_BizKey);
		theForm.setVo(vo);
		
		if(vo.getLinkurl()!=null && vo.getLinkurl().trim().length()>0){
		    forwardurl = netTestWeb.base.WebConstant.WebContext+vo.getLinkurl();
		}else {
			if(UsernotificationConstant.NotifyType_UserLetter.equals(vo.getNotifytype())
					&& sessUserID.equals(vo.getTouserid())){
				theForm.setCanreply(1);
			}
			tourl = "showdetailpage";
		}
		// 当是接收者查看未读消息时，更新消息未读状态
		if(UsernotificationConstant.IsRead_NotRead.equals(vo.getIsread())
			&& sessUserID.equals(vo.getTouserid())){
		    dao.updateReadByPK(UsernotificationConstant.IsRead_Read, vo.getNotifyid());
		}
        if(forwardurl!=null){
		    this.setMessUrlPage(request, forwardurl, null, null, WebConstant.Resource_BaseKey_Boundle);
        }
		return mapping.findForward(tourl);
	}
	
	/** 
	 * 删除用户消息
	 */
	private ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		UsernotificationForm theForm = (UsernotificationForm) form;
		String[] keys = theForm.getKeys();
		String result = String.valueOf(CommonConstant.success);
		Locale locale = null;
		String info = "";
		String tip = "";
		try {
			LoginInfoEdu loginfo = getLoginInfo();
			Long sessUserID = loginfo.getUserid();
			locale = loginfo.getLocale();
			UsernotificationDao dao = commonTool.biz.logicImpl.BOFactory.getUsernotificationDao();
			// 检查keys是否是用户自己的消息
			Usernotification tempvo = null;
			for(String id : keys){
				tempvo = dao.selectByPK(new Long(id));
				if(tempvo!=null && !tempvo.getTouserid().equals(sessUserID)
						&& !tempvo.getCreatorid().equals(sessUserID)){
					throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
				}
			}
			int rows = dao.deleteBatchByPK(keys);
			info = String.valueOf(rows);
		}catch (Exception e) {
			result = String.valueOf(CommonConstant.fail);
			if(e instanceof LogicException){
				tip = e.getMessage();
			}else {
				tip = ExceptionConstant.Error_System;
			}
			tip = getResources(request, "BizKey").getMessage(locale, tip);
			log.error("error in Usernotification.delete", e);
		}
		this.writeAjaxRtn(result, info, tip, response);
		return null;
	}
	
}
