<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>
<html>
<head>
	<title>角色权限</title>
	<script type="text/javascript">
	    function topGoback(){
	        document.location.href = 'listRoles.do';
	    }
	</script>
</head>

  <%
     String roleId = request.getParameter("roleId");
   %>  
<FRAMESET border=1 frameSpacing=1 frameBorder="yes" cols=150,* bordercolor="#EEEEEE">
  <FRAME id="left" name="left" marginWidth=0 marginHeight=0 src="toRescfoldTreePage.do?rescListType=1&roleId=<%=roleId %>" scrolling="auto">
  <FRAME id="content" name="content" src="" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>