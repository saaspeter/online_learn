<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="acegi.page.{[#folder#]}.menus_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/{[#folder#]}/menus.do?method=save" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="acegi.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="acegi.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.title"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.title"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.target"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.target"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.onclick"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.onclick"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.onmouseover"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.onmouseover"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.onmouseout"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.onmouseout"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.image"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.image"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.altimage"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.altimage"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.tooltip"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.tooltip"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.page"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.page"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.width"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.width"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.height"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.height"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.forward"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.forward"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.action"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.action"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.menus.menutype"/>:</div>
			   <div class="field"> 
			      <bean:write name="menusForm" property="vo.menutype"/>
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
