<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="testdeptForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="netTest.page.exam.testdept_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/listTestdept.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="testdeptForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="testdeptForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value=""/>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="netTest.page.exam.testdept.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="testdeptForm" property="queryVO.{[#searchColumn#]}"/>"/>
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
			 <li><button type="button" onclick="addRecord('/exam/addTestdept.do');"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="delRec('list','keys','/exam/deleteTestdept.do');"><bean:message key="netTest.commonpage.delete"/></button></li>
			 
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
        <td><bean:message key="netTest.page.{[#folder#]}.testdept.shopid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.orgbaseid"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.orgname"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptuserset"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptquaper"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptavescore"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptusernum"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptselfusernum"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdepttestingnum"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptendnum"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptselfendnum"/></td>

        <td><bean:message key="netTest.page.{[#folder#]}.testdept.status"/></td>


        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="testdeptid"/>' value="<bean:write name="vo" property="testdeptid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <input id='pkId<bean:write name="vo" property="testdeptid"/>Name' type="hidden" value="<bean:write name="vo" property="{[#searchColumn#]}"/>"/>
        <td><bean:write name="vo" property="shopid"/></td>

        <td><bean:write name="vo" property="testid"/></td>

        <td><bean:write name="vo" property="orgbaseid"/></td>

        <td><bean:write name="vo" property="orgname"/></td>

        <td><bean:write name="vo" property="testdeptuserset"/></td>

        <td><bean:write name="vo" property="testdeptquaper"/></td>

        <td><bean:write name="vo" property="testdeptavescore"/></td>

        <td><bean:write name="vo" property="testdeptusernum"/></td>

        <td><bean:write name="vo" property="testdeptselfusernum"/></td>

        <td><bean:write name="vo" property="testdepttestingnum"/></td>

        <td><bean:write name="vo" property="testdeptendnum"/></td>

        <td><bean:write name="vo" property="testdeptselfendnum"/></td>

        <td><bean:write name="vo" property="status"/></td>


        <td><a href="#" onclick="goUrl('/exam/editTestdept.do?queryVO.testdeptid=<bean:write name="vo" property="testdeptid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="testdeptForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
