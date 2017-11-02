package netTestWeb.security.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTest.bean.BOFactory;
import netTest.common.session.LoginInfoEdu;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.security.form.LoginForm;
import commonWeb.base.RequestresponseUtil;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import commonTool.constant.CommonConstant;
import commonTool.util.DateUtil;
import commonWeb.base.BaseActionBase;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.dao.SuperuserDao;
import commonWeb.security.dao.impl.SuperuserDaoImpl;


public class LoginAction extends BaseAction{

	static Logger log = Logger.getLogger(LoginAction.class.getName());
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

            //ActionMessages errors = new ActionMessages();
            LoginForm loginForm = (LoginForm) form;
        	LoginInfoEdu loginfo = getLoginInfo();
            if(loginfo!=null){
            	// 查看是否是超级管理员
            	String ip = RequestresponseUtil.getClintIP(request);
        	    String syscode = loginfo.getSyscode();
        	    Date nowdate = DateUtil.getInstance().getNowtime_GLNZ();
        	    SuperuserDao dao = SuperuserDaoImpl.getInstance();
        	    int result = dao.validateSuperAdmin(loginfo.getLoginname(), ip, syscode, nowdate);
        	    if(result==1){
        	    	loginfo.setRootLogin(true);
        	    }

            	// 设置国家语言信息
            	BaseActionBase baseaction = new BaseActionBase();
            	baseaction.setLocaleDetail(loginfo, request);
                loginForm.setLocaleid(loginfo.getLocaleid());
                
                // 设置用户的商店列表和产品列表和权限等信息
    			BOFactory.getUserLoginSessionLogic().loadUserProduct(loginfo, null);
                
                loginForm.setShopid(loginfo.getShopid());
                request.setAttribute("shopid", loginfo.getShopid());
                // 把loginInfo放到session中
                request.getSession(true).setAttribute(KeyInMemoryConstant.CustomerInfoKey, loginfo);
            }
            
            String forwardurl = "userhomepage";
            // if admin, set admin page
            if(loginfo.isRootLogin()||RolesConstant.hasRole(RolesConstant.ROLE_SysAdmin, loginfo.getAuthorities())
            	 ||RolesConstant.hasRole(RolesConstant.ROLE_BizDataAdmin, loginfo.getAuthorities()))
            {
            	forwardurl = "superadminpage";
            }
            
            // 如果是social登录的，并且当用户没有设置login name，则跳转到设置页面
            Short stagestatus = loginfo.getStagestatus();
            if(CommonConstant.isSocialLogin(loginfo.getLogintype())
            	&& CustomerConstant.StageStatus_notSetLoginName.equals(stagestatus)){
            	request.setAttribute("userid", loginfo.getUserid().toString());
            	request.setAttribute("loginname", loginfo.getLoginname());
            	request.setAttribute("email", loginfo.getEmail());
            	request.setAttribute("logintype", loginfo.getLogintype().toString());
            	forwardurl = "activeregisterpage";
            	// TODO 改变用户的token为anonymous token, 当用户重新设置了用户名密码后再重新设为登陆
            }
            return mapping.findForward(forwardurl);
    }

}
