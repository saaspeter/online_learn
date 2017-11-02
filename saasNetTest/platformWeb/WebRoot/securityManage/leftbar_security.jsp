<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant " %>
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
	   <li><a href="#" onclick="DoMenu('platform_security');return false;">+平台系统</a>
   	       <ul id="platform_security" class="expanded">
	           <authz:privilege res="/securityManage/resources_main.jsp">
			      <li><a href="javascript:void(0);" id="id_security_rescMag_platform" onclick="pressMenu('id_security_rescMag_platform');goRightUrl('#/<%=CommonConstant.WebContext_Platform %>/securityManage/resources_main.jsp');return false;">&gt;&gt;资源权限管理</a></li>
			   </authz:privilege>
			   <authz:privilege res="/securityManage/listRoles.do">
			      <li><a href="javascript:void(0);" id="id_security_roleMag_platform" onclick="pressMenu('id_security_roleMag_platform');goRightUrl('#/<%=CommonConstant.WebContext_Platform %>/securityManage/listRoles.do');return false;">&gt;&gt;角色管理</a></li>
			   </authz:privilege>
	       </ul>
       </li>
       <li><a href="#" onclick="DoMenu('saasnettest_security');return false;">+教育系统</a>
   	       <ul id="saasnettest_security" class="expanded">
	          <authz:privilege res="/securityManage/resources_main.jsp">
			      <li><a href="javascript:void(0);" id="id_security_rescMag_nettest" onclick="pressMenu('id_security_rescMag_nettest');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/securityManage/resources_main.jsp');return false;">&gt;&gt;资源权限管理</a></li>
			   </authz:privilege>
			   <authz:privilege res="/securityManage/listRoles.do">
			      <li><a href="javascript:void(0);" id="id_security_roleMag_nettest" onclick="pressMenu('id_security_roleMag_nettest');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/securityManage/listRoles.do');return false;">&gt;&gt;角色管理</a></li>
			   </authz:privilege>
	       </ul>
       </li>
	   
	</ul>
</div>

</body>
</html>