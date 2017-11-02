<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%@ page import="platform.constant.SysParam,commonTool.constant.CommonConstant" %>
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
<div id="PARENT">
<ul id="nav">
   <li><a id="id_basicset_langset" href="javascript:void(0);" onclick="pressMenu('id_basicset_langset');goRightUrl('/i18n/i18n.do?method=list');return false;">语言设置</a></li>
   <li><a href="#" onclick="DoMenu('spmlsz');return false;">+商品目录设置</a>
   	   <ul id="spmlsz" class="expanded">
	      <li><a id="id_basicset_allcategory" href="javascript:void(0);" onclick="pressMenu('id_basicset_allcategory');goRightUrl('/productcategory/productcategory_main.jsp');">&gt;&gt;全部目录</a></li>
	      <li><a id="id_basicset_hotcategory" href="javascript:void(0);" onclick="pressMenu('id_basicset_hotcategory');goRightUrl('/productcategory/magHotcategory.do');">&gt;&gt;热门目录</a></li>
	   </ul>
   </li>
   <li><a id="id_basicset_regisset" href="javascript:void(0);" onclick="pressMenu('id_basicset_regisset');goRightUrl('sdjj.html');return false;" title="">注册信息设置</a></li>
   <li><a id="id_basicset_shopfreetry" href="javascript:void(0);" onclick="pressMenu('id_basicset_shopfreetry');goRightUrl('/basicset/sysparam.do?method=list&queryVO.typecode=<%=SysParam.ShopFreeTry %>&queryVO.syscode=<%=CommonConstant.SysCode_Platform %>');return false;">商店免费设置</a></li>
   <li><a id="id_basicset_selffreetry" href="javascript:void(0);" onclick="pressMenu('id_basicset_selffreetry');goRightUrl('spgl.html');return false;">个人免费设置</a></li>
   <li><a id="id_basicset_shoptemplate" href="javascript:void(0);" onclick="pressMenu('id_basicset_shoptemplate');goRightUrl('gnpz.html');return false;">商店模板管理</a></li>
   <li><a id="id_basicset_sysconst" href="javascript:void(0);" onclick="pressMenu('id_basicset_sysconst');goRightUrl('/basicset/sysconst.do?method=list');return false;">系统常量管理</a></li>
   <li><a id="id_basicset_sysparam" href="javascript:void(0);" onclick="pressMenu('id_basicset_sysparam');goRightUrl('/basicset/sysparam.do?method=list');return false;">系统参数管理</a></li>
   <li><a href="#" onclick="DoMenu('sfsz');return false;">+收费设置</a>
   	   <ul id="sfsz" class="collapsed">
	      <li><a id="id_basicset_monthpay" href="javascript:void(0);" onclick="pressMenu('id_basicset_monthpay');goRightUrl('/basicset/sysparam.do?method=list&queryVO.typecode=<%=SysParam.PayMontyly %>&queryVO.syscode=<%=CommonConstant.SysCode_Platform %>');">&gt;&gt;月付费常量</a></li>
	      <li><a id="id_basicset_functioncharge" href="javascript:void(0);" onclick="pressMenu('id_basicset_functioncharge');goRightUrl('/sysadmin/sysfunctionitem.do?method=list');">&gt;&gt;功能收费管理</a></li>
	   </ul>
   </li>
   <li><a id="id_basicset_bbs" href="javascript:void(0);" onclick="pressMenu('id_basicset_bbs');goRightUrl('qxfp.html');return false;">论坛设置</a></li>
   
</ul>
</div>

</body>
</html>