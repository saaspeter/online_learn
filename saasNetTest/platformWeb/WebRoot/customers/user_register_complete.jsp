<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="validatecodeCheck" name="userForm" property="validatecodeCheck" type="java.lang.String"/>
    <title>用户信息编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="../styles/css/list_style.css" />
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
		
		.centerstyle{
		    width:1000px;
		    left: 50%;
			position:relative;
			clear: both;
			margin-left: -500px;
		}
		
	</style>
	
    <script type='text/javascript' src="../styles/script/pageAction.js"></script>
    <script type='text/javascript'>
	   


	</script>
    
  </head>
  
  <body>
	<div>
	<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
	<div id="fieldsTitleDiv" class="centerstyle" style="margin-top:20px;margin-bottom:20px;background-color:#eeeeee;font-size:25px;text-align: center">成功注册</div>
	 <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	  
	  <div class="userinput_div">
	  
	    

      </div>
      
      
	  <div id="buttomDiv"></div>
	</div>

  </body>
</html:html>
