<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>

<%  
   int rows = -1;
   String orderIdStr = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>订单列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
		#bannermenu3{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	</style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/orderproduct.js'></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
  </head>
  <body>
  <div id="centerContent">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/order/myOrderlist.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="custorderForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="custorderForm" property="backUrlEncode"/>">
  
  <div class="navlistBar">
        课程订单&nbsp;&gt;&gt;&nbsp;我的订单
  </div>
  
  <div id="firstLineDiv">
      <div id="searchDiv"> 
		订单编号:
		<input type="text" name="queryVO.ordercode" value="<bean:write name="custorderForm" property="queryVO.ordercode"/>"/>
		订单状态:
		<html:select name="custorderForm"  property="queryVO.orderstatus">
		   <html:option value=""></html:option>
		   <html:optionsSaas consCode="netTest.CustOrder.OrderStatus"/>
		</html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>订单编号</td>
        <td>下单时间</td>
        <td>订单产品</td>
        <td>订单金额</td>
        <td>订单状态</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx" type="netTest.order.vo.Custorder">
      <tr id='<%="dataTrId"+idx %>'>
        <td></td>
        <td><a href="#" onclick="goUrl('/order/viewOrderUser.do?queryVO.orderid=<bean:write name="vo" property="orderid"/>&backUrlEncode=','backUrlEncode');return false;" >
            <bean:write name="vo" property="ordercode"/></a>
        </td>
        <td><bean:write name="vo" property="ordertime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td id="prodTdId<%=idx %>">&nbsp;</td>
        <td><bean:write name="vo" property="fullcost"/></td>    
        <td><bean:writeSaas name="vo" property="orderstatus" consCode="netTest.CustOrder.OrderStatus"/></td>
      </tr>
      <% rows = idx; orderIdStr=orderIdStr+vo.getOrderid()+",";  %>
      </logic:iterate>
      </logic:present>
      </tbody>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  
    <div style="height:15px; clear:both;"></div>
	<div style="text-align: center">
	    <%if(totalPage>0){ %>
	    <a href="javascript:goPage(<bean:write name="page" property="prePageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_back.gif" alt="previous page" border=0/></a>       
	   <% for(int i=1;i<totalPage+1;i++){ %>
	        <a href="<%=WebConstant.WebContext + "/order/myOrderlist.do" %>?pageIndex=<%=i %>" class="<%=(i==currentPage)?"pageNumberPress":"pageNumberNormal" %>"><%=i %></a> 
	   <% } %>
	   <a href="javascript:goPage(<bean:write name="page" property="nextPageIndex" />)"><img src="<%=WebConstant.WebContext %>/styles/imgs/RW_icon_next.gif" alt="next page" border=0/></a>
	   <% } %>
	</div>
	
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
  </div>
  
  <script type="text/javascript">
	 <!--
       window.onload=function(){
		    var rowsVar = <%=rows %>;
            orderproduct.getOrderproductNames('<%=orderIdStr %>',
                function CB_check(prodArr){
                   if(prodArr!=null&&prodArr.length>0){
		              for(var temp=0;temp<=rowsVar;temp++){
		                  document.getElementById("prodTdId"+temp).innerHTML = prodArr[temp];
		              }
		           }
                }
            );
	   }
     //-->
  </script>
  </body>
</html:html>
