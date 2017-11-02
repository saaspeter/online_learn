<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>系统常量语言国家编辑</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../../styles/css/edit.css">
    <script language=JavaScript src="../../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/basicset/sysconstval.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysconstvalForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysconstvalForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.constvalueid" value="<bean:write name="sysconstvalForm" property="vo.constvalueid"/>">
	 <input type="hidden" name="vo.sysconstid" value="<bean:write name="sysconstvalForm" property="vo.sysconstid"/>">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="submitForm('editForm');return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">常量语言设置编辑</div>
	  
	  <div id="errorDisplayDiv"></div>

	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">所属语言地区:</div>
			   <div class="field"> 
			     <%boolean isAdd = true; %>
			     <logic:empty name="sysconstvalForm" property="vo.constvalueid"><%isAdd=false;%></logic:empty>
			     <html:select name="sysconstvalForm" property="vo.localeid"  disabled="<%=isAdd %>" style="width:300px;">
					<html:optionsCollection name="sysconstvalForm" property="localesList"/>
			     </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">常量名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" style="width:300px;" value="<bean:write name="sysconstvalForm" property="vo.name"/>" usage="notempty,max-length:40" tip="不能为空且不超过40个字!" fie="常量名称"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm'); return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
