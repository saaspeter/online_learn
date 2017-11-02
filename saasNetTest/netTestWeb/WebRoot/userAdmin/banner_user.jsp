<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,commonWeb.base.BaseActionBase, netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<%  String displayname = BaseActionBase.showDisplayName();
    if(displayname==null || "".endsWith(displayname)){
    	displayname = BaseActionBase.getLoginInfo(true).getLoginname();
    }
  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
  <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/menu.js"></script>
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

	<div style="background: #eeeeff;padding: 5px;">
	    <table style="width: 100%;border: 0px;" cellpadding="0" cellspacing="0">
	       <tr>
	           <td style="text-align: left;">
	                 欢迎：<%=displayname %> | 
				   <a href="<%=WebConstant.WebContext %>/customers/viewuser.do">个人设置</a> &nbsp;|
				   <a href="<%=WebConstant.WebContext %>/shopping/shoppingcartList.do" target="_shoppingcart">购物车</a>&nbsp;|
				   <a href="<%=WebConstant.WebContext %>/j_spring_security_logout">退出</a>
	           </td>
	           <td style="text-align: right;">
		   		   <a href="#">帮助</a>
	           </td>
	       </tr>
	    </table>
	</div>
	
	<div id="banner">
	   <table style="border: 0px;">
	       <tr>
	           <td width="302px;">
	               <img style="cursor: pointer;" onclick="document.location.href='<%=WebConstant.WebContext %>';" src="/<%=CommonConstant.WebContext_Education %>/styles/imgs/logo_platform.jpg" alt="公司logo" name="logo"/>
	           </td>
	           <td>
	               <font style="font-size: x-large;"><bean:message key="netTest.page.site.slogan"/></font>
	           </td>
	       </tr>
	   </table>
	</div>
	
	<div id="navigation">
	   <ul>
	      <li id="bannermenu1"><a href="/<%=CommonConstant.WebContext_Education %>/user/myhomepage.do" style="border-left: 2px solid #ffffff;text-align: center">我的首页</a></li>
	      <li id="bannermenu2" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
	         <a href="/<%=CommonConstant.WebContext_Education %>/product/myUserproduct.do">学习园地</a>
	         <ul>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/exam/listTestinfouser.do">我的考试</a></li>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/product/myUserproduct.do">我的课程</a></li>
	         </ul>
	      </li>
	      <li id="bannermenu4" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
	         <a href="/<%=CommonConstant.WebContext_Education %>/shop/myOwnShops.do">学校管理</a>
	         <ul>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/shop/myOwnShops.do">我的学校</a></li>
	             <li><a href="javascript:void(0)" onclick="window.open('/<%=CommonConstant.WebContext_Education %>/shop/applyNewShop.do')">创办学校</a></li>
	         </ul>
	      </li>
	      <li id="bannermenu3" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
	         <a href="/<%=CommonConstant.WebContext_Education %>/order/myOrderlist.do">订单管理</a>
	         <ul>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/order/myOrderlist.do">我的订单</a></li>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/shopping/shoppingcartList.do" target="_shoppingcart">购物车</a></li>
	         </ul>
	      </li>
	      <li id="bannermenu7" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
	         <a href="/<%=CommonConstant.WebContext_Education %>/userAdmin/listnotification.do">我的社交</a>
	         <ul>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/product/listMyOpenactivity.do">我的活动</a></li>
	             <li><a href="/<%=CommonConstant.WebContext_Education %>/userAdmin/listnotification.do">站内短信</a></li>
	         </ul>
	      </li>
	   </ul>
	</div>

</body>
</html>
