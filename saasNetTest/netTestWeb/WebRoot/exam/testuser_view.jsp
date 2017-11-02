<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.exam.testuser_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/exam/saveTestuser.do" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.shopid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.shopid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.testid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.testid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.userid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.userid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.username"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.username"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.testtype"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.testtype"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.paperid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.paperid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.starttime"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.starttime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.lefttime"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.lefttime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.suspendtesttime"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.suspendtesttime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.resumetesttime"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.resumetesttime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.endtime"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.endtime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.objectscore"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.objectscore"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.totalscore"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.totalscore"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.isqualify"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.isqualify"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testuser.status"/>:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="vo.status"/>
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
