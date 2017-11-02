<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本店首页</title>
<html:base/>

<script type="text/javascript" src="styles/script/pageAction.js"></script>
<script type="text/javascript">
function linkUrl(url){
	var iframe = document.getElementById("contentIframeId");
	iframe.src = url;
}

</script>

<style type="text/css">
<!--

.active{
	font-weight: bold;
	color: #0000FF;
}
.normal{
	font-weight: normal;
	color: #000000;
}

.titleBarTop{
	background-color: #DCEAFC;
    width:100%;
    height:22px;
    font-weight: bold;
    border-top:1px solid #0000FF;
}

.moreStyle{
    font-size: small;
    text-align: right;
    padding-right: 15px;
    list-style: none;
}

#content_all{
  width:900px;
  margin:0 auto;
  margin-left: 15px;
}

#leftbar{
   width:200px;
   height:500px;
   float:left;
   border-right: 1px solid #CCCCCC;
   margin-right: 10px;
}
#leftbar ul {
   margin:0px;
}
#leftbar ul li {
	clear:left;
	margin-top:5px;
	margin-left: 5px;
}
#maincontent{
    float:left;
    width: 600px;

}
-->
</style>
</head>
<body>

<div id="content_all">
	<div id="leftbar">
	    <div style="height:80px">
	       <div class="titleBarTop">布告栏</div>
	       <div>
	          <ul>
	             <li><a href="javascript:void(0);" onclick="linkUrl('<%=WebConstant.WebContext %>/shop/shoppostviewlist.do');return false;">最新通知</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:120px">
	       <div class="titleBarTop">商店信息</div>
	       <div>
	          <ul>
	             <li><a href="javascript:void(0);" style="">商店简介</a></li>
	             <li><a href="javascript:void(0);">师资力量</a></li>
	             <li><a href="javascript:void(0);">练习地址</a></li>
	             <li class="moreStyle"><a href="">&nbsp;更多</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:120px">
	       <div class="titleBarTop">友情链接</div>
	       <div>
	       1234455
	       </div>
	    </div>
	</div>
	<div id="maincontent">  
	    <iframe id="contentIframeId" frameborder="0" style="border:none;width: 650px;height: 500px;"></iframe>
	</div>
</div>
</body>
</html>