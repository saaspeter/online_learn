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
	<style type="text/css">
		#bannermenu3{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	</style>
	<script language=JavaScript src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    function cancelOrder(url){
	       if(!confirm('撤销订单会删除该订单，请确认是否撤销?')){
       	      return;
       	   }
	       var rtnObj = toAjaxUrl(url,true);
           if(rtnObj.result=="1"){ // forward the success page,if success
              newDiv('/pubs/messDiv.jsp?<%=KeyInMemoryConstant.DisMessKey%>=CommonSystem.commonAction.operation.succeed',1,0);
              getAndToUrl('backUrl');
           }else if(rtnObj.result=="2"){
              showMessagePage(getMessage("custOrder_cancelOrder_fail_processed"));
           }else{
              alert(getMessage("systemError"));
           }
	    }
	    
	    function saveNotes(){
	       var notes = document.getElementById("notesId").value;
	       if(notes!=null&&notes.length>200){
	          alert('客户备注不能超过200个字');
	          return;
	       }
	       var url = "<%=WebConstant.WebContext%>/order/editCustNotes.do?";
	       var param = "vo.orderid=<%=orderid %>&vo.notes="+notes;
	       var rtnObj = toAjaxUrl(url, false, param);
           if(rtnObj.result=="1"){ // forward the success page,if success
              showMessagePage(rtnObj.info); 
              document.getElementById('notesId').className='readonly';
           }else if(rtnObj.result=="2"){
              showMessagePage(rtnObj.info);
           }else{
              alert(getMessage("systemError"));
           }
	    }
	
	</script>
  </head>
  
  <body>
  <div id="centerContent">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="custorderForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="custorderForm" property="backUrlEncode"/>">

  <div class="navlistBar">
        课程订单&nbsp;&gt;&gt;&nbsp;我的订单&nbsp;&gt;&gt;&nbsp;订单详情
  </div>
  
  <div id="displayMessDiv">
     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>
  
  <div style="width: 100%; font-weight:bold; text-align:center; margin: 10px;">订单编号:<bean:write name="custorderForm" property="vo.ordercode"/></div>
  <div>
     <table border="0" cellspacing="1" style="width:100%;">
	    <tr>
	       <td width="5%">&nbsp;</td>
	       <td>用户：<bean:write name="custorderForm" property="vo.userid"/>&nbsp;于
	           <bean:write name="custorderForm" property="vo.ordertime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
	              在商家:&nbsp;<a href="javascript:void(0);" onclick=""><bean:write name="custorderForm" property="vo.shopname"/></a>
	             <img src="../styles/imgs/mail_send.png" style="cursor: pointer;width: 20px;" title="send message to shop admin" onclick="newDiv('/userAdmin/addnotification.do?objectadmintype=shop&objectid=<bean:write name="custorderForm" property="vo.shopid"/>',0,1,550,350);return false;"/>
	              &nbsp;下单，总金额:<bean:write name="custorderForm" property="vo.fullcost"/>，
	              目前状态:&nbsp;<bean:writeSaas name="custorderForm" property="vo.orderstatus" consCode="netTest.CustOrder.OrderStatus"/>
	       </td>
	       <td width="5%">&nbsp;</td>
	    </tr>
	    <%
	    if(!OrderConstant.OrderStatus_submit.equals(orderstatus)){
	    %> 
	    <tr>
	       <td>&nbsp;</td>
	       <td>审核说明:&nbsp;<bean:write name="custorderForm" property="vo.appnotes"/></td>
	       <td width="5%">&nbsp;</td>
	    </tr>
	    <% } %>
	    <tr>
	       <td>&nbsp;</td>
	       <td>客户备注:&nbsp;
	           <% if(OrderConstant.OrderStatus_submit.equals(orderstatus)){%>
	           <textarea id="notesId" rows="2" cols="70" class="readonly" onclick="this.className=''" title="点击即可编辑"><bean:write name="custorderForm" property="vo.notes"/></textarea>
	           &nbsp;&nbsp;
	           <a href="javascript:void(0);" onclick="saveNotes();return false;">[保存]</a>&nbsp;
	           <% } else{ %>
	           <bean:write name="custorderForm" property="vo.notes"/>
	           <% } %>
	       </td>
	       <td width="5%">&nbsp;</td>
	    </tr>
     </table>
  </div>
  
  <div class="dashLine"></div>
  
  <div style="text-align: center; width:100%; background-color: #F5FBFE">
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
      <button class="bigButton" onclick="getAndToUrl('backUrl');">返回</button>&nbsp;&nbsp;
      <%
      if(OrderConstant.OrderStatus_submit.equals(orderstatus)){
      %> 
         <button class="bigButton" onclick="cancelOrder('cancelOrder.do?vo.orderid=<%=orderid%>');return false;">撤销订单</button>&nbsp;&nbsp;
      <%
      }
      %> 
  </div>
      
  </div>
  </div>
  </body>
</html:html>
