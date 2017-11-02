<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant,platform.constant.CustomerConstant"%>
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
	
	<style>
	    
		.userinput_div{
		    width: 80%;
			float:left;
			overflow: auto;
		}
		
		.field_left{
			width:30%;
			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 252px;
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
		    display:inline-block;
			background-image:url(../styles/imgs/icon_success.gif);
			width:14px;
		}
		
		.login_fieldDesc_wrong{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
		
		.login_fieldDesc_wrong i{
		    display:inline-block;
			background-image:url(../styles/imgs/icon_error.gif);
			width:14px;
		}

		.input_on{
		   border:2px solid #FF99CC;
		   width: 250px;
		   font-size: larger;
		}
		
		.right_style{
		    padding-left:10px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
		
	</style>
	
    <script type='text/javascript' src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="../styles/script/region/region.js"></script>
    <script type='text/javascript'>
	   
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	   }
	   
	   function checkUser(nameId){
	      var input = document.getElementById(nameId).value;
		  var tipObj = document.getElementById(nameId+"_tip");
		  var tdObj = document.getElementById(nameId+"_tip_td");
		  if(input==null||input==''){
		     tdObj.className = "login_fieldDesc_wrong";
		     tipObj.innerHTML="请输入登录名";
		  }else if(input=='peter'){
		     tdObj.className = "login_fieldDesc_right";
		     tipObj.innerHTML="";
			 
		  }else if(input!='peter'){
		     tdObj.className = "login_fieldDesc_wrong";
		     tipObj.innerHTML="登录名必须是peter";
		  }
	   }
	   
	</script>
    
  </head>
  
  <body>
	<div >
	<html:form styleId="editForm" action="/customers/user.do?method=save" method="post">
     <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	 <input type="hidden" name="contactinfo.contactinfoid" value="<bean:write name="userForm" property="contactinfo.contactinfoid"/>"/> 
	
	  <div id="fieldsTitleDiv" style="background-color:#eeeeee;font-size:25px;">用户注册</div>
	  
	  <div class="userinput_div">
	  
	     <table cellspacing="7" cellpadding="0" border="0" width="100%">
		    <tr>
			   <td class="field_left">登录名:</td>
			   <td class="login_field"><input id="nameId" type="text" class="user_input" name="vo.loginname" value="" onchange="checkUser('nameId');" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="nameId_tip_td" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="nameId_tip">4 - 20 字母,数字,下划线</span></td>
			</tr>
			<tr>
			   <td class="field_left">姓名:</td>
			   <td class="login_field"><input type="text" name="vo.name" class="user_input" value="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip">不大于64位字符, 约20个汉字</span></td>
			</tr>
			<tr>
			   <td class="field_left">密码:</td>
			   <td class="login_field"><input type="password" name="vo.pass" class="user_input" value="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip">不少于4位</span></td>
			</tr>
			<tr>
			   <td class="field_left">再次输入密码:</td>
			   <td class="login_field"><input type="password" name="pass2" class="user_input" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left">电子邮件:</td>
			   <td class="login_field"><input type="text" name="contactinfo.email" class="user_input" value="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip">常用邮箱,用于通知您最新消息</span></td>
			</tr>
			<tr>
			   <td class="field_left" >所在国家:</td>
			   <td class="login_field">
			       <html:select name="userForm" property="vo.localeid" styleClass="user_input">
					 <html:optionsSaas localeListSet="true"/>
			       </html:select>
			   </td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left" >所在地区:</td>
			   <td class="login_field">
			       <select id="provinceId" name="province" onchange="selectRegion()"></select>(省)&nbsp;&nbsp;&nbsp;
                   <select id="cityId" name="vo.regioncode"></select>(市)
			   </td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left">所在时区:</td>
			   <td class="login_field">
			       <select name="vo.timezone" id="timezoneId" ></select>
			   </td>
			   <td id="" class="login_fieldDesc"></td>
			</tr>
			<tr>
			   <td class="field_left">性别:</td>
			   <td class="login_field">
			      <label for="gender_id_male">
			          <input id="gender_id_male" type="radio" name="vo.gender" value="1"> 男&nbsp;&nbsp;&nbsp;
			      </label>
			      <label for="gender_id_female">
			          <input id="gender_id_female" type="radio" name="vo.gender" value="2"> 女&nbsp;&nbsp;&nbsp;
			      </label>
			   </td>
			   <td id="" class="login_fieldDesc"></td>
			</tr>
			<tr>
			   <td class="field_left">出生年份:</td>
			   <td class="login_field">
			       <input type="text" name="vo.age" class="user_input" value="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left">验证码:</td>
			   <td class="login_field">
			       <input type="text" name="validateCode" class="user_input" value="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </td>
			   <td id="" class="login_fieldDesc"><i>&nbsp;&nbsp;</i><span id="_tip"></span></td>
			</tr>
			
			<tr>
			   <td colspan="3" style="padding-top:30px;padding-bottom:30px;padding-left:35%;"><button style="font-size:25px;">立即注册</button></td>
			</tr>
		 </table>

      </div>

	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	<script language="JavaScript" type="text/JavaScript">
    <!-- 
    
        var localeidVar = '<bean:write name="userForm" property="vo.localeid"/>';
        loadTimezoneList('timezoneId');
        initRegion(localeidVar, '','');
    //-->
    </script>

  </body>
</html:html>
