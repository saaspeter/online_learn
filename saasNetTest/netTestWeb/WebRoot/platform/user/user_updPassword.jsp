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
	      var url = "<%=WebConstant.WebContext%>/customers/updatepassword.do?";
	      var oldpassword = document.getElementById("oldpasswordId").value;
	      var newpassword = document.getElementById("newpasswordId").value;
          var param = "oldpassword="+oldpassword+"&newpassword="+newpassword;
          var rtncheckArr = checkElement(document.getElementById("oldpasswordId"));
          if(!rtncheckArr[1]){
             return;
          }
          rtncheckArr = checkElement(document.getElementById("newpasswordId"));
          if(!rtncheckArr[1]){
             return;
          }
          rtncheckArr = checkElement(document.getElementById("newpassword2Id"));
          if(!rtncheckArr[1]){
             return;
          }
	      var rtnObj = toAjaxUrl(url, false, param);
          showMessagePage(rtnObj.info); 
          document.getElementById("oldpasswordId").value='';
          document.getElementById("newpasswordId").value='';
          document.getElementById("newpassword2Id").value='';
	   }
	   g_check.showErrType = 1;

	</script>
    
  </head>
  
  <body>
	<div class="centerstyle">
	<jsp:include flush="true" page='<%="/userAdmin/banner_user.jsp" %>'></jsp:include>
	
	<div class="navlistBar">
       <table width="97%" align="center">
          <tr><td>个人设置&gt;&gt;修改用户密码</td>
              <td align="right"></td>
          </tr>
       </table>
    </div>
	
	<div class="leftmenu">
       <ul id="nav">
          <li class="menu"><a href="javascript:void(0);" onclick="goUrl('/customers/viewuser.do');return false;">基本信息</a></li>
          <li class="press"><a href="javascript:void(0);" onclick="goUrl('/platform/user/user_updPassword.jsp');return false;">修改密码</a></li>
       </ul>
    </div>
	
	<div class="centercontent">
	
	<div class="fieldsTitleDiv" style="margin-top:15px; margin-bottom:15px; padding:5px; background-color:#DCEAFC;font-size:25px; text-align: center">更新密码</div>
	
	<div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
    </div>
	
	<form id="editForm" action="/customers/viewuser.do" method="post">
     
	  <div class="userinput_div">
	  
	     <table cellspacing="7" cellpadding="5" border="0" width="100%">
		    <tr>
			   <td class="field_left"><font color="#ff0000">*</font>原密码:</td>
			   <td class="login_field"><input type="password" id="oldpasswordId" name="oldpassword" class="user_input" usage="notempty" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="oldpasswordId_tip_td" class="login_fieldDesc"><i></i><span id="oldpasswordId_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>新密码:</td>
			   <td class="login_field"><input type="password" id="newpasswordId" name="newpassword" class="user_input" usage="notempty" exp="^\S{4,20}$" tip="4 - 20位密码" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
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
