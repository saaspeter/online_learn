<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="../../styles/css/leftMenu.css">
  <script type="text/javascript" src="../../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../../styles/script/menu.js"></script>
</head>

<body>

<div id="leftbar">
<ul id="nav">
   <li class="menu"><a  id="id_customerAdmin_mess" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_mess');goRightUrl('');return false;">信息台</a></li>
   <li class="menu"><a  href="javascript:void(0);" onclick="DoMenu('sdgl');return false;">+商店管理</a>
   		<ul id="sdgl" class="expanded">
		   <li><a id="id_customerAdmin_shoplist" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_shoplist');goRightUrl('/shop/listshop.do');return false;">&gt;&gt;商店列表</a></li>
		   <li><a id="id_customerAdmin_shopfee" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_shopfee');return false;">&gt;&gt;商店费用</a></li>
		</ul>
   </li>
   <li class="menu"><a  id="" href="javascript:void(0);" onclick="DoMenu('sdshgl');return false;">+商店审核管理</a>
   		<ul id="sdshgl" class="expanded">
		   <li><a id="id_customerAdmin_applyshop" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_applyshop');goRightUrl('/shop/applyNewShop.do');return false;">&gt;&gt;申请商店</a></li>
		   <li><a id="id_customerAdmin_tocheckshop" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_tocheckshop');goRightUrl('/shop/listshopapp.do');return false;">&gt;&gt;审核商店列表</a></li>
		</ul>
   </li>
   <li class="menu"><a id="id_customerAdmin_shopprod" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_shopprod');goRightUrl('/product/allproduct_main.jsp');return false;">商店产品管理</a></li>
   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('yhgl');return false;">+用户管理</a>
       <ul id="yhgl" class="expanded">
           <li><a id="id_customerAdmin_user" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_user');goRightUrl('/customers/listuser.do');return false;">&gt;&gt;用户管理</a></li>
       </ul>
   </li>
   <li class="menu"><a href="javascript:void(0);" onclick="DoMenu('ddgl');return false;">+用户订单管理</a>
   	   <ul id="ddgl" class="expanded">
		   <li><a id="id_customerAdmin_allorder" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_allorder');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/order/listOrderMag.do');return false;">&gt;&gt;所有订单列表</a></li>
		   <li><a id="id_customerAdmin_todoorder" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_todoorder');goRightUrl('#/<%=CommonConstant.WebContext_Education %>/order/listOrderMag.do?queryVO.orderstatus=1');return false;">&gt;&gt;需要处理订单</a></li>
	   </ul>
   </li>
   <li class="menu"><a id="" href="javascript:void(0);" onclick="DoMenu('fygl');return false;">+费用管理</a>
		<ul id="fygl" class="collapsed">
		   <li><a id="id_customerAdmin_monthfee" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_monthfee');return false;">&gt;&gt;当月应缴费管理</a></li>
		   <li><a id="id_customerAdmin_qianfee" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_qianfee');return false;">&gt;&gt;欠费管理</a></li>
		   <li><a id="id_customerAdmin_givefee" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_givefee');return false;">&gt;&gt;缴费历史</a></li>
		</ul>
   </li>
   <li class="menu"><a id="id_customerAdmin_bbsset" href="javascript:void(0);" onclick="pressMenu('id_customerAdmin_bbsset');goRightUrl('qxfp.html');return false;">论坛设置</a></li>
</ul>
</div>

</body>
</html>