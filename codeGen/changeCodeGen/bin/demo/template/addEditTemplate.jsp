<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="{[#Project#]}Web.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="{[#actionName#]}Form" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="{[#actionName#]}Form" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="{[#Project#]}.page.{[#folder#]}.{[#jspName#]}_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/{[#folder#]}/save{[#className#]}.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="{[#actionName#]}Form" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="{[#actionName#]}Form" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.{[#pkName#]}" value="<bean:write name="{[#actionName#]}Form" property="vo.{[#pkName#]}"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="{[#Project#]}.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              {[#addEditColumnsDefines#]}
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="submitForm('editForm');"><bean:message key="{[#Project#]}.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="{[#Project#]}.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="{[#Project#]}.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
