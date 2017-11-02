<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="tenantForm" property="jsSuffix" type="java.lang.String"/>
    <title>企业用户注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/list_style.css" />
	
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    
  </head>
  
  <body>
	<div class="editPage">
	 <input type="hidden" name="vo.userid" value="<bean:write name="tenantForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="tenantForm" property="vo.usertype"/>"/>
	  <div id="fieldsTitleDiv" style="background-color:#eeeeee;">成功注册</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  
	  <div id="buttomDiv"></div>
	</div>
    
  </body>
</html:html>
