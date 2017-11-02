<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>客户状态变更</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../../styles/css/tab/simpleTab2.css" />
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>
    <script type="text/javascript" type="text/JavaScript">
    <!--
       function submitPage(){
          var orginStatus = document.getElementById("orginStatus").value;
          var statusObj = document.getElementById("statusId");  
          var nowStatus = statusObj.options[statusObj.selectedIndex].value;
          if(orginStatus==nowStatus){
             showMessagePage("请选择改变后的状态!");
             return;
          }
          submitForm('editForm');
       }
       
    //-->
    </script>
  </head>
  
  <body>
	<div class="editPage" style="width: 80%;margin-left: 10%">
	<html:form styleId="editForm" action="/customers/savestatus.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="userForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="userForm" property="backUrlEncode"/>">	
     <input type="hidden" name="vo.userid" value="<bean:write name="userForm" property="vo.userid"/>">
	 <input type="hidden" id="orginStatus" value="<bean:write name="userForm" property="vo.status"/>"/>
	
	  <div class="fieldsTitleDiv">管理用户:<bean:write name="userForm" property="vo.loginname"/></div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div style="height:auto; width:100%;">
	      <ul class="tabnav">
	          <li id="tab1" class='selectTab'><a href="javascript:void(0);" onclick="showTab(1,2)">用户状态</a></li>
		      <li id="tab2"><a href="javascript:void(0);" onclick="goUrl('/user/showuseraccountsetting.do?settingvo.userid=<bean:write name="userForm" property="vo.userid"/>&vo.loginname=<bean:write name="userForm" property="vo.loginname"/>');">用户功能管理</a></li>
	      </ul>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
            <li>
			   <div class="fieldLabel">客户帐号:</div>
			   <div class="field"> 
			       <bean:write name="userForm" property="vo.loginname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

            <li>
			   <div class="fieldLabel">客户名称:</div>
			   <div class="field"> 
			     <bean:write name="userForm" property="vo.displayname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		
			
			<li>
			   <div class="fieldLabel">所在国家:</div>
			   <div class="field"> 
			     <html:select name="userForm" property="vo.localeid" disabled="true">
					<html:optionsSaas localeListSet="true"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">原状态:</div>
			   <div class="field"> 
			     <bean:writeSaas name="userForm" property="vo.status" consCode="platform.CustomerConstant.UserStatus"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">改变后状态:</div>
			   <div class="field"> 
			   	 <html:select name="userForm" property="vo.status" styleId="statusId">
					<html:optionsSaas consCode="platform.CustomerConstant.UserStatus" param1="change"/>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">改变原因:</div>
			   <div class="field"> 
			   	  <input name="statuschangereason" value="" usage="max-length:120" fie="改变原因"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPage();"><bean:message key="netTest.commonpage.save"/></button></li>
			
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>

  </body>
</html:html>
