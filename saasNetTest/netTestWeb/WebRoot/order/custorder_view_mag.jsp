<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTestWeb.base.KeyInMemoryConstant,netTest.order.constant.OrderConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="custorderForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="orderid" name="custorderForm" property="vo.orderid" type="java.lang.Long"/>
    <bean:define id="orderstatus" name="custorderForm" property="vo.orderstatus" type="java.lang.Short"/>
    <title>客户订单</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    function cancelOrder(url){
	       if(!confirm('撤销订单会删除该订单，请确认是否撤销?')){
       	      return;
       	   }
	       var rtnObj = toAjaxUrl(url,false);
           if(rtnObj.result=="1"){ // forward the success page,if success
              newDiv('/pubs/messDiv.jsp?<%=KeyInMemoryConstant.DisMessKey%>=CommonSystem.commonAction.operation.succeed',1,0);
              getAndToUrl('backUrl');
           }else if(rtnObj.result=="2"){
              showMessagePage(getMessage("custOrder_cancelOrder_fail_processed"));
           }else{
              alert(getMessage("systemError"));
           }
	    }
	    
	    function editNotes(orderid, notes){
	       var url = "/order/editCustNotes.do?vo.orderid="+orderid+"&vo.notes="+notes;
	       var rtnObj = toAjaxUrl(url,false);
           if(rtnObj.result=="1"){ // forward the success page,if success
              newDiv('/pubs/messDiv.jsp?<%=KeyInMemoryConstant.DisMessKey%>=CommonSystem.commonAction.operation.succeed',1,0);
              goUrl('/order/viewOrder.do?queryVO.orderid=<%=orderid%>&backUrlEncode=','backUrlEncode');
           }else if(rtnObj.result=="2"){
              showMessagePage(getMessage("custOrder_cancelOrder_fail_processed"));
           }else{
              alert(getMessage("systemError"));
           }
	    }
	
	</script>
  </head>
  
  <body>
  <div class="listPage">
  
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="custorderForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="custorderForm" property="backUrlEncode"/>">
  
  <div id="firstLineDiv">
  </div>
  
  <div class="fieldsTitleDiv">
      <font style="font-weight: bold">订单编号:<bean:write name="custorderForm" property="vo.ordercode"/></font>
  </div>
  
  <div style="clear:left">
     <table border="0" cellspacing="1" style="width:100%;background-color: #F5FBFE">
	    <tr>
	       <td width="5%">&nbsp;</td>
	       <td><span style="font-weight: bold">用户：</span><bean:write name="custorderForm" property="vo.userdisplayname"/>&nbsp;于
	           <bean:write name="custorderForm" property="vo.ordertime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
	            在商家:&nbsp;<a href="javascript:void(0);" onclick=""><bean:write name="custorderForm" property="vo.shopname"/></a>&nbsp;
	            下单，<span style="font-weight: bold">总金额:</span><bean:write name="custorderForm" property="vo.fullcost"/>，
	           <span style="font-weight: bold">目前状态:</span>&nbsp;<bean:writeSaas name="custorderForm" property="vo.orderstatus" consCode="netTest.CustOrder.OrderStatus"/>
	       </td>
	       <td width="5%">&nbsp;</td>
	    </tr>
	    <%
	    if(!OrderConstant.OrderStatus_submit.equals(orderstatus)){
	    %> 
	    <tr>
	       <td>&nbsp;</td>
	       <td><span style="font-weight: bold">审核说明:</span>&nbsp;<bean:write name="custorderForm" property="vo.appnotes"/></td>
	       <td width="5%">&nbsp;</td>
	    </tr>
	    <% } %>
	    <tr>
	       <td>&nbsp;</td>
	       <td><span style="font-weight: bold">客户备注:</span>&nbsp;<bean:write name="custorderForm" property="vo.notes"/>&nbsp;&nbsp;</td>
	       <td width="5%">&nbsp;</td>
	    </tr>
     </table>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>
  
  <div class="bottomFunDiv">
      订单课程列表
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>课程名称</td>
        <td>课程价格</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="custorderForm" property="vo.orderProdList">
	  <logic:iterate id="orderprodvo" name="custorderForm" property="vo.orderProdList" indexId="ind">
      <tr>
        <td></td>
        <td><bean:write name="orderprodvo" property="productname"/></td>
        <td><bean:write name="orderprodvo" property="productprice"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
      </tbody>
    </table>
  </div>
  <div style="clear:both; width:25px;">&nbsp;</div>
  <div style="clear: both; width:100%; text-align:center;">
      <button class="bigButton" onclick="goUrl('/order/shopOrderlist.do?queryVO.orderstatus=1');">返回</button>&nbsp;&nbsp;
      <%
      if(OrderConstant.OrderStatus_submit.equals(orderstatus)){
      %> 
         <button class="bigButton" onclick="newDiv('/order/toApproveOrderPage.do?vo.orderid=<bean:write name="custorderForm" property="vo.orderid"/>',1,0,600,400);">审核订单</button>
      <%
      }
      %> 
  </div>
      
  </div>

  </body>
</html:html>
