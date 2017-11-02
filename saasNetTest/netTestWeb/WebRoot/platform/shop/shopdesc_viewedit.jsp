<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="contentVar" name="shopvalueForm" property="vo.shopdesc" type="java.lang.String"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店公告</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/edit.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div>
		
	  <div id="fieldsTitleDiv"><bean:write name="shopvalueForm" property="vo.shopname"/></div>
	  <div style="width:98%;margin:10px;">
	  <%if(contentVar!=null&&!"".equals(contentVar)){ %>
	  <%=contentVar %>
	  <%}else { %>
	     请编辑填写商店简介
	  <%} %>
	  </div>

	  <div style="text-align: center;">
	     <button style="font-size: larger;" onclick="goUrl('/shop/editshopdescript.do');return false;">编辑</button>
	  </div>
      <p>

	</div>
  </body>
</html:html>
