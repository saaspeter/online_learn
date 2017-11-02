<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>用户信息列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/customers/listuser.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userForm" property="backUrlEncode"/>">	
    <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">用户名: 
		<input type="text" name="query.name" value="<bean:write name="userForm" property="query.name"/>"/>
		客户状态:
		<html:select name="userForm" property="query.status">
		    <html:option value=""></html:option>
			<html:optionsCollection name="userForm" property="statusList"/>
		</html:select>
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
		 <li><button type="button" onclick="addRecord('/customers/adduser.do');return false;"><bean:message key="platform.commonpage.add"/></button></li>
		 <li><button type="button" onclick="modifyRecord('list','keys','/customers/statuschangepage.do?queryVO.userid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;">用户状态变更</button></li>
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
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        
        <td>用户名</td>

        <td>帐号</td>
                
        <td>用户状态</td>
        <td></td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td><a href="#" onclick="goUrl('/customers/viewuser.do?vo.userid=<bean:write name="vo" property="userid"/>','backUrlEncode');return false;"><bean:write name="vo" property="name"/></a></td>

        <td><bean:write name="vo" property="loginname"/></td>
        
        <td><bean:write name="vo" property="status"/></td>

		<td>
		    <img src="../styles/imgs/edit.gif" style="cursor:pointer;" alt="修改" onclick="goUrl('/customers/edituser.do?vo.userid=<bean:write name="vo" property="userid"/>');return false;"/>
		    &nbsp;&nbsp;
		    <img src="../styles/imgs/delete.png" style="cursor:pointer;" alt="删除" onclick="goUrl('/customers/deleteuser.do?vo.userid=<bean:write name="vo" property="userid"/>');return false;"/>
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
          changeComplexSearch("complexSearchDiv","<bean:write name="userForm" property="complexSearchDivStatus"/>");
          setListTableStyle();
       }
     //-->
  </script>
  </body>
</html:html>
