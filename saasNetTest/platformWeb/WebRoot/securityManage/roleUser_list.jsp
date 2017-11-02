<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>角色用户列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/checkform.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script language="javascript">
	<!--  
       function selectCallBack(ids){
          if(ids!=null&&ids!=""){
             document.getElementById("userIds").value=ids;
          }
          clearDiv();
          document.getElementById("list").action="saveRoleUser.do";
          document.forms[0].submit();
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listRoleUser.do" method="post">
  <input type="hidden" name="rolesVO.id" value="<bean:write name="userRoleForm" property="rolesVO.id"/>"/>
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userRoleForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userRoleForm" property="backUrlEncode"/>">
  <input id="userIds" type="hidden" name="userIds" value="">
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">登录名: 
		 <input type="text" name="queryVO.loginname" value="<bean:write name="userRoleForm" property="queryVO.loginname"/>"/>
		 <input type="submit" name="Submit" value="查询" />
      </div>
  </div>

  <div class="topContentDiv">
	 &gt;&gt;&gt;角色名：<bean:write name="userRoleForm" property="rolesVO.name"/>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <li><button type="button" onclick="newDiv('/securityManage/listUsersForRoleAdd.do?queryVO.roleId=<bean:write name="userRoleForm" property="rolesVO.id"/>',1,1,600,400);return false;">新增用户</button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleteRoleUser.do','请选择一条记录!','确定要删除吗?');return false;">删除</button></li>
		     <li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
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
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>登录帐号</td>

        <td>用户名</td>

        <td>用户类型</td>

        <td>用户状态</td>

      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td>
           <input type="checkbox" name="keys" value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td>
        <a href="javascript:newDiv('/securityManage/viewUserPage.do?queryVO.userid=<bean:write name="vo" property="userid"/>',0,1,600,400);">
           <bean:write name="vo" property="loginname"/>
        </a>
        </td>

        <td><bean:write name="vo" property="name"/></td>

        <td><bean:write name="vo" property="usertype"/></td>

        <td><bean:write name="vo" property="status"/></td>

      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
	       setListTableStyle();
	   }
     //-->
  </script>
  </div>
  </body>
</html:html>
