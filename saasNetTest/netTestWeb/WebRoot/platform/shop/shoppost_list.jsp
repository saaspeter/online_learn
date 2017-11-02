<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shoppostForm" property="jsSuffix" type="java.lang.String"/>
    <title>商店公告</title>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<link href="<%=request.getContextPath() %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <div class="navlistBar">
	     公告管理&nbsp;&nbsp;(公告通知，商店动态等)
  </div>

  <div style="height:5px; clear:both;"></div>
  
  <html:form styleId="list" action="/shop/listShoppost.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shoppostForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shoppostForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="searchDiv">标题: 
		<input type="text" name="queryVO.caption" value="<bean:write name="shoppostForm" property="queryVO.caption"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
	  </div>
  </div>
  <div id="functionLineDiv" style="height:2.5em">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" class="btn btn-success fontBold" style="margin-left:30px;" onclick="addRecord('/shop/addShoppost.do');"><bean:message key="netTest.commonpage.add"/></button></li>
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
      <thead>
      <tr class="listDataTableHead">
        <td width="10px"></td>
        <td>标题</td>
        <td>创建日期</td>
        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><a href="javascript:void(0)" onclick="newDiv('/shop/viewShoppost.do?queryVO.id=<bean:write name="vo" property="id"/>',0,1,600,400);"><bean:write name="vo" property="caption"/></a></td>
        <td><bean:write name="vo" property="createtime" format="yyyy-MM-dd"/></td>
        <td>
            <img src="../../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/shop/editShoppost.do?queryVO.id=<bean:write name="vo" property="id"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
            <img src="../../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/shop/deleteShoppost.do?vo.id=<bean:write name="vo" property="id"/>&backUrl=');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>

  </body>
</html:html>
