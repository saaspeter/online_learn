<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="contentVar" name="shopvalueForm" property="vo.shopdesc"/>
<%if(contentVar==null){
	 contentVar = "";
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店简介</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/edit.css">

  </head>
  
  <body>
	<div>
		
	  <div id="fieldsTitleDiv"><bean:write name="shopvalueForm" property="vo.shopname"/></div>
	  <div style="width:98%;margin:10px;">
	  <%=contentVar %>
	  </div>
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
