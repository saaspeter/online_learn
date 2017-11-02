<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,commonWeb.base.BaseActionBase, platformWeb.base.WebConstant"%>

<%String loginname = BaseActionBase.getLoginInfo(true).getLoginname();
  if(loginname==null){ loginname = ""; } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/banner.css" />
  <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
  <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/menu.js"></script>
	<script type="text/javascript">
	<!--
	
	function goTopUrl(url){
	   window.top.document.location=url;
	}

	function displaySubMenu(li) {
        var subMenu = li.getElementsByTagName("ul")[0];
        subMenu.style.display = "block";
    }

    function hideSubMenu(li) {
        var subMenu = li.getElementsByTagName("ul")[0];
        subMenu.style.display = "none";
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
<div id="top">
<img name="" src="<%=WebConstant.WebContext %>/styles/imgs/logo_platform.jpg"  alt="公司logo" />
欢迎：<%=loginname %> | 
<a href="javascript:void(0)" onclick="goBelowBannerUrl('myPreference_main.jsp');return false;">个人设置</a> | 
<a href="#">退出登录</a>
</div>
<div id="navigation">
   <ul>
      <li id="bannermenu1"><a href="/<%=CommonConstant.WebContext_Platform %>">我的首页</a></li>
      <li id="bannermenu2" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
         <a href="/<%=CommonConstant.WebContext_Platform %>/userprodshop/myUserproduct.do">学习考试</a>
         <ul>
             <li><a href="/<%=CommonConstant.WebContext_Education %>/exam/listTestinfouser.do">公开考试</a></li>
             <li><a href="/<%=CommonConstant.WebContext_Education %>/exam/listTestinfouser.do">我的考试</a></li>
             <li><a href="/<%=CommonConstant.WebContext_Education %>/exercise/listUserExer.do">我的练习</a></li>
             <li><a href="/<%=CommonConstant.WebContext_Education %>/learncont/selfLearncontent.do">在线学习</a></li>
         </ul>
      </li>
      <li id="bannermenu3" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
         <a id="id_selectCourse" href="#">课程管理</a>
         <ul>
             <li><a href="/<%=CommonConstant.WebContext_Education %>/userprodshop/myUserproduct.do">我的课程</a></li>
             <li><a href="/<%=CommonConstant.WebContext_Platform %>/shopping/shopping_discript.jsp">我要选课</a></li>
         </ul>
      </li>
      <li id="bannermenu4" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
         <a href="">课程订单</a>
         <ul>
             <li><a href="/<%=CommonConstant.WebContext_Platform %>/userprodshop/myUserproduct.do">申请中订单</a></li>
             <li><a href="">订单查询</a></li>
         </ul>
      </li>
      <li id="bannermenu5" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
         <a href="">商店管理</a>
         <ul>
             <li><a href="">开设的商店</a></li>
             <li><a href="">我要开商店</a></li>
         </ul>
      </li>
      <li id="bannermenu6"><a href="">我的社交</a></li>
   </ul>
</div>

</body>
</html>
