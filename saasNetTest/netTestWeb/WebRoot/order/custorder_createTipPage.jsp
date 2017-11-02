<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.order.constant.OrderConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="ordercode" name="custorderForm" property="vo.ordercode" type="java.lang.String"></bean:define>
    <bean:define id="orderstatus" name="custorderForm" property="vo.orderstatus" type="java.lang.Short"></bean:define>
    <title>订单生成</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
    <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
	<div class="editPage">
		  
	  <div id="fieldsTitleDiv" style="text-align: center;">
	     <%if(OrderConstant.OrderStatus_approve.equals(orderstatus)){ %>
	         <bean:message key="netTest.OrderAction.SubmitAndApproved.success" arg0="<%=ordercode %>" bundle="BizKey"/> 
	     <%}else {%>
	         <bean:message key="netTest.OrderAction.Submit.success" arg0="<%=ordercode %>" bundle="BizKey"/> 
	     <%} %>
	  </div>
	  
	  <div style="clear: both;width:100%;height:30px;text-align:center;">
	      <button type="button" onclick="window.close();return false;" style="height: 40px;font-size: large;">关闭窗口</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      <button type="button" onclick="goUrl('/order/myOrderlist.do');return false;" style="height: 40px;font-size: large;">查看订单</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </div>
	  
	  <div id="buttomDiv"></div>
	
	  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
	</div>
	</div>
  </body>
</html:html>
