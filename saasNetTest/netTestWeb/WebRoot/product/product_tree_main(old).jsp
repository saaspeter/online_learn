<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<% String shopidStr = request.getParameter("shopid");
   if(shopidStr==null){
	   shopidStr = "";
   }
%>
<html>
<head>
	<title>产品目录</title>
	<style type="text/css">
	   <!--
	     .{
		    margin:0px;
		 }
	   -->
	</style>
	<script type="text/javascript">
	    parent.clickmenu('id_shopadmin_prodtest');
	</script>
</head>

<body>
<table style="border:0px;width: 100%;height: 100%; border: 0px;" cellpadding="0" cellspacing="0">
    <tr>
       <td id="catetreeTdId" style="width: 170px;border-right: 1px #cccccc solid;height: 100%;"><iframe id="catetreeFrameId" height=100% width=100% marginheight="0" marginwidth="0" frameborder="0" style="text-align: left;" src="<%=WebConstant.WebContext %>/productcategory/shopprodcatetreepage.do?shopid=<%=shopidStr %>"></iframe></td>
       <td id="contentTdId" style="padding-left: 3px;text-align: left;height: 100%;"><iframe frameborder=0 height=101% width=100% scrolling=auto style="text-align: left;" src="<%=WebConstant.WebContext %>/product/listshopproductmag.do?shopid=<%=shopidStr %>"></iframe></td>
    </tr>
</table>
</body>
</html>