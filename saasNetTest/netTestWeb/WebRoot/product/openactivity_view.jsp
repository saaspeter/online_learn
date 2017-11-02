<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.product.vo.Openactivity" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="jointype" name="openactivityForm" property="vo.jointype" type="java.lang.Short"></bean:define>
<bean:define id="shopid" name="openactivityForm" property="vo.shopid" type="java.lang.Long"></bean:define>
<bean:define id="productid" name="openactivityForm" property="vo.productid" type="java.lang.Long"></bean:define>
<bean:define id="status" name="openactivityForm" property="vo.status" type="java.lang.Short"></bean:define>

<%
    String formobile = request.getParameter("formobile");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:write name="openactivityForm" property="vo.name"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link href="<%=request.getContextPath() %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
		<!--
	    #menu21{
			display: block;
			color: #667;
			background-color: #ec8;
		}
		
		#leftAD {
			float: left;
			background-color: #FFFFFF;
			margin-top: 5em;
			width: 120 px;
		}
		
		#content_mobile {
			width: 95%;
			padding-left: 2px;
		}
		
		#contentDivId img {
		    max-width:100% !important;
		    height:auto !important;
		}
		
		hr{
		   background-color:blue;
		}
		
		hr.style-eight {
		    padding: 0;
		    margin: 35px 15px 35px 15px;
		    border: none;
		    border-top: medium double #333;
		    color: #333;
		    text-align: center;
		}
		hr.style-eight:after {
		    content: "详细信息";
		    display: inline-block;
		    position: relative;
		    top: -0.7em;
		    font-size: 1.5em;
		    padding: 0 0.25em;
		    background: white;
		}
		-->
		</style>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    
  </head>
  
  <body>
    <input id="backUrl" type="hidden" name="backUrl" value='<bean:write name="openactivityForm" property="backUrl"/>'>
	<div id='<%if("0".equals(formobile)){ out.print("content"); }else {out.print("content_mobile");}; %>' class="col-xs-12 col-md-9 center-block">
	    <%if("0".equals(formobile)){ %>
	        <jsp:include flush="true" page='<%="/shop/banner_shop.jsp?shopid="+shopid %>'></jsp:include>
	    <div class="navlistBar">
			
		</div>
	    <%}else { %>
	    <jsp:include flush="true" page='/index/banner_short_mobile.jsp'></jsp:include>
	    <%} %>

		<div style="height: 5px; clear: both;"></div>

		<div id="fieldsTitleDiv">
			<bean:write name="openactivityForm" property="vo.name"/>
		</div>

		<p/>
		
		<div id="" style="width: 90%; margin: 10px; text-align: center;">
			<table class="formTable2" style="margin-left:5%;">
			    <tr>
			        <td style="width:6em;">时间:</td>
			        <td><bean:writeDate name="openactivityForm" property="vo.starttime" formatKey="Common.DateFormatType.DateTime"/>
            			    --- <bean:writeDate name="openactivityForm" property="vo.endtime" formatKey="Common.DateFormatType.DateTime"/></td>
			        <td style="width:6em;">活动状态:</td>
			        <td><bean:writeSaas name="openactivityForm" property="vo.status" consCode="OpenActivity.Status"/></td>
			    </tr>
			    <%if(Openactivity.JoinType_Offline.equals(jointype)){ %>
			    <tr>
			        <td>所在地区:</td>
			        <td><bean:writeSaas name="openactivityForm" property="vo.regioncode" consCode="CountryregionConstant.RegionCode"/></td>
			        <td>具体地址:</td>
			        <td><bean:write name="openactivityForm" property="vo.place"/></td>
			    </tr>
			    <%}else if(Openactivity.JoinType_Online.equals(jointype)){ %>
			    <tr>
			        <td>参加方式:</td>
			        <td><bean:writeSaas name="openactivityForm" property="vo.jointype" consCode="OpenActivity.JoinType"/></td>
			        <td>加会链接:</td>
			        <td><bean:write name="openactivityForm" property="vo.onlineurl"/></td>
			    </tr>
			    <%} %>
			    <tr>
			        <td>主办方:</td>
			        <td><bean:write name="openactivityForm" property="vo.shopname"/></td>
			        <td>联系方式:</td>
			        <td><bean:write name="openactivityForm" property="vo.contactinfo"/></td>
			    </tr>
			    <tr>
			        <td>
			        <%if(productid!=null){ %>所属课程<% }else { %>
			                       内容分类<% } %>
			        :</td>
			        <td>
			        <%if(productid!=null){ %><bean:write name="openactivityForm" property="vo.productname"/>
			        <%}else { %><bean:write name="openactivityForm" property="categoryName" /><%} %>
			        </td>
			        <td colspan="2">
			            <a href="javascript:void(0)" class="messLink" onclick="newDiv('/product/listOpenactivitymember.do?queryVO.activityid=<bean:write name="openactivityForm" property="vo.activityid"/>',0,1,600,600);" title="点击查看具体参加人员">
			               <bean:write name="openactivityForm" property="vo.usernum"/>人报名
			            </a>
			            <%if(Openactivity.Status_Scheduled.equals(status)){ %>
			            <logic:notPresent name="openactivityForm" property="membervo">
			            &nbsp;,&nbsp;&nbsp;&nbsp;
			            <button class="btn btn-success" type="button" onclick="newDiv('/product/toJoinActivityPage.do?vo.activityid=<bean:write name="openactivityForm" property="vo.activityid"/>',0,1,600,450);">我要参加</button>
			            </logic:notPresent>
			            <%} %>
			            <logic:present name="openactivityForm" property="membervo">
			            &nbsp;,&nbsp; 我已参加该活动（<a class="messLink" href="javascript:void(0)" onclick="newDiv('/product/editOpenactivitymember.do?vo.activityid=<bean:write name="openactivityForm" property="vo.activityid"/>',0,1,600,470)">详情</a>）
			            <%if(Openactivity.Status_Scheduled.equals(status)){ %>，如不想参加，请点击&nbsp;
			                 <button class="btn btn-default" type="button" onclick="leaveActivity()">取消</button>
			            <%} %>
			            </logic:present>
			        </td>
			    </tr>
			</table>
		</div>
		
		
		<hr class="style-eight">
		
		
		<div id="contentDivId" style="width: 90%; margin: 10px; text-align: left;">
			<bean:write name="openactivityForm" property="vo.description" filter="false"/>
		</div>

		<div style="height: 20px; clear: both;"></div>
		<%if("0".equals(formobile)){ %>
		<jsp:include flush="true" page="/foot.jsp"></jsp:include>
		<%} %>
	</div>
	<script type="text/javascript">
	    function leaveActivity(){
	    	var id = '<bean:write name="openactivityForm" property="vo.activityid"/>';
	    	delSingleRecAjax('/product/userleaveactivity.do?queryVO.activityid='+id, 'backUrl', '确定取消参加活动?');
	    }
	</script>
  </body>
</html:html>
