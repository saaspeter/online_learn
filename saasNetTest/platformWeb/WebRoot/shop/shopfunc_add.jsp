<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.SysfunctionitemConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>添加功能</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/shopfunc.do?method=insert" method="post">
	 <bean:define id="locale" name="shopfuncForm" property="locale" type="java.util.Locale"></bean:define>
	 <bean:define id="funcArr" name="shopfuncForm" property="funcArr" type="java.lang.String[]"></bean:define>
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopfuncForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopfuncForm" property="backUrlEncode"/>">	
	 <input type="hidden" name="vo.shopid" value="<bean:write name="shopfuncForm" property="vo.shopid"/>">
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
		  <div id="toUrl">
		      <bean:message key="platform.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="platform.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
          	<table width="90%" align="center">
			   <tr bgcolor="#eeeeee"><td colspan="5">平台系统功能：</td></tr>
			       <logic:iterate id="vo" name="shopfuncForm" property="funcPlatList">
			          <bean:define id="funcode" name="vo" property="functioncode" type="java.lang.String"></bean:define>
			          <bean:define id="functionid" name="vo" property="functionid" type="java.lang.Long"/>
			          <bean:define id="systemid" name="vo" property="systemid" type="java.lang.String"/>
			          
			          <%
    	          	             boolean chose = false; 
    	          	             String functionidStr = functionid.toString();
    	          	             for(int i=0;i<funcArr.length;i++){
    	          	                if(functionidStr!=null&&functionidStr.equals(funcArr[i]))
    	          	                chose = true;
    	          	             }
			          %>
			          <tr>
			             <td>
			               <input type="checkbox" name="funcArr" value="<%=functionidStr%>" <%if(chose) out.print("checked=\"checked\"  disabled=\"disabled\""); %> >
			             </td>
			             <td><%=SysfunctionitemConstant.qryFuncName(funcode,locale)%></td>
			             <td><%=CommonConstant.qrySystemName(systemid,locale)%></td>
			             <td><bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/></td>
			             <td><bean:write name="vo" property="funcdesc"/></td>
			          </tr>
			       </logic:iterate>
			</table>
			<br>
			<table width="90%" align="center">
			   <tr bgcolor="#eeeeee"><td colspan="5">教育系统功能：</td></tr>
			   
			       <logic:iterate id="vo" name="shopfuncForm" property="funcEduList">
			          <bean:define id="funcode" name="vo" property="functioncode" type="java.lang.String"></bean:define>
			          <bean:define id="functionid" name="vo" property="functionid" type="java.lang.Long"/>
			          <bean:define id="systemid" name="vo" property="systemid" type="java.lang.String"/>
			          
			          <%
    	          	             boolean chose = false; 
    	          	             String functionidStr = functionid.toString();
    	          	             for(int i=0;i<funcArr.length;i++){
    	          	                if(functionidStr!=null&&functionidStr.equals(funcArr[i]))
    	          	                chose = true;
    	          	             }
			          %>
			           <tr>
				          <td>
				             <input type="checkbox" name="funcArr" value="<%=functionidStr%>" <%if(chose) out.print("checked=\"checked\"  disabled=\"disabled\""); %>>
				          </td>
				          <td><%=SysfunctionitemConstant.qryFuncName(funcode,locale)%></td>
				          <td><%=CommonConstant.qrySystemName(systemid,locale)%></td>
				          <td><bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/></td>
				          <td><bean:write name="vo" property="funcdesc"/></td>
			          </tr>
			       </logic:iterate>
			   
			</table>

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
  </body>
</html:html>
