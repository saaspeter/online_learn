package commonWeb.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


import commonTool.base.Page;
import commonTool.constant.CommonConstant;

/**
 * 功能：Action基础类
 */
public class BaseAction extends BaseActionBase
{
	
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
            pageIndex = Integer.parseInt(strPage);
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
    public int getPageSize()
    {
        return CommonConstant.PAGESIZE;
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
     * 设置在信息页面需要显示的链接地址和信息，返回需要转向页面的链接
     * @param request
     * @param form
     * @param actionType
     * @param n
     */
//    public void setMessagePage(HttpServletRequest request,BaseForm form,int actionType,String disMessArg0Key,String bundle){
//    	String disMessKey = "";
// 	    String backUrl = "";
//	    String backUrlEncode = "";
//
//    	if(actionType==KeyInMemoryConstant.ActionTypeAddInt){
//    		disMessKey = "commonWeb.page.pubs.message.jsp.addSuccess";
//    	}
//    	else if(actionType==KeyInMemoryConstant.ActionTypeEditInt){
//    		disMessKey = "commonWeb.page.pubs.message.jsp.modifySuccess";    		
//    	}
//    	else if(actionType==KeyInMemoryConstant.ActionTypeDeleteInt){
//    		disMessKey = "commonWeb.page.pubs.message.jsp.deleteSuccess";
//    	}
//    	else if(actionType==KeyInMemoryConstant.ActionTypeImportInt){
//    		disMessKey = "commonWeb.page.pubs.message.jsp.importSuccess";
//    	}
//    	// 设置列表和新增的链接地址
//    	if(form!=null){
//    	   int hasUrlSubmit = form.getHasUrlSubmit();
//     	   backUrl = form.getBackUrl();
//    	   backUrlEncode = form.getBackUrlEncode();
//    	   if(backUrl!=null&&backUrl.trim().length()>0&&hasUrlSubmit==1){//需要判断是否有提交backUrlEncoder,如果没有提交则不能自动转向页面
//    	       if(disMessKey!=null&&disMessKey.trim().length()>0)
//    	          backUrl += "&"+KeyInMemoryConstant.DisMessKey+"="+disMessKey;
//    	       if(disMessArg0Key!=null&&disMessArg0Key.trim().length()>0)
//    	          backUrl += "&"+KeyInMemoryConstant.DisMessArg0Key+"="+disMessArg0Key;
//    	       if(bundle!=null&&bundle.trim().length()>0)
//    	    	  backUrl += "&bundle="+bundle;
//    	       if(backUrlEncode!=null&&backUrlEncode.trim().length()>0)
//    	          backUrl += "&backUrlEncode="+backUrlEncode;
//    	    }else
//    	    	backUrl = null;
//    	}
//    	if(request!=null){
//    	   if(backUrl!=null&&backUrl.trim().length()>0)
//    	      request.setAttribute("url", backUrl);
//        // 放在request中已备转向message页面时使用
//    	   request.setAttribute(KeyInMemoryConstant.DisMessKey, disMessKey);
//		   request.setAttribute(KeyInMemoryConstant.DisMessArg0Key, disMessArg0Key);
//		   request.setAttribute("bundle", bundle);
//    	}
//    }
    
    /**
     * 设置在信息页面需要显示的信息
     * @param request
     * @param form
     * @param actionType
     * @param n
     */
//    public void setMessagePage(HttpServletRequest request,BaseForm form,String messCode,String bundle){
//    	String backUrl = "";
//    	if(form!=null){
//     	   int hasUrlSubmit = form.getHasUrlSubmit();
//    	   backUrl = form.getBackUrl();
//     	   String backUrlEncode = form.getBackUrlEncode();
//    	   if(backUrl!=null&&backUrl.trim().length()>0&&hasUrlSubmit==1){
//    		   backUrl += "&"+KeyInMemoryConstant.DisMessKey+"="+messCode;
//    	       if(bundle!=null&&bundle.trim().length()>0)
//     	    	  backUrl += "&bundle="+bundle;
//    		   if(backUrlEncode!=null&&backUrlEncode.trim().length()>0)
//     	          backUrl += "&backUrlEncode="+backUrlEncode;
//    	   }else
//   	    	  backUrl = null;
//    	}
//    	if(request!=null){
//     	   if(backUrl!=null&&backUrl.trim().length()>0)
//     	      request.setAttribute("url", backUrl);
//         // 放在request中已备转向message页面时使用
//     	   request.setAttribute(KeyInMemoryConstant.DisMessKey, messCode);
// 		   request.setAttribute("bundle", bundle);
//     	}
//    }
    
    /**
     * 设置要显示的信息，通过设置ActionMessage来处理
     * @param request
     * @param messCode
     * @param args
     * @param bundle
     */
    public void setMessage(HttpServletRequest request,String messCode,String[] args,String bundle){
		ActionMessages errors = new ActionMessages();
		ActionMessage mess = null;
		if(messCode!=null&&messCode.trim().length()>0){
			if(args==null||args.length<1)
				mess = new ActionMessage(messCode);
			else if(args.length==1)
				mess = new ActionMessage(messCode,args[0]);
			else if(args.length==2)
				mess = new ActionMessage(messCode,args[0],args[1]);
			else if(args.length==3)
				mess = new ActionMessage(messCode,args[0],args[1],args[2]);
			else if(args.length>=4)
				mess = new ActionMessage(messCode,args[0],args[1],args[2],args[3]);
		}
		errors.add(Globals.MESSAGE_KEY, mess);
		this.saveMessages(request, errors);
		if(bundle!=null&&bundle.trim().length()>0)
			request.setAttribute("bundle",bundle);
    }
    
    
}
