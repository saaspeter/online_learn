<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>
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
	
	<style>
	    body{ margin:0px }
	    input{
	       border: 1px solid #eeeeee;
	    }
	
		.userinput_div{
		    width: 75%;
			float:left;
			overflow: auto;
		}
		
		.field_left{
			width:285px;
			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 251px;
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
			background-image:url(../styles/imgs/icon_success.gif);
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
			background-image:url(../styles/imgs/icon_error.gif);
			background-repeat: no-repeat;
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
	      checkElement(obj,1);
	   }
	   
	   g_check.showErrType = 1;
	   
	   function checkUser(nameId){
          var nameObj = document.getElementById(nameId);
	  	  var result = null;
	  	  var retArr = null;
	  	  var message = "";
	      if(nameObj!=null&&nameObj.value!=''){
	        dwr.engine.setAsync(false);
	        customer.isExists(nameObj.value,function CB_checkUser(rtn){
	           if(rtn==null||rtn==true){
	           	  message = nameObj.value+","+getMessage("loginnameDepu");
			      result = false;
			   }else if(rtn!=null&&rtn==false){
			      message = getMessage("loginnameCanuse");
			      result = true;
	           }else{
	           	  alert(getMessage("systemError"));
	           }
	           retArr = new Array(result, message);
	        });
	      }
	      return retArr;
	   }
	   
	   function checkPass(id1, id2){
	      var pass1 = document.getElementById(id1).value;
	      var pass2 = document.getElementById(id2).value;
	      var result = true;
	      var message = '';
	      if(pass2!=pass1){
	         result = false;
	         message = "两次输入的密码不相同";
	      }
	      var retArr = new Array(result, message);
	      return retArr;
	   }
	   
	   
	   function refresh_jcaptcha(obj){
	   	  obj.src="<%= request.getContextPath()%>/jcaptcha?" + Math.random();
	   }

	</script>
    
  </head>
  
  <body>
	<div>
	<jsp:include flush="true" page="../index/banner_short.jsp"></jsp:include>
	<div id="fieldsTitleDiv" class="centerstyle" style="margin-top:15px; margin-bottom:15px; padding:5px; background-color:#DCEAFC;font-size:25px; text-align: center">用户注册</div>
	<html:form styleId="editForm" action="/customers/registeruser.do" method="post">
     <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	 <input type="hidden" name="contactinfo.contactinfoid" value="<bean:write name="userForm" property="contactinfo.contactinfoid"/>"/> 
	  
	  <div class="userinput_div">
	  
	     <table cellspacing="7" cellpadding="0" border="0" width="100%">
		    <tr>
			   <td class="field_left"><font color="#ff0000">*</font>登录名:</td>
			   <td class="login_field"><input id="loginnameId" type="text" class="user_input" name="vo.loginname" value="" usage="notempty" exp="^\S{0,60}$" fct="checkUser('loginnameId')" tip="4 - 20 字母,数字,下划线" fctMsg="该名称已被注册" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="loginnameId_tip_td" class="login_fieldDesc"><i></i><span id="loginnameId_tip">4 - 20 字母,数字,下划线</span></td>
			</tr>
			<tr>
			   <td class="field_left">姓名:</td>
			   <td class="login_field"><input id="nameId" type="text" name="vo.name" class="user_input" value="" exp="^\S{0,60}$" tip="不大于64位字符, 约20个汉字" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="nameId_tip_td" class="login_fieldDesc"><i></i><span id="nameId_tip">不大于64位字符, 约20个汉字</span></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>密码:</td>
			   <td class="login_field"><input id="passwordId" type="password" name="vo.pass" class="user_input" value="" usage="notempty" exp="^\S{4,20}$" tip="4 - 20位密码" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="passwordId_tip_td" class="login_fieldDesc"><i></i><span id="passwordId_tip">4 - 20位密码</span></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>再次输入密码:</td>
			   <td class="login_field"><input id="pass2Id" type="password" name="pass2" class="user_input" fct="checkPass('pass2Id','passwordId')" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="pass2Id_tip_td" class="login_fieldDesc"><i></i><span id="pass2Id_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>电子邮件:</td>
			   <td class="login_field"><input id="emailId" type="text" name="contactinfo.email" class="user_input" value="" usage="notempty,email" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="emailId_tip_td" class="login_fieldDesc"><i></i><span id="emailId_tip">常用邮箱,用于通知您最新消息</span></td>
			</tr>
			<tr>
			   <td class="field_left" ><font color="#ff0000">*</font>所在国家:</td>
			   <td class="login_field">
			       <html:select name="userForm" property="vo.localeid" styleClass="user_input">
					 <html:optionsSaas localeListSet="true"/>
			       </html:select>
			   </td>
			   <td id="" class="login_fieldDesc"><i></i><span id="_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left" >所在地区:</td>
			   <td colspan="2">
			      <table>
			          <tr>
			             <td>
			                 <select id="provinceId" name="province" onchange="selectRegion()" class="font_input"></select>(省)&nbsp;&nbsp;&nbsp;
                             <select id="cityId" name="contactinfo.regioncode" class="font_input"></select>(市)
                         </td>
			             <td id="cityId_tip_td" class="login_fieldDesc"><i></i><span id="cityId_tip"></span></td>
			          </tr>
			      </table>
			   </td>
			</tr>
			<tr>
			   <td class="field_left">所在时区:</td>
			   <td class="login_field">
			       <select name="vo.timezone" id="timezoneId" class="user_input"></select>
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
			       <input id="birthdayId" type="text" name="vo.birthday" class="user_input" value="" usage="int-range:1900:2011" tip="出生年份,在1900到2011之间" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </td>
			   <td id="birthdayId_tip_td" class="login_fieldDesc"><i></i><span id="birthdayId_tip">出生年份,在1900到2011之间</span></td>
			</tr>
			<tr>
			   <td></td>
			   <td colspan="2">
			       <img name ="jcaptcha" id ="jcaptcha" height="70px" onclick ="refresh_jcaptcha(this)" src ="<%= request.getContextPath()%>/jcaptcha" alt ="click to refresh" style ="cursor:pointer;"/>
			   </td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>字符验证:</td>
			   <td class="login_field">
			       <input id="validateCodeId" type="text" name="jcaptcha_response" usage="notempty" tip="请输入上面图片中的文字" class="user_input" value="" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </td>
			   <td id="validateCodeId_tip_td" class="login_fieldDesc"><span id="validateCodeId_tip">请输入上面图片中的文字</span></td>
			</tr>
			
			<tr>
			   <td colspan="3" style="padding-top:30px;padding-bottom:30px;padding-left:35%;">
			      <button style="font-size:25px;" onclick="submitForm('editForm');return false;">立即注册</button>
			   </td>
			</tr>
		 </table>

      </div>
      
      <div style="float:left;border-left: solid 1px #eeeeee;background-color: #DCEAFC;width: 180px;">
          <table cellspacing="7" cellpadding="0" border="0" width="100%">
             <tr>
                <td height="20px;"></td>
             </tr>
             <tr>
                <td style="font-size:25px;">已有帐号</td>
             </tr>
             <tr>
                <td>
                    <button style="font-size:25px;">请登录</button>
                </td>
             </tr>
             <tr>
                <td style="height:20px">&nbsp;</td>
             </tr>
             <tr>
                <td>开设自家培训机</td>
             </tr>
             <tr>
                <td>搭建公司自己学习平台</td>
             </tr>
             <tr>
                <td style="height:10px">&nbsp;</td>
             </tr>
             <tr>
                <td>海量培训课程学习</td>
             </tr>
             <tr>
                <td>自由选择培训机构</td>
             </tr>
             <tr>
                <td>自由互动交流学习</td>
             </tr>
 			 <tr>
                <td height="20px;"></td>
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
        // load validateCode check
        <%if("0".equals(validatecodeCheck)){  %>
           document.getElementById("validateCodeId_tip_td").className = "login_fieldDesc_wrong";
           document.getElementById("validateCodeId_tip").innerHTML = "验证码输入错误，请重试";
        <%}  %>
    //-->
    </script>

  </body>
</html:html>
