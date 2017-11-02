<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="baseId" name="orgbaseForm" property="queryVO.pid"></bean:define>
<bean:define id="magtype" name="orgbaseForm" property="magtype" type="java.lang.String"/>
<%
  String orgUrl = WebConstant.WebContext+"/org/listdeptinfo.do?queryVO.pid="+baseId; 
  String userUrl = WebConstant.WebContext+"/orguser/listorguser.do?queryVO.orgbaseid="+baseId; 
  String rightUrl = orgUrl;
  if("user".equals(magtype)){
     rightUrl = userUrl;
  }

%>

<html>
<head>
    <html:base />
	<title>机构设置</title>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	<!--
	 var pageName="orgUser_main";
	 
     function showOrgRight(id,name){
        if(id!=null&&id!=""){
	       url = "<%=WebConstant.WebContext %>/org/listdeptinfo.do?queryVO.pid="+id;
	       document.getElementById("content").src = url;
	    }
	 }
	 
	 function showUserRight(id,name){
	    if(id!=null&&id!=""){
	       url = "<%=WebConstant.WebContext %>/orguser/listorguser.do?queryVO.orgbaseid="+id;
	       document.getElementById("content").src = url;
	    }
	 }
	 	 
	 function showRight(id,name){
	    <%
	      if("org".equals(magtype)){
	    %>
	         showOrgRight(id,name);
	    <%
	      }else if("user".equals(magtype)){
		%>
		     showUserRight(id,name);
		<%} %>
	 }
	 
	//-->
	</script>
</head>


<FRAMESET border=1 id="orgUserMain" name="orgUserMain" frameSpacing=1 frameBorder=1 cols=170,* bordercolor="#EEEEEE">
  <FRAME id="left" name="left" marginWidth=0 marginHeight=0 src="<%=WebConstant.WebContext %>/org/orgtreemag.do?queryVO.pid=<%=baseId %>" scrolling="auto">
  <FRAME id="content" name="content" src="<%=rightUrl %>" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>