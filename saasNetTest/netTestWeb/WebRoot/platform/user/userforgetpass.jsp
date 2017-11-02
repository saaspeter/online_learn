<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="true">
  <head>
    <html:base />
    <% Object attrObj = request.getAttribute("captchacodecheck"); 
       String captchacodecheck = (attrObj==null)?"":(String)attrObj;
    %>
    <title>忘记密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/leftMenu.css">
	<style>
	    body{ margin:0px }
	    input{
	       border: 1px solid #eeeeee;
	    }
	
		.userinput_div{
		    width: 100%;
			float:left;
			overflow: auto;
			margin: 70px;
		}
		
		.field_left{
			width:250px;
			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 251px;
			font-size: larger;
		}
		
		.user_input{
            width: 250px;
			font-size: larger;
			border:1px solid #bbbbbb;
		}
		
		.login_fieldDesc{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#999;
		}
		
		.login_fieldDesc_right{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#00ff00;
		}
		
		.login_fieldDesc_right i{
		    width:16px;
		    display:inline-block;
			background-image:url(../../styles/imgs/icon_success.gif);
			background-repeat: no-repeat;
		}
		
		.login_fieldDesc_wrong{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
		
		.login_fieldDesc_wrong i{
		    width:16px;
		    display:inline-block;
			background-image:url(../../styles/imgs/icon_error.gif);
			background-repeat: no-repeat;
		}

		.input_on{
		   border:2px solid #FF99CC;
		   width: 250px;
		   font-size: larger;
		}
		
		.font_input{
			font-size: larger;
		}
		
	</style>
	
    <script type='text/javascript' src="../../styles/script/pageAction.js"></script>
    <script type='text/javascript'>
       
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }
	   
	   g_check.showErrType = 1;
	   
	   function refresh_captcha(id){
	      var obj = document.getElementById(id);
	   	  obj.src="<%=request.getContextPath()%>/captcha.png?" + Math.random();
	   }
	</script>
    
  </head>
  
  <body>
	<div class="centerstyle">
	<jsp:include flush="true" page='<%="/index/banner_short.jsp" %>'></jsp:include>
		
	<div class="centerstyle">
	
	<div class="fieldsTitleDiv" >密码重置</div>
	
	<div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
    </div>
	
	<form id="editForm" action="${pageContext.request.contextPath}/customers/requestresetpassemail.do" method="post">
     
	  <div class="userinput_div">
	  
	     <table cellspacing="7" cellpadding="5" border="0" width="100%;margin-top:40px;">
		    <tr>
			   <td class="field_left"><font color="#ff0000">*</font>请输入注册时填写的email:</td>
			   <td class="login_field"><input type="text" id="emailId" name="email" class="user_input" usage="notempty,email" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="emailId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="emailId_tip"></span></td>
			</tr>
			<tr>
			   <td></td>
			   <td colspan="2">
			       <img name ="captcha" id ="captcha" height="70px" src ="<%= request.getContextPath()%>/captcha.png" alt ="click to refresh" style ="cursor:pointer;"/>
			       <a href="javascript:refresh_captcha('captcha');">看不清换一张</a>
			   </td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>请输入验证码:</td>
			   <td class="login_field"><input type="text" id="validationId" name="captcha_response" class="user_input" usage="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="validateCodeId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="validateCodeId_tip">请输入上面图片中的文字</span></td>
			</tr>
			<tr>
			   <td colspan="3" style="padding-top:60px;padding-bottom:30px;padding-left:35%;">
			      <button style="font-size:25px;" onclick="submitForm('editForm');return false;">继续</button>
			   </td>
			</tr>
		 </table>

      </div>
	  <div id="buttomDiv"></div>
	</form>
	
	</div>
	</div>

    <script language="JavaScript" type="text/JavaScript">
    <!-- 
        <%if("0".equals(captchacodecheck)){ %>
           document.getElementById("validateCodeId_tip_td").className = "login_fieldDesc_wrong";
           document.getElementById("validateCodeId_tip").innerHTML = "验证码输入错误，请重试";
        <%}  %>
    //-->
    </script>
  </body>
</html>
