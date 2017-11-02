<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>
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
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/customers/saveuser.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userForm" property="backUrlEncode"/>">	
     <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="userForm" property="vo.usertype"/>"/>
	 <input type="hidden" name="contactinfo.contactinfoid" value="<bean:write name="userForm" property="contactinfo.contactinfoid"/>"/> 
	
	  <div id="fieldsTitleDiv" style="background-color:#eeeeee;">用户基本信息</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>     
            <li>
			   <div class="fieldLabel">登录名:</div>
			   <div class="field"> 
			      <logic:empty name="userForm" property="vo.userid">
			         <input id="nameId" type="text" name="vo.loginname" value="<bean:write name="userForm" property="vo.loginname"/>" onchange="checkUser('loginname');"/>
			      </logic:empty>
			      <logic:notEmpty name="userForm" property="vo.userid">
			         <bean:write name="userForm" property="vo.loginname"/>
			      </logic:notEmpty>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">**姓名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="userForm" property="vo.name"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">昵称:</div>
			   <div class="field"> 
			     <input type="text" name="custinfoex.nickname" value="<bean:write name="userForm" property="custinfoex.nickname"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在国家:</div>
			   <div class="field"> 
			     <html:select name="userForm" property="vo.localeid">
					<html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

			<li>
			   <div class="fieldLabel">所在时区:</div>
			   <div class="field"> 
			      <select name="vo.timezone" id="timezoneId"></select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">性别:</div>
			   <div class="field"> 
			      <html:select name="userForm" property="vo.gender" style="width:300px">
				     <html:option value=""></html:option>
					 <html:optionsSaas consCode="Sysconst.Gender"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">出生年月:</div>
			   <div class="field"> 
			     <input id="birthdayId" type="text" name="vo.birthday" class="user_input" value="" usage="int-range:1900:2011" tip="出生年份,在1900到2011之间" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>


            <li><div style="text-align:center;width:100%;background-color:#eeeeee;">用户联系信息</div></li>

		    <li>
			   <div class="fieldLabel">职业:</div>
			   <div class="field"> 
			     <input type="text" name="vo.occupation" value="<bean:write name="userForm" property="vo.occupation"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
						
		    <li>
			   <div class="fieldLabel">地址:</div>
			   <div class="field"> 
			     <input type="text" name="contactinfo.address" value="<bean:write name="userForm" property="contactinfo.address"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">电子邮件:</div>
			   <div class="field"> 
			     <input type="text" name="contactinfo.email" value="<bean:write name="userForm" property="contactinfo.email"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">电话:</div>
			   <div class="field"> 
			     <input type="text" name="contactinfo.telephone" value="<bean:write name="userForm" property="contactinfo.telephone"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">简述:</div>
			   <div class="field"> 
			     <input type="text" name="custinfoex.notes" value="<bean:write name="userForm" property="custinfoex.notes"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	<script language="JavaScript" type="text/JavaScript">
    <!-- 
        var defaultTimezone = '<bean:write name="userForm" property="vo.timezone"/>';
        loadTimezoneList('timezoneId', defaultTimezone);
    //-->
    </script>
  </body>
</html:html>
