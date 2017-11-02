<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="accountvalidatetaskForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="accountvalidatetaskForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.user.accountvalidatetask_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/user/saveAccountvalidatetask.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="accountvalidatetaskForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="accountvalidatetaskForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="accountvalidatetaskForm" property="vo.id"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.userid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.userid" value="<bean:write name="accountvalidatetaskForm" property="vo.userid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.email"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.email" value="<bean:write name="accountvalidatetaskForm" property="vo.email"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.validatetype"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.validatetype" value="<bean:write name="accountvalidatetaskForm" property="vo.validatetype"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.validatecode"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.validatecode" value="<bean:write name="accountvalidatetaskForm" property="vo.validatecode"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.status"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.status" value="<bean:write name="accountvalidatetaskForm" property="vo.status"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.createdate"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.createdate" value="<bean:write name="accountvalidatetaskForm" property="vo.createdate"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.aliveenddate"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.aliveenddate" value="<bean:write name="accountvalidatetaskForm" property="vo.aliveenddate"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.activedate"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.activedate" value="<bean:write name="accountvalidatetaskForm" property="vo.activedate"/>" <%=disableStr %> />
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
