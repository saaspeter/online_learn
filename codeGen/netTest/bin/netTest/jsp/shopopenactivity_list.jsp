<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shopopenactivityForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="netTest.page.product.shopopenactivity_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listShopopenactivity.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopopenactivityForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopopenactivityForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="netTest.page.product.shopopenactivity.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="shopopenactivityForm" property="queryVO.{[#searchColumn#]}"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="netTest.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>Property one</td>
              <td>Property two</td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button onclick="addRecord('/product/addShopopenactivity.do');"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button onclick="delRec('list','keys','/product/deleteShopopenactivity.do');"><bean:message key="netTest.commonpage.delete"/></button></li>
			 
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
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.name"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.starttime"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.during"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.jointype"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.onlineurl"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.localeid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.regioncode"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.place"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.contactinfo"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.shopid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.productid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.creatorid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.status"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.description"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.createtime"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.lastupdatetime"/></td>


        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="activityid"/>' value="<bean:write name="vo" property="activityid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/product/viewShopopenactivity.do?queryVO.activityid=<bean:write name="vo" property="activityid"/>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <input id='pkId<bean:write name="vo" property="activityid"/>Name' type="hidden" value="<bean:write name="vo" property="{[#searchColumn#]}"/>"/>
        <td><bean:write name="vo" property="name"/></td>

        <td><bean:write name="vo" property="starttime"/></td>

        <td><bean:write name="vo" property="during"/></td>

        <td><bean:write name="vo" property="jointype"/></td>

        <td><bean:write name="vo" property="onlineurl"/></td>

        <td><bean:write name="vo" property="localeid"/></td>

        <td><bean:write name="vo" property="regioncode"/></td>

        <td><bean:write name="vo" property="place"/></td>

        <td><bean:write name="vo" property="contactinfo"/></td>

        <td><bean:write name="vo" property="shopid"/></td>

        <td><bean:write name="vo" property="productid"/></td>

        <td><bean:write name="vo" property="creatorid"/></td>

        <td><bean:write name="vo" property="status"/></td>

        <td><bean:write name="vo" property="description"/></td>

        <td><bean:write name="vo" property="createtime"/></td>

        <td><bean:write name="vo" property="lastupdatetime"/></td>


        <td><a href="#" onclick="goUrl('/product/editShopopenactivity.do?queryVO.activityid=<bean:write name="vo" property="activityid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="shopopenactivityForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
