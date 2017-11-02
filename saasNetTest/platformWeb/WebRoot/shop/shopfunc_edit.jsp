<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.ShopfuncConstant,commonTool.constant.CommonConstant,java.util.List" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店功能</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/shopfunc.do?method=save" method="post">
	<bean:define id="locale" name="shopfuncForm" property="locale" type="java.util.Locale"></bean:define>
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopfuncForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopfuncForm" property="backUrlEncode"/>">	
	 <input type="hidden" name="vo.shopfuncid" value="<bean:write name="shopfuncForm" property="vo.shopfuncid"/>">
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
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">功能名:</div>
			   <div class="field"> 
			     <bean:write name="shopfuncForm" property="vo.functionname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">支付类型:</div>
			   <div class="field"> 
			     <bean:writeSaas name="shopfuncForm" property="vo.paytype" consCode="Common.PayTypeConstant.PayType"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">所属系统:</div>
			   <div class="field"> 
			     <bean:define id="systemcode" name="shopfuncForm" property="vo.systemid" type="java.lang.String"/>
			     <%=CommonConstant.qrySystemName(systemcode,locale) %>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">功能费用:</div>
			   <div class="field"> 
			     <bean:write name="shopfuncForm" property="vo.cost"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">是否已支付:</div>
			   <div class="field"> 
			     <bean:define id="ispay" name="shopfuncForm" property="vo.ispay" type="java.lang.Short"/>
			     <%=ShopfuncConstant.getIsPayName(ispay,locale) %>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">开始日期:</div>
			   <div class="field"> 
			     <bean:write name="shopfuncForm" property="vo.startdate" format="yyyy-MM-dd hh:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">中止日期:</div>
			   <div class="field"> 
			     <bean:write name="shopfuncForm" property="vo.enddate" format="yyyy-MM-dd hh:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">状态:</div>
			   <div class="field"> 
			     <% List statusList = ShopfuncConstant.getStatusLabels(locale); %>
			     <html:select name="shopfuncForm" property="vo.status" style="width:150px;">
				     <html:optionsCollection name="statusList"/>
			     </html:select>
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
  </body>
</html:html>
