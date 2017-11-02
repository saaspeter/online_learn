<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="baseId" name="orgbaseForm" property="queryVO.pid"></bean:define>
<%
  String rightUrl = WebConstant.WebContext+"/orguser/selectorguser.do?queryVO.orgbaseid="+baseId; 
  String rightUrl_tree = WebConstant.WebContext+"/orguser/selectorguser.do?queryVO.orgbaseid=";
%>

<html>
<head>
	<title>机构设置</title>
	<script language="javascript">
	<!--

	 function showRight(id,name){
	   if(id!=null&&id!=""){
	      document.getElementById("content").src = "<%=rightUrl_tree %>"+id;
	   }
	 }

	//-->
	</script>
</head>

<FRAMESET border=1 id="orgUserMain" name="orgUserMain" frameSpacing=1 frameBorder=1 cols=130,* bordercolor="#EEEEEE">
  <FRAME id="left" name="left" marginWidth=0 marginHeight=0 src="<%=WebConstant.WebContext %>/org/orgtreemag.do?queryVO.pid=<%=baseId %>" scrolling="auto">
  <FRAME id="content" name="content" src="<%=rightUrl %>" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>