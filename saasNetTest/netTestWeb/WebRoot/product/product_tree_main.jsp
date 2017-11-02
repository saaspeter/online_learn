<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<% String shopidStr = request.getParameter("shopid");
   if(shopidStr==null){
	   shopidStr = "";
   }
%>
<html>
<head>
	<title><bean:message key="netTest.page.common.title"/></title>
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="../styles/css/list.css" rel="stylesheet"/>
	<style type="text/css">
	   <!--
	     .{
		    margin:0px;
		 }
		 #menu5{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	   -->
	</style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>

</head>

<body>
<div class="col-xs-12 col-md-9 center-block">

    <jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidStr %>'></jsp:include>

	<div class="navlistBar" style="margin-top: 10px;">
	    &#9660 <span style="font-size: larger;margin-right: 50px;">学校全部课程 </span> 
	    <a href='<%=request.getContextPath()+"/usercontext/listMymagProd.do?shopid="+shopidStr %>' class="btn btn-success">进入我直接管理的课程</a> &nbsp;&nbsp;&nbsp;
    </div>
    
    <div id="mainContent">
	<table style="border:0px;width:100%;height:100%;border:0px;margin:0px;" cellpadding="0" cellspacing="0">
	    <tr>
	       <td id="catetreeTdId" style="width: 200px;border-right: 1px #cccccc solid;">
	           <iframe id="catetreeFrameId" height=100% width=100% marginheight="0" marginwidth="0" frameborder="0" style="text-align: left;" src="<%=WebConstant.WebContext %>/productcategory/shopprodcatetreepage.do?shopid=<%=shopidStr %>"></iframe>
	       </td>
	       <td id="contentTdId" style="padding-left: 3px;text-align: left;">
	           <iframe frameborder=0 height=100% width=100% scrolling=auto style="text-align: left;" src="<%=WebConstant.WebContext %>/product/listshopproductmag.do?shopid=<%=shopidStr %>"></iframe>
	       </td>
	    </tr>
	</table>
    </div>

    <jsp:include flush="true" page="../foot.jsp"></jsp:include>
</div>
</body>
</html>