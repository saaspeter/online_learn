<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>培训机构商店列表</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/listapplyshopauthen.do" method="get">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">	
  
  <div id="firstLineDiv">
      <div id="searchDiv">
        &nbsp; 
         商店code: 
		<input type="text" name="searchtext" value="<bean:write name="shopForm" property="extvo.shopcode"/>" /> 
		申请状态:
		 <html:select name="shopForm" property="extvo.authenstatus" >
            <option></option>
			<html:optionsSaas consCode="platform.ShopConstant.ApplyAuthenStatus"/>
		 </html:select>
		&nbsp;
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
	  </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li></li>
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
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
        <td>商店名</td>
        <td>申请状态</td>
        <td>申请时间</td>
        <td></td>
      </tr>
      </thead>
      <tbody>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td>
            <a href="javascript:void(0);" onclick="newDiv('/shop/viewshoppage.do?queryVO.shopid=<bean:write name="vo" property="shopid"/>',0,1,500,350);"><bean:write name="vo" property="shopname"/></a>
            (<bean:write name="vo" property="shopcode"/>)
        </td>  
        
        <td><bean:writeSaas name="vo" property="authenstatus" consCode="platform.ShopConstant.ApplyAuthenStatus"/></td>  

        <td><bean:write name="vo" property="authendate" format="yyyy-MM-dd"/></td>
        <td>
            <img src="../../styles/imgs/edit.png" style="cursor:pointer;" title="修改" onclick="goUrl('/platform/shop/shopMagAdminMain.jsp?shopid=<bean:write name="vo" property="shopid"/>&shopname=<bean:write name="vo" property="shopname"/>&backUrlEncode=','backUrlEncode');return false;"/>
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
