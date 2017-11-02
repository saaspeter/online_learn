<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="openactivitymemberForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="canadmin" name="openactivitymemberForm" property="canadmin" type="java.lang.Boolean"/>
    <title></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/list.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listOpenactivitymember.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="openactivitymemberForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="openactivitymemberForm" property="backUrlEncode"/>">
  <div class="titleBar">
               人员列表
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td>姓名</td>
		<%if(canadmin){ %>
        <td>联系方式</td>
		<%} %>
        <td>报名时间</td>
		<%if(canadmin){ %>
        <td>状态</td>
        <%} %>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><bean:write name="vo" property="displayname"/></td>
		<%if(canadmin){ %>
        <td><bean:write name="vo" property="phone"/>&nbsp;&nbsp;
            <bean:write name="vo" property="othercontactinfo"/>
        </td>
		<%} %>
        <td><bean:writeDate name="vo" property="registertime" format="MM/dd HH:mm"/></td>
		<%if(canadmin){ %>
        <td><bean:writeSaas name="vo" property="joinstatus" consCode="OpenActivityMember.JoinStatus"/>
            <img src="<%=request.getContextPath() %>/styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/product/editOpenactivitymember.do?vo.activityid=<bean:write name="vo" property="activityid"/>&vo.userid=<bean:write name="vo" property="userid"/>');return false;"/>
        </td>
        <%} %>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
    <div style="margin-top:7px;"><jsp:include flush="true" page="/pubs/pagetiles_bottom.jsp"></jsp:include></div>
  </div>
  
  </html:form>
  </div>
  </body>
</html:html>
