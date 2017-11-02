<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,netTestWeb.base.WebConstant,platform.constant.ShopfuncConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店功能列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/shopfunc.do?method=list" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopfuncForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopfuncForm" property="backUrlEncode"/>">	
 <input type="hidden" name="queryVO.shopid" value="<bean:write name="shopfuncForm" property="queryVO.shopid"/>">
  <bean:define id="locale" name="shopfuncForm" property="locale" type="java.util.Locale"></bean:define>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/shop/shopfunc.do?method=addPage&vo.shopid=<bean:write name="shopfuncForm" property="queryVO.shopid"/>');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/shop/shopfunc.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete"/></button></li>
			 <li><button type="button" onclick="document.forms[0].submit()"><bean:message key="platform.commonpage.refreash"/></button></li>
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
    <table class="listDataTable" >
      <tr class="head">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>功能名</td>

        <td>支付类型</td>

        <td>所属系统</td>

        <td>费用</td>

        <td>是否支付</td>

        <td>使用时间</td>

        <td>结束时间</td>

        <td>有效性</td>

      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
	  <bean:define id="paytype" name="vo" property="paytype" type="java.lang.Short"/>
      <bean:define id="systemcode" name="vo" property="syscode" type="java.lang.String"/>
      <bean:define id="ispay" name="vo" property="ispay" type="java.lang.Short"/>
      <bean:define id="status" name="vo" property="status" type="java.lang.Short"/>
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="shopfuncid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td><a href="javascript:newDiv('/shop/shopfunc.do?method=editPage&queryVO.shopfuncid=<bean:write name="vo" property="shopfuncid"/>&editType=<%=WebConstant.editType_view%>',0,1,600,400);"><bean:write name="vo" property="functionname"/></a></td>

        <td><bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/></td>

        <td><%=CommonConstant.qrySystemName(systemcode,locale) %></td>

        <td><bean:write name="vo" property="cost"/></td>

        <td><%=ShopfuncConstant.getIsPayName(ispay,locale) %></td>

        <td><bean:write name="vo" property="startdate" format="yyyy-MM-dd"/></td>

        <td><bean:write name="vo" property="enddate" format="yyyy-MM-dd"/></td>

        <td><%=ShopfuncConstant.getStatusName(status,locale) %></td>

      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
         setListTableStyle();
       }
     //-->
  </script>
  </body>
</html:html>
