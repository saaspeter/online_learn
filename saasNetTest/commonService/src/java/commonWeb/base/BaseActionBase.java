package commonWeb.base;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import commonTool.base.Page;
import commonTool.biz.logicImpl.I18nLogicImpl;
import commonTool.constant.CommonConstant;
import commonTool.exception.NoLoginException;
import commonTool.exception.UserLoginException;
import commonTool.util.ParseXmlDoc;
import commonTool.util.StringUtil;
import commonWeb.security.authentication.UserinfoSession;

/**
 * 功能：Action基础类
 */
public class BaseActionBase extends DispatchAction
{

    /**
     * 如果用户的localeid为空，则根据用户的request设置用户的国家语言，
     * 如果用户request中的locale在系统中没有，则使用系统默认的国家语言
     * @param userinfo
     * @param request
     */
	public void setLocaleDetail(UserinfoSession userinfo,HttpServletRequest request){
		Long localeid = userinfo.getLocaleid();
		Locale locale = null;
		String jsSuffix = null;
		// 如果用户设置的locale为空，则使用request中locale
		if(localeid==null){
			Long uselocaleid = (Long)userinfo.getUseLocaleid(request);
			if(uselocaleid!=null){
				localeid = uselocaleid;
				locale = (Locale)userinfo.getUseLocale(request);
				jsSuffix = ((String)userinfo.getUseJsSuffix(request));
			}else if(request.getLocale()!=null){
				locale = request.getLocale();
				// 根据request中的locale查询localeid, 如果不是被支持的locale则使用系统默认的localeid
				localeid = I18nLogicImpl.getLocaleid(locale);
				// 在此重新查询一次Locale，因为如果用户request使用的Locale不在系统有的Locale,则使用系统默认的Locale
				locale = I18nLogicImpl.getLocale(localeid); 
				jsSuffix = "_"+locale.getLanguage()+"_"+locale.getCountry();
			}
			
			userinfo.setLocaleid(localeid);
			userinfo.setLocale(locale);
			userinfo.setJsSuffix(jsSuffix);
		}else {
			locale = userinfo.getLocale();
		}
		// 设置struts中的国家语言
		request.getSession().setAttribute(Globals.LOCALE_KEY, locale);
		userinfo.setUseLocale(locale, request);
		userinfo.setUseLocaleid(localeid, request);
	}
	
	/**
	 * 切换用户使用的locale,可以用于主界面上的site站点切换
	 * @param uselocaleid
	 * @param request
	 */
	public void switchUseLocale(Long uselocaleid, HttpServletRequest request){
		if(uselocaleid==null)
			return;
		// 根据localeid得到locale,如果不是系统支持的，则用系统默认的locale
		Locale locale = I18nLogicImpl.getLocale(uselocaleid);
		uselocaleid = I18nLogicImpl.getLocaleid(locale);
		// 设置struts中的默认的用户自己选择的国家语言
		HttpSession session = ((HttpServletRequest)request).getSession();
		session.setAttribute(KeyInMemoryConstant.SessionKey_LocaleidUserSelect, uselocaleid);
		session.setAttribute(KeyInMemoryConstant.SessionKey_LocaleUserSelect, locale);
		UserinfoSession usersession = getLoginInfo(true);
		usersession.setLocale(locale);
		usersession.setLocaleid(uselocaleid);
		// 设置struts中的国家语言
		session.setAttribute(Globals.LOCALE_KEY, locale);
	}
	
    /**
     * 功能： 获取当前页码
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public int getCurrPageNumber(HttpServletRequest request)
    {
        int pageIndex = 1;
        String strPage = request.getParameter("pageIndex");
        if (strPage != null && strPage.trim().length() > 0)
        {
            try {
				pageIndex = Integer.parseInt(strPage);
				if(pageIndex<1){
					pageIndex = CommonConstant.PAGESIZE;
				}
			} catch (NumberFormatException e) {
				// do nothing
			}
        }
        return pageIndex;
    }
    
    public Integer getTotalNumber(HttpServletRequest request)
    {
        Integer totalDataNumber = null;
        String totalDataNumberStr = request.getParameter("totalDataNumber");
        if (totalDataNumberStr != null && totalDataNumberStr.trim().length() > 0)
        {
        	totalDataNumber = Integer.parseInt(totalDataNumberStr.trim());
        }
        return totalDataNumber;
    }

    /**
     * 功能：每页显示的条数
     * 
     * @return
     */
    public int getPageSize(HttpServletRequest request)
    {
        int pageSize = CommonConstant.PAGESIZE;
        String pageSizeStr = request.getParameter("pageSize");
        if (pageSizeStr != null && pageSizeStr.trim().length() > 0)
        {
        	try {
				pageSize = Integer.parseInt(pageSizeStr);
				if(pageSize<1){
					pageSize = CommonConstant.PAGESIZE;
				}
			} catch (NumberFormatException e) {
				// do nothing
			}
        }
        return pageSize;
    }

    /**
     * 功能： 设置分页对象
     * 
     * @param request
     * @param page
     * @return
     */
    public Page setPage(HttpServletRequest request, Page page)
    {
    	if(page!=null){
            request.setAttribute("page", page);
            request.setAttribute("pageList", page.getList());
     	}
         return page;
    }
    
    /**
     * 把结果和结果信息用Document形式写出，以便页面ajax解析
     * @param result
     * @param info: 用逗号隔开的不能删除的key的字符串
     * @param response
     * @throws Exception
     */
    public void writeAjaxRtn(String result,String info, String tip,HttpServletResponse response) throws Exception{
		Document resDoc = ParseXmlDoc.parseResult(result, info, tip);
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(resDoc, out);
    }
    
    /**
     * 把结果和结果信息用Document形式写出，以便页面ajax解析
     * @param result
     * @param info: 用逗号隔开的不能删除的key的字符串
     * @param response
     * @throws Exception
     */
    public void writeAjaxRtn(String[][] resultArr, HttpServletResponse response) throws Exception{
		Document resDoc = ParseXmlDoc.parseResult(resultArr);
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(resDoc, out);
    }

    /**
     * 功能： 跳转到异常处理页面
     * @param mapping
     * @param request
     * @param e 其它程序throw出来的异常对象
     * @param errorKey 需要显示在错误页面的信息资源key
     * @return
     */
    public ActionForward forwardErrorPage(ActionMapping mapping, HttpServletRequest request, Exception e, String errorKey)
    {
        if(errorKey!=null||errorKey.trim().length()>0){
        	ActionMessages errors = new ActionMessages();
        	ActionMessage newError = new ActionMessage(errorKey);
        	errors.add(ActionMessages.GLOBAL_MESSAGE, newError);
            this.saveErrors(request, errors);
        }
        return mapping.findForward("errorPage");
    }
    
    /**
     * 设置在信息页面需要显示的链接地址和信息，使用的是FormBean中的backUrl
     * @param request
     * @param form
     * @param actionType
     * @param n
     */
    public void setMessagePage(HttpServletRequest request,BaseFormBase form,String messCode,String disMessArg0Key,String bundle)
    {
 	    String backUrl = "";
	    String backUrlEncode = "";

    	// 设置列表和新增的链接地址
    	if(form!=null){
    	   int hasUrlSubmit = form.getHasUrlSubmit();
     	   backUrl = form.getBackUrl();
    	   backUrlEncode = form.getBackUrlEncode();
    	   if(backUrl!=null&&backUrl.trim().length()>0&&hasUrlSubmit==1){//需要判断是否有提交backUrlEncoder,如果没有提交则不能自动转向页面
    	       if(messCode!=null&&messCode.trim().length()>0)
    	          backUrl += StringUtil.getUrlDeli(backUrl)+KeyInMemConstBase.DisMessKey+"="+messCode;
    	       if(disMessArg0Key!=null&&disMessArg0Key.trim().length()>0)
    	          backUrl += StringUtil.getUrlDeli(backUrl)+KeyInMemConstBase.DisMessArg0Key+"="+disMessArg0Key;
    	       if(bundle!=null&&bundle.trim().length()>0)
    	    	  backUrl += StringUtil.getUrlDeli(backUrl)+"bundle="+bundle;
    	       if(backUrlEncode!=null&&backUrlEncode.trim().length()>0)
    	          backUrl += StringUtil.getUrlDeli(backUrl)+"backUrlEncode="+backUrlEncode;
    	    }else
    	    	backUrl = null;
    	}
    	if(request!=null){
    	   if(backUrl!=null&&backUrl.trim().length()>0){
    	      request.setAttribute("url", backUrl);
    	   }
        // 放在request中已备转向message页面时使用
    	   request.setAttribute(KeyInMemConstBase.DisMessKey, messCode);
		   request.setAttribute(KeyInMemConstBase.DisMessArg0Key, disMessArg0Key);
		   request.setAttribute("bundle", bundle);
    	}
    }
    
    /**
     * 设置在信息页面需要显示的链接地址和信息
     * @param request
     * @param form
     * @param actionType
     * @param n
     */
    public void setMessUrlPage(HttpServletRequest request,String url,String messCode,String disMessArg0Key,String bundle)
    {
    	// 设置列表和新增的链接地址
    	if(url!=null&&url.trim().length()>0){
	       if(messCode!=null&&messCode.trim().length()>0)
	    	   url += StringUtil.getUrlDeli(url)+KeyInMemConstBase.DisMessKey+"="+messCode;
	       if(disMessArg0Key!=null&&disMessArg0Key.trim().length()>0)
	    	   url += StringUtil.getUrlDeli(url)+KeyInMemConstBase.DisMessArg0Key+"="+disMessArg0Key;
	       if(bundle!=null&&bundle.trim().length()>0)
	    	   url += StringUtil.getUrlDeli(url)+"bundle="+bundle;
    	}
    	if(request!=null){
    	   if(url!=null&&url.trim().length()>0)
    	      request.setAttribute("url", url);
        // 放在request中已备转向message页面时使用
    	   request.setAttribute(KeyInMemConstBase.DisMessKey, messCode);
		   request.setAttribute(KeyInMemConstBase.DisMessArg0Key, disMessArg0Key);
		   request.setAttribute("bundle", bundle);
    	}
    }
    
    public HttpSession getSession(HttpServletRequest request){
        // 在此得到session,如果没有，则跳转登录页面
    	HttpSession session = request.getSession(false);
    	if(session==null)
    		throw new NoLoginException();
    	return session;
    }
    
    /**
     * 默认的方法:得到用户的登录信息
     * 不允许匿名登录
     * @return
     */
    public static UserinfoSession getLoginInfo(){
    	return getLoginInfo(false);
    }
    
    /**
     * 得到用户的登录信息
     * @param allowAnonymous: 是否允许匿名登录，如果为false，并且是匿名登录，则返回到登录界面
     * @return
     */
    public static UserinfoSession getLoginInfo(boolean allowAnonymous){
    	UserinfoSession userinfo = null;
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj!=null){
           userinfo = (UserinfoSession)obj;
           if(!allowAnonymous && userinfo.isAnonymous()){
        	   // 如果是匿名登录并且不允许匿名登录，则在此抛出没有正确登录的异常。暂时把shopid放在错误信息中
        	   String info = "";
        	   if(userinfo.getShopid()!=null){
        	      info = userinfo.getShopid().toString();
        	   }
        	   throw new NoLoginException(info);
           }
        }
        if(userinfo==null)
        	throw new UserLoginException("Error:BaseActionBase:cannot get the userinfoSession!");
        return userinfo;
    }
    
    public static Long getUselocaleidFromSession(HttpServletRequest request) throws Exception{
    	Long uselocaleid = I18nLogicImpl.getInstance().getDefaultLocaleid();
    	HttpSession session = request.getSession(true);
    	String userlocaleidStr = (String)session.getAttribute(KeyInMemoryConstant.SessionKey_LocaleidUserSelect);
    	if(userlocaleidStr!=null && userlocaleidStr.trim().length()>0){
    		uselocaleid = new Long(userlocaleidStr);
    		if(!I18nLogicImpl.isSupport(uselocaleid)){
    			uselocaleid = I18nLogicImpl.getInstance().getDefaultLocaleid();
    		}
    	}
    	return uselocaleid;
    }
    
    public static String showDisplayName(){
    	String displayname = getLoginInfo(true).getDisplayname();
    	if(!StringUtils.isEmpty(displayname)){
    		return displayname;
    	}else {
    		String loginame = getLoginInfo(true).getLoginname();
    		if(loginame==null){
    			loginame = "";
    		}
    		return loginame;
    	}
    }
        
}
