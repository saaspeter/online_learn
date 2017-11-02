<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>资源列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
       function confirm(){
          var backObj = selectCheck('list','keys','2','','请选择要新加入的资源!');
          if(backObj!=null){
             if(typeof(parent.selectCallBack)!="undefined"){
		        parent.selectCallBack(backObj['ids']);
		     }
          }
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listRescsForRoleAdd.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userRoleForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userRoleForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input type="hidden" name="queryVO.pid" value="<bean:write name="userRoleForm" property="queryVO.pid"/>"/>
  <input type="hidden" name="queryVO.roleId" value="<bean:write name="userRoleForm" property="queryVO.roleId"/>"/>
  
  <div id="firstLineDiv">
      <div id="help">
	     <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">资源名: 
		<input type="text" name="queryVO.rescname" value="<bean:write name="userRoleForm" property="queryVO.rescname"/>"/>
		<input type="submit" name="Submit" value="查询" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="confirm();return false;">确定添加</button></li>
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
        <td>资源名</td>

        <td>资源类型</td>

        <td>资源描述</td>
      </tr>
      <logic:present name="userRoleForm" property="rescList">
	  <logic:iterate id="vo" name="userRoleForm" property="rescList">
      <tr>
        <td>
            <input type="checkbox" name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')">
        </td>
        <td><bean:write name="vo" property="name"/></td>

        <td><bean:write name="vo" property="resType"/></td>

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
