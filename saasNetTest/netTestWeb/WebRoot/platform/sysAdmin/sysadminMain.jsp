<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<html>
<head>
	<title>内容主页面</title>
	<script language="javascript">
	<!--
	 	 
	//-->
	</script>
</head>

<FRAMESET id="mainContent" name="mainContent" cols="150,*" frameborder=YES FRAMESPACING=3 BORDER=3 bordercolor="#EEEEEE">
  <FRAME id="leftFrame" name="leftFrame" marginWidth=0 marginHeight=0 src="<%=WebConstant.WebContext %>/platform/sysAdmin/customerAdmin/leftbar_customerAdmin.jsp">
  <FRAME id="mainFrame" name="mainFrame" src="<%=WebConstant.WebContext %>/shop/listshop.do" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>