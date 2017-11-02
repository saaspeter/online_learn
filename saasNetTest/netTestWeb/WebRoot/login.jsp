<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonWeb.base.KeyInMemConstBase,platform.social.constant.SocialoathtokenConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<% java.util.Locale locale = (java.util.Locale)session.getAttribute(KeyInMemConstBase.SessionKey_LocaleUserSelect);
   String localestr = (locale==null)?"":locale.toString(); %>

<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/>-用户登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style>
	    body{ margin:0px }
	    input{
	       border: 1px solid #eeeeee;
	    }
	
		.message_div{
		    width: 52%;
			float:left;
			margin: 0px;
			margin-top: 50px;
			overflow: auto;
			padding: 10px;
		}
		
		.message_div table tr td{
		    margin: 20px;
		}
		
		.login_div{
			float:left;
			border-left: solid 1px #eeeeee;
			background-color: #DCEAFC;
			width: 40%;
			margin: 20px;
			margin-top: 40px;
			padding:20px;
		}
		
		.login_table{
		    width: 100%;
			font-size: larger;
		}
		
		.message_table{
		    font-size: 20px;
		}
		
		.message_table tr{
		    margin: 12px;
		}
		
		.login_input{
			width: 95%;
			padding-left:5px;
			font-size: 1.5em;
			border: 1px solid grey;
		}
		
		.center-block {
		    float: none;
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
      <div class="col-xs-12 col-md-9 center-block">
	  <jsp:include flush="true" page="/index/banner_short.jsp"></jsp:include>
	  
	  <div class="message_div hidden-xs">
	  
	     <table class="message_table">
             <tr>
                <td style="height:20px">&nbsp;</td>
             </tr>
             <tr>
                <td>&bull;海量培训课程学习</td>
             </tr>
             <tr>
                <td>&bull;自由选择培训机构</td>
             </tr>
             <tr>
                <td>&bull;互动分享学习</td>
             </tr>
             
             <tr>
                <td style="height:10px">&nbsp;</td>
             </tr>
             <tr>
                <td>&bull;开设单位机构自己的学习平台</td>
             </tr>
             <tr>
                <td>&bull;搭建单位自己的知识分享库</td>
             </tr>
 			 <tr>
                <td height="20px;"></td>
             </tr>
             <tr>
                <td style="font: bold;padding-left: 50px;"><a href="#">了解更多</a></td>
             </tr>
             <tr>
                <td height="40px;"></td>
             </tr>
             <tr>
                <td style="font: bold;padding-left: 200px;"><a href="customers/adduser.do">注册新帐号</a></td>
             </tr>
          </table>

      </div>
      
      <div class="login_div">
          <form action="${pageContext.request.contextPath}/spring_security_login" method="post">
             <input name="urltype" type="hidden" value="login">
			 <table class="login_table">
			    <tr>
					<td style="font-size: x-large;text-align: center;">登录</td>
				</tr>
				<tr>
					<td style="height: 1.5em;"></td>
				</tr>
				<tr>
					<td style="font: bold;">用户名/电子邮件:</td>
				</tr>
				<tr>
					<td><input id="inputusername_id" class="login_input" type="text" id="username" name="j_username" value="${SPRING_SECURITY_LAST_USERNAME}"/></td>
				</tr>
				<tr>
					<td style="height: 1.5em;"></td>
				</tr>
				<tr>
					<td style="font: bold;">密码:</td>
				</tr>
				<tr>
					<td><input id="inputpass_id" class="login_input" type="password" id="password" name="j_password" value=""/></td>
				</tr>
				<tr>
					<td style="height: 1.5em;">
					    <logic:notEmpty name="userserviceForm" property="message">
					       <font color="#ff0000">&hearts;&nbsp;
					       <bean:write name="userserviceForm" property="message"/>
					       </font><br>
					    </logic:notEmpty>
					</td>
				</tr>
				<tr>
				    <td>
				        <button type="submit" style="cursor: pointer;font-size: larger;font-weight: bold;border: 1px #eeeeee; background: #55FF55;padding:10 30 10 30px;margin-left:1.5em; margin-right:1.5em;">登录</button>
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
	<div style="height: 100px;"></div>
	<jsp:include flush="true" page="/foot.jsp"></jsp:include>
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
</html:html>
