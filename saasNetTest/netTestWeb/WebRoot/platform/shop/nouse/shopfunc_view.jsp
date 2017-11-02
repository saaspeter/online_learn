<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.{[#folder#]}.shopfunc_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/{[#folder#]}/shopfunc.do?method=save" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.shopid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.shopid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.functionid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.functionid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.functioncode"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.functioncode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.paytype"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.paytype"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.systemid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.systemid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.cost"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.cost"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.ispay"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.ispay"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.startdate"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.startdate"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.enddate"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.enddate"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.version"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.version"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.shopfunc.status"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopfuncForm" property="vo.status"/>
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
