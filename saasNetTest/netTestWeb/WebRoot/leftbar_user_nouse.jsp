<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="styles/css/leftMenu.css">
  <script type="text/javascript" src="styles/script/pageAction.js"></script>
  <script type="text/javascript" src="styles/script/menu.js"></script>
</head>

<body>

<div id="leftbar">
<ul id="nav">
   <li class="menu"><a id="id_user_mess" href="javascript:void(0);" onclick="pressMenu('id_user_mess');goRightUrl();return false;">信息台</a></li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('jrks');return false;" title="">进入考试</a>
      <ul id="jrks" class="expanded">
         <li><a id="id_user_mytest"  href="javascript:void(0);" onclick="pressMenu('id_user_mytest');goRightUrl('/exam/listTestinfouser.do');return false;">&gt;&gt;我的考试</a></li>
         <li><a id="id_user_testresult" href="javascript:void(0);" onclick="pressMenu('id_user_testresult');goRightUrl('/exam/listTestResultuser.do');return false;">&gt;&gt;考试结果</a></li>
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
   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('grxtsz');return false;">个人系统设置</a>  
      <ul id="grxtsz" class="collapsed">
         <li><a id="id_user_modideptpass" href="javascript:void(0);" onclick="pressMenu('id_user_modideptpass');return false;">&gt;&gt;修改单位密码</a></li>
         <li><a id="id_user_defaultprodset" href="javascript:void(0);" onclick="pressMenu('id_user_defaultprodset');return false;">&gt;&gt;默认产品设置</a></li>
	  </ul>
   </li>
</ul>
</div>

</body>
</html>