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
  <form id="form1" action="j_acegi_cas_security_check" method="post">
    <div id="first"></div>
    <table>
        <tr><td>User:</td><td><input type='text' name='j_username'></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>
    <div></div>
  </form>
  </body>
</html:html>
