<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shopopenactivitymemberForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="netTest.page.product.shopopenactivitymember_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listShopopenactivitymember.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopopenactivitymemberForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopopenactivitymemberForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="netTest.page.product.shopopenactivitymember.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="shopopenactivitymemberForm" property="queryVO.{[#searchColumn#]}"/>"/>
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
			 <li><button onclick="addRecord('/product/addShopopenactivitymember.do');"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button onclick="delRec('list','keys','/product/deleteShopopenactivitymember.do');"><bean:message key="netTest.commonpage.delete"/></button></li>
			 
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
        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.userid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.displayname"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.phone"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.othercontactinfo"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.registertime"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.joinstatus"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.shopopenactivitymember.note"/></td>


        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="activityid"/>' value="<bean:write name="vo" property="activityid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/product/viewShopopenactivitymember.do?queryVO.activityid=<bean:write name="vo" property="activityid"/>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <input id='pkId<bean:write name="vo" property="activityid"/>Name' type="hidden" value="<bean:write name="vo" property="{[#searchColumn#]}"/>"/>
        <td><bean:write name="vo" property="userid"/></td>

        <td><bean:write name="vo" property="displayname"/></td>

        <td><bean:write name="vo" property="phone"/></td>

        <td><bean:write name="vo" property="othercontactinfo"/></td>

        <td><bean:write name="vo" property="registertime"/></td>

        <td><bean:write name="vo" property="joinstatus"/></td>

        <td><bean:write name="vo" property="note"/></td>


        <td><a href="#" onclick="goUrl('/product/editShopopenactivitymember.do?queryVO.activityid=<bean:write name="vo" property="activityid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="shopopenactivitymemberForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
