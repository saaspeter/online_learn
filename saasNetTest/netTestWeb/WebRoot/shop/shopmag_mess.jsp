<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="hasshopdescript" name="shopForm" property="hasshopdescript" type="java.lang.String"/>
<bean:define id="ordertodocount" name="shopForm" property="ordertodocount" type="java.lang.Integer"/>
<bean:define id="hasshopcategory" name="shopForm" property="hasshopcategory" type="java.lang.String"/>
<bean:define id="hasshopproduct" name="shopForm" property="hasshopproduct" type="java.lang.String"/>
<bean:define id="shopidVar" name="shopForm" property="shopid" type="java.lang.Long"/>
<%int ordertodoint = 0; if(ordertodocount!=null){ ordertodoint = ordertodocount; } %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>管理商店信息台</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <style>
        body {
			background: #F5FBFE;
		}
	</style>

  </head>
  
  <body>
	  <div class="fieldsTitleDiv">管理商店信息台</div>
	  <%if("no".equals(hasshopdescript)){ %>
	  <div style="margin: 25px;">
	      <a href="/<%=CommonConstant.WebContext_Education %>/shop/editshopdescript.do" onclick="parent.pressMenu('id_shopadmin_shopintroduce');">
	         <font style="color: red;font-size: larger;">商店简介还未填写, 请填写</font>
	      </a>
	  </div>
      <%} %>
      
      <%if("no".equals(hasshopcategory)||"no".equals(hasshopproduct)){ %>
	  <div style="margin: 25px;">
	      <a href="javascript:void(0)" onclick="parent.document.location.href='/<%=CommonConstant.WebContext_Education %>/product/product_tree_main.jsp?shopid=<%=shopidVar %>'">
	          <font style="color: red;font-size: larger;">商店还未设置课程,请先设置商店课程</font>
	      </a>
	  </div>
      <%} %>
      
      <div style="font-size: larger;margin: 25px;">
          <%if(ordertodoint>0){ %>
          <a href="/<%=CommonConstant.WebContext_Education %>/order/shopOrderlist.do?queryVO.orderstatus=1" onclick="parent.pressMenu('id_shopadmin_orderlist');">
	       未审批的订单数目:<%=ordertodoint %> 
	      </a>
	      <%}else { %>
	       未审批的订单数目:<%=ordertodoint %> 
	      <%} %>
	  </div>
	  
	  <div id="buttomDiv"></div>
  </body>
</html:html>
