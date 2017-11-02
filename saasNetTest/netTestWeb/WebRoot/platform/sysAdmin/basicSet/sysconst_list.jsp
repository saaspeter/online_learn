<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTestWeb.base.KeyInMemoryConstant,commonTool.constant.CommonConstant,commonTool.constant.SysconstConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>系统常量列表</title>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/basicset/sysconst.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstForm" property="backUrlEncode"/>">
  <bean:define id="locale" name="sysconstForm" property="locale" type="java.util.Locale"></bean:define>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../../../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
         常量名称: 
		<input type="text" name="queryVO.name" value="<bean:write name="sysconstForm" property="queryVO.name"/>"/>
         常量编码: 
		<input type="text" name="queryVO.sysconstcode" value="<bean:write name="sysconstForm" property="queryVO.sysconstcode"/>"/>
		所属系统: 
	    <html:select name="sysconstForm" property="queryVO.syscode" style="width:150px;">
		    <html:optionsCollection name="sysconstForm" property="sysList"/>
		</html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/basicset/sysconst.do?method=addPage');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/basicset/sysconst.do?method=editPage&editType=<%=WebConstant.editType_edit %>&vo.sysconstid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/basicset/sysconst.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
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
    <table class="listDataTable">
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>常量名称</td>
        <td>常量编码</td>
        <td>所属系统</td>
        <td>取值方式</td>
        <td>编辑子选项</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="sysconstid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><a href="<%=WebConstant.WebContext %>/basicset/sysconst.do?method=editPage&editType=<%=WebConstant.editType_view %>&vo.sysconstid=<bean:write name="vo" property="sysconstid"/>&backUrlEncode=<bean:write name="sysconstForm" property="backUrlEncode"/>"><bean:write name="vo" property="name"/></a>
        <td><bean:write name="vo" property="sysconstcode"/></td>
        <td><bean:writeSaas name="vo" property="syscode" consCode="Platform.SybsystemList"/></td>
        <td><bean:writeSaas name="vo" property="getvalueway" consCode="Const.Common.getValueWay"/></td>
        <td>
           <logic:equal name="vo" property="getvalueway" value="<%=String.valueOf(SysconstConstant.GetValueWay_ConsItem) %>">
              <a href="<%=WebConstant.WebContext %>/basicset/sysconstitem.do?method=list&queryVO.sysconstcode=<bean:write name="vo" property="sysconstcode"/>">编辑子选项</a>
           </logic:equal>
        </td>
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
