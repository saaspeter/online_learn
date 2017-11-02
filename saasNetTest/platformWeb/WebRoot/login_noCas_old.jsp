<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>login.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	<!--
	   #first{
	      height:250px;
	   }
	   
	   #loginMessage{
	      text-align:center;
	   }
	   
	   .line {
	      width:100%;
	      margin:10px auto;
	      text-align:center;
	   }
	-->
    </style>
    <script language=JavaScript>
       function onEnter(event)
		{
			if (event.keyCode == 13)
			{
				document.forms[0].submit();
			}
		}
       
    </script>

  </head>

  <body  onkeypress="javascript:onEnter(event)">
  <html:form styleId="form1" action="/login.do" method="post">
    <div id="first"></div>
    <div id="loginMessage"><font color="red"><html:messages id="error" bundle="BizKey" message="true"><li><bean:write name="error"/></li></html:messages></font></div>
    <div class="line">帐号:<input type="text" name="loginname" value="<bean:write name="loginForm" property="loginname"/>"></div>
    <div class="line">密码:<input type="password" name="pass"></div>
    <div class="line">类型:
        <html:select name="loginForm" property="userType">
			<html:optionsCollection name="loginForm" property="userTypeList"/>
	    </html:select>
	</div>
    <div class="line"><button type="button" onclick="document.forms[0].submit()">登录</button></div>
    <div></div>
  </html:form>
  </body>
</html:html>
