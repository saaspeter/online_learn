<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.user.accountvalidatetask_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/user/saveAccountvalidatetask.do" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.userid"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.userid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.email"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.email"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.validatetype"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.validatetype"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.validatecode"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.validatecode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.status"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.createdate"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.createdate"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.aliveenddate"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.aliveenddate"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.user.accountvalidatetask.activedate"/>:</div>
			   <div class="field"> 
			      <bean:write name="accountvalidatetaskForm" property="vo.activedate"/>
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
