
package netTestWeb.platform.systemset.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netTestWeb.base.BaseAction;
import netTestWeb.base.KeyInMemoryConstant;
import netTestWeb.base.WebConstant;
import netTestWeb.platform.systemset.form.SysfunctionitemForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import platform.constant.SysfunctionitemConstant;
import platform.dao.SysfunctionitemDao;
import platform.dto.SysfunctionitemQuery;
import platform.exception.ExceptionConstant;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Sysfunctionitem;

import commonTool.base.Page;
import commonTool.constant.CommonConstant;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class SysfunctionitemAction extends BaseAction {
	
	static Logger log = Logger.getLogger(SysfunctionitemAction.class.getName());
	
	/** 
	 * Method 
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysfunctionitemForm theForm = (SysfunctionitemForm) form;
		SysfunctionitemQuery voQuery = theForm.getQueryVO();
		Locale locale = getLoginInfo().getLocale();
		request.setAttribute(KeyInMemoryConstant.SessionKey_LocaleUserSelect, locale);
		theForm.setSysList(CommonConstant.qrySystemLabel(locale));

		SysfunctionitemDao dao = BOFactory_Platform.getSysfunctionitemDao();
		Page page = dao.selectByVOPage(voQuery, getCurrPageNumber(request), getPageSize(request) , getTotalNumber(request));
		this.setPage(request, page);
		
		return mapping.findForward("list");
	}
	
	
}
