<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="acegi.page.{[#folder#]}.resources_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/{[#folder#]}/resources.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.id" value="<bean:write name="resourcesForm" property="vo.id"/>">
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
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.name"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="resourcesForm" property="vo.name"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.resType"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.resType" value="<bean:write name="resourcesForm" property="vo.resType"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.resString"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.resString" value="<bean:write name="resourcesForm" property="vo.resString"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.syscode"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.syscode" value="<bean:write name="resourcesForm" property="vo.syscode"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.upid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.upid" value="<bean:write name="resourcesForm" property="vo.upid"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.ordno"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.ordno" value="<bean:write name="resourcesForm" property="vo.ordno"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.status"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.status" value="<bean:write name="resourcesForm" property="vo.status"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.resources.descn"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.descn" value="<bean:write name="resourcesForm" property="vo.descn"/>"/>
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
