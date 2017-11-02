<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看国家语言</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="listPage">
	<html:form styleId="editForm" action="/i18n/i18n.do?method=save" method="post">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;">返回</button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title='<bean:message key="platform.commonpage.help" bundle="platform.pagemess"/>'><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">
		      <bean:message key="platform.commonpage.toUrl" bundle="platform.pagemess"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="platform.commonpage.listPage" bundle="platform.pagemess"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="errorDisplayDiv"></div>
	
	  <div id="fieldsTitleDiv">查看国家语言</div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">语言:</div>
			   <div class="field"> 
			      <bean:write name="i18nForm" property="vo.language"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">国家:</div>
			   <div class="field"> 
			      <bean:write name="i18nForm" property="vo.country"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">是否有效:</div>
			   <div class="field"> 
			      <bean:write name="i18nForm" property="vo.isset"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">是否是默认设置:</div>
			   <div class="field"> 
			      <bean:write name="i18nForm" property="vo.isdefaultset"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
			<li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;">返回</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
