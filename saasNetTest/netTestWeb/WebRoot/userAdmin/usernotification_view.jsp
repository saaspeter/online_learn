<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="canreply" name="usernotificationForm" property="canreply" type="java.lang.Integer"></bean:define>
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
	<div class="editPage">
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">标题:</div>
			   <div class="field"> 
			      <bean:write name="usernotificationForm" property="vo.messcode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">发送人:</div>
			   <div class="field"> 
			      <bean:write name="usernotificationForm" property="vo.creatorname"/>
			      (<bean:writeDate name="usernotificationForm" property="vo.createtime" format="yyyy-MM-dd HH:mm"/>)
			   </div>
			   <div class="fieldDesc"><input type="hidden" id="touseridId" value="<bean:write name="usernotificationForm" property="vo.touserid"/>"/></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">内容:</div>
			   <div class="field"> 
			      <bean:write name="usernotificationForm" property="vo.content"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  
	  <%if(canreply==1) { %>
	  <div id="functionBarButtomDiv">
	     <button type="button" onclick="goUrl('addnotification.do?touseridStr=<bean:write name="usernotificationForm" property="vo.creatorid"/>&touserloginame=<bean:write name="usernotificationForm" property="vo.creatorname"/>&vo.messcode=RE:<bean:write name="usernotificationForm" property="vo.messcode"/>');" style="bigbutton">回复</button>
	  </div>
	  <%} %>
	  
	  <div id="buttomDiv"></div>
	</div>
  </body>
</html:html>
