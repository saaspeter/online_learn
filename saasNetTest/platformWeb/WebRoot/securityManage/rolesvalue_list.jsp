<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>角色列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listRolesvalue.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="rolesvalueForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="rolesvalueForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">角色名: 
		<input type="text" name="queryVO.name" value="<bean:write name="rolesvalueForm" property="queryVO.name"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="platform.commonpage.query"/>" />
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
			 <li><button type="button" onclick="addRecord('/securityManage/addrolesvalue.do');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/securityManage/editRolesvalue.do?queryVO.valueid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleterolesvalue.do','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete"/></button></li>
			 
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
        <td>资源名</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="valueid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><bean:write name="vo" property="name"/></td>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="rolesvalueForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
