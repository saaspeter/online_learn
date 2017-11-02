<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,platformWeb.base.KeyInMemoryConstant" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增语言国家</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/i18n/i18n.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="i18nForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="i18nForm" property="backUrlEncode"/>">	
     <input type="hidden" name="vo.localeid" value="<bean:write name="i18nForm" property="vo.localeid"/>">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">
		      <bean:message key="platform.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="platform.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
	     <% String disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
            String disMessArg0Key = request.getAttribute(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessArg0Key));
            if(disMessKey!=null&&disMessKey.trim().length()>0){
            if(disMessArg0Key==null||disMessArg0Key.trim().length()<1){
         %>
         <bean:message key="<%=disMessKey %>" bundle="BizKey"/>
         <% }else{%>
         <bean:message key="<%=disMessKey %>" bundle="BizKey" arg0="<%=disMessArg0Key %>"/>
         <%}} %>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">语言:</div>
			   <div class="field"> 
			     <input type="text" name="vo.language" value="<bean:write name="i18nForm" property="vo.language"/>" exp="^\S{2}$" tip="语言代码，2位字符!" fie="语言代码"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">国家:</div>
			   <div class="field"> 
			     <input type="text" name="vo.country" value="<bean:write name="i18nForm" property="vo.country"/>" exp="^\S{2}$" tip="国家代码，2位字符!" fie="国家代码"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">是否生效:</div>
			   <div class="field"> 
			     <html:radio name="i18nForm" property="vo.isset" value="<%=String.valueOf(CommonConstant.no)%>">不生效</html:radio>
			     <html:radio name="i18nForm" property="vo.isset" value="<%=String.valueOf(CommonConstant.yes)%>">生效</html:radio>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">是否是默认设置:</div>
			   <div class="field"> 
			     <html:radio name="i18nForm"  property="vo.isdefaultset" value="<%=String.valueOf(CommonConstant.no)%>">不是默认设置</html:radio>
			     <html:radio name="i18nForm"  property="vo.isdefaultset" value="<%=String.valueOf(CommonConstant.yes)%>">默认设置</html:radio>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


			  <div id="fieldDisPlus"></div>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
