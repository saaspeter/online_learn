<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.exam.testdept_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/exam/saveTestdept.do" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.shopid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.shopid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.orgbaseid"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.orgbaseid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.orgname"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.orgname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptuserset"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptuserset"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptquaper"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptquaper"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptavescore"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptavescore"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptusernum"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptusernum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptselfusernum"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptselfusernum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdepttestingnum"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdepttestingnum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptendnum"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptendnum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.testdeptselfendnum"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.testdeptselfendnum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.exam.testdept.status"/>:</div>
			   <div class="field"> 
			      <bean:write name="testdeptForm" property="vo.status"/>
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
