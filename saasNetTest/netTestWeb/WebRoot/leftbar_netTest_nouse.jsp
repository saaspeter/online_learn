<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%String shopidVar = request.getParameter("shopid"); 
    if(shopidVar==null||"null".equals(shopidVar)){shopidVar="";}
  %>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="/<%=CommonConstant.WebContext_Education %>/styles/css/leftMenu.css">
  <script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/pageAction.js"></script>
  <script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/menu.js"></script>
  <script language="javascript">
	<!-- 
	 function toRightUrl(mainUrl){
	    mainUrl = doContextUrl(mainUrl);
	    document.getElementById("contentIframeId").src=mainUrl;
	 }
	 //-->
  </script>
</head>

<body>
<div id="leftbar" style="width:100%;">
	<ul id="nav">
	   <li class="menu"><a id="id_mag_mess" href="javascript:void(0);" onclick="pressMenu('id_mag_mess');toRightUrl('/product/showprodmagmess.do?shopid=<%=shopidVar %>');return false;">信息台</a></li>
	   <li><a href="javascript:void(0);" onclick="DoMenu('cpgl');return false;">课程管理</a>
	      <ul id="cpgl" class="expanded">
	        <authz:privilege res='<%="/product/product_tree_main.jsp?shopid="+shopidVar %>'>
	        <li><a id="id_shopadmin_prodtest" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_prodtest');toRightUrl('/product/product_tree_main.jsp?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;全部课程</a></li>
	        </authz:privilege>
	        <li><a id="id_shopadmin_myprodmag" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_myprodmag');toRightUrl('/usercontext/listMymagProd.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;我的课程</a></li>
	        <li><a id="id_kecheng_tz" href="javascript:void(0);" onclick="pressMenu('id_kecheng_tz');toRightUrl('/product/listcoursepostmag.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;课程通知</a></li>
		  </ul>
	   </li>
	   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('lxsz');return false;">学习相关</a> 
		   <ul id="lxsz" class="expanded">
		   		 <li><a id="id_mag_learnDoc" href="javascript:void(0);" onclick="pressMenu('id_mag_learnDoc');toRightUrl('/learncont/listLearncontent.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;学习管理</a></li>
		         <li><a id="id_mag_comments" href="javascript:void(0);" onclick="pressMenu('id_mag_comments');toRightUrl('/social/listprodcommentsmag.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;提问答疑</a></li>
		   </ul>
	   </li>
	   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('kssz');return false;" title="">考试练习</a>
		   <ul id="kssz" class="expanded">
		         <li><a id="id_mag_testmag" href="javascript:void(0);" onclick="pressMenu('id_mag_testmag');toRightUrl('/exam/listTodoTestinfo.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;考试管理</a></li>
		         <li><a id="id_mag_papermag" href="javascript:void(0);" onclick="pressMenu('id_mag_papermag');toRightUrl('/paper/listPaper.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;试卷管理</a></li>
		         <li><a id="id_mag_waremag" href="javascript:void(0);" onclick="pressMenu('id_mag_waremag');toRightUrl('/wareques/listWare1.do?shopid=<%=shopidVar %>');return false;">&bull;&nbsp;题库管理</a></li>
		   </ul>
	   </li>
	</ul>
</div>

</body>
</html>