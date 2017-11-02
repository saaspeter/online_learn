<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="shopopenactivitymemberForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="shopopenactivitymemberForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.product.shopopenactivitymember_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveShopopenactivitymember.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopopenactivitymemberForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopopenactivitymemberForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.activityid" value="<bean:write name="shopopenactivitymemberForm" property="vo.activityid"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.userid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.userid" value="<bean:write name="shopopenactivitymemberForm" property="vo.userid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.displayname"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.displayname" value="<bean:write name="shopopenactivitymemberForm" property="vo.displayname"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.phone"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.phone" value="<bean:write name="shopopenactivitymemberForm" property="vo.phone"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.othercontactinfo"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.othercontactinfo" value="<bean:write name="shopopenactivitymemberForm" property="vo.othercontactinfo"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.registertime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.registertime" value="<bean:write name="shopopenactivitymemberForm" property="vo.registertime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.joinstatus"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.joinstatus" value="<bean:write name="shopopenactivitymemberForm" property="vo.joinstatus"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.note"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.note" value="<bean:write name="shopopenactivitymemberForm" property="vo.note"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
