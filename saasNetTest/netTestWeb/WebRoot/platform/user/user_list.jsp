<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>用户信息列表</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/customers/listuser.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userForm" property="backUrlEncode"/>">	
  <div id="firstLineDiv">
      
      <div id="searchDiv">用户名: 
		<input type="text" name="queryVO.displayname" value="<bean:write name="userForm" property="queryVO.displayname"/>"/>
		客户状态:
		<html:select name="userForm" property="queryVO.status">
		    <html:option value=""></html:option>
		    <html:optionsSaas consCode="platform.CustomerConstant.UserStatus"/>
		</html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/customers/adduser.do');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
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
        <td width="20px"></td>
        
        <td>用户名</td>

        <td>帐号</td>
                
        <td>用户状态</td>
        <td></td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        
        <td><a href="javascript:void(0)" onclick="goUrl('/customers/viewprofile.do?vo.userid=<bean:write name="vo" property="userid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:write name="vo" property="displayname"/></a></td>

        <td><bean:write name="vo" property="loginname"/></td>
        
        <td><bean:writeSaas name="vo" property="status" consCode="platform.CustomerConstant.UserStatus"/></td>

		<td>
		    <img src="../../styles/imgs/edit.png" style="cursor:pointer;" title="修改用户状态" onclick="goUrl('/customers/statuschangepage.do?queryVO.userid=<bean:write name="vo" property="userid"/>&backUrlEncode=','backUrlEncode');return false;"/>
		    &nbsp;&nbsp;
		    <img src="../../styles/imgs/delete.png" style="cursor:pointer;" title="删除" onclick="goUrl('/customers/deleteuser.do?vo.userid=<bean:write name="vo" property="userid"/>');return false;"/>
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
