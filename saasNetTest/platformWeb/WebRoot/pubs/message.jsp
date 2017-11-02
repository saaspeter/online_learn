<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.KeyInMemoryConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="commonWeb.page.pubs.message.jsp.title" bundle="BizKey"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/message.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>

  </head>
  <body>
  <div style="text-align: center; margin: 90px 50px 50px 50px; font-size: larger;">
 <% String disMessKey = request.getParameter(KeyInMemoryConstant.DisMessKey)==null?null:(request.getParameter(KeyInMemoryConstant.DisMessKey));
    String disMessArg0Key = request.getParameter(KeyInMemoryConstant.DisMessArg0Key)==null?null:(request.getParameter(KeyInMemoryConstant.DisMessArg0Key));
    String bundle = request.getParameter("bundle")==null?null:(request.getParameter("bundle"));
    String pageAction = (request.getAttribute("pageAction")==null)?null:((String)request.getAttribute("pageAction"));
    String url = (request.getAttribute("url")==null)?null:((String)request.getAttribute("url"));
    // 查看request中的值
    if(disMessKey==null){
       disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
    }
    if(disMessArg0Key==null){
       disMessArg0Key = request.getAttribute(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessArg0Key));
    }
    if(bundle==null){
       bundle = request.getAttribute("bundle")==null?null:((String)request.getAttribute("bundle"));
    }
    // 显示信息
    if(disMessKey!=null&&disMessKey.trim().length()>0){
      if(disMessArg0Key==null||disMessArg0Key.trim().length()<1){
         if(bundle==null||bundle.trim().length()<1){
  %>
            <bean:message key="<%=disMessKey %>"/>
 <%      }else{
  %>
            <bean:message key="<%=disMessKey %>" bundle="<%=bundle %>"/>
 <%      }
      } else if(bundle==null||bundle.trim().length()<1){ %>
            <bean:message key="<%=disMessKey %>" arg0="<%=disMessArg0Key %>"/>
 <%   }else{ %>
         <bean:message key="<%=disMessKey %>" bundle="<%=bundle %>" arg0="<%=disMessArg0Key %>"/>
 <%   }
    } 
 %>
    <html:messages id="messStr" message="true">
        <li><bean:write name="messStr"/></li>
    </html:messages>
 </div>
 <%
   if("closeWindow".equals(pageAction)){
 %>
   <div style="height:30px; align:center; font-size: larger;">
     <a href="javascript:window.close();">
        <bean:message key="platform.commonpage.closeWindow"/>
     </a>
   </div>
 <%
   }else if(url!=null&&url.trim().length()>0){
  %>
 <div style="height:30px; align:center; font-size: larger;">
     <a href="<%=url.trim() %>">
        <bean:message key="platform.commonpage.back"/>
     </a>
 </div>
 <%} %>
  </body>
</html:html>
