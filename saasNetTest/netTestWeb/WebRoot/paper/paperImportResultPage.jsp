<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="paperid" name="paperForm" property="vo.paperid" type="java.lang.Long"></bean:define>
<bean:define id="successNumber" name="paperForm" property="importSuccessNumber" type="java.lang.Integer"></bean:define>
<bean:define id="failNumber" name="paperForm" property="importFailNumber" type="java.lang.Integer"/>
<html>
   <head>
	  <title>导入试卷结果</title>
	  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	  <script language=JavaScript src="../styles/script/pageAction.js"></script>
	  <html:base/>
   </head>
<body>
	 	
	<div class="editPage">
		试卷导入完毕，共成功导入&nbsp;&nbsp;<%=successNumber %>&nbsp;&nbsp;题目，失败&nbsp;&nbsp;<%=failNumber %>题目
	</div>
		
	<div id="displayMessDiv" style="text-align: left;">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
       <%if(failNumber>0){ %>
       <font style="font-weight: bold;">错误信息:</font><p/>
       <logic:iterate id="messageStr" name="paperForm" property="messageList" indexId="ind">
          <%=(ind+1)+". "+messageStr %> <br>
       </logic:iterate>
       <%} %>
	</div>
	
	<div style="text-align: center;">
	   <button type="button" onclick="goUrl('/paper/editPaper.do?queryVO.paperid=<bean:write name="paperForm" property="vo.paperid"/>');">编辑试卷: <bean:write name="paperForm" property="vo.papername"/></button>
	</div>
</body>
</html>
