<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<!-- operator ware list -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="wareForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="wareVO" name="wareForm" property="vo"/>
    <bean:define id="productId" name="wareForm" property="sessionProductid" type="java.lang.Long"/>
    <bean:define id="productName" name="wareForm" property="sessionProductname" type="java.lang.String"/>
    <title>题库列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language="javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	
  </head>
    <%if(productId==null){
       productName = "所有可用产品";
    } %>
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/wareques/listWare1.do?recurltype=1" method="post">
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=(productId==null)?"":productId.toString() %>">
  <input id="prodnameId" type="hidden" name="productname" value="">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="wareForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="wareForm" property="backUrlEncode"/>">
  <input type="hidden" name="shopid" value="<bean:write name="wareForm" property="shopid"/>">
  <div class="fieldsTitleDiv">
       <bean:write name="wareForm" property="productvo.productname"/>
  </div>
  <div id="firstLineDiv">
      <div id="searchDiv">
         题库: 
		<input type="text" name="queryVO.warename" value="<bean:write name="wareForm" property="queryVO.warename"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" style="margin-left:30px;" class="button green fontBold" onclick="addRecord('/wareques/addWare.do');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
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
      <%if(productId==null){ %> 请选择课程信息，否则不能查询 <%} %>
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>题库名</td>
        <td>创建时间</td>
        <td>所属产品</td>
        <td>题目数</td>
        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><a href="javascript:void(0);" onclick="goUrl('/wareques/listWareques1.do?queryVO.wareid=<bean:write name="vo" property="wareid"/>');return false;">
            <bean:write name="vo" property="warename"/></a>
            <input id='pkId[<bean:write name="vo" property="wareid"/>]Name' type="hidden" value="<bean:write name="vo" property="warename"/>"/>
        </td>
        <td><bean:write name="vo" property="warecreatetime" format="yyyy-MM-dd"/></td>
        <td><bean:write name="vo" property="productname"/></td>
        <td><bean:write name="vo" property="warequesnum"/></td>
        <td>
            <img src="../styles/imgs/exam.png" title="题目管理" style="cursor:pointer;" onclick="goUrl('/wareques/listWareques1.do?queryVO.wareid=<bean:write name="vo" property="wareid"/>');return false;"/>&nbsp;
		    <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/wareques/editWare.do?queryVO.wareid=<bean:write name="vo" property="wareid"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/wareques/deleteWare.do?vo.wareid=<bean:write name="vo" property="wareid"/>&backUrl=');return false;"/>
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
