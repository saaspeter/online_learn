<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.KeyInMemoryConstant,netTest.product.vo.Openactivity" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="openactivityForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="currentPage" name="page" property="currentPage" type="java.lang.Integer"/>
<bean:define id="totalPage" name="page" property="totalPage" type="java.lang.Integer"/>
<bean:define id="localeid" name="openactivityForm" property="queryVO.localeid" type="java.lang.Long"/>
<%  
   int rows = -1;
   String shopStr = "";
   Long categoryid = (Long)session.getAttribute(KeyInMemoryConstant.CategoryID_Key);
   String categoryname = (String)session.getAttribute(KeyInMemoryConstant.CategoryName_Key);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="openactivityForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="netTest.page.common.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/banner.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/tab/simpleTab2.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/css/list.css" />
<link href="<%=request.getContextPath() %>/styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<style type="text/css">
	<!--
	
	#menu21{
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
  
  <jsp:include flush="true" page="/shop/banner_shop.jsp"></jsp:include>
    <div class="navlistBar">
	     公开活动&nbsp;&nbsp;(包括:免费公开培训信息, 公开户外学习活动等)
    </div>

    <div style="height:5px; clear:both;"></div>
  
  <div style="text-align: center;padding: 3px;margin-bottom: 10px;width: 100%;">
	    <form id="listFormId" action="<%=request.getContextPath() %>/product/listOpenactivity.do" method="post">
	       <input type="hidden" name="shopid" value="<bean:write name="openactivityForm" property="shopid"/>"/>
	       <input type="hidden" name="showtype" value="1"/>
	       <input type="hidden" id="Id_categoryid" name="categoryid" value="<%=categoryid %>"/>
	       <input type="hidden" id="Id_categoryname" name="categoryname" value="<%=categoryname %>"/>
	       <input type="hidden" id="pageIndex_id" name="pageIndex"/>
	       <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	       <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value=""/>
	       <table border="0" cellpadding="2px" style="text-align: left;margin: auto;">
	           <tr>
	               <td>
	                   <input type="text" name="searchtext" class="searchTxt_second" value="<bean:write name="openactivityForm" property="queryVO.name"/>" /> 
				       &nbsp;<button type="button" onclick="document.forms[0].submit();" class="btn btn-primary">搜索</button>
	               </td>
	           </tr>
	           
	       </table>
	    </form>
  </div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div style="width: 100%;margin-bottom: 10px;min-height:300px;">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>活动</td>
        <td>开始时间</td>
        <td>活动地区</td>
        <td>活动状态</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.product.vo.Openactivity">
      <tr>
        <td></td>
        <td>
           <a href='<%=request.getContextPath() %>/product/viewOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>&showtype=1&formobile=0'>
           <bean:write name="vo" property="name"/>
           </a>
        </td>
        <td><bean:writeDate name="vo" property="starttime" formatKey="Common.DateFormatType.DateTime"/><br>
            --- <bean:writeDate name="vo" property="endtime" formatKey="Common.DateFormatType.DateTime"/>
        </td>
        <td><bean:writeSaas name="vo" property="regioncode" consCode="CountryregionConstant.RegionCode"/></td>
        <td>
            <%if(Openactivity.Status_Scheduled.equals(vo.getStatus())) { %>
            <button type="button" class="btn btn-success" onclick="goUrl('/product/viewOpenactivity.do?vo.activityid=<bean:write name="vo" property="activityid"/>')">了解详情</button>
            <%}else { %>
            <bean:writeSaas name="vo" property="status" consCode="OpenActivity.Status"/>
            <%} %>
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

  </script>
  </body>
</html:html>
