<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>角色列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
       function confirm(){
          var backObj = selectCheck('list','keys','2','Name','请选择上级角色!');
          if(backObj!=null){
             if(typeof(parent.selectCallBack)!="undefined"){
		        parent.selectCallBack(backObj['ids'],backObj['names']);
		     }
          }
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listRolesForUserAdd.do" method="post">
  <input type="hidden" name="queryVO.userId" value="<bean:write name="rolesForm" property="queryVO.userId"/>"/>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">角色名: 
		<input type="text" name="queryVO.name" value="<bean:write name="rolesForm" property="queryVO.name"/>"/>
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
        <td>角色名</td>
		<td>状态</td>
        <td>描述</td>
      </tr>
      <logic:present name="rolesForm" property="roleList">
	  <logic:iterate id="vo" name="rolesForm" property="roleList" indexId="ind">
      <tr>
        <td><input type="checkbox" id="upId<%=ind %>" name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td>
	        <a href="javascript:newDiv('/securityManage/viewRolePage.do?queryVO.id=<bean:write name="vo" property="id"/>',0,1,600,400);">
	           <bean:write name="vo" property="name"/>
	           <input type="hidden" id="upId<%=ind %>Name" value="<bean:write name="vo" property="name"/>">
	        </a>
        </td>
        
        <td><bean:writeSaas name="vo" property="status" consCode=""/></td>

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
