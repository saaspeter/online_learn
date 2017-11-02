<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="true">
  <head>
    <html:base />

    <title>修改密码</title>
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

		.centerstyle{
		    width:1000px;
		    left: 50%;
			position:relative;
			clear: both;
			margin-left: -500px;
		}
		
		.font_input{
			font-size: larger;
		}
		
		.leftmenu{
		    float:left;
     	    margin: 20px 5px 5px 5px;
     	    width: 150px;
		}
		
		.centercontent{
		    float: left; margin: 5px;
		    width: 820px;
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
	   
	   function checkPass(id1, id2){
	      var pass1 = document.getElementById(id1).value;
	      var pass2 = document.getElementById(id2).value;
	      var result = true;
	      var message = '';
	      if(pass2==null||pass2==''){
	         result = false;
	         message = "密码不能为空";
	      }else if(pass2!=pass1){
	         result = false;
	         message = "两次输入的密码不相同";
	      }
	      var retArr = new Array(result, message);
	      return retArr;
	   }
	   
	   function updatePass(){
	      var newpassword = document.getElementById("newpasswordId").value;
          rtncheckArr = checkElement(document.getElementById("newpasswordId"));
          if(!rtncheckArr[1]){
             return;
          }
          rtncheckArr = checkElement(document.getElementById("newpassword2Id"));
          if(!rtncheckArr[1]){
             return;
          }
          submitForm("editForm");
	   }
	   g_check.showErrType = 1;

	</script>
    
  </head>
  
  <body>
	<div class="centerstyle">
	<jsp:include flush="true" page='<%="/index/banner_short.jsp" %>'></jsp:include>
	
	<div class="navlistBar">
       <table width="97%" align="center">
          <tr><td>更新用户密码</td>
              <td align="right"></td>
          </tr>
       </table>
    </div>
	
	<div class="centercontent">
	
	<div class="fieldsTitleDiv" style="margin-top:15px; margin-bottom:15px; padding:5px; background-color:#DCEAFC;font-size:25px; text-align: center">更新密码</div>
	
	<div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
    </div>
	
	<form id="editForm" action="${pageContext.request.contextPath}/customers/saveresetpassmail.do" method="post">
      <input type="hidden" name="validatecode" value="<bean:write name="accountvalidatetaskForm" property="validatecode"/>">
	  <div class="userinput_div">
	     
	     <table cellspacing="7" cellpadding="5" border="0" width="100%">
			<tr>
			   <td class="field_left"><font color="#ff0000"></font>用户email:</td>
			   <td class="login_field"><%=request.getParameter("email") %></td>
			   <td id="newpasswordId_tip_td" class="login_fieldDesc"><input name="email" type="hidden" value="<%=request.getParameter("email") %>"></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>新密码:</td>
			   <td class="login_field"><input type="password" id="newpasswordId" name="password" class="user_input" usage="notempty" exp="^\S{4,20}$" tip="4 - 20位密码" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="newpasswordId_tip_td" class="login_fieldDesc"><i></i><span id="newpasswordId_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>再次输入新密码:</td>
			   <td class="login_field"><input type="password" id="newpassword2Id" class="user_input" fct="checkPass('newpassword2Id','newpasswordId')" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="newpassword2Id_tip_td" class="login_fieldDesc"><i></i><span id="newpassword2Id_tip"></span></td>
			</tr>
			<tr>
			   <td colspan="3" style="padding-top:30px;padding-bottom:30px;padding-left:35%;">
			      <button style="font-size:25px;" onclick="updatePass();return false;">保存修改</button>
			   </td>
			</tr>
		 </table>

      </div>
	  <div id="buttomDiv"></div>
	</form>
	
	</div>
	</div>


  </body>
</html>
