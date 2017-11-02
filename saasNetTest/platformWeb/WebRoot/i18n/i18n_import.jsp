<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">

<head>
  <html:base />
  <title>导入语言国家列表</title>
  <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
</head>

<body>

	<div class="editPage">
	<html:form styleId="import" action="/i18n/i18n.do?method=importExcel" method="post" enctype="multipart/form-data">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="i18nForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="i18nForm" property="backUrlEncode"/>">
	  
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitForm('import')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;">&nbsp;导入</button></li>
				 <li><button type="button" onclick="goUrl('/i18n/i18n.do?method=list');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			     <li><a href="locales.xls" target="_blank">excel模板文件下载</a></li>
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
	
	  <div id="fieldsTitleDiv">导入语言国家列表</div>
	  
	  <div id="displayMessDiv"></div>
	
	  <div id="fieldDisDiv">
	     <ul>
		    <li>
			   <div class="fieldLabel">请选择导入文件:</div>
			   <div class="field"> 
			       <input type="file" name="xmlFile" usage="notempty,excel" style="width:80%">
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
			<li><button type="button" onclick="if(submitForm('import')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;">导入</button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	    </html:form>
	</div>

</body>
</html:html>
