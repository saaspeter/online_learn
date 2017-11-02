<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.product.shopopenactivitymember_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveShopopenactivitymember.do" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.userid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.userid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.displayname"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.displayname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.phone"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.phone"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.othercontactinfo"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.othercontactinfo"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.registertime"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.registertime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.joinstatus"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.joinstatus"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivitymember.note"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivitymemberForm" property="vo.note"/>
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
