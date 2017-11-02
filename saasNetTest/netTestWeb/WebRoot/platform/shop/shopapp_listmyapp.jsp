<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.ShopappConstant,netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>申请商店列表</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../../styles/css/tab/simpleTab2.css" />
	<link href="../../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">

		#bannermenu4{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	
	</style>
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block"> 
  <tiles:insert page="/userAdmin/banner_user.jsp"></tiles:insert>
  
  <div class="listPage">
  <html:form styleId="list" action="/shop/listmyshopapp.do" method="post">
   
  <div class="navlistBar">
       商店管理&nbsp;&gt;&gt;&nbsp;申请商店记录
  </div>
  
  <div style="height:auto; width:100%;">
     <ul class="tabnav">
	    <li><a href="<%=WebConstant.WebContext %>/shop/myOwnShops.do">我的学校</a></li>
		<li class='selectTab'><a href="javascript:void(0)">申请学校记录</a></li>
	 </ul>
  </div>
  
  <div id="firstLineDiv">
      <div id="searchDiv">商店名: 
		<input type="text" name="queryVO.shopname" value="<bean:write name="shopappForm" property="queryVO.shopname"/>"/>
		&nbsp;
		审核状态:
		<html:select name="shopappForm" property="queryVO.appstatus" >
            <option></option>
			<html:optionsSaas consCode="platform.ShopappConstant.AppStatus"/>
		 </html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>

        <td>商店</td>

        <td>申请日期</td>

        <td>审核状态</td>
      </tr>
      </thead>
      <logic:present name="shopappForm" property="shopapplist">
	  <logic:iterate id="vo" name="shopappForm" property="shopapplist" indexId="ind" type="platform.vo.Shopapp">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>

        <td><a href="javascript:void(0);" onclick="newDiv('/shop/viewshopappPage.do?queryVO.shopappid=<bean:write name="vo" property="shopappid"/>',0,1,650,400);return false;" title="查看详情"><bean:write name="vo" property="shopname"/></a>
            (<bean:write name="vo" property="shopcode"/>)
        </td>

        <td><bean:write name="vo" property="appdate" format="yyyy-MM-dd"/></td>

        <td>
            <bean:writeSaas name="vo" property="appstatus" consCode="platform.ShopappConstant.AppStatus"/>
            <%if(!ShopappConstant.AppStatus_needApprove.equals(vo.getAppstatus())) {%>
            (操作日期:<bean:write name="vo" property="replydate" format="yyyy-MM-dd"/>)
            <%} %>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  
  <jsp:include flush="true" page="../../foot.jsp"></jsp:include>
  </div>

  </body>
</html:html>
