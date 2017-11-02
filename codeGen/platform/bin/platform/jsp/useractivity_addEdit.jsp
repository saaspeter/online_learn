<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="useractivityForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="useractivityForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.user.useractivity_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/user/saveUseractivity.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="useractivityForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="useractivityForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.activityid" value="<bean:write name="useractivityForm" property="vo.activityid"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.useractivity.userid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.userid" value="<bean:write name="useractivityForm" property="vo.userid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.useractivity.actiontype"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.actiontype" value="<bean:write name="useractivityForm" property="vo.actiontype"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.useractivity.targetobject"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.targetobject" value="<bean:write name="useractivityForm" property="vo.targetobject"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.useractivity.result"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.result" value="<bean:write name="useractivityForm" property="vo.result"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.useractivity.note"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.note" value="<bean:write name="useractivityForm" property="vo.note"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.useractivity.createtime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.createtime" value="<bean:write name="useractivityForm" property="vo.createtime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="submitForm('editForm');"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
