<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看商店信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	  <div id="fieldsTitleDiv">商店基本信息</div>
	  <div id="fieldDisDiv">
	     <table cellspacing="7" cellpadding="0" border="0" width="100%">
			  <tr>
			     <td class="field_left">商店名称:</td>
			     <td class="login_field"><bean:write name="shopForm" property="vo.shopname"/></td>
			     <td id="shopnameId_tip_td" class="login_fieldDesc"></td>
			  </tr>

              <tr>
			     <td class="field_left">商店代号:</td>
			     <td class="login_field"><bean:write name="shopForm" property="vo.shopcode"/></td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">所属行业:</td>
			     <td class="login_field">
			         <bean:writeSaas name="shopForm" property="vo.bizarea" consCode="common.bizArea"/>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店类型:</td>
			     <td class="login_field">
			         <bean:writeSaas name="shopForm" property="vo.ownertype" consCode="platform.ShopConstant.OwnerType"/>
			     </td>
			     <td id="ownertypeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店开放类型:</td>
			     <td class="login_field" colspan="2">
			         <bean:writeSaas name="shopForm" property="vo.opentype" consCode="platform.ShopConstant.OpenType"/>
			     </td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店人数规模:</td>
			     <td class="login_field">
			         <bean:writeSaas name="shopForm" property="vo.usernumscale" consCode="platform.ShopConstant.UserNumScale"/>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">电子邮件:</td>
			     <td class="login_field">
			        <bean:write name="shopForm" property="contactinfo.email"/>
			     </td>
			     <td id="emailId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">电话/手机:</td>
			     <td class="login_field">
			        <bean:write name="shopForm" property="contactinfo.telephone"/>
			     </td>
			     <td id="telephoneId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店所在地:</td>
			     <td colspan="2">
			        <bean:writeSaas name="shopForm" property="contactinfo.regioncode" consCode="CountryregionConstant.RegionCode"/>
			     </td>
			     
			  </tr>

			  <tr>
			     <td class="field_left">商店地址:</td>
			     <td class="login_field">
			        <bean:write name="shopForm" property="contactinfo.address"/>
			     </td>
			     <td id="addressId_tip_td" class="login_fieldDesc"></td>
			  </tr>

	      </table>
	  </div>

	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
