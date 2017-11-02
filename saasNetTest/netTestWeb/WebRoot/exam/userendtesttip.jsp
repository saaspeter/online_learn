<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.exam.constant.TestinfoConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="selfExamForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="testviewresulttype" name="selfExamForm" property="testviewresulttype" type="java.lang.Short"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>提交试卷成功</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language="javascript">
	<!--

	//-->
	</script>

  </head>
  
  <body>
	<div class="editPage">
	     成功提交考卷，<%if(TestinfoConstant.ViewResultType_ViewScoreInstant.equals(testviewresulttype)){ %>
	     您本次考试的总分是: <bean:write name="selfExamForm" property="totalscore"/>, 
	     <%} %>
	     请于开放考试结果后查看详细考试结果
	     
	</div>
	<div style="text-align: center;height: 40px;top: 15px;">
	   <button type="button" onclick="window.close();">关闭本窗口</button>
	</div>
  </body>
</html:html>
