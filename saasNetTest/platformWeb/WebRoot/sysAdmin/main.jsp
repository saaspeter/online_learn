<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<html>
<head>
	<title>内容主页面</title>
	<script language="javascript">
	<!--
	 	 
	//-->
	</script>
</head>

<FRAMESET border=1 id="mainContent" name="mainContent" frameSpacing=0 frameBorder=1 cols=151,* bordercolor="#EEEEEE">
  <FRAME id="leftFrame" name="leftFrame" marginWidth=0 marginHeight=0 src="<%=WebConstant.WebContext %>/sysAdmin/customerAdmin/leftbar_customerAdmin.jsp">
  <FRAME id="mainFrame" name="mainFrame" src="<%=WebConstant.WebContext %>/shop/listshop.do" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>