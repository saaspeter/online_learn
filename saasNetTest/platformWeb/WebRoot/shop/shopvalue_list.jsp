<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/shop/listshopvalue.do" method="post">
   <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopvalueForm" property="backUrl"/>">
   <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopvalueForm" property="backUrlEncode"/>">	
   <input type="hidden" name="queryVO.shopid" value="<bean:write name="shopvalueForm" property="queryVO.shopid"/>">	
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/shop/addshopvaluepage.do?vo.shopid=<bean:write name="shopvalueForm" property="queryVO.shopid"/>');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 
		  </ul>
	  </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="head">
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>

        <td>商店名</td>
        
        <td>注册地</td>

        <td></td>
      </tr>
      <logic:present name="shopvalueForm" property="shopvalueList">
	  <logic:iterate id="vo" name="shopvalueForm" property="shopvalueList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="shopvalueid"/>" onClick="selectInfo(this,'clickedRow')"></td>

        <td><a href="javascript:newDiv('/shop/viewshopdescript.do?queryVO.shopvalueid=<bean:write name="vo" property="shopvalueid"/>&editType=<%=WebConstant.editType_view %>',0,1,500,200);"><bean:write name="vo" property="shopname"/></a></td>

        <td><bean:write name="vo" property="localeName"/></td>

        <td><a href="/shop/editshopdescript.do?queryVO.shopid=<bean:write name="vo" property="shopid"/>&queryVO.localeid=<bean:write name="vo" property="localeid"/>">编辑</a>
            <a href="/shop/deleteShopValue.do?vo.shopvalueid=<bean:write name="vo" property="shopvalueid"/>">删除</a>
        </td>
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
