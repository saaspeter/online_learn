<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="infonewsForm" property="jsSuffix" type="java.lang.String" />
<bean:define id="editType" name="infonewsForm" property="editType" type="java.lang.Integer" />
<bean:define id="contentVar" name="infonewsForm" property="vo.content" />

<%
	String formobile = request.getParameter("formobile");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
<html:base />
<title><bean:message key="netTest.page.common.title" />--<bean:write name="infonewsForm" property="categoryname" /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext%>/styles/css/edit.css">
<link href="<%=WebConstant.WebContext %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">
<!--
#menu2 {
	display: block;
	color: #667;
	background-color: #ec8;
}

#leftAD {
	float: left;
	background-color: #FFFFFF;
	margin-top: 5em;
	width: 120 px;
}

#content_mobile {
	width: 95%;
	padding-left: 2px;
}

#rightAD {
	height: 800px;
	float: right;
	clear: right;
	margin-top: 5em;
	background-color: #FFFFFF;
}

	#contentDivId img {
	    max-width:100% !important;
	    height:auto !important;
	}
-->
</style>

<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/pageAction.js"></script>

</head>

<body>
	<div id='<%if("0".equals(formobile)){ out.print("content"); }else {out.print("content_mobile");}; %>' class="col-xs-12 col-md-9 center-block">
	    <%if("0".equals(formobile)){ %>
	    <jsp:include flush="true" page="../../index/banner.jsp"></jsp:include>
		<div class="navlistBar">
			<bean:write name="infonewsForm" property="categoryname" />
			&gt; 咨询信息
		</div>
	    <%}else { %>
	    <jsp:include flush="true" page="../../index/banner_short_mobile.jsp"></jsp:include>
	    <%} %>

		<div style="height: 5px; clear: both;"></div>

		<div id="fieldsTitleDiv">
			<bean:write name="infonewsForm" property="vo.caption" />
		</div>

		<p />
		
		<div id="contentDivId" style="width: 90%; margin: 10px; text-align: left;">
			<%=contentVar%>
		</div>

		<div style="height: 20px; clear: both;"></div>
		<%if("0".equals(formobile)){ %>
		<jsp:include flush="true" page="../../foot.jsp"></jsp:include>
		<%} %>
	</div>
</body>
</html:html>
