<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="gender" name="userForm" property="vo.gender" type="java.lang.Short"/>
    <title>用户信息编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/leftMenu.css">
	<style>
	    body{ margin:0px }
	    input{
	       border: 1px solid #eeeeee;
	    }
	
		.userinput_div{
		    width: 100%;
			overflow: auto;
		}
		
		.field_left{
			width:200px;
			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 301px;
			font-size: larger;
		}
		
		.user_input{
            width: 300px;
			font-size: larger;
			border:#aaaaaa 1px solid;
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
		   width: 300px;
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
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezoneUtil.js"></script>
    <script type='text/javascript' src="../../styles/script/region/region.js"></script>
    <script type='text/javascript'>
	   
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }
	   
	   g_check.showErrType = 1;   

	</script>
    
  </head>
  
  <body>
	<div class="centerstyle">
	<jsp:include flush="true" page='<%="/userAdmin/banner_user.jsp" %>'></jsp:include>
	
	<div class="navlistBar">
       <table width="97%" align="center">
          <tr><td>个人设置&gt;&gt;用户基本信息</td>
              <td align="right"><a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../../styles/imgs/help.jpg"></a></td>
          </tr>
       </table>
    </div>
	
	<div class="leftmenu">
       <ul id="nav">
          <li class="press"><a href="javascript:void(0);" onclick="goUrl('/customers/viewuser.do');return false;">基本信息</a></li>
          <li class="menu"><a href="javascript:void(0);" onclick="goUrl('/platform/user/user_updPassword.jsp');return false;">修改密码</a></li>
       </ul>
    </div>
	
	<div class="centercontent">
	
	<div class="fieldsTitleDiv" style="margin-top:15px; margin-bottom:15px; padding:5px; background-color:#DCEAFC;font-size:25px; text-align: center">修改用户信息</div>
	<html:form styleId="editForm" action="/customers/saveuser.do" method="post">
     <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	 <input type="hidden" name="contactinfo.userid" value="<bean:write name="userForm" property="contactinfo.userid"/>"/> 
	  
	  <div class="userinput_div">
	  
	     <table cellspacing="7" cellpadding="5" border="0" width="100%">
		    <tr>
			   <td class="field_left">登录名:</td>
			   <td class="login_field"><bean:write name="userForm" property="vo.loginname"/></td>
			   <td id="loginnameId_tip_td" class="login_fieldDesc"></td>
			</tr>
			<tr>
			   <td class="field_left">姓名:</td>
			   <td class="login_field">
			       <input id="nameId" type="text" name="vo.displayname" class="user_input" value="<bean:write name="userForm" property="vo.displayname"/>" usage="notempty,max-length:60" tip="不能为空,小于64位字符(约20个汉字)" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </td>
			   <td id="nameId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="nameId_tip">不能为空,小于64位字符(约20个汉字)</span></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000">*</font>电子邮件:</td>
			   <td class="login_field"><bean:write name="userForm" property="vo.email"/></td>
			   <td id="emailId_tip_td" class="login_fieldDesc"></td>
			</tr>
			<tr>
			   <td class="field_left"><font color="#ff0000"></font>电话:</td>
			   <td class="login_field"><input id="telephoneId" type="text" name="contactinfo.telephone" class="user_input" value="<bean:write name="userForm" property="contactinfo.telephone"/>" usage="telphone" tip="格式 +86 0102222222" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/></td>
			   <td id="telephoneId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="telephoneId_tip">格式 +86 0102222222</span></td>
			</tr>
			<tr>
			   <td class="field_left" ><font color="#ff0000">*</font>所在国家:</td>
			   <td class="login_field">
			       <bean:writeSaas name="userForm" property="vo.localeid" showLocaleName="true"/>
			   </td>
			   <td id="" class="login_fieldDesc"><i></i><span id="_tip"></span></td>
			</tr>
			<tr>
			   <td class="field_left" >所在地区:</td>
			   <td colspan="2">
			      <table style="border:0px;">
			          <tr>
			             <td>
			                 <select id="provinceId" name="privCode" onchange="selectRegion()" class="font_input"></select>(省)&nbsp;&nbsp;&nbsp;
                         </td>
                         <td id="cityId_container_id" style="text-align: left;">
                             <select id="cityId" name="cityCode" class="font_input"></select>
                         </td>
			             <td id="cityId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="cityId_tip"></span></td>
			          </tr>
			      </table>
			   </td>
			</tr>
			<tr>
			   <td class="field_left">所在时区:</td>
			   <td colspan="2">
			       <select name="vo.timezone" id="timezoneId" class="font_input" style="width: 350px;"></select>
			   </td>
			</tr>
			<tr>
			   <td class="field_left">性别:</td>
			   <td class="login_field">
			      <label for="gender_id_male">
			          <input id="gender_id_male" type="radio" name="vo.gender" <%if(gender!=null&&"1".equals(gender.toString())){ out.print("checked=\"checked\""); } %> value="1"> 男&nbsp;&nbsp;&nbsp;
			      </label>
			      <label for="gender_id_female">
			          <input id="gender_id_female" type="radio" name="vo.gender" <%if(gender!=null&&"2".equals(gender.toString())){ out.print("checked=\"checked\""); } %> value="2"> 女&nbsp;&nbsp;&nbsp;
			      </label>
			   </td>
			   <td id="" class="login_fieldDesc"></td>
			</tr>
			<tr>
			   <td class="field_left">出生年份:</td>
			   <td class="login_field">
			       <input id="birthdayId" type="text" name="custinfoex.birthday" class="user_input" value="<bean:write name="userForm" property="custinfoex.birthday"/>" usage="int-range:1900:2011" tip="出生年份,在1900到2011之间" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </td>
			   <td id="birthdayId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="birthdayId_tip">出生年份,在1900到2011之间</span></td>
			</tr>
			
			<tr>
			   <td colspan="3" style="padding-top:30px;padding-bottom:30px;padding-left:25%;">
			      <button style="font-size:25px;" onclick="submitForm('editForm');return false;">保存修改</button>
			      &nbsp;&nbsp;&nbsp;&nbsp;
			      <button style="font-size:25px;" onclick="goUrl('/customers/viewuser.do');return false;">取消</button>
			   </td>
			</tr>
		 </table>

      </div>
	  <div id="buttomDiv"></div>
	</html:form>
	
	</div>
	</div>
	<script language="JavaScript" type="text/JavaScript">
    <!-- 
        var localeidVar = '<bean:write name="userForm" property="vo.localeid"/>';
        loadTimezoneList('timezoneId','<bean:write name="userForm" property="vo.timezone"/>');
        initRegion(localeidVar, '<bean:write name="userForm" property="privCode"/>','<bean:write name="userForm" property="cityCode"/>');

    //-->
    </script>

  </body>
</html:html>
