<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="acegi.page.{[#folder#]}.user_role_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/{[#folder#]}/user_role.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="user_roleForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="user_roleForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.roleId" value="<bean:write name="user_roleForm" property="vo.roleId"/>">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="acegi.commonpage.save"/></button></li>
				 <li><button onclick="return false;"><bean:message key="acegi.commonpage.reset"/></button></li>
				 <li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="acegi.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="acegi.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">
		      <bean:message key="acegi.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="acegi.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="acegi.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.user_role.userId"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.userId" value="<bean:write name="user_roleForm" property="vo.userId"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.user_role.usetype"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.usetype" value="<bean:write name="user_roleForm" property="vo.usetype"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


			  <div id="fieldDisPlus"></div>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="acegi.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="acegi.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="acegi.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
