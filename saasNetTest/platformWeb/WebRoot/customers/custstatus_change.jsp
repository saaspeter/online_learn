<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.KeyInMemoryConstant"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>客户状态变更</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script language="JavaScript" type="text/JavaScript">
    <!--
       function submitPage(){
          var orginStatus = document.getElementById("orginStatus").value;
          var statusObj = document.getElementById("statusId");  
          var nowStatus = statusObj.options[statusObj.selectedIndex].value;
          if(orginStatus==nowStatus){
             showMessagePage("请选择改变后的状态!");
             return;
          }
          if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);}
       }
       
    //-->
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/customers/savestatus.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="baseuserForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="baseuserForm" property="backUrlEncode"/>">	
     <input type="hidden" name="vo.userid" value="<bean:write name="baseuserForm" property="vo.userid"/>">
	 <input type="hidden" id="orginStatus" value="<bean:write name="baseuserForm" property="vo.status"/>"/>
	  <div id="functionLineDiv">
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv" style="background-color:#eeeeee;">客户信息</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
            <li>
			   <div class="fieldLabel">客户帐号:</div>
			   <div class="field"> 
			       <bean:write name="baseuserForm" property="vo.loginname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

            <li>
			   <div class="fieldLabel">客户名称:</div>
			   <div class="field"> 
			     <bean:write name="baseuserForm" property="vo.name"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
		
			
			<li>
			   <div class="fieldLabel">所在国家:</div>
			   <div class="field"> 
			     <html:select name="baseuserForm" property="vo.localeid" disabled="true">
					<html:optionsCollection name="baseuserForm" property="localesList"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
					
			<li><div style="text-align:center;width:100%;background-color:#eeeeee;">用户状态</div></li>
			
			<li>
			   <div class="fieldLabel">原状态:</div>
			   <div class="field"> 
			     <bean:write name="baseuserForm" property="orginStatusName"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">改变后状态:</div>
			   <div class="field"> 
			   	 <html:select name="baseuserForm" property="vo.status" styleId="statusId">
					<html:optionsCollection name="baseuserForm" property="customerStatusList"/>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPage();"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>

  </body>
</html:html>
