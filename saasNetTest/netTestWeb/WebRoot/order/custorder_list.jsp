<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="listType" name="custorderForm" property="listType" type="java.lang.Integer"></bean:define>
 <% String url = "/order/listOrderMag.do"; 
    if(listType==3){
       url = "/order/shopOrderlist.do";
    }
    int rows = -1;
    String orderIdStr = "";
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>订单列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/orderproduct.js'></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	<script type="text/javascript">
	    var ms_autoApproved = '<bean:message key="netTest.page.order.approveSet.autoApproved"/>';
	    var ms_manaualApproved = '<bean:message key="netTest.page.order.approveSet.manaualApproved"/>';
	    var ms_autoApprovedTip = '<bean:message key="netTest.page.order.approveSet.autoApprovedTip"/>';
		    
	    function showorderapproveset(){
	        var rtnObj = toAjaxUrl("/shop/showorderapproveconfig.do",false);
            if(rtnObj.result=="1"){ 
               document.getElementById("orderappfuncId").innerHTML=ms_autoApproved;
               document.getElementById("orderappfuncId").title=ms_autoApprovedTip;
            }else if(rtnObj.result=="0"){
               document.getElementById("orderappfuncId").innerHTML=ms_manaualApproved;
               document.getElementById("orderappfuncId").title='';
            }else{
               document.getElementById("orderappfuncId").innerHTML='N/A error';
            }
	    }
	    
	    function switchStatus(selObj){
	       if(selObj==null){
             return;
           }
          
           submitForm("list");
	    }
	
	</script>
  </head>

  <body>
  <div class="listPage">
  <html:form styleId="list" action="<%=url %>" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="custorderForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="custorderForm" property="backUrlEncode"/>">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
		订单编号:
		<input type="text" name="queryVO.ordercode" value="<bean:write name="custorderForm" property="queryVO.ordercode"/>"/>
		订单状态:
		<html:select name="custorderForm"  property="queryVO.orderstatus" onchange="switchStatus(this);">
		   <html:option value=""></html:option>
		   <html:optionsSaas consCode="netTest.CustOrder.OrderStatus"/>
		</html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li>订单审核模式:<span id="orderappfuncId" style="font-weight: bold;color: blue" title=""></span>
			     <img src="../styles/imgs/settings.png" onclick="newDiv('setapproveorderfunc.jsp?',0,1,500,250);return false;" style="width: 20px;cursor: pointer;" title="点击修改"/>
			 </li>
		  </ul>
	  </div>
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
    <table id="dataTableId" class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td>订单编号</td>
        <td>订单产品</td>      
        <td>下单时间</td>
        <td>订单金额</td>
        <td>订单状态</td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx" type="netTest.order.vo.Custorder">
      <tr id='<%="dataTrId"+idx %>' class='<%=(idx%2==0)?"oddRow":"evenRow" %>'>
        <td>
            <a href="#" onclick="goUrl('/order/viewOrderMag.do?queryVO.orderid=<bean:write name="vo" property="orderid"/>&backUrl=');return false;" >
               <bean:write name="vo" property="ordercode"/>
            </a>
        </td>
        <td id="prodTdId<%=idx %>">&nbsp;</td>
        <td><bean:write name="vo" property="ordertime" format="yyyy-MM-dd HH:mm"/></td>
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
           
           //
           showorderapproveset();
	   }
     //-->
  </script>
  </body>
</html:html>
