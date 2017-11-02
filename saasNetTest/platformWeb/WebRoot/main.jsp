<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<html>
<head>
	<title>内容主页面</title>
	<script language="javascript">
	<!--
	 	 
	//-->
	</script>
</head>
<% String leftUrl=request.getParameter("leftUrl");
   String rightUrl=request.getParameter("rightUrl");
 %>

<FRAMESET border="0.2" id="mainContent" name="mainContent" frameSpacing=0 frameBorder=yes cols=140,* bordercolor="#EEEEEE">
  <FRAME id="leftFrame" name="leftFrame" marginWidth=0 marginHeight=0 src="<%=leftUrl %>" scrolling="auto">
  <FRAME id="mainFrame" name="mainFrame" src="<%=rightUrl %>" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>