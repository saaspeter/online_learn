package platformWeb.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.CustomerConstant;
import platformWeb.form.LoginForm;

public class ToLoginAction extends Action

{

	static Logger log = Logger.getLogger(ToLoginAction.class.getName());
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    	LoginForm loginForm = (LoginForm)form;
    	List userTypeList = CustomerConstant.getUserTypeLabels(request.getLocale());
    	loginForm.setUserType(CustomerConstant.UserType_user);
    	loginForm.setUserTypeList(userTypeList);
        
    	return mapping.findForward("login");
    }
    
}
