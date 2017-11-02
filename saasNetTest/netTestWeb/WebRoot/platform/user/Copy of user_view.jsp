<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.KeyInMemoryConstant,netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>用户信息编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
  </head>
  
  <body>
	<div class="editPage">
	<input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userForm" property="backUrlEncode"/>">	
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">用户信息</div>
	  
	  <div id="fieldDisDiv">
	     <ul>     
            <li>
			   <div class="fieldLabel">登录名:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="vo.loginname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">**姓名:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="vo.name"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">密码提示问题:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="custinfoex.tipquestion"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">问题答案:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="custinfoex.tipanswer"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">昵称:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="custinfoex.nickname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在国家:</div>
			   <div class="field"> 
			     <html:select name="userForm" property="vo.localeid" disabled="true">
					<html:optionsCollection name="userForm" property="localesList"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">帐户金额:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="vo.lcoinremain"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">性别:</div>
			   <div class="field"> 
			      <select>
			         <option></option>
			         <option>male</option>
			         <option>female</option>
			         <option>half male,half female</option>
			      </select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">年龄:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="vo.age"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
            <li><div style="text-align:center;width:100%;background-color:#dddddd;">用户状态</div></li>
            
            <li>
			   <div class="fieldLabel">用户状态:</div>
			   <div class="field"> 
			   	   <bean:write name="userForm" property="vo.status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

            <li><div style="text-align:center;width:100%;background-color:#dddddd;">用户联系信息</div></li>

		    <li>
			   <div class="fieldLabel">手机:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="contractinfo.cellphone"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">职业:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="vo.occupation"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">创建者:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="vo.creator"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
						
		    <li>
			   <div class="fieldLabel">地址:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="contractinfo.address"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">电子邮件:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="contractinfo.email"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">电话:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="contractinfo.telephone"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">简述:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="custinfoex.notes"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>

	</div>

  </body>
</html:html>
