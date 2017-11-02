<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="exerExamForm" property="jsSuffix" type="java.lang.String"/>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>练习信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	
  </head>
  
  <body>
	<div class="editPage">
	    练习完成，您可以<button type="button" onclick="goUrl('/exercise/beforeDoExercise.do?exerid=<bean:write name="exerExamForm" property="exerid"/>');return false;">重新查看练习</button>，
	    或者<button type="button" onclick="parent.window.close();">关闭窗口</button>
	</div>
  </body>
</html:html>
