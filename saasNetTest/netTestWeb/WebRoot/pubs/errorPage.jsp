<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.exception.LogicException"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true" locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="commonWeb.page.pubs.errorPage.jsp.title" bundle="BizKey"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <% String errorCode = null;
     String arg0 = null;
     String extra_message = null;
     if(request.getAttribute("errorCode")!=null){
        errorCode = (String)request.getAttribute("errorCode");
     }
     if(errorCode==null||errorCode.trim().length()<1){
        errorCode = request.getParameter("errorCode"); 
     } 
     
     if(request.getAttribute(LogicException.Key_arg0)!=null){
    	 arg0 = (String)request.getAttribute(LogicException.Key_arg0);
     }
     if(arg0==null||arg0.trim().length()<1){
    	 arg0 = request.getParameter(LogicException.Key_arg0); 
     }
     
     if(request.getAttribute(LogicException.Key_extra_display)!=null){
    	 extra_message = (String)request.getAttribute(LogicException.Key_extra_display);
     }
  
  %>
  
  <body>
    <div><H1><bean:message key="commonWeb.page.pubs.errorPage.jsp.messTitle" bundle="BizKey"/></H1></div>
    <div>
        <html:errors bundle="BizKey"/><p>
        <%if(errorCode!=null && errorCode.trim().length()>0){ %>
            <%if(arg0!=null && arg0.trim().length()>0){ %>
               <bean:message key="<%=errorCode %>" arg0="<%=arg0 %>" bundle="BizKey"/><p>
            <%}else { %>
               <bean:message key="<%=errorCode %>" bundle="BizKey"/><p>
        <% } } %>
        <%if(extra_message!=null && extra_message.trim().length()>0){ %>
                      错误信息: &nbsp;&nbsp; <%=extra_message %>
        <%} %>
    </div>
    <div>
        <p>
        <!-- 
        <button type="button" onclick="history.back();">Back</button>
         -->
    </div>
  </body>
</html:html>
