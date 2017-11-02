<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="../../../styles/css/leftMenu.css">
  <script type="text/javascript" src="../../../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../../../styles/script/menu.js"></script>
</head>

<body>

<div id="leftbar">
<ul id="nav">
   <li class="menu" id="id_customerAdmin_mess"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_mess');goRightUrl('');return false;">信息台</a></li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('sdgl');return false;">+商店管理</a>
   		<ul id="sdgl" class="expanded">
		   <li id="id_customerAdmin_shoplist"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_shoplist');goRightUrl('/shop/listshop.do');return false;">&gt;&gt;商店列表</a></li>
		   <li id="id_customerAdmin_tocheckshop"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_tocheckshop');goRightUrl('/shop/listshopapp.do?queryVO.appstatus=4');return false;">&gt;&gt;审核商店申请</a></li>
		   <li id="id_customerAdmin_authenshop"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_authenshop');goRightUrl('/shop/listapplyshopauthen.do');return false;">&gt;&gt;认证商店列表</a></li>
		</ul>
   </li>
   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('yhgl');return false;">+用户管理</a>
       <ul id="yhgl" class="expanded">
           <li id="id_customerAdmin_user"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_user');goRightUrl('/customers/listuser.do');return false;">&gt;&gt;用户管理</a></li>
       </ul>
   </li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('ddgl');return false;">+用户订单管理</a>
   	   <ul id="ddgl" class="expanded">
		   <li id="id_customerAdmin_allorder"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_allorder');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/order/listOrderMag.do');return false;">&gt;&gt;所有订单列表</a></li>
		   <li id="id_customerAdmin_todoorder"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_todoorder');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/order/listOrderMag.do?queryVO.orderstatus=1');return false;">&gt;&gt;需要处理订单</a></li>
	   </ul>
   </li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('cpgl');return false;">+产品管理</a>
   	   <ul id="cpgl" class="expanded">
		   <li id="id_customerAdmin_productlist"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_productlist');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/product/viewProductListMag.do');return false;">&gt;&gt;产品列表</a></li>
	   </ul>
   </li>
   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('fygl');return false;">+费用管理</a>
		<ul id="fygl" class="collapsed">
		   <li id="id_customerAdmin_monthfee"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_monthfee');return false;">&gt;&gt;当月应缴费管理</a></li>
		   <li id="id_customerAdmin_qianfee"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_qianfee');return false;">&gt;&gt;欠费管理</a></li>
		   <li id="id_customerAdmin_givefee"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_givefee');return false;">&gt;&gt;缴费历史</a></li>
		</ul>
   </li>
   <li class="menu" id="id_customerAdmin_bbsset"><a href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_bbsset');goRightUrl('qxfp.html');return false;">论坛设置</a></li>
</ul>
</div>

</body>
</html>