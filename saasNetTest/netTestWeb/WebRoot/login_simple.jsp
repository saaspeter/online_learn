<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,commonWeb.base.KeyInMemConstBase"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<% java.util.Locale locale = (java.util.Locale)session.getAttribute(KeyInMemConstBase.SessionKey_LocaleUserSelect);
   String localestr = (locale==null)?"":locale.toString(); %>

<html>
  <head>
    <title>登录页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="free learning,online learing,online training">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	    
	    input{
	       border: 1px solid #eeeeee;
	    }
	    
	    .centerstyle{
		    width:98%;
			position:relative;
			clear: both;
			text-align: center;
		}
		
		.login_div{
			border-left: solid 1px #eeeeee;
			background-color: #DCEAFC;
			width: 280px;
			text-align: center;
			margin: auto;
			margin-top: 20px;
			margin-bottom: 15px;
			padding:10px;
		}
		
		.login_table{
		    width: 100%;
			font-size: larger;
		}
		
		.login_div table tr td{
		     margin: 15px;
		}
		
		.login_input{
			width: 95%;
			padding-left:5px;
			font-size: larger;
		}
		
	</style>
	
	<%if(localestr!=null && localestr.indexOf("zh_CN")!=-1){ %>
	<script type='text/javascript' src="styles/script/social/sociallogin_cn.js"></script>
	<%}else { %>
	<script type='text/javascript' src="styles/script/social/sociallogin.js"></script>
	<%} %>
	<script language='JavaScript' src='styles/script/timezone/timezoneUtil.js'></script>
	
	<script type='text/javascript'>
	   
	   function initInput(){
	       document.getElementById("inputusername_id").focus();
	   }

	</script>
  </head>
  <body onload="initInput()"> 
    <div class="centerstyle">
    
    <div id="logo">
	   <img src="/<%=CommonConstant.WebContext_Education %>/styles/imgs/logo_platform.jpg" name="logo" id="logo" onclick="document.location.href='<%="/"+CommonConstant.WebContext_Education %>';" title="回到主页" style="background-color: #33CCFF;cursor: pointer;" />
	</div>
    
    <div class="login_div">
	<form action="${pageContext.request.contextPath}/spring_security_login" method="post">
	    <input type="hidden" name="urltype" value="simple">
		<table class="login_table">
		    <tr>
				<td style="font-size: x-large;text-align: center;background-color: #DCEAFC;" colspan="2">登录</td>
			</tr>
			<tr>
				<td style="height: 20px;" colspan="2"></td>
			</tr>
			<tr>
				<td style="font: bold;">用户名/电子邮件:</td>
			</tr>
			<tr>
				<td><input id="inputusername_id" class="login_input" type="text" id="username" name="j_username" value="${SPRING_SECURITY_LAST_USERNAME}"/></td>
			</tr>
			<tr>
				<td style="font: bold;">密码:</td>
			</tr>
			<tr>
				<td><input id="inputpass_id" class="login_input" type="password" id="password" name="j_password" value=""/></td>
			</tr>
			<tr>
				<td style="height: 20px;">
				    <logic:notEmpty name="userserviceForm" property="message">
				       <font color="#ff0000">&hearts;&nbsp;
				       <bean:write name="userserviceForm" property="message"/>
				       </font><br>
				    </logic:notEmpty>
				</td>
			</tr>
			<tr>
			    <td style="text-align: center;">
			        <button type="submit" style="cursor: pointer;font-size: larger;font-weight: bold;border: 1px #eeeeee; background: #55FF55;padding:10px;">登录</button>
			        &nbsp;&nbsp;&nbsp;
			        <a href="platform/user/userforgetpass.jsp">无法访问帐号?</a>
			    </td>
			</tr>
			<%if(localestr!=null && localestr.indexOf("zh_CN")!=-1){ %>
				<tr>
				    <td style="padding-top: 20px;text-align: right;">
				        <span id="qqLoginBtn"></span>
						<script type="text/javascript">
						    QC.Login({
						       btnId:"qqLoginBtn",
						       scope:"all",
						       size: "A_L"
							}, function(reqData, opts){//登录成功
								//根据返回数据，更换按钮显示状态方法
								   var dom = document.getElementById(opts['btnId']),
								   _logoutTemplate=[
								   //头像
								   '<span><img src="{figureurl}" class="{size_key}"/></span>',
								    //昵称
								   '<span>{nickname}&nbsp;&nbsp; 登录系统中... </span>',
								     //退出
								   '<span><a href="javascript:QC.Login.signOut();">退出QQ</a></span>' 
								   ].join("");
								     dom && (dom.innerHTML = QC.String.format(_logoutTemplate, {
								      nickname : QC.String.escHTML(reqData.nickname),
								     figureurl : reqData.figureurl
								    }));
								    setTimeout(function(){onQQSignInCallback(reqData);}, 3000);
							}, function(opts){//注销成功
							      var msg="GoodBye,欢迎下次光临";
							      alert(msg); 
							});
						    
						    
						</script>
				        
				    </td>
				</tr>
				<%}else { %>
				<tr>
				    <td style="padding-top: 20px;text-align: right;">
				        <div id="gConnect">
						    <button type="button" class="g-signin"
						        data-scope="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email"
						        data-requestvisibleactions="http://schemas.google.com/AddActivity"
						        data-clientId='<bean:write name="userserviceForm" property="clientid_google"/>'
						        data-callback="onGoogleSignInCallback"
						        data-theme="dark"
						        data-cookiepolicy="single_host_origin">
						    </button>
						    <span id="loadingId" style="display:none;">loading to login ...</span>
						</div>
				    </td>
				</tr>
				<%} %>
		</table>
	</form>
	</div>
	</div>
	<script language="JavaScript">
        function getTimezoneStr(){
    	   var timezonestr = getCalculateTimezone();
    	   if(timezonestr!=null){
    		   timezonestr = timezonestr.replace('+', '%2B');
    	   }
    	   return timezonestr;
        }
        function getLocaleStr(){
           return '<%=localestr %>';
        }
    </script>
  </body>
</html>
