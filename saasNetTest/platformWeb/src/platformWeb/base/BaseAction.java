package platformWeb.base;

import javax.servlet.http.HttpServletRequest;

import commonWeb.base.BaseActionBase;

/**
 * 功能：Action基础类
 */
public class BaseAction extends BaseActionBase
{
    
    /**
     * 设置在信息页面需要显示的链接地址和信息，返回需要转向页面的链接
     * @param request
     * @param form
     * @param actionType
     * @param n
     */
//    public void setMessagePage(HttpServletRequest request,BaseForm form,int actionType,String disMessArg0Key,String bundle)
//    {
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
    
}
