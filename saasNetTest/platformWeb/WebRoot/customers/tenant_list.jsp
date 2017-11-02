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
    <title>公司用户列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/customers/listTenant.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="tenantForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="tenantForm" property="backUrlEncode"/>">	
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">公司客户名: 
		<input type="text" name="query.name" value="<bean:write name="tenantForm" property="query.name"/>"/>
		客户状态:
		<html:select name="tenantForm" property="query.status">
			<html:optionsCollection name="tenantForm" property="statusList"/>
		</html:select>
		<input type="submit" name="Submit" value="查询" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="platform.commonpage.complexQuery"/></a>
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
			 <li><button type="button" onclick="addRecord('/customers/addTenant.do');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/customers/editTenant.do?vo.userid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/customers/deleteTenant.do','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/customers/statuschangepage.do?queryVO.userid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;">用户状态变更</button></li>
			 <li><button type="button" onclick="document.forms[0].submit()"><bean:message key="platform.commonpage.refreash"/></button></li>
		     <li>(需要加上用户审批的功能)</li>
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
        
        <td>客户名称</td>

        <td>帐号</td>

        <td>帐户余额</td>
        
        <td>用户状态</td>

      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')"></td>

        <td><a href="#" onclick="goUrl('/customers/viewTenant.do?vo.userid=<bean:write name="vo" property="userid"/>','backUrlEncode');return false;">
            <bean:write name="vo" property="name"/></a></td>

        <td><bean:write name="vo" property="loginname"/></td>

        <td><bean:write name="vo" property="lcoinremain"/></td>
        
        <td><bean:write name="vo" property="status"/></td>

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
          changeComplexSearch("complexSearchDiv","<bean:write name="tenantForm" property="complexSearchDivStatus"/>");
          setListTableStyle();
       }
     //-->
  </script>
  </body>
</html:html>
