<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="commonWeb.base.BaseActionBase" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String loginname = BaseActionBase.getLoginInfo(true).getLoginname();
  if(loginname==null){ loginname = ""; } %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商店的标识</title>
  <link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
  <script type="text/javascript" src="../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../styles/script/menu.js"></script>
	<script type="text/javascript">
	<!--
	
	function goTopUrl(url){
	   window.top.document.location=url;
	}
	
	function menuclick(id,url1,url2,url3){
	   if(id!=null&&id!=''){
	      pressMenu(id);
	   }
	   if(typeof(url1)!="undefined"&&url1!=null&&url1!=''){
	      if(typeof(url2)=="undefined"){
	         url2 = '';
	      }
	      if(typeof(url3)=="undefined"){
	         url3 = '';
	      }
	      goBelowBannerUrl(url1, url2, url3);
	   }
	   return false;
	}

	-->
	</script>
	<style type="text/css">
	<!--
	#top{
		border: 1px solid #dddddd;
	}
	
	-->
	</style>
</head>

<body>
<div id="top">
<img name="" src="../styles/imgs/logo_platform.jpg" alt="公司logo"/>目前仅<font color="#ff0000">平台基础设置</font>下的<font color="#ff0000">"语言设置"</font>、<font color="#ff0000">"商品目录设置"</font>可用.你好，<%=loginname %>，<a href="#">退出</a>，<a href="#" onclick="goTopUrl('../index.html')">返回首页</a>
</div>
<div id="navigation">
    <ul>
      <li id="menu1"><a id="id_banner_cusotmer" href="javascript:void(0);" onclick="menuclick('id_banner_cusotmer','../main.jsp','/sysAdmin/customerAdmin/leftbar_customerAdmin.jsp','/shop/listshop.do');return false;">客户管理</a></li>
	  <li id="menu2"><a id="id_banner_content" href="javascript:void(0);" onclick="pressMenu('id_banner_content');alert('还未实现');return false;">网站内容管理</a></li>
      <li id="menu3"><a id="id_banner_security" href="javascript:void(0);" onclick="menuclick('id_banner_security','../main.jsp','/securityManage/leftbar_security.jsp','/securityManage/resources_main.jsp');return false;">权限管理</a></li>
      <li id="menu4"><a id="id_banner_basicset" href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../main.jsp','/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">平台基础设置</a></li>
    </ul>
</div>
</body>
</html>
