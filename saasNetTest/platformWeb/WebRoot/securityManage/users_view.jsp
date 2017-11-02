<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>用户信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/acegi/securityManage/users.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="usersForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="usersForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.userid" value="<bean:write name="usersForm" property="vo.userid"/>">
	  <div id="functionLineDiv">
		  <div id="help">
		       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">用户信息</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">登录帐号:</div>
			   <div class="field"> 
			      <bean:write name="usersForm" property="vo.loginname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">登录密码:</div>
			   <div class="field"> 
			      <bean:write name="usersForm" property="vo.pass"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">用户名:</div>
			   <div class="field"> 
			      <bean:write name="usersForm" property="vo.name"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">用户类型:</div>
			   <div class="field"> 
			      <bean:write name="usersForm" property="vo.usertype"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">状态:</div>
			   <div class="field"> 
			      <bean:write name="usersForm" property="vo.status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
			<li><button type="button" onclick="parent.clearDiv();return false;">关闭</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
