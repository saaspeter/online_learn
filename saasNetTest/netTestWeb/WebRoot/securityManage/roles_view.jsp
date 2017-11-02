<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>角色编辑</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	 <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="rolesForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="rolesForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="rolesForm" property="vo.id"/>">
	  <div id="functionLineDiv">
		  <div id="help">
		       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">角色编辑</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
		    <li>
			   <div class="fieldLabel">角色名:</div>
			   <div class="field"> 
			     <bean:write name="rolesForm" property="vo.name"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">角色描述:</div>
			   <div class="field"> 
			     <bean:write name="rolesForm" property="vo.descn"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
