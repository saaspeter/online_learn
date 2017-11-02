<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="commonTool.page.{[#folder#]}.sysconstitem_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/commonTool/{[#folder#]}/sysconstitem.do?method=save" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="commonTool.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="commonTool.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.sysconstitem.sysconstid"/>:</div>
			   <div class="field"> 
			      <bean:write name="sysconstitemForm" property="vo.sysconstid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.sysconstitem.localeid"/>:</div>
			   <div class="field"> 
			      <bean:write name="sysconstitemForm" property="vo.localeid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.sysconstitem.value"/>:</div>
			   <div class="field"> 
			      <bean:write name="sysconstitemForm" property="vo.value"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.sysconstitem.orderno"/>:</div>
			   <div class="field"> 
			      <bean:write name="sysconstitemForm" property="vo.orderno"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.sysconstitem.pid"/>:</div>
			   <div class="field"> 
			      <bean:write name="sysconstitemForm" property="vo.pid"/>
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
