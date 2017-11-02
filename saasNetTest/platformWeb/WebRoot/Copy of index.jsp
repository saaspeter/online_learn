<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>首页</title>
<link rel="stylesheet" type="text/css" media="screen" href="styles/css/banner.css" />
<style type="text/css">
<!--

#leftAD {
	float:left;
	background-color:#FFFFFF;
	margin-top:5em;
	width: 120 px;
}
#content {
    width:1000px;
    left: 50%;
	position:relative;
	clear: both;
	margin-left: -500px;
}

#rightAD {
	height:800px;
	float:right;
	clear:right;
	margin-top:5em;
	background-color:#FFFFFF;
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

#maincontent{
   width:790px;
   float:right;
   text-align: left;
   border:1px solid #cccccc;
}

#leftbar{
   width:200px;
   height:500px;
   float:left;
   
   
}

#footer{
   width:1000px; position:relative; border-top: 1px solid #cccccc; 
   left: 50%; margin-left: -500px; 
   clear:both;
   text-align: center;
   padding: 10px;
   font-size: smaller;
}

#first {
	
}

#first div.off {
	height:33px;
	background:url(./styles/imgs/tab/tabs_0.gif) repeat-x bottom;
	border:1px #cccccc solid;
	
	float:left;
	cursor:pointer;
	border-bottom-color:#000;
	line-height:32px;
	z-index:40;
	position:relative;
	text-align: center;
}

#first div.on {
	height:33px;
	position:relative;
	padding-top:1px;
	background:url(./styles/imgs/tab/tabs_2.gif) repeat-x bottom;
	float:left;
	cursor:pointer;
	border:1px #cccccc solid;
	
	border-bottom:0;
	z-index:100;
	line-height:33px;
	text-align: center;
}

div.hide {
	width:0px;
	display:none;
	overflow:hidden;
}

div.show {
	clear:left;
	width:180px;
	height:430px;
	padding:5px;
	top:-1px;
	position:relative;
	border:1px solid #cccccc;
	z-index:50;
	overflow:auto;
}
.clear {
	clear:both;
}

-->
</style>
<script type="text/javascript" src="./styles/script/tab/simple_tab.js"></script>
<script type="text/javascript">
   function onclickTree(id,name){     
      if(id==null||id==""){
         return;
      }
      alert(name);
   }
</script>
</head>

<body>

<div id="content">
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

<div style="height:10px; clear:both;"></div>

<div style="height:auto; width:100%; text-align: left">
	<div id="leftbar">
	     <div id="first">
			<div class="on" style="width:94px" id="simpletab1">全部目录</div>
			<div class="off" style="width:94px" id="simpletab2">热门目录</div>
		 </div>
		 <div class="show" id="simpletab1_cont">
	         <iframe frameborder="0" width="100%" height="100%" src="productcategory/productcategory_tree.jsp" scrolling="auto"></iframe>
	     </div>
	     <div class="hide" id="simpletab2_cont">
	            还没做
	     </div>
	</div>
    <div id="maincontent" >ggggggggggggggggggggggggg</div>
</div>

</div>

<div style="height:20px; clear:both;"></div>

<div id="footer">
    关于若渴 &nbsp;&nbsp;&nbsp; 广告服务 &nbsp;&nbsp;&nbsp; 帮助中心 &nbsp;&nbsp;&nbsp; 站点地图 &nbsp;&nbsp;&nbsp; 联系我们
</div>

</body>
</html>
