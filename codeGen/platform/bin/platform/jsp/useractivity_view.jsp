<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.user.useractivity_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/user/saveUseractivity.do" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.useractivity.userid"/>:</div>
			   <div class="field"> 
			      <bean:write name="useractivityForm" property="vo.userid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.useractivity.actiontype"/>:</div>
			   <div class="field"> 
			      <bean:write name="useractivityForm" property="vo.actiontype"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.useractivity.targetobject"/>:</div>
			   <div class="field"> 
			      <bean:write name="useractivityForm" property="vo.targetobject"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.useractivity.result"/>:</div>
			   <div class="field"> 
			      <bean:write name="useractivityForm" property="vo.result"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.useractivity.note"/>:</div>
			   <div class="field"> 
			      <bean:write name="useractivityForm" property="vo.note"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.useractivity.createtime"/>:</div>
			   <div class="field"> 
			      <bean:write name="useractivityForm" property="vo.createtime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
