package platformWeb.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springside.components.acegi.dao.SuperuserDao;
import org.springside.components.acegi.vo.UserinfoSession;

import platform.constant.CustomerConstant;
import platformWeb.base.BaseAction;
import platformWeb.form.LoginForm;

import commonTool.util.DateUtil;

public class LoginAction extends BaseAction

{

	static Logger log = Logger.getLogger(LoginAction.class.getName());
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

            ActionMessages errors = new ActionMessages();
            LoginForm loginForm = (LoginForm) form;
            UserinfoSession loginfo = getLoginInfo();
            if(loginfo==null||loginfo.isAnonymous()){
            	List userTypeList = CustomerConstant.getUserTypeLabels(request.getLocale());
            	loginForm.setUserTypeList(userTypeList);
            	errors.add("login.notExistUserErr", new ActionMessage("login.notExistUserErr"));
            	if (!errors.isEmpty()) {
            		this.saveMessages(request, errors);;
                }
            	return mapping.getInputForward();
            } else{
            	// 设置用户的国家语言
            	this.setLocaleDetail(loginfo, request);
            	
            	Short staus = loginfo.getStatus();
            	// 根据用户的状态stauts决定该用户是否还有效，是否需要转向
            	// TODO
            	
            	// 查看是否是超级管理员
            	String ip = ((HttpServletRequest)request).getRemoteAddr();
        	    String syscode = loginfo.getSyscode();
        	    Date nowdate = DateUtil.getInstance().getNowtime_GLNZ();
        	    SuperuserDao dao = org.springside.components.acegi.logicImpl.BOFactory.getSuperuserDao();
        	    int result = dao.validateSuperAdmin(loginfo.getLoginname(), ip, syscode, nowdate);
        	    if(result==1){
        	    	loginfo.setRootLogin(true);
        	    }
            	
            	Short userType = loginfo.getUsertype();            	
            	if(userType.equals(CustomerConstant.UserType_tenant))
            		return mapping.findForward("tenantAdmin");
            	else if(userType.equals(CustomerConstant.UserType_user))
            		return mapping.findForward("userAdmin");
            	else 
            		return null;
            }
            
            
            
    }
        
}
