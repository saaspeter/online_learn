<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="productdesc" name="productForm" property="vo.productdesc" type="java.lang.String"/>
    <title>课程详细信息</title>

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <% if(productdesc==null) { productdesc="";} %>
  </head>
  
 <body>
	<div>
	  
	  <div style="clear:both; width:100%;margin-bottom: 30px;">
	      <%if(!"".equals(productdesc)){ %>
	      <%=productdesc %>
	      <%}else { %>
	      <span class="alertFont">请填写课程介绍，包括课程内容介绍，课程教学目标，对学生的要求，教师介绍等</span>
	      <%} %>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="goUrl('/product/editproductdesc.do?vo.productbaseid=<bean:write name="productForm" property="vo.productbaseid"/>');">编辑</button></li>
		 </ul>
	  </div>
	</div>

  </body>
</html:html>
