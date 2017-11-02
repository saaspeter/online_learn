<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="acegiWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="acegi.page.{[#folder#]}.role_resc_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/{[#folder#]}/role_resc.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="role_rescForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="role_rescForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="acegi.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="acegi.page.{[#folder#]}.role_resc.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="role_rescForm" property="queryVO.{[#searchColumn#]}"/>"/>
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
			 <li><button onclick="addRecord('/{[#folder#]}/role_resc.do?method=addPage');return false;"><bean:message key="acegi.commonpage.add"/></button></li>
			 <li><button onclick="modifyRecord('list','keys','/{[#folder#]}/role_resc.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.{[#pkName#]}=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BaseKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BaseKey"/>');return false;"><bean:message key="acegi.commonpage.modify"/></button></li>
			 <li><button onclick="deleteRecord('list','keys','/{[#folder#]}/role_resc.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BaseKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BaseKey"/>');return false;"><bean:message key="acegi.commonpage.delete"/></button></li>
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
        <td><bean:message key="acegi.page.{[#folder#]}.role_resc.rescId"/></td>

        <td><bean:message key="acegi.page.{[#folder#]}.role_resc.roleId"/></td>


		<div id="dataTableColumnPlus"></div>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="{[#pkName#]}"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/{[#folder#]}/role_resc.do?method=editPage&queryVO.{[#pkName#]}=<bean:write name="vo" property="{[#pkName#]}"/>&editType=<%=WebConstant.editType_view %>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <td><bean:write name="vo" property="rescId"/></td>

        <td><bean:write name="vo" property="roleId"/></td>


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
		    changeComplexSearch("complexSearchDiv","<bean:write name="role_rescForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
