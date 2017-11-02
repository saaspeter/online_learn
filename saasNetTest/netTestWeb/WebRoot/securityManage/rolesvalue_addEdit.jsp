<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑角色名称</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/securityManage/rolesvalue.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="rolesvalueForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="rolesvalueForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.valueid" value="<bean:write name="rolesvalueForm" property="vo.valueid"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="rolesvalueForm" property="vo.id"/>"/> 
	 <input type="hidden" name="vo.shopid" value="<bean:write name="rolesvalueForm" property="vo.shopid"/>"/> 
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="submitForm('editForm');"><bean:message key="platform.commonpage.save" bundle="platform.pagemess"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset" bundle="platform.pagemess"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title='<bean:message key="platform.commonpage.help" bundle="platform.pagemess"/>'><img src="../styles/imgs/help.jpg"></a>
		  </div>

	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord" bundle="platform.pagemess"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">语言设置:</div>
			   <div class="field"> 
			     <input type="text" name="vo.localeid" value="<bean:write name="rolesvalueForm" property="vo.localeid"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">角色名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="rolesvalueForm" property="vo.name"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="platform.commonpage.save" bundle="platform.pagemess"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset" bundle="platform.pagemess"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back" bundle="platform.pagemess"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
