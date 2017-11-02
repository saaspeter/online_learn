<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="acegiWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="acegi.page.{[#folder#]}.custorder_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/{[#folder#]}/custorder.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="custorderForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="custorderForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="acegi.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="acegi.page.{[#folder#]}.custorder.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="custorderForm" property="queryVO.{[#searchColumn#]}"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="acegi.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="acegi.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>Property one</td>
              <td>Property two</td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button onclick="addRecord('/{[#folder#]}/custorder.do?method=addPage');return false;"><bean:message key="acegi.commonpage.add"/></button></li>
			 <li><button onclick="modifyRecord('list','keys','/{[#folder#]}/custorder.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.orderid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BaseKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BaseKey"/>');return false;"><bean:message key="acegi.commonpage.modify"/></button></li>
			 <li><button onclick="deleteRecord('list','keys','/{[#folder#]}/custorder.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BaseKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BaseKey"/>');return false;"><bean:message key="acegi.commonpage.delete"/></button></li>
			 <li><button onclick="document.forms[0].submit()"><bean:message key="acegi.commonpage.refreash"/></button></li>
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
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td><bean:message key="acegi.page.{[#folder#]}.custorder.userid"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.ordercode"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.ordername"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.ordertype"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.ordertime"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.baseaccountid"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.reqendtime"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.fullcost"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.prepaycost"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.discount"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.prepaytime"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.fullpaytime"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.payway"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.paystatus"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.custorder.orderstatus"/></td>


		<div id="dataTableColumnPlus"></div>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="orderid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/{[#folder#]}/custorder.do?method=editPage&queryVO.orderid=<bean:write name="vo" property="orderid"/>&editType=<%=WebConstant.editType_view %>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <td><bean:write name="vo" property="userid"/></td>

        <td><bean:write name="vo" property="ordercode"/></td>

        <td><bean:write name="vo" property="ordername"/></td>

        <td><bean:write name="vo" property="ordertype"/></td>

        <td><bean:write name="vo" property="ordertime"/></td>

        <td><bean:write name="vo" property="baseaccountid"/></td>

        <td><bean:write name="vo" property="reqendtime"/></td>

        <td><bean:write name="vo" property="fullcost"/></td>

        <td><bean:write name="vo" property="prepaycost"/></td>

        <td><bean:write name="vo" property="discount"/></td>

        <td><bean:write name="vo" property="prepaytime"/></td>

        <td><bean:write name="vo" property="fullpaytime"/></td>

        <td><bean:write name="vo" property="payway"/></td>

        <td><bean:write name="vo" property="paystatus"/></td>

        <td><bean:write name="vo" property="orderstatus"/></td>


		<div id="dataTableColumnDataPlus"></div>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
		    changeComplexSearch("complexSearchDiv","<bean:write name="custorderForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
