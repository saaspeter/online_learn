<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>常量子选项列表</title>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/basicset/sysconstitem.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstitemForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstitemForm" property="backUrlEncode"/>">
  <input type="hidden" name="queryVO.sysconstcode" value="<bean:write name="sysconstitemForm" property="queryVO.sysconstcode"/>">
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="<%=WebConstant.WebContext %>/styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">名称: 
		<input type="text" name="queryVO.labelname" value="<bean:write name="sysconstitemForm" property="queryVO.labelname"/>"/>
		取值:
		<input type="text" name="queryVO.value" value="<bean:write name="sysconstitemForm" property="queryVO.value"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
      <div>
         常量&gt;&gt;
           <bean:write name="sysconstitemForm" property="constname"/>(<bean:write name="sysconstitemForm" property="queryVO.sysconstcode"/>)
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/basicset/sysconstitem.do?method=addPage&vo.sysconstcode=<bean:write name="sysconstitemForm" property="queryVO.sysconstcode"/>');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/basicset/sysconstitem.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
			 <li><button type="button" onclick="goUrl('/basicset/sysconst.do?method=list');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
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
        <td>子项名称</td>
        <td>取值</td>
        <td>显示顺序</td>
        <td>修改</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="itemid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><a href="<%=WebConstant.WebContext %>/basicset/sysconstitem.do?method=editPage&queryVO.itemid=<bean:write name="vo" property="itemid"/>&queryVO.sysconstcode=<bean:write name="sysconstitemForm" property="queryVO.sysconstcode"/>&editType=<%=WebConstant.editType_view %>&backUrlEncode=<bean:write name="sysconstitemForm" property="backUrlEncode"/>">
                <bean:write name="vo" property="labelname"/>
        </a></td>
        <td><bean:write name="vo" property="value"/></td>
        <td><bean:write name="vo" property="orderno"/></td>
        <td>
           <a href="<%=WebConstant.WebContext %>/basicset/sysconstitem.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.sysconstcode=<bean:write name="sysconstitemForm" property="queryVO.sysconstcode"/>&queryVO.itemid=<bean:write name="vo" property="itemid"/>&backUrlEncode=<bean:write name="sysconstitemForm" property="backUrlEncode"/>">修改</a> 
        </td>
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
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
