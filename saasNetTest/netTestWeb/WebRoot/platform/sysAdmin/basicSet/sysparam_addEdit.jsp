<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑系统编码</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../../styles/css/edit.css">
    <script language=JavaScript src="../../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/basicset/sysparam.do?method=save" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysparamForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysparamForm" property="backUrlEncode"/>">	
	 <input type="hidden" name="vo.code" value="<bean:write name="sysparamForm" property="vo.code"/>">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="netTest.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
			  </ul>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">新增编辑</div>
	  
	  <div id="errorDisplayDiv"></div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">参数编码：</div>
			   <div class="field"> 
			     <bean:write name="sysparamForm" property="vo.code"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">参数值：</div>
			   <div class="field"> 
			     <input type="text" name="vo.value" value="<bean:write name="sysparamForm" property="vo.value"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所属分类：</div>
			   <div class="field"> 
			     <input type="text" name="vo.typecode" value="<bean:write name="sysparamForm" property="vo.typecode"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所属系统：</div>
			   <div class="field"> 
			      <bean:writeSaas name="sysparamForm" property="vo.syscode" consCode="Platform.SybsystemList"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">备注：</div>
			   <div class="field"> 
			     <input type="text" name="vo.notes" value="<bean:write name="sysparamForm" property="vo.notes"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <br><br>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
