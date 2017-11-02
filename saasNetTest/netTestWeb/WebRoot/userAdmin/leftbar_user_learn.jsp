<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" href="../styles/css/leftMenu.css">
  <script type="text/javascript" src="../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../styles/script/menu.js"></script>
</head>

<body>

<div id="leftbar">
<ul id="nav">
   <li class="menu"><a id="id_userplat_mess" href="javascript:void(0);" onclick="pressMenu('id_security_rescMag');goRightUrl('');return false;">信息台</a></li>
   <li class="menu"><a  href="javascript:void(0);" onclick="DoMenu('product');return false;">+我的课程</a>
   		<ul id="product" class="expanded">
		   <li><a id="id_userplat_seebyprod" href="javascript:void(0);" onclick="pressMenu('id_userplat_seebyprod');goRightUrl('#/<%=CommonConstant.WebContext_Platform %>/userprodshop/myUserproduct.do');return false;">&gt;&gt;所有课程</a></li>
		   <li><a id="id_userplat_seebyshop" href="javascript:void(0);" onclick="pressMenu('id_userplat_seebyshop');return false;">&gt;&gt;我的收藏</a></li>
		</ul>
   </li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('jrks');return false;" title="">所有考试</a>
      <ul id="jrks" class="expanded">
         <li><a id="id_user_mytest"  href="javascript:void(0);" onclick="pressMenu('id_user_mytest');goRightUrl('/exam/listTestinfouser.do');return false;">&gt;&gt;我的考试</a></li>
         <li><a id="id_user_sattest" href="javascript:void(0);" onclick="pressMenu('id_user_sattest');return false;">&gt;&gt;考试统计</a></li>
	  </ul>
   </li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('jrlx');return false;" title="">进入练习</a>
      <ul id="jrlx" class="expanded">
         <li><a id="id_user_exerinfo" href="javascript:void(0);" onclick="pressMenu('id_user_exerinfo');goRightUrl('/exercise/listUserExer.do');return false;">&gt;&gt;我的练习</a></li>
         
	  </ul>
   </li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('zwxx');return false;">自我学习</a>
      <ul id="zwxx" class="expanded">
         <li><a id="id_user_learncont" href="javascript:void(0);" onclick="pressMenu('id_user_learncont');goRightUrl('/learncont/selfLearncontent.do');return false;">&gt;&gt;在线学习</a></li>
	  </ul>
   </li>
   
</ul>
</div>

</body>
</html>