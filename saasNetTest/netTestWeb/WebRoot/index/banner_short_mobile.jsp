<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/styles/css/banner.css" />
<style type="text/css">
<!--
#topbar {
	width: 1000px;
	left: 50%;
	position: relative;
	clear: both;
	margin-left: -500px;
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

<div id="topbar_mobile">
	<div style="height: 15px;"></div>
	<div id="banner">
		<div style="float: left">
			<img style="cursor: pointer; width: 302px;"
				onclick="document.location.href='<%=request.getContextPath() %>';"
				src="<%=request.getContextPath() %>/styles/imgs/logo_platform.jpg"
				alt="公司logo" name="logo" />
		</div>
		<div style="height: 100%; float: left; max-width: 650px">
			<font style="font-size: x-large; line-height: 60px;">
			    <bean:message key="netTest.page.site.slogan" />
			</font>
		</div>
	</div>

</div>