<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>用户列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
       function confirm(){
          var backObj = selectCheck('list','keys','2','','请选择要新加入的用户!');
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
  <html:form styleId="list" action="/securityManage/listUsersForRoleAdd.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="usersForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="usersForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	     <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">用户登录帐号: 
		<input type="text" name="queryVO.loginname" value="<bean:write name="usersForm" property="queryVO.loginname"/>"/>
		<input type="submit" name="Submit" value="查询" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="confirm();return false;">确定添加</button></li>
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
        <td>登录帐号</td>

        <td>登录密码</td>

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
           <bean:write name="vo" property="loginname"/>
        </td>

        <td><bean:write name="vo" property="pass"/></td>

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
