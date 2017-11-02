<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant, netTest.order.constant.OrderConstant, platform.shop.vo.Shopdescarticle"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试平台功能栏</title>
  <link rel="stylesheet" type="text/css" href="/<%=CommonConstant.WebContext_Education %>/styles/css/leftMenu.css">
  <script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/pageAction.js"></script>
  <script type="text/javascript" src="/<%=CommonConstant.WebContext_Education %>/styles/script/menu.js"></script>
  <script language="javascript">
	<!--
     function gotoOrgUser(url,type){
        var mainUrl = url+"&type="+type;
        document.getElementById("contentIframeId").src=mainUrl;
	 }
	 
	 function toRightUrl(mainUrl){
	    mainUrl = doContextUrl(mainUrl);
	    document.getElementById("contentIframeId").src=mainUrl;
	    //loadDivByAjax('maincontent1', url);
	 }
	 //-->
  </script>
</head>

<body>
<input type="hidden" id="testValue" value="testHello">
<div id="leftbar">
<ul id="nav">
   <li><a id="id_shopadmin_mess" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_mess');toRightUrl('/shop/shopmagmessage.do');return false;">信息台</a></li>
   <li><a id="" href="javascript:void(0);" onclick="DoMenu('sdsz');return false;">商店设置</a>
      <ul id="sdsz" class="expanded">
        <li><a id="id_shopadmin_shopset" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_shopset');toRightUrl('/shop/viewshopeditpage.do');return false;">&bull;&nbsp;基本设置</a></li>
	    <li><a id="id_shopadmin_shopintroduce" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_shopintroduce');toRightUrl('/shop/viewshopdescedit.do');return false;">&bull;&nbsp;商店简介</a></li>
	    <li><a id="id_shopadmin_teacher" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_teacher');toRightUrl('/shop/viewshopdescarticleedit.do?queryVO.articletype=<%=Shopdescarticle.ArticleType_TeacherIntroduce %>');return false;">&bull;&nbsp;教师介绍</a></li>
	    <li><a id="id_shopadmin_shopcontact" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_shopcontact');toRightUrl('/shop/listshopcontact.do');return false;">&bull;&nbsp;联系信息</a></li>
	  </ul>
   </li>
   
   <li><a id="" href="javascript:void(0);" onclick="DoMenu('xxfb');return false;">信息发布</a>
      <ul id="xxfb" class="expanded">
        <li><a id="id_shopadmin_post" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_post');toRightUrl('/shop/listShoppost.do');return false;">&bull;&nbsp;公告管理</a></li>
	    <li><a id="id_shopadmin_activity" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_activity');toRightUrl('/product/listOpenactivity.do?showtype=2');return false;">&bull;&nbsp;公开活动</a></li>
	  </ul>
   </li>
   
   <li><a id="" href="javascript:void(0);" onclick="DoMenu('ddxs');return false;">订单管理</a>
      <ul id="ddxs" class="expanded">
        <li><a id="id_shopadmin_orderlist" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_orderlist');toRightUrl('/order/shopOrderlist.do?queryVO.orderstatus=<%=OrderConstant.OrderStatus_submit %>');return false;">&bull;&nbsp;订单列表</a></li>
	  </ul>
   </li>
   
   <li><a id="" href="javascript:void(0);" onclick="DoMenu('rygl');return false;">单位人员管理</a>
      <ul id="rygl" class="expanded">
        <li><a id="id_sysadmin_dept" href="javascript:void(0);" onclick="pressMenu('id_sysadmin_dept');toRightUrl('/org/magorgmain.do');return false;" >&bull;&nbsp;单位管理</a></li>
        <li><a id="id_shopadmin_usermag" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_usermag');toRightUrl('/org/magorgusermain.do');return false;">&bull;&nbsp;人员管理</a></li>
	    <li><a id="id_shopadmin_userlist" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_userlist');toRightUrl('/userproduct/listuserprodmag.do');return false;">&bull;&nbsp;课程人员</a></li>
	  </ul>
   </li>
   
   <li><a id="id_shopadmin_frontstyle" href="javascript:void(0);" onclick="pressMenu('id_shopadmin_frontstyle');toRightUrl('/shop/editshopstyle.do');return false;">商店样式</a></li>
   
</ul>
</div>

</body>
</html>