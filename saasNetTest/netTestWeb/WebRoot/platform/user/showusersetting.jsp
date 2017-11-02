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
       
    //-->
    </script>
  </head>
  
  <body>
	<div class="editPage" style="width: 80%;margin-left: 10%">
	<html:form styleId="editForm" action="/user/updateuseraccountsetting.do" method="post">
     <input type="hidden" name="settingvo.userid" value="<bean:write name="userForm" property="settingvo.userid"/>">
	 
	  <div class="fieldsTitleDiv">管理用户:<bean:write name="userForm" property="vo.loginname"/></div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div style="height:auto; width:100%;">
	      <ul class="tabnav">
	          <li id="tab1"><a href="javascript:void(0);" onclick="goUrl('/customers/statuschangepage.do?queryVO.userid=<bean:write name="userForm" property="settingvo.userid"/>');">用户状态</a></li>
		      <li id="tab2" class='selectTab'><a href="javascript:void(0);">用户功能管理</a></li>
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
			   <div class="fieldLabel">已创建商店数目:</div>
			   <div class="field"> 
			      <bean:write name="userForm" property="settingvo.createdShopNum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		
			
			<li>
			   <div class="fieldLabel">正在申请商店数目:</div>
			   <div class="field"> 
			       <bean:write name="userForm" property="settingvo.inapplyShopNum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">创建商店功能:</div>
			   <div class="field"> 
			      <html:select name="userForm" property="settingvo.shopcreateable">
					 <html:optionsSaas consCode="platform.Useraccountsetting.Shopcreateable"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>

  </body>
</html:html>
