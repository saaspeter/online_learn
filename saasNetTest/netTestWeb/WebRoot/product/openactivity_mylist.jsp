<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="openactivitymemberForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="openactivitymemberForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="netTest.page.common.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/banner.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/list.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/bootstrap/3.3.1/css/bootstrap.min.css"/>
	<style type="text/css">
	<!--
	#bannermenu7{
	  display: block;
	  color: #667;
	  background-color: #ec8;
	}
	-->
	</style>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  <div class="navlistBar">
	    我的社交  &gt; 公开活动&nbsp;&nbsp;(包括:免费公开培训信息, 公开户外学习活动等)
  </div>

  <div style="height:5px; clear:both;"></div>

  <div style="width: 100%;margin-bottom: 10px;min-height:300px;">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>活动名</td>
        <td>我的状态</td>
        <td>参加时间</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td></td>
        <td>
            <a href='<%=request.getContextPath() %>/product/viewOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>&formobile=0' class="messLink" target="_myActivity">
               <bean:write name="vo" property="activityvo.name"/>
            </a> 
        </td>
        <td>
            <logic:present name="vo" property="joinedtime">
                <bean:writeDate name="vo" property="joinedtime" formatKey="Common.DateFormatType.DateTime"/>&nbsp;参加
            </logic:present>
            <logic:notPresent name="vo" property="joinedtime">
                <bean:writeDate name="vo" property="registertime" formatKey="Common.DateFormatType.DateTime"/>&nbsp;注册
            </logic:notPresent>
        </td>
        <td>
            <bean:writeSaas name="vo" property="activityvo.status" consCode="OpenActivity.Status"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  
  <jsp:include flush="true" page="/pubs/pagetiles_bottom.jsp"></jsp:include>
  <jsp:include flush="true" page="/foot.jsp"></jsp:include>
  
  </div>
  <script type='text/javascript'>
	 <!--
	   
     //-->
  </script>
  </body>
</html:html>
