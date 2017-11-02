<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<%
   String shopid = request.getParameter("shopid");
   if((shopid==null||shopid.trim().length()<1)&&request.getAttribute("shopid")!=null){
      shopid = String.valueOf(((Long)request.getAttribute("shopid")).longValue());
   }
   String url = "/"+CommonConstant.WebContext_Education+"/shop/shoppostviewlist.do";
   String targeturl = request.getParameter("targeturl");
   if(targeturl!=null&&targeturl.trim().length()>0){
      url = targeturl;
   }
   if(url.indexOf("?")==-1){
      url += "?shopid=" + shopid;
   }else {
      url = "&shopid=" + shopid;
   }
   String menuid = "menu1";
   String targetmenuid = request.getParameter("targetmenuid");
   if(targetmenuid!=null&&targetmenuid.trim().length()>0){
      menuid = targetmenuid;
   }
   
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><bean:message key="netTest.page.common.title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">

<%="#"+menuid %>{
  display: block;
  color: #667;
  background-color: #ec8;
}

*{ margin: 0px; }

</style>
</head>

<body>

<div class="col-xs-12 col-md-9 center-block">

	<jsp:include flush="true" page='<%="banner_shop.jsp?shopid="+shopid %>'></jsp:include>

	<div style="height:15px; clear:both;"></div>

	<div id="maincontent1">  
	    <jsp:include flush="true" page='<%="shop_index.jsp?shopid="+shopid %>'></jsp:include>
	</div>

    <jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>

</body>
</html>