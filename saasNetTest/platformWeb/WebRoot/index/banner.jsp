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
    &nbsp;&nbsp;<a href="/<%=CommonConstant.WebContext_Platform %>/customers/adduser.do">用户注册</a>,&nbsp;
    ,<a href="/<%=CommonConstant.WebContext_Education %>/login.do">登陆</a>，<a href="/<%=CommonConstant.WebContext_Platform %>">购物车</a>，
	   <a href="#">帮助</a>
</div>
<div id="banner">
    <div id="logo">
	   <img src="/<%=CommonConstant.WebContext_Platform %>/styles/imgs/logo_platform.jpg" alt="公司logo" name="logo" id="logo" style="background-color: #33CCFF" />
	</div>
    <div id="search" >
        <input type="text" style="width: 200px;height: 25px;"/> 
        <select id="cat" name="cat">
		  <option value="">所有分类</option>
		  <option>英语</option>
		  <option>英语/四六级</option>
		  <option>英语/托福</option>
		  <option>法律/司法考试</option>
		  <option>会计/注会考试</option>	
		</select>
	    <button>搜索</button>
	</div>
</div>


<div id="navigation" style="clear:both;">
    <ul>
      <li id="menu1"><a href="/<%=CommonConstant.WebContext_Platform %>/index.jsp" style="width:90px; text-align: center" >首页</a></li>
      <li id="menu2"><a href="/<%=CommonConstant.WebContext_Platform %>/index/listInfonews.do?categoryid=<%=request.getParameter("categoryid")%>&categoryname=<%=request.getParameter("categoryname") %>" >咨询信息</a></li>
	  <li id="menu3"><a href="/<%=CommonConstant.WebContext_Education %>/shopping/searchProductList.do?categoryid=<%=request.getParameter("categoryid")%>&categoryname=<%=request.getParameter("categoryname") %>" >培训课程/请求</a></li>
      <li id="menu4"><a href="/<%=CommonConstant.WebContext_Education %>/index/listIndexOpenTest.do?categoryid=<%=request.getParameter("categoryid")%>&categoryname=<%=request.getParameter("categoryname") %>" >对外考试</a></li>
      <li id="menu5"><a href="/<%=CommonConstant.WebContext_Platform %>/shop/listShopIndex.do?categoryid=<%=request.getParameter("categoryid")%>&categoryname=<%=request.getParameter("categoryname") %>" >培训公司</a></li>
      <li id="menu6"><a href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../main.jsp','/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">BBS论坛</a></li>
    </ul>
</div>

</div>

