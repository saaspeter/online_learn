<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看系统参数</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script language=JavaScript src="../../styles/script/checkform.js"></script>
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
			  
	  <div id="help">
		  <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">参数编码：</div>
			   <div class="field"> 
			     <bean:write name="sysparamForm" property="vo.code"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">参数值：</div>
			   <div class="field"> 
			     <bean:write name="sysparamForm" property="vo.value"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所属分类：</div>
			   <div class="field"> 
			     <bean:write name="sysparamForm" property="vo.typecode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所属系统：</div>
			   <div class="field"> 
			     <bean:write name="sysparamForm" property="vo.syscode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">备注：</div>
			   <div class="field"> 
			     <bean:write name="sysparamForm" property="vo.notes"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
