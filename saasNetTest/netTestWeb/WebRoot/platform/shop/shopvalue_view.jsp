<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	
	  <div id="fieldsTitleDiv">查看记录</div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">注册国家:</div>
			   <div class="field"> 
			     <html:select name="shopvalueForm" property="vo.localeid" disabled="true">
			        <html:optionsCollection name="shopvalueForm" property="localesList"/>
		         </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">商店名:</div>
			   <div class="field"> 
			     <bean:write name="shopvalueForm" property="vo.shopname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">商店简介:</div>
			   <div class="field"> 
			     <bean:write name="shopvalueForm" property="vo.shopdescSim"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">商店详细介绍:</div>
			   <div class="field"> 
			     <bean:write name="shopvalueForm" property="vo.shopdesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
