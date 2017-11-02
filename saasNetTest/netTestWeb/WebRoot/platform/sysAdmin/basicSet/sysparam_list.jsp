<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTestWeb.base.KeyInMemoryConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>设置系统编码</title>
	<link rel="stylesheet" type="text/css" href="../../../styles/css/list.css">
	<script type="text/javascript" src="../../../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../../../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/basicset/sysparam.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysparamForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysparamForm" property="backUrlEncode"/>">	

  <div id="firstLineDiv">
      <div id="searchDiv">编码: 
		<input type="text" name="queryVO.code" value="<bean:write name="sysparamForm" property="queryVO.code"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  
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
      <thead>
      <tr class="listDataTableHead">
        <td width="10px"></td>
        <td>参数编码</td>
        <td>参数值</td>
        <td>参数分类</td>
        <td>所属系统</td>
        <td></td>
        <td>备注</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><bean:write name="vo" property="code"/></td>
        <td><bean:write name="vo" property="value"/></td>
        <td><bean:write name="vo" property="typecode"/></td>
        <td><bean:writeSaas name="vo" property="syscode" consCode="Platform.SybsystemList"/></td>
        <td><img src="../../../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/basicset/sysparam.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.code=<bean:write name="vo" property="code"/>&backUrlEncode=','backUrlEncode');return false;"/></td>
        <td><bean:write name="vo" property="notes"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </body>
</html:html>
