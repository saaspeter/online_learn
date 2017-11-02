<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.shop.vo.Shopcontactinfo" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="shoplocaleid" name="shopcontactinfoForm" property="shopvo.localeid"></bean:define>
    <title>商店地址列表</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/list.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
  </head>

  <body>
  <div class="listPage">
  
  <div class="fieldsTitleDiv">
      <table style="border: 0px;width: 100%;text-align: center;">
         <tr>
            <td>
                <font style="font-weight: bold;font-size: x-large;">联系方式</font>
            </td>
         </tr>
         <tr>
            <td style="padding-top: 15px;">
                <font style="font-size: medium;padding: 15px;">商店所在国家:<bean:writeSaas name="shopcontactinfoForm" property="shopvo.localeid" showLocaleName="true"/></font>
            </td>
         </tr>
      </table>
  </div>
      
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div>
      <logic:present name="shopcontactinfoForm" property="contactList">
      <ul class="articleList">
	  <logic:iterate id="vo" name="shopcontactinfoForm" property="contactList" indexId="idx">
	      <li>
	          <table cellpadding="5px;">
	              <tr>
			          <td colspan="3">
			           所在地区: <bean:writeSaas name="vo" property="regioncode" consCode="CountryregionConstant.RegionCode" localeid="<%=shoplocaleid.toString() %>"/>
			            &nbsp;&nbsp;&nbsp;
			            <logic:equal name="vo" property="isdefault" value="<%=Shopcontactinfo.Isdefault_default.toString() %>">
			               <img src="<%=request.getContextPath() %>/styles/imgs/home.png" title="总部地址"/>
			            </logic:equal>
			          </td>
			      </tr>
			      <tr>
			          <td>联系人: <bean:write name="vo" property="contactname"/></td>
			          <td>联系电话: <bean:write name="vo" property="telephone"/></td>
			          <td>Email: <bean:write name="vo" property="email"/></td>
			      </tr>
			      <tr>
			          <td colspan="3">
			           详细地址: <bean:write name="vo" property="address" />
			          </td>
			      </tr>
	          </table>
	      </li>
      </logic:iterate>
      </ul>
      </logic:present>
  </div>
  <div id="buttomDiv"></div>

  </div>
  
  </body>
</html:html>
