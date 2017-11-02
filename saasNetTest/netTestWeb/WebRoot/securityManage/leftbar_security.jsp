<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="../styles/css/leftMenu.css">
  <script type="text/javascript" src="../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../styles/script/menu.js"></script>
</head>

<body>
<div id="PARENT">
	<ul id="nav">
	   <authz:privilege res="/securityManage/resources_main.jsp">
	      <li><a href="javascript:void(0);" id="id_security_rescMag" onclick="pressMenu('id_security_rescMag');goRightUrl('/securityManage/resources_main.jsp');return false;">&gt;资源权限管理</a></li>
	   </authz:privilege>
	   <authz:privilege res="/securityManage/listRoles.do">
	      <li><a href="javascript:void(0);" id="id_security_roleMag" onclick="pressMenu('id_security_roleMag');goRightUrl('/securityManage/listRoles.do');return false;">&gt;角色管理</a></li>
	   </authz:privilege>
	</ul>
</div>

</body>
</html>