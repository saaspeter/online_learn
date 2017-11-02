<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>用户角色列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/checkform.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script language="javascript">
	<!--  
       function selectCallBack(ids){
          if(ids!=null&&ids!=""){
             document.getElementById("roleIds").value=ids;
          }
          clearDiv();
          document.getElementById("list").action="/securityManage/saveUserRole.do";
          document.forms[0].submit();
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listUserRole.do" method="post">
  <input type="hidden" name="usersVO.userid" value="<bean:write name="userRoleForm" property="usersVO.userid"/>"/>
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userRoleForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userRoleForm" property="backUrlEncode"/>">
  <input id="roleIds" type="hidden" name="roleIds" value="">
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
  </div>

  <div class="topContentDiv">
	 用户名:<bean:write name="userRoleForm" property="usersVO.name"/> 登录名:<bean:write name="userRoleForm" property="usersVO.loginname"/>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <li><button type="button" onclick="newDiv('/securityManage/listRolesForUserAdd.do?queryVO.userId=<bean:write name="userRoleForm" property="usersVO.userid"/>',1,1,600,500);return false;">新增角色</button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleteUserRole.do','请选择一条记录!','确定要删除吗?');return false;">删除角色</button></li>
			 <li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
		  </ul>
	  </div>
	  	  
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>角色</td>

        <td>描述</td>
      </tr>
      <logic:present name="userRoleForm" property="roleList">
	  <logic:iterate id="vo" name="userRoleForm" property="roleList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>       
        <td>
           <bean:write name="vo" property="name"/>
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
