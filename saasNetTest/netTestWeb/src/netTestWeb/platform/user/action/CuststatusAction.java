
package netTestWeb.platform.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.user.form.CuststatusForm;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import platform.dao.CuststatusDao;
import platform.dto.CuststatusQuery;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Custstatus;
import commonTool.base.Page;
import commonTool.util.AssertUtil;


public class CuststatusAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CuststatusAction.class.getName());
	
	/** 
	 * Method list
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		CuststatusForm theForm = (CuststatusForm) form;
        AssertUtil.assertNotNull(theForm.getUserid(), null);
		
		CuststatusDao dao = BOFactory_Platform.getCuststatusDao();
		List list = dao.selectByUserid(theForm.getUserid());
		theForm.setDatalist(list);
		
		return mapping.findForward("list");
	}
		
}
