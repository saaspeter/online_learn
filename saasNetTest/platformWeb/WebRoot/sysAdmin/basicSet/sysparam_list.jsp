<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant,platformWeb.base.KeyInMemoryConstant" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>设置系统编码</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/basicset/sysparam.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysparamForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysparamForm" property="backUrlEncode"/>">	

  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">编码: 
		<input type="text" name="queryVO.code" value="<bean:write name="sysparamForm" property="queryVO.code"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="platform.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/basicset/sysparam.do?method=addPage&vo.typecode=<bean:write name="sysparamForm" property="queryVO.typecode"/>&vo.syscode=<bean:write name="sysparamForm" property="queryVO.syscode"/>');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/basicset/sysparam.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.sysparamid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/basicset/sysparam.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete"/></button></li>
			 <li><button type="button" onclick="document.forms[0].submit()"><bean:message key="platform.commonpage.refreash"/></button></li>
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
      <% String disMessKey = request.getParameter(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getParameter(KeyInMemoryConstant.DisMessKey));
         String disMessArg0Key = request.getParameter(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getParameter(KeyInMemoryConstant.DisMessArg0Key));
         if(disMessKey!=null&&disMessKey.trim().length()>0){
           if(disMessArg0Key==null||disMessArg0Key.trim().length()<1){
       %>
         <bean:message key="<%=disMessKey %>" bundle="BizKey"/>
      <% }else{
       %>
         <bean:message key="<%=disMessKey %>" bundle="BizKey" arg0="<%=disMessArg0Key %>"/>
      <%}} %>
  </div>


  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>参数编码</td>
        <td>参数值</td>
        <td>参数分类</td>
        <td>所属系统</td>
        <td>备注</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="sysparamid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><a href="javascript:newDiv('/basicset/sysparam.do?method=editPage&queryVO.sysparamid=<bean:write name="vo" property="sysparamid"/>&editType=<%=WebConstant.editType_view %>',0,1,450,250);"><bean:write name="vo" property="code"/></a></td>
        <td><bean:write name="vo" property="value"/></td>
        <td><bean:write name="vo" property="typecode"/></td>
        <td><bean:write name="vo" property="syscode"/></td>
        <td><bean:write name="vo" property="notes"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
     window.onload=function (){  
        setListTableStyle();
     } 
  </script>
  </body>
</html:html>
