<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户平台管理</title>
  <link rel="stylesheet" type="text/css" href="../styles/css/leftMenu.css">
  <script type="text/javascript" src="../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../styles/script/menu.js"></script>
</head>

<body>

<div id="leftbar">
<ul id="nav">
   <li class="menu"><a id="id_userplat_mess" href="javascript:void(0);" onclick="pressMenu('id_security_rescMag');goRightUrl('');return false;">信息台</a></li>
   <li class="menu"><a id="id_userplat_buy" href="javascript:void(0);" onclick="pressMenu('id_userplat_buy');goRightUrl('/shopping/shopping_discript.jsp');return false;">购买产品</a></li>
   <li class="menu"><a  href="javascript:void(0);" onclick="DoMenu('product');return false;">+我的产品服务</a>
   		<ul id="product" class="expanded">
		   <li><a id="id_userplat_seebyprod" href="javascript:void(0);" onclick="pressMenu('id_userplat_seebyprod');goRightUrl('');return false;">&gt;&gt;我的课程</a></li>
		   <li><a id="id_userplat_seebyshop" href="javascript:void(0);" onclick="pressMenu('id_userplat_seebyshop');return false;">&gt;&gt;公开考试</a></li>
		</ul>
   </li>
   <li class="menu"><a  href="javascript:void(0);" onclick="DoMenu('myorder');return false;">+我的订单</a>
   		<ul id="myorder" class="expanded">
		   <li><a id="id_userplat_pendorder" href="javascript:void(0);" onclick="pressMenu('id_userplat_pendorder');goRightUrl('/order/custorder.do?method=list2&queryVO.orderstatusEx=1');return false;">&gt;&gt;申请中订单</a></li>
		   <li><a id="id_userplat_orderlist" href="javascript:void(0);" onclick="pressMenu('id_userplat_orderlist');goRightUrl('/order/myOrderlist.do');return false;">&gt;&gt;订单查询</a></li>
		</ul>
   </li>
   <li class="menu"><a id="id_userplat_myaccount" href="javascript:void(0);" onclick="pressMenu('id_userplat_myaccount');goRightUrl('');return false;">我的帐户</a></li>
   <li class="menu"><a id="id_userplat_consume" href="javascript:void(0);" onclick="pressMenu('id_userplat_consume');goRightUrl('');return false;">消费信息</a></li>
   <li class="menu"><a id="id_userplat_reginfo" href="javascript:void(0);" onclick="pressMenu('id_userplat_reginfo');goRightUrl('');return false;">注册基本信息</a></li>
   <li class="menu"><a id="id_userplat_enterbbs" href="javascript:void(0);" onclick="pressMenu('id_userplat_enterbbs');return false;">进入论坛</a></li>
   
</ul>
</div>

</body>
</html>