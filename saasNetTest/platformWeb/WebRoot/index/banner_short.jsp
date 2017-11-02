<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>

<link rel="stylesheet" type="text/css" media="screen" href="<%=CommonConstant.WebContext_Platform %>/styles/css/banner.css" />
<style type="text/css">
<!--

#topbar {
    width:1000px;
    left: 50%;
	position:relative;
	clear: both;
	margin-left: -500px;
}

#banner {
    position:relative;
	height:60px;
	float:left;
	width: 100%;
	background-color:#ffffff;
	border-bottom: 1px solid #dddddd;
}

#search {
	position:absolute;
    right: 5px;
    bottom: 5px;
}

#logo {
	position:relative;
	width:301 px;
    float:left;
}

-->
</style>

<div id="topbar">
<div style="background: #ff0000">
    <a href="/<%=CommonConstant.WebContext_Platform %>/sysAdmin/sysAdmin_index.jsp">管理平台系统</a>,
    &nbsp;&nbsp;
    <a href="/<%=CommonConstant.WebContext_Platform %>/customers/adduser.do">普通用户注册</a>,&nbsp;
    ,<a href="/<%=CommonConstant.WebContext_Education %>/login.do">登陆</a>，<a href="/<%=CommonConstant.WebContext_Platform %>">购物车</a>，
	   <a href="#">帮助</a>
</div>
<div id="banner">
    <div id="logo">
	   <img src="/<%=CommonConstant.WebContext_Platform %>/styles/imgs/logo_platform.jpg" alt="公司logo" name="logo" id="logo" style="background-color: #33CCFF" />
	</div>
    
</div>

</div>

