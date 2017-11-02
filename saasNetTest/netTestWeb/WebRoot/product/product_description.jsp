<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="productdesc" name="productForm" property="vo.productdesc" type="java.lang.String"/>
    <title>课程详细信息</title>

    <% if(productdesc==null) { productdesc="";} %>
  </head>
  
 <body>

	<div style="margin: 0px;">
	  <div style="clear:both; width:100%;">
	      <%=productdesc %>
	  </div>
	  
	  <div id="buttomDiv"></div>
    </div>
  </body>
  <script type="text/javascript">
      window.onload = function(){
 		 if(typeof(parent.adjustHeightInBuy)!='undefined'){
 			 var bodyheight = document.body.offsetHeight;
 			 parent.adjustHeightInBuy(bodyheight);
 	     }
 	  };
  </script>
</html:html>
