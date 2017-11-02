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
  <html:form styleId="list" action="/securityManage/listRoles.do">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="rolesForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="rolesForm" property="backUrlEncode"/>">

  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">角色名: 
		<input type="text" name="queryVO.name" value="<bean:write name="rolesForm" property="queryVO.name"/>"/>
		角色类型:
		<html:select name="rolesForm" property="queryVO.isdefaultset" >
		   <html:option value=""/>
           <html:optionsSaas consCode="common.Roles.isdefaultset" />
        </html:select>
		<input type="submit" name="Submit" value="查询" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <authz:authorize ifAllGranted="Role_SysAdmin"></authz:authorize>
		        <li><button type="button" onclick="addRecord('/securityManage/addRolePage.do');return false;">新增</button></li>
			    <li><button type="button" onclick="modifyRecord('list','keys','/securityManage/editRolePage.do?queryVO.id=','请选择一条记录修改','一次只能选择一条记录修改');return false;">修改</button></li>
			    <li><button type="button" onclick="delRec('list','keys','/securityManage/deleteRole.do');return false;">删除</button></li>
			    <li><button type="button" onclick="modifyRecord('list','keys','/securityManage/listRoleUser.do?rolesVO.id=','请选择一个角色','一次只能选择一个角色');return false;">角色用户管理</button></li>
		        <li><button type="button" onclick="modifyRecord('list','keys','/securityManage/roleResources_main.jsp?roleId=','请选择一个角色','一次只能选择一个角色');return false;">角色权限管理</button></li>
		     
		  </ul>
	  </div>
	 
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
        <td>角色名</td>
        <td>code</td>
        <td>操作</td>
        <td>说明</td>
      </tr>
      </thead>
      <logic:present name="rolesForm" property="roleList">
	  <logic:iterate id="vo" name="rolesForm" property="roleList">
      <tr>
        <td><input type="checkbox" id='pkId[<bean:write name="vo" property="id"/>]' name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td>
	        <a href="javascript:newDiv('/securityManage/viewRolePage.do?queryVO.id=<bean:write name="vo" property="id"/>',0,1,600,400);return false;">
	           <bean:write name="vo" property="name"/></a>
	        <input id='pkId[<bean:write name="vo" property="id"/>]Name' type="hidden" value="<bean:write name="vo" property="name"/>"/>
        </td>
        
        <td><bean:write name="vo" property="code"/></td>
        
        <td>
            <a href="editRolePage.do?queryVO.id=<bean:write name="vo" property="id"/>">修改</a>&nbsp;|&nbsp;
            <a href="listRoleUser.do?rolesVO.id=<bean:write name="vo" property="id"/>">角色用户</a>&nbsp;|&nbsp;
            <a href="roleResources_main.jsp?roleId=<bean:write name="vo" property="id"/>">角色权限</a>&nbsp;|&nbsp;
        </td>
        <td><bean:write name="vo" property="descn"/></td>
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
