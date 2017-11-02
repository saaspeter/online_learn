<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="orgbaseid" name="orguserForm" property="vo.orgbaseid" type="java.lang.Long"></bean:define>
<bean:define id="successNumber" name="orguserForm" property="importSuccessNumber" type="java.lang.Integer"></bean:define>
<bean:define id="failNumber" name="orguserForm" property="importFailNumber" type="java.lang.Integer"/>
<html>
   <head>
	  <title></title>
	  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	  <script language=JavaScript src="../styles/script/pageAction.js"></script>
	  <html:base/>
   </head>
<body>
	 	
	<div class="editPage">
		用户导入完毕，共成功导入&nbsp;&nbsp;<%=successNumber %>&nbsp;&nbsp;用户，失败&nbsp;&nbsp;<%=failNumber %>用户
	</div>
		
	<div id="displayMessDiv" style="text-align: left;">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
       <%if(failNumber>0){ %>
       <font style="font-weight: bold;">错误信息:</font><p/>
       <logic:iterate id="messageStr" name="orguserForm" property="messageList" indexId="ind">
          <%=(ind+1)+". "+messageStr %> <br>
       </logic:iterate>
       <%} %>
	</div>
	<p>
	<div style="text-align: center;">
	   <button type="button" onclick="goUrl('/orguser/listorguser.do?queryVO.orgbaseid=<%=orgbaseid %>');">返回用户列表</button>
	</div>
</body>
</html>
