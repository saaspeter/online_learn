<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userForm" property="jsSuffix" type="java.lang.String"/>
    <title>用户信息编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="../../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" media="screen" href="../../styles/css/list.css" />
	<style>
	    body{ margin:0px }
	    input{
	       border: 1px solid #eeeeee;
	    }
		
		.right_style{
		    padding-left:10px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
		
	</style>
	
    <script type='text/javascript'>
	   
	</script>
    
  </head>
  
  <body>
	<div class="col-xs-12 col-md-9 center-block">
	<jsp:include flush="true" page="../../index/banner.jsp"></jsp:include>
	<div id="fieldsTitleDiv" style="margin-top:20px;margin-bottom:20px;background-color:#eeeeee;font-size:25px;text-align: center">成功注册</div>
	 <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	  
	  <div style="text-align: center;">
	  
	                我们已经给您发送一封欢迎邮件，您现在可以<a href="<%=WebConstant.WebContext %>/tologin.do">登陆</a>系统

      </div>
      
	  <div id="buttomDiv"></div>
	</div>

  </body>
</html:html>
