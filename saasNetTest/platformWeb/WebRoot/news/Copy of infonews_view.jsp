<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="contentVar" name="infonewsForm" property="vo.content"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>咨询信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	
		  
	  <div id="help">
		  <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"></div>
	  <div id="errorDisplayDiv"></div>
	  <div style="width: 100%;text-align: center;"><bean:write name="infonewsForm" property="vo.caption"/></div>
	  <p>
	  <div style="width:90%;margin:10px;text-align:center;">
	  <%=contentVar %>
	  </div>
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
