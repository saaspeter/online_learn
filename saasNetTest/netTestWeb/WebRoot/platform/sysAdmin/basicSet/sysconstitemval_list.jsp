<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>常量子选项语言设置列表</title>
	<link rel="stylesheet" type="text/css" href="../../../styles/css/list.css">
	<script type="text/javascript" src="../../../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/basicset/sysconstitemval.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstitemvalForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstitemvalForm" property="backUrlEncode"/>">
  <input type="hidden" name="queryVO.itemid" value="<bean:write name="sysconstitemvalForm" property="queryVO.itemid"/>">
  <div id="firstLineDiv">
      
      <div id="searchDiv">子选项名: 
		<input type="text" name="queryVO.labelname" value="<bean:write name="sysconstitemvalForm" property="queryVO.labelname"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/basicset/sysconstitemval.do?method=addPage&vo.itemid=<bean:write name="sysconstitemvalForm" property="queryVO.itemid"/>');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/basicset/sysconstitemval.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.itemvalueid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/basicset/sysconstitemval.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
			 <li><button type="button" onclick="document.forms[0].submit()"><bean:message key="netTest.commonpage.refreash"/></button></li>
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
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>子选项名</td>
        <td>语言地区</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="itemvalueid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><bean:write name="vo" property="labelname"/></td>
        <td><bean:write name="vo" property="localeName"/></td>
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
