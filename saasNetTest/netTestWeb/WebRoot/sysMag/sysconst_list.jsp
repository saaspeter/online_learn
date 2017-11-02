<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTestWeb.base.KeyInMemoryConstant,commonTool.constant.CommonConstant,commonTool.constant.SysconstConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>系统常量列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/sysMag/sysconst.do?method=list" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstForm" property="backUrlEncode"/>">
  
  <bean:define id="locale" name="sysconstForm" property="locale" type="java.util.Locale"></bean:define>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">常量名称: 
		<input type="text" name="queryVO.name" value="<bean:write name="sysconstForm" property="queryVO.name"/>"/>
		
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/sysMag/sysconst.do?method=addPage');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/sysMag/sysconst.do?method=editPage&editType=<%=WebConstant.editType_edit %>&vo.sysconstid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/sysMag/sysconst.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
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
      <% String disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
         String disMessArg0Key = request.getAttribute(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessArg0Key));
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
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>常量名称</td>
        <td>常量编码</td>
        <td>所属系统</td>
        <td>取值方式</td>
        <td>编辑子选项</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="sysconstid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><a href="<%=WebConstant.WebContext %>/sysMag/sysconst.do?method=editPage&editType=<%=WebConstant.editType_view %>&vo.sysconstid=<bean:write name="vo" property="sysconstid"/>&backUrlEncode=<bean:write name="sysconstForm" property="backUrlEncode"/>"><bean:write name="vo" property="name"/></a>
        <td><bean:write name="vo" property="sysconstcode"/></td>
        <td><bean:define id="syscode" name="vo" property="syscode" type="java.lang.String"/>
            <%=CommonConstant.qrySystemName(syscode,locale) %>
        </td>
        <td><bean:writeSaas name="vo" property="getvalueway" consCode="Const.Common.getValueWay"/></td>
        <td>
           <logic:equal name="vo" property="getvalueway" value="<%=String.valueOf(SysconstConstant.GetValueWay_ConsItem) %>">
              <a href="<%=WebConstant.WebContext %>/sysMag/sysconstitem.do?method=list&queryVO.sysconstcode=<bean:write name="vo" property="sysconstcode"/>">编辑子选项</a>
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
  <script language=JavaScript>
	 <!--
       window.onload=function(){
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
