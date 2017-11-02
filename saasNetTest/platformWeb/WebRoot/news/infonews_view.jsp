<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="infonewsForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="infonewsForm" property="editType" type="java.lang.Integer"/>
<bean:define id="contentVar" name="infonewsForm" property="vo.content"/>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看咨询信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/edit.css">
	<style type="text/css">
		<!--
		
		#leftAD {
			float:left;
			background-color:#FFFFFF;
			margin-top:5em;
			width: 120 px;
		}
		#content {
		    width:1000px;
		    left: 50%;
			position:relative;
			clear: both;
			margin-left: -500px;
		}
		
		#rightAD {
			height:800px;
			float:right;
			clear:right;
			margin-top:5em;
			background-color:#FFFFFF;
		}
		
		-->
	</style>
	
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
    <div id="content">
    <jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
    <div class="navlistBar">
	    <bean:write name="infonewsForm" property="categoryname"/> &gt; 咨询信息
    </div>

	<div style="height:5px; clear:both;"></div>
	
	  <div id="fieldsTitleDiv"><bean:write name="infonewsForm" property="vo.caption"/></div>

	  <p>
	  <div style="width:90%;margin:10px;text-align:center;">
	  <%=contentVar %>
	  </div>

	  <div style="height:20px; clear:both;"></div>

      <jsp:include flush="true" page="../index/foot.jsp"></jsp:include>

	</div>
  </body>
</html:html>
