<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="shopcontactinfoForm" property="jsSuffix" type="java.lang.String"/>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑商店简介</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div >
	   <div class="fieldsTitleDiv"><bean:write name="shopcontactinfoForm" property="shopvo.shopname"/></div>

	  <div >
		 <table class="formTable">
		     <tr>
		        <td align="right">电话:</td>
		        <td><bean:write name="shopcontactinfoForm" property="vo.telephone"/></td>
		        <td align="right">电子邮件:</td>
		        <td><bean:write name="shopcontactinfoForm" property="vo.email"/></td>
		     </tr>
		     <tr>
		        <td align="right">所在地区:</td>
		        <td colspan="3">
		            
		            <bean:writeSaas name="shopcontactinfoForm" property="vo.regioncode" consCode="CountryregionConstant.RegionCode"/>

		        </td>
		     </tr>
		     <tr>
		        <td align="right">具体地址:</td>
		        <td colspan="3">
                    <bean:write name="shopcontactinfoForm" property="vo.address"/>
		        </td>
		     </tr>
		 </table>
	  </div>

	</div>
	
  </body>
</html:html>
