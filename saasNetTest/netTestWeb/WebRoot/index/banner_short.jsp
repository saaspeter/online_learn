<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" media="screen" href="/<%=CommonConstant.WebContext_Education%>/styles/css/banner.css" />
<style type="text/css">
<!--
#topbar {
	width:100%;
	position: relative;
	clear: both;
}

#topbar_mobile {
	width: 95%;
	padding-left: 2px;
}

#banner {
	position: relative;
	height: 60px;
	float: left;
	width: 100%;
	background-color: #ffffff;
	border-bottom: 1px solid #dddddd;
}
-->
</style>

<div id="topbar">
	<div style="height: 15px;"></div>
	<div id="banner">
		<div style="float: left">
			<img style="cursor: pointer; width: 302px;"
				onclick="document.location.href='/<%=CommonConstant.WebContext_Education%>';"
				src="/<%=CommonConstant.WebContext_Education%>/styles/imgs/logo_platform.jpg"
				alt="公司logo" name="logo" />
		</div>
		<div style="height: 100%; float: left; max-width: 650px">
			<font style="font-size: x-large; line-height: 60px;">
			  <bean:message key="netTest.page.site.slogan" />
			</font>
		</div>
	</div>

</div>