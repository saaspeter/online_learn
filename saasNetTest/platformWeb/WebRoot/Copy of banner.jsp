<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>首页</title>
<link rel="stylesheet" type="text/css" media="screen" href="styles/css/banner.css" />
<style type="text/css">
<!--

#topbar {
    width:1000px;
    left: 50%;
	position:relative;
	clear: both;
	margin-left: -500px;
}

#banner {
    position:relative;
	height:60px;
	float:left;
	width: 100%;
	background-color:#ffffff;
	border-bottom: 1px solid #dddddd;
}

#search {
	position:absolute;
    right: 5px;
    bottom: 5px;
}

#logo {
	position:relative;
	width:301 px;
    float:left;
}

-->
</style>
<script type="text/javascript" src="./styles/script/tab/simple_tab.js"></script>

</head>

<body>

<div id="topbar">
<div style="background: #ff0000"><a href="/platform/customers/tenant.do?method=addPage">机构注册</a>，
    <a href="/platform/customers/user.do?method=addPage">普通用户注册</a>,<a href="sysAdmin/sysAdmin_index.jsp">管理平台系统</a>
    ,<a href="login.jsp">登陆</a>，<a href="/platform/customers/tenant.do?method=addPage">注册</a>，
	   <a href="#">帮助</a>
</div>
<div id="banner">
    <div id="logo">
	   <img src="styles/imgs/logo_platform.jpg" alt="公司logo" name="logo" id="logo" style="background-color: #33CCFF" />
	</div>
    <div id="search" >
        <input type="text" style="width: 200px;height: 25px;"/> 
        <select id="cat" name="cat">
		  <option value="">所有分类</option>
		  <option>英语</option>
		  <option>英语/四六级</option>
		  <option>英语/托福</option>
		  <option>法律/司法考试</option>
		  <option>会计/注会考试</option>	
		</select>
	    <button>搜索</button>
	</div>
</div>


<div id="navigation" style="clear:both;">
    <ul>
      <li id="menu1"><a id="id_banner_default" href="javascript:void(0);" style="width:90px; text-align: center" onclick="return false;">首页</a></li>
      <li id="menu2"><a id="id_banner_info" href="javascript:void(0);" onclick="pressMenu('id_banner_content');alert('还未实现');return false;">资讯信息</a></li>
	  <li id="menu3"><a id="id_banner_course" href="javascript:void(0);" onclick="pressMenu('id_banner_content');alert('还未实现');return false;">培训课程</a></li>
      <li id="menu4"><a id="id_banner_opentest" href="javascript:void(0);" onclick="menuclick('id_banner_security','../main.jsp','/securityManage/leftbar_security.jsp','/securityManage/resources_main.jsp');return false;">对外考试</a></li>
      <li id="menu5"><a id="id_banner_shop" href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../main.jsp','/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">培训公司</a></li>
      <li id="menu6"><a id="id_banner_freeresc" href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../main.jsp','/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">免费资源</a></li>
      <li id="menu7"><a id="id_banner_bbs" href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../main.jsp','/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">BBS论坛</a></li>
    </ul>
</div>

</div>

</body>
</html>
