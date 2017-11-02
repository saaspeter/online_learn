<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant, netTestWeb.base.WebConstant, netTest.orguser.constant.OrguserConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="securitylevel" name="userRoleForm" property="securitylevel"></bean:define>
    <%String saveaction = "";
      String delaction = "";
      if("0".equals(securitylevel)){
    	  saveaction = "/shopuserManage/saveShopUserRole.do";
    	  delaction = "/shopuserManage/deleteShopUserRole.do";
      }else if("1".equals(securitylevel)){
    	  saveaction = "/securityManage/saveUserRole.do";
    	  delaction = "/securityManage/deleteUserRole.do";
      }
      %>
    <title>用户角色列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/orguserLogic.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script language="javascript">
	<!--  
       function selectCallBack(ids){
          if(ids!=null&&ids!=""){
             document.getElementById("roleIds").value=ids;
          }
          clearDiv();
          document.forms[0].submit();
       }
	//-->
	</script>
  </head>
  
  <body>
  <div>
  
  <html:form styleId="list" action="<%=saveaction %>" method="post">
  <input type="hidden" name="queryVO.userid" value="<bean:write name="userRoleForm" property="usersVO.userid"/>"/>
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userRoleForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userRoleForm" property="backUrlEncode"/>">
  <input id="roleIds" type="hidden" name="roleIds" value="">
  <input type="hidden" name="syscode" value="<%=CommonConstant.SysCode_Education %>">


  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <li><button type="button" onclick="newDiv('/shopuserManage/listShopRolesSel.do?queryVO.userId=<bean:write name="userRoleForm" property="usersVO.userid"/>&syscode=<%=CommonConstant.SysCode_Education %>',1,1,600,500);return false;">新增角色</button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','<%=delaction %>','请选择一条记录!','确定要删除吗?');return false;">删除角色</button></li>
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
        <td>角色</td>
        <td>描述</td>
      </tr>
      </thead>
      <logic:present name="userRoleForm" property="roleList">
	  <logic:iterate id="vo" name="userRoleForm" property="roleList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
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

  </html:form>
  </div>

  </body>
</html:html>
