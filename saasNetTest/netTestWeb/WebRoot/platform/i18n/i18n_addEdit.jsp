<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增语言国家</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>

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
				 <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">
		      <bean:message key="netTest.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="netTest.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
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
			   <div style="float:left;text-align:left;padding-left: 20px;"> 
			     <label for="issetId1">
			     <html:radio styleId="issetId1" name="i18nForm" property="vo.isset" value="<%=String.valueOf(CommonConstant.no)%>">不生效</html:radio>
			     </label>
			     <label for="issetId2">
			     <html:radio styleId="issetId2" name="i18nForm" property="vo.isset" value="<%=String.valueOf(CommonConstant.yes)%>">生效</html:radio>
			     </label>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">是否是默认设置:</div>
			   <div style="float:left;text-align:left;padding-left: 20px;"> 
			     <label for="isdefaultsetId1">
			     <html:radio styleId="isdefaultsetId1" name="i18nForm"  property="vo.isdefaultset" value="<%=String.valueOf(CommonConstant.no)%>">不是默认设置</html:radio>
			     </label>
			     <label for="isdefaultsetId2">
			     <html:radio styleId="isdefaultsetId2" name="i18nForm"  property="vo.isdefaultset" value="<%=String.valueOf(CommonConstant.yes)%>">默认设置</html:radio>
			     </label>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
