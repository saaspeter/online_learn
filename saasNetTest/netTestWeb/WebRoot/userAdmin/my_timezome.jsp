<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonWeb.base.BaseActionBase"%>
<%@ include file="/pubs/taglibs.jsp" %>

<% String jsSuffix = BaseActionBase.getLoginInfo(true).getJsSuffix();
   if(jsSuffix==null||jsSuffix.trim().length()<1){
	  jsSuffix = "default";
   }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
  <script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
  <script type="text/javascript" src="../styles/script/menu.js"></script>
	<script type="text/javascript">
	<!--
	
	function goTopUrl(url){
	   window.top.document.location=url;
	}

	-->
	</script>
	<style type="text/css">
	<!--
	#top{
		border: 1px solid #dddddd;
		margin-bottom: 2px;
	}
	
	-->
	</style>
</head>

<body>
   <div class="navlistBar">
        个人设置&gt;设定地区语言
   </div>
   <div style="text-align: center;margin: 20px;">
        请选择国家语言:
       <html:select name="producttestForm" property="vo.localeid" style="width:200px">
		  <html:optionsSaas localeListSet="true"/>
       </html:select>
   </div>
   <div>
        请选择时区:
        <select id="timezoneId"></select>
   </div>
</body>
<script language=JavaScript>
   var defaultTimezone = '';
   loadTimezoneList('timezoneId', defaultTimezone);
</script>
</html>
