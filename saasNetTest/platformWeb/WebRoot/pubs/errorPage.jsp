<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


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
     if(request.getAttribute("errorCode")!=null){
        errorCode = (String)request.getAttribute("errorCode");
     }
     if(errorCode==null||errorCode.trim().length()<1){
        errorCode = request.getParameter("errorCode"); 
     } 
  
  %>
  
  <body>
    <div><H1><bean:message key="commonWeb.page.pubs.errorPage.jsp.messTitle" bundle="BizKey"/></H1></div>
    <div>
        <html:errors bundle="BizKey"/><p>
        <%if(errorCode!=null&&errorCode.trim().length()>0){ %>
            <bean:message key="<%=errorCode %>" bundle="BizKey"/>
        <% } %>
    </div>
    <div>
        <p>
        <button type="button" onclick="history.back();">Back</button>
    </div>
  </body>
</html:html>
