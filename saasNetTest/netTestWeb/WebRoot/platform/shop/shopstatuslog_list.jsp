<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店状态变化</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">

  <div class="titleBar"><bean:write name="shopstatuslogForm" property="shopname"/>&nbsp;&nbsp;&nbsp;状态变化</div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"></td>

        <td>原状态</td>

        <td>改变后状态</td>

        <td>日期</td>

        <td>描述</td>

      </tr>
      <logic:present name="shopstatuslogForm" property="logList">
	  <logic:iterate id="vo" name="shopstatuslogForm" property="logList">
      <tr>
        <td></td>

        <td><bean:writeSaas name="vo" property="bfstatus" consCode="ShopConstant.ShopStatus"/></td>

        <td><bean:writeSaas name="vo" property="afstatus" consCode="ShopConstant.ShopStatus"/></td>
        
        <td><bean:write name="vo" property="statustime" format="yyyy-MM-dd"/></td>

        <td><bean:write name="vo" property="statusdisc"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
      <logic:notPresent name="shopstatuslogForm" property="logList">
          
      </logic:notPresent>
    </table>
  </div>
  <div id="buttomDiv"></div>

  </div>

  </body>
</html:html>
