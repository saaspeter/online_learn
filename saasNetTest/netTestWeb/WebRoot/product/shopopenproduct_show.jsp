<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.vo.Productbase" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>培训课程</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type="text/javascript">
	    
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listshopopenproduct.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productForm" property="backUrlEncode"/>">
  <input type="hidden" name="queryVO.shopid" value="<bean:write name="productForm" property="queryVO.shopid"/>">
  <div id="firstLineDiv">
      <div id="help">
	       
      </div>
  </div>

  <div class="titleBar"><%=request.getParameter("title") %></div>
  
  <logic:empty name="productForm" property="queryVO.categoryid">
  	 <div></div>
  </logic:empty>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr>
        <td style="width: 242px;"></td>
        <td></td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="productForm" property="productList">
	  <logic:iterate id="vo" name="productForm" property="productList" indexId="idx" type="netTest.product.vo.Productbase">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td>
            <img src="<%=WebConstant.getDefaultLogoImg(vo.getLogoimage(), Productbase.ObjectType) %>" style="width: 240px;height: 135px;cursor: pointer;" alt="<bean:write name="vo" property="productname"/>" 
                 onclick="goUrl('<%=WebConstant.WebContext %>/product/viewCourseToBuy.do?shopid=<bean:write name="vo" property="shopid"/>&queryVO.productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrlEncode=','backUrlEncode'); return false;" />
        </td>
        <td>
            <span style="font-size: x-large;">
            <a href="javascript:void(0);" onclick="goUrl('<%=WebConstant.WebContext %>/product/viewCourseToBuy.do?shopid=<bean:write name="vo" property="shopid"/>&queryVO.productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrlEncode=','backUrlEncode'); return false;"><bean:write name="vo" property="productname"/></a> 
            </span>
        </td>
	    <td><bean:write name="vo" property="productprice"/>&nbsp;(<bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/>)</td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  </html:form>
  </div>

  </body>
</html:html>
