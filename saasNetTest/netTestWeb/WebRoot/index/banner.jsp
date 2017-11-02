<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,commonWeb.base.BaseActionBase,commonWeb.security.authentication.UserinfoSession,commonWeb.base.KeyInMemConstBase"%>
<%@ include file="/pubs/taglibs.jsp"%>

<% String displayname = BaseActionBase.showDisplayName();
   String loginname = BaseActionBase.getLoginInfo(true).getLoginname();
   
   if(loginname==null){ loginname = ""; } 
  %>

<link rel="stylesheet" type="text/css" media="screen" href="/<%=CommonConstant.WebContext_Education %>/styles/css/banner.css" />
<style type="text/css">
<!--

#topbar {
    width:100%;
    position:relative;
	clear: both;
}

#search {
	position:absolute;
    right: 5px;
    bottom: 5px;
}

#logo {
	position:relative;
	width:301px;
    float:left;
}

-->
</style>

<div id="topbar">

	<div style="background: #eeeeff;padding: 5px;">
	    <table style="width: 100%;border: 0px;" cellpadding="0" cellspacing="0">
	       <tr>
	           <td style="text-align: left;">
	               <%if(loginname!=null && !"".equals(loginname)){ %>
	                    欢迎：<%=displayname %> &nbsp;&nbsp;
					  <a href="/<%=CommonConstant.WebContext_Education %>/user/myhomepage.do">我的空间</a>&nbsp;|
					  <a href="/<%=CommonConstant.WebContext_Education %>/shopping/shoppingcartList.do" target="_shoppingcart">购物车</a>&nbsp;|
				      <a href="/<%=CommonConstant.WebContext_Education %>/j_spring_security_logout">退出</a>
	               <%}else { %>
	               <a href="/<%=CommonConstant.WebContext_Education %>/customers/adduser.do">用户注册</a>&nbsp;&nbsp;
	               <a href="/<%=CommonConstant.WebContext_Education %>/tologin.do">登陆</a>
	               <%} %>
	           </td>
	           <td style="text-align: right;">
	               <font style="color: red"><bean:message key="Common.banner.testing_text"/></font>
		   		   &nbsp;&nbsp;&nbsp;
		   		   <a href="#">帮助</a>
	           </td>
	       </tr>
	    </table>
	</div>
	
	<div id="banner">
	   <table style="border: 0px;">
	       <tr>
	           <td width="302px;">
	               <img style="cursor: pointer;" onclick="document.location.href='/<%=CommonConstant.WebContext_Education %>';" src="/<%=CommonConstant.WebContext_Education %>/styles/imgs/logo_platform.jpg" alt="公司logo" name="logo"/>
	           </td>
	           <td style="max-width:650px">
	               <font style="font-size: x-large;"><bean:message key="netTest.page.site.slogan"/></font>
	           </td>
	       </tr>
	   </table>
	</div>
	
	<div id="navigation" style="clear:both;">
	    <ul>
	      <li id="menu1"><a href="/<%=CommonConstant.WebContext_Education %>/" style="border-left: 2px solid #ffffff;text-align: center"><bean:message key="netTest.page.index.banner.jsp.firstmenu" locale="<%=KeyInMemConstBase.SessionKey_LocaleUserSelect %>"/></a></li>
	      <li id="menu2"><a href="/<%=CommonConstant.WebContext_Education %>/index/listInfonews.do" >咨询信息</a></li>
		  <li id="menu3"><a href="/<%=CommonConstant.WebContext_Education %>/shopping/searchProductList.do" >培训课程</a></li>
	      <!-- <li id="menu4"><a href="/<%=CommonConstant.WebContext_Education %>/index/listIndexOpenTest.do" >公开考试</a></li> -->
	      <li id="menu5"><a href="/<%=CommonConstant.WebContext_Education %>/shop/listShopIndex.do" >培训公司</a></li>
	      <li id="menu6"><a href="/<%=CommonConstant.WebContext_Education %>/product/listOpenactivity.do">公开活动</a></li>
	      <li id="menu7"><a href="/<%=CommonConstant.WebContext_Education %>/about/aboutsite.jsp">关于我们</a></li>
	    </ul>
	</div>

</div>

