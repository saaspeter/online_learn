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
		    width: 100%;
			clear: both;
			overflow: auto;
		}
		
		.userinput_div ul{
		   list-style: none;
		   margin:20px;
		}
		
		.login_left{
			float:left;
			clear:left;
			width:38%;
			padding-right: 15px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			float:left;
			align:left;
			text-align:left;
			margin:2px;

		}
		
		.user_input{
            width: 250px;
			font-size: larger;
		}
		
		.login_fieldDesc{
			float:left;	
			clear:right;
		}
		
	</style>
	
    <script type='text/javascript' src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="../styles/script/region/region.js"></script>
  </head>
  
  <body>
	<div >
	<html:form styleId="editForm" action="/customers/user.do?method=save" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userForm" property="backUrlEncode"/>">	
     <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	 <input type="hidden" name="contactinfo.contactinfoid" value="<bean:write name="userForm" property="contactinfo.contactinfoid"/>"/> 
	
	  <div id="fieldsTitleDiv" style="background-color:#eeeeee;">用户基本信息</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div class="userinput_div">
	     <ul>     
            <li>
			   <div class="login_left">登录名:</div>
			   <div class="login_field"> 
 		          <input id="nameId" type="text" class="user_input" name="vo.loginname" value="" onchange="checkUser('nameId');"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="login_left">**姓名:</div>
			   <div class="login_field"> 
			     <input type="text" name="vo.name" class="user_input" value=""/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="login_left">密码:</div>
			   <div class="login_field"> 
			     <input type="password" name="vo.pass" class="user_input" value=""/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
            <li>
			   <div class="login_left">再次输入密码:</div>
			   <div class="login_field"> 
			     <input type="password" name="pass2" class="user_input"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="login_left">电子邮件:</div>
			   <div class="login_field"> 
			     <input type="text" name="contactinfo.email" class="user_input" value=""/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="login_left">所在国家:</div>
			   <div class="login_field"> 
			      <html:select name="userForm" property="vo.localeid">
					 <html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="login_left">所在地区:</div>
			   <div class="login_field"> 
			      省份 <select id="provinceId" name="province" onchange="selectRegion()"></select>　
                     城市 <select id="cityId" name="vo.regioncode"></select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

			<li>
			   <div class="login_left">所在时区:</div>
			   <div class="login_field"> 
			      <select name="vo.timezone" id="timezoneId"></select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
				
			<li>
			   <div class="login_left">性别:</div>
			   <div class="login_field"> 
			      <label for="gender_id_male">
			          <input id="gender_id_male" type="radio" name="vo.gender" value="1"> 男&nbsp;&nbsp;&nbsp;
			      </label>
			      <label for="gender_id_female">
			          <input id="gender_id_female" type="radio" name="vo.gender" value="2"> 女&nbsp;&nbsp;&nbsp;
			      </label>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="login_left">出生年份:</div>
			   <div class="login_field"> 
			     <input type="text" name="vo.age" class="user_input" value=""/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		    
         </ul>
      </div>
	  
	  <div style="text-align: center;">
	     <button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return false;"><bean:message key="platform.commonpage.save"/></button>
	     &nbsp;&nbsp;&nbsp;
	     <button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button>
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
