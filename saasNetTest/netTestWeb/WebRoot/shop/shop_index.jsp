<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.shop.vo.Shopdescarticle" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="netTest.page.common.title"/></title>
<html:base/>
<%
   String shopid = request.getParameter("shopid");
   String mainurl = WebConstant.WebContext+"/shop/viewshopintroduce.do?queryVO.shopid="+shopid;
%>
<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
<script type="text/javascript">
function linkUrl(url, param){
	//var iframe = document.getElementById("contentIframeId");
	//iframe.src = url;
	loadDivByAjax("maincontent", url, param, false);
}

function divLinkUrl(url){
	linkUrl(url);
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

.moreStyle{
    font-size: small;
    text-align: right;
    padding-right: 15px;
    list-style: none;
}

#leftbar{
   width:18%;
   height:500px;
   float:left;
   border-right: 1px solid #CCCCCC;
}
#leftbar ul {
   margin:0px;
   padding: 0px;
}
#leftbar ul li {
	clear:left;
	margin-top:5px;
	margin-left: 15px;
}
#maincontent{
    margin-left:15px;
    float:left;
    width: 80%;
    min-height: 500px;
}
-->
</style>
</head>
<body>

<div class="col-xs-12 col-md-9 center-block">
	<jsp:include flush="true" page='<%="banner_shop.jsp?shopid="+shopid %>'></jsp:include>

	<div style="height:15px; clear:both;"></div>

	<div id="leftbar">
	    <div style="height:80px;">
	       <div class="titleBarTop">布告栏</div>
	       <div>
	          <ul>
	             <li><a href="javascript:void(0);" onclick="linkUrl('<%=WebConstant.WebContext %>/shop/shoppostviewlist.do?shopid=<%=shopid %>');return false;">学校通知</a></li>
	          </ul>
	       </div>
	    </div>
	    <div style="height:140px">
	       <div class="titleBarTop">学校信息</div>
	       <div>
	          <ul>
	             <li><a href="javascript:void(0);" onclick="linkUrl('<%=WebConstant.WebContext %>/shop/viewshopintroduce.do?queryVO.shopid=<%=shopid %>');return false;">学校简介</a></li>
	             <li><a href="javascript:void(0);" onclick="linkUrl('<%=WebConstant.WebContext %>/shop/viewshopdescarticle.do?queryVO.articletype=<%=Shopdescarticle.ArticleType_TeacherIntroduce %>&queryVO.shopid=<%=shopid %>');return false;">师资力量</a></li>
	             <li><a href="javascript:void(0);" onclick="linkUrl('<%=WebConstant.WebContext %>/shop/listshopcontactpublic.do?queryVO.shopid=<%=shopid %>');return false;">联系地址</a></li>
	             <li class="moreStyle"><a href="">&nbsp;更多</a></li>
	          </ul>
	       </div>
	    </div>
	</div>
	<div id="maincontent">  
	    
	</div>
	
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>
<script type="text/javascript">
    linkUrl('<%=mainurl %>');
</script>
</body>
</html>