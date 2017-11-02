<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>
<%
   String shopid = request.getParameter("shopid");
   if(request.getAttribute("shopid")!=null){
      shopid = String.valueOf(((Long)request.getAttribute("shopid")).longValue());
   }
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本店课程</title>
<html:base/>
<% String shopidVar = request.getParameter("shopid"); 
%>
<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">
<!--

*{
   margin: 0px;
}

#menu4{
  display: block;
  color: #667;
  background-color: #ec8;
}

#leftbar1{
   float:left;
   width: 13%;
}

.selectA{
   display: block;
   color: selectedtext;
   background: selected;
   background-color:#CCCCFF;
}

#maincontent1{
    float:left;
    width: 85%;
    min-height: 700px;
    height: 700px;
    border-left: 1px #cccccc solid;
    margin-left: 6px;
}
-->
</style>
<script type="text/javascript" src="../styles/script/pageAction.js"></script>
</head>
<body>
<div class="col-xs-12 col-md-9 center-block">

    <jsp:include flush="true" page='<%="banner_shop.jsp?shopid="+shopid %>'></jsp:include>

	<div style="height:15px; clear:both;"></div>

	<div>
		<div id="leftbar1">
		    <jsp:include flush="true" page="../leftbar_shopAdmin.jsp?shopid=<%=shopidVar %>"></jsp:include>
		</div>
		<div id="maincontent1">  
		    <iframe id="contentIframeId" frameborder=0 style="width:100%;height:100%;overflow: auto;" src="/<%=CommonConstant.WebContext_Education %>/shop/shopmagmessage.do"></iframe>
		</div>
	</div>
	
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>

</body>
</html>