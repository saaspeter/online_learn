<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonToolWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="systemlogForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="systemlogForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="commonTool.page.biz.systemlog_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/biz/saveSystemlog.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="systemlogForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="systemlogForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.logid" value="<bean:write name="systemlogForm" property="vo.logid"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="commonTool.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.systemlog.logcode"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.logcode" value="<bean:write name="systemlogForm" property="vo.logcode"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.systemlog.createtime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.createtime" value="<bean:write name="systemlogForm" property="vo.createtime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.systemlog.endtime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.endtime" value="<bean:write name="systemlogForm" property="vo.endtime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.systemlog.result"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.result" value="<bean:write name="systemlogForm" property="vo.result"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="commonTool.page.{[#folder#]}.systemlog.content"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.content" value="<bean:write name="systemlogForm" property="vo.content"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="submitForm('editForm');"><bean:message key="commonTool.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="commonTool.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="commonTool.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
