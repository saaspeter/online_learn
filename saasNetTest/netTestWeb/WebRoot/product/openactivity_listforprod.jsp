<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.KeyInMemoryConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="openactivityForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="ableadd" name="openactivityForm" property="ableAdd" type="java.lang.Boolean"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="productname" name="openactivityForm" property="queryVO.productname" type="java.lang.String"/>
<bean:define id="productid" name="openactivityForm" property="queryVO.productid" type="java.lang.Long"/>
<%  
   int rows = -1;
   String shopStr = "";

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/list.css" />
	<link href="<%=request.getContextPath() %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	<!--
	
	-->
	</style>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/bower_components/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/bower_components/bootstrap/js/tooltip.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/utiltool.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/commonlogic.js"></script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  
  <div class="navlistBar">
	  <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_operatorMag %>');" title="点击选择产品"><%=productname %></a> &gt; 公开活动&nbsp;(包括:免费公开培训信息, 公开户外学习活动等)
  </div>

  <div style="height:5px; clear:both;"></div>
  
  <form id="listFormId" action="<%=request.getContextPath() %>/product/listOpenactivity.do" method="post">
  <input type="hidden" name="showtype" value="3"/>
  <input type="hidden" id="pageIndex_id" name="pageIndex"/>
  <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
  <input type="hidden" id="prodidId" name="queryVO.productid" value='<bean:write name="openactivityForm" property="queryVO.productid"/>'/>
  <input type="hidden" id="prodnameId" name="queryVO.productname" value='<bean:write name="openactivityForm" property="queryVO.productname"/>'/>
  <div id="firstLineDiv">
      <div id="searchDiv">标题: 
		<input type="text" class="userInput" name="queryVO.name" value='<bean:write name="openactivityForm" property="queryVO.name"/>' />
		状态:
		<html:select name="openactivityForm" property="queryVO.status" styleClass="select_second">
		    <html:option value=""></html:option>
		    <html:optionsSaas consCode="OpenActivity.Status"/>
		</html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
	  </div>
  </div>
  <div id="functionLineDiv" style="height:2.5em">
	  <div id="functionButtonDiv" <%if(!ableadd){ %> data-toggle="tooltip" data-placement="bottom" data-original-title='只有认证通过的商店才可以发布公开活动，请先认证商店' <%} %> >
		  <ul>
			 <li><button type="button" class="btn btn-success fontBold" style="margin-left:30px;"
			     <%if(ableadd){ %>onclick="addRecord('/product/addOpenactivity.do?vo.productid=<%=productid %>&showtype=3');" 
			     <%} else { %> disabled="disabled" <%} %> >
			     <bean:message key="netTest.commonpage.add"/></button>
			 </li>
		  </ul>
	  </div>
	  <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
  </div>
  </form>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div style="width: 100%;margin-bottom: 10px;min-height:300px;">
    <table class="listDataTable">
      <thead>
      <tr class="listDataTableHead">
        <td width="10px"></td>
        <td>活动</td>
        <td>活动时间</td>
        <td>状态</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td></td>
        <td>
            <a href='<%=request.getContextPath() %>/product/viewOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>&showtype=1&formobile=0' target='_openactivity'>
            <bean:write name="vo" property="name"/></a>
        </td>
        <td><bean:writeDate name="vo" property="starttime" formatKey="Common.DateFormatType.DateTime"/> --- 
            <bean:writeDate name="vo" property="endtime" formatKey="Common.DateFormatType.DateTime"/>
        </td>
        <td><bean:writeSaas name="vo" property="status" consCode="OpenActivity.Status"/></td>
        <td>
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/product/editOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>&showtype=3');return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/product/deleteOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>&backUrl=');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  </div>
  <script type='text/javascript'>
    $(function () {
	  $('[data-toggle="tooltip"]').tooltip('show')
	})
  </script>
  </body>
</html:html>
