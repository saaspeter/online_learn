<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="contentVar" name="shopdescarticleForm" property="vo.content"/>
    <title>商店介绍</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/edit.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div>
	  <div id="fieldsTitleDiv">教师介绍</div>
      <div style="text-align: center;">更新时间: <bean:write name="shopdescarticleForm" property="vo.createtime" format="yyyy-MM-dd"/></div>
	  <div style="width:98%;margin:10px;">
	  <%=contentVar %>
	  </div>
	  
	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>
