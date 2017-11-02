<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/addshopvalue.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopvalueForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopvalueForm" property="backUrlEncode"/>">	
	 <input type="hidden" name="vo.shopvalueid" value="<bean:write name="shopvalueForm" property="vo.shopvalueid"/>"/>
	 <input type="hidden" name="vo.shopid" value="<bean:write name="shopvalueForm" property="vo.shopid"/>"/>
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  
	  </div>
	
	  <div id="fieldsTitleDiv">新增记录</div>
	  
	  <div id="displayMessDiv">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">注册国家:</div>
			   <div class="field"> 
			     <%boolean isAdd = true; %>
			     <logic:empty name="shopvalueForm" property="vo.shopvalueid"><%isAdd=false;%></logic:empty>
			     <html:select name="shopvalueForm" property="vo.localeid" disabled="<%=isAdd %>">
			        <html:optionsCollection name="shopvalueForm" property="localesList"/>
		         </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">商店名:</div>
			   <div class="field"> 
			     <input type="text" name="vo.shopname" value="<bean:write name="shopvalueForm" property="vo.shopname"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>		

		    <li>
			   <div class="fieldLabel">商店详细介绍:</div>
			   <div class="field"> 
			     <input type="text" name="vo.shopdesc" value="<bean:write name="shopvalueForm" property="vo.shopdesc"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			  <div id="fieldDisPlus"></div>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	<script language=JavaScript>
	 <!--
     //-->
    </script>
  </body>
</html:html>
