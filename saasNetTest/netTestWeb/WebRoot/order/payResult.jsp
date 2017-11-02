<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title> 支付结果</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/message.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>

  </head>
  <body>
  <% String disMessKey = "";
     if(request.getAttribute("disMessKey")!=null)
        disMessKey = (String)request.getAttribute("disMessKey"); 
  %>
  <div>
 
    <bean:message key="<%=disMessKey %>"/>

 </div>
 
 <div style="height:30px;align:center">
     <html:link >
        返回订单列表
     </html:link>
 </div>

  </body>
</html:html>
