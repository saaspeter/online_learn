<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.shop.vo.Shopcontactinfo" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店地址列表</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>

  </head>

  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/listshopcontact.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopcontactinfoForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopcontactinfoForm" property="backUrlEncode"/>">

  <div class="titleBar">
      <font style="font-weight: bold"><bean:write name="shopcontactinfoForm" property="shopvo.shopname"/></font>
  </div>
  
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" class="button green fontBold" onclick="addRecord('/shop/addshopcontact.do?vo.shopid=<bean:write name="shopcontactinfoForm" property="shopvo.shopid"/>');return false;">新增地址</button></li>
		  </ul>
	  </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table id="dataTableId" class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>所在地区</td>
        <td>电话</td>
        <td>电子邮件</td>
        <td></td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="shopcontactinfoForm" property="contactList">
	  <logic:iterate id="vo" name="shopcontactinfoForm" property="contactList" indexId="idx">
      <tr>
        <td>
            <logic:equal name="vo" property="isdefault" value="<%=Shopcontactinfo.Isdefault_default.toString() %>">
            <img src="../../styles/imgs/home.png" title="商店默认地址"/>
            </logic:equal>
        </td>
        <td><a href="javascript:void(0)" onclick="newDiv('/shop/viewshopcontact.do?queryVO.contactinfoid=<bean:write name="vo" property="contactinfoid"/>',0,1,500,350);return false;">
            <bean:writeSaas name="vo" property="regioncode" consCode="CountryregionConstant.RegionCode"/>
            </a></td>
        <td><bean:write name="vo" property="telephone"/></td>

        <td><bean:write name="vo" property="email"/></td>

        <td>
            <img src="../../styles/imgs/edit.png" style="width: 20px; height:20px; cursor:pointer;" onclick="goUrl('/shop/editshopcontact.do?queryVO.contactinfoid=<bean:write name="vo" property="contactinfoid"/>');return false;" title="修改"/>
            &nbsp;
            <logic:notEqual name="vo" property="isdefault" value="<%=Shopcontactinfo.Isdefault_default.toString() %>">
            <img src="../../styles/imgs/delete.png" style="width: 20px; height:20px; cursor:pointer;" onclick="deleteSingleRec('/shop/delshopcontact.do?vo.contactinfoid=<bean:write name="vo" property="contactinfoid"/>');return false;" title="删除"/>
            </logic:notEqual>
        </td>
        
      </tr>
      </logic:iterate>
      </logic:present>
      </tbody>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  
  </body>
</html:html>
